package edu.mx.utdelacosta.controller;

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
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.Respuesta;
import edu.mx.utdelacosta.model.RespuestaCargaEvaluacion;
import edu.mx.utdelacosta.model.RespuestaEvaluacionTutor;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICargaEvaluacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IComentarioEvaluacionTutorService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IEvaluacionComentarioService;
import edu.mx.utdelacosta.service.IEvaluacionTutorService;
import edu.mx.utdelacosta.service.IEvaluacionesService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IRespuestaCargaEvaluacionService;
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
					System.err.println("existe carga evaluacion y actauliza");
					respuestaCaEva.getRespuesta().setOpcionRespuesta(new OpcionRespuesta(idOpRepuesta));
					respuestaCaEva.getRespuesta().setFechaModificacion(fechaActual);
					serviceResCarEva.guardar(respuestaCaEva);
					return "ok";
				}
			}else{
				System.err.println("existe carga horaria");
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
					serviceEvaluacionComentario.guardar(evaluacionComentario);
					return "ok";
				}
				
			}			
		}
		return "error";
	}	
	
	@GetMapping("/entrevistaInicial")
	public String entrevistaInicial() {
		return "encuestas/entrevistaInicial";
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
					serviceComEvaTutor.guardar(comentarioEvaluacionTutor);
					return "ok";
				}
				
			}			
			
		}	
		
		return "Error";
	}	
	
}
