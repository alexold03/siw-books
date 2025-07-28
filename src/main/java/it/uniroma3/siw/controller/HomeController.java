package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.AutoreService;
import it.uniroma3.siw.service.LibroService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
	@Autowired
	LibroService libroService;
	@Autowired
	AutoreService autoreService;

	@GetMapping("/")
	public String home(Model model,HttpServletResponse response) {
		 

		model.addAttribute("libri", libroService.findTop3ByOrderByIdDesc());

		model.addAttribute("autori", autoreService.findTop3ByOrderByIdDesc());

		return "home"; 
	}
}


