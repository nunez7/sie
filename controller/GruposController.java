package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.GrupoDTO;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IPersonaService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Servicios Escolares')")
@RequestMapping("/grupo")
public class GruposController {

	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private ICarrerasServices carreraService;
	
	@Autowired
	private IPersonaService personaService;

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardar(@RequestBody GrupoDTO datos) {
		Grupo grupo = null;
		if (datos.getIdGrupo() > 0) {
			grupo = grupoService.buscarPorId(datos.getIdGrupo());
		} else {
			grupo = new Grupo();
			grupo.setFechaAlta(new Date());
			// Es una persona Vacia
			grupo.setProfesor(new Persona(8167));
			grupo.setJefeGrupo(new Persona(8167));
			grupo.setSubjefeGrupo(new Persona(8167));
			grupo.setHorario("");
			//Estos no se deben modificar
			grupo.setCarrera(new Carrera(datos.getIdCarrera()));
			grupo.setCuatrimestre(new Cuatrimestre(datos.getIdCuatrimestre()));
			grupo.setPeriodo(new Periodo(datos.getIdPeriodo()));
		}
		grupo.setActivo(datos.isActivo());
		grupo.setNombre(datos.getNombreGrupo());
		grupo.setCapacidadMaxima(datos.getCantidad());
		grupoService.guardar(grupo);
		return "ok";
	}

	@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares','Informatica', 'Rector')")
	@GetMapping("/search/{id}")
	public ResponseEntity<GrupoDTO> leer(@PathVariable("id") int id) {
		Grupo grupo = grupoService.buscarPorId(id);

		if (grupo == null) {
			return ResponseEntity.notFound().build();
		} else {
			GrupoDTO grupoDto = new GrupoDTO();
			grupoDto.setIdCarrera(grupo.getCarrera().getId());
			grupoDto.setIdCuatrimestre(grupo.getCuatrimestre().getId());
			grupoDto.setIdGrupo(grupo.getCarrera().getId());
			grupoDto.setNombreGrupo(grupo.getNombre());
			grupoDto.setActivo(grupo.getActivo());
			grupoDto.setCantidad(grupo.getCapacidadMaxima());
			return ResponseEntity.ok(grupoDto);
		}
	}
	
	@GetMapping ("/loadperiodo/{periodo}")
	public String gruposf(@PathVariable(name ="periodo", required = false) int periodo, Model model) {
		List<Grupo> grupos  = grupoService.buscarTodoPorPeriodoOrdenPorId(periodo);
		model.addAttribute("grupos", grupos);
		return "escolares/grupos :: lf-grupos";
	}
	
	@PostMapping("/siguiente")
	@ResponseBody
	public Map<String, String> siguienteGrupo(@RequestBody Map<String, String> obj) {
		int periodo = Integer.parseInt(obj.get("periodo"));
		int carrera = Integer.parseInt(obj.get("carrera"));
		int cuatri = Integer.parseInt(obj.get("cuatri"));
		
		Carrera career = carreraService.buscarPorId(carrera);
		String cadena = career.getClave()+"-"+cuatri;
		
		String numero= String.valueOf(grupoService.buscarGrupoConsecutivo(cadena, periodo));
		Map<String, String> map = new HashMap<>();
		map.put("grupo", cadena+numero);
		return map;
	}
	
	@GetMapping ("/find-all-by-periodocarrera/{id}")
	public String gruposF(@PathVariable(name ="id", required = false) int id, Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		List<Grupo> grupos = new ArrayList<>();
		grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), id);
		model.addAttribute("grupos", grupos);
		return "escolares/aspirantesAceptados :: lf-grupos";
	}
	
	@PostMapping(path = "/asigar-tutor", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String asignarTutor(@RequestBody Map<String, String> obj, HttpSession session) {
		int idPersona = Integer.parseInt(obj.get("id"));
		Persona persona = personaService.buscarPorId(idPersona);
		String operacion = obj.get("operacion");
		int cveGrupo = (Integer) session.getAttribute("cveGrupo");
		Grupo grupo = grupoService.buscarPorId(cveGrupo);
		switch(operacion) {
		case "jefe" : 
			grupo.setJefeGrupo(persona);
			break;
		case "subJefe" :
			grupo.setSubjefeGrupo(persona);
			break;
		case "tutor" :
			grupo.setProfesor(persona);
			break;
		}
		grupoService.guardar(grupo);
		return "ok";
	}
	
	@GetMapping(path = "/get/{id}")
	@ResponseBody
	public ResponseEntity<Grupo> obtenerGrupo(@PathVariable("id") int cveGrupo){
		Grupo grupo = grupoService.buscarPorId(cveGrupo);
		return ResponseEntity.ok(grupo);
	}
}
