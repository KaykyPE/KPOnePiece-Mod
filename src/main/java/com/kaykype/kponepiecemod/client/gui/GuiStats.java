package com.kaykype.kponepiecemod.client.gui;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.CapManager;
import com.kaykype.kponepiecemod.capabilities.ModPacketHandler;
import com.kaykype.kponepiecemod.capabilities.ModSetup;
import com.kaykype.kponepiecemod.capabilities.Packet;
import com.kaykype.kponepiecemod.client.gui.Buttons.NavBar.attributesButton;
import com.kaykype.kponepiecemod.client.gui.Buttons.NavBar.skillsButton;
import com.kaykype.kponepiecemod.client.gui.Buttons.NavBar.trainingButton;
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

    private Button buttonAtributos;
    private Button buttonSkills;
    private Button buttonTreinos;
    private Button buttonMaestrias;

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
            this.textStr = "STR "+playerStats.getStr();
            this.textDex = "DEX "+playerStats.getDex();
            this.textCon = playerStats.getCon()+" CON";
            this.textSpi = playerStats.getSpi()+" SPI";
            this.textTpLabel = "Pontos: ";
            this.textTp = "" + playerStats.getTp();
        });
        this.xStr = 49.0F;
        this.xDex = 49.0F;
        this.xCon = 49.0F;
        this.xSpi = 49.0F;
        this.xTp = 0.0F;
    }

    @Override
    public void init() {
        buttons.clear();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(true);

        int offsetFromScreenLeft = width / 2;
        int offsetFromScreenTop = height / 2;

        //NavBar
        buttons.add(buttonAtributos = new attributesButton((offsetFromScreenLeft - 146)-20, 10, 73, 23, "", button -> {
        }));

        buttons.add(buttonSkills = new skillsButton((offsetFromScreenLeft - 73)-10, 10, 73, 23, "", button -> {
        }));

        buttons.add(buttonTreinos = new trainingButton(offsetFromScreenLeft+10, 10, 73, 23, "", button -> {
        }));

        buttons.add(buttonMaestrias = new com.kaykype.kponepiecemod.client.gui.Buttons.NavBar.buttonMaestrias(((offsetFromScreenLeft+10) + 73)+20, 10, 73, 23, "", button -> {
        }));

        //Atributos
        buttons.add(buttonStr = new addButton(offsetFromScreenLeft - 190, offsetFromScreenTop, 13, 13, "", button -> {
        }));

        buttons.add(buttonDex = new addButton(offsetFromScreenLeft - 190, offsetFromScreenTop + 20, 13, 13, "", button -> {
        }));

        buttons.add(buttonCon = new addButton(offsetFromScreenLeft - 190, offsetFromScreenTop + 40, 13, 13, "", button -> {
        }));

        buttons.add(buttonSpi = new addButton(offsetFromScreenLeft - 190, offsetFromScreenTop + 60, 13, 13, "", button -> {
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
                ModPacketHandler.sendToServer(new Packet(playerStats.getTp(), playerStats.getStr()+1, playerStats.getCon(), playerStats.getDex(), playerStats.getSpi(), playerStats.getLife(), playerStats.getEnergy(), playerStats.getStamina(), playerStats.getRace(), playerStats.getCargo()));
            });

            return true;
        }
        if (buttonDex.isMouseOver(mouseX, mouseY)) {
            player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                ModPacketHandler.sendToServer(new Packet(playerStats.getTp(), playerStats.getStr(), playerStats.getCon(), playerStats.getDex()+1, playerStats.getSpi(), playerStats.getLife(), playerStats.getEnergy(), playerStats.getStamina(), playerStats.getRace(), playerStats.getCargo()));
            });
            return true;
        }
        if (buttonCon.isMouseOver(mouseX, mouseY)) {
            player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                ModPacketHandler.sendToServer(new Packet(playerStats.getTp(), playerStats.getStr(), playerStats.getCon()+1, playerStats.getDex(), playerStats.getSpi(), playerStats.getLife(), playerStats.getEnergy(), playerStats.getStamina(), playerStats.getRace(), playerStats.getCargo()));
            });
            return true;
        }
        if (buttonSpi.isMouseOver(mouseX, mouseY)) {
            player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
                ModPacketHandler.sendToServer(new Packet(playerStats.getTp(), playerStats.getStr(), playerStats.getCon(), playerStats.getDex(), playerStats.getSpi()+1, playerStats.getLife(), playerStats.getEnergy(), playerStats.getStamina(), playerStats.getRace(), playerStats.getCargo()));
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
            textStr = "STR "+ playerStats.getStr();
            textDex = "DEX "+playerStats.getDex();
            textCon = "CON "+playerStats.getCon();
            textSpi = "SPI "+playerStats.getSpi();
            textTp = ""+playerStats.getTp();

            xStr = 25 + mc.font.width(textStr);
            xDex = 25 + mc.font.width(textDex);
            xCon = 25 - mc.font.width(textCon);
            xSpi = 25 - mc.font.width(textSpi);
            int offsetFromScreenLeft = width / 2;
            xTp = ((offsetFromScreenLeft - 200) - (mc.font.width(textTp)/2)) + 64;
        });
    }

    /**
     * Draws the screen and all the components in it.
     */

    @Override
    public void render(MatrixStack matrixStack, int parWidth, int parHeight, float p_73863_3_) {
        int offsetFromScreenLeft = width / 2;
        int offsetFromScreenTop = height / 2;

        fill(matrixStack, 0, 0, width, height, 0x8F000000);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        mc.getTextureManager().bind(new ResourceLocation(Reference.MODID, "textures/gui/container/menu.png"));
        blit(matrixStack, offsetFromScreenLeft-200, offsetFromScreenTop - (165/2), 0, 0, 128, 165);
        blit(matrixStack, offsetFromScreenLeft-200, offsetFromScreenTop - 115, 0, 167, 128, 23);

        RenderSystem.disableBlend();

        font.drawShadow(matrixStack, textStr,
                (offsetFromScreenLeft-170),
                offsetFromScreenTop+6, 0xFFFFFF);
        font.drawShadow(matrixStack, textDex,
                (offsetFromScreenLeft - 170),
                (offsetFromScreenTop + 26), 0xFFFFFF);
        font.drawShadow(matrixStack, textCon,
                (offsetFromScreenLeft - 170),
                (offsetFromScreenTop + 46), 0xFFFFFF);
        font.drawShadow(matrixStack, textSpi,
                (offsetFromScreenLeft - 170),
                (offsetFromScreenTop + 66), 0xFFFFFF);
        font.drawShadow(matrixStack, "Seus atributos",
                ((offsetFromScreenLeft - 200) - (mc.font.width("Seus atributos")/2)) + 64,
                ((offsetFromScreenTop - 115) - font.lineHeight/2) + 11, 0xFFFFFF);

        font.drawShadow(matrixStack, textTpLabel,
                ((offsetFromScreenLeft - 200) - (mc.font.width(textTpLabel)/2)) + 64,
                (offsetFromScreenTop - 75), 0x808080);
        font.drawShadow(matrixStack, textTp,
                xTp,
                (offsetFromScreenTop - 60), 0xFFFFFF);
        /*
        GlStateManager.translate(offsetFromScreenLeft+128, offsetFromScreenTop+20, 10);
        RenderPlayer playerModel = new RenderPlayer(Minecraft.getMinecraft().getRenderManager());
        playerModel.bindTexture(((EntityPlayer SP) player).getLocationSkin());
        playerModel.getMainModel().render(player, 0, 0, 0, 0, 0, 6f);
        */

        float f1 = (90.0F / (float) player.getBoundingBox().getYsize()) * 0.7f;

        int l = offsetFromScreenLeft+160;
        int i1 = offsetFromScreenTop;

        Minecraft mc = Minecraft.getInstance();

        matrixStack.pushPose();
        matrixStack.translate(l, i1, 50f);

        matrixStack.scale(-f1, f1, f1);

        float yRot = player.yRot;
        float xRot = player.xRot;
        float yBodyRot = player.yBodyRot;
        float headRot = player.yHeadRot;

        player.yRot = 30f;
        player.yBodyRot = 30f;
        player.xRot = 0;
        player.yHeadRot = 30f;
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180f));

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

        player.yRot = yRot;
        player.xRot = xRot;
        player.yBodyRot = yBodyRot;
        player.yHeadRot = headRot;

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