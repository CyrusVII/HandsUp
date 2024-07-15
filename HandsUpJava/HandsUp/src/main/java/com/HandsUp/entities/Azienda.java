package com.HandsUp.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "azienda")
public class Azienda {

    @Id
    @Column(name = "id_azienda")
    private Long idAzienda;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "nome_azienda", nullable = false, length = 255)
    private String nomeAzienda;

    @Column(name = "username", nullable = false, unique = true, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255) // Aggiunto il campo password
    private String password;

    @Column(name = "data_creazione",nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataCreazione;

    @Column(name = "area_competenza", length = 255)
    private String areaCompetenza;

    @Column(name = "immagine", columnDefinition = "MEDIUMBLOB")
    private byte[] immagine;

    @ManyToOne
    @JoinColumn(name = "id_attivita")
    private AttivitaVolontariato attivitaVolontariato;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "companies_roles",
        joinColumns = @JoinColumn(name = "company_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles  = new HashSet<>();

    // Getters and Setters
    public Long getIdAzienda() {
        return idAzienda;
    }

    public void setIdAzienda(Long idAzienda) {
        this.idAzienda = idAzienda;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }

    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getAreaCompetenza() {
        return areaCompetenza;
    }

    public void setAreaCompetenza(String areaCompetenza) {
        this.areaCompetenza = areaCompetenza;
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



