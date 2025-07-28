package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.service.AutoreService;
import it.uniroma3.siw.service.LibroService;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImmagineController {
	@Autowired
	private ImmagineRepository immagineService;
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private AutoreService autoreService;

	@GetMapping("/immagini/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getImageFromDb(@PathVariable Long id) {
	    Optional<Immagine> imgOpt = immagineService.findById(id);
	    if (imgOpt.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    Immagine img = imgOpt.get();
	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(img.getTipoMime()))
	            .body(img.getContenuto());
	}

	
	@PostMapping("/libri/{idLibro}/immagine")
	public String uploadImageLibro(@PathVariable Long idLibro, @RequestParam("file") MultipartFile file, Model model) {
	    Optional<Libro> libroOpt = Optional.ofNullable(libroService.findById(idLibro));
	    if (libroOpt.isEmpty()) {
	        model.addAttribute("error", "Libro non trovato");
	        return "errore";
	    }
	    try {
	        Immagine img = new Immagine();
	        img.setNomeFile(file.getOriginalFilename());
	        img.setTipoMime(file.getContentType());
	        img.setContenuto(file.getBytes());
	        img.setLibro(libroOpt.get());
	        
	        immagineService.save(img); 
	        
	        return "redirect:/libri/" + idLibro;
	    } catch (IOException e) {
	        e.printStackTrace();
	        model.addAttribute("error", "Errore caricando immagine");
	        return "errore";
	    }
	}
	    
	    
	   

}
