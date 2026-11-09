package com.mrsun.ironchests.common.block.regular;

import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.regular.entity.AbstractIronChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
//? if >= 1.21.11 {
/*import net.minecraft.world.entity.animal.feline.Cat;
*///?} else {
import net.minecraft.world.entity.animal.Cat;
//?}
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
//? if >= 1.17 {
import net.minecraft.world.level.block.entity.BlockEntityTicker;
//?}
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractIronChestBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

  public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

  private final IronChestsTypes chestType;
  private final boolean trapped;
  protected final Supplier<BlockEntityType<? extends AbstractIronChestBlockEntity>> blockEntityType;

  public AbstractIronChestBlock(Properties properties, IronChestsTypes chestType, boolean trapped, Supplier<BlockEntityType<? extends AbstractIronChestBlockEntity>> blockEntityType) {
    super(properties);
    this.chestType = chestType;
    this.trapped = trapped;
    this.blockEntityType = blockEntityType;

    this.registerDefaultState(this.stateDefinition.any()
      .setValue(FACING, Direction.NORTH)
      .setValue(WATERLOGGED, false));
  }

  public IronChestsTypes getChestType() {
    return this.chestType;
  }

  public boolean isTrapped() {
    return this.trapped;
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  //? if < 1.21.11 {
  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }
  //?}

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Direction direction = context.getHorizontalDirection().getOpposite();
    FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
    return this.defaultBlockState()
      .setValue(FACING, direction)
      .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  //? if >= 1.21.11 {
  /*@Override
  protected BlockState updateShape(BlockState state, net.minecraft.world.level.LevelReader level, net.minecraft.world.level.ScheduledTickAccess scheduledTicks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, net.minecraft.util.RandomSource random) {
    if (state.getValue(WATERLOGGED)) {
      scheduledTicks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
    return super.updateShape(state, level, scheduledTicks, pos, direction, neighborPos, neighborState, random);
  }
  *///?} else if >= 1.18 {
  @Override
  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
    if (state.getValue(WATERLOGGED)) {
      level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
  }
  //?} else {
  /*@Override
  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
    if (state.getValue(WATERLOGGED)) {
      level.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
  }*///?}

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING, WATERLOGGED);
  }

  @Override
  public BlockState rotate(BlockState state, Rotation rotation) {
    return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirror) {
    return state.rotate(mirror.getRotation(state.getValue(FACING)));
  }

  //? if >= 1.20.5 {
  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
    if (level.isClientSide()) {
      return InteractionResult.SUCCESS;
    }
    if (isChestBlockedAt(level, pos)) {
      return InteractionResult.CONSUME;
    }
    MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
    if (menuProvider != null) {
      player.openMenu(menuProvider);
    }
    return InteractionResult.CONSUME;
  }
  //?} else {
  /*@Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (level.isClientSide()) {
      return InteractionResult.SUCCESS;
    }
    if (isChestBlockedAt(level, pos)) {
      return InteractionResult.CONSUME;
    }
    MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
    if (menuProvider != null) {
      player.openMenu(menuProvider);
    }
    return InteractionResult.CONSUME;
  }*///?}

  // [2026-11-08 12:35] [CẬP NHẬT / SỬA LỖI]: Bổ sung phương thức setPlacedBy và hoàn thiện vòng đời đặt khối rương
  // [Giải pháp khắc phục]: Gọi wasPlaced trên BlockEntity khi đặt khối, đồng thời chuyển customName từ ItemStack sang BlockEntity
  // [Kiểm chứng]: Rương DirtChest 9000 tự động sinh Sách hướng dẫn Book of Dirt khi đặt lần đầu; rương đặt tên trong Anvil giữ nguyên tên hiển thị
  @Override
  public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack stack) {
    BlockEntity blockEntity = level.getBlockEntity(pos);
    if (blockEntity instanceof AbstractIronChestBlockEntity) {
      AbstractIronChestBlockEntity chest = (AbstractIronChestBlockEntity) blockEntity;
      chest.wasPlaced(livingEntity, stack);
      //? if >= 1.20.5 {
      if (stack.has(net.minecraft.core.component.DataComponents.CUSTOM_NAME)) {
        chest.setCustomName(stack.getHoverName());
      }
      //?} else {
      /*if (stack.hasCustomHoverName()) {
        chest.setCustomName(stack.getHoverName());
      }
      *///?}
    }
  }

  //? if >= 1.21.11 {
  /*@Override
  protected void affectNeighborsAfterRemoval(BlockState state, net.minecraft.server.level.ServerLevel level, BlockPos pos, boolean isMoving) {
    BlockEntity blockEntity = level.getBlockEntity(pos);
    if (blockEntity instanceof AbstractIronChestBlockEntity) {
      AbstractIronChestBlockEntity chest = (AbstractIronChestBlockEntity) blockEntity;
      chest.removeAdornments();
      Containers.dropContents(level, pos, chest);
      level.updateNeighbourForOutputSignal(pos, this);
    }
    super.affectNeighborsAfterRemoval(state, level, pos, isMoving);
  }
  *///?} else {
  @Override
  public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
    if (!state.is(newState.getBlock())) {
      BlockEntity blockEntity = level.getBlockEntity(pos);
      if (blockEntity instanceof AbstractIronChestBlockEntity) {
        AbstractIronChestBlockEntity chest = (AbstractIronChestBlockEntity) blockEntity;
        chest.removeAdornments();
        Containers.dropContents(level, pos, chest);
        level.updateNeighbourForOutputSignal(pos, this);
      }
      super.onRemove(state, level, pos, newState, isMoving);
    }
  }//?}

  @Override
  public boolean hasAnalogOutputSignal(BlockState state) {
    return true;
  }

  //? if >= 1.21.11 {
  /*@Override
  protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
    return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
  }
  *///?} else {
  @Override
  public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
    return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
  }
  //?}

  @Override
  public boolean isSignalSource(BlockState state) {
    return this.trapped;
  }

  @Override
  public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
    if (!this.trapped) return 0;
    BlockEntity blockEntity = level.getBlockEntity(pos);
    if (blockEntity instanceof AbstractIronChestBlockEntity) {
      AbstractIronChestBlockEntity chest = (AbstractIronChestBlockEntity) blockEntity;
      return Mth.clamp(chest.getOpenCount(), 0, 15);
    }
    return 0;
  }

  @Override
  public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
    return side == Direction.UP ? this.getSignal(state, level, pos, side) : 0;
  }

  public static boolean isChestBlockedAt(LevelAccessor level, BlockPos pos) {
    return isBlockedChestByBlock(level, pos) || isCatSittingOnChest(level, pos);
  }

  public static boolean isBlockedChestByBlock(BlockGetter level, BlockPos pos) {
    BlockPos above = pos.above();
    return level.getBlockState(above).isRedstoneConductor(level, above);
  }

  public static boolean isCatSittingOnChest(LevelAccessor level, BlockPos pos) {
    List<Cat> cats = level.getEntitiesOfClass(Cat.class, new AABB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
    for (Cat cat : cats) {
      if (cat.isInSittingPose()) {
        return true;
      }
    }
    return false;
  }

  //? if >= 1.17 {
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    return createTickerHelper(blockEntityType, this.blockEntityType.get(), AbstractIronChestBlockEntity::lidAnimateTick);
  }
  //?}

  //? if (>= 1.20.3 && < 26.3) {
  @Override
  protected com.mojang.serialization.MapCodec<? extends BaseEntityBlock> codec() {
    return null;
  }
  //?}

  //? if < 1.20.6 {
  /*@Override
  public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
    return false;
  }*///?}
}
