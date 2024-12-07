package com.kaykype.kponepiecemod.client.races;

public class minkRace extends raceHandler {
    public minkRace () {
        super();
    }

    public float strBuff() {
        return 1.15f;
    }

    @Override
    public float conBuff() {
        return 0.9f;
    }

    @Override
    public float dexBuff() {
        return 1.35f;
    }

    @Override
    public float spiBuff() {
        return 1.0f;
    }

    @Override
    public String displayName() { return "Mink"; }

    @Override
    public String name() { return "minkRace"; }
}
