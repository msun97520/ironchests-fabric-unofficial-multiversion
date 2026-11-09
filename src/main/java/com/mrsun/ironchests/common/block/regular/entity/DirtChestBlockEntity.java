package com.mrsun.ironchests.common.block.regular.entity;

import com.mrsun.ironchests.common.Util;
import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.inventory.IronChestMenu;
import net.minecraft.core.BlockPos;
//? if >= 1.20.5 {
import net.minecraft.core.HolderLookup;
//?}
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DirtChestBlockEntity extends AbstractIronChestBlockEntity {

  private boolean initializedBook = false;

  //? if >= 1.17 {
  public DirtChestBlockEntity(BlockPos pos, BlockState state) {
    super(IronChestsBlockEntityTypes.DIRT_CHEST, pos, state, IronChestsTypes.DIRT);
  }
  //?} else {
  /*public DirtChestBlockEntity() {
    super(IronChestsBlockEntityTypes.DIRT_CHEST, IronChestsTypes.DIRT);
  }
  *///?}

  @Override
  protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
    return IronChestMenu.createDirtContainer(containerId, playerInventory, this);
  }

  @Override
  public boolean canPlaceItem(int slot, ItemStack stack) {
    return stack.isEmpty() || stack.getItem() == Items.DIRT || stack.getItem() == Blocks.DIRT.asItem();
  }

  // [2026-11-08 12:35] [CẬP NHẬT / SỬA LỖI]: Chuẩn hóa cơ chế cấp sách Book of Dirt 1:1 với nguyên bản Iron Chests
  // [Giải pháp khắc phục]: Sử dụng hook wasPlaced khi người chơi đặt khối để nhét sách vào slot 0 nếu chưa từng được cấp
  // [Kiểm chứng]: Khi đặt rương mới luôn có sách hướng dẫn, khi bẻ rương trống không rớt sách lặp lại vô hạn
  @Override
  public void wasPlaced(LivingEntity livingEntity, ItemStack itemStack) {
    if (!this.initializedBook && this.isEmpty()) {
      this.setItem(0, Util.createDirtGuideBook());
      this.initializedBook = true;
      this.setChanged();
    }
  }

  @Override
  public void removeAdornments() {
    if (!this.getItems().get(0).isEmpty()) {
      ItemStack stack = this.getItems().get(0);
      if (stack.getItem() == Items.WRITTEN_BOOK) {
        this.getItems().set(0, ItemStack.EMPTY);
      }
    }
  }

  //? if >= 1.21.11 {
  /*@Override
  protected void loadAdditional(net.minecraft.world.level.storage.ValueInput input) {
    super.loadAdditional(input);
    this.initializedBook = input.getBooleanOr("ChestPlacedAlready", false);
    if (!this.initializedBook && this.isEmpty()) {
      this.setItem(0, Util.createDirtGuideBook());
      this.initializedBook = true;
    }
  }

  @Override
  protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput output) {
    super.saveAdditional(output);
    output.putBoolean("ChestPlacedAlready", this.initializedBook);
  }
  *///?} else if >= 1.20.5 {
  @Override
  protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.loadAdditional(tag, registries);
    this.initializedBook = tag.getBoolean("ChestPlacedAlready");
    if (!this.initializedBook && this.isEmpty()) {
      this.setItem(0, Util.createDirtGuideBook());
      this.initializedBook = true;
    }
  }

  @Override
  protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.saveAdditional(tag, registries);
    tag.putBoolean("ChestPlacedAlready", this.initializedBook);
  }
  
  //?} else if >= 1.18 {
  /*@Override
  public void load(CompoundTag tag) {
    super.load(tag);
    this.initializedBook = tag.getBoolean("ChestPlacedAlready");
    if (!this.initializedBook && this.isEmpty()) {
      this.setItem(0, Util.createDirtGuideBook());
      this.initializedBook = true;
    }
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    tag.putBoolean("ChestPlacedAlready", this.initializedBook);
  }
  *///?} else if >= 1.17 {
  /*@Override
  public void load(CompoundTag tag) {
    super.load(tag);
    this.initializedBook = tag.getBoolean("ChestPlacedAlready");
    if (!this.initializedBook && this.isEmpty()) {
      this.setItem(0, Util.createDirtGuideBook());
      this.initializedBook = true;
    }
  }

  @Override
  public CompoundTag save(CompoundTag tag) {
    super.save(tag);
    tag.putBoolean("ChestPlacedAlready", this.initializedBook);
    return tag;
  }
  *///?} else {
  /*@Override
  public void load(BlockState state, CompoundTag tag) {
    super.load(state, tag);
    this.initializedBook = tag.getBoolean("ChestPlacedAlready");
    if (!this.initializedBook && this.isEmpty()) {
      this.setItem(0, Util.createDirtGuideBook());
      this.initializedBook = true;
    }
  }

  @Override
  public CompoundTag save(CompoundTag tag) {
    super.save(tag);
    tag.putBoolean("ChestPlacedAlready", this.initializedBook);
    return tag;
  }
  *///?}
}
