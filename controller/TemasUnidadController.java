package edu.mx.utdelacosta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.TemaUnidad;
import edu.mx.utdelacosta.model.UnidadTematica;
import edu.mx.utdelacosta.model.dto.TemasUnidadDTO;
import edu.mx.utdelacosta.service.ITemaUnidadService;
import edu.mx.utdelacosta.service.IUnidadTematicaService;

@Controller
@RequestMapping("/temasUnidad")
public class TemasUnidadController {
	
	@Autowired
	private ITemaUnidadService temasService;
	
	@Autowired
	private IUnidadTematicaService unidadTematicaService;
	
	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardar(@RequestBody TemasUnidadDTO temasDTO) {
		TemaUnidad tema = null;
		//sacamos la unidadTematica
		UnidadTematica unidadTematica = null;
		//actualiza el registro
		if(temasDTO.getIdTema() > 0) {
			tema = temasService.buscarPorId(temasDTO.getIdTema());
			unidadTematica = unidadTematicaService.buscarPorId(temasDTO.getIdUnidad());
			tema.setUnidadTematica(unidadTematica);
		} else {
			//se hace la inserción
			tema = new TemaUnidad();
			unidadTematica = unidadTematicaService.buscarPorId(temasDTO.getIdUnidad());
			tema.setUnidadTematica(unidadTematica);
		}
		tema.setActivo(temasDTO.getActivo());
		tema.setConsecutivo(temasDTO.getConsecutivoTema());
		tema.setTema(temasDTO.getNombreTema());
		temasService.guardar(tema);
		return "ok";
	}
	

}
