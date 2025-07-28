package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class RecensioneController {

	@Autowired
	private RecensioneService recensioneService;

	@Autowired
	private LibroService libroService;

	@Autowired
	private UserService userService;

	@Autowired
	private CredentialsService credentialsService;

	@GetMapping("/libro/{id}/recensione/nuova")
	public String mostraFormRecensione(@PathVariable Long id, Model model) {
		
		Libro libro = libroService.findById(id);
		if (libro == null) return "redirect:/";

		model.addAttribute("libro", libro);
		model.addAttribute("recensione", new Recensione());
		return "formRecensione";
	}


	@PostMapping("/libro/{id}/recensione")
	public String aggiungiRecensione(@PathVariable("id") Long idL,
			@Valid @ModelAttribute("recensione") Recensione recensione,
			BindingResult bindingResult,
			Model model,
			@ModelAttribute("userDetails") UserDetails userDetails) {
		Libro libro = libroService.findById(idL);
		if (libro == null) {
			return "redirect:/";
		}

		model.addAttribute("libro", libro);

		if (userDetails == null) {  
			return "redirect:/login";
		}

		User user = credentialsService.getUserByUsername(userDetails.getUsername());
		if (user == null) {

			return "redirect:/login";
		}

		// Imposto autore e libro PRIMA della validazione per evitare errori NotNull
		recensione.setAutore(user);
		recensione.setLibro(libro);
		if (recensioneService.giaScritta(user, libro).isPresent()) {

			bindingResult.reject("recensione.duplicate", "Hai già recensito questo libro");
			return "formRecensione";
		}
		if (bindingResult.hasErrors()) {
			return "formRecensione";
		}
		recensione.setId(null); // sicurezza
		recensioneService.save(recensione);
		return "redirect:/libro/" + idL;
	}
	
	@PostMapping("/admin/libro/{id}/recensione/{idR}/cancella")
	public String cancellaRecensione(@PathVariable("id") Long idL,@PathVariable("idR") Long idR) {
		recensioneService.deleteByid(idR);
		return "redirect:/libro/"+idL;
	}
	



}
