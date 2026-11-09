package com.mrsun.ironchests.common.network;

import com.mrsun.ironchests.IronChestsFabric;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

//? if < 1.20.5 {
/*import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
*///?} else {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//?}

public class IronChestsNetwork {

  //? if < 1.20.5 {
  /*public static final ResourceLocation TOP_STACKS_CHANNEL = IronChestsFabric.id("top_stacks");
  *///?} else {
  public static final CustomPacketPayload.Type<TopStacksPayload> TOP_STACKS_TYPE = new CustomPacketPayload.Type<>(IronChestsFabric.id("top_stacks"));

  public record TopStacksPayload(BlockPos pos, List<ItemStack> stacks) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, TopStacksPayload> STREAM_CODEC = CustomPacketPayload.codec(
      TopStacksPayload::write,
      TopStacksPayload::new
    );

    public TopStacksPayload(RegistryFriendlyByteBuf buf) {
      this(buf.readBlockPos(), ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buf));
    }

    public void write(RegistryFriendlyByteBuf buf) {
      buf.writeBlockPos(this.pos);
      ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buf, this.stacks);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
      return TOP_STACKS_TYPE;
    }
  }
  //?}

  public static void initCommon() {
    //? if >= 26.1 {
    /*PayloadTypeRegistry.clientboundPlay().register(TOP_STACKS_TYPE, TopStacksPayload.STREAM_CODEC);
    *///?} elif >= 1.20.5 {
    PayloadTypeRegistry.playS2C().register(TOP_STACKS_TYPE, TopStacksPayload.STREAM_CODEC);
    //?}
  }

  public static void sendTopStacks(Level level, BlockPos pos, NonNullList<ItemStack> topStacks) {
    if (level instanceof ServerLevel) {
      ServerLevel serverLevel = (ServerLevel) level;
      //? if < 1.20.5 {
      /*FriendlyByteBuf buf = PacketByteBufs.create();
      buf.writeBlockPos(pos);
      buf.writeInt(topStacks.size());
      for (ItemStack stack : topStacks) {
        buf.writeItem(stack);
      }
      for (ServerPlayer player : PlayerLookup.tracking(serverLevel, pos)) {
        ServerPlayNetworking.send(player, TOP_STACKS_CHANNEL, buf);
      }
      *///?} else {
      TopStacksPayload payload = new TopStacksPayload(pos, new ArrayList<>(topStacks));
      for (ServerPlayer player : PlayerLookup.tracking(serverLevel, pos)) {
        ServerPlayNetworking.send(player, payload);
      }
      //?}
    }
  }
}
