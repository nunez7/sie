package edu.mx.utdelacosta.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Area;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dto.AreaDTO;
import edu.mx.utdelacosta.service.IAreaService;

@Controller
@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector')")
@RequestMapping("/area")
public class AreaController {
	
	@Autowired
	private IAreaService areaService;
	
	@PostMapping(path="/save")
    @ResponseBody
    public String changePreferences(@RequestBody AreaDTO areaRequest) {
		Area area = null;
		//Se hace insert 
		if(areaRequest.getIdArea()>0) {
			area = areaService.buscarPorId(areaRequest.getIdArea());
		}else {
			//Update 
			area = new Area();
			area.setFechaAlta(new Date());
		}
		area.setArea(areaRequest.getArea());
		area.setResponsable(new Persona(areaRequest.getIdEncargado()));
		areaService.guardar(area);
		return "ok";
	}

}
