package com.HandsUp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HandsUp.entities.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String> {
	Optional<Utente> findByUsername(String username);

	Utente findByUsernameAndPassword(String username, String password);

	Utente findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
}
