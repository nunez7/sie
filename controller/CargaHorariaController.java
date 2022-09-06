package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.CargaHorariaDTO;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@RequestMapping("/cargaHoraria")
public class CargaHorariaController {
	
	@Autowired
	private IMateriasService materiasService;
	
	@Autowired
	private ICargaHorariaService cargaHorariaService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired 
	private IPeriodosService periodoService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@Autowired
	private IHorarioService horarioService;
	
	
	@PostMapping(path= "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardar(@RequestBody Map<String, String> obj, HttpSession session) {
		//sacamos el usuario
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona")); 
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		int cveGrupo = (Integer) session.getAttribute("cveGrupo"); //idGrupo de la sesion
		List<Materia> materias = materiasService.buscarPorGrupoYCarreraYActivos(cveGrupo, usuario.getPreferencias().getIdCarrera());
		for(int i=0; i<materias.size(); i++) {
			int idMateria = materias.get(i).getId();
			int idProfesor = Integer.valueOf(obj.get("p-"+idMateria));
			if(idProfesor > 0) {
				//se busca el grupo por su id
				Grupo group = grupoService.buscarPorId(cveGrupo);
				// se busca la persona por su id
				Persona profesor = personaService.buscarPorId(idProfesor);
				//se busca la materia por su id
				Materia materia = materiasService.buscarPorId(idMateria);
				//buscamos el periodo por su id (se tomara el periodo de la sesión)
				Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
				// se busca la carga horaria de acuerdo a materia y periodo
				CargaHoraria cargaHoraria = cargaHorariaService.buscarPorMateriaYPeriodoYGrupo(idMateria, usuario.getPreferencias().getIdPeriodo(), cveGrupo);
				// se va comparar si ya existe sino se creara una nueva
				if(cargaHoraria != null) {
					cargaHoraria.setProfesor(profesor);
					cargaHoraria.setFechaAlta(new Date());
					cargaHorariaService.guardar(cargaHoraria);
				}
				else {
						//se agrega un nuevo objeto de carga horaria
						CargaHoraria carga = new CargaHoraria();
						//seteamos los valores de la nueva carga
						carga.setGrupo(group);
						carga.setActivo(true);
						carga.setProfesor(profesor);
						carga.setMateria(materia);
						carga.setPeriodo(periodo);
						carga.setFechaAlta(new Date());
						cargaHorariaService.guardar(carga);
				}
			}
		}
		return "ok";
	}
	
	@PostMapping(path = "/desactivar/{idCarga}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String desactivarCarga(@PathVariable("idCarga") int idCarga) {
		//se trae la arga horaria de acuerdo al id
		CargaHoraria cargaHoraria = cargaHorariaService.buscarPorIdCarga(idCarga);
		if(cargaHoraria != null) {
			//se desactiva la carga horaria 
			cargaHoraria.setActivo(false);
			//busco los horarios en base a la carga horaria
			List<Horario> horarios = horarioService.buscarPorCargaHoraria(cargaHoraria);
			if(!horarios.isEmpty()) {
				for(Horario horario : horarios) {
					horario.setActivo(false);
					horarioService.guardar(horario);
				}
			}
		}
		//se guarda la carga
		cargaHorariaService.guardar(cargaHoraria);
		return "ok";
	}
	
	@GetMapping(path = "/get/{id}")
	@ResponseBody
	public ResponseEntity<List<CargaHorariaDTO>> obtenerCarga(@PathVariable("id") int cveGrupo, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<CargaHoraria> cargasHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
		//se crea la lista de cargaHorariaDTO 
		List<CargaHorariaDTO> cargasDTO = new ArrayList<>();
		for(CargaHoraria ch : cargasHorarias) {
			//se crea un objeto de cargaHorariaDTO
			CargaHorariaDTO cargaDTO = new CargaHorariaDTO();
			cargaDTO.setIdCargaHoraria(ch.getId());
			cargaDTO.setIdMateria(ch.getMateria().getId());
			cargaDTO.setIdProfesor(ch.getProfesor().getId());
			cargasDTO.add(cargaDTO);
		}
		return ResponseEntity.ok(cargasDTO);
	}
}
