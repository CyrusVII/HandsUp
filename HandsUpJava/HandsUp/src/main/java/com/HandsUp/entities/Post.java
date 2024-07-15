package com.HandsUp.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "testo", columnDefinition = "TEXT")
    private String testo;

    @Column(name = "data_pubblicazione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPubblicazione;

    @Column(name = "mi_piace")
    private Integer miPiace=0;

    @Column(name = "non_mi_piace")
    private Integer nonMiPiace=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_utente", referencedColumnName = "email") 
    @JsonBackReference
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_azienda", referencedColumnName = "email")
    @JsonBackReference
    private Azienda azienda;

    @Column(name = "immagine", columnDefinition = "MEDIUMBLOB")
    private byte[] immagine;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Commento> commenti;

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

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(Date dataPubblicazione) {
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

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }
    
    @PrePersist
    protected void onCreate() {
        this.dataPubblicazione = new Date();
    }

	public void decrementDislikes() {
		this.nonMiPiace++;
		
	}

	public void incrementLikes() {
		this.miPiace++;		
	}
	
	public List<Commento> getCommenti() {
        return commenti;
    }

    public void setCommenti(List<Commento> commenti) {
        this.commenti = commenti;
    }
}

