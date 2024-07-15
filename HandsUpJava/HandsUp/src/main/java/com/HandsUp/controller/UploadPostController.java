package com.HandsUp.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Post;
import com.HandsUp.entities.Utente;
import com.HandsUp.repository.AziendaRepository;
import com.HandsUp.repository.PostRepository;
import com.HandsUp.repository.UtenteRepository;

import jakarta.servlet.http.HttpSession;
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@Controller
public class UploadPostController {
	
	@Autowired
    private PostRepository postRepo;
	@Autowired
    private UtenteRepository userRepository;
	@Autowired
    private AziendaRepository aziendaRepository;
	
	 private static final Logger logger = LoggerFactory.getLogger(UploadPostController.class);

	//loggedInCompany
    @GetMapping("/PaginaDiCaricamentoPost")
    public String mostraPaginaDiCaricamentoPost(Model model, HttpSession session) {
        Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
        Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");
        if (loggedInUser != null) {
        	logger.info("Accesso alla pagina di caricamento post per utente: {}", loggedInUser);
            model.addAttribute("username", loggedInUser.getUsername());
            model.addAttribute("profileImage", loggedInUser.getImmagine());
            model.addAttribute("userType", "ROLE_USER");
            return "PaginaDiCaricamentoPost";
        }else if(loggedInCompany != null) {
        	logger.info("Accesso alla pagina di caricamento post per utente: {}", loggedInCompany);
        	 model.addAttribute("username", loggedInCompany.getUsername());
             model.addAttribute("profileImage", loggedInCompany.getImmagine());
             model.addAttribute("userType", "ROLE_COMPANY");
            return "PaginaDiCaricamentoPost";
        }
        logger.info("Utente non autenticato, reindirizzamento alla pagina di login");
        return "redirect:/";
    }
    @PostMapping("/uploadPost")
    public String uploadPost(@RequestParam("testo") String testo,
                             @RequestParam("immagine") MultipartFile immagine,
                             Model model, HttpSession session) {
        try {
            Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
            Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");
            
            Post post = new Post();
            post.setTesto(testo);
            LocalDateTime dataCorrente = LocalDateTime.now();
            post.setDataPubblicazione(java.sql.Timestamp.valueOf(dataCorrente));
            
            if (immagine != null && !immagine.isEmpty()) {
                byte[] immagineBytes = immagine.getBytes();
                post.setImmagine(immagineBytes);
            }
            
            if (loggedInUser != null) {
                post.setUtente(loggedInUser);
            } else if (loggedInCompany != null) {
                post.setAzienda(loggedInCompany);
            }
            
            postRepo.save(post);
            logger.info("Post creato con successo: {}", post);
            
            return "redirect:/index";
        } catch (Exception e) {
            logger.error("Errore durante la creazione del post", e);
            model.addAttribute("error", "Errore durante la creazione del post.");
            return "PaginaDiCaricamentoPost";
        }
    }
    
    @PostMapping("/users/uploadProfilePicture")
    public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file, Model model, HttpSession session) {
        try {
            Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
            if (loggedInUser != null && file != null && !file.isEmpty()) {
                byte[] profilePictureBytes = file.getBytes();
                loggedInUser.setImmagine(profilePictureBytes);
                userRepository.save(loggedInUser);
                logger.info("Foto del profilo aggiornata con successo per l'utente: {}", loggedInUser.getUsername());
            }
            model.addAttribute("message", "Foto del profilo aggiornata con successo!");
            return "redirect:/profilo"; // reindirizza alla pagina del profilo
        } catch (Exception e) {
            logger.error("Errore durante l'aggiornamento della foto del profilo", e);
            model.addAttribute("error", "Errore durante l'aggiornamento della foto del profilo.");
            return "redirect:/profilo"; // reindirizza alla pagina del profilo
        }
    }
    
    @PostMapping("/azienda/uploadProfilePicture")
    public String uploadProfilePictureA(@RequestParam("profilePicture") MultipartFile file, Model model, HttpSession session) {
        try {
            Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");
            if (loggedInCompany != null && file != null && !file.isEmpty()) {
                byte[] profilePictureBytes = file.getBytes();
                loggedInCompany.setImmagine(profilePictureBytes);
                aziendaRepository.save(loggedInCompany);
                logger.info("Foto del profilo aggiornata con successo per l'utente: {}", loggedInCompany.getUsername());
            }
            model.addAttribute("message", "Foto del profilo aggiornata con successo!");
            return "redirect:/profilo"; // reindirizza alla pagina del profilo
        } catch (Exception e) {
            logger.error("Errore durante l'aggiornamento della foto del profilo", e);
            model.addAttribute("error", "Errore durante l'aggiornamento della foto del profilo.");
            return "redirect:/profilo"; // reindirizza alla pagina del profilo
        }
    }
}
