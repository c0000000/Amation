package it.itsrizzoli.amation.model;

// Classe interna per rappresentare il tempo totale per giorno
public class TotaleTempo {
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