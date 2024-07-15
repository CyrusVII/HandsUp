package com.HandsUp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Role;
import com.HandsUp.entities.Utente;
import com.HandsUp.repository.AziendaRepository;
import com.HandsUp.repository.DataSetAziendeRepository;
import com.HandsUp.repository.RoleRepository;
import com.HandsUp.repository.UtenteRepository;

@Controller
public class RegisterController {
	
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
    private DataSetAziendeRepository dataRepository;


    
	 @GetMapping("/register")
	    public String showSignUpForm(Model model) {
	        logger.info("Accesso alla pagina di registrazione");
	        model.addAttribute("user", new Utente());
	        model.addAttribute("company", new Azienda());
	        return "singup";
	    }
	    
	    @PostMapping("/process_register_user")
	    public String processUserRegistration(@ModelAttribute("user") Utente user) {
	        // Critta la password prima di salvarla
	        String encodedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(encodedPassword);

	        // Assegna il ruolo di utente
	        Role userRole = roleRepository.findByName("ROLE_USER");
	        if (userRole != null) {
	            user.getRoles().add(userRole); // Aggiungi il ruolo all'utente
	        } else {
	            // Gestione se il ruolo non viene trovato
	            throw new RuntimeException("ROLE_USER non trovato. Assicurati che il ruolo sia presente nel database.");
	        }

	        repoUser.save(user);
	        return "registerSuccess";
	    }


	    @PostMapping("/process_register_company")
	    public String processCompanyRegistration(@ModelAttribute("company") Azienda company) {
	        logger.info("Registrazione azienda: {}", company.getEmail());

	        // Cripta la password prima di salvarla
	        String encodedPassword = passwordEncoder.encode(company.getPassword());
	        company.setPassword(encodedPassword);

	        // Assegna il ruolo di azienda
	        Role companyRole = roleRepository.findByName("ROLE_COMPANY");
	        if (companyRole != null) {
	            company.getRoles().add(companyRole); // Aggiungi il ruolo all'azienda
	        } else {
	            // Gestione se il ruolo non viene trovato
	            throw new RuntimeException("ROLE_COMPANY non trovato. Assicurati che il ruolo sia presente nel database.");
	        }

	        repoAzienda.save(company);
	        return "registerSuccess";
	    }
	    
	    @GetMapping("/check_email")
	    @ResponseBody
	    public boolean checkEmail(@RequestParam String email) {
	        return repoUser.existsByEmail(email) || repoAzienda.existsByEmail(email);
	    }

	    @GetMapping("/check_username")
	    @ResponseBody
	    public boolean checkUsername(@RequestParam String username) {
	        return repoUser.existsByUsername(username) || repoAzienda.existsByUsername(username);
	    }
	    
	    @GetMapping("/check_companyId")
	    @ResponseBody
	    public boolean checkCompanyId(@RequestParam long idAzienda) {
	        return repoAzienda.existsByIdAzienda(idAzienda);
	    }

	    @GetMapping("/check_companyCode")
	    @ResponseBody
	    public boolean checkCompanyCode(@RequestParam long codiceFiscale) {
	        return dataRepository.existsByCodiceFiscale(codiceFiscale);
	    }

}
