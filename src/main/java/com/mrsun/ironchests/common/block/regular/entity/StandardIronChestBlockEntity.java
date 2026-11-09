package com.mrsun.ironchests.common.block.regular.entity;

import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.inventory.IronChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class StandardIronChestBlockEntity extends AbstractIronChestBlockEntity {

  //? if >= 1.17 {
  public StandardIronChestBlockEntity(BlockPos pos, BlockState state, IronChestsTypes type) {
    super(IronChestsBlockEntityTypes.getTypeFor(type), pos, state, type);
  }
  //?} else {
  /*public StandardIronChestBlockEntity(IronChestsTypes type) {
    super(IronChestsBlockEntityTypes.getTypeFor(type), type);
  }
  *///?}

  @Override
  protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
    return IronChestMenu.createMenu(this.getChestType(), containerId, playerInventory, this);
  }
}
