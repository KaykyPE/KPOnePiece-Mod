package com.kaykype.kponepiecemod;

import com.kaykype.kponepiecemod.network.ModPacketHandler;
import com.kaykype.kponepiecemod.capabilities.ModSetup;
import com.kaykype.kponepiecemod.network.PacketClient;
import com.kaykype.kponepiecemod.network.PacketServer;
import com.kaykype.kponepiecemod.client.items.modItems;
import com.kaykype.kponepiecemod.events.AllEvents;
import com.kaykype.kponepiecemod.events.PlayerEvents;
import com.kaykype.kponepiecemod.events.ServerEvents;
import com.kaykype.kponepiecemod.proxy.ClientProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MODID)
@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KPOnePieceMod {
    public static final Logger LOGGER = LogManager.getLogger();

    public KPOnePieceMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new AllEvents());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::setupCommon);
        modEventBus.addListener(this::setupDedicatedServer);

        modItems.register(modEventBus);
    }

    private void setupClient(final FMLClientSetupEvent event) {
        new ClientProxy();
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        ModSetup.reg();
        ModPacketHandler.register(PacketClient.class, PacketClient::encode, PacketClient::decode, PacketClient::handle);
        ModPacketHandler.register(PacketServer.class, PacketServer::encode, PacketServer::decode, PacketServer::handle);
    }

    private void setupDedicatedServer(final FMLDedicatedServerSetupEvent event) {
    }
}