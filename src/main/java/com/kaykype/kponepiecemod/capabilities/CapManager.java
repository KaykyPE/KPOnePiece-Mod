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
import net.minecraftforge.fml.loading.FMLCommonLaunchHandler;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Logger;

import static com.kaykype.kponepiecemod.capabilities.ModSetup.STATS;

public class CapManager implements ICapabilitySerializable<CompoundNBT>
{
    private final PlayerStats stats=new PlayerStats();
    private final LazyOptional<IPlayerStats> statsOptional=LazyOptional.of(()->this.stats);

    @Override
    public CompoundNBT serializeNBT()
    {
        if(STATS==null) return new CompoundNBT();
        else return (CompoundNBT) STATS.writeNBT(this.stats, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        if(STATS!=null) STATS.readNBT(this.stats, null, nbt);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return cap == STATS ? statsOptional.cast() : LazyOptional.empty();
    }
}