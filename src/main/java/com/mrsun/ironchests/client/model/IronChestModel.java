package com.mrsun.ironchests.client.model;

//? if >= 1.21.11 {
/*import com.mrsun.ironchests.IronChestsFabric;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class IronChestModel extends Model<Float> {
  private static final String BOTTOM = "iron_bottom";
  private static final String LID = "iron_lid";
  private static final String LOCK = "iron_lock";
  private final ModelPart lid;
  private final ModelPart lock;

  public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(IronChestsFabric.id("chest"), "main");

  public IronChestModel(ModelPart root) {
    //? if >= 26.1 {
    /^super(root, RenderTypes::entityCutoutCull);
    ^///?} else {
    super(root, RenderTypes::entityCutout);
    //?}
    this.lid = root.getChild(LID);
    this.lock = root.getChild(LOCK);
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshDefinition = new MeshDefinition();
    PartDefinition partDefinition = meshDefinition.getRoot();

    partDefinition.addOrReplaceChild(BOTTOM, CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), PartPose.ZERO);
    partDefinition.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
    partDefinition.addOrReplaceChild(LOCK, CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -2.0F, 14.0F, 2.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 9.0F, 1.0F));

    return LayerDefinition.create(meshDefinition, 64, 64);
  }

  @Override
  public void setupAnim(Float renderState) {
    super.setupAnim(renderState);
    this.lid.xRot = -(renderState * (float) (Math.PI / 2));
    this.lock.xRot = this.lid.xRot;
  }
}
*///?} else if >= 1.17 {
import com.mrsun.ironchests.IronChestsFabric;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class IronChestModel {

  public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(IronChestsFabric.id("chest"), "main");

  public static LayerDefinition createBodyLayer() {
    MeshDefinition mesh = new MeshDefinition();
    PartDefinition root = mesh.getRoot();

    root.addOrReplaceChild("iron_bottom",
      CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F),
      PartPose.ZERO);

    root.addOrReplaceChild("iron_lid",
      CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F),
      PartPose.offset(0.0F, 9.0F, 1.0F));

    root.addOrReplaceChild("iron_lock",
      CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F),
      PartPose.offset(0.0F, 8.0F, 0.0F));

    return LayerDefinition.create(mesh, 64, 64);
  }
}
//?} else {
/*public class IronChestModel {
}
*///?}
