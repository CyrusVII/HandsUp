package com.HandsUp.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "utente")
public class Utente {

    @Id
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 255)
    private String username;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "cognome", length = 255)
    private String cognome;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "data_nascita")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascita;

    @Column(name = "citta", length = 255)
    private String citta;

    @Column(name = "biografia", columnDefinition = "TEXT")
    private String biografia;

    @Column(name = "competenze", columnDefinition = "TEXT")
    private String competenze;

    @Column(name = "curriculum", columnDefinition = "TEXT")
    private String curriculum;

    @Column(name = "immagine", columnDefinition = "MEDIUMBLOB")
    private byte[] immagine;

    @ManyToOne
    @JoinColumn(name = "id_attivita")
    private AttivitaVolontariato attivitaVolontariato;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "utente_role",
               joinColumns = @JoinColumn(name = "utente_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonBackReference
    private Set<Role> roles = new HashSet<>();


    public Utente(String emailUtente) {
		this.email=emailUtente;
	}
    public Utente() {
    	
    }

	// Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getCompetenze() {
        return competenze;
    }

    public void setCompetenze(String competenze) {
        this.competenze = competenze;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    public AttivitaVolontariato getAttivitaVolontariato() {
        return attivitaVolontariato;
    }

    public void setAttivitaVolontariato(AttivitaVolontariato attivitaVolontariato) {
        this.attivitaVolontariato = attivitaVolontariato;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

