package edu.mx.utdelacosta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.UnidadTematica;
import edu.mx.utdelacosta.model.dto.UnidadTematicaDTO;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IUnidadTematicaService;

@Controller
@RequestMapping("/unidadTematica")
public class UnidadesTematicasController {
	
	@Autowired
	private IUnidadTematicaService unidadTematicaService;
	
	@Autowired
	private IMateriasService materiasService;
	
	@PostMapping(path= "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String save(@RequestBody UnidadTematicaDTO unidadRequest) {
		UnidadTematica unidad = null;
		//actualiza el registro
		if(unidadRequest.getIdUnidad() > 0) {
			unidad = unidadTematicaService.buscarPorId(unidadRequest.getIdUnidad());
		} else {
			//se hace inserción
			unidad = new UnidadTematica();
			//sacamos la materia 
			Materia materia = null;
			materia = materiasService.buscarPorId(unidadRequest.getIdMateria());
			unidad.setMateria(materia);
		}
		unidad.setConsecutivo(unidadRequest.getConsecutivo());
		unidad.setHorasPracticas(unidadRequest.getHorasPracticas());
		unidad.setHorasTeoricas(unidadRequest.getHorasTeoricas());
		unidad.setHorasTotales(unidadRequest.getHorasTotales());
		unidad.setNombre(unidadRequest.getNombre());
		unidad.setActivo(unidadRequest.getActivo());
		unidadTematicaService.guardar(unidad);
		return "ok";
	}

}
