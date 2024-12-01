package com.kaykype.kponepiecemod.capabilities;

import net.minecraft.entity.player.ServerPlayerEntity;

public class PlayerStats implements IPlayerStats {
    private int tp;
    private int str;
    private int dex;
    private int con;
    private int spi;
    private int life;
    private int energy;
    private int stamina;

    public PlayerStats () {
        tp  = 0;
        str = 10;
        dex = 10;
        spi = 10;
        con = 10;
        life = 100;
        energy = 100;
        stamina = 50;
    }

    @Override
    public int getStr() {
        return str;
    }

    @Override
    public void setStr(int value) {
        this.str = value;
    }

    @Override
    public int getTp() {
        return tp;
    }

    @Override
    public void setTp(int value) {
        this.tp = value;
    }

    @Override
    public int getCon() {
        return con;
    }

    @Override
    public void setCon(int value) {
        this.con = value;
    }

    @Override
    public int getDex() {
        return dex;
    }

    @Override
    public void setDex(int value) {
        this.dex = value;
    }

    @Override
    public int getSpi() {
        return spi;
    }

    @Override
    public void setSpi(int value) {
        this.spi = value;
    }

    @Override
    public int getLife() {
        return life;
    }

    @Override
    public void setLife(int value) {
        this.life = value;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int value) {
        this.energy = value;
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public void setStamina(int value) {
        this.stamina = value;
    }

    @Override
    public void update(ServerPlayerEntity player)
    {
        ModPacketHandler.sendToPlayer(player, new Packet(this.getTp(), this.getStr(), this.getCon(), this.getDex(), this.getSpi(), this.getLife(), this.getEnergy(), this.getStamina()));
    }
}
