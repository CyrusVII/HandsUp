package com.HandsUp.service;

import org.springframework.stereotype.Service;

import com.HandsUp.entities.Utente;

@Service
public interface UtenteService {

	Utente getUserByEmail(String email);

	Utente save(Utente user);
	
	public byte[] getProfileImage(String email);

}
