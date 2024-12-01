package com.kaykype.kponepiecemod.handlers;

import com.kaykype.kponepiecemod.events.PlayerEvents;
import com.kaykype.kponepiecemod.events.ServerEvents;
import net.minecraftforge.common.MinecraftForge;

public class eventHandler {
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
    }
}
