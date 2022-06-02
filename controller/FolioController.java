package edu.mx.utdelacosta.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/folio")
public class FolioController {
	
	@GetMapping("/edicion/{id}")
	public String archivosBanco(@PathVariable("id") int id) {
		return "caja/edicionFolio";
	}

}
