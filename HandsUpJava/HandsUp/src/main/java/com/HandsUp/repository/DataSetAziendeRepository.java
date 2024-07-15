package com.HandsUp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.HandsUp.entities.DataSetAziende;


@Repository
public interface DataSetAziendeRepository extends JpaRepository<DataSetAziende, Long>{

	boolean existsByCodiceFiscale(long codiceFiscale);
}
