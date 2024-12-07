package com.kaykype.kponepiecemod.client.races;

import com.kaykype.kponepiecemod.capabilities.ModSetup;
import net.minecraft.entity.player.PlayerEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class raceHandler {
    public raceHandler () {
    }

    public abstract float strBuff();

    public abstract float conBuff();

    public abstract float dexBuff();

    public abstract float spiBuff();

    public abstract String displayName();

    public abstract String name();
}