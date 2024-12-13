package com.kaykype.kponepiecemod.events;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.*;
import com.kaykype.kponepiecemod.network.ModPacketHandler;
import com.kaykype.kponepiecemod.network.PacketServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ServerEvents {
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getPlayer().getCommandSenderWorld().isClientSide) return;
        event.getPlayer().getCapability(ModSetup.STATS).ifPresent(stats -> {
            ModPacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new PacketServer(event.getPlayer().getUUID().toString(), stats));
        });
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getPlayer().getCommandSenderWorld().isClientSide) return;
        event.getPlayer().getCapability(ModSetup.STATS).ifPresent(stats -> {
            ModPacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new PacketServer(event.getPlayer().getUUID().toString(), stats));
        });
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(EntityJoinWorldEvent event) {
        if (event.getEntity().getCommandSenderWorld().isClientSide) return;
        if(!(event.getEntity() instanceof PlayerEntity)) return;

        ((ServerPlayerEntity)event.getEntity()).getCapability(ModSetup.STATS).ifPresent(stats -> {
            ModPacketHandler.sendToPlayer((ServerPlayerEntity) event.getEntity(), new PacketServer(event.getEntity().getUUID().toString(), stats));
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
                newStats.setRace(oldStats.getRace());
                newStats.setCargo(oldStats.getCargo());
                newStats.setFruta(oldStats.getFruta());
            });
        });
    }
}

