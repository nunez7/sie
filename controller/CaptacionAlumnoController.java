package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Escuela;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Municipio;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaDocumento;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.ProspectoDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IEscuelaService;
import edu.mx.utdelacosta.service.IEstadoCivilService;
import edu.mx.utdelacosta.service.IEstadoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.ILocalidadesService;
import edu.mx.utdelacosta.service.IMunicipiosService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Servicios Escolares')")
@RequestMapping("/captacion-alumno")
public class CaptacionAlumnoController {

	@Autowired
	private IPeriodosService periodosService;

	@Autowired
	private ICarrerasServices carreraService;

	@Autowired
	private IEstadoService estadosService;

	@Autowired
	private IMunicipiosService municipiosService;

	@Autowired
	private ILocalidadesService localidadesService;

	@Autowired
	private IEstadoCivilService edoCivilService;

	@Autowired
	private IEscuelaService escuelasService;

	@Autowired
	private IGrupoService grupoService;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private IAlumnoService alumnoService;

	@Value("${siestapp.ruta.docs}")
	private String rutaDocs;

	@GetMapping("/aceptarAspirantes")
	public String aceptarAspirantes(Model model, HttpSession session) {
		// List<Alumno> prospectos = alumnoService.buscarProspectosRegular();
		List<ProspectoDTO> prospectos = alumnoService.buscarProspectosActivos();
		// List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		List<Carrera> carreras = carreraService.buscarTodasTSUMenosIngles();
		List<PersonaDocumento> documentos = new ArrayList<>();
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);

		List<Grupo> grupos = grupoService.buscarTodoPorPeriodoOrdenPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("alumnos", prospectos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("carreras", carreras);
		model.addAttribute("documentos", documentos);
		return "escolares/aceptarAspirantes";
	}

	@GetMapping("/aspirantesAceptados")
	public String aspirantesAceptados(Model model, HttpSession session) {
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Integer idCarrera = ((Integer) session.getAttribute("cveCarrera"));
		if (idCarrera == null) {
			idCarrera = 2;
		}
		Carrera carIni = new Carrera(idCarrera);
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = periodosService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		// List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		List<Carrera> carreras = carreraService.buscarTodasTSUMenosIngles();
		List<Grupo> grupos = grupoService.buscarPorCuatrimestreCarreraYPeriodo(1, idCarrera,
				usuario.getPreferencias().getIdPeriodo());
		// List<Alumno> alumnos =
		// alumnoService.buscarTodoAceptarPorCarreraYPeriodo(carIni.getId(),
		// periodo.getId());
		List<Alumno> alumnos = alumnoService.buscarProspectosAceptados(carIni.getId(), periodo.getId());
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("carreras", carreras);
		model.addAttribute("carreraIni", carIni);
		return "escolares/aspirantesAceptados";
	}

	@GetMapping("/nuevoProspecto")
	public String nuevoProspecto(Model model) {
		// List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		List<Carrera> carreras = carreraService.buscarTodasTSUMenosIngles();
		Estado estado = new Estado();
		estado.setId(18);
		Municipio municipio = new Municipio();
		municipio.setId(2293);
		Escuela escuela = new Escuela();
		escuela.setId(7350);
		Estado estadoF = new Estado();
		estadoF.setId(18);
		Municipio municipioF = new Municipio();
		municipioF.setId(2293);
		Escuela escuelaF = new Escuela();
		escuelaF.setId(7350);

		model.addAttribute("carreras", carreras);
		model.addAttribute("estados", estadosService.buscarTodos());
		model.addAttribute("municipios", municipiosService.buscarPorEstado(estado));
		model.addAttribute("localidades", localidadesService.buscarPorMunicipio(municipio));
		model.addAttribute("municipiosF", municipiosService.buscarPorEstado(estadoF));
		model.addAttribute("localidadesF", localidadesService.buscarPorMunicipio(municipioF));
		model.addAttribute("escuelas", escuelasService.buscarTodoPorEstado(estadoF));
		model.addAttribute("escuelasF", escuelasService.buscarTodoPorEstado(estadoF));
		model.addAttribute("edosCivil", edoCivilService.buscarTodos());
		return "escolares/nuevoProspecto";
	}

}