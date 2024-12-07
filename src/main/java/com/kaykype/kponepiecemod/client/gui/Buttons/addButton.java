package com.kaykype.kponepiecemod.client.gui.Buttons;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.utils.AttributesManagement;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect;

public class addButton extends Button {
    private static final Minecraft mc = Minecraft.getInstance();

    public addButton (int x, int y, int width, int height, String text, IPressable onPress) {
        super(x, y, width, height, new StringTextComponent(text), onPress);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks){
        int textColor = 0xFFFFFF;

        boolean isButtonPressed = (mouseX >= x
                && mouseY >= y
                && mouseX < (x + width)
                && mouseY < (y + height));

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //mc.getTextureManager().bind(ButtonsTexture);
        int textureX = 0;
        int textureY = 0;

        if (isButtonPressed) {
            //textureY = 11;
            textColor = 0x4287f5;
        }

        //fill(matrixStack, x, y, x+width, y+height, 0xBD000000);

        FontRenderer fontRenderer = Minecraft.getInstance().font;
        fontRenderer.drawShadow(matrixStack, "+", x+(width/2), y+(height/2), textColor);
    }
}
