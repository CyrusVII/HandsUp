
package com.HandsUp.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.HandsUp.entities.AttivitaVolontariato;
import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Utente;
import com.HandsUp.repository.AttivitaVolontariatoRepository;
import com.HandsUp.repository.UtenteRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class AttivitaVolontariatoController {

	 private static final Logger logger = LoggerFactory.getLogger(UploadPostController.class);
	
	@Autowired
	private AttivitaVolontariatoRepository repo;
    @Autowired
    private UtenteRepository userRepository;
	
    @PostMapping("/creaAttivita")
    public String creaAttivitaVolontariato(@RequestParam("titolo") String titolo,
                                           @RequestParam("descrizione") String descrizione,
                                           @RequestParam("data_inizio") String dataInizioStr,
                                           @RequestParam("data_fine") String dataFineStr,
                                           
                                           Model model, HttpSession session) {
        
        try {
            logger.info("Inizio creazione attività di volontariato");
            
            Azienda loggedInCompany = (Azienda) session.getAttribute("loggedInCompany");
            if (loggedInCompany == null) {
                logger.error("Nessuna azienda loggata trovata nella sessione");
                model.addAttribute("error", "Nessuna azienda loggata trovata nella sessione.");
                return "/";
            }
            
            AttivitaVolontariato attivita = new AttivitaVolontariato();
            // Associa l'attività all'azienda loggata
            attivita.setAzienda(loggedInCompany);
            attivita.setTitolo(titolo);
            attivita.setDescrizione(descrizione);
            attivita.setLuogo(loggedInCompany.getAreaCompetenza());
            
            
         // Converti le stringhe delle date in LocalDate
            LocalDate dataInizio = LocalDate.parse(dataInizioStr);
            LocalDate dataFine = LocalDate.parse(dataFineStr);

            // Converti LocalDate in java.sql.Date
            Date dataInizioDate = Date.valueOf(dataInizio);
            Date dataFineDate = Date.valueOf(dataFine);
            
            attivita.setDataInizio(dataInizioDate);
            attivita.setDataFine(dataFineDate);
            

            
            // Salva l'attività di volontariato
            repo.save(attivita);
            System.out.println("l attivita: "+attivita);
            logger.info("Attività di volontariato creata con successo: {}", attivita);
            
            return "redirect:/index";
            
        } catch (Exception e) {
            logger.error("Errore durante la creazione dell'attività di volontariato", e);
            model.addAttribute("error", "Errore durante la creazione dell'attività di volontariato.");
            return "index";
        }
    }
    
    
	  @GetMapping("/trovaAttivita")
	  public ResponseEntity<List<AttivitaVolontariato>> getAllPosts() {
	        logger.info("Richiesta per ottenere tutti i post");
	        List<AttivitaVolontariato> posts = repo.findAll();
	        return ResponseEntity.ok(posts);
	    }
	  
	  @PostMapping("/users/{username}/{idAttivita}/aggiungi-utente")
	  public ResponseEntity<AttivitaVolontariato> aggiungiUtenteAllAttivita(
	          @PathVariable("idAttivita") Long idAttivita,
	          @PathVariable("username") String username) {

	      // Trova l'attività di volontariato per ID
	      AttivitaVolontariato attivita = repo.findById(idAttivita)
	              .orElseThrow(() -> new RuntimeException("Attività di volontariato non trovata con id: " + idAttivita));

	      // Trova l'utente per username
	      Utente utente = userRepository.findByUsername(username)
	              .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));

	      // Aggiungi l'attività di volontariato all'utente
	      utente.setAttivitaVolontariato(attivita);

	      // Aggiungi l'utente all'attività di volontariato
	      attivita.getUtenti().add(utente);

	      // Salva l'attività aggiornata (se necessario)
	      AttivitaVolontariato attivitaSalvata = repo.save(attivita);

	      return ResponseEntity.ok(attivitaSalvata);
	  }

	  
	}
