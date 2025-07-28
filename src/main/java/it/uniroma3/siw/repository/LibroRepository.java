package it.uniroma3.siw.repository;


import java.time.LocalDate;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Libro;

public interface LibroRepository extends CrudRepository <Libro,Long> {
	
	@Query("SELECT l FROM Libro l LEFT JOIN FETCH l.immagini ORDER BY l.id DESC")
	List<Libro> findTop3LibriConImmagini(PageRequest pageRequest);

	boolean existsByTitoloAndAnnoPubblicazione(String titolo, LocalDate annoPubblicazione);

	Page<Libro> findByTitoloContainingIgnoreCase(String titolo, Pageable pageable);

	Page<Libro> findAll(Pageable pageable);
	


}
