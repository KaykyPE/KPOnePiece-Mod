package com.kaykype.kponepiecemod.client.items;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.client.items.fruits.gomugomu;
import com.kaykype.kponepiecemod.client.tabs.fruitsTab;
import com.kaykype.kponepiecemod.events.AllEvents;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class modItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    public static final RegistryObject<Item> GOMU_GOMU = ITEMS.register("gomu_gomu_no_mi", gomugomu::new);

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
