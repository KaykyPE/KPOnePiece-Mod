package com.kaykype.kponepiecemod.client.tabs;

import com.kaykype.kponepiecemod.client.items.modItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class fruitsTab {
    public static final ItemGroup FRUITS_TAB = new ItemGroup("fruits_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(modItems.GOMU_GOMU.get());
        }
    };
}
