package com.HandsUp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HandsUp.entities.AttivitaVolontariato;

@Repository
public interface AttivitaVolontariatoRepository extends JpaRepository<AttivitaVolontariato, Long> {
    // Aggiungi eventuali query personalizzate qui, se necessario
	Optional<AttivitaVolontariato> findById(Long id);
}
