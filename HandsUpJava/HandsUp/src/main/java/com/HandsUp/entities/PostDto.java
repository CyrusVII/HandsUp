package com.HandsUp.entities;

public class PostDto {
    private Long id;
    private String testo;
    private String dataPubblicazione;
    private Integer miPiace;
    private Integer nonMiPiace;
    private String utenteUsername;
    private String aziendaUsername;

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

    public String getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public Integer getMiPiace() {
        return miPiace;
    }

    public void setMiPiace(Integer miPiace) {
        this.miPiace = miPiace;
    }

    public Integer getNonMiPiace() {
        return nonMiPiace;
    }

    public void setNonMiPiace(Integer nonMiPiace) {
        this.nonMiPiace = nonMiPiace;
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
}
