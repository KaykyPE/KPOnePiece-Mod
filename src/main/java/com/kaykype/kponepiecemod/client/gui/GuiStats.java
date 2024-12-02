package com.kaykype.kponepiecemod.client.gui;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.CapManager;
import com.kaykype.kponepiecemod.capabilities.ModPacketHandler;
import com.kaykype.kponepiecemod.capabilities.ModSetup;
import com.kaykype.kponepiecemod.capabilities.Packet;
import com.kaykype.kponepiecemod.client.gui.Buttons.addButton;
import com.kaykype.kponepiecemod.proxy.ServerProxy;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiStats extends Screen {
    private static final Minecraft mc = Minecraft.getInstance();

    String textTpLabel;
    String textTp;
    String textStr;
    String textDex;
    String textCon;
    String textSpi;

    private Button buttonStr;
    private Button buttonDex;
    private Button buttonCon;
    private Button buttonSpi;

    PlayerEntity player;

    float xStr;
    float xCon;
    float xDex;
    float xSpi;
    float xTp;

    public GuiStats(PlayerEntity p) {
        super(new StringTextComponent("Stats Menu"));
        this.player = p;
        player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
            this.textStr = "" + playerStats.getStr();
            this.textDex = "" + playerStats.getDex();
            this.textCon = "" + playerStats.getCon();
            this.textSpi = "" + playerStats.getSpi();
            this.textTpLabel = "Pontos";
            this.textTp = "" + playerStats.getTp();
        });
        this.xStr = 60.0F;
        this.xDex = 60.0F;
        this.xCon = 60.0F;
        this.xSpi = 60.0F;
        this.xTp = 0.0F;
    }

    @Override
    public void init() {
        buttons.clear();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(true);

        int offsetFromScreenLeft = (width) / 2;
        int offsetFromScreenTop = (height) / 2;

        buttons.add(buttonStr = new addButton(offsetFromScreenLeft - 46, offsetFromScreenTop - 70, 11, 11, "", button -> {
        }));

        buttons.add(buttonDex = new addButton(offsetFromScreenLeft - 46, offsetFromScreenTop + 10, 11, 11, "", button -> {
        }));

        buttons.add(buttonCon = new addButton(offsetFromScreenLeft + 46, offsetFromScreenTop - 70, 11, 11, "", button -> {
        }));

        buttons.add(buttonSpi = new addButton(offsetFromScreenLeft + 46, offsetFromScreenTop + 10, 11, 11, "", button -> {
        }));
        buttonStr.visible = true;

        buttonDex.visible = true;

        buttonCon.visible = true;

        buttonSpi.visible = true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (buttonStr.isMouseOver(mouseX, mouseY)) {
            player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                ModPacketHandler.sendToServer(new Packet(playerStats.getTp(), playerStats.getStr()+1, playerStats.getCon(), playerStats.getDex(), playerStats.getSpi(), playerStats.getLife(), playerStats.getEnergy(), playerStats.getStamina()));
            });

            return true;
        }
        if (buttonDex.isMouseOver(mouseX, mouseY)) {
            player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                playerStats.setDex(playerStats.getDex() + 1);
            });
            return true;
        }
        if (buttonCon.isMouseOver(mouseX, mouseY)) {
            player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                playerStats.setCon(playerStats.getCon() + 1);
            });
            return true;
        }
        if (buttonSpi.isMouseOver(mouseX, mouseY)) {
            player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                playerStats.setSpi(playerStats.getSpi() + 1);
            });
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void tick() {
        player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
            textStr = playerStats.getStr() + " STR";
            textDex = playerStats.getDex() + " DEX";
            textCon = "CON " + playerStats.getCon();
            textSpi = "SPI " + playerStats.getSpi();
            textTp = "" + playerStats.getTp();

            xStr = 49 + mc.font.width(textStr);
            xDex = 49 + mc.font.width(textDex);
            int offsetFromScreenLeft = (width) / 2;
            xTp = offsetFromScreenLeft + (mc.font.width(textTp) / 2);
        });
    }

    /**
     * Draws the screen and all the components in it.
     */

    @Override
    public void render(MatrixStack matrixStack, int parWidth, int parHeight, float p_73863_3_) {
        int offsetFromScreenLeft = (width) / 2;
        int offsetFromScreenTop = (height) / 2;

        mc.getTextureManager().bind(new ResourceLocation(Reference.MODID, "textures/gui/container/menu1.png"));

        blit(matrixStack, offsetFromScreenLeft - (243/2), offsetFromScreenTop - (169/2), 0, 0, 243, 169);

        font.drawShadow(matrixStack, textStr,
                (offsetFromScreenLeft - xStr),
                offsetFromScreenTop - 69, 0xFFFFFF);
        font.drawShadow(matrixStack, textDex,
                (offsetFromScreenLeft - xDex),
                offsetFromScreenTop + 12, 0xFFFFFF);
        font.drawShadow(matrixStack, textCon,
                (offsetFromScreenLeft + xCon),
                (offsetFromScreenTop - 69), 0xFFFFFF);
        font.drawShadow(matrixStack, textSpi,
                (offsetFromScreenLeft + xSpi),
                offsetFromScreenTop + 12, 0xFFFFFF);
        font.drawShadow(matrixStack, textTpLabel,
                (offsetFromScreenLeft - 12),
                (offsetFromScreenTop - 125), 0xFFFFFF);
        font.drawShadow(matrixStack, textTp,
                xTp,
                (offsetFromScreenTop - 110), 0xFFFFFF);
        /*
        GlStateManager.translate(offsetFromScreenLeft+128, offsetFromScreenTop+20, 10);
        RenderPlayer playerModel = new RenderPlayer(Minecraft.getMinecraft().getRenderManager());
        playerModel.bindTexture(((EntityPlayerSP) player).getLocationSkin());
        playerModel.getMainModel().render(player, 0, 0, 0, 0, 0, 6f);
        */

        /*
        int l = offsetFromScreenLeft;
        int i1 = offsetFromScreenTop;

        Minecraft mc = Minecraft.getInstance();

        float f1 = 90.0F / (float) player.getBbHeight();

        matrixStack.pushPose();
        matrixStack.translate(l, i1, 50f);

        float originalBodyRot = player.yBodyRot;
        float originalYaw = player.yRot;
        float originalPitch = player.xRot;
        float originalHeadRot = player.yHeadRot;

        float diffX = (float) (l) - (float) parWidth;
        float diffY = (float) (i1) - (float) parHeight;

        player.yBodyRot = (float) Math.atan((double) (diffX / 40.0F)) * 20.0F;
        player.yRot = (float) Math.atan((double) (diffX / 40.0F)) * 40.0F;
        player.xRot = -((float) Math.atan((double) (diffY / 40.0F))) * 20.0F;
        player.yHeadRot = player.yRot;

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
        matrixStack.scale(-f1, f1, f1);

        EntityRendererManager renderManager = mc.getEntityRenderDispatcher();

        renderManager.render(
                player,
                0.0D,
                0.0D,
                0.0D,
                0.0F,
                1.0F,
                matrixStack,
                mc.renderBuffers().bufferSource(),
                15728880
        );

        matrixStack.popPose();

        player.yBodyRot = originalBodyRot;
        player.yRot = originalYaw;
        player.xRot = originalPitch;
        player.yHeadRot = originalHeadRot;
         */

        super.render(matrixStack, parWidth, parHeight, p_73863_3_);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat
     * events
     */
    @Override
    public void onClose() {
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.onClose();
            Minecraft.getInstance().player.closeContainer();
            return true;
        }

        // Passa a execução para a classe pai caso não seja a tecla ESC
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in
     * single-player
     */
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}