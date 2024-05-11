package com.progetto.monitoraggio;

public class Impianti {
    private String id;
    private String descrizione;
    private float latitudine;
    private float longitudine;

    public Impianti(String id, String descrizione, float latitudine, float longitudine) {
        this.id = id;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
