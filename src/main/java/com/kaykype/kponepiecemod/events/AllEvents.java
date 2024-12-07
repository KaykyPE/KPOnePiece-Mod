package com.kaykype.kponepiecemod.events;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.CapManager;
import com.kaykype.kponepiecemod.capabilities.ModPacketHandler;
import com.kaykype.kponepiecemod.capabilities.ModSetup;
import com.kaykype.kponepiecemod.capabilities.Packet;
import com.kaykype.kponepiecemod.client.races.RaceMetods;
import com.kaykype.kponepiecemod.utils.AttributesManagement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class AllEvents {
    @SubscribeEvent
    public static void PlayerEatAnytime(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();

        if (!player.level.isClientSide) {
            if (event.getItemStack().getItem().isEdible()) {
                event.setCanceled(true);
                player.startUsingItem(Hand.MAIN_HAND);
                event.setCancellationResult(ActionResultType.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    void attackEvent(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() != null) {
            if (!(event.getSource().getEntity() instanceof PlayerEntity) && (event.getEntity() instanceof PlayerEntity)) {
                ((PlayerEntity) event.getEntity()).getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                    if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length == 0) return;

                    int dmg = (int) event.getAmount();
                    event.setAmount(0);
                    int life = playerStats.getLife() - AttributesManagement.damageWithResistence((PlayerEntity) event.getEntity(), dmg, playerStats.getDex());

                    if ((playerStats.getLife() - dmg) <= 0) {
                        ((PlayerEntity) event.getEntity()).setHealth(0);
                        life = AttributesManagement.getMaxLife(playerStats.getCon(), playerStats.getRace());
                    }
                    ModPacketHandler.sendToServer(new Packet(playerStats.getTp(), playerStats.getStr(), playerStats.getCon(), playerStats.getDex(), playerStats.getSpi(), life, playerStats.getEnergy(), playerStats.getStamina(), playerStats.getRace(), playerStats.getCargo()));
                });
            }

            if (!(event.getEntity() instanceof PlayerEntity) && (event.getSource().getEntity() instanceof PlayerEntity)) {
                event.getSource().getEntity().getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                    if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length == 0) return;
                    int dmg = (int) event.getAmount() + AttributesManagement.totalStrength(playerStats.getStr(), playerStats.getRace());
                    event.setAmount(dmg);
                });
            }
        }
    }

    private static final ThreadLocal<Boolean> didPushMatrix = ThreadLocal.withInitial(() -> false);

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        PlayerEntity player = event.getPlayer();
        player.getCapability(STATS).ifPresent(playerStats -> {
            if (Arrays.stream(new RaceMetods().listAll())
                    .filter(key -> key.equals(playerStats.getRace()))
                    .toArray(String[]::new).length > 0) {
                if (player != null && "giantRace".equals(playerStats.getRace())) {
                    event.getMatrixStack().pushPose();
                    event.getMatrixStack().scale((float) (player.getBoundingBox().getYsize() * 1.1), (float) (player.getBoundingBox().getYsize() * 1.1), (float) (player.getBoundingBox().getYsize() * 1.1));
                    didPushMatrix.set(true);
                }
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        if (!player.level.isClientSide) {
            player.getCapability(STATS).ifPresent(playerStats -> {
                if ("giantRace".equals(playerStats.getRace())) {
                    double scaleFactor = 1.1;
                    AxisAlignedBB newBoundingBox = new AxisAlignedBB(
                            player.position().x - 0.5 * scaleFactor,
                            player.position().y,
                            player.position().z - 0.5 * scaleFactor,
                            player.position().x + 0.5 * scaleFactor,
                            player.position().y + player.getBbHeight() * scaleFactor,
                            player.position().z + 0.5 * scaleFactor
                    );
                    if (!player.getBoundingBox().equals(newBoundingBox)) {
                        player.setBoundingBox(newBoundingBox);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        if (didPushMatrix.get()) {
            event.getMatrixStack().popPose();
            didPushMatrix.set(false);
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
