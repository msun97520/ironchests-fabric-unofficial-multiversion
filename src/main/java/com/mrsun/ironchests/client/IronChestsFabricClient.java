package com.mrsun.ironchests.client;

import com.mrsun.ironchests.client.model.IronChestModel;
import com.mrsun.ironchests.client.network.IronChestsNetworkClient;
import com.mrsun.ironchests.client.render.IronChestRenderer;
import com.mrsun.ironchests.client.screen.IronChestScreen;
import com.mrsun.ironchests.common.block.IronChestsBlocks;
import com.mrsun.ironchests.common.block.regular.entity.IronChestsBlockEntityTypes;
import com.mrsun.ironchests.common.inventory.IronChestsMenuTypes;
import net.fabricmc.api.ClientModInitializer;
//? if < 1.21.11 {
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.regular.entity.AbstractIronChestBlockEntity;
import com.mrsun.ironchests.common.block.regular.entity.CrystalChestBlockEntity;
import com.mrsun.ironchests.common.block.regular.entity.DirtChestBlockEntity;
import com.mrsun.ironchests.common.block.regular.entity.StandardIronChestBlockEntity;
import com.mrsun.ironchests.common.item.IronChestsItems;
//? if < 1.17 {
/*import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
*///?} else {
import net.minecraft.client.Minecraft;
//?}
//?}
//? if >= 26.1 {
/*import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
*///?} else if >= 1.17 {
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
//?} else {
/*import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
*///?}
//? if >= 1.18 {
import net.minecraft.client.gui.screens.MenuScreens;
//?} else {
/*import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
*///?}
//? if < 1.19.3 {
/*import com.mrsun.ironchests.IronChestsFabric;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.renderer.Sheets;
*///?}

public class IronChestsFabricClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    IronChestsNetworkClient.initClient();
    //? if >= 26.1 {
    /*ModelLayerRegistry.registerModelLayer(IronChestModel.LAYER_LOCATION, IronChestModel::createBodyLayer);
    *///?} else if >= 1.17 {
    EntityModelLayerRegistry.registerModelLayer(IronChestModel.LAYER_LOCATION, IronChestModel::createBodyLayer);
    //?}

    //? if >= 1.21.11 {
    /*registerSpecialModelRenderer();
    *///?}

    //? if >= 1.17 {
    BlockEntityRendererRegistry.register(IronChestsBlockEntityTypes.COPPER_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.register(IronChestsBlockEntityTypes.IRON_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.register(IronChestsBlockEntityTypes.GOLD_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.register(IronChestsBlockEntityTypes.DIAMOND_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.register(IronChestsBlockEntityTypes.CRYSTAL_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.register(IronChestsBlockEntityTypes.OBSIDIAN_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.register(IronChestsBlockEntityTypes.DIRT_CHEST, IronChestRenderer::new);
    //?} else {
    /*// [2026-11-08 13:35] [CẬP NHẬT]: Trên 1.16.5 không có Copper nên không đăng ký renderer cho COPPER_CHEST
    BlockEntityRendererRegistry.INSTANCE.register(IronChestsBlockEntityTypes.IRON_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.INSTANCE.register(IronChestsBlockEntityTypes.GOLD_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.INSTANCE.register(IronChestsBlockEntityTypes.DIAMOND_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.INSTANCE.register(IronChestsBlockEntityTypes.CRYSTAL_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.INSTANCE.register(IronChestsBlockEntityTypes.OBSIDIAN_CHEST, IronChestRenderer::new);
    BlockEntityRendererRegistry.INSTANCE.register(IronChestsBlockEntityTypes.DIRT_CHEST, IronChestRenderer::new);
    *///?}

    //? if >= 1.18 {
    MenuScreens.register(IronChestsMenuTypes.COPPER_CHEST, IronChestScreen::new);
    MenuScreens.register(IronChestsMenuTypes.IRON_CHEST, IronChestScreen::new);
    MenuScreens.register(IronChestsMenuTypes.GOLD_CHEST, IronChestScreen::new);
    MenuScreens.register(IronChestsMenuTypes.DIAMOND_CHEST, IronChestScreen::new);
    MenuScreens.register(IronChestsMenuTypes.CRYSTAL_CHEST, IronChestScreen::new);
    MenuScreens.register(IronChestsMenuTypes.OBSIDIAN_CHEST, IronChestScreen::new);
    MenuScreens.register(IronChestsMenuTypes.DIRT_CHEST, IronChestScreen::new);
    //?} else if >= 1.17 {
    /*ScreenRegistry.register(IronChestsMenuTypes.COPPER_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.IRON_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.GOLD_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.DIAMOND_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.CRYSTAL_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.OBSIDIAN_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.DIRT_CHEST, IronChestScreen::new);
    *///?} else {
    /*// [2026-11-08 13:35] [CẬP NHẬT]: Trên 1.16.5 không có Copper nên không đăng ký Screen cho COPPER_CHEST
    ScreenRegistry.register(IronChestsMenuTypes.IRON_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.GOLD_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.DIAMOND_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.CRYSTAL_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.OBSIDIAN_CHEST, IronChestScreen::new);
    ScreenRegistry.register(IronChestsMenuTypes.DIRT_CHEST, IronChestScreen::new);
    *///?}

    //? if (>= 1.17 && < 1.19.3) {
    /*// [2026-11-08 12:00] [CẬP NHẬT / SỬA LỖI]: Khắc phục triệt để lỗi mất texture rương (tím đen / missingno) trên Fabric < 1.19.3
    ClientSpriteRegistryCallback.event(Sheets.CHEST_SHEET).register((atlas, registry) -> {
      registry.register(IronChestsFabric.id("model/copper_chest"));
      registry.register(IronChestsFabric.id("model/trapped_copper_chest"));
      registry.register(IronChestsFabric.id("model/iron_chest"));
      registry.register(IronChestsFabric.id("model/trapped_iron_chest"));
      registry.register(IronChestsFabric.id("model/gold_chest"));
      registry.register(IronChestsFabric.id("model/trapped_gold_chest"));
      registry.register(IronChestsFabric.id("model/diamond_chest"));
      registry.register(IronChestsFabric.id("model/trapped_diamond_chest"));
      registry.register(IronChestsFabric.id("model/crystal_chest"));
      registry.register(IronChestsFabric.id("model/trapped_crystal_chest"));
      registry.register(IronChestsFabric.id("model/obsidian_chest"));
      registry.register(IronChestsFabric.id("model/trapped_obsidian_chest"));
      registry.register(IronChestsFabric.id("model/dirt_chest"));
      registry.register(IronChestsFabric.id("model/trapped_dirt_chest"));
    });
    *///?} else if < 1.17 {
    /*// [2026-11-08 13:35] [CẬP NHẬT]: Trên 1.16.5 không có Copper nên không đăng ký sprite cho copper_chest
    ClientSpriteRegistryCallback.event(Sheets.CHEST_SHEET).register((atlas, registry) -> {
      registry.register(IronChestsFabric.id("model/iron_chest"));
      registry.register(IronChestsFabric.id("model/trapped_iron_chest"));
      registry.register(IronChestsFabric.id("model/gold_chest"));
      registry.register(IronChestsFabric.id("model/trapped_gold_chest"));
      registry.register(IronChestsFabric.id("model/diamond_chest"));
      registry.register(IronChestsFabric.id("model/trapped_diamond_chest"));
      registry.register(IronChestsFabric.id("model/crystal_chest"));
      registry.register(IronChestsFabric.id("model/trapped_crystal_chest"));
      registry.register(IronChestsFabric.id("model/obsidian_chest"));
      registry.register(IronChestsFabric.id("model/trapped_obsidian_chest"));
      registry.register(IronChestsFabric.id("model/dirt_chest"));
      registry.register(IronChestsFabric.id("model/trapped_dirt_chest"));
    });
    *///?}

    //? if < 1.21.11 {
    // [2026-11-08 13:15] [CẬP NHẬT / SỬA LỖI]: Bỏ đăng ký translucent cho CRYSTAL_CHEST trên BlockRenderLayerMap.
    // Lý do: Chest là ENTITYBLOCK_ANIMATED do IronChestRenderer quản lý bằng entityCutout. Gán translucent ở cấp Block level
    // sẽ làm sai lệch render layer của chunk mesher, gây xung đột depth buffer. Giữ đúng chuẩn 1:1 theo bản gốc Iron Chests.

    registerChestItemRenderer(IronChestsItems.DIRT_CHEST, IronChestsBlocks.DIRT_CHEST, IronChestsTypes.DIRT, false);
    registerChestItemRenderer(IronChestsItems.TRAPPED_DIRT_CHEST, IronChestsBlocks.TRAPPED_DIRT_CHEST, IronChestsTypes.DIRT, true);
    //? if >= 1.17 {
    registerChestItemRenderer(IronChestsItems.COPPER_CHEST, IronChestsBlocks.COPPER_CHEST, IronChestsTypes.COPPER, false);
    registerChestItemRenderer(IronChestsItems.TRAPPED_COPPER_CHEST, IronChestsBlocks.TRAPPED_COPPER_CHEST, IronChestsTypes.COPPER, true);
    //?}
    registerChestItemRenderer(IronChestsItems.IRON_CHEST, IronChestsBlocks.IRON_CHEST, IronChestsTypes.IRON, false);
    registerChestItemRenderer(IronChestsItems.TRAPPED_IRON_CHEST, IronChestsBlocks.TRAPPED_IRON_CHEST, IronChestsTypes.IRON, true);
    registerChestItemRenderer(IronChestsItems.GOLD_CHEST, IronChestsBlocks.GOLD_CHEST, IronChestsTypes.GOLD, false);
    registerChestItemRenderer(IronChestsItems.TRAPPED_GOLD_CHEST, IronChestsBlocks.TRAPPED_GOLD_CHEST, IronChestsTypes.GOLD, true);
    registerChestItemRenderer(IronChestsItems.DIAMOND_CHEST, IronChestsBlocks.DIAMOND_CHEST, IronChestsTypes.DIAMOND, false);
    registerChestItemRenderer(IronChestsItems.TRAPPED_DIAMOND_CHEST, IronChestsBlocks.TRAPPED_DIAMOND_CHEST, IronChestsTypes.DIAMOND, true);
    registerChestItemRenderer(IronChestsItems.CRYSTAL_CHEST, IronChestsBlocks.CRYSTAL_CHEST, IronChestsTypes.CRYSTAL, false);
    registerChestItemRenderer(IronChestsItems.TRAPPED_CRYSTAL_CHEST, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST, IronChestsTypes.CRYSTAL, true);
    registerChestItemRenderer(IronChestsItems.OBSIDIAN_CHEST, IronChestsBlocks.OBSIDIAN_CHEST, IronChestsTypes.OBSIDIAN, false);
    registerChestItemRenderer(IronChestsItems.TRAPPED_OBSIDIAN_CHEST, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST, IronChestsTypes.OBSIDIAN, true);
    //?}
  }

  //? if >= 1.21.11 {
  /*// [2026-11-08 20:20] [Mục đích / Lý do]: Đăng ký SpecialModelRenderer codec vào ID_MAPPER cho Minecraft >= 1.21.11 (1.21.11, 26.2, 26.3)
  // [Bản chất]: Cho phép hệ thống Items Model Definition của Mojang nạp "type": "ironchest-mrsun-unofficial:iron_chest".
  private static void registerSpecialModelRenderer() {
    //? if (>= 26.3 || (>= 1.21.11 && < 26.1)) {
    /^net.minecraft.client.renderer.special.SpecialModelRenderers.ID_MAPPER.put(
      com.mrsun.ironchests.IronChestsFabric.id("iron_chest"),
      com.mrsun.ironchests.client.render.special.IronChestSpecialRenderer.Unbaked.MAP_CODEC
    );
    ^///?} else {
    try {
      java.lang.reflect.Field field = net.minecraft.client.renderer.special.SpecialModelRenderers.class.getDeclaredField("ID_MAPPER");
      field.setAccessible(true);
      @SuppressWarnings("unchecked")
      net.minecraft.util.ExtraCodecs.LateBoundIdMapper<net.minecraft.resources.ResourceLocation, com.mojang.serialization.MapCodec<? extends net.minecraft.client.renderer.special.SpecialModelRenderer.Unbaked<?>>> mapper =
          (net.minecraft.util.ExtraCodecs.LateBoundIdMapper<net.minecraft.resources.ResourceLocation, com.mojang.serialization.MapCodec<? extends net.minecraft.client.renderer.special.SpecialModelRenderer.Unbaked<?>>>) field.get(null);
      mapper.put(
        com.mrsun.ironchests.IronChestsFabric.id("iron_chest"),
        com.mrsun.ironchests.client.render.special.IronChestSpecialRenderer.Unbaked.MAP_CODEC
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
    //?}
  }
  *///?}

  //? if < 1.21.11 {
  private static void registerChestItemRenderer(ItemLike item, Block block, IronChestsTypes type, boolean trapped) {
    AbstractIronChestBlockEntity dummy;
    //? if >= 1.17 {
    if (type == IronChestsTypes.DIRT) {
      dummy = new DirtChestBlockEntity(BlockPos.ZERO, block.defaultBlockState());
    } else if (type == IronChestsTypes.CRYSTAL) {
      dummy = new CrystalChestBlockEntity(BlockPos.ZERO, block.defaultBlockState());
    } else {
      dummy = new StandardIronChestBlockEntity(BlockPos.ZERO, block.defaultBlockState(), type);
    }
    //?} else {
    /*if (type == IronChestsTypes.DIRT) {
      dummy = new DirtChestBlockEntity();
    } else if (type == IronChestsTypes.CRYSTAL) {
      dummy = new CrystalChestBlockEntity();
    } else {
      dummy = new StandardIronChestBlockEntity(type);
    }
    *///?}
    dummy.setTrapped(trapped);
    BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
      //? if < 1.17 {
      /*BlockEntityRenderDispatcher.instance.renderItem(dummy, matrices, vertexConsumers, light, overlay);
      *///?} else {
      Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(dummy, matrices, vertexConsumers, light, overlay);
      //?}
    });
  }
  //?}
}
