pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "FabricMC" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    // Check the latest version on https://stonecutter.kikugie.dev/blog/changes/0.9
    id("dev.kikugie.stonecutter") version "0.9.8"

    // Used for cross-compat for 26.1+ and older versions (https://codeberg.org/KikuGie/loom-back-compat)
    id("dev.kikugie.loom-back-compat") version "0.4.2"

    // (https://github.com/gradle/foojay-toolchains)
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        fun match(project: String, vararg loaders: String, version: String = project) {
            for (loader in loaders) version("$project-$loader", version).buildscript("build.$loader.gradle.kts")
        }

        match("1.16.5", "fabric")
        match("1.17.1", "fabric")
        match("1.18.2", "fabric")
        match("1.19.2", "fabric")
        match("1.19.4", "fabric")
        match("1.20.1", "fabric")
        match("1.20.4", "fabric")
        match("1.20.6", "fabric")
        match("1.21.1", "fabric")
        match("1.21.11", "fabric")
        match("26.2", "fabric")
        match("26.3", "fabric")
        vcsVersion = "1.21.1-fabric"
    }
}

rootProject.name = "ironchest-mrsun-unofficial"
