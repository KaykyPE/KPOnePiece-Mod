package com.kaykype.kponepiecemod.client.items.fruits;

import com.kaykype.kponepiecemod.Reference;
import com.kaykype.kponepiecemod.network.ModPacketHandler;
import com.kaykype.kponepiecemod.network.PacketClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class eatFruitEvent {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (!event.getItem().isEdible()) return;
            PlayerEntity player = (PlayerEntity) event.getEntity();
            player.getCapability(STATS).ifPresent(playerStats -> {
                int teste = 2;
                player.sendMessage(new StringTextComponent(teste + ""), player.getUUID());
                if ((event.getItem().getItem().getRegistryName().getNamespace() + ":" + event.getItem().getItem().getRegistryName().getPath()).equals(Reference.MODID + ":gomu_gomu_no_mi")) {
                    if (!(playerStats.getFruta().equals("empty"))) {
                        player.setHealth(0);
                    } else {
                        ModPacketHandler.sendToServer(new PacketClient(playerStats));
                    }
                }
            });
        }
    }
}
