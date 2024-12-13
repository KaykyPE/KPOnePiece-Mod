package com.kaykype.kponepiecemod.network;

import com.kaykype.kponepiecemod.capabilities.IPlayerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;

public class PacketServer {
    private INBT data;
    private String pId;

    public PacketServer() {
    }

    public PacketServer(String pId, IPlayerStats data) {
        this.data = new CompoundNBT();
        this.data = STATS.getStorage().writeNBT(STATS, data, null);
        this.pId = pId;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeUtf(this.pId);
        buffer.writeNbt((CompoundNBT) this.data);
    }

    public static PacketServer decode(PacketBuffer buffer) {
        PacketServer packet = new PacketServer();
        packet.pId = buffer.readUtf();
        packet.data = buffer.readNbt();
        return packet;
    }

    public static void handle(PacketServer packet, Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> PacketServer.PacketHandler.handlePacket(packet));
        }
        ctx.get().setPacketHandled(true);
    }

    private static class PacketHandler {
        @OnlyIn(Dist.CLIENT)
        private static void handlePacket(PacketServer packet) {
            PlayerEntity player = Minecraft.getInstance().level.getPlayerByUUID(UUID.fromString(packet.pId));

            if(player == null) return;

            player.getCapability(STATS).ifPresent
                    (
                            playerStats ->
                            {
                                STATS.getStorage().readNBT(STATS, playerStats, null, packet.data);
                            }
                    );
        }
    }
}
