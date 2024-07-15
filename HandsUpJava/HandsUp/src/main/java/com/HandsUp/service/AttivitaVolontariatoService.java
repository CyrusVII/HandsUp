package com.HandsUp.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HandsUp.controller.PostController;
import com.HandsUp.entities.AttivitaVolontariato;
import com.HandsUp.repository.AttivitaVolontariatoRepository;

@Service
public class AttivitaVolontariatoService {
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
    @Autowired
    private AttivitaVolontariatoRepository attivitaVolontariatoRepository;

    public AttivitaVolontariato salvaAttivitaVolontariato(AttivitaVolontariato attivita) {
        return attivitaVolontariatoRepository.save(attivita);
    }

    public List<AttivitaVolontariato> trovaTutteAttivitaVolontariato() {
        return attivitaVolontariatoRepository.findAll();
    }

    public Optional<AttivitaVolontariato> findById(Long id) {
        return Optional.ofNullable(attivitaVolontariatoRepository.findById(id).orElse(null));
    }

	public AttivitaVolontariato save(AttivitaVolontariato attivita) {
		return attivitaVolontariatoRepository.save(attivita);
	}

	public Iterable<AttivitaVolontariato> findAll() {
	    logger.info("Richiesta per ottenere tutti i post");
	    return attivitaVolontariatoRepository.findAll();
	}
}