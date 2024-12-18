package it.itsrizzoli.amation.model;


public class AnimeRanking {

    private int idAnime;
    private int rank;

    public AnimeRanking(int idAnime, int rank) {
        this.idAnime = idAnime;
        this.rank = rank;
    }

    public int getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(int idAnime) {
        this.idAnime = idAnime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "idAnime=" + idAnime +
                ", rank=" + rank +
                '}';
    }
}