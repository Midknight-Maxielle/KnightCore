package com.midknight.knightcore.util;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;

public class KnightcoreTab {

    public static final CreativeModeTab KNIGHTCORE_TAB = new CreativeModeTab("knightcoreTab") {

        @Override @Nonnull
        public ItemStack makeIcon() {
            return new ItemStack(Items.WRITABLE_BOOK.asItem());
        }
    };
}
