package com.mrsun.ironchests.common.block.regular;

import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.regular.entity.AbstractIronChestBlockEntity;
import com.mrsun.ironchests.common.block.regular.entity.CrystalChestBlockEntity;
import com.mrsun.ironchests.common.block.regular.entity.DirtChestBlockEntity;
import com.mrsun.ironchests.common.block.regular.entity.StandardIronChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
//? if >= 1.17 {
import net.minecraft.world.level.block.entity.BlockEntityTicker;
//?}
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GenericIronChestBlock extends AbstractIronChestBlock {

  public GenericIronChestBlock(Properties properties, IronChestsTypes chestType, boolean trapped, Supplier<BlockEntityType<? extends AbstractIronChestBlockEntity>> blockEntityType) {
    super(properties, chestType, trapped, blockEntityType);
  }

  //? if >= 1.17 {
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    if (this.getChestType() == IronChestsTypes.CRYSTAL) {
      return new CrystalChestBlockEntity(pos, state);
    } else if (this.getChestType() == IronChestsTypes.DIRT) {
      return new DirtChestBlockEntity(pos, state);
    } else {
      return new StandardIronChestBlockEntity(pos, state, this.getChestType());
    }
  }

  // [2026-11-08 20:45] [Mục đích / Lý do]: Kích hoạt ticker máy chủ cho Crystal Chest để xử lý đồng bộ 8 item xoay tức thời
  // [Bản chất]: Mô phỏng 1:1 theo CrystalChestBlock của Iron Chests gốc, phía Server gọi CrystalChestBlockEntity::tick khi có thay đổi.
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    if (this.getChestType() == IronChestsTypes.CRYSTAL) {
      return level.isClientSide()
        ? createTickerHelper(blockEntityType, this.blockEntityType.get(), AbstractIronChestBlockEntity::lidAnimateTick)
        : createTickerHelper(blockEntityType, this.blockEntityType.get(), CrystalChestBlockEntity::tick);
    }
    return super.getTicker(level, state, blockEntityType);
  }
  //?} else {
  /*@Override
  public BlockEntity newBlockEntity(net.minecraft.world.level.BlockGetter level) {
    if (this.getChestType() == IronChestsTypes.CRYSTAL) {
      return new CrystalChestBlockEntity();
    } else if (this.getChestType() == IronChestsTypes.DIRT) {
      return new DirtChestBlockEntity();
    } else {
      return new StandardIronChestBlockEntity(this.getChestType());
    }
  }
  *///?}
}
