package com.kaykype.kponepiecemod.client.gui;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.capabilities.ModPacketHandler;
import com.kaykype.kponepiecemod.capabilities.Packet;
import com.kaykype.kponepiecemod.client.gui.Buttons.createCharacter.attributesButton;
import com.kaykype.kponepiecemod.client.gui.Buttons.createCharacter.skillsButton;
import com.kaykype.kponepiecemod.client.gui.Buttons.createCharacter.doneButton;
import com.kaykype.kponepiecemod.client.gui.Buttons.createCharacter.nextButton;
import com.kaykype.kponepiecemod.client.gui.Buttons.createCharacter.previousButton;
import com.kaykype.kponepiecemod.client.races.RaceMetods;
import com.kaykype.kponepiecemod.client.races.raceHandler;
import com.kaykype.kponepiecemod.utils.AttributesManagement;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;

import java.util.Arrays;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;

public class GuiCreateCharacter extends Screen {
    private static final Minecraft mc = Minecraft.getInstance();

    String textRaceSelected;
    String textRoleSelected;

    String textForca;
    String textVida;
    String textResistencia;
    String textEnergia;

    String textStrBuffPercent;
    String textConBuffPercent;
    String textDexBuffPercent;
    String textSpiBuffPercent;

    int textStrBuffColor;
    int textDexBuffColor;
    int textConBuffColor;
    int textSpiBuffColor;

    private Button previousRaceButton;
    private Button nextRaceButton;

    private Button previousRoleButton;
    private Button nextRoleButton;

    private Button doneButton;

    private Button skillsButton;
    private Button attrButton;

    boolean isAttrButtonSelected;

    PlayerEntity player;

    public GuiCreateCharacter(PlayerEntity p) {
        super(new StringTextComponent("Create Character Menu"));
        this.player = p;

        this.textRaceSelected = "Humano";
        this.textRoleSelected = "Pirata";

        this.isAttrButtonSelected = true;

        this.textForca = "Força: " + (int) (AttributesManagement.totalStrength(10, textRaceSelected) * new RaceMetods().getRaceByName(textRaceSelected).strBuff());
        this.textResistencia = "Resistência: " + (int) (AttributesManagement.totalResistence(10, textRaceSelected) * new RaceMetods().getRaceByName(textRaceSelected).dexBuff());
        this.textVida = "Vida: " + (int) (AttributesManagement.getMaxLife(10, textRaceSelected) * new RaceMetods().getRaceByName(textRaceSelected).conBuff());
        this.textEnergia = "Energia: " + (int) (AttributesManagement.getMaxEnergy(10, textRaceSelected) * new RaceMetods().getRaceByName(textRaceSelected).spiBuff());

        this.textStrBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).strBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).strBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).strBuff() - 1) * 100)) + "%";
        this.textConBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).conBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).conBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).conBuff() - 1) * 100)) + "%";
        this.textDexBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).dexBuff() - 1) * 100)) + "%";
        this.textSpiBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).spiBuff() - 1) * 100)) + "%";

        this.textStrBuffColor = new RaceMetods().getRaceByName(textRaceSelected).strBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? 0xff000d : 0x919191;
        this.textDexBuffColor = new RaceMetods().getRaceByName(textRaceSelected).dexBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? 0xff000d : 0x919191;
        this.textConBuffColor = new RaceMetods().getRaceByName(textRaceSelected).conBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? 0xff000d : 0x919191;
        this.textSpiBuffColor = new RaceMetods().getRaceByName(textRaceSelected).spiBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? 0xff000d : 0x919191;
    }

    @Override
    public void init() {
        buttons.clear();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(true);

        int offsetFromScreenLeft = width / 2;
        int offsetFromScreenTop = height / 2;

        //selects
        buttons.add(previousRaceButton = new previousButton((offsetFromScreenLeft - (13 / 2)) - 30, offsetFromScreenTop - 120, 13, 13, "", button -> {
        }));

        buttons.add(previousRoleButton = new previousButton((offsetFromScreenLeft - (13 / 2)) - 30, offsetFromScreenTop - 90, 13, 13, "", button -> {
        }));

        buttons.add(nextRaceButton = new nextButton((offsetFromScreenLeft - (13 / 2)) + 35, offsetFromScreenTop - 120, 13, 13, "", button -> {
        }));

        buttons.add(nextRoleButton = new nextButton((offsetFromScreenLeft - (13 / 2)) + 35, offsetFromScreenTop - 90, 13, 13, "", button -> {
        }));
        //others
        buttons.add(doneButton = new doneButton((width - 73) - 20, (height - 23) - 20, 73, 23, "", button -> {
        }));

        buttons.add(attrButton = new attributesButton(offsetFromScreenLeft - 210, offsetFromScreenTop + 100, 73, 23, "", button -> {
        }, isAttrButtonSelected));

        buttons.add(skillsButton = new skillsButton(((offsetFromScreenLeft - 190) - 73) + 128, offsetFromScreenTop + 100, 73, 23, "", button -> {
        }, isAttrButtonSelected));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (previousRoleButton.isMouseOver(mouseX, mouseY)) {
            if (textRoleSelected.equals("Pirata")) textRoleSelected = "Marinha";
            else if (textRoleSelected.equals("Marinha")) textRoleSelected = "Pirata";
            player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
            return true;
        }

        if (nextRoleButton.isMouseOver(mouseX, mouseY)) {
            if (textRoleSelected.equals("Pirata")) textRoleSelected = "Marinha";
            else if (textRoleSelected.equals("Marinha")) textRoleSelected = "Pirata";
            player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
            return true;
        }

        if (previousRaceButton.isMouseOver(mouseX, mouseY)) {
            String[] Races = (new RaceMetods()).listAll();

            String[] raceListDN = Arrays.stream(Races).map(key -> {
                raceHandler race = new RaceMetods().getRaceByName(key);

                return race.displayName();
            }).toArray(String[]::new);

            int pagAtual = 0;

            for (int i = 0; !(raceListDN[i].equals(textRaceSelected)); i++) {
                pagAtual++;
            }

            if (pagAtual <= 0) pagAtual = raceListDN.length;

            player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);

            textRaceSelected = raceListDN[pagAtual - 1];

            this.textForca = "Força: " + AttributesManagement.totalStrength(10, textRaceSelected);
            this.textResistencia = "Resistência: " + AttributesManagement.totalResistence(10, textRaceSelected);
            this.textVida = "Vida: " + AttributesManagement.getMaxLife(10, textRaceSelected);
            this.textEnergia = "Energia: " + AttributesManagement.getMaxEnergy(10, textRaceSelected);

            this.textStrBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).strBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).strBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).strBuff() - 1) * 100)) + "%";
            this.textConBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).conBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).conBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).conBuff() - 1) * 100)) + "%";
            this.textDexBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).dexBuff() - 1) * 100)) + "%";
            this.textSpiBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).spiBuff() - 1) * 100)) + "%";

            this.textStrBuffColor = new RaceMetods().getRaceByName(textRaceSelected).strBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? 0xff000d : 0x919191;
            this.textDexBuffColor = new RaceMetods().getRaceByName(textRaceSelected).dexBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? 0xff000d : 0x919191;
            this.textConBuffColor = new RaceMetods().getRaceByName(textRaceSelected).conBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? 0xff000d : 0x919191;
            this.textSpiBuffColor = new RaceMetods().getRaceByName(textRaceSelected).spiBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? 0xff000d : 0x919191;
            return true;
        }

        if (nextRaceButton.isMouseOver(mouseX, mouseY)) {
            String[] Races = (new RaceMetods()).listAll();

            String[] raceListDN = Arrays.stream(Races).map(key -> {
                raceHandler race = new RaceMetods().getRaceByName(key);

                return race.displayName();
            }).toArray(String[]::new);

            int pagAtual = 0;

            for (int i = 0; !(raceListDN[i].equals(textRaceSelected)); i++) {
                pagAtual++;
            }

            if (pagAtual >= (raceListDN.length - 1)) pagAtual = -1;

            player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);

            textRaceSelected = raceListDN[pagAtual + 1];

            this.textForca = "Força: " + AttributesManagement.totalStrength(10, textRaceSelected);
            this.textResistencia = "Resistência: " + AttributesManagement.totalResistence(10, textRaceSelected);
            this.textVida = "Vida: " + AttributesManagement.getMaxLife(10, textRaceSelected);
            this.textEnergia = "Energia: " + AttributesManagement.getMaxEnergy(10, textRaceSelected);

            this.textStrBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).strBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).strBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).strBuff() - 1) * 100)) + "%";
            this.textConBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).conBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).conBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).conBuff() - 1) * 100)) + "%";
            this.textDexBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).dexBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).dexBuff() - 1) * 100)) + "%";
            this.textSpiBuffPercent = (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() > 1 ? "+" : new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? "-" : "") + (int) (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? (100 - (new RaceMetods().getRaceByName(textRaceSelected).spiBuff() * 100)) : ((new RaceMetods().getRaceByName(textRaceSelected).spiBuff() - 1) * 100)) + "%";

            this.textStrBuffColor = new RaceMetods().getRaceByName(textRaceSelected).strBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).strBuff() < 1 ? 0xff000d : 0x919191;
            this.textDexBuffColor = new RaceMetods().getRaceByName(textRaceSelected).dexBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).dexBuff() < 1 ? 0xff000d : 0x919191;
            this.textConBuffColor = new RaceMetods().getRaceByName(textRaceSelected).conBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).conBuff() < 1 ? 0xff000d : 0x919191;
            this.textSpiBuffColor = new RaceMetods().getRaceByName(textRaceSelected).spiBuff() > 1 ? 0x00ff2a : new RaceMetods().getRaceByName(textRaceSelected).spiBuff() < 1 ? 0xff000d : 0x919191;
            return true;
        }

        if (skillsButton.isMouseOver(mouseX, mouseY)) {
            isAttrButtonSelected = false;
            player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
            int offsetFromScreenLeft = width / 2;
            int offsetFromScreenTop = height / 2;
            buttons.set(5, attrButton = new attributesButton(offsetFromScreenLeft - 210, offsetFromScreenTop + 100, 73, 23, "", buttonC -> {
            }, isAttrButtonSelected));
            buttons.set(6, skillsButton = new skillsButton(((offsetFromScreenLeft - 190) - 73) + 128, offsetFromScreenTop + 100, 73, 23, "", buttonC -> {
            }, isAttrButtonSelected));
            return true;
        }

        if (attrButton.isMouseOver(mouseX, mouseY)) {
            isAttrButtonSelected = true;
            player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
            int offsetFromScreenLeft = width / 2;
            int offsetFromScreenTop = height / 2;
            buttons.set(5, attrButton = new attributesButton(offsetFromScreenLeft - 210, offsetFromScreenTop + 100, 73, 23, "", buttonC -> {
            }, isAttrButtonSelected));
            buttons.set(6, skillsButton = new skillsButton(((offsetFromScreenLeft - 190) - 73) + 128, offsetFromScreenTop + 100, 73, 23, "", buttonC -> {
            }, isAttrButtonSelected));
            return true;
        }

        if (this.doneButton.isMouseOver(mouseX, mouseY)) {
            player.getCapability(STATS).ifPresent(playerStats -> {
                ModPacketHandler.sendToServer(new Packet(
                        playerStats.getTp(),
                        playerStats.getStr(),
                        playerStats.getCon(),
                        playerStats.getDex(),
                        playerStats.getSpi(),
                        AttributesManagement.getMaxLife(10, textRaceSelected),
                        AttributesManagement.getMaxEnergy(10, textRaceSelected),
                        AttributesManagement.getMaxStamina(10, textRaceSelected),
                        new RaceMetods().getRaceByName(textRaceSelected).name(),
                        textRoleSelected
                ));
            });

            this.onClose();
            Minecraft.getInstance().player.closeContainer();
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(MatrixStack matrixStack, int parWidth, int parHeight, float p_73863_3_) {
        int offsetFromScreenLeft = width / 2;
        int offsetFromScreenTop = height / 2;

        fill(matrixStack, 0, 0, width, height, 0x8F000000);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        mc.getTextureManager().bind(new ResourceLocation(Reference.MODID, "textures/gui/container/menu.png"));
        blit(matrixStack, offsetFromScreenLeft - 200, offsetFromScreenTop - (165 / 2), 0, 0, 128, 165);
        blit(matrixStack, offsetFromScreenLeft - 200, offsetFromScreenTop - 115, 0, 167, 128, 23);

        RenderSystem.disableBlend();

        skillsContainer(matrixStack, offsetFromScreenLeft, offsetFromScreenTop);
        attrContainer(matrixStack, offsetFromScreenLeft, offsetFromScreenTop);

        font.drawShadow(matrixStack, "Buffs",
                ((offsetFromScreenLeft - 200) - (mc.font.width("Buffs") / 2)) + 64,
                ((offsetFromScreenTop - 115) - font.lineHeight / 2) + 11, 0xFFFFFF);
        font.drawShadow(matrixStack, textRaceSelected,
                (offsetFromScreenLeft - (mc.font.width(textRaceSelected) / 2)),
                ((offsetFromScreenTop + (mc.font.lineHeight / 2)) - 122), 0xFFFFFF);
        font.drawShadow(matrixStack, textRoleSelected,
                (offsetFromScreenLeft - (mc.font.width(textRoleSelected) / 2)),
                ((offsetFromScreenTop + (mc.font.lineHeight / 2)) - 92), 0xFFFFFF);

        float f1 = (90.0F / (float) player.getBoundingBox().getYsize()) * (textRaceSelected == "Gigante" ? 1.2f : 1);

        int l = offsetFromScreenLeft + 160;
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

    public void attrContainer(MatrixStack matrixStack, int offsetFromScreenLeft, int offsetFromScreenTop) {
        if (!isAttrButtonSelected) return;

        font.drawShadow(matrixStack, textForca,
                (offsetFromScreenLeft - 195),
                offsetFromScreenTop - 40, 0xFFFFFF);
        font.drawShadow(matrixStack, textResistencia,
                (offsetFromScreenLeft - 195),
                (offsetFromScreenTop - 30), 0xFFFFFF);
        font.drawShadow(matrixStack, textVida,
                (offsetFromScreenLeft - 195),
                (offsetFromScreenTop - 20), 0xFFFFFF);
        font.drawShadow(matrixStack, textEnergia,
                (offsetFromScreenLeft - 195),
                (offsetFromScreenTop - 10), 0xFFFFFF);

        font.drawShadow(matrixStack, textStrBuffPercent,
                (offsetFromScreenLeft - 110),
                offsetFromScreenTop - 40, textStrBuffColor);
        font.drawShadow(matrixStack, textDexBuffPercent,
                (offsetFromScreenLeft - 110),
                (offsetFromScreenTop - 30), textDexBuffColor);
        font.drawShadow(matrixStack, textConBuffPercent,
                (offsetFromScreenLeft - 110),
                (offsetFromScreenTop - 20), textConBuffColor);
        font.drawShadow(matrixStack, textSpiBuffPercent,
                (offsetFromScreenLeft - 110),
                (offsetFromScreenTop - 10), textSpiBuffColor);
    }

    public void skillsContainer(MatrixStack matrixStack, int offsetFromScreenLeft, int offsetFromScreenTop) {
        if (isAttrButtonSelected) return;

        font.drawShadow(matrixStack, "...",
                (offsetFromScreenLeft - 195),
                offsetFromScreenTop - 40, 0xd1d1d1);
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
