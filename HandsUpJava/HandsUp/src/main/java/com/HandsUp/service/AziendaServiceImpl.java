package com.HandsUp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HandsUp.entities.Azienda;
import com.HandsUp.repository.AziendaRepository;

@Service
public class AziendaServiceImpl implements AziendaService {
    
    @Autowired
    private AziendaRepository aziendaRepository;

    @Override
    public Azienda getCompanyByEmail(String email) {
        return aziendaRepository.findByEmail(email);
    }

	@Override
	public Azienda save(Azienda company) {
		return aziendaRepository.save(company);
	}
}