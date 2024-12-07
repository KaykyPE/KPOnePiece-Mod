package com.kaykype.kponepiecemod.client.races;

public class fishmanRace extends raceHandler {
    public fishmanRace () {
        super();
    }

    @Override
    public float strBuff() {
        return 1.2f;
    }

    @Override
    public float conBuff() {
        return 1.15f;
    }

    @Override
    public float dexBuff() {
        return 0.90f;
    }

    @Override
    public float spiBuff() {
        return 0.85f;
    }

    @Override
    public String displayName() { return "Homem Peixe"; }

    @Override
    public String name() { return "fishmanRace"; }
}
