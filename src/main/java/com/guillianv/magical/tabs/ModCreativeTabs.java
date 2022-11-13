package com.guillianv.magical.tabs;

import com.guillianv.magical.items.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {


    public static final CreativeModeTab MAGICAL_TAB = new CreativeModeTab("magical_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.WAND_NORMAL.get());
        }
    };

}
