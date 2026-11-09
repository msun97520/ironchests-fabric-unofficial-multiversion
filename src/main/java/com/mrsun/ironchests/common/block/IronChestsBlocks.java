package com.mrsun.ironchests.common.block;

import com.mrsun.ironchests.common.block.regular.AbstractIronChestBlock;
import com.mrsun.ironchests.common.block.regular.GenericIronChestBlock;
import com.mrsun.ironchests.common.block.regular.entity.IronChestsBlockEntityTypes;
import com.mrsun.ironchests.common.registry.IronChestsRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
//? if >= 1.21.11 {
/*import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import com.mrsun.ironchests.IronChestsFabric;
*///?}
//? if >= 1.20 {
import net.minecraft.world.level.material.MapColor;
//?} else {
/*import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.material.Material;*///?}

public class IronChestsBlocks {

  private static BlockBehaviour.Properties metalProps(String name, float strength, float blastRes) {
    //? if >= 1.21.11 {
    /*return BlockBehaviour.Properties.of()
        .setId(ResourceKey.create(Registries.BLOCK, IronChestsFabric.id(name)))
        .mapColor(MapColor.METAL)
        .strength(strength, blastRes);
    *///?} elif >= 1.20 {
    return BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(strength, blastRes);
    //?} else {
    /*return FabricBlockSettings.of(Material.METAL).strength(strength, blastRes);*///?}
  }

  private static BlockBehaviour.Properties glassProps(String name, float strength, float blastRes) {
    //? if >= 1.21.11 {
    /*return BlockBehaviour.Properties.of()
        .setId(ResourceKey.create(Registries.BLOCK, IronChestsFabric.id(name)))
        .mapColor(MapColor.NONE)
        .strength(strength, blastRes)
        .noOcclusion();
    *///?} elif >= 1.20 {
    return BlockBehaviour.Properties.of().mapColor(MapColor.NONE).strength(strength, blastRes).noOcclusion();
    //?} else {
    /*return FabricBlockSettings.of(Material.GLASS).strength(strength, blastRes).noOcclusion();*///?}
  }

  private static BlockBehaviour.Properties dirtProps(String name, float strength, float blastRes) {
    //? if >= 1.21.11 {
    /*return BlockBehaviour.Properties.of()
        .setId(ResourceKey.create(Registries.BLOCK, IronChestsFabric.id(name)))
        .mapColor(MapColor.DIRT)
        .strength(strength, blastRes);
    *///?} elif >= 1.20 {
    return BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(strength, blastRes);
    //?} else {
    /*return FabricBlockSettings.of(Material.DIRT).strength(strength, blastRes);*///?}
  }

  // Regular Chests
  //? if >= 1.17 {
  public static final Block COPPER_CHEST = IronChestsRegistries.registerBlock(
    "copper_chest", new GenericIronChestBlock(metalProps("copper_chest", 3.0F, 6.0F), IronChestsTypes.COPPER, false, () -> IronChestsBlockEntityTypes.COPPER_CHEST)
  );
  //?}
  public static final Block IRON_CHEST = IronChestsRegistries.registerBlock(
    "iron_chest", new GenericIronChestBlock(metalProps("iron_chest", 3.0F, 6.0F), IronChestsTypes.IRON, false, () -> IronChestsBlockEntityTypes.IRON_CHEST)
  );
  public static final Block GOLD_CHEST = IronChestsRegistries.registerBlock(
    "gold_chest", new GenericIronChestBlock(metalProps("gold_chest", 3.0F, 6.0F), IronChestsTypes.GOLD, false, () -> IronChestsBlockEntityTypes.GOLD_CHEST)
  );
  public static final Block DIAMOND_CHEST = IronChestsRegistries.registerBlock(
    "diamond_chest", new GenericIronChestBlock(metalProps("diamond_chest", 3.0F, 6.0F), IronChestsTypes.DIAMOND, false, () -> IronChestsBlockEntityTypes.DIAMOND_CHEST)
  );
  public static final Block CRYSTAL_CHEST = IronChestsRegistries.registerBlock(
    "crystal_chest", new GenericIronChestBlock(glassProps("crystal_chest", 3.0F, 6.0F), IronChestsTypes.CRYSTAL, false, () -> IronChestsBlockEntityTypes.CRYSTAL_CHEST)
  );
  public static final Block OBSIDIAN_CHEST = IronChestsRegistries.registerBlock(
    "obsidian_chest", new GenericIronChestBlock(metalProps("obsidian_chest", 50.0F, 1200.0F), IronChestsTypes.OBSIDIAN, false, () -> IronChestsBlockEntityTypes.OBSIDIAN_CHEST)
  );
  public static final Block DIRT_CHEST = IronChestsRegistries.registerBlock(
    "dirt_chest", new GenericIronChestBlock(dirtProps("dirt_chest", 2.0F, 2.0F), IronChestsTypes.DIRT, false, () -> IronChestsBlockEntityTypes.DIRT_CHEST)
  );

  // Trapped Chests
  //? if >= 1.17 {
  public static final Block TRAPPED_COPPER_CHEST = IronChestsRegistries.registerBlock(
    "trapped_copper_chest", new GenericIronChestBlock(metalProps("trapped_copper_chest", 3.0F, 6.0F), IronChestsTypes.COPPER, true, () -> IronChestsBlockEntityTypes.COPPER_CHEST)
  );
  //?}
  public static final Block TRAPPED_IRON_CHEST = IronChestsRegistries.registerBlock(
    "trapped_iron_chest", new GenericIronChestBlock(metalProps("trapped_iron_chest", 3.0F, 6.0F), IronChestsTypes.IRON, true, () -> IronChestsBlockEntityTypes.IRON_CHEST)
  );
  public static final Block TRAPPED_GOLD_CHEST = IronChestsRegistries.registerBlock(
    "trapped_gold_chest", new GenericIronChestBlock(metalProps("trapped_gold_chest", 3.0F, 6.0F), IronChestsTypes.GOLD, true, () -> IronChestsBlockEntityTypes.GOLD_CHEST)
  );
  public static final Block TRAPPED_DIAMOND_CHEST = IronChestsRegistries.registerBlock(
    "trapped_diamond_chest", new GenericIronChestBlock(metalProps("trapped_diamond_chest", 3.0F, 6.0F), IronChestsTypes.DIAMOND, true, () -> IronChestsBlockEntityTypes.DIAMOND_CHEST)
  );
  public static final Block TRAPPED_CRYSTAL_CHEST = IronChestsRegistries.registerBlock(
    "trapped_crystal_chest", new GenericIronChestBlock(glassProps("trapped_crystal_chest", 3.0F, 6.0F), IronChestsTypes.CRYSTAL, true, () -> IronChestsBlockEntityTypes.CRYSTAL_CHEST)
  );
  public static final Block TRAPPED_OBSIDIAN_CHEST = IronChestsRegistries.registerBlock(
    "trapped_obsidian_chest", new GenericIronChestBlock(metalProps("trapped_obsidian_chest", 50.0F, 1200.0F), IronChestsTypes.OBSIDIAN, true, () -> IronChestsBlockEntityTypes.OBSIDIAN_CHEST)
  );
  public static final Block TRAPPED_DIRT_CHEST = IronChestsRegistries.registerBlock(
    "trapped_dirt_chest", new GenericIronChestBlock(dirtProps("trapped_dirt_chest", 2.0F, 2.0F), IronChestsTypes.DIRT, true, () -> IronChestsBlockEntityTypes.DIRT_CHEST)
  );

  public static void init() {
    // Triggers class loading and registration
  }
}

