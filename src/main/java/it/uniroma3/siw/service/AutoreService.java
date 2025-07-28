package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.AutoreRepository;

@Service
public class AutoreService {
	@Autowired
	private AutoreRepository autoreRepository;

	public List<Autore> findTop3ByOrderByIdDesc() {
		return this.autoreRepository.findTop3ByOrderByIdDesc();
	}

	public List<Autore> findAll() {
		return (List<Autore>) this.autoreRepository.findAll();
	}

	public Autore findById(Long id) {
		
		return this.autoreRepository.findById(id).orElse(null);
	}

	public void save(Autore autore) {
		this.autoreRepository.save(autore);
		
	}

	public void deleteById(Long id) {
		Autore autore = autoreRepository.findById(id).orElse(null);
	    
	    if (autore != null) {
	        // Rimuove l'autore dalla lista autori di ciascun libro
	        for (Libro libro : autore.getLibri()) {
	            libro.getAutori().remove(autore);
	        }
	        
	        // Pulisce la lista dei libri dell'autore (evita problemi di riferimento)
	        autore.getLibri().clear();

	        autoreRepository.delete(autore);
	    }
		
	}

	public boolean existsByNomeAndDataNascita(String nome, LocalDate dataNascita) {
		
		return this.autoreRepository.existsByNomeAndDataNascita(nome,dataNascita);
	}
	
	public Page<Autore> findByNomeOCognome(String query,Pageable pageable){
		return this.autoreRepository.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(query, query, pageable);
	}

	public Page<Autore> findAll(Pageable pageable) {
		return this.autoreRepository.findAll(pageable);
	}

	

}
