package com.HandsUp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HandsUp.entities.Utente;
import com.HandsUp.repository.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {
    
    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public Utente getUserByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    @Override
    public Utente save(Utente user) {
        return utenteRepository.save(user);
    }
    

    @Override
    public byte[] getProfileImage(String email) {
        // Supponiamo che l'utente sia memorizzato con un campo `profileImage`
        Optional<Utente> user = Optional.of(utenteRepository.findByEmail(email));
        if (user.isPresent() && user.get().getImmagine() != null) {
        	 return user.get().getImmagine();
        }
        return null;
    }
}
