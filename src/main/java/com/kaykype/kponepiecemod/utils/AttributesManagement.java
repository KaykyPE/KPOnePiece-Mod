package com.kaykype.kponepiecemod.utils;

import com.kaykype.kponepiecemod.capabilities.ModSetup;
import com.kaykype.kponepiecemod.client.races.RaceMetods;
import net.minecraft.entity.player.PlayerEntity;

public class AttributesManagement {
    public static int getMaxLife(int con, String classe) {
        int maxLife = (int)((float)con * 22 * (classe == "empty" ? 1 : new RaceMetods().getRaceByName(classe).conBuff()));
        return maxLife;
    }

    public static int getMaxEnergy(int spi, String classe) {
        int maxEnergy = (int)((float)spi * 36 * (classe == "empty" ? 1 : new RaceMetods().getRaceByName(classe).spiBuff()));
        return maxEnergy;
    }

    public static int damageWithResistence(PlayerEntity player, int damage, int dex) {
        int finalDamage = damage - (int) ((float) (dex * 3.1 * new RaceMetods().getPlayer(player).dexBuff()));
        return finalDamage < 0 ? 1 : finalDamage;
    }

    public static int totalResistence(int dex, String classe) {
        int finalResistence = (int)((float)dex * 3.1 * (classe == "empty" ? 1 : new RaceMetods().getRaceByName(classe).dexBuff()));
        return finalResistence;
    }

    public static int getMaxStamina(int con, String classe) {
        int maxStamina = (int)((float)con*5 * (classe == "empty" ? 1 : new RaceMetods().getRaceByName(classe).conBuff()));
        return maxStamina;
    }

    public static int totalStrength(int str, String classe) {
        int totalStrength = (int)((float)str * 3.5 * (classe == "empty" ? 1 : new RaceMetods().getRaceByName(classe).strBuff()));

        return totalStrength;
    }
}
