package com.kaykype.kponepiecemod.events;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.*;
import com.kaykype.kponepiecemod.client.gui.GuiStats;
import com.kaykype.kponepiecemod.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaykype.kponepiecemod.KPOnePieceMod.LOGGER;
import static com.kaykype.kponepiecemod.capabilities.PlayerStats.*;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ServerEvents {
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getPlayer().getCommandSenderWorld().isClientSide) return;
        event.getPlayer().getCapability(ModSetup.STATS).ifPresent(stats -> {
            LOGGER.info("PlayerChangedDimensionEvent: {}", stats.getStr());
            ModPacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new Packet(stats.getTp(), stats.getStr(), stats.getCon(), stats.getDex(), stats.getSpi(), stats.getLife(), stats.getEnergy(), stats.getStamina()));
        });
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getPlayer().getCommandSenderWorld().isClientSide) return;
        event.getPlayer().getCapability(ModSetup.STATS).ifPresent(stats -> {
            LOGGER.info("PlayerRespawnEvent: {}", stats.getStr());
            ModPacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new Packet(stats.getTp(), stats.getStr(), stats.getCon(), stats.getDex(), stats.getSpi(), stats.getLife(), stats.getEnergy(), stats.getStamina()));
        });
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer().getCommandSenderWorld().isClientSide) return;

        event.getPlayer().getCapability(ModSetup.STATS).ifPresent(stats -> {
            LOGGER.info("PlayerLoggedInEvent: {}", stats.getStr());
            ModPacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new Packet(stats.getTp(), stats.getStr(), stats.getCon(), stats.getDex(), stats.getSpi(), stats.getLife(), stats.getEnergy(), stats.getStamina()));
        });
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        event.getOriginal().getCapability(ModSetup.STATS).ifPresent(oldStats -> {
            event.getPlayer().getCapability(ModSetup.STATS).ifPresent(newStats -> {
                newStats.setStr(oldStats.getStr());
                newStats.setTp(oldStats.getTp());
                newStats.setCon(oldStats.getCon());
                newStats.setDex(oldStats.getDex());
                newStats.setSpi(oldStats.getSpi());
                newStats.setLife(oldStats.getLife());
                newStats.setEnergy(oldStats.getEnergy());
                newStats.setStamina(oldStats.getStamina());
            });
        });
    }
}

