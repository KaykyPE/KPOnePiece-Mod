package com.kaykype.kponepiecemod.events;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.client.gui.GuiCreateCharacter;
import com.kaykype.kponepiecemod.client.gui.GuiStats;
import com.kaykype.kponepiecemod.client.races.RaceMetods;
import com.kaykype.kponepiecemod.network.ModPacketHandler;
import com.kaykype.kponepiecemod.network.PacketClient;
import com.kaykype.kponepiecemod.proxy.ClientProxy;
import com.kaykype.kponepiecemod.utils.AttributesManagement;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;
import static net.minecraft.client.gui.AbstractGui.blit;
import static net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class PlayerEvents {
    @SubscribeEvent
    public static void onEvent(InputEvent.KeyInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;

        KeyBinding statsButton = ClientProxy.STATS_BUTTON;

        if (statsButton.isDown()) {
            player.getCapability(STATS).ifPresent(playerStats -> Minecraft.getInstance().setScreen(Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length > 0 ? new GuiStats(player) : new GuiCreateCharacter(player)));
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
    public static void onPlayerConsume(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (!event.getItem().isEdible()) return;
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (event.getItem().getItem().getFoodProperties().getNutrition() > 0) {
                if ((event.getItem().getItem().getRegistryName().getNamespace() + ":" + event.getItem().getItem().getRegistryName().getPath()).equals(Reference.MODID + ":gomu_gomu_no_mi"))
                    return;

                player.getCapability(STATS).ifPresent(playerStats -> {
                    if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length == 0)
                        return;

                    playerStats.setLife((int) Math.min(
                            playerStats.getLife() + (AttributesManagement.getMaxLife(playerStats.getCon(), playerStats.getRace()) * 0.30),
                            AttributesManagement.getMaxLife(playerStats.getCon(), playerStats.getRace())
                    ));
                    playerStats.setEnergy((int) Math.min(
                            playerStats.getEnergy() + (AttributesManagement.getMaxEnergy(playerStats.getSpi(), playerStats.getRace()) * 0.20),
                            AttributesManagement.getMaxEnergy(playerStats.getSpi(), playerStats.getRace())
                    ));
                    playerStats.setStamina((int) Math.min(
                            playerStats.getStamina() + (AttributesManagement.getMaxStamina(playerStats.getCon(), playerStats.getRace()) * 0.25),
                            AttributesManagement.getMaxStamina(playerStats.getCon(), playerStats.getRace())
                    ));

                    ModPacketHandler.sendToServer(new PacketClient(playerStats));
                });
            }
        }
    }

    @SubscribeEvent
    public static void disableHealthBar(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTH) return;
        Minecraft.getInstance().player.getCapability(STATS).ifPresent(playerStats -> {
            if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length > 0) {
                event.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (event.getSound() != null
                && event.getSound().getLocation().equals(new ResourceLocation("minecraft", "item.armor.equip_generic"))
                && (Minecraft.getInstance().screen instanceof GuiStats || Minecraft.getInstance().screen instanceof GuiCreateCharacter)) {
            event.setResultSound(SimpleSound.forUI(SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F));
        }
    }

    @SubscribeEvent
    public static void healthBar(RenderGameOverlayEvent.Post event) {
        PlayerEntity player = Minecraft.getInstance().player;

        player.getCapability(STATS).ifPresent(playerStats -> {
            if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length == 0)
                return;

            Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(Reference.MODID + ":textures/gui/icons.png"));
            drawTexturedModalRect(10, 10, 0, 0, 230, 41, 0);

            int xPos = (9 + 40);

            int HealthBarWidth = 185;
            int HealthBarHeigth = 12;
            int yPos = (9 + 7);

            float healthPercent = (float) playerStats.getLife() / AttributesManagement.getMaxLife(playerStats.getCon(), playerStats.getRace());

            int EnergyBarWidth = 143;
            int EnergyBarHeigth = 10;
            int yEnergyPos = (9 + 20);

            float energyPercent = (float) playerStats.getEnergy() / AttributesManagement.getMaxEnergy(playerStats.getSpi(), playerStats.getRace());

            int StaminaBarWidth = 72;
            int StaminaBarHeigth = 5;
            int yStaminaPos = (9 + 31);

            float staminaPercent = (float) playerStats.getStamina() / AttributesManagement.getMaxStamina(playerStats.getStamina(), playerStats.getRace());

            FontRenderer fontRenderer = Minecraft.getInstance().font;

            RenderSystem.disableTexture();
            AbstractGui.fill(event.getMatrixStack(), xPos, yPos, xPos + (int) (HealthBarWidth * healthPercent), yPos + HealthBarHeigth, 0xFFFF5555);
            AbstractGui.fill(event.getMatrixStack(), xPos, yEnergyPos, xPos + (int) (EnergyBarWidth * energyPercent), yEnergyPos + EnergyBarHeigth, 0xFF4287F5);
            AbstractGui.fill(event.getMatrixStack(), xPos, yStaminaPos, xPos + (int) (StaminaBarWidth * staminaPercent), yStaminaPos + StaminaBarHeigth, 0xFFFFF700);
            RenderSystem.enableTexture();

            fontRenderer.drawShadow(event.getMatrixStack(), playerStats.getLife() + " / " + AttributesManagement.getMaxLife(playerStats.getCon(), playerStats.getRace()), xPos + 2, yPos + 2, 0xFFFFFF);
            fontRenderer.drawShadow(event.getMatrixStack(), playerStats.getEnergy() + " / " + AttributesManagement.getMaxEnergy(playerStats.getSpi(), playerStats.getRace()), xPos + 2, yEnergyPos + 1, 0xFFFFFF);

            ResourceLocation playerUITexture = new ResourceLocation("minecraft:textures/gui/icons.png");
            Minecraft.getInstance().getTextureManager().bind(playerUITexture);
        });
    }
}