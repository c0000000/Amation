package it.itsrizzoli.amation.model;

import java.util.List;

public class AnimeModel {
    private int id; // L'identificativo univoco dell'anime
    private String title; // Il titolo dell'anime
    private String synopsis; // Il riassunto della trama dell'anime
    private String picture; // L'URL dell'immagine di copertina dell'anime
    private List<Character> characters; // Lista dei personaggi dell'anime
    private List<Staff> staff; // Lista dello staff dell'anime
    private String trailer; // L'URL del trailer video dell'anime
    private String englishTitle; // Il titolo in inglese dell'anime
    private String japaneseTitle; // Il titolo in giapponese dell'anime
    private List<String> synonyms; // Lista di sinonimi del titolo dell'anime
    private String type; // Tipo di anime (TV, OVA, Film, Special)
    private int episodes; // Numero di episodi andati in onda
    private String aired; // Periodo di messa in onda
    private String premiered; // Data di prima messa in onda
    private String broadcast; // Quando l'anime viene trasmesso
    private List<String> producers; // Lista dei produttori dell'anime
    private List<String> studios; // Lista degli studios dell'anime
    private String source; // Su cosa è basato l'anime (es. manga)
    private String duration; // Durata media di un episodio
    private String rating; // Classificazione di età dell'anime
    private List<String> genres; // Lista dei generi dell'anime
    private String status; // Stato dell'anime (in corso, terminato)
    private double score; // Punteggio medio dell'anime
    private String scoreStats; // Statistiche sul punteggio
    private int ranked; // Posizione nell'ranking dell'anime
    private int popularity; // Popolarità dell'anime
    private int members; // Numero di membri dell'anime
    private int favorites; // Numero di preferiti dell'anime
    private String url; // L'URL della pagina dell'anime
}
