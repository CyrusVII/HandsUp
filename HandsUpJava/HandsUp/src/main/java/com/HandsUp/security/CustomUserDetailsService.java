package com.HandsUp.security;

import com.HandsUp.entities.Utente;
import com.HandsUp.entities.Azienda;
import com.HandsUp.repository.UtenteRepository;
import com.HandsUp.repository.AziendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private AziendaRepository aziendaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email);
        if (utente != null) {
            return new DatabaseUserDetails(utente);
        }

        Azienda azienda = aziendaRepository.findByEmail(email);
        if (azienda != null) {
            return new DatabaseUserDetails(azienda);
        }

        throw new UsernameNotFoundException("Utente non trovato con email: " + email);
    }
}


