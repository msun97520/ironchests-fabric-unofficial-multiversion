package com.mrsun.ironchests.common.block;

import com.mrsun.ironchests.IronChestsFabric;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum IronChestsTypes implements StringRepresentable {

  IRON(54, 9, 184, 222, "textures/gui/iron_container.png", 256, 256),
  GOLD(81, 9, 184, 276, "textures/gui/gold_container.png", 256, 276),
  DIAMOND(108, 12, 238, 276, "textures/gui/diamond_container.png", 256, 276),
  COPPER(45, 9, 184, 204, "textures/gui/copper_container.png", 256, 256),
  CRYSTAL(108, 12, 238, 276, "textures/gui/diamond_container.png", 256, 276),
  OBSIDIAN(108, 12, 238, 276, "textures/gui/diamond_container.png", 256, 276),
  DIRT(1, 1, 184, 184, "textures/gui/dirt_container.png", 256, 256),
  WOOD(0, 0, 0, 0, "", 0, 0);

  public final int size;
  public final int rowLength;
  public final int xSize;
  public final int ySize;
  private final String texturePath;
  public final int textureXSize;
  public final int textureYSize;

  IronChestsTypes(int size, int rowLength, int xSize, int ySize, String texturePath, int textureXSize, int textureYSize) {
    this.size = size;
    this.rowLength = rowLength;
    this.xSize = xSize;
    this.ySize = ySize;
    this.texturePath = texturePath;
    this.textureXSize = textureXSize;
    this.textureYSize = textureYSize;
  }

  public ResourceLocation getGuiTexture() {
    return IronChestsFabric.id(this.texturePath);
  }

  public String getId() {
    return this.name().toLowerCase(Locale.ROOT);
  }

  @Override
  public String getSerializedName() {
    return this.getId();
  }

  public int getRowCount() {
    if (this.rowLength == 0) return 0;
    return this.size / this.rowLength;
  }

  public boolean isTransparent() {
    return this == CRYSTAL;
  }

  public static List<Block> getBlocksForType(IronChestsTypes type) {
    switch (type) {
      case IRON:
        return Arrays.asList(IronChestsBlocks.IRON_CHEST, IronChestsBlocks.TRAPPED_IRON_CHEST);
      case GOLD:
        return Arrays.asList(IronChestsBlocks.GOLD_CHEST, IronChestsBlocks.TRAPPED_GOLD_CHEST);
      case DIAMOND:
        return Arrays.asList(IronChestsBlocks.DIAMOND_CHEST, IronChestsBlocks.TRAPPED_DIAMOND_CHEST);
      //? if >= 1.17 {
      case COPPER:
        return Arrays.asList(IronChestsBlocks.COPPER_CHEST, IronChestsBlocks.TRAPPED_COPPER_CHEST);
      //?}
      case CRYSTAL:
        return Arrays.asList(IronChestsBlocks.CRYSTAL_CHEST, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST);
      case OBSIDIAN:
        return Arrays.asList(IronChestsBlocks.OBSIDIAN_CHEST, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST);
      case DIRT:
        return Arrays.asList(IronChestsBlocks.DIRT_CHEST, IronChestsBlocks.TRAPPED_DIRT_CHEST);
      default:
        return Arrays.asList(Blocks.CHEST);
    }
  }
}
