package com.mrsun.ironchests.common.item;

import com.mrsun.ironchests.common.Util;
import com.mrsun.ironchests.common.block.IronChestsTypes;

import static com.mrsun.ironchests.common.block.IronChestsTypes.COPPER;
import static com.mrsun.ironchests.common.block.IronChestsTypes.CRYSTAL;
import static com.mrsun.ironchests.common.block.IronChestsTypes.DIAMOND;
import static com.mrsun.ironchests.common.block.IronChestsTypes.GOLD;
import static com.mrsun.ironchests.common.block.IronChestsTypes.IRON;
import static com.mrsun.ironchests.common.block.IronChestsTypes.OBSIDIAN;
import static com.mrsun.ironchests.common.block.IronChestsTypes.WOOD;

public enum IronChestsUpgradeType {

  WOOD_TO_COPPER(WOOD, COPPER),
  WOOD_TO_IRON(WOOD, IRON),
  COPPER_TO_IRON(COPPER, IRON),
  IRON_TO_GOLD(IRON, GOLD),
  GOLD_TO_DIAMOND(GOLD, DIAMOND),
  DIAMOND_TO_CRYSTAL(DIAMOND, CRYSTAL),
  DIAMOND_TO_OBSIDIAN(DIAMOND, OBSIDIAN);

  public final String name;
  public final IronChestsTypes source;
  public final IronChestsTypes target;

  IronChestsUpgradeType(IronChestsTypes source, IronChestsTypes target) {
    this.name = Util.toEnglishName(this.name());
    this.source = source;
    this.target = target;
  }

  public boolean canUpgrade(IronChestsTypes from) {
    return from == this.source;
  }
}

