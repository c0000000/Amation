package it.itsrizzoli.amation.model;

import com.google.gson.annotations.SerializedName;

// Classe interna per rappresentare il tempo totale per giorno
public class TotaleTempo {
    @SerializedName("tempo_s")
    private int tempoS; // Tempo in secondi
    private int giorno; // Giorno

    // Getter e Setter
    public int getTempoS() {
        return tempoS;
    }

    public void setTempoS(int tempoS) {
        this.tempoS = tempoS;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }
}