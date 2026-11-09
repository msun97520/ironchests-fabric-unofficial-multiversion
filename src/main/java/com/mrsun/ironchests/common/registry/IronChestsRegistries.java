package com.mrsun.ironchests.common.registry;

import com.mrsun.ironchests.IronChestsFabric;
import com.mrsun.ironchests.common.inventory.IronChestsMenuTypes;
import net.minecraft.core.Registry;
//? if >= 1.19.3 {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
//?}
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class IronChestsRegistries {

  public static <T extends Block> T registerBlock(String name, T block) {
    //? if >= 1.19.3 {
    return Registry.register(BuiltInRegistries.BLOCK, IronChestsFabric.id(name), block);
    //?} else {
    /*return Registry.register(Registry.BLOCK, IronChestsFabric.id(name), block);*///?}
  }

  public static <T extends Item> T registerItem(String name, T item) {
    //? if >= 1.19.3 {
    return Registry.register(BuiltInRegistries.ITEM, IronChestsFabric.id(name), item);
    //?} else {
    /*return Registry.register(Registry.ITEM, IronChestsFabric.id(name), item);*///?}
  }

  public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> type) {
    //? if >= 1.19.3 {
    return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronChestsFabric.id(name), type);
    //?} else {
    /*return Registry.register(Registry.BLOCK_ENTITY_TYPE, IronChestsFabric.id(name), type);*///?}
  }

  public static <T extends AbstractContainerMenu> MenuType<T> registerMenu(String name, IronChestsMenuTypes.MenuFactory<T> factory) {
    //? if >= 1.19.3 {
    return Registry.register(BuiltInRegistries.MENU, IronChestsFabric.id(name), new MenuType<>(factory::create, FeatureFlags.VANILLA_SET));
    //?} else if >= 1.18 {
    /*return Registry.register(Registry.MENU, IronChestsFabric.id(name), new MenuType<>(factory::create));
    *///?} else {
    /*return net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.registerSimple(IronChestsFabric.id(name), factory::create);
    *///?}
  }
}
