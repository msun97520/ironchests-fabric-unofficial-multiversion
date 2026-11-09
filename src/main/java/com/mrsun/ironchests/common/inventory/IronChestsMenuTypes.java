package com.mrsun.ironchests.common.inventory;

import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.registry.IronChestsRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class IronChestsMenuTypes {

  @FunctionalInterface
  public interface MenuFactory<T extends AbstractContainerMenu> {
    T create(int containerId, Inventory playerInventory);
  }

  //? if >= 1.17 {
  public static final MenuType<IronChestMenu> COPPER_CHEST = IronChestsRegistries.registerMenu(
    "copper_chest", IronChestMenu::createCopperContainer
  );
  //?}

  public static final MenuType<IronChestMenu> IRON_CHEST = IronChestsRegistries.registerMenu(
    "iron_chest", IronChestMenu::createIronContainer
  );

  public static final MenuType<IronChestMenu> GOLD_CHEST = IronChestsRegistries.registerMenu(
    "gold_chest", IronChestMenu::createGoldContainer
  );

  public static final MenuType<IronChestMenu> DIAMOND_CHEST = IronChestsRegistries.registerMenu(
    "diamond_chest", IronChestMenu::createDiamondContainer
  );

  public static final MenuType<IronChestMenu> CRYSTAL_CHEST = IronChestsRegistries.registerMenu(
    "crystal_chest", IronChestMenu::createCrystalContainer
  );

  public static final MenuType<IronChestMenu> OBSIDIAN_CHEST = IronChestsRegistries.registerMenu(
    "obsidian_chest", IronChestMenu::createObsidianContainer
  );

  public static final MenuType<IronChestMenu> DIRT_CHEST = IronChestsRegistries.registerMenu(
    "dirt_chest", IronChestMenu::createDirtContainer
  );

  public static MenuType<IronChestMenu> getTypeFor(IronChestsTypes type) {
    switch (type) {
      //? if >= 1.17 {
      case COPPER: return COPPER_CHEST;
      //?}
      case IRON: return IRON_CHEST;
      case GOLD: return GOLD_CHEST;
      case DIAMOND: return DIAMOND_CHEST;
      case CRYSTAL: return CRYSTAL_CHEST;
      case OBSIDIAN: return OBSIDIAN_CHEST;
      case DIRT: return DIRT_CHEST;
      default: return IRON_CHEST;
    }
  }

  public static void init() {
  }
}
