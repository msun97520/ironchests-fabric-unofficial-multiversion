package com.mrsun.ironchests.common.block.entity;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface ICrystalChest {
  NonNullList<ItemStack> getTopItems();

  void receiveMessageFromServer(NonNullList<ItemStack> topStacks);
}
