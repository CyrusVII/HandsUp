package com.HandsUp.service;

import org.springframework.stereotype.Service;

import com.HandsUp.entities.Azienda;


@Service
public interface AziendaService {
	
	
    public Azienda getCompanyByEmail(String email);
    
    Azienda save(Azienda company);
}
