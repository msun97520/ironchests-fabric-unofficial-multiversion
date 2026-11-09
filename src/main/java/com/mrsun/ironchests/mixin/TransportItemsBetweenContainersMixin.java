package com.mrsun.ironchests.mixin;

//? if >= 1.21.11 {
/*import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.regular.AbstractIronChestBlock;
import com.mrsun.ironchests.common.block.regular.entity.AbstractIronChestBlockEntity;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TransportItemsBetweenContainers.class)
public abstract class TransportItemsBetweenContainersMixin {

  @Inject(method = "isWantedBlock", at = @At("RETURN"), cancellable = true)
  private void ironchests$modifyWantedBlock(PathfinderMob mob, BlockState state, CallbackInfoReturnable<Boolean> cir) {
    if (!cir.getReturnValueZ()) {
      if (state.getBlock() instanceof AbstractIronChestBlock) {
        AbstractIronChestBlock chestBlock = (AbstractIronChestBlock) state.getBlock();
        if (mob.getMainHandItem().isEmpty()) {
          // Source container: chỉ rương đồng mod (copper chest)
          if (chestBlock.getChestType() == IronChestsTypes.COPPER) {
            cir.setReturnValue(true);
          }
        } else {
          // Destination container: các rương mod khác (iron, gold, diamond, dirt, v.v.)
          if (chestBlock.getChestType() != IronChestsTypes.COPPER) {
            cir.setReturnValue(true);
          }
        }
      }
    }
  }

  @Inject(method = "isAnotherMobInteractingWithTarget", at = @At("RETURN"), cancellable = true)
  private void ironchests$checkModChestOpen(TransportItemsBetweenContainers.TransportItemTarget target, Level level, CallbackInfoReturnable<Boolean> cir) {
    if (!cir.getReturnValueZ() && target != null) {
      if (target.blockEntity() instanceof AbstractIronChestBlockEntity) {
        AbstractIronChestBlockEntity ironChest = (AbstractIronChestBlockEntity) target.blockEntity();
        if (!ironChest.getEntitiesWithContainerOpen().isEmpty()) {
          cir.setReturnValue(true);
        }
      }
    }
  }

  @Inject(method = "getTransportTarget", at = @At("RETURN"), cancellable = true)
  private void ironchests$findModChestTarget(ServerLevel level, PathfinderMob mob, CallbackInfoReturnable<Optional<TransportItemsBetweenContainers.TransportItemTarget>> cir) {
    Optional<TransportItemsBetweenContainers.TransportItemTarget> current = cir.getReturnValue();
    double closestDist = Double.MAX_VALUE;
    TransportItemsBetweenContainers.TransportItemTarget bestTarget = null;
    if (current != null && current.isPresent()) {
      bestTarget = current.get();
      closestDist = bestTarget.pos().distToCenterSqr(mob.position());
    }

    BlockPos mobPos = mob.blockPosition();
    int minChunkX = (mobPos.getX() - 32) >> 4;
    int maxChunkX = (mobPos.getX() + 32) >> 4;
    int minChunkZ = (mobPos.getZ() - 32) >> 4;
    int maxChunkZ = (mobPos.getZ() + 32) >> 4;

    for (int cx = minChunkX; cx <= maxChunkX; cx++) {
      for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
        LevelChunk chunk = level.getChunkSource().getChunkNow(cx, cz);
        if (chunk == null) continue;
        for (BlockEntity be : new ArrayList<>(chunk.getBlockEntities().values())) {
          if (be instanceof AbstractIronChestBlockEntity) {
            AbstractIronChestBlockEntity modChest = (AbstractIronChestBlockEntity) be;
            BlockPos pos = modChest.getBlockPos();
            if (Math.abs(pos.getX() - mobPos.getX()) <= 32 
                && Math.abs(pos.getZ() - mobPos.getZ()) <= 32 
                && Math.abs(pos.getY() - mobPos.getY()) <= 8) {
              double dist = pos.distToCenterSqr(mob.position());
              if (dist < closestDist) {
                // Nếu golem rảnh tay: chỉ nhặt từ rương đồng mod và rương không được rỗng
                if (mob.getMainHandItem().isEmpty()) {
                  if (modChest.getChestType() != IronChestsTypes.COPPER || modChest.isEmpty()) {
                    continue;
                  }
                } else {
                  // Nếu golem có đồ: cất vào rương mod khác đồng, và rương phải nhận được đồ
                  if (modChest.getChestType() == IronChestsTypes.COPPER) {
                    continue;
                  }
                  if (!ironchests$canAcceptItem(modChest, mob.getMainHandItem())) {
                    continue;
                  }
                }

                if (!modChest.getEntitiesWithContainerOpen().isEmpty()) {
                  continue;
                }

                TransportItemsBetweenContainers.TransportItemTarget target = TransportItemsBetweenContainers.TransportItemTarget.tryCreatePossibleTarget(modChest, level);
                if (target != null) {
                  bestTarget = target;
                  closestDist = dist;
                }
              }
            }
          }
        }
      }
    }

    if (bestTarget != null && (current == null || !current.isPresent() || bestTarget != current.get())) {
      cir.setReturnValue(Optional.of(bestTarget));
    }
  }

  private static boolean ironchests$canAcceptItem(Container container, ItemStack handItem) {
    if (container.isEmpty()) {
      return true;
    }
    for (int i = 0; i < container.getContainerSize(); i++) {
      ItemStack stack = container.getItem(i);
      if (!stack.isEmpty() && ItemStack.isSameItemSameComponents(stack, handItem) && stack.getCount() < stack.getMaxStackSize()) {
        return true;
      }
    }
    return false;
  }
}
*///?}
