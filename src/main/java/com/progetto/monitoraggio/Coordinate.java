package com.progetto.monitoraggio;

public class Coordinate {
    private float latitudine;
    private float longitudine;

    public Coordinate(float latitudine, float longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }
}
