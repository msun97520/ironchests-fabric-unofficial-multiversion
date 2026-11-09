package com.mrsun.ironchests.common.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class DirtChestSlot extends Slot {

  public DirtChestSlot(Container container, int slot, int x, int y) {
    super(container, slot, x, y);
  }

  @Override
  public boolean mayPlace(ItemStack stack) {
    return stack.isEmpty() || stack.getItem() == Items.DIRT || stack.getItem() == Blocks.DIRT.asItem();
  }
}

