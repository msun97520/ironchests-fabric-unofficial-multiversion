package com.mrsun.ironchests.client.render.special;

//? if >= 1.21.11 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrsun.ironchests.client.model.IronChestModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
//? if >= 26.1 {
/^import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
^///?} else {
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
//?}
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class IronChestSpecialRenderer implements NoDataSpecialModelRenderer {

  //? if >= 26.1 {
  /^private final SpriteGetter sprites;
  private final SpriteId sprite;
  ^///?} else {
  private final MaterialSet materials;
  private final Material material;
  //?}
  private final IronChestModel model;
  private final float openness;

  //? if >= 26.1 {
  /^public IronChestSpecialRenderer(SpriteGetter sprites, IronChestModel model, SpriteId sprite, float openness) {
    this.sprites = sprites;
    this.model = model;
    this.sprite = sprite;
    this.openness = openness;
  }
  ^///?} else {
  public IronChestSpecialRenderer(MaterialSet materials, IronChestModel model, Material material, float openness) {
    this.materials = materials;
    this.model = model;
    this.material = material;
    this.openness = openness;
  }
  //?}

  //? if >= 26.1 {
  /^@Override
  public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
    //? if >= 26.3 {
    /^¹submitNodeCollector.submitModel(this.model, this.openness, poseStack, lightCoords, overlayCoords, -1, this.sprite, this.sprites, outlineColor);
    ¹^///?} else {
    submitNodeCollector.submitModel(this.model, this.openness, poseStack, lightCoords, overlayCoords, -1, this.sprite, this.sprites, outlineColor, null);
    //?}
  }
  ^///?} else {
  @Override
  public void submit(net.minecraft.world.item.ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
    nodeCollector.submitModel(
      this.model,
      this.openness,
      poseStack,
      this.material.renderType(net.minecraft.client.renderer.rendertype.RenderTypes::entityCutout),
      lightCoords,
      overlayCoords,
      -1,
      this.materials.get(this.material),
      outlineColor,
      null
    );
  }
  //?}

  @Override
  public void getExtents(Consumer<Vector3fc> output) {
    PoseStack poseStack = new PoseStack();
    this.model.setupAnim(this.openness);
    this.model.root().getExtentsForGui(poseStack, output);
  }

  public record Unbaked(ResourceLocation texture, float openness) implements NoDataSpecialModelRenderer.Unbaked {
    public static final MapCodec<IronChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(
          ResourceLocation.CODEC.fieldOf("texture").forGetter(IronChestSpecialRenderer.Unbaked::texture),
          Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(IronChestSpecialRenderer.Unbaked::openness)
        )
        .apply(i, IronChestSpecialRenderer.Unbaked::new)
    );

    public Unbaked(ResourceLocation texture) {
      this(texture, 0.0F);
    }

    @Override
    public MapCodec<IronChestSpecialRenderer.Unbaked> type() {
      return MAP_CODEC;
    }

    //? if >= 26.1 {
    /^@Override
    public IronChestSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
      IronChestModel model = new IronChestModel(context.entityModelSet().bakeLayer(IronChestModel.LAYER_LOCATION));
      SpriteId fullTexture = new SpriteId(Sheets.CHEST_SHEET, this.texture);
      return new IronChestSpecialRenderer(context.sprites(), model, fullTexture, this.openness);
    }
    ^///?} else {
    @Override
    public IronChestSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
      IronChestModel model = new IronChestModel(context.entityModelSet().bakeLayer(IronChestModel.LAYER_LOCATION));
      Material fullTexture = new Material(Sheets.CHEST_SHEET, this.texture);
      return new IronChestSpecialRenderer(context.materials(), model, fullTexture, this.openness);
    }
    //?}
  }
}
*///?}
