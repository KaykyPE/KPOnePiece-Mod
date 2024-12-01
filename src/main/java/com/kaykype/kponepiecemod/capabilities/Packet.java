package com.kaykype.kponepiecemod.capabilities;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class Packet {
    private int pontos;
    private int str;
    private int con;
    private int dex;
    private int spi;
    private int vidaAtual;
    private int energiaAtual;
    private int staminaAtual;

    public Packet(int pontos, int str, int con, int dex, int spi, int vidaAtual, int energiaAtual, int staminaAtual)
    {
        this.pontos=pontos;
        this.str=str;
        this.dex=dex;
        this.con=con;
        this.spi=spi;
        this.vidaAtual=vidaAtual;
        this.energiaAtual=energiaAtual;
        this.staminaAtual=staminaAtual;
    }

    public static void encode(Packet packet, PacketBuffer buffer)
    {
        buffer.writeInt(packet.pontos);
        buffer.writeInt(packet.str);
        buffer.writeInt(packet.con);
        buffer.writeInt(packet.dex);
        buffer.writeInt(packet.spi);
        buffer.writeInt(packet.vidaAtual);
        buffer.writeInt(packet.energiaAtual);
        buffer.writeInt(packet.staminaAtual);
    }

    public static Packet decode(PacketBuffer buffer)
    {
        return new Packet(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public static void handle(Packet packet, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(()-> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->PacketHandler.handlePacket(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }

    private static class PacketHandler
    {
        private static void handlePacket(Packet packet, Supplier<NetworkEvent.Context> ctx)
        {
            Minecraft mc=Minecraft.getInstance();
            mc.player.getCapability(ModSetup.STATS).ifPresent
                    (
                            playerStats->
                            {
                                playerStats.setTp(packet.pontos);
                                playerStats.setStr(packet.str);
                                playerStats.setCon(packet.con);
                                playerStats.setDex(packet.dex);
                                playerStats.setSpi(packet.spi);
                                playerStats.setLife(packet.vidaAtual);
                                playerStats.setEnergy(packet.energiaAtual);
                                playerStats.setStamina(packet.staminaAtual);
                            }
                    );
        }
    }
}
