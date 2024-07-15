package com.HandsUp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.HandsUp.entities.Azienda;

@Repository
public interface AziendaRepository extends CrudRepository<Azienda, Integer> {

	Optional<Azienda> findByUsername(String username);

	Azienda findByUsernameAndPassword(String username, String password);

	Azienda findByEmail(String email);

	Optional<Azienda> findByNomeAzienda(String nomeAzienda);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
	
	boolean existsByIdAzienda(long idAzienda);
}
