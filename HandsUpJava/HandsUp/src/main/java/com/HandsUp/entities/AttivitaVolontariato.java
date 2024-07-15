package com.HandsUp.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "attivita_volontariato")
public class AttivitaVolontariato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attivita")
    private Long idAttivita;

    @Column(name = "titolo", length = 255)
    private String titolo;

    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;

    @Column(name = "data_inizio")
    @Temporal(TemporalType.DATE)
    private Date dataInizio;

    @Column(name = "data_fine")
    @Temporal(TemporalType.DATE)
    private Date dataFine;

    @Column(name = "luogo", length = 255)
    private String luogo;

    @Column(name = "posti_disponibili")
    private int postiDisponibili;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_azienda", referencedColumnName = "email")
    @JsonBackReference
    private Azienda azienda;

    @OneToMany(mappedBy = "attivitaVolontariato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Utente> utenti = new HashSet<>();

    // Getters and Setters
    public Long getIdAttivita() {
        return idAttivita;
    }

    public void setIdAttivita(Long idAttivita) {
        this.idAttivita = idAttivita;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public Azienda getAzienda() {
		return azienda;
	}

	public void setAzienda(Azienda azienda) {
		this.azienda = azienda;
	}

	public Set<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(Set<Utente> utenti) {
        this.utenti = utenti;
    }

}