package edu.mx.utdelacosta.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.service.IConceptoService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/concepto")
public class ConceptoController {
	
	@Autowired
	private IConceptoService conceptoService;
	 
	@GetMapping("/search/{id}")
	public ResponseEntity<Concepto> get(@PathVariable("id") int id) {
		Concepto concepto = conceptoService.buscarPorId(id);
		if (concepto == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(concepto);
	    }
	}
	
	@PostMapping(path="/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardar(@RequestBody Concepto concepto) {
		Concepto c = new Concepto();
		if(concepto.getId() >  0) {
			//se edita el registro
			c = conceptoService.buscarPorId(concepto.getId());
			c.setConcepto(concepto.getConcepto());
			c.setMonto(concepto.getMonto());
			c.setTipo(concepto.getTipo());
			c.setEstatus(concepto.getEstatus());
			conceptoService.guardar(c);
			return "up";
		}
		//guarda el nuevo registro
		c.setConcepto(concepto.getConcepto());
		c.setMonto(concepto.getMonto());
		c.setTipo(concepto.getTipo());
		c.setEstatus(concepto.getEstatus());
		c.setFechaAlta(new Date());
		conceptoService.guardar(c);
		return "ok";
	}

}
