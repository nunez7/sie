package edu.mx.utdelacosta.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.ParcialesDTO;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Rector') and hasRole('Informatica') and hasRole('Director')")
@RequestMapping("/corteEvaluativo")
public class CorteEvaluativoController {
	
	@Autowired
	private ICarrerasServices carrerasServices;
	
	@Autowired
	private IPeriodosService periodoService;
	
	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@Autowired
	private IPersonaService personaService;
	

	@PostMapping(path = "/agregar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String agregar(@RequestBody ParcialesDTO  dto, HttpSession session) {
		//creamos el usuario de acuerdo a la authenticación 
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona")); 
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(usuario.getPersona().getId());
		// hace el proceso de inserción o actualización
		for(int i = 0; i < carreras.size(); i++) {
			//creamos los cortes evaluativos
			CorteEvaluativo corteEvaluativo1 = new CorteEvaluativo();
			CorteEvaluativo corteEvaluativo2 = new CorteEvaluativo();
			//buscamos si hay cortes para esa carrera
			List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(carreras.get(i), periodo);
			//comparamos si hay cortes para la carrera sino creamos uno nuevo
			if(cortes.size() > 0) {
				 corteEvaluativo1 = cortes.get(0);
				 corteEvaluativo2 = cortes.get(1);
			}
			
			corteEvaluativo1.setPeriodo(periodo);
			corteEvaluativo1.setFechaInicio(dto.getFc1());
			corteEvaluativo1.setFechaFin(dto.getFf1());
			corteEvaluativo1.setInicioRemedial(dto.getIr1());
			corteEvaluativo1.setFinRemedial(dto.getFr1());
			corteEvaluativo1.setInicioExtraordinario(dto.getIe1());
			corteEvaluativo1.setFinExtraordinario(dto.getFe1());
			corteEvaluativo1.setInicioEvaluaciones(dto.getIev());
			corteEvaluativo1.setFinEvaluaciones(dto.getFev());
			corteEvaluativo1.setFechaAsistencia(dto.getFc1());
			corteEvaluativo1.setConsecutivo(1);
			corteEvaluativo1.setFechaDosificacion(dto.getFd());
			corteEvaluativo1.setLimiteCaptura(dto.getFlc1());//captura de calificaciones
			corteEvaluativo1.setLimiteRemedial(dto.getFcr1());//limite captura de remediales
			corteEvaluativo1.setLimiteExtraordinario(dto.getFce1());
			Carrera carrera1 = carrerasServices.buscarPorId(carreras.get(i).getId());
			corteEvaluativo1.setCarrera(carrera1);
			corteEvaluativoService.guardar(corteEvaluativo1);
			//inserta el segundo corte evaluativo
			corteEvaluativo2.setPeriodo(periodo);
			corteEvaluativo2.setFechaInicio(dto.getFc2());
			corteEvaluativo2.setFechaFin(dto.getFf2());
			corteEvaluativo2.setInicioRemedial(dto.getIr2());
			corteEvaluativo2.setFinRemedial(dto.getFr2());
			corteEvaluativo2.setInicioExtraordinario(dto.getIe2());
			corteEvaluativo2.setFinExtraordinario(dto.getFe2());
			corteEvaluativo2.setInicioEvaluaciones(dto.getIev());
			corteEvaluativo2.setFinEvaluaciones(dto.getFev());
			corteEvaluativo2.setFechaAsistencia(dto.getFf2());
			corteEvaluativo2.setConsecutivo(2);
			corteEvaluativo2.setFechaDosificacion(dto.getFd());
			corteEvaluativo2.setLimiteCaptura(dto.getFlc2());//captura de calificaciones
			corteEvaluativo2.setLimiteRemedial(dto.getFcr2());//limite captura de remediales
			corteEvaluativo2.setLimiteExtraordinario(dto.getFce2());
			Carrera carrera2 = carrerasServices.buscarPorId(carreras.get(i).getId());
			corteEvaluativo2.setCarrera(carrera2);
			corteEvaluativoService.guardar(corteEvaluativo2);
			
		}
		return "ok";
	}
}
