package com.mrsun.ironchests.common.block.regular.entity;

import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.entity.ICrystalChest;
import com.mrsun.ironchests.common.inventory.IronChestMenu;
import com.mrsun.ironchests.common.network.IronChestsNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
//? if >= 1.20.5 {
import net.minecraft.core.HolderLookup;
//?}
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CrystalChestBlockEntity extends AbstractIronChestBlockEntity implements ICrystalChest {

  private final NonNullList<ItemStack> topStacks = NonNullList.withSize(8, ItemStack.EMPTY);
  private boolean inventoryTouched;
  private boolean hadStuff;

  //? if >= 1.17 {
  public CrystalChestBlockEntity(BlockPos pos, BlockState state) {
    super(IronChestsBlockEntityTypes.CRYSTAL_CHEST, pos, state, IronChestsTypes.CRYSTAL);
  }
  //?} else {
  /*public CrystalChestBlockEntity() {
    super(IronChestsBlockEntityTypes.CRYSTAL_CHEST, IronChestsTypes.CRYSTAL);
  }
  *///?}

  @Override
  protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
    return IronChestMenu.createCrystalContainer(containerId, playerInventory, this);
  }

  // [2026-11-08 21:20] [Mục đích / Lý do]: Server tick ticker đồng bộ danh sách 8 item xoay tức thời khi người chơi tương tác trong GUI
  // [Bản chất]: Khớp 1:1 với CrystalChestBlockEntity::tick của mod Iron Chests gốc. Khi inventoryTouched được đánh dấu,
  // chỉ chạy 1 lần duy nhất trong chu kỳ tick để tính toán 8 item nhiều nhất và phát sóng TopStacksPayload tới Client.
  // Tuyệt đối KHÔNG gọi sortTopStacks() trong setItem/removeItem/dropContents để tránh spam gói tin và lỗi tạo block ma khi phá rương.
  public static void tick(Level level, BlockPos blockPos, BlockState blockState, AbstractIronChestBlockEntity chestBlockEntity) {
    if (chestBlockEntity instanceof CrystalChestBlockEntity) {
      CrystalChestBlockEntity crystalChest = (CrystalChestBlockEntity) chestBlockEntity;
      if (!level.isClientSide() && crystalChest.inventoryTouched) {
        crystalChest.inventoryTouched = false;
        crystalChest.sortTopStacks();
      }
    }
  }

  //? if < 1.17 {
  /*@Override
  public void tick() {
    super.tick();
    if (this.level != null && !this.level.isClientSide() && this.inventoryTouched) {
      this.inventoryTouched = false;
      this.sortTopStacks();
    }
  }
  *///?}

  @Override
  public ItemStack getItem(int index) {
    this.inventoryTouched = true;
    return super.getItem(index);
  }

  @Override
  public void setItem(int index, ItemStack stack) {
    super.setItem(index, stack);
    this.inventoryTouched = true;
  }

  @Override
  public ItemStack removeItem(int index, int count) {
    this.inventoryTouched = true;
    return super.removeItem(index, count);
  }

  @Override
  public ItemStack removeItemNoUpdate(int index) {
    this.inventoryTouched = true;
    return super.removeItemNoUpdate(index);
  }

  @Override
  public void setItems(NonNullList<ItemStack> contents) {
    super.setItems(contents);
    this.inventoryTouched = true;
  }

  @Override
  public NonNullList<ItemStack> getTopItems() {
    return this.topStacks;
  }

  // [2026-11-08 18:35] [Mục đích / Lý do]: Nhận cập nhật 8 item tức thời từ Server gửi xuống qua TopStacksSyncPacket
  @Override
  public void receiveMessageFromServer(NonNullList<ItemStack> topStacks) {
    for (int i = 0; i < 8; i++) {
      if (i < topStacks.size()) {
        this.topStacks.set(i, topStacks.get(i));
      } else {
        this.topStacks.set(i, ItemStack.EMPTY);
      }
    }
  }

  // [2026-11-08 20:45] [Mục đích / Lý do]: Đồng bộ danh sách 8 item xoay của Crystal Chest ngay khi kho đồ thay đổi
  @Override
  public void setChanged() {
    super.setChanged();
    this.inventoryTouched = true;
  }

  // [2026-11-08 20:45] [Bản chất]: Thuật toán gom nhóm số lượng và sắp xếp 8 item nhiều nhất của Crystal Chest từ bản gốc Iron Chests
  public void rebuildTopStacks() {
    this.sortTopStacks();
  }

  public void sortTopStacks() {
    if (this.level == null || this.level.isClientSide()) {
      return;
    }

    NonNullList<ItemStack> tempCopy = NonNullList.withSize(IronChestsTypes.CRYSTAL.size, ItemStack.EMPTY);
    boolean hasStuff = false;
    int compressedIdx = 0;

    mainLoop:
    for (int i = 0; i < IronChestsTypes.CRYSTAL.size; i++) {
      ItemStack itemStack = this.getItems().get(i);
      if (!itemStack.isEmpty()) {
        for (int j = 0; j < compressedIdx; j++) {
          ItemStack tempCopyStack = tempCopy.get(j);
          //? if >= 1.20.5 {
          boolean same = ItemStack.isSameItemSameComponents(tempCopyStack, itemStack);
          //?} else {
          /*boolean same = tempCopyStack.getItem() == itemStack.getItem() && java.util.Objects.equals(tempCopyStack.getTag(), itemStack.getTag());
          *///?}
          if (same) {
            tempCopyStack.grow(itemStack.getCount());
            continue mainLoop;
          }
        }
        tempCopy.set(compressedIdx, itemStack.copy());
        compressedIdx++;
        hasStuff = true;
      }
    }

    if (!hasStuff && this.hadStuff) {
      this.hadStuff = false;
      for (int i = 0; i < this.topStacks.size(); i++) {
        this.topStacks.set(i, ItemStack.EMPTY);
      }
      IronChestsNetwork.sendTopStacks(this.level, this.worldPosition, this.topStacks);
      return;
    }

    this.hadStuff = true;

    tempCopy.sort((stack1, stack2) -> {
      if (stack1.isEmpty()) {
        return 1;
      } else if (stack2.isEmpty()) {
        return -1;
      } else {
        return stack2.getCount() - stack1.getCount();
      }
    });

    int slot = 0;
    for (ItemStack itemStack : tempCopy) {
      if (!itemStack.isEmpty() && itemStack.getCount() > 0) {
        if (slot == this.topStacks.size()) {
          break;
        }
        // [2026-11-08 21:20] [Bản chất]: Lưu bản sao với count = 1 để hiển thị item model 3D,
        // triệt tiêu vĩnh viễn lỗi range [1;99] của Mojang Codec khi số lượng item gom nhóm vượt quá 99 (128, 192...).
        ItemStack displayStack = itemStack.copy();
        displayStack.setCount(1);
        this.topStacks.set(slot, displayStack);
        slot++;
      }
    }

    for (int i = slot; i < this.topStacks.size(); i++) {
      this.topStacks.set(i, ItemStack.EMPTY);
    }

    IronChestsNetwork.sendTopStacks(this.level, this.worldPosition, this.topStacks);
  }

  //? if >= 1.18 {
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }
  //?} else {
  /*@Override
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
  }*///?}
}
