package com.kaykype.kponepiecemod.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;

import static com.kaykype.kponepiecemod.KPOnePieceMod.LOGGER;

public class ModSetup {
    @CapabilityInject(IPlayerStats.class)
    public static Capability<IPlayerStats> STATS = null;

    public static void reg() {
        CapabilityManager.INSTANCE.register(
                IPlayerStats.class,
                new Capability.IStorage<IPlayerStats>() {
                    @Override
                    public INBT writeNBT(Capability<IPlayerStats> capability, IPlayerStats instance, Direction side) {
                        CompoundNBT nbt = new CompoundNBT();
                        nbt.putInt("pontos", instance.getTp());
                        nbt.putInt("forca", instance.getStr());
                        nbt.putInt("vida", instance.getCon());
                        nbt.putInt("resistencia", instance.getDex());
                        nbt.putInt("energia", instance.getSpi());
                        nbt.putInt("vidaAtual", instance.getLife());
                        nbt.putInt("energiaAtual", instance.getEnergy());
                        nbt.putInt("staminaAtual", instance.getStamina());
                        nbt.putString("raça", instance.getRace());
                        nbt.putString("cargo", instance.getCargo());
                        nbt.putString("fruta", instance.getFruta());
                        LOGGER.info(instance.getRace());
                        return nbt;
                    }

                    @Override
                    public void readNBT(Capability<IPlayerStats> capability, IPlayerStats instance, Direction side, INBT nbt) {
                        CompoundNBT compoundNBT = (CompoundNBT) nbt;
                        instance.setTp(compoundNBT.getInt("pontos"));
                        instance.setStr(compoundNBT.getInt("forca"));
                        instance.setCon(compoundNBT.getInt("vida"));
                        instance.setDex(compoundNBT.getInt("resistencia"));
                        instance.setSpi(compoundNBT.getInt("energia"));
                        instance.setLife(compoundNBT.getInt("vidaAtual"));
                        instance.setEnergy(compoundNBT.getInt("energiaAtual"));
                        instance.setStamina(compoundNBT.getInt("staminaAtual"));
                        instance.setRace(compoundNBT.getString("raça"));
                        instance.setCargo(compoundNBT.getString("cargo"));
                        instance.setFruta(compoundNBT.getString("fruta"));
                        LOGGER.info(instance.getRace());
                    }
                },
                PlayerStats::new
        );
    }
}
