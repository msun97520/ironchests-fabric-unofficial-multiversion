package com.mrsun.ironchests;

import com.mrsun.ironchests.common.block.IronChestsBlocks;
import com.mrsun.ironchests.common.block.regular.entity.IronChestsBlockEntityTypes;
import com.mrsun.ironchests.common.creativetabs.IronChestsCreativeTab;
import com.mrsun.ironchests.common.inventory.IronChestsMenuTypes;
import com.mrsun.ironchests.common.item.ChestUpgradeItem;
import com.mrsun.ironchests.common.item.IronChestsItems;
import com.mrsun.ironchests.common.network.IronChestsNetwork;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;

public class IronChestsFabric implements ModInitializer {

  public static final String MOD_ID = "ironchest-mrsun-unofficial";

  public static ResourceLocation id(String path) {
    //? if >= 1.21 {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    //?} else {
    /*return new ResourceLocation(MOD_ID, path);*///?}
  }

  @Override
  public void onInitialize() {
    IronChestsBlocks.init();
    IronChestsItems.init();
    IronChestsBlockEntityTypes.init();
    IronChestsMenuTypes.init();
    IronChestsCreativeTab.init();
    IronChestsNetwork.initCommon();

    // Hook in-place chest upgrade via UseBlockCallback
    UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
      ItemStack held = player.getItemInHand(hand);
      if (held.getItem() instanceof ChestUpgradeItem) {
        ChestUpgradeItem upgradeItem = (ChestUpgradeItem) held.getItem();
        InteractionResult result = upgradeItem.tryUpgradeChest(world, hitResult.getBlockPos(), player, held);
        if (result != InteractionResult.PASS) {
          return result;
        }
      }
      return InteractionResult.PASS;
    });
  }
}

