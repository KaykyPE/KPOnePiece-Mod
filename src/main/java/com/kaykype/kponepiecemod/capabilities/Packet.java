package com.kaykype.kponepiecemod.capabilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class Packet {
    private int pontos;
    private int forca;
    private int vida;
    private int resistencia;
    private int energia;
    private int vidaAtual;
    private int energiaAtual;
    private int staminaAtual;
    private String raça;
    private String cargo;


    public Packet(int pontos, int str, int con, int dex, int spi, int vidaAtual, int energiaAtual, int staminaAtual, String raça, String cargo) {
        this.pontos = pontos;
        this.forca = str;
        this.resistencia = dex;
        this.vida = con;
        this.energia = spi;
        this.vidaAtual = vidaAtual;
        this.energiaAtual = energiaAtual;
        this.staminaAtual = staminaAtual;
        this.raça = cargo;
        this.cargo = raça;
    }

    public static void encode(Packet packet, PacketBuffer buffer) {
        buffer.writeInt(packet.pontos);
        buffer.writeInt(packet.forca);
        buffer.writeInt(packet.vida);
        buffer.writeInt(packet.resistencia);
        buffer.writeInt(packet.energia);
        buffer.writeInt(packet.vidaAtual);
        buffer.writeInt(packet.energiaAtual);
        buffer.writeInt(packet.staminaAtual);
        buffer.writeUtf(packet.raça);
        buffer.writeUtf(packet.cargo);
    }

    public static Packet decode(PacketBuffer buffer) {
        return new Packet(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readUtf(), buffer.readUtf());
    }

    public static void handle(Packet packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PacketHandler.handlePacket(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }

    private static class PacketHandler {
        private static void handlePacket(Packet packet, Supplier<NetworkEvent.Context> ctx) {
            if (ctx.get().getDirection().getReceptionSide().isServer()) {
                ServerPlayerEntity serverPlayer = ctx.get().getSender();
                if (serverPlayer != null) {
                    ctx.get().getSender().getCapability(ModSetup.STATS).ifPresent
                            (
                                    playerStats ->
                                    {
                                        playerStats.setTp(packet.pontos);
                                        playerStats.setStr(packet.forca);
                                        playerStats.setCon(packet.vida);
                                        playerStats.setDex(packet.resistencia);
                                        playerStats.setSpi(packet.energia);
                                        playerStats.setLife(packet.vidaAtual);
                                        playerStats.setEnergy(packet.energiaAtual);
                                        playerStats.setStamina(packet.staminaAtual);
                                        playerStats.setRace(packet.raça);
                                        playerStats.setCargo(packet.cargo);
                                        ModPacketHandler.sendToPlayer(serverPlayer, new Packet(packet.pontos, packet.forca, packet.vida, packet.resistencia, packet.energia, packet.vidaAtual, packet.energiaAtual, packet.staminaAtual, packet.raça, packet.cargo));
                                    }
                            );
                }
            } else {
                PlayerEntity clientPlayer = Minecraft.getInstance().player;
                if (clientPlayer != null) {
                    clientPlayer.getCapability(ModSetup.STATS).ifPresent
                            (
                                    playerStats ->
                                    {
                                        playerStats.setTp(packet.pontos);
                                        playerStats.setStr(packet.forca);
                                        playerStats.setCon(packet.vida);
                                        playerStats.setDex(packet.resistencia);
                                        playerStats.setSpi(packet.energia);
                                        playerStats.setLife(packet.vidaAtual);
                                        playerStats.setEnergy(packet.energiaAtual);
                                        playerStats.setStamina(packet.staminaAtual);
                                        playerStats.setRace(packet.raça);
                                        playerStats.setCargo(packet.cargo);
                                    }
                            );
                }
            }
        }
    }
}