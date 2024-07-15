package com.HandsUp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dati_aziende")
public class DataSetAziende {

	@Id
	@Column(nullable=false)
	private Long codiceFiscale;

    private String denominazione;
	
    private String indirizzo;
	
    private String citta;
	
    private String provincia;
	
    private String cap;

    private String tipo;
	
	public DataSetAziende() {
		
	}

	public DataSetAziende(Long codiceFiscale, String denominazione, String indirizzo, String citta, String provincia,
			String cap, String tipo) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.citta = citta;
		this.provincia = provincia;
		this.cap = cap;
		this.tipo = tipo;
	}

	public Long getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(Long codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
