package com.HandsUp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Utente;
import com.HandsUp.repository.AziendaRepository;
import com.HandsUp.repository.UtenteRepository;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@CrossOrigin
@RequestMapping("/azienda")
public class AziendaController {

    private static final Logger logger = LoggerFactory.getLogger(AziendaController.class);

    @Autowired
    private AziendaRepository aziendaRepository;
    
    @GetMapping("/{username}/profile-picture")
    public ResponseEntity<byte[]> getUserProfilePicture(@PathVariable String username) {
        if (username == null || username.isBlank()) {
            logger.warn("Richiesta per immagine profilo con username nullo o vuoto");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("Richiesta per immagine profilo per l'utente: {}", username);
        Optional<Azienda> userOptional = aziendaRepository.findByUsername(username);
        if (userOptional.isPresent()) {
        	Azienda azienda = userOptional.get();
            byte[] image = azienda.getImmagine();
            if (image != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "image/jpeg");
                logger.info("Immagine del profilo trovata per l'utente: {}", username);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } else {
                logger.warn("Immagine del profilo non trovata per l'utente: {}", username);
            }
        } else {
            logger.warn("Utente non trovato con username: {}", username);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
