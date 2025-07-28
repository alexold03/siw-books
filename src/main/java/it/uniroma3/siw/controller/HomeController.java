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
		 // Disabilita la cache del browser per questa risposta
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setDateHeader("Expires", 0); // Proxies

		model.addAttribute("libri", libroService.findTop3ByOrderByIdDesc());

		model.addAttribute("autori", autoreService.findTop3ByOrderByIdDesc());

		return "home"; // src/main/resources/templates/index.html
	}
}


