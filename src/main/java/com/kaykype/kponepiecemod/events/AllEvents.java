package com.kaykype.kponepiecemod.events;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.CapManager;
import com.kaykype.kponepiecemod.network.ModPacketHandler;
import com.kaykype.kponepiecemod.capabilities.ModSetup;
import com.kaykype.kponepiecemod.network.PacketClient;
import com.kaykype.kponepiecemod.client.races.RaceMetods;
import com.kaykype.kponepiecemod.utils.AttributesManagement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class AllEvents {
    @SubscribeEvent
    void attackEvent(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() != null) {
            if (!(event.getSource().getEntity() instanceof PlayerEntity) && (event.getEntity() instanceof PlayerEntity)) {
                event.getEntity().getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                    if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length == 0)
                        return;

                    int dmg = (int) event.getAmount();
                    event.setAmount(0);
                    playerStats.setLife(playerStats.getLife() - AttributesManagement.damageWithResistence((PlayerEntity) event.getEntity(), dmg, playerStats.getDex()));

                    if ((playerStats.getLife() - dmg) <= 0) {
                        ((PlayerEntity) event.getEntity()).setHealth(0);
                        playerStats.setLife(AttributesManagement.getMaxLife(playerStats.getCon(), playerStats.getRace()));
                    }
                    ModPacketHandler.sendToServer(new PacketClient(playerStats));
                });
            }

            if (!(event.getEntity() instanceof PlayerEntity) && (event.getSource().getEntity() instanceof PlayerEntity)) {
                event.getSource().getEntity().getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                    //if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length == 0)
                    //    return;
                    int dmg = (int) event.getAmount() + AttributesManagement.totalStrength(playerStats.getStr(), playerStats.getRace());
                    event.setAmount(dmg);
                });
            }
        }
    }

    @SubscribeEvent
    void AttachCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            CapManager provider = new CapManager();
            event.addCapability(new ResourceLocation(Reference.MODID, "player_attributes"), provider);
        }
    }
}
