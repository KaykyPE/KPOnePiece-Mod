package com.kaykype.kponepiecemod.capabilities;

import com.kaykype.kponepiecemod.Reference;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Logger;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;

public class CapManager implements ICapabilitySerializable<CompoundNBT>
{
    private final PlayerStats stats=new PlayerStats();
    private final LazyOptional<IPlayerStats> statsOptional=LazyOptional.of(()->this.stats);

    public void invalidate()
    {
        this.statsOptional.invalidate();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        LogManager.getLogger().info(stats);
        LogManager.getLogger().info(STATS);
        if(STATS==null) return new CompoundNBT();
        else return (CompoundNBT) STATS.writeNBT(this.stats, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        LogManager.getLogger().info(stats);
        LogManager.getLogger().info(STATS);
        if(STATS!=null) STATS.readNBT(this.stats, null, nbt);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        LogManager.getLogger().info(STATS);
        return cap == STATS ? statsOptional.cast() : LazyOptional.empty();
    }

    private PlayerStats playerStats = stats;
}