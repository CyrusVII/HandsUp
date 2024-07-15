package com.HandsUp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Utente;
import com.HandsUp.repository.AziendaRepository;
import com.HandsUp.repository.UtenteRepository;
import com.HandsUp.security.DatabaseUserDetails;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtenteRepository repoUser;

    @Autowired
    private AziendaRepository repoAzienda;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/loginGo")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("userType") String userType,
                               HttpSession session) {
        logger.info("Tentativo di login con email: {} e userType: {}", email, userType);

        if ("user".equals(userType)) {
            Utente user = repoUser.findByEmail(email);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                DatabaseUserDetails userDetails = new DatabaseUserDetails(user);
                session.setAttribute("loggedInUser", user);
                session.setAttribute("authorities", userDetails.getAuthorities());
                
                logger.info("Login utente riuscito per email: {}", email);
                return "redirect:/index"; // Reindirizza alla pagina index dopo il login
            } else {
                logger.warn("Credenziali non valide per utente con email: {}", email);
            }
        } else if ("company".equals(userType)) {
            Azienda company = repoAzienda.findByEmail(email);
            if (company != null && passwordEncoder.matches(password, company.getPassword())) {
                DatabaseUserDetails companyDetails = new DatabaseUserDetails(company);
                session.setAttribute("loggedInCompany", company);
                session.setAttribute("authorities", companyDetails.getAuthorities());
              
                logger.info("Login azienda riuscito per email: {}", email);
                return "redirect:/index"; // Reindirizza alla pagina index dopo il login
            } else {
                logger.warn("Credenziali non valide per azienda con email: {}", email);
            }
        }

        // Se il login non riesce, reindirizza alla pagina di login con un parametro di errore
        logger.info("Login fallito per email: {}", email);
        return "redirect:/?error=true";
    }

    @PostMapping("/logout")
    public String processLogout(HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            logger.info("Logout utente: {}", ((Utente) session.getAttribute("loggedInUser")).getEmail());
            session.removeAttribute("loggedInUser");
        }

        if (session.getAttribute("loggedInCompany") != null) {
            logger.info("Logout azienda: {}", ((Azienda) session.getAttribute("loggedInCompany")).getEmail());
            session.removeAttribute("loggedInCompany");
        }

        // Reindirizza alla pagina di login o a una pagina pubblica
        return "redirect:/";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login"; // Questo assume che il nome della tua pagina di login sia "login.html" o "login.jsp"
    }
}
