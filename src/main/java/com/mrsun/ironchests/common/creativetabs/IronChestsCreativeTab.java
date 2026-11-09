package com.mrsun.ironchests.common.creativetabs;

import com.mrsun.ironchests.IronChestsFabric;
import com.mrsun.ironchests.common.item.IronChestsItems;
import com.mrsun.ironchests.common.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

//? if >= 26.1 {
/*import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
*///?} else if >= 1.20 {
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;//?}

//? if (>= 1.19.3 && < 1.20) {
/*import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
*///?}

//? if < 1.19.3 {
/*import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;*///?}

public class IronChestsCreativeTab {

  //? if < 1.19.3 {
  public static final CreativeModeTab TAB = FabricItemGroupBuilder.build(
    IronChestsFabric.id("main"),
    () -> new ItemStack(IronChestsItems.IRON_CHEST)
  );
  *///?}

  //? if >= 1.20 {
  public static final ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(
    BuiltInRegistries.CREATIVE_MODE_TAB.key(),
    IronChestsFabric.id("main")
  );
  //?}

  public static void init() {
    //? if >= 26.1 {
    /*CreativeModeTab tabModern = FabricCreativeModeTab.builder()
      .title(Util.translatable("itemGroup." + IronChestsFabric.MOD_ID))
      .icon(() -> new ItemStack(IronChestsItems.IRON_CHEST))
      .displayItems((params, output) -> fillItems(output))
      .build();
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, tabModern);
    *///?} else if >= 1.20 {
    CreativeModeTab tabModern = FabricItemGroup.builder()
      .title(Util.translatable("itemGroup." + IronChestsFabric.MOD_ID))
      .icon(() -> new ItemStack(IronChestsItems.IRON_CHEST))
      .displayItems((params, output) -> fillItems(output))
      .build();
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, tabModern);
    //?} else if >= 1.19.3 {
    /*FabricItemGroup.builder(IronChestsFabric.id("main"))
      .title(Util.translatable("itemGroup." + IronChestsFabric.MOD_ID))
      .icon(() -> new ItemStack(IronChestsItems.IRON_CHEST))
      .displayItems((params, output) -> fillItems(output))
      .build();
    *///?}
  }

  //? if >= 1.19.3 {
  private static void fillItems(CreativeModeTab.Output output) {
    output.accept(IronChestsItems.COPPER_CHEST);
    output.accept(IronChestsItems.IRON_CHEST);
    output.accept(IronChestsItems.GOLD_CHEST);
    output.accept(IronChestsItems.DIAMOND_CHEST);
    output.accept(IronChestsItems.CRYSTAL_CHEST);
    output.accept(IronChestsItems.OBSIDIAN_CHEST);
    output.accept(IronChestsItems.DIRT_CHEST);

    output.accept(IronChestsItems.TRAPPED_COPPER_CHEST);
    output.accept(IronChestsItems.TRAPPED_IRON_CHEST);
    output.accept(IronChestsItems.TRAPPED_GOLD_CHEST);
    output.accept(IronChestsItems.TRAPPED_DIAMOND_CHEST);
    output.accept(IronChestsItems.TRAPPED_CRYSTAL_CHEST);
    output.accept(IronChestsItems.TRAPPED_OBSIDIAN_CHEST);
    output.accept(IronChestsItems.TRAPPED_DIRT_CHEST);

    output.accept(IronChestsItems.WOOD_TO_COPPER_CHEST_UPGRADE);
    output.accept(IronChestsItems.WOOD_TO_IRON_CHEST_UPGRADE);
    output.accept(IronChestsItems.COPPER_TO_IRON_CHEST_UPGRADE);
    output.accept(IronChestsItems.IRON_TO_GOLD_CHEST_UPGRADE);
    output.accept(IronChestsItems.GOLD_TO_DIAMOND_CHEST_UPGRADE);
    output.accept(IronChestsItems.DIAMOND_TO_CRYSTAL_CHEST_UPGRADE);
    output.accept(IronChestsItems.DIAMOND_TO_OBSIDIAN_CHEST_UPGRADE);
  }
  //?}
}
