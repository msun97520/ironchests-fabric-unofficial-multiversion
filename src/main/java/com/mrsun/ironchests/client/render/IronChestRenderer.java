package com.mrsun.ironchests.client.render;

//? if >= 1.21.11 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrsun.ironchests.IronChestsFabric;
import com.mrsun.ironchests.client.model.IronChestModel;
import com.mrsun.ironchests.client.model.ModelItem;
import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.entity.ICrystalChest;
import com.mrsun.ironchests.common.block.regular.AbstractIronChestBlock;
import com.mrsun.ironchests.common.block.regular.entity.AbstractIronChestBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
//? if >= 26.1 {
/^import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
^///?} else if >= 1.21.11 {
/^import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
^///?}
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IronChestRenderer<T extends AbstractIronChestBlockEntity> implements BlockEntityRenderer<T, IronChestRenderState> {

//? if >= 26.1 {
  /^private final SpriteGetter sprites;
^///?} else {
  private final MaterialSet materials;
//?}
  private final IronChestModel model;
  private final ItemModelResolver itemModelResolver;

  private static final List<ModelItem> MODEL_ITEMS = Arrays.asList(
    new ModelItem(0.3F, 0.45F, 0.3F, 3.0F),
    new ModelItem(0.7F, 0.45F, 0.3F, 3.0F),
    new ModelItem(0.3F, 0.45F, 0.7F, 3.0F),
    new ModelItem(0.7F, 0.45F, 0.7F, 3.0F),
    new ModelItem(0.3F, 0.15F, 0.3F, 3.0F),
    new ModelItem(0.7F, 0.15F, 0.3F, 3.0F),
    new ModelItem(0.3F, 0.15F, 0.7F, 3.0F),
    new ModelItem(0.7F, 0.15F, 0.7F, 3.0F)
  );

  public IronChestRenderer(BlockEntityRendererProvider.Context context) {
    //? if >= 26.1 {
    /^this.sprites = context.sprites();
    ^///?} else {
    this.materials = context.materials();
    //?}
    this.itemModelResolver = context.itemModelResolver();
    this.model = new IronChestModel(context.bakeLayer(IronChestModel.LAYER_LOCATION));
  }

  @Override
  public IronChestRenderState createRenderState() {
    return new IronChestRenderState();
  }

  @Override
  public void extractRenderState(T blockEntity, IronChestRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
    BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

    BlockState blockState = blockEntity.getBlockState();
    IronChestsTypes chestType = blockEntity.getChestType();

    renderState.chestType = chestType;
    renderState.open = blockEntity.getOpenNess(partialTick);
    renderState.angle = blockState.hasProperty(AbstractIronChestBlock.FACING)
      ? blockState.getValue(AbstractIronChestBlock.FACING).toYRot()
      : Direction.SOUTH.toYRot();
    renderState.trapped = blockState.getBlock() instanceof AbstractIronChestBlock && ((AbstractIronChestBlock) blockState.getBlock()).isTrapped();
    renderState.items = new ArrayList<>();
    renderState.itemRotation = (float) (360D * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) - partialTick;

    int i = (int) blockEntity.getBlockPos().asLong();
    if (chestType.isTransparent() && blockEntity instanceof ICrystalChest) {
      ICrystalChest crystalChest = (ICrystalChest) blockEntity;
      for (int j = 0; j < MODEL_ITEMS.size() && j < crystalChest.getTopItems().size(); j++) {
        ItemStack item = crystalChest.getTopItems().get(j);
        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        if (!item.isEmpty()) {
          this.itemModelResolver.updateForTopItem(itemStackRenderState, item, ItemDisplayContext.NONE, blockEntity.getLevel(), null, i + j);
        }
        renderState.items.add(itemStackRenderState);
      }
    }
  }

  @Override
  public void submit(IronChestRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
    poseStack.pushPose();
    poseStack.translate(0.5F, 0.5F, 0.5F);
    //? if >= 26.3 {
    /^poseStack.rotateDegrees(Axis.YP, -renderState.angle);
    ^///?} else {
    poseStack.mulPose(Axis.YP.rotationDegrees(-renderState.angle));
    //?}
    poseStack.translate(-0.5F, -0.5F, -0.5F);
    float f = renderState.open;
    f = 1.0F - f;
    f = 1.0F - f * f * f;

    String textureName = (renderState.trapped ? "trapped_" : "") + renderState.chestType.getId() + "_chest";
    ResourceLocation textureLoc = IronChestsFabric.id("model/" + textureName);
    //? if >= 26.3 {
    /^SpriteId spriteId = new SpriteId(Sheets.CHEST_SHEET, textureLoc);
    nodeCollector.submitModel(
      this.model,
      f,
      poseStack,
      renderState.lightCoords,
      OverlayTexture.NO_OVERLAY,
      -1,
      spriteId,
      this.sprites,
      0
    );
    ^///?} else if >= 26.1 {
    /^SpriteId spriteId = new SpriteId(Sheets.CHEST_SHEET, textureLoc);
    nodeCollector.submitModel(
      this.model,
      f,
      poseStack,
      renderState.lightCoords,
      OverlayTexture.NO_OVERLAY,
      -1,
      spriteId,
      this.sprites,
      0,
      renderState.breakProgress
    );
    ^///?} else {
    Material material = new Material(Sheets.CHEST_SHEET, textureLoc);
    RenderType renderType = material.renderType(RenderTypes::entityCutout);
    TextureAtlasSprite textureatlassprite = this.materials.get(material);

    nodeCollector.submitModel(
      this.model,
      f,
      poseStack,
      renderType,
      renderState.lightCoords,
      OverlayTexture.NO_OVERLAY,
      -1,
      textureatlassprite,
      0,
      renderState.breakProgress
    );
    //?}

    poseStack.popPose();

    if (renderState.chestType.isTransparent() && Vec3.atCenterOf(renderState.blockPos).closerThan(cameraRenderState.pos, 128d)) {
      for (int i = 0; i < renderState.items.size() && i < MODEL_ITEMS.size(); i++) {
        ItemStackRenderState itemStackRenderState = renderState.items.get(i);
        if (!itemStackRenderState.isEmpty()) {
          ModelItem modelItem = MODEL_ITEMS.get(i);
          float scale = modelItem.getSizeScaled();

          poseStack.pushPose();
          poseStack.translate(modelItem.getX(), modelItem.getY(), modelItem.getZ());
          //? if >= 26.3 {
          /^poseStack.rotateDegrees(Axis.YP, renderState.itemRotation);
          ^///?} else {
          poseStack.mulPose(Axis.YP.rotationDegrees(renderState.itemRotation));
          //?}
          poseStack.scale(scale, scale, scale);
          itemStackRenderState.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
          poseStack.popPose();
        }
      }
    }
  }
}
*///?} else {
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
//? if >= 1.19.3 {
import com.mojang.math.Axis;
//?} else {
/*import com.mojang.math.Vector3f;*///?}
import com.mrsun.ironchests.IronChestsFabric;
import com.mrsun.ironchests.client.model.IronChestModel;
import com.mrsun.ironchests.client.model.ModelItem;
import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.block.entity.ICrystalChest;
import com.mrsun.ironchests.common.block.regular.AbstractIronChestBlock;
import com.mrsun.ironchests.common.block.regular.entity.AbstractIronChestBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//? if >= 1.17 {
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//?} else {
/*import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
*///?}
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

//? if >= 1.19.4 {
import net.minecraft.world.item.ItemDisplayContext;
//?} else {
/*import net.minecraft.client.renderer.block.model.ItemTransforms;*///?}

import java.util.Arrays;
import java.util.List;

//? if >= 1.17 {
public class IronChestRenderer<T extends AbstractIronChestBlockEntity> implements BlockEntityRenderer<T> {
//?} else {
/*public class IronChestRenderer<T extends AbstractIronChestBlockEntity> extends BlockEntityRenderer<T> {
*///?}

  private final ModelPart lid;
  private final ModelPart bottom;
  private final ModelPart lock;

  private static final List<ModelItem> MODEL_ITEMS = Arrays.asList(
    new ModelItem(0.3F, 0.45F, 0.3F, 3.0F),
    new ModelItem(0.7F, 0.45F, 0.3F, 3.0F),
    new ModelItem(0.3F, 0.45F, 0.7F, 3.0F),
    new ModelItem(0.7F, 0.45F, 0.7F, 3.0F),
    new ModelItem(0.3F, 0.15F, 0.3F, 3.0F),
    new ModelItem(0.7F, 0.15F, 0.3F, 3.0F),
    new ModelItem(0.3F, 0.15F, 0.7F, 3.0F),
    new ModelItem(0.7F, 0.15F, 0.7F, 3.0F)
  );

  //? if >= 1.17 {
  public IronChestRenderer(BlockEntityRendererProvider.Context context) {
    ModelPart root = context.bakeLayer(IronChestModel.LAYER_LOCATION);
    this.bottom = root.getChild("iron_bottom");
    this.lid = root.getChild("iron_lid");
    this.lock = root.getChild("iron_lock");
  }
  //?} else {
  /*public IronChestRenderer(BlockEntityRenderDispatcher dispatcher) {
    super(dispatcher);
    this.bottom = new ModelPart(64, 64, 0, 19);
    this.bottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
    this.lid = new ModelPart(64, 64, 0, 0);
    this.lid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
    this.lid.y = 9.0F;
    this.lid.z = 1.0F;
    this.lock = new ModelPart(64, 64, 0, 0);
    this.lock.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
    this.lock.y = 8.0F;
  }
  *///?}

  @Override
  public void render(T chest, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
    Level level = chest.getLevel();
    BlockState state = level != null ? chest.getBlockState() : null;
    Direction facing = state != null && state.hasProperty(AbstractIronChestBlock.FACING)
      ? state.getValue(AbstractIronChestBlock.FACING)
      : Direction.SOUTH;

    IronChestsTypes chestType = chest.getChestType();
    boolean trapped = chest.isTrapped();
    if (!trapped && state != null && state.getBlock() instanceof AbstractIronChestBlock) {
      AbstractIronChestBlock b = (AbstractIronChestBlock) state.getBlock();
      trapped = b.isTrapped();
    }

    poseStack.pushPose();
    poseStack.translate(0.5D, 0.5D, 0.5D);
    //? if >= 1.19.3 {
    poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
    //?} else {
    /*poseStack.mulPose(Vector3f.YP.rotationDegrees(-facing.toYRot()));*///?}
    poseStack.translate(-0.5D, -0.5D, -0.5D);

    float openness = chest.getOpenNess(partialTicks);
    openness = 1.0F - openness;
    openness = 1.0F - openness * openness * openness;

    this.lid.xRot = -(openness * ((float) Math.PI / 2F));
    this.lock.xRot = this.lid.xRot;

    String textureName = (trapped ? "trapped_" : "") + chestType.getId() + "_chest";
    ResourceLocation textureLoc = IronChestsFabric.id("model/" + textureName);
    Material material = new Material(Sheets.CHEST_SHEET, textureLoc);
    // [2026-11-08 13:15] [CẬP NHẬT / SỬA LỖI]: Triệt tiêu lỗi chồng chập texture / Z-fighting ở đế Crystal Chest trên mọi phiên bản < 26.1.
    // [Giải pháp khắc phục]: Luôn sử dụng RenderType::entityCutout cho mọi loại rương (kể cả Crystal) chuẩn 100% như bản gốc Iron Chests.
    // Cơ chế: entityTranslucent tắt culling khiến mặt đáy của rương Crystal (tọa độ Y=0) bị vẽ đè lên mặt trên của block sàn (cùng Y=0).
    // entityCutout bật CULL_ENABLED, giúp OpenGL loại bỏ mặt đáy rương khi nhìn từ trên xuống, chấm dứt hoàn toàn hiện tượng Z-fighting.
    VertexConsumer consumer = material.buffer(bufferSource, RenderType::entityCutout);

    this.lid.render(poseStack, consumer, packedLight, packedOverlay);
    this.lock.render(poseStack, consumer, packedLight, packedOverlay);
    this.bottom.render(poseStack, consumer, packedLight, packedOverlay);

    poseStack.popPose();

    // Render items floating inside crystal chest
    if (chestType.isTransparent() && chest instanceof ICrystalChest && level != null) {
      ICrystalChest crystalChest = (ICrystalChest) chest;
      float rotation = (float) (360D * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) - partialTicks;
      for (int i = 0; i < MODEL_ITEMS.size() && i < crystalChest.getTopItems().size(); i++) {
        ItemStack item = crystalChest.getTopItems().get(i);
        if (!item.isEmpty()) {
          renderFloatingItem(poseStack, bufferSource, item, MODEL_ITEMS.get(i), rotation, packedLight, level);
        }
      }
    }
  }

  private static void renderFloatingItem(PoseStack matrices, MultiBufferSource buffer, ItemStack item, ModelItem modelItem, float rotation, int light, Level level) {
    matrices.pushPose();
    matrices.translate(modelItem.getX(), modelItem.getY(), modelItem.getZ());
    //? if >= 1.19.3 {
    matrices.mulPose(Axis.YP.rotationDegrees(rotation));
    //?} else {
    /*matrices.mulPose(Vector3f.YP.rotationDegrees(rotation));*///?}
    float scale = modelItem.getSizeScaled();
    matrices.scale(scale, scale, scale);

    //? if >= 1.19.4 {
    Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, matrices, buffer, level, 0);
    //?} else if >= 1.17 {
    /*Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, matrices, buffer, 0);
    *///?} else {
    /*Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, matrices, buffer);
    *///?}

    matrices.popPose();
  }
}
//?}
