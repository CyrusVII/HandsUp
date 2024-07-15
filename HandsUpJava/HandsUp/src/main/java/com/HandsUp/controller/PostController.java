package com.HandsUp.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HandsUp.entities.Commento;
import com.HandsUp.entities.Post;
import com.HandsUp.entities.Utente;
import com.HandsUp.repository.CommentoRepository;
import com.HandsUp.repository.PostRepository;
import com.HandsUp.service.PostService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;
    @Autowired
    private CommentoRepository commentoRepository;

    // API per ottenere tutti i post
    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        logger.info("Richiesta per ottenere tutti i post");
        List<Post> posts = postRepository.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PostMapping("/users/{username}/posts/{postId}/delete")
    public String deletePost(@PathVariable Long postId,
                             @PathVariable String username,
                             RedirectAttributes redirectAttributes) {
        try {
            // Log per tracciare l'inizio dell'eliminazione del post
            logger.info("Inizio eliminazione del post con ID {} per l'utente {}", postId, username);

            // Verifica se il post esiste
            Optional<Post> optionalPost = postRepository.findById(postId);
            if (!optionalPost.isPresent()) {
                // Log se il post non è trovato
                logger.warn("Post non trovato con ID {}", postId);
                return "redirect:/index"; // Redirect alla pagina index se il post non è trovato
            }

            Post post = optionalPost.get();

            // Verifica che l'utente abbia il permesso di eliminare il post
            if (!post.getUtente().getUsername().equals(username.trim())) {
                // Log se l'utente non è autorizzato a eliminare il post
                logger.warn("Utente non autorizzato a eliminare il post con ID {}", postId);
                return "redirect:/index"; // Redirect alla pagina index se l'utente non è autorizzato
            }

            // Esegui l'eliminazione del post
            postRepository.delete(post);

            // Log se il post è stato eliminato con successo
            logger.info("Post con ID {} eliminato con successo dall'utente {}", postId, username);

            // Aggiungi un messaggio di successo da mostrare sulla pagina index
            redirectAttributes.addFlashAttribute("successMessage", "Post eliminato con successo.");

            return "redirect:/index"; // Redirect alla pagina index dopo aver eliminato il post

        } catch (Exception e) {
            // Log dell'errore durante l'eliminazione del post
            logger.error("Errore durante l'eliminazione del post con ID {} per l'utente {}: {}", postId, username, e.getMessage(), e);

            // Aggiungi un messaggio di errore da mostrare sulla pagina index
            redirectAttributes.addFlashAttribute("errorMessage", "Errore durante l'eliminazione del post.");

            return "redirect:/index"; // Redirect alla pagina index in caso di errore
        }
    }
    
    @PostMapping("/azienda/{username}/posts/{postId}/delete")
    public String deletePostA(@PathVariable Long postId,
                             @PathVariable String username,
                             RedirectAttributes redirectAttributes) {
        try {
            // Log per tracciare l'inizio dell'eliminazione del post
            logger.info("Inizio eliminazione del post con ID {} per l'utente {}", postId, username);

            // Verifica se il post esiste
            Optional<Post> optionalPost = postRepository.findById(postId);
            if (!optionalPost.isPresent()) {
                // Log se il post non è trovato
                logger.warn("Post non trovato con ID {}", postId);
                return "redirect:/index"; // Redirect alla pagina index se il post non è trovato
            }

            Post post = optionalPost.get();

            // Verifica che l'utente abbia il permesso di eliminare il post
            if (!post.getAzienda().getUsername().equals(username.trim())) {
                // Log se l'utente non è autorizzato a eliminare il post
                logger.warn("Utente non autorizzato a eliminare il post con ID {}", postId);
                return "redirect:/index"; // Redirect alla pagina index se l'utente non è autorizzato
            }

            // Esegui l'eliminazione del post
            postRepository.delete(post);

            // Log se il post è stato eliminato con successo
            logger.info("Post con ID {} eliminato con successo dall'utente {}", postId, username);

            // Aggiungi un messaggio di successo da mostrare sulla pagina index
            redirectAttributes.addFlashAttribute("successMessage", "Post eliminato con successo.");

            return "redirect:/index"; // Redirect alla pagina index dopo aver eliminato il post

        } catch (Exception e) {
            // Log dell'errore durante l'eliminazione del post
            logger.error("Errore durante l'eliminazione del post con ID {} per l'utente {}: {}", postId, username, e.getMessage(), e);

            // Aggiungi un messaggio di errore da mostrare sulla pagina index
            redirectAttributes.addFlashAttribute("errorMessage", "Errore durante l'eliminazione del post.");

            return "redirect:/index"; // Redirect alla pagina index in caso di errore
        }
    }
    
    @GetMapping("/users/{username}/posts/{postId}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable String username, @PathVariable Long postId) {
        logger.info("Richiesta per ottenere l'immagine del post con id: {} per l'utente: {}", postId, username);
        try {
            // Qui implementa la logica per recuperare l'immagine associata al post dal repository o da dove è memorizzata
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null && post.getImmagine() != null) {
                // Assume che l'immagine sia memorizzata come byte array
                byte[] imageBytes = post.getImmagine();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Imposta il tipo di contenuto dell'immagine

                logger.info("Immagine del post con id: {} ottenuta con successo per l'utente: {}", postId, username);
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } else {
                // Gestione nel caso in cui l'immagine non sia disponibile per il post specificato
                logger.warn("Impossibile trovare l'immagine per il post con id: {} per l'utente: {}", postId, username);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Errore durante il recupero dell'immagine del post con id: {} per l'utente: {}: {}", postId, username, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/azienda/{username}/posts/{postId}/image")
    public ResponseEntity<byte[]> getPostImageA(@PathVariable String username, @PathVariable Long postId) {
        logger.info("Richiesta per ottenere l'immagine del post con id: {} per l'utente: {}", postId, username);
        try {
            // Qui implementa la logica per recuperare l'immagine associata al post dal repository o da dove è memorizzata
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null && post.getImmagine() != null) {
                // Assume che l'immagine sia memorizzata come byte array
                byte[] imageBytes = post.getImmagine();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Imposta il tipo di contenuto dell'immagine

                logger.info("Immagine del post con id: {} ottenuta con successo per l'utente: {}", postId, username);
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } else {
                // Gestione nel caso in cui l'immagine non sia disponibile per il post specificato
                logger.warn("Impossibile trovare l'immagine per il post con id: {} per l'utente: {}", postId, username);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Errore durante il recupero dell'immagine del post con id: {} per l'utente: {}: {}", postId, username, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/{id}/dislike")
    public ResponseEntity<?> dislikePost(@PathVariable("id") Long id, HttpSession session) {
        Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return new ResponseEntity<>("Utente non autenticato", HttpStatus.UNAUTHORIZED);
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post non trovato con id: " + id));
        
        post.decrementDislikes(); // Metodo per decrementare i Non Mi Piace nel modello Post

        postRepository.save(post); // Salva il post aggiornato nel repository

        return ResponseEntity.ok(post); // Restituisce il post aggiornato come risposta JSON
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable("id") Long id, HttpSession session) {
        Utente loggedInUser = (Utente) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return new ResponseEntity<>("Utente non autenticato", HttpStatus.UNAUTHORIZED);
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post non trovato con id: " + id));

        post.incrementLikes(); // Metodo per incrementare i Mi Piace nel modello Post

        postRepository.save(post); // Salva il post aggiornato nel repository

        return ResponseEntity.ok(post); // Restituisce il post aggiornato come risposta JSON
    }

}
