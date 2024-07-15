package com.HandsUp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Post;
import com.HandsUp.entities.Utente;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Non è necessario aggiungere ulteriori metodi qui, JpaRepository fornisce già i metodi CRUD base.
	List<Post> findByUtenteOrderByDataPubblicazioneDesc(Utente utente);

	List<Post> findByAziendaOrderByDataPubblicazioneDesc(Azienda azienda);
}
