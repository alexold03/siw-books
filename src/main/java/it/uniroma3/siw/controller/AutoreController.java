package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.AutoreService;
import jakarta.validation.Valid;

@Controller
public class AutoreController {
	@Autowired
	private AutoreService autoreService;

	@GetMapping("/autori")
	private String mostraAutori(Model model,
			@PageableDefault(page = 0, size = 9, sort = "cognome") Pageable pageable,
			@RequestParam(name = "searchAutore", required = false) String searchAutore) {
		Page<Autore> autorePage;

		if (searchAutore != null && !searchAutore.trim().isEmpty()) {
			autorePage = autoreService.findByNomeOCognome(searchAutore, pageable);
		} else {
			autorePage = autoreService.findAll(pageable);
		}

		model.addAttribute("autorePage", autorePage);
		model.addAttribute("currentPage", autorePage.getNumber() + 1);
		model.addAttribute("totalPages", autorePage.getTotalPages());
		model.addAttribute("totalItems", autorePage.getTotalElements());
		model.addAttribute("pageSize", pageable.getPageSize());
		model.addAttribute("searchAutore", searchAutore);

		return "autori.html";
	}

	@GetMapping("autore/{id}")
	private String mostraAutore(@PathVariable("id")Long id,Model model) {
		model.addAttribute("autore", autoreService.findById(id));
		return "autore.html";

	}

	@GetMapping("/admin/autore/crea")
	private String aggiungiAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "admin/formAutore";

	}

	@PostMapping("/admin/autore/crea")
	private String salvaAutore(@Valid@ModelAttribute Autore autore,BindingResult bindingResult, @RequestParam("file") MultipartFile file) throws IOException  {

		if (bindingResult.hasErrors()) {
			return "admin/formAutore";
		}
		if (!file.isEmpty()) {
			autore.setFotografia(file.getBytes());
		}
		autoreService.save(autore);

		return "redirect:/autori";
	}

	@GetMapping("/autore/foto/{id}")
	@ResponseBody
	public byte[] getFoto(@PathVariable Long id) {
		Autore autore = autoreService.findById(id);
		return autore.getFotografia();
	}

	@PostMapping("/admin/autore/{id}/elimina")
	public String eliminaAutore(@PathVariable("id") Long id) {
		autoreService.deleteById(id);
		return "redirect:/autori";
	}

	@GetMapping("/admin/autore/{id}/immagine")
	public String mostraFormImmagineAutore(@PathVariable("id") Long id, Model model) {
		Autore autore = autoreService.findById(id);
		if (autore == null) {
			return "redirect:/autori";
		}
		model.addAttribute("autore", autore);
		return "admin/formImmagineAutore"; 
	}


	@PostMapping("/admin/autore/{id}/immagine")
	public String aggiornaImmagineAutore(@PathVariable("id") Long id,
			@RequestParam("file") MultipartFile file) throws IOException {
		Autore autore = autoreService.findById(id);
		if (autore == null) {
			return "redirect:/autori";
		}
		if (!file.isEmpty()) {
			autore.setFotografia(file.getBytes());
			autoreService.save(autore);
		}
		return "redirect:/autore/" + id;
	}




}
