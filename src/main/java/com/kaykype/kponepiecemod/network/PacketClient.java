package com.kaykype.kponepiecemod.network;

import com.kaykype.kponepiecemod.capabilities.IPlayerStats;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;

public class PacketClient {
    private INBT data;

    public PacketClient() {
    }

    public PacketClient(IPlayerStats data) {
        this.data = new CompoundNBT();
        this.data = STATS.getStorage().writeNBT(STATS, data, null);
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeNbt((CompoundNBT) this.data);
    }

    public static PacketClient decode(PacketBuffer buffer) {
        PacketClient packet = new PacketClient();
        packet.data = buffer.readNbt();
        return packet;
    }

    public static void handle(PacketClient packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> PacketHandler.handlePacket(packet, ctx));
        ctx.get().setPacketHandled(true);
    }

    private static class PacketHandler {
        private static void handlePacket(PacketClient packet, Supplier<NetworkEvent.Context> ctx) {
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ctx.get().getSender().getCapability(STATS).ifPresent
                        (
                                playerStats ->
                                {
                                    STATS.getStorage().readNBT(STATS, playerStats, null, packet.data);
                                }
                        );
            }
        }
    }
}