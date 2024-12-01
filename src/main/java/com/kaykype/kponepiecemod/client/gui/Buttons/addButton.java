package com.kaykype.kponepiecemod.client.gui.Buttons;

import com.kaykype.kponepiecemod.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect;

public class addButton extends Button {
    private static final Minecraft mc = Minecraft.getInstance();

    private static ResourceLocation ButtonsTexture = new ResourceLocation(Reference.MODID+":textures/gui/button1.png");

    public addButton (int x, int y, int width, int height, String text, IPressable onPress) {
        super(x, y, width, height, new StringTextComponent(text), onPress);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks){
        boolean isButtonPressed = (mouseX >= x
                && mouseY >= y
                && mouseX < x + 11
                && mouseY < y + 11);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bind(ButtonsTexture);
        int textureX = 0;
        int textureY = 0;

        if (isButtonPressed) {
            textureY = 11;
        }

        blit(matrixStack, x, y,
                textureX, textureY,
                11, 11);
    }
}
