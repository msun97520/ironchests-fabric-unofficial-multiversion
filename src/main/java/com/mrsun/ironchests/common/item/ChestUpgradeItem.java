package com.mrsun.ironchests.common.item;

import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.regular.AbstractIronChestBlock;
import com.mrsun.ironchests.common.block.regular.entity.AbstractIronChestBlockEntity;
import com.mrsun.ironchests.common.block.regular.entity.CrystalChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.TrappedChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class ChestUpgradeItem extends Item {

  private final IronChestsUpgradeType type;

  public ChestUpgradeItem(IronChestsUpgradeType type, Properties properties) {
    super(properties);
    this.type = type;
  }

  public IronChestsUpgradeType getType() {
    return this.type;
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    return this.tryUpgradeChest(context.getLevel(), context.getClickedPos(), context.getPlayer(), context.getItemInHand());
  }

  public InteractionResult tryUpgradeChest(Level level, BlockPos pos, Player player, ItemStack upgradeStack) {
    BlockState oldState = level.getBlockState(pos);
    Block oldBlock = oldState.getBlock();
    BlockEntity oldEntity = level.getBlockEntity(pos);

    boolean isTrapped = false;
    boolean matches = false;

    if (this.type.canUpgrade(IronChestsTypes.WOOD)) {
      //? if >= 1.21.11 {
      if (oldBlock instanceof net.minecraft.world.level.block.CopperChestBlock || oldState.is(net.minecraft.tags.BlockTags.COPPER_CHESTS)) {
        return InteractionResult.PASS;
      }
      *///?}
      if (oldBlock instanceof ChestBlock) {
        if (oldState.hasProperty(ChestBlock.TYPE) && oldState.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
          return InteractionResult.PASS;
        }
        matches = true;
        isTrapped = oldBlock instanceof TrappedChestBlock;
      }
    } else {
      if (oldBlock instanceof AbstractIronChestBlock) {
        AbstractIronChestBlock chestBlock = (AbstractIronChestBlock) oldBlock;
        if (this.type.canUpgrade(chestBlock.getChestType())) {
          matches = true;
          isTrapped = chestBlock.isTrapped();
        }
      }
    }

    if (!matches || oldEntity == null) {
      return InteractionResult.PASS;
    }

    if (oldEntity instanceof AbstractIronChestBlockEntity) {
      AbstractIronChestBlockEntity ironChest = (AbstractIronChestBlockEntity) oldEntity;
      if (ironChest.getOpenCount() > 0) {
        return InteractionResult.PASS;
      }
      if (player != null && !ironChest.canOpen(player)) {
        return InteractionResult.PASS;
      }
    }

    if (level.isClientSide()) {
      return InteractionResult.SUCCESS;
    } else if (oldEntity instanceof ChestBlockEntity) {
      ChestBlockEntity vanillaChest = (ChestBlockEntity) oldEntity;
      if (player != null && !vanillaChest.canOpen(player)) {
        return InteractionResult.PASS;
      }
    }

    NonNullList<ItemStack> oldItems = NonNullList.create();
    Component customName = null;

    if (oldEntity instanceof AbstractIronChestBlockEntity) {
      AbstractIronChestBlockEntity ironChest = (AbstractIronChestBlockEntity) oldEntity;
      for (int i = 0; i < ironChest.getContainerSize(); i++) {
        oldItems.add(ironChest.getItem(i).copy());
      }
      customName = ironChest.getCustomName();
    } else if (oldEntity instanceof ChestBlockEntity) {
      ChestBlockEntity vanillaChest = (ChestBlockEntity) oldEntity;
      for (int i = 0; i < vanillaChest.getContainerSize(); i++) {
        oldItems.add(vanillaChest.getItem(i).copy());
      }
      customName = vanillaChest.getCustomName();
    }

    Direction facing = Direction.NORTH;
    if (oldState.hasProperty(AbstractIronChestBlock.FACING)) {
      facing = oldState.getValue(AbstractIronChestBlock.FACING);
    }
    boolean waterlogged = false;
    if (oldState.hasProperty(AbstractIronChestBlock.WATERLOGGED)) {
      waterlogged = oldState.getValue(AbstractIronChestBlock.WATERLOGGED);
    }

    Block targetBlock = isTrapped
      ? IronChestsTypes.getBlocksForType(this.type.target).get(1)
      : IronChestsTypes.getBlocksForType(this.type.target).get(0);

    BlockState targetState = targetBlock.defaultBlockState()
      .setValue(AbstractIronChestBlock.FACING, facing)
      .setValue(AbstractIronChestBlock.WATERLOGGED, waterlogged);

    level.setBlock(pos, targetState, 3);
    BlockEntity newEntity = level.getBlockEntity(pos);

    if (newEntity instanceof AbstractIronChestBlockEntity) {
      AbstractIronChestBlockEntity newChest = (AbstractIronChestBlockEntity) newEntity;
      for (int i = 0; i < oldItems.size() && i < newChest.getContainerSize(); i++) {
        newChest.setItem(i, oldItems.get(i));
      }
      if (customName != null) {
        newChest.setCustomName(customName);
      }
      if (newChest instanceof CrystalChestBlockEntity) {
        CrystalChestBlockEntity crystalChest = (CrystalChestBlockEntity) newChest;
        crystalChest.rebuildTopStacks();
      }
      newChest.setChanged();
    }

    AbstractIronChestBlockEntity.playSound(level, pos, SoundEvents.ANVIL_USE);

    //? if >= 1.17 {
    if (player != null && !player.getAbilities().instabuild) {
      upgradeStack.shrink(1);
    }
    //?} else {
    /*if (player != null && !player.abilities.instabuild) {
      upgradeStack.shrink(1);
    }
    *///?}

    return InteractionResult.SUCCESS;
  }
}

