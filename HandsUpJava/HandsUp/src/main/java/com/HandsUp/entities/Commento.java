package com.HandsUp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "commento")
public class Commento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "testo", columnDefinition = "TEXT")
    private String testo;

    @Column(name = "mi_piace")
    private int miPiace;

    @Column(name = "immagine", columnDefinition = "MEDIUMBLOB")
    private byte[] immagine;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "username_azienda", referencedColumnName = "username")
    private Azienda azienda;

    @ManyToOne
    @JoinColumn(name = "username_utente", referencedColumnName = "username")
    private Utente utente;

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

