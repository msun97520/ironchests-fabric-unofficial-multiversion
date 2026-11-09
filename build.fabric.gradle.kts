plugins {
    // This plugin applies the correct loom variant based on the Minecraft version
    id("dev.kikugie.loom-back-compat")
}

// DO NOT set group = ...!
version = property("mod.version") as String
base.archivesName = "${property("mod.id")}-${sc.current.version}-fabric"

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

// This can be used for publishing on Modrinth and Curseforge
val compatibleVersions: List<String> = sc.properties.rawOrNull("mod", "mc_releases")
    ?.asList().orEmpty().map { it.toString() }

repositories {
    /**
     * Restricts dependency search of the given [groups] to the [maven URL][url],
     * improving the setup speed.
     */
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }
    strictMaven("https://www.cursemaven.com", "CurseForge", "curse.maven")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
}

dependencies {
    /**
     * Fetches only the required Fabric API modules to not waste time downloading all of them for each version.
     * @see <a href="https://github.com/FabricMC/fabric">List of Fabric API modules</a>
     */
    fun fapi(vararg modules: String) {
        for (it in modules) modImplementation(fabricApi.module(it, sc.properties["deps.fabric_api"]))
    }

    minecraft("com.mojang:minecraft:${sc.current.version}")
    // Applies Mojang Mappings on obfuscated versions
    loomx.applyMojangMappings()

    // Use `mod{dependency type}` even on 26.1+ - loom-back-compat converts them
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
}

loom {
    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json") // Useful for interface injection

    decompilerOptions.named("vineflower") {
        options.put("mark-corresponding-synthetics", "1") // Adds names to lambdas - useful for mixins
    }

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run") // Shares the run directory between versions
        jvmArguments.add("-Dmixin.debug.export=true") // Exports transformed classes for debugging
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, value: String) {
            inputs.property(key, value)
            set(key, value)
        }

        val props = buildMap {
            register("id", sc.properties["mod.id"])
            register("name", sc.properties["mod.name"])
            register("version", sc.properties["mod.version"])
            register("description", sc.properties.getOrNull<String>("mod.description") ?: "")
            register("author", sc.properties.getOrNull<String>("mod.author") ?: "MrSun")
            register("minecraft", sc.properties["mod.mc_compat"])
            register("loader", Regex("\\d+\\.\\d+").find(sc.properties.get<String>("deps.fabric_loader"))!!.value)
            val fapiModId = if (sc.current.parsed < "1.18") "fabric" else "fabric-api"
            register("fapi_id", fapiModId)
        }

        filesMatching("fabric.mod.json") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        val isCopperAge = sc.current.parsed >= "1.21.11"
        val copperMixinsStr = if (isCopperAge) "\"TransportItemsBetweenContainersMixin\"" else ""
        filesMatching("*.mixins.json") {
            expand(
                "java" to mixinJava,
                "copper_mixins" to copperMixinsStr
            )
        }

        exclude("META-INF/neoforge.mods.toml")

        val modIdStr = sc.properties["mod.id"] as String
        if (sc.current.parsed < "1.17") {
            // [2026-11-08 13:35] [CẬP NHẬT]: Minecraft 1.16.5 chưa có Copper.
            // Loại bỏ triệt để mọi tài nguyên, blockstates, models, textures liên quan đến Copper khỏi JAR 1.16.5.
            exclude("data/c/tags/items/copper_ingots.json")
            exclude("assets/$modIdStr/blockstates/*copper*.json")
            exclude("assets/$modIdStr/models/block/*copper*.json")
            exclude("assets/$modIdStr/models/item/*copper*.json")
            exclude("assets/$modIdStr/items/*copper*.json")
            exclude("assets/$modIdStr/textures/**/copper*")
            exclude("assets/$modIdStr/textures/**/trapped_copper*")
            exclude("assets/$modIdStr/textures/**/wood_copper*")
        }

        if (sc.current.parsed < "1.21.11") {
            // [2026-11-08 20:45] [TỐI ƯU]: Items Model Definition (assets/<modid>/items/) dành riêng cho Minecraft >= 1.21.11 (1.21.11, 26.2, 26.3).
            // Loại bỏ thư mục này khỏi các bản cũ (< 1.21.11) để JAR nhẹ và đúng chuẩn từng phiên bản.
            exclude("assets/$modIdStr/items/**")
        }

        doLast {
            val modId = sc.properties["mod.id"] as String
            // [2026-11-08 19:30] [SỬA LỖI CÔNG THỨC 1.21.1+]: Minecraft 1.21 chuyển thư mục công thức từ 'recipes' sang 'recipe' (số ít).
            // Trước đó logic kiểm tra >= 1.21.2 khiến 1.21 và 1.21.1 bị gán nhầm vào 'recipes', làm Minecraft 1.21.1 bỏ qua toàn bộ công thức!
            // Giải pháp: >= 1.21 dùng thư mục chính 'recipe', đồng thời copy đồng thời sang cả 'recipes' để đảm bảo tương thích 100%.
            val isModernDataDir = sc.current.parsed >= "1.21"
            val recipeDirName = if (isModernDataDir) "recipe" else "recipes"
            val recipeDir = destinationDir.resolve("data/$modId/$recipeDirName")
            recipeDir.mkdirs()
            val altRecipeDir = destinationDir.resolve("data/$modId/${if (isModernDataDir) "recipes" else "recipe"}")
            altRecipeDir.mkdirs()
            val isCompactRecipe = sc.current.parsed >= "1.21.2"

            val isModernLoot = sc.current.parsed >= "1.21"
            val lootDirName = if (isModernLoot) "loot_table" else "loot_tables"
            val lootDir = destinationDir.resolve("data/$modId/$lootDirName/blocks")
            lootDir.mkdirs()

            // 1. Loot tables for chests (Copper chỉ xuất hiện từ 1.17+)
            val isCopperAvailable = sc.current.parsed >= "1.17"
            val chestNames = buildList {
                add("dirt_chest")
                add("trapped_dirt_chest")
                if (isCopperAvailable) {
                    add("copper_chest")
                    add("trapped_copper_chest")
                }
                add("iron_chest")
                add("trapped_iron_chest")
                add("gold_chest")
                add("trapped_gold_chest")
                add("diamond_chest")
                add("trapped_diamond_chest")
                add("crystal_chest")
                add("trapped_crystal_chest")
                add("obsidian_chest")
                add("trapped_obsidian_chest")
            }

            for (chestName in chestNames) {
                val lootJson = """
                {
                  "type": "minecraft:block",
                  "pools": [
                    {
                      "bonus_rolls": 0.0,
                      "conditions": [
                        {
                          "condition": "minecraft:survives_explosion"
                        }
                      ],
                      "entries": [
                        {
                          "type": "minecraft:item",
                          "functions": [
                            {
                              "function": "minecraft:copy_name",
                              "source": "block_entity"
                            }
                          ],
                          "name": "$modId:$chestName"
                        }
                      ],
                      "rolls": 1.0
                    }
                  ]
                }
                """.trimIndent()
                lootDir.resolve("$chestName.json").writeText(lootJson)
            }

            // 2. Chest item models for versions < 1.21.4 (must use "parent": "builtin/entity" and display transforms)
            if (sc.current.parsed < "1.21.4") {
                val itemModelsDir = destinationDir.resolve("assets/$modId/models/item")
                itemModelsDir.mkdirs()
                val builtinEntityModelJson = """
                {
                  "parent": "builtin/entity",
                  "display": {
                    "gui": {
                      "rotation": [ 30, 45, 0 ],
                      "translation": [ 0, 0, 0 ],
                      "scale": [ 0.625, 0.625, 0.625 ]
                    },
                    "ground": {
                      "rotation": [ 0, 0, 0 ],
                      "translation": [ 0, 3, 0 ],
                      "scale": [ 0.25, 0.25, 0.25 ]
                    },
                    "head": {
                      "rotation": [ 0, 180, 0 ],
                      "translation": [ 0, 0, 0 ],
                      "scale": [ 1, 1, 1 ]
                    },
                    "fixed": {
                      "rotation": [ 0, 180, 0 ],
                      "translation": [ 0, 0, 0 ],
                      "scale": [ 0.5, 0.5, 0.5 ]
                    },
                    "thirdperson_righthand": {
                      "rotation": [ 75, 315, 0 ],
                      "translation": [ 0, 2.5, 0 ],
                      "scale": [ 0.375, 0.375, 0.375 ]
                    },
                    "firstperson_righthand": {
                      "rotation": [ 0, 315, 0 ],
                      "translation": [ 0, 0, 0 ],
                      "scale": [ 0.4, 0.4, 0.4 ]
                    }
                  }
                }
                """.trimIndent()

                for (chestName in chestNames) {
                    itemModelsDir.resolve("$chestName.json").writeText(builtinEntityModelJson)
                }
            }

            val copperItem = if (sc.current.parsed < "1.17") "#c:copper_ingots" else "minecraft:copper_ingot"

            // Helper to generate shaped recipe
            fun writeShapedRecipe(name: String, pattern: List<String>, keys: Map<String, String>, resultItem: String) {
                val patternJson = pattern.joinToString(",\n") { "    \"$it\"" }
                val keysJson = keys.entries.joinToString(",\n") { (k, v) ->
                    val isTag = v.startsWith("#")
                    val id = if (isTag) v.substring(1) else v
                    val tagOrItem = if (isTag) "tag" else "item"
                    if (isCompactRecipe) {
                        "    \"$k\": \"$v\""
                    } else {
                        "    \"$k\": { \"$tagOrItem\": \"$id\" }"
                    }
                }
                val resultJson = if (sc.current.parsed >= "1.20.5") {
                    """{ "id": "$modId:$resultItem", "count": 1 }"""
                } else {
                    """{ "item": "$modId:$resultItem", "count": 1 }"""
                }

                val json = """
                {
                  "type": "minecraft:crafting_shaped",
                  "category": "misc",
                  "pattern": [
                $patternJson
                  ],
                  "key": {
                $keysJson
                  },
                  "result": $resultJson
                }
                """.trimIndent()
                recipeDir.resolve("$name.json").writeText(json)
            }

            // Helper to generate shapeless recipe
            fun writeShapelessRecipe(name: String, ingredients: List<String>, resultItem: String) {
                val ingJson = ingredients.joinToString(",\n") { ing ->
                    val isTag = ing.startsWith("#")
                    val id = if (isTag) ing.substring(1) else ing
                    val tagOrItem = if (isTag) "tag" else "item"
                    if (isCompactRecipe) {
                        "    \"$ing\""
                    } else {
                        "    { \"$tagOrItem\": \"$id\" }"
                    }
                }
                val resultJson = if (sc.current.parsed >= "1.20.5") {
                    """{ "id": "$modId:$resultItem", "count": 1 }"""
                } else {
                    """{ "item": "$modId:$resultItem", "count": 1 }"""
                }

                val json = """
                {
                  "type": "minecraft:crafting_shapeless",
                  "category": "misc",
                  "ingredients": [
                $ingJson
                  ],
                  "result": $resultJson
                }
                """.trimIndent()
                recipeDir.resolve("$name.json").writeText(json)
            }

            // Chest recipes
            writeShapedRecipe("vanilla_dirt_chest", listOf("DDD", "DCD", "DDD"), mapOf("D" to "minecraft:dirt", "C" to "minecraft:chest"), "dirt_chest")
            if (isCopperAvailable) {
                writeShapedRecipe("vanilla_copper_chest", listOf("MMM", "MCM", "MMM"), mapOf("M" to copperItem, "C" to "minecraft:chest"), "copper_chest")
                writeShapedRecipe("copper_iron_chest", listOf("MGM", "GSG", "MGM"), mapOf("M" to "minecraft:iron_ingot", "G" to "minecraft:glass", "S" to "$modId:copper_chest"), "iron_chest")
            }
            writeShapedRecipe("vanilla_iron_chest", listOf("MMM", "MCM", "MMM"), mapOf("M" to "minecraft:iron_ingot", "C" to "minecraft:chest"), "iron_chest")
            writeShapedRecipe("iron_gold_chest", listOf("MMM", "MSM", "MMM"), mapOf("M" to "minecraft:gold_ingot", "S" to "$modId:iron_chest"), "gold_chest")
            writeShapedRecipe("gold_diamond_chest", listOf("GGG", "MSM", "GGG"), mapOf("G" to "minecraft:glass", "M" to "minecraft:diamond", "S" to "$modId:gold_chest"), "diamond_chest")
            writeShapedRecipe("diamond_crystal_chest", listOf("GGG", "GSG", "GGG"), mapOf("G" to "minecraft:glass", "S" to "$modId:diamond_chest"), "crystal_chest")
            writeShapedRecipe("diamond_obsidian_chest", listOf("MMM", "MSM", "MMM"), mapOf("M" to "minecraft:obsidian", "S" to "$modId:diamond_chest"), "obsidian_chest")

            // Trapped chest recipes (shapeless: chest + tripwire hook)
            val baseChests = buildList {
                add("dirt")
                if (isCopperAvailable) add("copper")
                add("iron")
                add("gold")
                add("diamond")
                add("crystal")
                add("obsidian")
            }
            for (type in baseChests) {
                writeShapelessRecipe("trapped_${type}_chest", listOf("$modId:${type}_chest", "minecraft:tripwire_hook"), "trapped_${type}_chest")
            }

            // Upgrade recipes
            if (isCopperAvailable) {
                writeShapedRecipe("wood_to_copper_chest_upgrade", listOf("MMM", "MPM", "MMM"), mapOf("M" to copperItem, "P" to "#minecraft:planks"), "wood_to_copper_chest_upgrade")
                writeShapedRecipe("copper_to_iron_chest_upgrade", listOf("IGI", "GCG", "IGI"), mapOf("I" to "minecraft:iron_ingot", "C" to copperItem, "G" to "minecraft:glass"), "copper_to_iron_chest_upgrade")
            }
            writeShapedRecipe("wood_to_iron_chest_upgrade", listOf("MMM", "MPM", "MMM"), mapOf("M" to "minecraft:iron_ingot", "P" to "#minecraft:planks"), "wood_to_iron_chest_upgrade")
            writeShapedRecipe("iron_to_gold_chest_upgrade", listOf("GGG", "GIG", "GGG"), mapOf("G" to "minecraft:gold_ingot", "I" to "minecraft:iron_ingot"), "iron_to_gold_chest_upgrade")
            writeShapedRecipe("gold_to_diamond_chest_upgrade", listOf("GGG", "MSM", "GGG"), mapOf("G" to "minecraft:glass", "M" to "minecraft:diamond", "S" to "minecraft:gold_ingot"), "gold_to_diamond_chest_upgrade")
            writeShapedRecipe("diamond_to_crystal_chest_upgrade", listOf("GGG", "GMG", "GGG"), mapOf("G" to "minecraft:glass", "M" to "minecraft:obsidian"), "diamond_to_crystal_chest_upgrade")
            writeShapedRecipe("diamond_to_obsidian_chest_upgrade", listOf("MMM", "MGM", "MMM"), mapOf("M" to "minecraft:obsidian", "G" to "minecraft:glass"), "diamond_to_obsidian_chest_upgrade")

            // Đồng bộ toàn bộ recipe sang thư mục song song (đảm bảo cả data/.../recipe/ và data/.../recipes/ đều hiện diện 100% trong mọi JAR)
            recipeDir.listFiles()?.forEach { file ->
                if (file.isFile && file.name.endsWith(".json")) {
                    altRecipeDir.resolve(file.name).writeText(file.readText())
                }
            }

            // 3. Multi-version Block & Item Tags generation (both plural < 1.21 and singular >= 1.21)
            fun writeTag(dir: java.io.File, relativePath: String, values: List<String>) {
                val file = dir.resolve(relativePath)
                file.parentFile.mkdirs()
                val valuesJson = values.joinToString(",\n") { "    \"$it\"" }
                val json = """
                {
                  "values": [
                $valuesJson
                  ]
                }
                """.trimIndent()
                file.writeText(json)
            }

            val allChests = chestNames.map { "$modId:$it" }
            val nonDirtChests = chestNames.filter { !it.contains("dirt") }.map { "$modId:$it" }
            val dirtChests = chestNames.filter { it.contains("dirt") }.map { "$modId:$it" }
            val baseChestsOnly = buildList {
                if (isCopperAvailable) add("$modId:copper_chest")
                add("$modId:iron_chest")
                add("$modId:gold_chest")
                add("$modId:diamond_chest")
                add("$modId:crystal_chest")
                add("$modId:obsidian_chest")
            }

            // minecraft:mineable/pickaxe
            writeTag(destinationDir.resolve("data/minecraft/tags/blocks"), "mineable/pickaxe.json", nonDirtChests)
            writeTag(destinationDir.resolve("data/minecraft/tags/block"), "mineable/pickaxe.json", nonDirtChests)

            // minecraft:mineable/shovel
            writeTag(destinationDir.resolve("data/minecraft/tags/blocks"), "mineable/shovel.json", dirtChests)
            writeTag(destinationDir.resolve("data/minecraft/tags/block"), "mineable/shovel.json", dirtChests)

            // minecraft:guarded_by_piglins
            writeTag(destinationDir.resolve("data/minecraft/tags/blocks"), "guarded_by_piglins.json", nonDirtChests)
            writeTag(destinationDir.resolve("data/minecraft/tags/block"), "guarded_by_piglins.json", nonDirtChests)

            // minecraft:features_cannot_replace
            writeTag(destinationDir.resolve("data/minecraft/tags/blocks"), "features_cannot_replace.json", baseChestsOnly)
            writeTag(destinationDir.resolve("data/minecraft/tags/block"), "features_cannot_replace.json", baseChestsOnly)

            // [2026-11-09 11:00] Tự động tạo tag minecraft:copper_chests cho cả block và item (áp dụng cho Minecraft >= 1.21.11 có Copper Golem)
            if (isCopperAvailable) {
                val copperChestList = listOf("$modId:copper_chest", "$modId:trapped_copper_chest")
                writeTag(destinationDir.resolve("data/minecraft/tags/blocks"), "copper_chests.json", copperChestList)
                writeTag(destinationDir.resolve("data/minecraft/tags/block"), "copper_chests.json", copperChestList)
                writeTag(destinationDir.resolve("data/minecraft/tags/items"), "copper_chests.json", copperChestList)
                writeTag(destinationDir.resolve("data/minecraft/tags/item"), "copper_chests.json", copperChestList)
            }

            // c:chests
            writeTag(destinationDir.resolve("data/c/tags/blocks"), "chests.json", allChests)
            writeTag(destinationDir.resolve("data/c/tags/block"), "chests.json", allChests)
            writeTag(destinationDir.resolve("data/c/tags/items"), "chests.json", allChests)
            writeTag(destinationDir.resolve("data/c/tags/item"), "chests.json", allChests)
        }
    }

    // Includes the license file in the built mod
    withType<Jar> {
        val name = project.property("mod.id")
        inputs.property("mod_id", name)
        from("../../LICENSE") { rename { "$it-$name" } }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", project.property("mod.version"))
        // loomx.mod(Sources)Jar returns the jar task for the applied loom variant
        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
    }
}
