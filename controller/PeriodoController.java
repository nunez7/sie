package edu.mx.utdelacosta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Ciclo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.dto.PeriodoDTO;
import edu.mx.utdelacosta.service.IPeriodosService;

@Controller
@RequestMapping("/periodo")
public class PeriodoController {
	
	@Autowired
	private IPeriodosService periodosService;

	@GetMapping("/search/{id}")
	public ResponseEntity<Periodo> leer(@PathVariable("id") int id) {
		Periodo periodo = periodosService.buscarPorId(id);
		if (periodo == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(periodo);
		}
	}
	
	@PostMapping(path="/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String changePreferences(@RequestBody PeriodoDTO dto) {
		Periodo periodo = null;
		//Se hace insert 
		if(dto.getIdPeriodo()>0) {
			periodo = periodosService.buscarPorId(dto.getIdPeriodo());
		}else {
			//Update 
			periodo = new Periodo();
			periodo.setFechaAlta(new Date());
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		periodo.setNombre(dto.getNombre());
		periodo.setCiclo(new Ciclo(dto.getIdCiclo()));
		try {
			periodo.setInicio(format.parse(dto.getFechaInicio()));
			periodo.setFin(format.parse(dto.getFechaFin()));
			if(!dto.getInicioInscripcion().isEmpty() && !dto.getInicioInscripcion().equals("")) {
				periodo.setInicioInscripcion(format.parse(dto.getInicioInscripcion()));
			}
			if(!dto.getFinInscripcion().isEmpty() && !dto.getFinInscripcion().equals("")) {
				periodo.setFinInscripcion(format.parse(dto.getFinInscripcion()));
			}
			periodosService.guardar(periodo);
			return "ok";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}
	
}
