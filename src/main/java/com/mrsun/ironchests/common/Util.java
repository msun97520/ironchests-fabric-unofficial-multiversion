package com.mrsun.ironchests.common;

import com.mrsun.ironchests.IronChestsFabric;
//? if >= 1.20.5 {
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.component.WrittenBookContent;
//?} else {
/*import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;*///?}
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

  public static final String BOOK_AUTHOR = "cpw";

  public static String toEnglishName(String internalName) {
    return Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_"))
      .map(StringUtils::capitalize)
      .collect(Collectors.joining(" "));
  }

  public static Component translatable(String key) {
    //? if >= 1.19 {
    return Component.translatable(key);
    //?} else {
    /*return new net.minecraft.network.chat.TranslatableComponent(key);
    *///?}
  }

  public static ItemStack createDirtGuideBook() {
    ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
    //? if >= 1.20.5 {
    Function<Integer, Filterable<Component>> pageGenerationFunc = index ->
      Filterable.passThrough(translatable(IronChestsFabric.MOD_ID + ".book.dirtchest9000." + (index + 1)));

    List<Filterable<Component>> list = Stream.iterate(0, index -> index + 1)
      .limit(5)
      .map(pageGenerationFunc)
      .toList();

    book.set(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(
      Filterable.passThrough("How to use your DirtChest 9000!"),
      BOOK_AUTHOR,
      3,
      list,
      true
    ));
    book.set(DataComponents.ITEM_NAME, translatable(IronChestsFabric.MOD_ID + ".book.dirtchest9000"));
    //?} else {
    /*ListTag pages = new ListTag();
    for (int i = 1; i <= 5; i++) {
      pages.add(StringTag.valueOf(Component.Serializer.toJson(translatable(IronChestsFabric.MOD_ID + ".book.dirtchest9000." + i))));
    }
    book.addTagElement("pages", pages);
    book.addTagElement("generation", IntTag.valueOf(3));
    book.addTagElement("author", StringTag.valueOf(BOOK_AUTHOR));
    book.addTagElement("title", StringTag.valueOf("How to use your DirtChest 9000!"));*///?}
    return book;
  }
}

