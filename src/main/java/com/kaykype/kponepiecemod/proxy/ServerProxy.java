package com.kaykype.kponepiecemod.proxy;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.ModPacketHandler;
import com.kaykype.kponepiecemod.capabilities.ModSetup;
import com.kaykype.kponepiecemod.capabilities.Packet;
import com.kaykype.kponepiecemod.events.ServerEvents;
import net.minecraft.advancements.criterion.EntityHurtPlayerTrigger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

/**
 * For all things on the Server side
 * 
 * @author CJMinecraft
 *
 */

public class ServerProxy {

	public ServerProxy() {
		IEventBus modEventBus = MinecraftForge.EVENT_BUS;

		modEventBus.addListener(this::onServerStopping);
		modEventBus.addListener(this::init);
	}

	private void init(FMLCommonSetupEvent event) {
	}

	private void onServerStopping(FMLServerStoppingEvent event) {

	}

	public static void guiAddStr (PlayerEntity player) {

	}
}
