package com.mrsun.ironchests.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.inventory.IronChestMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

//? if >= 26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
*///?} else if >= 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
*///?} else if >= 1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else if >= 1.17 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
*///?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?}

public class IronChestScreen extends AbstractContainerScreen<IronChestMenu> {

  private final IronChestsTypes chestType;
  private final int textureXSize;
  private final int textureYSize;

  public IronChestScreen(IronChestMenu menu, Inventory playerInventory, Component title) {
    //? if >= 26.1 {
    /*super(menu, playerInventory, title, menu.getChestType().xSize, menu.getChestType().ySize);
    this.titleLabelX = 8;
    this.titleLabelY = 6;
    this.inventoryLabelX = (menu.getChestType().xSize - 162) / 2 + 1;
    this.inventoryLabelY = menu.getChestType().ySize - 94;
    *///?} else {
    super(menu, playerInventory, title);
    this.imageWidth = menu.getChestType().xSize;
    this.imageHeight = menu.getChestType().ySize;
    //?}
    this.chestType = menu.getChestType();
    this.textureXSize = this.chestType.textureXSize;
    this.textureYSize = this.chestType.textureYSize;
  }

  //? if >= 26.1 {
  /*@Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    this.extractBackground(graphics, mouseX, mouseY, a);
    super.extractRenderState(graphics, mouseX, mouseY, a);
    this.extractTooltip(graphics, mouseX, mouseY);
  }

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    super.extractBackground(graphics, mouseX, mouseY, a);
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;

    graphics.blit(RenderPipelines.GUI_TEXTURED, this.chestType.getGuiTexture(), x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
  }
  *///?} else if >= 1.20.2 {
  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
    super.render(guiGraphics, mouseX, mouseY, partialTicks);
    this.renderTooltip(guiGraphics, mouseX, mouseY);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    guiGraphics.drawString(this.font, this.title, 8, 6, 0xFF404040, false);
    int playerInvLeft = (this.chestType.xSize - 162) / 2 + 1;
    guiGraphics.drawString(this.font, this.playerInventoryTitle, playerInvLeft, this.imageHeight - 94, 0xFF404040, false);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    //? if >= 1.21.11 {
    /*guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.chestType.getGuiTexture(), x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
    *///?} else {
    guiGraphics.blit(this.chestType.getGuiTexture(), x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
    //?}
  }
  //?} else if >= 1.20 {
  /*@Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics);
    super.render(guiGraphics, mouseX, mouseY, partialTicks);
    this.renderTooltip(guiGraphics, mouseX, mouseY);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    // [2026-11-08 21:37] In 1.21.11+, GuiGraphics.drawString checks ARGB.alpha(color) == 0 and drops text if alpha is missing.
    // 0xFF404040 (-12566464) provides full alpha opacity, fully compatible across all versions.
    guiGraphics.drawString(this.font, this.title, 8, 6, 0xFF404040, false);
    int playerInvLeft = (this.chestType.xSize - 162) / 2 + 1;
    guiGraphics.drawString(this.font, this.playerInventoryTitle, playerInvLeft, this.imageHeight - 94, 0xFF404040, false);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    guiGraphics.blit(this.chestType.getGuiTexture(), x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
  }
  *///?} else if >= 1.17 {
  /*@Override
  public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(poseStack);
    super.render(poseStack, mouseX, mouseY, partialTicks);
    this.renderTooltip(poseStack, mouseX, mouseY);
  }

  @Override
  protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
    this.font.draw(poseStack, this.title, 8, 6, 0xFF404040);
    int playerInvLeft = (this.chestType.xSize - 162) / 2 + 1;
    this.font.draw(poseStack, this.playerInventoryTitle, playerInvLeft, this.imageHeight - 94, 0xFF404040);
  }

  @Override
  protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, this.chestType.getGuiTexture());
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
  }
  *///?} else {
  /*@Override
  public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(poseStack);
    super.render(poseStack, mouseX, mouseY, partialTicks);
    this.renderTooltip(poseStack, mouseX, mouseY);
  }

  @Override
  protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
    this.font.draw(poseStack, this.title, 8, 6, 0xFF404040);
    int playerInvLeft = (this.chestType.xSize - 162) / 2 + 1;
    this.font.draw(poseStack, this.inventory.getDisplayName(), playerInvLeft, this.imageHeight - 94, 0xFF404040);
  }

  @Override
  protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.minecraft.getTextureManager().bind(this.chestType.getGuiTexture());
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
  }
  *///?}
}
