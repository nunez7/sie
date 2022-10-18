package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CargaEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Comentario;
import edu.mx.utdelacosta.model.ComentarioEvaluacionTutor;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.EvaluacionComentario;
import edu.mx.utdelacosta.model.EvaluacionTutor;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.OpcionRespuesta;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.Respuesta;
import edu.mx.utdelacosta.model.RespuestaCargaEvaluacion;
import edu.mx.utdelacosta.model.RespuestaComentario;
import edu.mx.utdelacosta.model.RespuestaEvaluacionInicial;
import edu.mx.utdelacosta.model.RespuestaEvaluacionTutor;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;
import edu.mx.utdelacosta.model.dto.PreguntaDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICargaEvaluacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IComentarioEvaluacionTutorService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IEvaluacionComentarioService;
import edu.mx.utdelacosta.service.IEvaluacionTutorService;
import edu.mx.utdelacosta.service.IEvaluacionesService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IOpcionesRepuestaService;
import edu.mx.utdelacosta.service.IPreguntaService;
import edu.mx.utdelacosta.service.IRespuestaCargaEvaluacionService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionInicialService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionTutorService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Alumno')")
@RequestMapping("/encuestas")
public class EncuestasController {
	
	@Autowired
	private IEvaluacionesService serviceEvaluacion;
	
	@Autowired
	private IAlumnoService serviceAlumno;
	
	@Autowired
	private IGrupoService serviceGrupo;
	
	@Autowired
	private ICargaHorariaService serviceCargaHor;
	
	@Autowired
	private IRespuestaCargaEvaluacionService serviceResCarEva;
	
	@Autowired
	private IEvaluacionComentarioService serviceEvaluacionComentario;
	
	@Autowired
	private ICargaEvaluacionService serviceCargaEva;
	
	@Autowired
	private IEvaluacionTutorService serviceEvaTutor;
	
	@Autowired
	private IRespuestaEvaluacionTutorService serviceResEvaTutor;
	
	@Autowired
	private IComentarioEvaluacionTutorService serviceComEvaTutor;
	
	@Autowired
	private ICorteEvaluativoService serviceCorteEva;
	
	@Autowired
	private IRespuestaEvaluacionInicialService resEvaIniService;
	
	@Autowired
	private IPreguntaService preguntaService; 
	
	@Autowired
	private IOpcionesRepuestaService opcionesRepuestaService;
	
	@GetMapping("/evaluacionDocente")
	public String evaluacionDocente(Model model, HttpSession session, Authentication authentication) {	
		//Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			List<Grupo> grupos = serviceGrupo.buscarTodosDeAlumnosOrdenPorPeriodoDesc(alumno.getId());								
			//Extracción del grupo guardado en cesión
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");			
			
			if (cveGrupo != null) {												
				Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);		
				Evaluacion evaluacion = serviceEvaluacion.buscar(3);
				List<CorteEvaluativo> cortesEva = serviceCorteEva.buscarPorPeridoYCarreraFechaInicioAsc(grupo.getPeriodo(), grupo.getCarrera());				
				//validación de las fechas de evolución 				
				if((cortesEva.size() > 0) && (cortesEva.get(0).getInicioEvaluaciones() != null) && (cortesEva.get(0).getFinEvaluaciones() != null)) {
					Date fechaHoy = new Date();	
					CorteEvaluativo corteEvaluativo = cortesEva.get(0);															
										
					if(corteEvaluativo.getInicioEvaluaciones().before(fechaHoy) && corteEvaluativo.getFinEvaluaciones().after(fechaHoy)) {
						List<CargaHoraria> cargasHr = serviceCargaHor.buscarPorGrupo(new Grupo(cveGrupo));						
						Integer cveCargaHr = (Integer) session.getAttribute("cveCargaHr");
						
						//Se valida si ya se sección una asignatura para evaluarla
						if(cveCargaHr != null) {						
							//Se inyectan las respuestas asociadas al modelo a cada pregunta de la evaluación seleccionada		
							for (Pregunta pregunta : evaluacion.getPreguntas()) {												
								List<OpcionRespuestaDTO> OpcionesRepuesta = serviceResCarEva.buscarOpcionesRespuestaYRespuestaPorPregunta(evaluacion.getId(), pregunta.getId(), cvePersona, cveCargaHr);
								pregunta.setOpcionesRespuesta(OpcionesRepuesta);
							}	
							//Se valida si ya ahi un comentario asociado a un rejitro en carga evalusion en base a carga horaria en cesion
							EvaluacionComentario evaluacionComentario = serviceEvaluacionComentario.buscarEvaluacionComentarioPorPersona(cvePersona, 3, cveCargaHr);
							String comentario = ""; 
							if(evaluacionComentario == null) {
								
							}else{
								comentario = evaluacionComentario.getComentario().getComentario();
							}
							model.addAttribute("comentario", comentario);							
						}	
						model.addAttribute("encuestaActiva", true);
						model.addAttribute("cargasHr", cargasHr);
						model.addAttribute("cveCargaHr", cveCargaHr);						
					}else{
						model.addAttribute("encuestaActiva", false);
						model.addAttribute("corteEvaluativo", corteEvaluativo);
					}	
					
				}else{
					model.addAttribute("encuestaActiva", null);
				}	
				model.addAttribute("evaluacion", evaluacion);
			}					
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("grupos", grupos);					
		}
		
		model.addAttribute("alumno", alumno);
		return "encuestas/evaluacionDocente";
	}
	
	@PostMapping(path="/guardar-respuesta-evaluacion-docente", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String respuestaEvaluacionDocente(@RequestBody Map<String, String> obj, Model model, HttpSession session) {
		//Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}		
			
		Integer idEvaluacion = Integer.parseInt(obj.get("idEvaluacion"));	
		Integer idPregunta = Integer.parseInt(obj.get("idPregunta"));
		Integer idOpRepuesta = Integer.parseInt(obj.get("idOpRepuesta"));
		Integer cveCargaHr = (Integer) session.getAttribute("cveCargaHr");	
		Date fechaActual = new Date();	
		
		//Se valida si ya ahi una evaluaci�n registrada para esta carga horaria
		CargaEvaluacion cargaEvaluacion = serviceCargaEva.buscarPorCargaHorariaYEvaluacion(new CargaHoraria(cveCargaHr), new Evaluacion(idEvaluacion));
			
		//Se valida si ya hay una respuesta para esta respuesta, evaluaci�n y carga horaria
		RespuestaCargaEvaluacion respuestaCaEva = serviceResCarEva.buscarRespuestaPorPregunta(idEvaluacion, idPregunta, cvePersona, cveCargaHr);
		if(cveCargaHr != null) {
		
			if(cargaEvaluacion != null) {

				if (respuestaCaEva==null) {				
					Respuesta res = new Respuesta();			
					res.setEvaluacion(new Evaluacion(idEvaluacion));
					res.setPregunta(new Pregunta(idPregunta));
					res.setOpcionRespuesta(new OpcionRespuesta(idOpRepuesta));
					res.setPersona(new Persona(cvePersona));			
					res.setActivo(true);
					res.setFechaAlta(fechaActual);
				
					RespuestaCargaEvaluacion resCarEva = new RespuestaCargaEvaluacion();								

					resCarEva.setRespuesta(res);
					resCarEva.setCargaEvaluacion(cargaEvaluacion);
					serviceResCarEva.guardar(resCarEva);
					return "ok";
				}else{											
					respuestaCaEva.getRespuesta().setOpcionRespuesta(new OpcionRespuesta(idOpRepuesta));
					respuestaCaEva.getRespuesta().setFechaModificacion(fechaActual);
					serviceResCarEva.guardar(respuestaCaEva);
					return "ok";
				}
			}else{
				CargaEvaluacion carEva = new CargaEvaluacion();
				carEva.setCargaHoraria(new CargaHoraria(cveCargaHr));
				carEva.setEvaluacion(new Evaluacion(idEvaluacion));
				carEva.setFechaAlta(fechaActual);
				carEva.setActivo(true);
				serviceCargaEva.guardar(carEva);
				
				Respuesta res = new Respuesta();			
				res.setEvaluacion(new Evaluacion(idEvaluacion));
				res.setPregunta(new Pregunta(idPregunta));
				res.setOpcionRespuesta(new OpcionRespuesta(idOpRepuesta));
				res.setPersona(new Persona(cvePersona));			
				res.setActivo(true);
				res.setFechaAlta(fechaActual);			
				
				RespuestaCargaEvaluacion resCarEva = new RespuestaCargaEvaluacion();
				resCarEva.setCargaEvaluacion(carEva);
				resCarEva.setRespuesta(res);
				serviceResCarEva.guardar(resCarEva);
				return "ok";
			}
		}
		return "error";
	}
	
	@PostMapping(path="/guardar-comentario-evaluacion-docente", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String comentarioEvaluacionDocente(@RequestParam("comentario_evaluacion") String comentario, HttpSession session) {
		//Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}	
		
		Date fechaActual = new Date();			
		Evaluacion evaluacion = serviceEvaluacion.buscar(3);
		Integer cveCargaHr = (Integer) session.getAttribute("cveCargaHr");	
		
		//Se valida si ya ahi una evaluaci�n vinculada a la carga horaria en cesion		
		CargaEvaluacion cargaEvaluacion = serviceCargaEva.buscarPorCargaHorariaYEvaluacion(new CargaHoraria(cveCargaHr), evaluacion);
		
		//Se valida si ya ahi un comentario asociado a un rejitro en carga evalusion en base a carga horaria en cesion
		EvaluacionComentario evaluacionComentario = serviceEvaluacionComentario.buscarEvaluacionComentarioPorPersona(cvePersona, 3, cveCargaHr);
		
		if(cveCargaHr != null) {
		
			if(cargaEvaluacion == null) {						
				CargaEvaluacion carEv = new CargaEvaluacion();
				carEv.setCargaHoraria(new CargaHoraria(cveCargaHr));
				carEv.setEvaluacion(evaluacion);
				carEv.setFechaAlta(fechaActual);
				carEv.setActivo(true);
				serviceCargaEva.guardar(carEv);
				
				Comentario com = new Comentario();
				com.setTitulo(evaluacion.getNombre());
				com.setComentario(comentario);
				com.setPersona(new Persona(cvePersona));
				com.setFechaAlta(fechaActual);
				com.setActivo(true);
				
				EvaluacionComentario evaCom = new EvaluacionComentario();
				evaCom.setComentario(com);
				evaCom.setEvaluacion(evaluacion);
				evaCom.setCargaEvaluacion(carEv);
				serviceEvaluacionComentario.guardar(evaCom);			
				return "ok";
				
			}else{
				
				if(evaluacionComentario == null) {				
					Comentario com = new Comentario();
					com.setTitulo(evaluacion.getNombre());
					com.setComentario(comentario);
					com.setPersona(new Persona(cvePersona));
					com.setFechaAlta(fechaActual);
					com.setActivo(true);
					
					EvaluacionComentario evaCom = new EvaluacionComentario();
					evaCom.setComentario(com);
					evaCom.setEvaluacion(evaluacion);
					evaCom.setCargaEvaluacion(cargaEvaluacion);
					serviceEvaluacionComentario.guardar(evaCom);
					return "ok";
				}else{
					evaluacionComentario.getComentario().setComentario(comentario);
					evaluacionComentario.getComentario().setFechaModificacion(fechaActual);
					serviceEvaluacionComentario.guardar(evaluacionComentario);
					return "ok";
				}
				
			}			
		}
		return "error";
	}	
	
	@GetMapping("/evaluacionTutor")
	public String evaluacionTutor(Model model, HttpSession session, Authentication authentication) {
		//Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			
			List<Grupo> grupos = serviceGrupo.buscarTodosDeAlumnosOrdenPorPeriodoDesc(alumno.getId());								
			//Extracción del grupo guardado en cesión
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");			
			
			if (cveGrupo != null) {
				Date fechaHoy = new Date();	
				Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);
				Evaluacion evaluacion = serviceEvaluacion.buscar(4);
				List<CorteEvaluativo> cortesEva = serviceCorteEva.buscarPorPeridoYCarreraFechaInicioAsc(grupo.getPeriodo(), grupo.getCarrera());				
				//validación de las fechas de evolución 				
				if((cortesEva.size() > 0) && (cortesEva.get(0).getInicioEvaluaciones() != null) && (cortesEva.get(0).getFinEvaluaciones() != null)) {
					CorteEvaluativo corteEvaluativo = cortesEva.get(0);
					if(corteEvaluativo.getInicioEvaluaciones().before(fechaHoy) && corteEvaluativo.getFinEvaluaciones().after(fechaHoy)) {
						
						//Se inyectan las respuestas asociadas al modelo a cada pregunta de la evaluación seleccionada		
						for (Pregunta pregunta : evaluacion.getPreguntas()) {												
							List<OpcionRespuestaDTO> OpcionesRepuesta = serviceResEvaTutor.buscarOpcionesRespuestaYRespuestaPorPregunta(pregunta.getId(), cvePersona, evaluacion.getId(), cveGrupo);
							pregunta.setOpcionesRespuesta(OpcionesRepuesta);
						}
						
						//Se valida si ya hay un comentario asociado a al grupo y persona para la evaslucion del tutor		
						ComentarioEvaluacionTutor comentarioEvaluacionTutor = serviceComEvaTutor.buscarComentarioPorPersona(cvePersona, cveGrupo, 4);
						String comentario = ""; 
						if(comentarioEvaluacionTutor == null) {
							
						}else{
							comentario = comentarioEvaluacionTutor.getComentario().getComentario();
						}						
						model.addAttribute("comentario", comentario);

						model.addAttribute("encuestaActiva", true);
					}else{
						model.addAttribute("encuestaActiva", false);
						model.addAttribute("corteEvaluativo", corteEvaluativo);
					}					
				}else{
					model.addAttribute("encuestaActiva", null);
				}
				model.addAttribute("evaluacion", evaluacion);				
			}			
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("grupos", grupos);		
		}
		model.addAttribute("alumno", alumno);
		return "encuestas/evaluacionTutor";
	}
	
	@PostMapping(path="/guardar-respuesta-evaluacion-tutor", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String respuestaEvaluacionTutor(@RequestBody Map<String, String> obj, Model model, HttpSession session) {
		//Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}		
								
		Integer idPregunta = Integer.parseInt(obj.get("idPregunta"));
		Integer idOpRepuesta = Integer.parseInt(obj.get("idOpRepuesta"));
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");		
		Date fechaActual = new Date();
		
		//Se valida si ya hay una avalusion asociada al grupo		
		EvaluacionTutor evaluacionTutor = serviceEvaTutor.buscarPorEvaluacionYGrupo(new Evaluacion(4), new Grupo(cveGrupo));
		
		//Se valida si ya hay una respuesta asociada a la persona, evalusion, pregunta y grupo
		RespuestaEvaluacionTutor respuestaEvaluacionTutor = serviceResEvaTutor.buscarRespuestaPorPregunta(cvePersona, 4, idPregunta, cveGrupo);
		
		if (cveGrupo != null) {
			
			if(evaluacionTutor == null) {
				
				EvaluacionTutor evaTutor = new EvaluacionTutor();
				evaTutor.setEvaluacion(new Evaluacion(4));
				evaTutor.setGrupo(new Grupo(cveGrupo));
				evaTutor.setFechaAlta(fechaActual);
				serviceEvaTutor.guardar(evaTutor);
				
				Respuesta res = new Respuesta();			
				res.setEvaluacion(new Evaluacion(4));
				res.setPregunta(new Pregunta(idPregunta));
				res.setOpcionRespuesta(new OpcionRespuesta(idOpRepuesta));
				res.setPersona(new Persona(cvePersona));			
				res.setActivo(true);
				res.setFechaAlta(fechaActual);							
				
				RespuestaEvaluacionTutor resEvaTutor = new RespuestaEvaluacionTutor();
				resEvaTutor.setEvaluacionTutor(evaTutor);
				resEvaTutor.setRespuesta(res);
				serviceResEvaTutor.guardar(resEvaTutor);
				return "ok";
				
			}else{
				
				if(respuestaEvaluacionTutor == null){
					
					Respuesta res = new Respuesta();			
					res.setEvaluacion(new Evaluacion(4));
					res.setPregunta(new Pregunta(idPregunta));
					res.setOpcionRespuesta(new OpcionRespuesta(idOpRepuesta));
					res.setPersona(new Persona(cvePersona));			
					res.setActivo(true);
					res.setFechaAlta(fechaActual);	
					
					RespuestaEvaluacionTutor resEvaTutor = new RespuestaEvaluacionTutor();
					resEvaTutor.setEvaluacionTutor(evaluacionTutor);
					resEvaTutor.setRespuesta(res);
					serviceResEvaTutor.guardar(resEvaTutor);
					return "ok";
				}else{	
					
					respuestaEvaluacionTutor.getRespuesta().setOpcionRespuesta(new OpcionRespuesta(idOpRepuesta));
					respuestaEvaluacionTutor.getRespuesta().setFechaModificacion(fechaActual);
					serviceResEvaTutor.guardar(respuestaEvaluacionTutor);
					return "ok";
				}
				
			}
		}
		
		return "Error";
	}
	
	@PostMapping(path="/guardar-comentario-evaluacion-tutor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String comentarioEvaluacionTutor(@RequestParam("comentario_evaluacion") String comentario, HttpSession session) {
		//Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}	
		
		Date fechaActual = new Date();	
		Evaluacion evaluacion = serviceEvaluacion.buscar(4);
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");	
		//Se valida si ya hay una avalusion asociada al grupo		
		EvaluacionTutor evaluacionTutor = serviceEvaTutor.buscarPorEvaluacionYGrupo(new Evaluacion(4), new Grupo(cveGrupo));
		//Se valida si ya hay un comentario asociado a al grupo y persona para la evaslucion del tutor		
		ComentarioEvaluacionTutor comentarioEvaluacionTutor = serviceComEvaTutor.buscarComentarioPorPersona(cvePersona, cveGrupo, 4);
		
		if(cveGrupo != null) {
		
			if(evaluacionTutor == null) {		
				
				EvaluacionTutor evaTutor = new EvaluacionTutor();
				evaTutor.setEvaluacion(new Evaluacion(4));
				evaTutor.setGrupo(new Grupo(cveGrupo));
				evaTutor.setFechaAlta(fechaActual);
				serviceEvaTutor.guardar(evaTutor);
				
				Comentario com = new Comentario();
				com.setTitulo(evaluacion.getNombre());
				com.setComentario(comentario);
				com.setPersona(new Persona(cvePersona));
				com.setFechaAlta(fechaActual);
				com.setActivo(true);
				
				ComentarioEvaluacionTutor comEvaTutor = new ComentarioEvaluacionTutor();
				comEvaTutor.setComentario(com);
				comEvaTutor.setEvaluacion(evaluacion);
				comEvaTutor.setEvaluacionTutor(evaTutor);
				serviceComEvaTutor.guardar(comEvaTutor);
				return "ok";
			}else{
				
				if(comentarioEvaluacionTutor == null) {	
					
					Comentario com = new Comentario();
					com.setTitulo(evaluacion.getNombre());
					com.setComentario(comentario);
					com.setPersona(new Persona(cvePersona));
					com.setFechaAlta(fechaActual);
					com.setActivo(true);
					
					ComentarioEvaluacionTutor comEvaTutor = new ComentarioEvaluacionTutor();
					comEvaTutor.setComentario(com);
					comEvaTutor.setEvaluacion(evaluacion);
					comEvaTutor.setEvaluacionTutor(evaluacionTutor);
					serviceComEvaTutor.guardar(comEvaTutor);
					return "ok";
				}else{
					
					comentarioEvaluacionTutor.getComentario().setComentario(comentario);
					comentarioEvaluacionTutor.getComentario().setFechaModificacion(fechaActual);
					serviceComEvaTutor.guardar(comentarioEvaluacionTutor);
					return "ok";
				}
				
			}			
			
		}	
		
		return "Error";
	}	
	
	@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Alumno')")
	@GetMapping("/entrevistaInicial")
	public String entrevistaInicial(Model model, HttpSession session) {		
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		int rol = usuario.getRoles().get(0).getId();
		Date fechaHoy = new Date();		
		
		Alumno alumno = null;
		List<Grupo> grupos = new ArrayList<>();
		//se valida si el usuario es alumno 
		if(rol==1) {
			alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
			grupos = serviceGrupo.buscarTodosDeAlumnosOrdenPorPeriodoDesc(alumno.getId());	
		}else {
			grupos = serviceGrupo.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		}
		
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
		Evaluacion evaluacion = null;
		if (cveGrupo != null) {			
			//se valida si el cuatrimestre al que pertenece el grupo seleccionado
			Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);
			if(grupo.getCuatrimestre().getConsecutivo()==1) {
				evaluacion = serviceEvaluacion.buscar(6);
			}else if(grupo.getCuatrimestre().getConsecutivo()==7) {
				evaluacion = serviceEvaluacion.buscar(7);
			}else{
				evaluacion = serviceEvaluacion.buscar(5);
			}
			
			//validación de las fechas de evaluación 
			List<Alumno> alumnos =  new ArrayList<>();
			if(rol==1) {
				alumnos.add(alumno);
			}else {
				alumnos = serviceAlumno.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			}
			
			List<CorteEvaluativo> cortesEva = serviceCorteEva.buscarPorPeridoYCarreraFechaInicioAsc(grupo.getPeriodo(), grupo.getCarrera());				
			//validación de las fechas de evaluación 				
			if((cortesEva.size() > 0) && (cortesEva.get(0).getInicioEvaluaciones() != null) && (cortesEva.get(0).getFinEvaluaciones() != null)) {
				CorteEvaluativo corteEvaluativo = cortesEva.get(0);
				if(corteEvaluativo.getInicioEvaluaciones().before(fechaHoy) && corteEvaluativo.getFinEvaluaciones().after(fechaHoy)) {
					
					//se valida si el usuario es alumno 
					Integer cvePersonaAl = null;
					if(rol==1) {
						cvePersonaAl = cvePersona;
					}else {
						cvePersonaAl = (Integer) session.getAttribute("t-cvePersonaAl");
					}
					
					if(cvePersonaAl!=null) {
						//Se inyectan las respuestas asociadas a cada pregunta de la evaluación seleccionada asi como su repuesta si es que la ahi		
						for (Pregunta pregunta : evaluacion.getPreguntas()) {
							
							List<OpcionRespuestaDTO> OpcionesRepuesta = resEvaIniService.buscarOpcionesRespuestaYRespuestaPorPregunta(pregunta.getId(), cvePersonaAl, evaluacion.getId(), cveGrupo);
							pregunta.setOpcionesRespuesta(OpcionesRepuesta);
							
							if(pregunta.getAbierta()==true) {
								RespuestaEvaluacionInicial respuestaEI = resEvaIniService.buscarRespuestaAbiertaPorPregunta(evaluacion.getId(), pregunta.getId(), cvePersonaAl, cveGrupo);
								pregunta.setComentarioRespuesta(respuestaEI != null ? respuestaEI.getRespuestaComentario().getComentario().getComentario() : null);
							}
							
						}
					}
					
					model.addAttribute("cvePersonaAl", cvePersonaAl);
					model.addAttribute("encuestaActiva", true);
				}else{
					model.addAttribute("encuestaActiva", false);
					model.addAttribute("corteEvaluativo", corteEvaluativo);
				}					
			}else{
				model.addAttribute("encuestaActiva", null);
			}
			model.addAttribute("alumnos", alumnos);
			if(rol==1) {
				model.addAttribute("grupo", grupo);
			}
		}
		model.addAttribute("evaluacion", evaluacion);	
		model.addAttribute("cveGrupo", cveGrupo);		
		model.addAttribute("grupos", grupos);
		model.addAttribute("rol", rol);
		return "encuestas/entrevistaInicial";
	}
	
	@PostMapping(path = "/guardar-entrevistaInicialTutor", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarIndividualTutor(@RequestBody Map<String, String> obj, HttpSession session, Model model){
		int cvePersona;
		try {
			cvePersona = Integer.parseInt(obj.get("idPersona"));
		} catch (Exception e) {
			cvePersona = 0;
		}
		
		Date fechaActual = new Date();
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
		List<PreguntaDTO> preguntas = new ArrayList<>();
		
		Evaluacion evaluacion = null;
		//se valida si el cuatrimestre al que pertenece el grupo seleccionado
		Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);
		if(grupo.getCuatrimestre().getConsecutivo()==1) {
			evaluacion = serviceEvaluacion.buscar(6);
		}else if(grupo.getCuatrimestre().getConsecutivo()==7) {
			evaluacion = serviceEvaluacion.buscar(7);
		}else{
			evaluacion = serviceEvaluacion.buscar(5);
		}
		
		if(evaluacion.getId() == 5) {
			
			PreguntaDTO pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(43);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(44);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(45);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(46);
			preguntas.add(pregunta);
			
		}else if(evaluacion.getId() == 6) {

			PreguntaDTO pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(77);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(90);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(106);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(130);
			preguntas.add(pregunta);
			
		}else if(evaluacion.getId() == 7) {
			
			PreguntaDTO pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(168);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(199);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(226);
			preguntas.add(pregunta);
			pregunta = new PreguntaDTO();
			pregunta.setIdPregunta(227);
			preguntas.add(pregunta);
			
		}
		
		if(cvePersona!=0) {
			if (cveGrupo != null) {
				//Se valida si ya hay una avalusion asociada al grupo		
				EvaluacionTutor evaluacionTutor = serviceEvaTutor.buscarPorEvaluacionYGrupo(evaluacion, new Grupo(cveGrupo));
				if(evaluacionTutor == null) {
					evaluacionTutor = new EvaluacionTutor();
					evaluacionTutor.setEvaluacion(evaluacion);
					evaluacionTutor.setGrupo(new Grupo(cveGrupo));
					evaluacionTutor.setFechaAlta(fechaActual);
					serviceEvaTutor.guardar(evaluacionTutor);
				}
				
				for(PreguntaDTO p:preguntas) {
					Pregunta pregunta = preguntaService.buscarPorId(p.getIdPregunta());
					if(pregunta.getAbierta()==true) {
		
						RespuestaEvaluacionInicial respuestaEI = resEvaIniService.buscarRespuestaAbiertaPorPregunta(pregunta.getEvaluacion().getId(), pregunta.getId(), cvePersona, cveGrupo);
						if(respuestaEI==null) {
							
							Comentario comentario =  new Comentario();
							comentario.setPersona(new Persona(cvePersona));
							comentario.setTitulo(pregunta.getEvaluacion().getNombre());
							comentario.setComentario(obj.get("pregunta"+pregunta.getConsecutivo()));
							comentario.setFechaAlta(fechaActual);
							comentario.setActivo(true);
							
							RespuestaComentario rComentario = new RespuestaComentario();
							rComentario.setPregunta(pregunta);
							rComentario.setComentario(comentario);
							rComentario.setEvaluacion(pregunta.getEvaluacion());
							
							RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
							resEvaInicial.setRespuesta(null);
							resEvaInicial.setRespuestaComentario(rComentario);
							resEvaInicial.setEvaluacionTutor(evaluacionTutor);
							resEvaIniService.guardar(resEvaInicial);
							
						}else{
							if(!respuestaEI.getRespuestaComentario().getComentario().getComentario().equals(obj.get("pregunta"+pregunta.getConsecutivo()))) {
								respuestaEI.getRespuestaComentario().getComentario().setComentario(obj.get("pregunta"+pregunta.getConsecutivo()));
								respuestaEI.getRespuestaComentario().getComentario().setFechaModificacion(fechaActual);
								resEvaIniService.guardar(respuestaEI);
							}
						}
						
					}else{
						
						RespuestaEvaluacionInicial respuestaEI = resEvaIniService.buscarRespuestaCerradaPorPregunta(pregunta.getEvaluacion().getId(), pregunta.getId(), cvePersona, cveGrupo);
				
						if(respuestaEI==null) {
							
							Respuesta respuesta = new Respuesta();
							respuesta.setEvaluacion(evaluacion);
							respuesta.setPregunta(pregunta);
							respuesta.setOpcionRespuesta(new OpcionRespuesta(Integer.parseInt(obj.get("pregunta"+pregunta.getConsecutivo()))));
							respuesta.setPersona(new Persona(cvePersona));
							respuesta.setActivo(true);
							respuesta.setFechaAlta(fechaActual);
							
							RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
							resEvaInicial.setRespuesta(respuesta);
							resEvaInicial.setRespuestaComentario(null);
							resEvaInicial.setEvaluacionTutor(evaluacionTutor);
							resEvaIniService.guardar(resEvaInicial);
							
						}else{
							if(obj.get("pregunta"+pregunta.getConsecutivo())!=null) {
								if(respuestaEI.getRespuesta().getOpcionRespuesta().getId() != Integer.parseInt(obj.get("pregunta"+pregunta.getConsecutivo()))) {
									respuestaEI.getRespuesta().setOpcionRespuesta(new OpcionRespuesta(Integer.parseInt(obj.get("pregunta"+pregunta.getConsecutivo()))));
									respuestaEI.getRespuesta().setFechaModificacion(fechaActual);
									resEvaIniService.guardar(respuestaEI);
								}
							}
						}
						
					}
				}
				return "ok";
			}
			return "noGru";
		}
		return "noAl";
	}
	
	@PostMapping(path = "/guardar-entrevistaInicialAlumno", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarInicialAlumno(@RequestBody Map<String, String> obj, HttpSession session, Model model){
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Date fechaActual = new Date();
		List<String> respuestasV = new ArrayList<>();

		respuestasV.add(obj.get("pregunta1"));
		respuestasV.add(obj.get("pregunta2"));
		respuestasV.add(obj.get("pregunta3"));
		respuestasV.add(obj.get("pregunta4"));
		respuestasV.add(obj.get("pregunta5"));
		respuestasV.add(obj.get("pregunta6"));
		respuestasV.add(obj.get("pregunta7"));
		respuestasV.add(obj.get("pregunta8"));
		respuestasV.add(obj.get("pregunta9"));
		respuestasV.add(obj.get("pregunta10"));
		respuestasV.add(obj.get("pregunta11"));
		respuestasV.add(obj.get("pregunta12"));
		if(obj.get("cveEvaluacion").equals("6") || obj.get("cveEvaluacion").equals("7")) {
			respuestasV.add(obj.get("pregunta13"));
			respuestasV.add(obj.get("pregunta14"));
			respuestasV.add(obj.get("pregunta15"));
			respuestasV.add(obj.get("pregunta16"));
			respuestasV.add(obj.get("pregunta17"));
			respuestasV.add(obj.get("pregunta18"));
			respuestasV.add(obj.get("pregunta19"));
			respuestasV.add(obj.get("pregunta20"));
			respuestasV.add(obj.get("pregunta21"));
			respuestasV.add(obj.get("pregunta22"));
			respuestasV.add(obj.get("pregunta23"));
			respuestasV.add(obj.get("pregunta24"));
			respuestasV.add(obj.get("pregunta25"));
			respuestasV.add(obj.get("pregunta26"));
			respuestasV.add(obj.get("pregunta27"));
			respuestasV.add(obj.get("pregunta28"));
			respuestasV.add(obj.get("pregunta29"));
			respuestasV.add(obj.get("pregunta30"));
			respuestasV.add(obj.get("pregunta31"));
			respuestasV.add(obj.get("pregunta32"));
			respuestasV.add(obj.get("pregunta33"));
			respuestasV.add(obj.get("pregunta34"));
			respuestasV.add(obj.get("pregunta35"));
			respuestasV.add(obj.get("pregunta36"));
			respuestasV.add(obj.get("pregunta37"));
			respuestasV.add(obj.get("pregunta38"));
			respuestasV.add(obj.get("pregunta39"));
			respuestasV.add(obj.get("pregunta40"));
			respuestasV.add(obj.get("pregunta41"));
			respuestasV.add(obj.get("pregunta42"));
			respuestasV.add(obj.get("pregunta43"));
			respuestasV.add(obj.get("pregunta44"));
			respuestasV.add(obj.get("pregunta45"));
			respuestasV.add(obj.get("pregunta46"));
			respuestasV.add(obj.get("pregunta47"));
			respuestasV.add(obj.get("pregunta48"));
			respuestasV.add(obj.get("pregunta49"));
			respuestasV.add(obj.get("pregunta50"));
			respuestasV.add(obj.get("pregunta51"));
			respuestasV.add(obj.get("pregunta52"));
			respuestasV.add(obj.get("pregunta53"));
			respuestasV.add(obj.get("pregunta54"));
			respuestasV.add(obj.get("pregunta55"));
			respuestasV.add(obj.get("pregunta56"));
			respuestasV.add(obj.get("pregunta57"));
			respuestasV.add(obj.get("pregunta58"));
			respuestasV.add(obj.get("pregunta59"));
			respuestasV.add(obj.get("pregunta60"));
			respuestasV.add(obj.get("pregunta61"));
			respuestasV.add(obj.get("pregunta62"));
			respuestasV.add(obj.get("pregunta63"));
			respuestasV.add(obj.get("pregunta64"));
			respuestasV.add(obj.get("pregunta65"));
			respuestasV.add(obj.get("pregunta66"));
			respuestasV.add(obj.get("pregunta67"));
			respuestasV.add(obj.get("pregunta68"));
			respuestasV.add(obj.get("pregunta69"));
			respuestasV.add(obj.get("pregunta70"));
			respuestasV.add(obj.get("pregunta71"));
			respuestasV.add(obj.get("pregunta72"));
			respuestasV.add(obj.get("pregunta73"));
			respuestasV.add(obj.get("pregunta74"));
			respuestasV.add(obj.get("pregunta75"));
			respuestasV.add(obj.get("pregunta76"));
			respuestasV.add(obj.get("pregunta77"));
			respuestasV.add(obj.get("pregunta78"));
			respuestasV.add(obj.get("pregunta79"));
			respuestasV.add(obj.get("pregunta80"));
			respuestasV.add(obj.get("pregunta81"));
			respuestasV.add(obj.get("pregunta82"));
			respuestasV.add(obj.get("pregunta83"));
		}
		if(obj.get("cveEvaluacion").equals("7")) {
			respuestasV.add(obj.get("pregunta84"));
			respuestasV.add(obj.get("pregunta85"));
			respuestasV.add(obj.get("pregunta86"));
			respuestasV.add(obj.get("pregunta87"));
			respuestasV.add(obj.get("pregunta88"));
			respuestasV.add(obj.get("pregunta89"));
			respuestasV.add(obj.get("pregunta90"));
			respuestasV.add(obj.get("pregunta91"));
			respuestasV.add(obj.get("pregunta92"));
			respuestasV.add(obj.get("pregunta93"));
			respuestasV.add(obj.get("pregunta94"));
			respuestasV.add(obj.get("pregunta95"));
			respuestasV.add(obj.get("pregunta96"));
			respuestasV.add(obj.get("pregunta97"));
		}
	
		Evaluacion evaluacion = null;
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
		if(cvePersona!=0) {
			if (cveGrupo != null) {
				
				//se valida si el cuatrimestre al que pertenece el grupo seleccionado
				Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);
				if(grupo.getCuatrimestre().getConsecutivo()==1) {
					evaluacion = serviceEvaluacion.buscar(6);
				}else if(grupo.getCuatrimestre().getConsecutivo()==7) {
					evaluacion = serviceEvaluacion.buscar(7);
				}else{
					evaluacion = serviceEvaluacion.buscar(5);
				}
				
				//Se valida si ya hay una avalusion asociada al grupo		
				EvaluacionTutor evaluacionTutor = serviceEvaTutor.buscarPorEvaluacionYGrupo(evaluacion, new Grupo(cveGrupo));
				if(evaluacionTutor == null) {
					evaluacionTutor = new EvaluacionTutor();
					evaluacionTutor.setEvaluacion(evaluacion);
					evaluacionTutor.setGrupo(new Grupo(cveGrupo));
					evaluacionTutor.setFechaAlta(fechaActual);
					serviceEvaTutor.guardar(evaluacionTutor);
				}
				
				for(Pregunta pregunta : evaluacion.getPreguntas()) {
						
					RespuestaEvaluacionInicial respuestaEI = null;
					List<RespuestaEvaluacionInicial> respuestasEI = new ArrayList<>();
					if(pregunta.getAbierta()==true) {
						respuestaEI = resEvaIniService.buscarRespuestaAbiertaPorPregunta(evaluacion.getId(), pregunta.getId(), cvePersona, cveGrupo);
					}else if(pregunta.getLimite()>1){
						respuestasEI = resEvaIniService.buscarRespuestaCerradaMultiplePorPregunta(evaluacion.getId(), pregunta.getId(), cvePersona, cveGrupo);
					}else{
						respuestaEI = resEvaIniService.buscarRespuestaCerradaPorPregunta(evaluacion.getId(), pregunta.getId(), cvePersona, cveGrupo);
					}
					
					if(pregunta.getLimite()>1) {
						
						//se comvierte el string con las repuestas del selector multiple en una lista del tipo Integer
						String s = ",";
						String r = respuestasV.get(pregunta.getConsecutivo()-1);							
					    String[] re = r.split(s);		
						List<String> res = Arrays.asList(re);				
						ArrayList<Integer> respuestas = new ArrayList<Integer>();//lista de repuestas seleccionadas
						for(int i = 0; i < res.size(); i++) {
							respuestas.add(Integer.parseInt(res.get(i)));   
						}
						
						//se valida si ya hay repuestas 
						if(respuestasEI.size()==0) {
							
							for(Integer respuestaF : respuestas) {
								Respuesta respuesta = new Respuesta();
								respuesta.setEvaluacion(evaluacion);
								respuesta.setPregunta(pregunta);
								respuesta.setOpcionRespuesta(new OpcionRespuesta(respuestaF));
								respuesta.setPersona(new Persona(cvePersona));
								respuesta.setActivo(true);
								respuesta.setFechaAlta(fechaActual);
								
								RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
								resEvaInicial.setRespuesta(respuesta);
								resEvaInicial.setRespuestaComentario(null);
								resEvaInicial.setEvaluacionTutor(evaluacionTutor);
								resEvaIniService.guardar(resEvaInicial);
							}
							
						}else{
							
							//Consulto las opciones de respuesta asociadas a cada pregunta
							List<OpcionRespuesta> opcionRespuestas = opcionesRepuestaService.buscarPorPregunta(pregunta.getId());
							RespuestaEvaluacionInicial VRespuestaEI = null;
							//se recorren las opciones de respuesta disponibles con las recibidas del formulario
						    for (OpcionRespuesta opcionR : opcionRespuestas) { 
						        if (!respuestas.contains(opcionR.getId())) { 
						        	// eliminar
						        	VRespuestaEI = resEvaIniService.buscarRespuestaCerradaPorPreguntaYOpcionRespuesta(pregunta.getEvaluacion().getId(), pregunta.getId(), opcionR.getId(), cvePersona, cveGrupo);
						        	if(VRespuestaEI!=null) {
						        		resEvaIniService.eliminar(VRespuestaEI);
						        	}
						        }else{
						        	//agregar
						        	VRespuestaEI = resEvaIniService.buscarRespuestaCerradaPorPreguntaYOpcionRespuesta(pregunta.getEvaluacion().getId(), pregunta.getId(), opcionR.getId(), cvePersona, cveGrupo);
						        	if(VRespuestaEI==null) {
						        		Respuesta respuesta = new Respuesta();
										respuesta.setEvaluacion(evaluacion);
										respuesta.setPregunta(pregunta);
										respuesta.setOpcionRespuesta(opcionR);
										respuesta.setPersona(new Persona(cvePersona));
										respuesta.setActivo(true);
										respuesta.setFechaAlta(fechaActual);
										
										RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
										resEvaInicial.setRespuesta(respuesta);
										resEvaInicial.setRespuestaComentario(null);
										resEvaInicial.setEvaluacionTutor(evaluacionTutor);
										resEvaIniService.guardar(resEvaInicial);
						        	}
						        }
						    } 

						}	
						
					}else{
						
						if(respuestaEI==null) {
							if(pregunta.getAbierta()==true){
								if( respuestasV.get(pregunta.getConsecutivo()-1)!=null) {
									Comentario comentario =  new Comentario();
									comentario.setPersona(new Persona(cvePersona));
									comentario.setTitulo(evaluacion.getNombre());
									comentario.setComentario(respuestasV.get(pregunta.getConsecutivo()-1));
									comentario.setFechaAlta(fechaActual);
									comentario.setActivo(true);
									
									RespuestaComentario rComentario = new RespuestaComentario();
									rComentario.setPregunta(pregunta);
									rComentario.setComentario(comentario);
									rComentario.setEvaluacion(evaluacion);
									
									RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
									resEvaInicial.setRespuesta(null);
									resEvaInicial.setRespuestaComentario(rComentario);
									resEvaInicial.setEvaluacionTutor(evaluacionTutor);
									resEvaIniService.guardar(resEvaInicial);
								}
							}else{
								if(respuestasV.get(pregunta.getConsecutivo()-1)!=null) {
									Respuesta respuesta = new Respuesta();
									respuesta.setEvaluacion(evaluacion);
									respuesta.setPregunta(pregunta);
									respuesta.setOpcionRespuesta(new OpcionRespuesta(Integer.parseInt(respuestasV.get(pregunta.getConsecutivo()-1))));
									respuesta.setPersona(new Persona(cvePersona));
									respuesta.setActivo(true);
									respuesta.setFechaAlta(fechaActual);
									
									RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
									resEvaInicial.setRespuesta(respuesta);
									resEvaInicial.setRespuestaComentario(null);
									resEvaInicial.setEvaluacionTutor(evaluacionTutor);
									resEvaIniService.guardar(resEvaInicial);
								}
							}
						
						}else{
							
							if(pregunta.getAbierta()==true){
								
								if(respuestasV.get(pregunta.getConsecutivo()-1)!=null) {
									if(!respuestaEI.getRespuestaComentario().getComentario().getComentario().equals(respuestasV.get(pregunta.getConsecutivo()-1))) {
										respuestaEI.getRespuestaComentario().getComentario().setComentario(respuestasV.get(pregunta.getConsecutivo()-1));
										respuestaEI.getRespuestaComentario().getComentario().setFechaModificacion(fechaActual);
										resEvaIniService.guardar(respuestaEI);
									}
								}
								
							}else{
								
								if(respuestasV.get(pregunta.getConsecutivo()-1)!=null) {
									if(respuestaEI.getRespuesta().getOpcionRespuesta().getId() != Integer.parseInt(respuestasV.get(pregunta.getConsecutivo()-1))) {
										respuestaEI.getRespuesta().setOpcionRespuesta(new OpcionRespuesta(Integer.parseInt(respuestasV.get(pregunta.getConsecutivo()-1))));
										respuestaEI.getRespuesta().setFechaModificacion(fechaActual);
										resEvaIniService.guardar(respuestaEI);
									}
								}
								
							}
							
						}	
					}
					
				}
				return "ok";
			}
			return "noGru";
		}
		return "noAl";
	}
	
	@PostMapping(path = "/guardar-entrevistaInicialIndividual", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarInicialIndividual(@RequestBody Map<String, String> obj, HttpSession session, Model model){
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = 0;
		}
		
		int rol = usuario.getRoles().get(0).getId();
		
		if(rol!=1) {
			try {
				cvePersona = (Integer) session.getAttribute("t-cvePersonaAl");
			} catch (Exception e) {
				cvePersona = 0;
			}
		}
		
		Date fechaHoy = new Date();		
		String idPregunta = obj.get("idPregunta");
		String Respuesta = obj.get("idRespuesta");
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
		
		if(idPregunta!=null) {
			if(Respuesta!=null && !Respuesta.isEmpty()) {
				if(cvePersona!=0) {
					if (cveGrupo != null) {
						
						Pregunta pregunta = preguntaService.buscarPorId(Integer.parseInt(idPregunta));
						
						//Se valida si ya hay una avalusion asociada al grupo		
						EvaluacionTutor evaluacionTutor = serviceEvaTutor.buscarPorEvaluacionYGrupo(pregunta.getEvaluacion(), new Grupo(cveGrupo));
						if(evaluacionTutor == null) {
							evaluacionTutor = new EvaluacionTutor();
							evaluacionTutor.setEvaluacion(pregunta.getEvaluacion());
							evaluacionTutor.setGrupo(new Grupo(cveGrupo));
							evaluacionTutor.setFechaAlta(fechaHoy);
							serviceEvaTutor.guardar(evaluacionTutor);
						}
						
						RespuestaEvaluacionInicial respuestaEI = null;
						List<RespuestaEvaluacionInicial> respuestasEI = new ArrayList<>();
						if(pregunta.getAbierta()==true) {
							respuestaEI = resEvaIniService.buscarRespuestaAbiertaPorPregunta(pregunta.getEvaluacion().getId(), pregunta.getId(), cvePersona, cveGrupo);
						}else if(pregunta.getLimite()>1){
							respuestasEI = resEvaIniService.buscarRespuestaCerradaMultiplePorPregunta(pregunta.getEvaluacion().getId(), pregunta.getId(), cvePersona, cveGrupo);
						}else{
							respuestaEI = resEvaIniService.buscarRespuestaCerradaPorPregunta(pregunta.getEvaluacion().getId(), pregunta.getId(), cvePersona, cveGrupo);
						}
						
						if(pregunta.getLimite()>1) {
							
							//se comvierte el string con las repuestas del selector multiple en una lista del tipo Integer
							String s = ",";
							String r = Respuesta;							
						    String[] re = r.split(s);		
							List<String> res = Arrays.asList(re);				
							ArrayList<Integer> respuestas = new ArrayList<Integer>();//lista de repuestas seleccionadas
							for(int i = 0; i < res.size(); i++) {
								respuestas.add(Integer.parseInt(res.get(i)));   
							}
							
							//se valida si ya hay repuestas 
							if(respuestasEI.size()==0) {
								
								for(Integer respuestaF : respuestas) {
									Respuesta respuesta = new Respuesta();
									respuesta.setEvaluacion(pregunta.getEvaluacion());
									respuesta.setPregunta(pregunta);
									respuesta.setOpcionRespuesta(new OpcionRespuesta(respuestaF));
									respuesta.setPersona(new Persona(cvePersona));
									respuesta.setActivo(true);
									respuesta.setFechaAlta(fechaHoy);
									
									RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
									resEvaInicial.setRespuesta(respuesta);
									resEvaInicial.setRespuestaComentario(null);
									resEvaInicial.setEvaluacionTutor(evaluacionTutor);
									resEvaIniService.guardar(resEvaInicial);
								}
								
							}else{
								//Consulto las opciones de respuesta asociadas a cada pregunta
								List<OpcionRespuesta> opcionRespuestas = opcionesRepuestaService.buscarPorPregunta(pregunta.getId());
								RespuestaEvaluacionInicial VRespuestaEI = null;
								//se recorren las opciones de respuesta disponibles con las recibidas del formulario
							    for (OpcionRespuesta opcionR : opcionRespuestas) { 
							        if (!respuestas.contains(opcionR.getId())) { 
							        	// eliminar
							        	VRespuestaEI = resEvaIniService.buscarRespuestaCerradaPorPreguntaYOpcionRespuesta(pregunta.getEvaluacion().getId(), pregunta.getId(), opcionR.getId(), cvePersona, cveGrupo);
							        	if(VRespuestaEI!=null) {
							        		resEvaIniService.eliminar(VRespuestaEI);
							        	}
							        }else{
							        	//agregar
							        	VRespuestaEI = resEvaIniService.buscarRespuestaCerradaPorPreguntaYOpcionRespuesta(pregunta.getEvaluacion().getId(), pregunta.getId(), opcionR.getId(), cvePersona, cveGrupo);
							        	if(VRespuestaEI==null) {
							        		Respuesta respuesta = new Respuesta();
											respuesta.setEvaluacion(pregunta.getEvaluacion());
											respuesta.setPregunta(pregunta);
											respuesta.setOpcionRespuesta(opcionR);
											respuesta.setPersona(new Persona(cvePersona));
											respuesta.setActivo(true);
											respuesta.setFechaAlta(fechaHoy);
											
											RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
											resEvaInicial.setRespuesta(respuesta);
											resEvaInicial.setRespuestaComentario(null);
											resEvaInicial.setEvaluacionTutor(evaluacionTutor);
											resEvaIniService.guardar(resEvaInicial);
							        	}
							        }
							    } 
						 							
							}	
							
						}else{
							
							if(respuestaEI==null) {
								if(pregunta.getAbierta()==true){
									
										Comentario comentario =  new Comentario();
										comentario.setPersona(new Persona(cvePersona));
										comentario.setTitulo(pregunta.getEvaluacion().getNombre());
										comentario.setComentario(Respuesta);
										comentario.setFechaAlta(fechaHoy);
										comentario.setActivo(true);
										
										RespuestaComentario rComentario = new RespuestaComentario();
										rComentario.setPregunta(pregunta);
										rComentario.setComentario(comentario);
										rComentario.setEvaluacion(pregunta.getEvaluacion());
										
										RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
										resEvaInicial.setRespuesta(null);
										resEvaInicial.setRespuestaComentario(rComentario);
										resEvaInicial.setEvaluacionTutor(evaluacionTutor);
										resEvaIniService.guardar(resEvaInicial);
									
								}else{
									
									Respuesta respuesta = new Respuesta();
									respuesta.setEvaluacion(pregunta.getEvaluacion());
									respuesta.setPregunta(pregunta);
									respuesta.setOpcionRespuesta(new OpcionRespuesta(Integer.parseInt(Respuesta)));
									respuesta.setPersona(new Persona(cvePersona));
									respuesta.setActivo(true);
									respuesta.setFechaAlta(fechaHoy);
									
									RespuestaEvaluacionInicial resEvaInicial = new RespuestaEvaluacionInicial();							
									resEvaInicial.setRespuesta(respuesta);
									resEvaInicial.setRespuestaComentario(null);
									resEvaInicial.setEvaluacionTutor(evaluacionTutor);
									resEvaIniService.guardar(resEvaInicial);
									
								}
							
							}else{
								
								if(pregunta.getAbierta()==true){
									if(!respuestaEI.getRespuestaComentario().getComentario().getComentario().equals(Respuesta)) {
										respuestaEI.getRespuestaComentario().getComentario().setComentario(Respuesta);
										respuestaEI.getRespuestaComentario().getComentario().setFechaModificacion(fechaHoy);
										resEvaIniService.guardar(respuestaEI);
									}
								}else{
									if(respuestaEI.getRespuesta().getOpcionRespuesta().getId() != Integer.parseInt(Respuesta)) {
										respuestaEI.getRespuesta().setOpcionRespuesta(new OpcionRespuesta(Integer.parseInt(Respuesta)));
										respuestaEI.getRespuesta().setFechaModificacion(fechaHoy);
										resEvaIniService.guardar(respuestaEI);
									}
								}
							
							}	
						}
						
						return "ok";
					}
					return "noGru";
				}
				return "noAl";	
			}
			return "noRe";	
		}
		return "noPre";
	}
	
}
