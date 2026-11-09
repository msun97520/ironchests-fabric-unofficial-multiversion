package com.mrsun.ironchests.common.block.regular.entity;

import com.mrsun.ironchests.IronChestsFabric;
import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.regular.AbstractIronChestBlock;
import com.mrsun.ironchests.common.inventory.IronChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
//? if >= 1.20.5 {
import net.minecraft.core.HolderLookup;
//?}
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
//? if >= 1.17 {
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
//?}
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

//? if >= 1.17 {
public abstract class AbstractIronChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity {
//?} else {
/*public abstract class AbstractIronChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity, net.minecraft.world.level.block.entity.TickableBlockEntity {
*///?}

  private NonNullList<ItemStack> items;
  private final IronChestsTypes chestType;
  //? if >= 1.17 {
  private final ChestLidController chestLidController = new ChestLidController();
  //?} else {
  /*private float openness;
  private float prevOpenness;
  private int openCount;
  *///?}
  private Component customName;
  private boolean trapped;

  public boolean isTrapped() {
    return this.trapped;
  }

  public void setTrapped(boolean trapped) {
    this.trapped = trapped;
  }

  public void setCustomName(Component customName) {
    this.customName = customName;
  }

  @Override
  public Component getCustomName() {
    return this.customName;
  }

  @Override
  public Component getName() {
    return this.customName != null ? this.customName : this.getDefaultName();
  }

  //? if >= 1.17 {
  private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
    @Override
    protected void onOpen(Level level, BlockPos pos, BlockState blockState) {
      AbstractIronChestBlockEntity.playSound(level, pos, SoundEvents.CHEST_OPEN);
    }

    @Override
    protected void onClose(Level level, BlockPos pos, BlockState blockState) {
      AbstractIronChestBlockEntity.playSound(level, pos, SoundEvents.CHEST_CLOSE);
    }

    @Override
    protected void openerCountChanged(Level level, BlockPos pos, BlockState blockState, int prevCount, int newCount) {
      AbstractIronChestBlockEntity.this.signalOpenCount(level, pos, blockState, prevCount, newCount);
    }

    @Override
    public boolean isOwnContainer(Player player) {
      if (player.containerMenu instanceof IronChestMenu) {
        IronChestMenu menu = (IronChestMenu) player.containerMenu;
        return menu.getContainer() == AbstractIronChestBlockEntity.this;
      }
      return false;
    }
  };
  //?}

  //? if >= 1.17 {
  protected AbstractIronChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, IronChestsTypes chestType) {
    super(type, pos, state);
    this.chestType = chestType;
    this.items = NonNullList.withSize(chestType.size, ItemStack.EMPTY);
  }
  //?} else {
  /*protected AbstractIronChestBlockEntity(BlockEntityType<?> type, IronChestsTypes chestType) {
    super(type);
    this.chestType = chestType;
    this.items = NonNullList.withSize(chestType.size, ItemStack.EMPTY);
  }
  *///?}

  public IronChestsTypes getChestType() {
    return this.chestType;
  }

  @Override
  public int getContainerSize() {
    return this.chestType.size;
  }

  @Override
  protected NonNullList<ItemStack> getItems() {
    return this.items;
  }

  @Override
  protected void setItems(NonNullList<ItemStack> items) {
    this.items = items;
  }

  @Override
  protected Component getDefaultName() {
    return com.mrsun.ironchests.common.Util.translatable(IronChestsFabric.MOD_ID + ".container." + this.chestType.getId() + "_chest");
  }

  //? if >= 1.21.11 {
  /*@Override
  protected void loadAdditional(net.minecraft.world.level.storage.ValueInput input) {
    super.loadAdditional(input);
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    if (!this.tryLoadLootTable(input)) {
      ContainerHelper.loadAllItems(input, this.items);
    }
  }

  @Override
  protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput output) {
    super.saveAdditional(output);
    if (!this.trySaveLootTable(output)) {
      ContainerHelper.saveAllItems(output, this.items);
    }
  }
  *///?} else if >= 1.20.5 {
  @Override
  protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.loadAdditional(tag, registries);
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    if (!this.tryLoadLootTable(tag)) {
      ContainerHelper.loadAllItems(tag, this.items, registries);
    }
  }

  @Override
  protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.saveAdditional(tag, registries);
    if (!this.trySaveLootTable(tag)) {
      ContainerHelper.saveAllItems(tag, this.items, registries);
    }
  }
  
  //?} else if >= 1.18 {
  /*@Override
  public void load(CompoundTag tag) {
    super.load(tag);
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    if (!this.tryLoadLootTable(tag)) {
      ContainerHelper.loadAllItems(tag, this.items);
    }
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    if (!this.trySaveLootTable(tag)) {
      ContainerHelper.saveAllItems(tag, this.items);
    }
  }
  *///?} else if >= 1.17 {
  /*@Override
  public void load(CompoundTag tag) {
    super.load(tag);
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    if (!this.tryLoadLootTable(tag)) {
      ContainerHelper.loadAllItems(tag, this.items);
    }
  }

  @Override
  public CompoundTag save(CompoundTag tag) {
    super.save(tag);
    if (!this.trySaveLootTable(tag)) {
      ContainerHelper.saveAllItems(tag, this.items);
    }
    return tag;
  }
  *///?} else {
  /*@Override
  public void load(BlockState state, CompoundTag tag) {
    super.load(state, tag);
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    if (!this.tryLoadLootTable(tag)) {
      ContainerHelper.loadAllItems(tag, this.items);
    }
  }

  @Override
  public CompoundTag save(CompoundTag tag) {
    super.save(tag);
    if (!this.trySaveLootTable(tag)) {
      ContainerHelper.saveAllItems(tag, this.items);
    }
    return tag;
  }
  *///?}

  @Override
  public boolean triggerEvent(int id, int type) {
    if (id == 1) {
      //? if >= 1.17 {
      this.chestLidController.shouldBeOpen(type > 0);
      //?} else {
      /*this.openCount = type;
      *///?}
      return true;
    }
    return super.triggerEvent(id, type);
  }

  //? if >= 1.21.11 {
  /*@Override
  public void startOpen(net.minecraft.world.entity.ContainerUser user) {
    // [2026-11-09 11:00] Hỗ trợ mọi ContainerUser (bao gồm Copper Golem và Player) mở rương, kích hoạt lid animation và âm thanh
    if (!this.remove && !user.getLivingEntity().isSpectator()) {
      this.openersCounter.incrementOpeners(user.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState(), user.getContainerInteractionRange());
    }
  }

  @Override
  public void stopOpen(net.minecraft.world.entity.ContainerUser user) {
    // [2026-11-09 11:00] Hỗ trợ mọi ContainerUser (Copper Golem, Player) đóng rương và giảm số lượng opener
    if (!this.remove && !user.getLivingEntity().isSpectator()) {
      this.openersCounter.decrementOpeners(user.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  @Override
  public java.util.List<net.minecraft.world.entity.ContainerUser> getEntitiesWithContainerOpen() {
    return this.openersCounter.getEntitiesWithContainerOpen(this.getLevel(), this.getBlockPos());
  }
  *///?} else if >= 1.17 {
  @Override
  public void startOpen(Player player) {
    if (!this.remove && !player.isSpectator()) {
      this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  @Override
  public void stopOpen(Player player) {
    if (!this.remove && !player.isSpectator()) {
      this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }
  //?} else {
  /*@Override
  public void startOpen(Player player) {
    if (!this.remove && !player.isSpectator()) {
      this.openCount++;
      if (this.level != null) {
        this.signalOpenCount(this.level, this.worldPosition, this.getBlockState(), this.openCount - 1, this.openCount);
      }
    }
  }

  @Override
  public void stopOpen(Player player) {
    if (!this.remove && !player.isSpectator()) {
      this.openCount = Math.max(0, this.openCount - 1);
      if (this.level != null) {
        this.signalOpenCount(this.level, this.worldPosition, this.getBlockState(), this.openCount + 1, this.openCount);
      }
    }
  }
  *///?}

  public int getOpenCount() {
    //? if >= 1.17 {
    return this.openersCounter.getOpenerCount();
    //?} else {
    /*return this.openCount;
    *///?}
  }

  @Override
  public float getOpenNess(float partialTicks) {
    //? if >= 1.17 {
    return this.chestLidController.getOpenness(partialTicks);
    //?} else {
    /*return net.minecraft.util.Mth.lerp(partialTicks, this.prevOpenness, this.openness);
    *///?}
  }

  public void recheckOpen() {
    //? if >= 1.17 {
    if (!this.remove) {
      this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
    //?}
  }

  // [2026-11-08 12:00] [CẬP NHẬT / SỬA LỖI]: Bổ sung cập nhật tín hiệu Redstone cho Rương Bẫy (Trapped Chests)
  // [Giải pháp khắc phục]: Khi số lượng người chơi mở rương thay đổi, nếu rương là rương bẫy (trapped),
  // bắt buộc phải gọi level.updateNeighborsAt tại vị trí rương và khối bên dưới để kích hoạt dây Redstone và khối kề bên.
  // [Kiểm chứng]: Đảm bảo tín hiệu Redstone phát ra tức thì và chính xác khi mở/đóng rương bẫy trên mọi phiên bản.
  protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int prevCount, int newCount) {
    Block block = state.getBlock();
    level.blockEvent(pos, block, 1, newCount);
    if (prevCount != newCount && (this.isTrapped() || (block instanceof AbstractIronChestBlock && ((AbstractIronChestBlock) block).isTrapped()))) {
      level.updateNeighborsAt(pos, block);
      level.updateNeighborsAt(pos.below(), block);
    }
  }

  // [2026-11-08 12:35] [Mục đích / Lý do]: Cung cấp hook vòng đời đặt khối và dọn dẹp vật phẩm bổ trợ khi phá rương
  // [Hành vi chi tiết]: wasPlaced được gọi từ setPlacedBy khi người chơi đặt khối rương; removeAdornments được gọi trong onRemove trước khi rớt đồ
  // [Điều kiện ràng buộc]: Mặc định không làm gì, cho phép các lớp con (như DirtChestBlockEntity) nạp sách hướng dẫn hoặc dọn sách khi phá rương
  public void wasPlaced(LivingEntity livingEntity, ItemStack stack) {
  }

  public void removeAdornments() {
  }

  //? if >= 1.17 {
  public static void lidAnimateTick(Level level, BlockPos pos, BlockState state, AbstractIronChestBlockEntity chest) {
    chest.chestLidController.tickLid();
  }
  //?} else {
  /*@Override
  public void tick() {
    this.prevOpenness = this.openness;
    if (this.openCount > 0 && this.openness == 0.0F) {
      playSound(this.level, this.worldPosition, SoundEvents.CHEST_OPEN);
    }
    if (this.openCount == 0 && this.openness > 0.0F || this.openCount > 0 && this.openness < 1.0F) {
      float f = this.openness;
      if (this.openCount > 0) {
        this.openness += 0.1F;
      } else {
        this.openness -= 0.1F;
      }
      if (this.openness > 1.0F) {
        this.openness = 1.0F;
      }
      if (this.openness < 0.0F) {
        this.openness = 0.0F;
      }
      if (this.openness < 0.5F && f >= 0.5F) {
        playSound(this.level, this.worldPosition, SoundEvents.CHEST_CLOSE);
      }
    }
  }
  *///?}

  public static void playSound(Level level, BlockPos pos, SoundEvent sound) {
    double x = pos.getX() + 0.5D;
    double y = pos.getY() + 0.5D;
    double z = pos.getZ() + 0.5D;
    //? if >= 26.1 {
    /*level.playSound(null, x, y, z, sound, SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    *///?} else {
    level.playSound(null, x, y, z, sound, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    //?}
  }
}
