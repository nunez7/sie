package edu.mx.utdelacosta.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

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
	public String agregar(@RequestBody Map<String, String> obj, HttpSession session) {
		//se toman las variables que se envian del formulario
		Date fc1 = Date.valueOf(obj.get("fc1"));
		Date fc2 = Date.valueOf(obj.get("fc2"));
		Date ff1 = Date.valueOf(obj.get("ff1"));
		Date ff2 = Date.valueOf(obj.get("ff2"));
		Date ir1 = Date.valueOf(obj.get("ir1"));
		Date ir2 = Date.valueOf(obj.get("ir2"));
		Date fr1 = Date.valueOf(obj.get("fr1"));
		Date fr2 = Date.valueOf(obj.get("fr2"));
		Date iex1 = Date.valueOf(obj.get("ie1"));
		Date iex2 = Date.valueOf(obj.get("ie2"));
		Date fex1 = Date.valueOf(obj.get("fe1"));
		Date fex2 = Date.valueOf(obj.get("fe2"));
		Date iev1 = Date.valueOf(obj.get("iev1"));
		Date iev2 = Date.valueOf(obj.get("iev2"));
		Date fev1 = Date.valueOf(obj.get("fev1"));
		Date fev2 = Date.valueOf(obj.get("fev2"));
		Date fd1 = Date.valueOf(obj.get("fd1"));
		Date fd2 = Date.valueOf(obj.get("fd2"));
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
			corteEvaluativo1.setFechaInicio(fc1);
			corteEvaluativo1.setFechaFin(ff1);
			corteEvaluativo1.setInicioRemedial(ir1);
			corteEvaluativo1.setFinRemedial(fr1);
			corteEvaluativo1.setInicioExtraordinario(iex1);
			corteEvaluativo1.setFinExtraordinario(fex1);
			corteEvaluativo1.setInicioEvaluaciones(iev1);
			corteEvaluativo1.setFinEvaluaciones(fev1);
			corteEvaluativo1.setFechaAsistencia(ff1);
			corteEvaluativo1.setConsecutivo(1);
			corteEvaluativo1.setFechaDosificacion(fd1);
			Carrera carrera1 = carrerasServices.buscarPorId(carreras.get(i).getId());
			corteEvaluativo1.setCarrera(carrera1);
			corteEvaluativoService.guardar(corteEvaluativo1);
			//inserta el segundo corte evaluativo
			corteEvaluativo2.setPeriodo(periodo);
			corteEvaluativo2.setFechaInicio(fc2);
			corteEvaluativo2.setFechaFin(ff2);
			corteEvaluativo2.setInicioRemedial(ir2);
			corteEvaluativo2.setFinRemedial(fr2);
			corteEvaluativo2.setInicioExtraordinario(iex2);
			corteEvaluativo2.setFinExtraordinario(fex2);
			corteEvaluativo2.setInicioEvaluaciones(iev2);
			corteEvaluativo2.setFinEvaluaciones(fev2);
			corteEvaluativo2.setFechaAsistencia(ff2);
			corteEvaluativo2.setConsecutivo(2);
			corteEvaluativo2.setFechaDosificacion(fd2);
			Carrera carrera2 = carrerasServices.buscarPorId(carreras.get(i).getId());
			corteEvaluativo2.setCarrera(carrera2);
			corteEvaluativoService.guardar(corteEvaluativo2);
			
		}
		return "ok";
	}
}
