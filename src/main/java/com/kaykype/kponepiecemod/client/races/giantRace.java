package com.kaykype.kponepiecemod.client.races;

public class giantRace extends raceHandler {
    public giantRace () {
        super();
    }

    @Override
    public float strBuff() {
        return 1.3f;
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
        return 0.75f;
    }

    @Override
    public String displayName() { return "Gigante"; }

    @Override
    public String name() { return "giantRace"; }
}
