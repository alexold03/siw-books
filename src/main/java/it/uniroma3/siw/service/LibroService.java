package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.AutoreRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.LibroRepository;

@Service
public class LibroService {
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutoreRepository autoreRepository;
	@Autowired
	private  ImmagineRepository immagineRepository;
	
	public Iterable<Libro> findAll(){
		return libroRepository.findAll();
	}
	public Libro findById(Long id) {
		return this.libroRepository.findById(id).orElse(null);
	}
	public List<Libro> findTop3ByOrderByIdDesc() {
	    return libroRepository.findTop3LibriConImmagini(PageRequest.of(0, 3));
	}
	public void save(Libro libro) {
		this.libroRepository.save(libro);
	}
	public void aggiungiAutore(Long libroId, Long autoreId) {
	    Libro libro = libroRepository.findById(libroId).orElseThrow();
	    Autore autore = autoreRepository.findById(autoreId).orElseThrow();

	    if (!libro.getAutori().contains(autore)) {
	        libro.getAutori().add(autore);
	        libroRepository.save(libro);
	    }
	}

	public void rimuoviAutore(Long libroId, Long autoreId) {
	    Libro libro = libroRepository.findById(libroId).orElseThrow();
	    Autore autore = autoreRepository.findById(autoreId).orElseThrow();

	    if (libro.getAutori().contains(autore)) {
	        libro.getAutori().remove(autore);
	        libroRepository.save(libro);
	    }
	}
	public void deleteById(Long id) {
		this.libroRepository.deleteById(id);
		
	}
	public boolean existsByTitoloAndAnnoPubblicazione(String titolo, LocalDate annoPubblicazione) {
		return this.libroRepository.existsByTitoloAndAnnoPubblicazione(titolo,annoPubblicazione);
	}
	
	@Transactional
	public void impostaCopertina(Long idLibro, Long idImmagine) {
	    Libro libro = libroRepository.findById(idLibro).orElseThrow();
	    Immagine immagine = immagineRepository.findById(idImmagine).orElse(null);

	    if (!libro.getImmagini().contains(immagine)) {
	        throw new IllegalArgumentException("L'immagine non appartiene a questo libro");
	    }

	    libro.setCopertina(immagine);
	    libroRepository.save(libro);
	}
	public Page<Libro> findByTitoloContaining(String searchTitolo, Pageable pageable) {
		return this.libroRepository.findByTitoloContainingIgnoreCase(searchTitolo, pageable);
	}
	public Page<Libro> findAll(Pageable pageable) {
		return this.libroRepository.findAll(pageable);
	}
	
	

	


}
