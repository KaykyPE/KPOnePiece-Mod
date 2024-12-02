package com.kaykype.kponepiecemod.capabilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public interface IPlayerStats {
    int getTp();
    int getStr();
    int getCon();
    int getDex();
    int getSpi();
    int getLife();
    int getEnergy();
    int getStamina();

    void setTp(int tp);
    void setStr(int str);

    void setCon(int con);
    void setDex(int dex);
    void setSpi(int spi);
    void setLife(int life);
    void setEnergy(int energy);
    void setStamina(int stamina);
}
