package com.mrsun.ironchests.common.inventory;

import com.mrsun.ironchests.common.block.IronChestsTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class IronChestMenu extends AbstractContainerMenu {

  private final Container container;
  private final IronChestsTypes chestType;

  public static IronChestMenu createCopperContainer(int containerId, Inventory playerInventory) {
    return createMenu(IronChestsTypes.COPPER, containerId, playerInventory, new SimpleContainer(IronChestsTypes.COPPER.size));
  }

  public static IronChestMenu createCopperContainer(int containerId, Inventory playerInventory, Container container) {
    return createMenu(IronChestsTypes.COPPER, containerId, playerInventory, container);
  }

  public static IronChestMenu createIronContainer(int containerId, Inventory playerInventory) {
    return createMenu(IronChestsTypes.IRON, containerId, playerInventory, new SimpleContainer(IronChestsTypes.IRON.size));
  }

  public static IronChestMenu createIronContainer(int containerId, Inventory playerInventory, Container container) {
    return createMenu(IronChestsTypes.IRON, containerId, playerInventory, container);
  }

  public static IronChestMenu createGoldContainer(int containerId, Inventory playerInventory) {
    return createMenu(IronChestsTypes.GOLD, containerId, playerInventory, new SimpleContainer(IronChestsTypes.GOLD.size));
  }

  public static IronChestMenu createGoldContainer(int containerId, Inventory playerInventory, Container container) {
    return createMenu(IronChestsTypes.GOLD, containerId, playerInventory, container);
  }

  public static IronChestMenu createDiamondContainer(int containerId, Inventory playerInventory) {
    return createMenu(IronChestsTypes.DIAMOND, containerId, playerInventory, new SimpleContainer(IronChestsTypes.DIAMOND.size));
  }

  public static IronChestMenu createDiamondContainer(int containerId, Inventory playerInventory, Container container) {
    return createMenu(IronChestsTypes.DIAMOND, containerId, playerInventory, container);
  }

  public static IronChestMenu createCrystalContainer(int containerId, Inventory playerInventory) {
    return createMenu(IronChestsTypes.CRYSTAL, containerId, playerInventory, new SimpleContainer(IronChestsTypes.CRYSTAL.size));
  }

  public static IronChestMenu createCrystalContainer(int containerId, Inventory playerInventory, Container container) {
    return createMenu(IronChestsTypes.CRYSTAL, containerId, playerInventory, container);
  }

  public static IronChestMenu createObsidianContainer(int containerId, Inventory playerInventory) {
    return createMenu(IronChestsTypes.OBSIDIAN, containerId, playerInventory, new SimpleContainer(IronChestsTypes.OBSIDIAN.size));
  }

  public static IronChestMenu createObsidianContainer(int containerId, Inventory playerInventory, Container container) {
    return createMenu(IronChestsTypes.OBSIDIAN, containerId, playerInventory, container);
  }

  public static IronChestMenu createDirtContainer(int containerId, Inventory playerInventory) {
    return createMenu(IronChestsTypes.DIRT, containerId, playerInventory, new SimpleContainer(IronChestsTypes.DIRT.size));
  }

  public static IronChestMenu createDirtContainer(int containerId, Inventory playerInventory, Container container) {
    return createMenu(IronChestsTypes.DIRT, containerId, playerInventory, container);
  }

  public static IronChestMenu createMenu(IronChestsTypes type, int containerId, Inventory playerInventory, Container container) {
    return new IronChestMenu(IronChestsMenuTypes.getTypeFor(type), containerId, playerInventory, container, type);
  }

  public IronChestMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, Container container, IronChestsTypes chestType) {
    super(menuType, containerId);
    checkContainerSize(container, chestType.size);
    this.container = container;
    this.chestType = chestType;

    container.startOpen(playerInventory.player);

    if (chestType == IronChestsTypes.DIRT) {
      this.addSlot(new DirtChestSlot(container, 0, 12 + 4 * 18, 8 + 2 * 18));
    } else {
      for (int chestRow = 0; chestRow < chestType.getRowCount(); chestRow++) {
        for (int chestCol = 0; chestCol < chestType.rowLength; chestCol++) {
          this.addSlot(new Slot(container, chestCol + chestRow * chestType.rowLength, 12 + chestCol * 18, 18 + chestRow * 18));
        }
      }
    }

    int leftCol = (chestType.xSize - 162) / 2 + 1;

    for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
      for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
        this.addSlot(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, chestType.ySize - (4 - playerInvRow) * 18 - 10));
      }
    }

    for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
      this.addSlot(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, chestType.ySize - 24));
    }
  }

  public IronChestsTypes getChestType() {
    return this.chestType;
  }

  public Container getContainer() {
    return this.container;
  }

  @Override
  public boolean stillValid(Player player) {
    return this.container.stillValid(player);
  }

  @Override
  public ItemStack quickMoveStack(Player player, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.slots.get(index);

    if (slot != null && slot.hasItem()) {
      ItemStack itemstack1 = slot.getItem();
      itemstack = itemstack1.copy();

      if (index < this.chestType.size) {
        if (!this.moveItemStackTo(itemstack1, this.chestType.size, this.slots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.moveItemStackTo(itemstack1, 0, this.chestType.size, false)) {
        return ItemStack.EMPTY;
      }

      if (itemstack1.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }
    }

    return itemstack;
  }

  @Override
  public void removed(Player player) {
    super.removed(player);
    this.container.stopOpen(player);
  }
}
