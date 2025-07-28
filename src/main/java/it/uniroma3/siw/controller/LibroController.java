package it.uniroma3.siw.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AutoreService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ImmagineService;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;
import jakarta.validation.Valid;

@Controller
public class LibroController {
	@Autowired 
	private LibroService libroService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private RecensioneService recensioneService;
	@Autowired
	private ImmagineService immagineService;
	@Autowired
	private AutoreService autoreService;

	@GetMapping("/libri")
	public String mostraCatalogoLibri(
	        Model model,
	        @PageableDefault(page = 0, size = 9, sort = "titolo") Pageable pageable,
	        @RequestParam(name = "searchTitolo", required = false) String searchTitolo
	) {
	    Page<Libro> libroPage;

	    if (searchTitolo != null && !searchTitolo.trim().isEmpty()) {
	        libroPage = libroService.findByTitoloContaining(searchTitolo, pageable);
	    } else {
	        libroPage = libroService.findAll(pageable);
	    }

	    model.addAttribute("libroPage", libroPage);
	    model.addAttribute("currentPage", libroPage.getNumber() + 1);
	    model.addAttribute("totalPages", libroPage.getTotalPages());
	    model.addAttribute("totalItems", libroPage.getTotalElements());
	    model.addAttribute("pageSize", pageable.getPageSize());
	    model.addAttribute("searchTitolo", searchTitolo);

	    return "libri.html";
	}

	@GetMapping("/libro/{id}")
	public String getLibro(@PathVariable("id") Long id, Model model,
			@ModelAttribute("userDetails") UserDetails userDetails) {
		Libro libro = this.libroService.findById(id);
		if (libro==null) {
			return "redirect:/libri";
		}
		model.addAttribute("libro", libro);
		model.addAttribute("recensione", new Recensione());

		if (userDetails != null) {
			User user = credentialsService.getUserByUsername(userDetails.getUsername());
			boolean giaRecensito = recensioneService.giaScritta(user, libro).isPresent();
			model.addAttribute("giaRecensito", giaRecensito);
		}

		return "libro.html";
	}
	// Mostra form upload immagine - solo ADMIN può accedere

	@GetMapping("admin/libri/{idLibro}/immagine")
	public String mostraFormUpload(@PathVariable Long idLibro, Model model) {
		Libro libro = libroService.findById(idLibro);
		model.addAttribute("libro", libro);
		return "admin/formImmagine"; // il nome del template HTML per il form
	}

	// Gestisce upload immagine

	@PostMapping("admin/libri/{idLibro}/immagine")
	public String caricaImmagine(@PathVariable Long idLibro, 
			@RequestParam("file") MultipartFile file,
			Model model) {
		try {
			Libro libro = libroService.findById(idLibro);
			immagineService.salvaImmaginePerLibro(libro, file);
			return "redirect:/libro/" + idLibro; // torna al dettaglio libro
		} catch (Exception e) {
			model.addAttribute("error", "Errore nel caricamento dell'immagine.");
			return "admin/formImmagine";
		}
	}

	@GetMapping("admin/libro/crea")
	public String mostraFormLibro(Model model) {
		model.addAttribute("libro", new Libro());
		model.addAttribute("listaAutori", autoreService.findAll());
		return "admin/formLibro";
	}

	@PostMapping("admin/libro/crea")
	public String salvaLibro(@Valid @ModelAttribute("libro") Libro libro, 
            BindingResult bindingResult,
            Model model,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("listaAutori", autoreService.findAll());
	        return "admin/formLibro"; 
	    }

		libroService.save(libro);
		if (!file.isEmpty()) {
			immagineService.salvaImmaginePerLibro(libro, file);
		}
		return "redirect:/libri";
	}
	
	@GetMapping("admin/libri/{idLibro}/autori")
	public String gestisciAutoriLibro(@PathVariable Long idLibro, Model model) {
	    Libro libro = libroService.findById(idLibro);
	    model.addAttribute("libro", libro);

	    List<Autore> autoriLibro = libro.getAutori();
	    List<Autore> tutti = autoreService.findAll();

	    // Rimuovi gli autori già associati
	    tutti.removeAll(autoriLibro);

	    model.addAttribute("autoriAssociati", autoriLibro);
	    model.addAttribute("autoriNonAssociati", tutti);

	    return "admin/gestioneAutoriLibro";
	}

	@PostMapping("admin/libri/{idLibro}/autori/aggiungi")
	public String aggiungiAutore(@PathVariable Long idLibro, @RequestParam Long autoreId) {
	    libroService.aggiungiAutore(idLibro, autoreId);
	    return "redirect:/admin/libri/" + idLibro + "/autori";
	}

	@PostMapping("admin/libri/{idLibro}/autori/rimuovi")
	public String rimuoviAutore(@PathVariable Long idLibro, @RequestParam Long autoreId,RedirectAttributes redirectAttributes) {
		Libro libro = libroService.findById(idLibro);

	    if (libro.getAutori().size() <= 1) {
	        redirectAttributes.addFlashAttribute("erroreAutori", "errore.rimozione.autore.unico");
	        return "redirect:/admin/libri/" + idLibro + "/autori";
	    }

	    libroService.rimuoviAutore(idLibro, autoreId);
	    return "redirect:/admin/libri/" + idLibro + "/autori";
	}
	
	@PostMapping("admin/libri/{idLibro}/autori")
	public String tornaAlLibro(@PathVariable Long idLibro) {
	    return "redirect:/libro/" + idLibro;
	}

	
	
	@PostMapping("/admin/libro/{id}/elimina")
	public String eliminaLibro(@PathVariable("id") Long id) {
	    libroService.deleteById(id);
	    return "redirect:/libri";
	}
	
	@PostMapping("/admin/libri/{idLibro}/copertina")
	public String impostaCopertina(@PathVariable Long idLibro, @RequestParam Long immagineId) {
	    libroService.impostaCopertina(idLibro, immagineId);
	    return "redirect:/libro/" + idLibro ;
	}
	
	@GetMapping("/admin/libri/{id}/modifica")
	public String modificaLibro(@PathVariable("id") Long idL, Model model){
	    model.addAttribute("libro", libroService.findById(idL));
	    model.addAttribute("listaAutori", autoreService.findAll());
	    model.addAttribute("modifica", true);
	    return "admin/formLibro";
	}
	
	@PostMapping("/admin/libro/{id}/modifica")
	public String aggiornaLibro(@PathVariable("id") Long id,
	                            @Valid @ModelAttribute("libro") Libro libro,
	                            BindingResult bindingResult,
	                            @RequestParam("file") MultipartFile file,
	                            Model model) throws IOException {
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("listaAutori", autoreService.findAll());
	        model.addAttribute("modifica", true);
	        return "admin/formLibro";
	    }

	    libro.setId(id); // Assicurati che l'ID venga preservato

	    if (!file.isEmpty()) {
	        immagineService.salvaImmaginePerLibro(libro, file);
	    }

	    libroService.save(libro); // Funziona anche per update se usi .save()
	    return "redirect:/libri";
	}
	
	





}







