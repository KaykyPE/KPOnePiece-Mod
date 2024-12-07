package com.kaykype.kponepiecemod.client.races;

public class humanRace extends raceHandler {
    public humanRace () {
        super();
    }

    @Override
    public float strBuff() {
        return 1.0f;
    }

    @Override
    public float conBuff() {
        return 1.0f;
    }

    @Override
    public float dexBuff() {
        return 1.0f;
    }

    @Override
    public float spiBuff() {
        return 1.2f;
    }

    @Override
    public String displayName() { return "Humano"; }

    @Override
    public String name() { return "humanRace"; }
}
