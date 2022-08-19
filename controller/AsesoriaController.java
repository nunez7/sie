package edu.mx.utdelacosta.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Asesoria;
import edu.mx.utdelacosta.model.AsesoriaAlumno;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dtoreport.AsesoriaDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsesoriaAlumnoService;
import edu.mx.utdelacosta.service.IAsesoriaService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Academia') and hasRole('Director') and hasRole('Asistente')")
@RequestMapping("/asesoria")
public class AsesoriaController {


	@Autowired
	private IAsesoriaService asesoriaService;
	
	@Autowired
	private IAsesoriaAlumnoService asesoriaAlumnoService;
	
	@Autowired
	private IUsuariosService usuarioService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private ICargaHorariaService cargaService;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private IPeriodosService periodoService;
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";

	@PostMapping(path = "/guardarAsesoria", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String GuardarAsistencia(@RequestBody Map<String, String> obj, HttpSession session) {
		String comentario = obj.get("comentario");

		Date fecha = Date.valueOf(obj.get("fecha"));
		String tema = obj.get("tema");

		if(obj.size()<=4) {
			return "inv";
		}
		
		Integer tipoAsesoria = Integer.parseInt(obj.get("tipoAsesoria"));
		if (tipoAsesoria == 1) {
			if (obj.size() > 5) {
				return "max";
			}
		}
		
		
		CargaHoraria carga = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cveCarga"));
		Asesoria asesoria = new Asesoria();
		asesoria.setCargaHoraria(carga);
		asesoria.setComentario(comentario);
		asesoria.setFechaAsesoria(fecha);
		asesoria.setIdTipoAsesoria(tipoAsesoria);
		asesoria.setTema(tema);
		asesoriaService.guardar(asesoria);

		List<Alumno> alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(carga.getGrupo().getId());
		for (Alumno alumno : alumnos) {
			String respuesta = obj.get(alumno.getId().toString());
			if (respuesta != null) {
				if (respuesta.equals("on")) {
					AsesoriaAlumno asesoriaAlumno = new AsesoriaAlumno();
					asesoriaAlumno.setAlumno(alumno);
					asesoriaAlumno.setAsesoria(asesoria);
					asesoriaAlumnoService.guardar(asesoriaAlumno);
				}
			}
		}
		return "ok";
	}
	
	@GetMapping("/reporte-profesor")
	 public String verReporteProfesor(Model model, HttpSession session) {
		Persona persona = personaService.buscarPorId((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodo(persona.getId(), usuario.getPreferencias().getIdPeriodo());
		int cveGrupo;
		  try {
		   cveGrupo = (Integer) session.getAttribute("cveGrupo");
		  } catch (Exception e) {
		   cveGrupo = 0;
		  }
		  if (cveGrupo>0) {
			  List<AsesoriaDTO> asesorias = asesoriaService.buscarPorIdGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());			
			  model.addAttribute("asesorias", asesorias);
			  model.addAttribute("grupoActual", grupoService.buscarPorId(cveGrupo));
			  model.addAttribute("totalM", alumnoService.contarAlumnosPorSexoYGrupo("M", cveGrupo));
			  model.addAttribute("totalH", alumnoService.contarAlumnosPorSexoYGrupo("H", cveGrupo));
		}
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("persona", persona);
	  return "profesor/reporteAsesorias";
	 }
	
	@GetMapping("/reporte-director-asistente")
	 public String reporteDirectorAsesorias(Model model, HttpSession session) {
		Persona persona = personaService.buscarPorId((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<AsesoriaDTO> asesorias = asesoriaService.buscarPorPersonaCarreraAndPeriodo(usuario.getPersona().getId(), usuario.getPreferencias().getIdPeriodo());
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cuatrimestre", periodo.getNombre());
		model.addAttribute("asesorias", asesorias);
		model.addAttribute("totalM", alumnoService.contarAlumnosPorSexoYPersonaCarreraYPeriodo("M", persona.getId(), usuario.getPreferencias().getIdPeriodo()));
		model.addAttribute("totalH", alumnoService.contarAlumnosPorSexoYPersonaCarreraYPeriodo("H", persona.getId(), usuario.getPreferencias().getIdPeriodo()));
		model.addAttribute("nombreUT", NOMBRE_UT);
		return "asistente/reporteAsesorias";
	}
	
	@GetMapping("/reporte-individual-tutor")
	public String verReporIndividualteTutor(Model model, HttpSession session) {
		// extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		int cveGrupo;
		try {
		  cveGrupo = (Integer) session.getAttribute("ra-cveGrupo");
		} catch (Exception e) {
		  cveGrupo = 0;
		}
		int cveCarga;
		try {
			cveCarga = (Integer) session.getAttribute("ra-cveCarga");
		} catch (Exception e) {
			cveCarga = 0;
		}
		int cveAlumno;
		try {
			cveAlumno = (Integer) session.getAttribute("ra-cveAlumno");
		} catch (Exception e) {
			cveAlumno = 0;
		}
		List<Alumno> alumnos = new ArrayList<>();
		List<CargaHoraria> cargas = new ArrayList<>();
		List<AsesoriaAlumno> asesorias = new ArrayList<>();
		if (cveGrupo>0) {
			alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			cargas = cargaService.buscarPorGrupo(new Grupo(cveGrupo));
			if(cveAlumno>0) {
				if(cveCarga>0) {
					asesorias = asesoriaAlumnoService.buscarPorGrupoAlumnoPeriodoCargaYTipo(cveGrupo, cveAlumno, usuario.getPreferencias().getIdPeriodo(), cveCarga, 1);
				}else{
					asesorias = asesoriaAlumnoService.buscarPorGrupoAlumnoPeriodoYTipo(cveGrupo, cveAlumno, usuario.getPreferencias().getIdPeriodo(), 1);
				}
			}else{
				if(cveCarga>0) {
					asesorias = asesoriaAlumnoService.buscarPorGrupoPeriodoCargaYTipo(cveGrupo, usuario.getPreferencias().getIdPeriodo(), cveCarga, 1);
				}else{
					asesorias = asesoriaAlumnoService.buscarPorGrupoPeriodoYTipo(cveGrupo, usuario.getPreferencias().getIdPeriodo(), 1);
				}
			}
		}
		model.addAttribute("cargas", cargas);
		model.addAttribute("cveCarga", cveCarga);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("cveAlumno", cveAlumno);
		model.addAttribute("asesorias", asesorias);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "reportes/reporteAsesoriaIndividual";
	}
	
	@GetMapping("/reporte-grupal-tutor")
	public String verReporteGrupalTutor(Model model, HttpSession session) {
		// extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		int cveGrupo;
		try {
		  cveGrupo = (Integer) session.getAttribute("rag-cveGrupo");
		} catch (Exception e) {
		  cveGrupo = 0;
		}
		int cveCarga;
		try {
			cveCarga = (Integer) session.getAttribute("rag-cveCarga");
		} catch (Exception e) {
			cveCarga = 0;
		}
		List<Asesoria> asesorias = new ArrayList<>();
		List<CargaHoraria> cargas = new ArrayList<>();
		if (cveGrupo>0) {
			cargas = cargaService.buscarPorGrupo(new Grupo(cveGrupo));
			if(cveCarga>0) {
				asesorias = asesoriaService.buscarPorGrupoPeriodoCargaYTipo(cveGrupo, usuario.getPreferencias().getIdPeriodo(), cveCarga, 2);
			}else{
				asesorias = asesoriaService.buscarPorGrupoPeriodoYTipo(cveGrupo, usuario.getPreferencias().getIdPeriodo(), 2);
			}
			
		}
		model.addAttribute("asesorias", asesorias);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("cargas", cargas);
		model.addAttribute("cveCarga", cveCarga);
		return "reportes/reporteAsesoriaGrupal";
	}
}
