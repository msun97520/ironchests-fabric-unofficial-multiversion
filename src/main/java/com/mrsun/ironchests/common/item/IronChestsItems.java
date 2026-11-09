package com.mrsun.ironchests.common.item;

import com.mrsun.ironchests.common.block.IronChestsBlocks;
import com.mrsun.ironchests.common.creativetabs.IronChestsCreativeTab;
import com.mrsun.ironchests.common.registry.IronChestsRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
//? if >= 1.21.11 {
/*import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import com.mrsun.ironchests.IronChestsFabric;
*///?}

public class IronChestsItems {

  private static Item.Properties props(String name) {
    //? if >= 1.21.11 {
    /*return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, IronChestsFabric.id(name)));
    *///?} elif < 1.19.3 {
    /*return new Item.Properties().tab(IronChestsCreativeTab.TAB);*///?} else {
    return new Item.Properties();
    //?}
  }

  private static Item.Properties blockProps(String name) {
    //? if >= 1.21.11 {
    /*return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, IronChestsFabric.id(name))).useBlockDescriptionPrefix();
    *///?} elif < 1.19.3 {
    /*return new Item.Properties().tab(IronChestsCreativeTab.TAB);*///?} else {
    return new Item.Properties();
    //?}
  }

  // Chest BlockItems
  //? if >= 1.17 {
  public static final BlockItem COPPER_CHEST = IronChestsRegistries.registerItem(
    "copper_chest", new BlockItem(IronChestsBlocks.COPPER_CHEST, blockProps("copper_chest"))
  );
  //?}
  public static final BlockItem IRON_CHEST = IronChestsRegistries.registerItem(
    "iron_chest", new BlockItem(IronChestsBlocks.IRON_CHEST, blockProps("iron_chest"))
  );
  public static final BlockItem GOLD_CHEST = IronChestsRegistries.registerItem(
    "gold_chest", new BlockItem(IronChestsBlocks.GOLD_CHEST, blockProps("gold_chest"))
  );
  public static final BlockItem DIAMOND_CHEST = IronChestsRegistries.registerItem(
    "diamond_chest", new BlockItem(IronChestsBlocks.DIAMOND_CHEST, blockProps("diamond_chest"))
  );
  public static final BlockItem CRYSTAL_CHEST = IronChestsRegistries.registerItem(
    "crystal_chest", new BlockItem(IronChestsBlocks.CRYSTAL_CHEST, blockProps("crystal_chest"))
  );
  public static final BlockItem OBSIDIAN_CHEST = IronChestsRegistries.registerItem(
    "obsidian_chest", new BlockItem(IronChestsBlocks.OBSIDIAN_CHEST, blockProps("obsidian_chest"))
  );
  public static final BlockItem DIRT_CHEST = IronChestsRegistries.registerItem(
    "dirt_chest", new BlockItem(IronChestsBlocks.DIRT_CHEST, blockProps("dirt_chest"))
  );

  // Trapped Chest BlockItems
  //? if >= 1.17 {
  public static final BlockItem TRAPPED_COPPER_CHEST = IronChestsRegistries.registerItem(
    "trapped_copper_chest", new BlockItem(IronChestsBlocks.TRAPPED_COPPER_CHEST, blockProps("trapped_copper_chest"))
  );
  //?}
  public static final BlockItem TRAPPED_IRON_CHEST = IronChestsRegistries.registerItem(
    "trapped_iron_chest", new BlockItem(IronChestsBlocks.TRAPPED_IRON_CHEST, blockProps("trapped_iron_chest"))
  );
  public static final BlockItem TRAPPED_GOLD_CHEST = IronChestsRegistries.registerItem(
    "trapped_gold_chest", new BlockItem(IronChestsBlocks.TRAPPED_GOLD_CHEST, blockProps("trapped_gold_chest"))
  );
  public static final BlockItem TRAPPED_DIAMOND_CHEST = IronChestsRegistries.registerItem(
    "trapped_diamond_chest", new BlockItem(IronChestsBlocks.TRAPPED_DIAMOND_CHEST, blockProps("trapped_diamond_chest"))
  );
  public static final BlockItem TRAPPED_CRYSTAL_CHEST = IronChestsRegistries.registerItem(
    "trapped_crystal_chest", new BlockItem(IronChestsBlocks.TRAPPED_CRYSTAL_CHEST, blockProps("trapped_crystal_chest"))
  );
  public static final BlockItem TRAPPED_OBSIDIAN_CHEST = IronChestsRegistries.registerItem(
    "trapped_obsidian_chest", new BlockItem(IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST, blockProps("trapped_obsidian_chest"))
  );
  public static final BlockItem TRAPPED_DIRT_CHEST = IronChestsRegistries.registerItem(
    "trapped_dirt_chest", new BlockItem(IronChestsBlocks.TRAPPED_DIRT_CHEST, blockProps("trapped_dirt_chest"))
  );

  // Upgrades
  //? if >= 1.17 {
  public static final ChestUpgradeItem WOOD_TO_COPPER_CHEST_UPGRADE = IronChestsRegistries.registerItem(
    "wood_to_copper_chest_upgrade", new ChestUpgradeItem(IronChestsUpgradeType.WOOD_TO_COPPER, props("wood_to_copper_chest_upgrade").stacksTo(1))
  );
  //?}
  public static final ChestUpgradeItem WOOD_TO_IRON_CHEST_UPGRADE = IronChestsRegistries.registerItem(
    "wood_to_iron_chest_upgrade", new ChestUpgradeItem(IronChestsUpgradeType.WOOD_TO_IRON, props("wood_to_iron_chest_upgrade").stacksTo(1))
  );
  //? if >= 1.17 {
  public static final ChestUpgradeItem COPPER_TO_IRON_CHEST_UPGRADE = IronChestsRegistries.registerItem(
    "copper_to_iron_chest_upgrade", new ChestUpgradeItem(IronChestsUpgradeType.COPPER_TO_IRON, props("copper_to_iron_chest_upgrade").stacksTo(1))
  );
  //?}
  public static final ChestUpgradeItem IRON_TO_GOLD_CHEST_UPGRADE = IronChestsRegistries.registerItem(
    "iron_to_gold_chest_upgrade", new ChestUpgradeItem(IronChestsUpgradeType.IRON_TO_GOLD, props("iron_to_gold_chest_upgrade").stacksTo(1))
  );
  public static final ChestUpgradeItem GOLD_TO_DIAMOND_CHEST_UPGRADE = IronChestsRegistries.registerItem(
    "gold_to_diamond_chest_upgrade", new ChestUpgradeItem(IronChestsUpgradeType.GOLD_TO_DIAMOND, props("gold_to_diamond_chest_upgrade").stacksTo(1))
  );
  public static final ChestUpgradeItem DIAMOND_TO_CRYSTAL_CHEST_UPGRADE = IronChestsRegistries.registerItem(
    "diamond_to_crystal_chest_upgrade", new ChestUpgradeItem(IronChestsUpgradeType.DIAMOND_TO_CRYSTAL, props("diamond_to_crystal_chest_upgrade").stacksTo(1))
  );
  public static final ChestUpgradeItem DIAMOND_TO_OBSIDIAN_CHEST_UPGRADE = IronChestsRegistries.registerItem(
    "diamond_to_obsidian_chest_upgrade", new ChestUpgradeItem(IronChestsUpgradeType.DIAMOND_TO_OBSIDIAN, props("diamond_to_obsidian_chest_upgrade").stacksTo(1))
  );

  public static void init() {
    // Triggers class loading and registration
  }
}

