package com.kaykype.kponepiecemod.capabilities;

import com.kaykype.kponepiecemod.client.races.raceHandler;
import com.kaykype.kponepiecemod.utils.AttributesManagement;
import net.minecraft.entity.player.ServerPlayerEntity;

public class PlayerStats implements IPlayerStats {
    private int pontos;
    private int forca;
    private int resistencia;
    private int vida;
    private int energia;
    private int vidaAtual;
    private int energiaAtual;
    private int staminaAtual;
    private String raça;
    private String cargo;
    private String fruta;

    public PlayerStats() {
        raça = "empty";
        cargo = "empty";
        pontos = 0;
        forca = 10;
        resistencia = 10;
        vida = 10;
        energia = 10;
        vidaAtual = AttributesManagement.getMaxLife(10, "empty");
        energiaAtual = AttributesManagement.getMaxEnergy(10, "empty");
        staminaAtual = AttributesManagement.getMaxStamina(10, "empty");
        fruta = "empty";
    }

    @Override
    public String getFruta() {
        return fruta;
    }

    @Override
    public void setFruta(String fruta) {
        this.fruta = fruta;
    }

    @Override
    public int getStr() {
        return forca;
    }

    @Override
    public String getRace() {
        return raça;
    }

    @Override
    public void setRace(String raça) {
        this.raça = raça;
    }

    @Override
    public String getCargo() {
        return cargo;
    }

    @Override
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public void setStr(int value) {
        this.forca = value;
    }

    @Override
    public int getTp() {
        return pontos;
    }

    @Override
    public void setTp(int value) {
        this.pontos = value;
    }

    @Override
    public int getCon() {
        return vida;
    }

    @Override
    public void setCon(int value) {
        this.vida = value;
    }

    @Override
    public int getDex() {
        return resistencia;
    }

    @Override
    public void setDex(int value) {
        this.resistencia = value;
    }

    @Override
    public int getSpi() {
        return energia;
    }

    @Override
    public void setSpi(int value) {
        this.energia = value;
    }

    @Override
    public int getLife() {
        return vidaAtual;
    }

    @Override
    public void setLife(int value) {
        this.vidaAtual = value;
    }

    @Override
    public int getEnergy() {
        return energiaAtual;
    }

    @Override
    public void setEnergy(int value) {
        this.energiaAtual = value;
    }

    @Override
    public int getStamina() {
        return staminaAtual;
    }

    @Override
    public void setStamina(int value) {
        this.staminaAtual = value;
    }
}
