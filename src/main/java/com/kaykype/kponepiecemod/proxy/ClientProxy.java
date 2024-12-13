package com.kaykype.kponepiecemod.proxy;

import com.kaykype.kponepiecemod.client.gui.GuiStats;
import com.kaykype.kponepiecemod.events.PlayerEvents;
import com.kaykype.kponepiecemod.events.ServerEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "kponepiecemod", value = Dist.CLIENT)
public class ClientProxy {

	public ClientProxy () {
		registerKeyBindings();
	}

	public static final KeyBinding STATS_BUTTON = new KeyBinding(
			"key.statsui",
			GLFW.GLFW_KEY_V,
			"key.categories.kponepiecemod"
	);

	public static final KeyBinding COMBAT_MODE = new KeyBinding(
			"key.combatmode",
			GLFW.GLFW_KEY_APOSTROPHE,
			"key.categories.kponepiecemod"
	);

	public static void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(STATS_BUTTON);
		ClientRegistry.registerKeyBinding(COMBAT_MODE);
	}
}
