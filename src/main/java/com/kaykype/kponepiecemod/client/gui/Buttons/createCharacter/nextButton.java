package com.kaykype.kponepiecemod.client.gui.Buttons.createCharacter;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

public class nextButton extends Button{
    private static final Minecraft mc = Minecraft.getInstance();

    public nextButton (int x, int y, int width, int height, String text, Button.IPressable onPress) {
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

        if (isButtonPressed) {
            textColor = 0x4287f5;
        }

        FontRenderer fontRenderer = Minecraft.getInstance().font;
        fontRenderer.drawShadow(matrixStack, ">", (x-(fontRenderer.width(">")/2))+(width/2), (y-(fontRenderer.lineHeight/2))+(height/2), textColor);
    }
}
