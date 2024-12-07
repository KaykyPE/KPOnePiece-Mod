package com.kaykype.kponepiecemod.events;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.*;
import com.kaykype.kponepiecemod.client.gui.GuiCreateCharacter;
import com.kaykype.kponepiecemod.client.gui.GuiStats;
import com.kaykype.kponepiecemod.client.races.RaceMetods;
import com.kaykype.kponepiecemod.proxy.ClientProxy;
import com.kaykype.kponepiecemod.utils.AttributesManagement;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import javafx.geometry.Side;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.function.Function;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;
import static com.kaykype.kponepiecemod.capabilities.PlayerStats.*;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PlayerEvents {
    @SubscribeEvent
    public static void onEvent(InputEvent.KeyInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;

        KeyBinding statsButton = ClientProxy.STATS_BUTTON;

        if (statsButton.isDown()) {
            player.getCapability(STATS).ifPresent(playerStats -> Minecraft.getInstance().setScreen(Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length > 0 ? new GuiStats(player) : new GuiCreateCharacter(player)));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerConsume(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (!event.getItem().isEdible()) return;
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (event.getItem().getItem().getFoodProperties().getNutrition() > 0) {
                player.getCapability(STATS).ifPresent(playerStats -> {
                    if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length == 0)
                        return;

                    int newLife = (int) Math.min(
                            playerStats.getLife() + (AttributesManagement.getMaxLife(playerStats.getCon(), playerStats.getRace()) * 0.30),
                            AttributesManagement.getMaxLife(playerStats.getCon(), playerStats.getRace())
                    );
                    int newEnergy = (int) Math.min(
                            playerStats.getEnergy() + (AttributesManagement.getMaxEnergy(playerStats.getSpi(), playerStats.getRace()) * 0.20),
                            AttributesManagement.getMaxEnergy(playerStats.getSpi(), playerStats.getRace())
                    );
                    int newStamina = (int) Math.min(
                            playerStats.getStamina() + (AttributesManagement.getMaxStamina(playerStats.getCon(), playerStats.getRace()) * 0.25),
                            AttributesManagement.getMaxStamina(playerStats.getCon(), playerStats.getRace())
                    );

                    ModPacketHandler.sendToServer(new Packet(
                            playerStats.getTp(),
                            playerStats.getStr(),
                            playerStats.getCon(),
                            playerStats.getDex(),
                            playerStats.getSpi(),
                            newLife,
                            newEnergy,
                            newStamina,
                            playerStats.getRace(),
                            playerStats.getCargo()
                    ));
                });
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void disableHealthBar(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTH) return;
        Minecraft.getInstance().player.getCapability(STATS).ifPresent(playerStats -> {
            if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length > 0) {
                event.setCanceled(true);
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void healthBar(RenderGameOverlayEvent.Post event) {
        PlayerEntity player = Minecraft.getInstance().player;

        player.getCapability(STATS).ifPresent(playerStats -> {
            if (Arrays.stream(Arrays.stream(new RaceMetods().listAll()).toArray(String[]::new)).filter(key -> key.equals(playerStats.getRace())).toArray(String[]::new).length == 0)
                return;

            Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(Reference.MODID + ":textures/gui/icons.png"));
            GuiUtils.drawTexturedModalRect(10, 10, 0, 0, 230, 41, 0);

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