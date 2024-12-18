package it.itsrizzoli.amation.model;

import java.util.List;

// Modello per rappresentare il JSON
public class UserModel {
    private String nome; // Nome dell'utente
    private String cognome; // Cognome dell'utente
    private String dataNascita; // Data di nascita dell'utente
    private String username; // Username dell'utente
    private String email; // Email dell'utente
    private String password; // Password dell'utente
    private List<TotaleTempo> totaleTempo; // Lista di oggetti rappresentanti il tempo totale per giorno

    // Getter e Setter per la classe User
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TotaleTempo> getTotaleTempo() {
        return totaleTempo;
    }

    public void setTotaleTempo(List<TotaleTempo> totaleTempo) {
        this.totaleTempo = totaleTempo;
    }


}
