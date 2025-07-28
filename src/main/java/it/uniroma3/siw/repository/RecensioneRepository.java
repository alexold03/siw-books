package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;

public interface RecensioneRepository extends CrudRepository <Recensione,Long> {

	// Verifica se un utente ha già recensito un libro
	public Optional<Recensione> findByAutoreAndLibro(User autore, Libro libro);
}
