package edu.mx.utdelacosta.controller;

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

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.NivelEstudio;
import edu.mx.utdelacosta.model.PlanEstudio;
import edu.mx.utdelacosta.model.dto.PlanEstudioDTO;
import edu.mx.utdelacosta.service.IPlanEstudioService;

@Controller
@RequestMapping("/planestudio")
public class PlanEstudioController {
	
	@Autowired
	private IPlanEstudioService planEstudioService;
		
	@GetMapping("/obtener/{id}")
	public ResponseEntity<PlanEstudioDTO> leer(@PathVariable("id") int id) {
		PlanEstudio plan = planEstudioService.buscarPorId(id);
		if (plan == null) {
			return ResponseEntity.notFound().build();
		} else {
			PlanEstudioDTO planD = new PlanEstudioDTO();
			planD.setIdPlan(plan.getId());
			planD.setNombre(plan.getPlan());
			planD.setIdCarrera(plan.getCarrera().getId());
			planD.setHorasEstadia(plan.getHorasEstadia());
			planD.setIdNivelEstudio(plan.getNivelEstudio().getId());
			return ResponseEntity.ok(planD);
		}
	}
	
	@PostMapping(path= "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String save(@RequestBody PlanEstudioDTO dto) {
		PlanEstudio planEstudio = null;
		if(dto.getIdPlan()>0) {
			planEstudio = planEstudioService.buscarPorId(dto.getIdPlan());
		}else {
			 planEstudio = new PlanEstudio();
			 planEstudio.setFechaAlta(new Date());		
			 planEstudio.setActivo(true);
		}
		planEstudio.setPlan(dto.getNombre());
		Carrera carrera = new Carrera(dto.getIdCarrera());
		planEstudio.setCarrera(carrera);
		planEstudio.setHorasEstadia(dto.getHorasEstadia());
		NivelEstudio ne = new NivelEstudio(dto.getIdNivelEstudio());
		planEstudio.setNivelEstudio(ne);
		//se envia a guardar el registro
		planEstudioService.guardar(planEstudio); 
		return "ok";
	}
	
	@PostMapping(path = "/cambiar-estatus", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String cambiarEstatus(@RequestBody PlanEstudioDTO dto) {
		PlanEstudio plan = planEstudioService.buscarPorId(dto.getIdPlan());
		plan.setActivo(!dto.isActivo());
		planEstudioService.guardar(plan);
		return "ok";
	}
	
}
