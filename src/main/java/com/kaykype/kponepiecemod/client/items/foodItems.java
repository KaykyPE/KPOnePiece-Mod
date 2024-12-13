package com.kaykype.kponepiecemod.client.items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.FoodStats;

public class foodItems extends Item {
    public foodItems(Food foodProperties, Properties properties) {
        super(properties.food(foodProperties));
    }
}
