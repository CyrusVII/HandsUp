package com.HandsUp.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.HandsUp.entities.AttivitaVolontariato;
import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Commento;
import com.HandsUp.entities.Post;
import com.HandsUp.entities.Utente;
import com.HandsUp.repository.AziendaRepository;
import com.HandsUp.repository.CommentoRepository;
import com.HandsUp.repository.PostRepository;
import com.HandsUp.repository.RoleRepository;
import com.HandsUp.repository.UtenteRepository;
import com.HandsUp.service.AttivitaVolontariatoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private UtenteRepository repoUser;
    
    @Autowired
    private AziendaRepository repoAzienda;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentoRepository commentoRepository;
    @Autowired
	private AttivitaVolontariatoService attivitaVolontariatoRepository;
    
   
    public AppController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/")
    public String viewHomePage() {
        logger.info("Accesso alla pagina di login");
        return "login";
    }
    
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
        Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");

        Iterable<Post> posts = postRepository.findAll();

        List<Post> sortedPosts = StreamSupport.stream(posts.spliterator(), false)
                .sorted(Comparator.comparing(Post::getDataPubblicazione).reversed())
                .collect(Collectors.toList());

        // Recupera i commenti per ciascun post
        sortedPosts.forEach(post -> {
            List<Commento> commenti = commentoRepository.findByPost(post);
            post.setCommenti(commenti); // Imposta i commenti sul post
        });

        model.addAttribute("posts", sortedPosts);

        if (loggedInUser != null) {
            logger.info("Utente loggato: {}", loggedInUser.getUsername());
            logger.info("Utente Type: {}", loggedInUser.getRoles());
            model.addAttribute("username", loggedInUser.getUsername());
            model.addAttribute("profileImage", loggedInUser.getImmagine());
            model.addAttribute("userType", "ROLE_USER");
            
            if (loggedInUser.getAttivitaVolontariato() != null && loggedInUser.getAttivitaVolontariato().getIdAttivita() != null) {
                logger.info("Utente candidato: {}", loggedInUser.getAttivitaVolontariato().getIdAttivita());
                model.addAttribute("candidato", loggedInUser.getAttivitaVolontariato().getIdAttivita());
            }
        } else if (loggedInCompany != null) {
            logger.info("Azienda loggata: {}", loggedInCompany.getEmail());
            logger.info("Utente Type: {}", loggedInCompany.getRoles());
            model.addAttribute("username", loggedInCompany.getUsername());
            model.addAttribute("profileImage", loggedInCompany.getImmagine());
            model.addAttribute("citta", loggedInCompany.getAreaCompetenza());
            model.addAttribute("userType", "ROLE_COMPANY");
        } else {
            logger.info("Nessun utente o azienda loggati, reindirizzamento alla pagina di login");
            return "redirect:/";
        }


        return "index";
    }    
    
    @GetMapping("/profilo")
    public String viewProfilo(Model model, HttpSession session) {
    	
    	 Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
         Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");
         if (loggedInUser != null) {
             logger.info("Utente loggato: {}", loggedInUser.getUsername());
             List<Post> userPosts = postRepository.findByUtenteOrderByDataPubblicazioneDesc(loggedInUser);
             
             logger.info("Utente Type: {}", loggedInUser.getRoles());
             model.addAttribute("postsPU", userPosts);
             model.addAttribute("username", loggedInUser.getUsername());
             model.addAttribute("profileImage", loggedInUser.getImmagine());
             model.addAttribute("email", loggedInUser.getEmail());
             model.addAttribute("nome", loggedInUser.getNome());
             model.addAttribute("cognome", loggedInUser.getCognome());
             model.addAttribute("DataNascita", loggedInUser.getDataNascita());
             model.addAttribute("citta", loggedInUser.getCitta());
             if (loggedInUser.getAttivitaVolontariato() != null && loggedInUser.getAttivitaVolontariato().getIdAttivita() != null) {
                 logger.info("Utente candidato: {}", loggedInUser.getAttivitaVolontariato().getIdAttivita());
                 model.addAttribute("candidato", loggedInUser.getAttivitaVolontariato().getIdAttivita());
             }
             model.addAttribute("userType", "ROLE_USER");
             return "profilo";
         } else if (loggedInCompany != null) {
             logger.info("Azienda loggata: {}", loggedInCompany.getEmail());
             List<Post> aziendaPosts = postRepository.findByAziendaOrderByDataPubblicazioneDesc(loggedInCompany);
             model.addAttribute("postsPU", aziendaPosts);
             logger.info("Utente Type: {}", loggedInCompany.getRoles());
             model.addAttribute("username", loggedInCompany.getUsername());
             model.addAttribute("profileImage", loggedInCompany.getImmagine());
             model.addAttribute("email", loggedInCompany.getEmail());
             model.addAttribute("Id", loggedInCompany.getIdAzienda());
             model.addAttribute("nome", loggedInCompany.getNomeAzienda());
             model.addAttribute("DataNascita", loggedInCompany.getDataCreazione());
             model.addAttribute("citta", loggedInCompany.getAreaCompetenza());
             model.addAttribute("userType", "ROLE_COMPANY");
             return "profiloAzienda";
         } else {
             logger.info("Nessun utente o azienda loggati, reindirizzamento alla pagina di login");
             return "redirect:/";
         }
        
}
    
    @GetMapping("/PaginaPerLaCandidaturaVolontariato")
    public String viewAttivita(Model model, HttpSession session) {
    	 Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
         Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");
         
      // Recupera le attività
         Iterable<AttivitaVolontariato> attivita = attivitaVolontariatoRepository.findAll();
         List<AttivitaVolontariato> sortedAttivita = StreamSupport.stream(attivita.spliterator(), false)
                 .sorted(Comparator.comparing(AttivitaVolontariato::getDataInizio)) // Ordina per data di inizio, per esempio
                 .collect(Collectors.toList());

         model.addAttribute("attivitaList", sortedAttivita);
         
         if (loggedInUser != null) {
             logger.info("Utente loggato: {}", loggedInUser.getUsername());
             logger.info("Utente Type: {}", loggedInUser.getRoles());
             model.addAttribute("username", loggedInUser.getUsername());
             model.addAttribute("profileImage", loggedInUser.getImmagine());
             model.addAttribute("userType", "ROLE_USER");
             
             if (loggedInUser.getAttivitaVolontariato() != null && loggedInUser.getAttivitaVolontariato().getIdAttivita() != null) {
                 logger.info("Utente candidato: {}", loggedInUser.getAttivitaVolontariato().getIdAttivita());
                 model.addAttribute("candidato", loggedInUser.getAttivitaVolontariato().getIdAttivita());
             }
             return "PaginaPerLaCandidaturaVolontariato";
         } else if (loggedInCompany != null) {
             logger.info("Azienda loggata: {}", loggedInCompany.getEmail());
             logger.info("Utente Type: {}", loggedInCompany.getRoles());
             model.addAttribute("username", loggedInCompany.getUsername());
             model.addAttribute("profileImage", loggedInCompany.getImmagine());
             model.addAttribute("citta", loggedInCompany.getAreaCompetenza());
             model.addAttribute("userType", "ROLE_COMPANY");
             return "PaginaPerLaCandidaturaVolontariato";
         } else {
             logger.info("Nessun utente o azienda loggati, reindirizzamento alla pagina di login");
             return "/";
         }       
}
    
    @GetMapping("/ChiSiamo")
    public String viewChiSiamo(Model model, HttpSession session) {
    	 Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
         Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");
         
      // Recupera le attività
         Iterable<AttivitaVolontariato> attivita = attivitaVolontariatoRepository.findAll();
         List<AttivitaVolontariato> sortedAttivita = StreamSupport.stream(attivita.spliterator(), false)
                 .sorted(Comparator.comparing(AttivitaVolontariato::getDataInizio)) // Ordina per data di inizio, per esempio
                 .collect(Collectors.toList());

         model.addAttribute("attivitaList", sortedAttivita);
         
         if (loggedInUser != null) {
             logger.info("Utente loggato: {}", loggedInUser.getUsername());
             logger.info("Utente Type: {}", loggedInUser.getRoles());
             model.addAttribute("username", loggedInUser.getUsername());
             model.addAttribute("profileImage", loggedInUser.getImmagine());
             if (loggedInUser.getAttivitaVolontariato() != null && loggedInUser.getAttivitaVolontariato().getIdAttivita() != null) {
                 logger.info("Utente candidato: {}", loggedInUser.getAttivitaVolontariato().getIdAttivita());
                 model.addAttribute("candidato", loggedInUser.getAttivitaVolontariato().getIdAttivita());
             }
             model.addAttribute("userType", "ROLE_USER");
             return "ChiSiamo";
         } else if (loggedInCompany != null) {
             logger.info("Azienda loggata: {}", loggedInCompany.getEmail());
             logger.info("Utente Type: {}", loggedInCompany.getRoles());
             model.addAttribute("username", loggedInCompany.getUsername());
             model.addAttribute("profileImage", loggedInCompany.getImmagine());
             model.addAttribute("citta", loggedInCompany.getAreaCompetenza());
             model.addAttribute("userType", "ROLE_COMPANY");
             return "ChiSiamo";
         } else {
             logger.info("Nessun utente o azienda loggati, reindirizzamento alla pagina di login");
             return "/";
         }       
}
    
    @GetMapping("/PaginaWebPerlerichieste")
    public String viewAttivitaA(Model model, HttpSession session) {
         Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");
         
      // Recupera le attività
         Iterable<AttivitaVolontariato> attivita = attivitaVolontariatoRepository.findAll();
         List<AttivitaVolontariato> sortedAttivita = StreamSupport.stream(attivita.spliterator(), false)
                 .sorted(Comparator.comparing(AttivitaVolontariato::getDataInizio)) // Ordina per data di inizio, per esempio
                 .collect(Collectors.toList());

         model.addAttribute("attivitaList", sortedAttivita);
         
         if (loggedInCompany != null) {
             logger.info("Azienda loggata: {}", loggedInCompany.getEmail());
             logger.info("Utente Type: {}", loggedInCompany.getRoles());
             model.addAttribute("username", loggedInCompany.getUsername());
             model.addAttribute("profileImage", loggedInCompany.getImmagine());
             model.addAttribute("citta", loggedInCompany.getAreaCompetenza());
             model.addAttribute("userType", "ROLE_COMPANY");
             return "PaginaWebPerlerichieste";
         } else {
             logger.info("Nessun utente o azienda loggati, reindirizzamento alla pagina di login");
             return "/";
         }       
}
}
