package com.kaykype.kponepiecemod.utils;

public class AttributesManagement {
    public static int getMaxLife(int con) {
        int maxLife = con*10;
        return maxLife;
    }

    public static int getMaxEnergy(int spi) {
        int maxEnergy = spi*10;
        return maxEnergy;
    }

    public static int getMaxStamina(int con) {
        int maxStamina = con*5;
        return maxStamina;
    }
}
