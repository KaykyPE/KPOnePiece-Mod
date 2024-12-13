package com.kaykype.kponepiecemod.client.items.fruits;

import com.kaykype.kponepiecemod.client.items.foodItems;
import com.kaykype.kponepiecemod.client.tabs.fruitsTab;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class gomugomu extends foodItems {
    public gomugomu() {
        super(
                new Food.Builder()
                        .nutrition(0)
                        .saturationMod(0F)
                        .build(),
                new Item.Properties().tab(fruitsTab.FRUITS_TAB)
        );
    }
}
