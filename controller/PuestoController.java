package edu.mx.utdelacosta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Puesto;
import edu.mx.utdelacosta.model.dto.PuestoDTO;
import edu.mx.utdelacosta.service.IPuestoService;

@Controller
@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector')")
@RequestMapping("/puesto")
public class PuestoController {
	@Autowired
	private IPuestoService puestoService;
	
	@PostMapping(path="/save")
    @ResponseBody
    public String changePreferences(@RequestBody PuestoDTO puesto) {
		Puesto puestoGuardar = new Puesto();
		puestoGuardar.setId(puesto.getIdPuesto());
		puestoGuardar.setNombre(puesto.getNombre());
		puestoGuardar.setAbreviatura(puesto.getAbreviatura());
		puestoService.guardar(puestoGuardar);
		return "ok";
	}

}
