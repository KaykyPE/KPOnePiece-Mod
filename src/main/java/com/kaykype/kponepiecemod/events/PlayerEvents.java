package com.kaykype.kponepiecemod.events;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.*;
import com.kaykype.kponepiecemod.client.gui.GuiStats;
import com.kaykype.kponepiecemod.proxy.ClientProxy;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javafx.geometry.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.MixinEnvironment;

import static com.kaykype.kponepiecemod.KPOnePieceMod.LOGGER;
import static com.kaykype.kponepiecemod.capabilities.PlayerStats.*;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PlayerEvents {
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(InputEvent.KeyInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;

        System.out.println("Key Input Event");

        KeyBinding hello = ClientProxy.HELLO_KEY;

        if (hello.isDown()) {
            Minecraft.getInstance().setScreen(new GuiStats(player));

            Minecraft mc = Minecraft.getInstance();
        }
    }

    /*
    @SubscribeEvent
    public void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (event.getAmount() > 0) {
                PlayerStats.life -= event.getAmount();
                event.setAmount(0.5f);
            }
        }
    }
    */

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void disableHealthBar(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent e) {
        e.player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
            e.player.sendMessage(new StringTextComponent(playerStats.getStr()+" Cliente"), e.player.getUUID());
        });
    }

    @SubscribeEvent
    public static void healthBar(RenderGameOverlayEvent.Post event) {
        Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(Reference.MODID + ":textures/gui/icons.png"));

        PlayerEntity player = Minecraft.getInstance().player;

        player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
            //int HealthBarWidth = (int) ((playerStats.getLife() / (playerStats.getCon() * 10)) * 185);
            //int ChakraBarWidth = (int) ((playerStats.getEnergy() / (playerStats.getSpi() * 10)) * 143);
            //int StaminaBarWidth = (int) ((playerStats.getStamina() / (playerStats.getCon() * 5)) * 74);

            GuiUtils.drawTexturedModalRect(10, 10, 0, 0, 230, 41, 0);

            Minecraft mc = Minecraft.getInstance();

            int xPos = (9 + 40);

            int HealthBarWidth = 185;
            int HealthBarHeigth = 12;
            int yPos = (9 + 7);

            float healthPercent = playerStats.getLife() / (playerStats.getCon() * 10);

            int EnergyBarWidth = 143;
            int EnergyBarHeigth = 10;
            int yEnergyPos = (9 + 20);

            float energyPercent = playerStats.getEnergy() / (playerStats.getSpi() * 10);

            int StaminaBarWidth = 72;
            int StaminaBarHeigth = 5;
            int yStaminaPos = (9 + 31);

            float staminaPercent = playerStats.getStamina() / (playerStats.getCon() * 5);

            RenderSystem.disableTexture();
            AbstractGui.fill(event.getMatrixStack(), xPos, yPos, xPos + (int) (HealthBarWidth * healthPercent), yPos + HealthBarHeigth, 0xFFFF5555); // Vermelho
            AbstractGui.fill(event.getMatrixStack(), xPos, yEnergyPos, xPos + (int) (EnergyBarWidth * energyPercent), yEnergyPos + EnergyBarHeigth, 0xFF4287F5); // Azul
            AbstractGui.fill(event.getMatrixStack(), xPos, yStaminaPos, xPos + (int) (StaminaBarWidth * staminaPercent), yStaminaPos + StaminaBarHeigth, 0xFFFFF700); // Amarelo
            RenderSystem.enableTexture();

            ResourceLocation playerUITexture = new ResourceLocation("minecraft:textures/gui/icons.png");
            Minecraft.getInstance().getTextureManager().bind(playerUITexture);
        });
    }
}