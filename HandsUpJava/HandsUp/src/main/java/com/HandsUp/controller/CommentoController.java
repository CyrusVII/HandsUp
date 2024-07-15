package com.HandsUp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Commento;
import com.HandsUp.entities.CommentoConverter;
import com.HandsUp.entities.CommentoDto;
import com.HandsUp.entities.Post;
import com.HandsUp.entities.Utente;
import com.HandsUp.repository.AziendaRepository;
import com.HandsUp.repository.CommentoRepository;
import com.HandsUp.repository.PostRepository;
import com.HandsUp.repository.UtenteRepository;

@RestController
public class CommentoController {
    private static final Logger logger = LoggerFactory.getLogger(CommentoController.class);
    
    @Autowired
    private CommentoRepository commentoRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UtenteRepository userRepository;
    @Autowired
    private AziendaRepository aziendaRepository;

    @GetMapping("/comments/{post_id}")
    public ResponseEntity<List<CommentoDto>> trovaCommentiPerPost(@PathVariable("post_id") Long postId) {
        logger.info("Received request for comments with post_id: {}", postId);
        List<Commento> commenti = commentoRepository.findByPostId(postId);
        logger.info("Comments found: {}", commenti.size());

        // Convert to DTOs
        List<CommentoDto> commentiDto = commenti.stream()
                .map(CommentoConverter::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentiDto);
    }

    @PutMapping("/comments/crea/{post_id}")
    public ResponseEntity<Commento> creaCommento(
            @PathVariable("post_id") Long postId,
            @RequestParam("utente_username") String utenteUsername,
            @RequestParam("testo") String testo) {

        logger.info("Received request to create comment for post_id: {}", postId);

        // Trova il post associato all'id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post non trovato con id: " + postId));

        // Trova l'utente associato all'utente_username
        Utente utente = userRepository.findByUsername(utenteUsername)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con username: " + utenteUsername));

        // Costruisci il Commento
        Commento commento = new Commento();
        commento.setPost(post);
        commento.setUtente(utente);
        commento.setTesto(testo);

        // Esegui il salvataggio del commento nel repository
        Commento savedComment = commentoRepository.save(commento);

        logger.info("Comment created successfully with id: {}", savedComment.getId());

        // Ritorna la risposta con il commento salvato e lo status HTTP 201 Created
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
    @PutMapping("/commentsA/crea/{post_id}")
    public ResponseEntity<Commento> creaCommentoA(
            @PathVariable("post_id") Long postId,
            @RequestParam("azienda_username") String aziendaUsername,
            @RequestParam("testo") String testo) {

        logger.info("Received request to create comment for post_id: {}", postId);

        // Trova il post associato all'id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post non trovato con id: " + postId));

        // Trova l'utente associato all'utente_username
        Azienda azienda = aziendaRepository.findByUsername(aziendaUsername)
                .orElseThrow(() -> new IllegalArgumentException("Azienda non trovato con username: " + aziendaUsername));

        // Costruisci il Commento
        Commento commento = new Commento();
        commento.setPost(post);
        commento.setAzienda(azienda);
        commento.setTesto(testo);

        // Esegui il salvataggio del commento nel repository
        Commento savedComment = commentoRepository.save(commento);

        logger.info("Comment created successfully with id: {}", savedComment.getId());

        // Ritorna la risposta con il commento salvato e lo status HTTP 201 Created
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
}
