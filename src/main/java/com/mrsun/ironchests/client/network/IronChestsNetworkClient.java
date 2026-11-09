package com.mrsun.ironchests.client.network;

import com.mrsun.ironchests.common.block.entity.ICrystalChest;
import com.mrsun.ironchests.common.network.IronChestsNetwork;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

//? if < 1.20.5 {
/*import net.minecraft.network.FriendlyByteBuf;
*///?}

public class IronChestsNetworkClient {

  //? if < 1.20.5 {
  /*private static ItemStack readItemFromBuffer(FriendlyByteBuf buf) {
    return buf.readItem();
  }
  *///?}

  public static void initClient() {
    //? if < 1.20.5 {
    /*ClientPlayNetworking.registerGlobalReceiver(IronChestsNetwork.TOP_STACKS_CHANNEL, (client, handler, buf, responseSender) -> {
      BlockPos pos = buf.readBlockPos();
      int size = buf.readInt();
      NonNullList<ItemStack> stacks = NonNullList.withSize(size, ItemStack.EMPTY);
      for (int i = 0; i < size; i++) {
        stacks.set(i, readItemFromBuffer(buf));
      }
      client.execute(() -> {
        if (client.level != null) {
          BlockEntity be = client.level.getBlockEntity(pos);
          if (be instanceof ICrystalChest) {
            ICrystalChest crystalChest = (ICrystalChest) be;
            crystalChest.receiveMessageFromServer(stacks);
            Minecraft.getInstance().levelRenderer.blockChanged(null, pos, null, null, 0);
          }
        }
      });
    });
    *///?} else {
    ClientPlayNetworking.registerGlobalReceiver(IronChestsNetwork.TOP_STACKS_TYPE, (payload, context) -> {
      context.client().execute(() -> {
        if (context.client().level != null) {
          BlockEntity be = context.client().level.getBlockEntity(payload.pos());
          if (be instanceof ICrystalChest) {
            ICrystalChest crystalChest = (ICrystalChest) be;
            NonNullList<ItemStack> stacks = NonNullList.withSize(payload.stacks().size(), ItemStack.EMPTY);
            for (int i = 0; i < payload.stacks().size(); i++) {
              stacks.set(i, payload.stacks().get(i));
            }
            crystalChest.receiveMessageFromServer(stacks);

            // [2026-11-08 20:45] [Bản chất]: Ép hệ thống renderer làm mới bộ đệm hiển thị item xoay 3D
            //? if >= 26.1 {
            /*Minecraft.getInstance().levelExtractor.blockChanged(payload.pos(), 0);
            *///?} else {
            Minecraft.getInstance().levelRenderer.blockChanged(null, payload.pos(), null, null, 0);
            //?}
          }
        }
      });
    });
    //?}
  }
}
