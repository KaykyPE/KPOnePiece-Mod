package com.kaykype.kponepiecemod;

import com.kaykype.kponepiecemod.capabilities.CapManager;
import com.kaykype.kponepiecemod.capabilities.ModPacketHandler;
import com.kaykype.kponepiecemod.capabilities.ModSetup;
import com.kaykype.kponepiecemod.events.PlayerEvents;
import com.kaykype.kponepiecemod.events.ServerEvents;
import com.kaykype.kponepiecemod.handlers.eventHandler;
import com.kaykype.kponepiecemod.proxy.ClientProxy;
import com.kaykype.kponepiecemod.proxy.ServerProxy;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MODID)
@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KPOnePieceMod {
    public static final Logger LOGGER = LogManager.getLogger();

    public KPOnePieceMod() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::setupCommon);
        modEventBus.addListener(this::setupDedicatedServer);

        MinecraftForge.EVENT_BUS.register(new ServerEvents());
    }

    private void setupClient(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        new ClientProxy();
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        ModPacketHandler.register();
        ModSetup.reg();
    }

    private void setupDedicatedServer(final FMLDedicatedServerSetupEvent event) {
        new ServerProxy();
    }
}