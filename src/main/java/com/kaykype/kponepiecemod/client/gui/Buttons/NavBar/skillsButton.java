package com.kaykype.kponepiecemod.client.gui.Buttons.NavBar;

import com.kaykype.kponepiecemod.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

public class skillsButton extends Button {
    private static final Minecraft mc = Minecraft.getInstance();

    private static ResourceLocation ButtonsTexture = new ResourceLocation(Reference.MODID+":textures/gui/container/menu.png");

    public skillsButton (int x, int y, int width, int height, String text, Button.IPressable onPress) {
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
        mc.getTextureManager().bind(ButtonsTexture);

        if (isButtonPressed) {
            textColor = 0x4287f5;
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        blit(matrixStack, x, y, 0, 191, 73, 23);
        RenderSystem.disableBlend();

        FontRenderer fontRenderer = Minecraft.getInstance().font;
        fontRenderer.drawShadow(matrixStack, "Tecnicas", (x-(mc.font.width("Tecnicas")/2))+(width/2), (y+(height/2))-(mc.font.lineHeight/2), textColor);
    }
}
