package it.uniroma3.siw.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.ImmagineRepository;
import jakarta.transaction.Transactional;


@Service
public class ImmagineService {
	@Autowired
	private ImmagineRepository immagineRepository;
	@Transactional
	public void salvaImmaginePerLibro(Libro libro, MultipartFile file) throws IOException {
	    System.out.println("Salvo immagine per libro: " + libro);
	    System.out.println("File: " + file);

	    if (libro == null || file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("Libro o file non valido");
	    }

	    Immagine immagine = new Immagine();
	    immagine.setNomeFile(file.getOriginalFilename());
	    immagine.setTipoMime(file.getContentType());
	    immagine.setContenuto(file.getBytes());
	    immagine.setLibro(libro);
	    libro.setCopertina(immagine);

	    immagineRepository.save(immagine);
	}


}
