package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Libro;

public interface AutoreRepository extends CrudRepository <Autore,Long> {
	List<Autore> findTop3ByOrderByIdDesc();

	boolean existsByNomeAndDataNascita(String nome, LocalDate dataNascita);
	
	Page<Autore> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome, Pageable pageable);

	Page<Autore> findAll(Pageable pageable);


}
