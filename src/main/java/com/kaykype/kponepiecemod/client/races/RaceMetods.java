package com.kaykype.kponepiecemod.client.races;

import com.kaykype.kponepiecemod.capabilities.ModSetup;
import net.minecraft.entity.player.PlayerEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RaceMetods {
    public raceHandler getPlayer(PlayerEntity player) {
        AtomicReference<String> race = new AtomicReference<>("");
        player.getCapability(ModSetup.STATS).ifPresent(playerStats -> {
            String[] raceList = listAll();

            if (playerStats.getRace() != "empty") race.set(Arrays.stream(raceList).filter(key -> key.equals(playerStats.getRace())).findFirst().orElse(null));
        });

        raceHandler raceProps = null;

        if(race.get().equals("fishmanRace")) raceProps = new fishmanRace();
        if(race.get().equals("giantRace")) raceProps = new giantRace();
        if(race.get().equals("humanRace")) raceProps = new humanRace();
        if(race.get().equals("minkRace")) raceProps = new minkRace();

        return raceProps;
    }

    public raceHandler getRaceByName(String name) {
        raceHandler raceProps = null;

        if(name.equals("fishmanRace")) raceProps = new fishmanRace();
        if(name.equals("giantRace")) raceProps = new giantRace();
        if(name.equals("humanRace")) raceProps = new humanRace();
        if(name.equals("minkRace")) raceProps = new minkRace();

        if(name.equals("Homem Peixe")) raceProps = new fishmanRace();
        if(name.equals("Gigante")) raceProps = new giantRace();
        if(name.equals("Humano")) raceProps = new humanRace();
        if(name.equals("Mink")) raceProps = new minkRace();

        return raceProps;
    }

    public String[] listAll() {
        String[] races = {"fishmanRace", "giantRace", "humanRace", "minkRace"};

        return races;
    }
}
