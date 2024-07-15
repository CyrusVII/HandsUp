package com.HandsUp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.HandsUp.entities.Utente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UtenteRepositoryTest {

	@Autowired
	private UtenteRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateUser() {
		Utente user = new Utente();
		user.setEmail("prova3@gmail.com");
		user.setPassword("1234");
		user.setUsername("Alex");
		user.setCognome("maresca");
		user.setNome("Alex");
		// Conversione da LocalDate a java.sql.Date
		LocalDate localDate = LocalDate.of(2001, 9, 15);
		Date sqlDate = Date.valueOf(localDate);
		user.setDataNascita(sqlDate);
		Utente savedUser = repo.save(user);
		Utente existUser = entityManager.find(Utente.class, savedUser.getEmail());
		// Verifica che l'utente sia stato salvato correttamente
        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
	}
}
