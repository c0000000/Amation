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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public String getJapaneseTitle() {
        return japaneseTitle;
    }

    public void setJapaneseTitle(String japaneseTitle) {
        this.japaneseTitle = japaneseTitle;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getAired() {
        return aired;
    }

    public void setAired(String aired) {
        this.aired = aired;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    public List<String> getStudios() {
        return studios;
    }

    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getScoreStats() {
        return scoreStats;
    }

    public void setScoreStats(String scoreStats) {
        this.scoreStats = scoreStats;
    }

    public int getRanked() {
        return ranked;
    }

    public void setRanked(int ranked) {
        this.ranked = ranked;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
