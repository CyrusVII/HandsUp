package com.HandsUp.entities;

public class CommentoDto {
    private Long id;
    private String testo;
    private int miPiace;
    private byte[] immagine;
    private Long postId;
    private String utenteUsername;
    private String aziendaUsername;
    private String utenteImmagine; // Aggiunto campo per l'immagine dell'utente
    private String aziendaImmagine;
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public int getMiPiace() {
        return miPiace;
    }

    public void setMiPiace(int miPiace) {
        this.miPiace = miPiace;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUtenteUsername() {
        return utenteUsername;
    }

    public void setUtenteUsername(String utenteUsername) {
        this.utenteUsername = utenteUsername;
    }

    public String getAziendaUsername() {
        return aziendaUsername;
    }

    public void setAziendaUsername(String aziendaUsername) {
        this.aziendaUsername = aziendaUsername;
    }

    public String getUtenteImmagine() {
        return utenteImmagine;
    }

    public void setUtenteImmagine(String utenteImmagine) {
        this.utenteImmagine = utenteImmagine;
    }
    
    public String getAziendaImmagine() {
        return aziendaImmagine;
    }

    public void setAziendaImmagine(String aziendaImmagine) {
        this.aziendaImmagine = aziendaImmagine;
    }
}
