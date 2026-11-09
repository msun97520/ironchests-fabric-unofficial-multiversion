package com.mrsun.ironchests.common.block.regular.entity;

import com.mrsun.ironchests.common.block.IronChestsBlocks;
import com.mrsun.ironchests.common.block.IronChestsTypes;
import com.mrsun.ironchests.common.registry.IronChestsRegistries;
//? if >= 1.17 {
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
//?}
import net.minecraft.world.level.block.entity.BlockEntityType;

public class IronChestsBlockEntityTypes {

  //? if >= 1.17 {
  public static final BlockEntityType<StandardIronChestBlockEntity> COPPER_CHEST = IronChestsRegistries.registerBlockEntity(
    "copper_chest",
    FabricBlockEntityTypeBuilder.create((pos, state) -> new StandardIronChestBlockEntity(pos, state, IronChestsTypes.COPPER),
      IronChestsBlocks.COPPER_CHEST, IronChestsBlocks.TRAPPED_COPPER_CHEST).build()
  );

  public static final BlockEntityType<StandardIronChestBlockEntity> IRON_CHEST = IronChestsRegistries.registerBlockEntity(
    "iron_chest",
    FabricBlockEntityTypeBuilder.create((pos, state) -> new StandardIronChestBlockEntity(pos, state, IronChestsTypes.IRON),
      IronChestsBlocks.IRON_CHEST, IronChestsBlocks.TRAPPED_IRON_CHEST).build()
  );

  public static final BlockEntityType<StandardIronChestBlockEntity> GOLD_CHEST = IronChestsRegistries.registerBlockEntity(
    "gold_chest",
    FabricBlockEntityTypeBuilder.create((pos, state) -> new StandardIronChestBlockEntity(pos, state, IronChestsTypes.GOLD),
      IronChestsBlocks.GOLD_CHEST, IronChestsBlocks.TRAPPED_GOLD_CHEST).build()
  );

  public static final BlockEntityType<StandardIronChestBlockEntity> DIAMOND_CHEST = IronChestsRegistries.registerBlockEntity(
    "diamond_chest",
    FabricBlockEntityTypeBuilder.create((pos, state) -> new StandardIronChestBlockEntity(pos, state, IronChestsTypes.DIAMOND),
      IronChestsBlocks.DIAMOND_CHEST, IronChestsBlocks.TRAPPED_DIAMOND_CHEST).build()
  );

  public static final BlockEntityType<CrystalChestBlockEntity> CRYSTAL_CHEST = IronChestsRegistries.registerBlockEntity(
    "crystal_chest",
    FabricBlockEntityTypeBuilder.create(CrystalChestBlockEntity::new,
      IronChestsBlocks.CRYSTAL_CHEST, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST).build()
  );

  public static final BlockEntityType<StandardIronChestBlockEntity> OBSIDIAN_CHEST = IronChestsRegistries.registerBlockEntity(
    "obsidian_chest",
    FabricBlockEntityTypeBuilder.create((pos, state) -> new StandardIronChestBlockEntity(pos, state, IronChestsTypes.OBSIDIAN),
      IronChestsBlocks.OBSIDIAN_CHEST, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST).build()
  );

  public static final BlockEntityType<DirtChestBlockEntity> DIRT_CHEST = IronChestsRegistries.registerBlockEntity(
    "dirt_chest",
    FabricBlockEntityTypeBuilder.create(DirtChestBlockEntity::new,
      IronChestsBlocks.DIRT_CHEST, IronChestsBlocks.TRAPPED_DIRT_CHEST).build()
  );
  //?} else {
  /*// [2026-11-08 13:35] [CẬP NHẬT / SỬA LỖI]: Bỏ đăng ký Copper Chest Block Entity trên 1.16.5
  public static final BlockEntityType<StandardIronChestBlockEntity> IRON_CHEST = IronChestsRegistries.registerBlockEntity(
    "iron_chest",
    BlockEntityType.Builder.of(() -> new StandardIronChestBlockEntity(IronChestsTypes.IRON),
      IronChestsBlocks.IRON_CHEST, IronChestsBlocks.TRAPPED_IRON_CHEST).build(null)
  );

  public static final BlockEntityType<StandardIronChestBlockEntity> GOLD_CHEST = IronChestsRegistries.registerBlockEntity(
    "gold_chest",
    BlockEntityType.Builder.of(() -> new StandardIronChestBlockEntity(IronChestsTypes.GOLD),
      IronChestsBlocks.GOLD_CHEST, IronChestsBlocks.TRAPPED_GOLD_CHEST).build(null)
  );

  public static final BlockEntityType<StandardIronChestBlockEntity> DIAMOND_CHEST = IronChestsRegistries.registerBlockEntity(
    "diamond_chest",
    BlockEntityType.Builder.of(() -> new StandardIronChestBlockEntity(IronChestsTypes.DIAMOND),
      IronChestsBlocks.DIAMOND_CHEST, IronChestsBlocks.TRAPPED_DIAMOND_CHEST).build(null)
  );

  public static final BlockEntityType<CrystalChestBlockEntity> CRYSTAL_CHEST = IronChestsRegistries.registerBlockEntity(
    "crystal_chest",
    BlockEntityType.Builder.of(CrystalChestBlockEntity::new,
      IronChestsBlocks.CRYSTAL_CHEST, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST).build(null)
  );

  public static final BlockEntityType<StandardIronChestBlockEntity> OBSIDIAN_CHEST = IronChestsRegistries.registerBlockEntity(
    "obsidian_chest",
    BlockEntityType.Builder.of(() -> new StandardIronChestBlockEntity(IronChestsTypes.OBSIDIAN),
      IronChestsBlocks.OBSIDIAN_CHEST, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST).build(null)
  );

  public static final BlockEntityType<DirtChestBlockEntity> DIRT_CHEST = IronChestsRegistries.registerBlockEntity(
    "dirt_chest",
    BlockEntityType.Builder.of(DirtChestBlockEntity::new,
      IronChestsBlocks.DIRT_CHEST, IronChestsBlocks.TRAPPED_DIRT_CHEST).build(null)
  );
  *///?}

  public static BlockEntityType<?> getTypeFor(IronChestsTypes type) {
    switch (type) {
      //? if >= 1.17 {
      case COPPER: return COPPER_CHEST;
      //?}
      case IRON: return IRON_CHEST;
      case GOLD: return GOLD_CHEST;
      case DIAMOND: return DIAMOND_CHEST;
      case CRYSTAL: return CRYSTAL_CHEST;
      case OBSIDIAN: return OBSIDIAN_CHEST;
      case DIRT: return DIRT_CHEST;
      default: return IRON_CHEST;
    }
  }

  public static void init() {
  }
}
