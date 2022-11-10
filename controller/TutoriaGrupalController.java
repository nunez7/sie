package edu.mx.utdelacosta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AsesoriaSolicitud;
import edu.mx.utdelacosta.model.AsistenciaTemaGrupal;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Foco;
import edu.mx.utdelacosta.model.FocosAtencion;
import edu.mx.utdelacosta.model.Fortaleza;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.TemaGrupal;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoAsistenciaDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsesoriaService;
import edu.mx.utdelacosta.service.IAsistenciaTemaGrupalService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IFocosAtencionService;
import edu.mx.utdelacosta.service.IFortalezaService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.ITemaGrupalService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Academia') and hasRole('Director')")
@RequestMapping("/tutoria-grupal")
public class TutoriaGrupalController {
	
	@Value("${spring.mail.username}")
	private String correo;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private EmailSenderService emailService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private IFortalezaService fortalezaService;

	@Autowired
	private IFocosAtencionService focoAtenService;

	@Autowired
	private ITemaGrupalService temaGrupalService;

	@Autowired
	private IAsistenciaTemaGrupalService asisTemaGruService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private ICargaHorariaService cargaHorariaService;

	@Autowired
	private IAsesoriaService asesoriaService;

	@PostMapping(path = "/actualizar-tema", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String actualizarTema(@RequestBody Map<String, String> obj, HttpSession session) throws ParseException {
		SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dFHora = new SimpleDateFormat("hh:mm");
		Date fechaHoy = new Date();

		Integer cveTemaGrupal = Integer.parseInt(obj.get("cveTemaGrupal"));
		Date fechaProgramada = dFDia.parse(obj.get("fechaProgramada"));
		Date horaInicio = dFHora.parse(obj.get("horaInicio"));
		String tema = obj.get("tema").trim();
		Integer foco = Integer.parseInt(obj.get("vulnerabilidad"));
		String actividad = obj.get("actividad").trim();

		String subActividad = obj.get("subActividad").trim();
		String objetivo = obj.get("objetivo").trim();
		String observaciones = obj.get("observaciones").trim();
		String seguimiento = obj.get("seguimiento").trim();

		TemaGrupal temaGrupal = temaGrupalService.bucarPorId(cveTemaGrupal);
		if (temaGrupal != null) {
			if (temaGrupal.getFechaRealizada() == null) {
				temaGrupal.setFechaRealizada(fechaHoy);
			}
			temaGrupal.setFechaProgramada(fechaProgramada);
			temaGrupal.setHoraInicio(horaInicio);
			temaGrupal.setTema(tema);
			temaGrupal.setFoco(new Foco(foco));
			temaGrupal.setActividad(actividad);
			temaGrupal.setSubActividad(subActividad);
			temaGrupal.setObjetivo(objetivo);
			temaGrupal.setObservaciones(observaciones);
			temaGrupal.setSeguimiento(seguimiento);
			temaGrupalService.guardar(temaGrupal);
			return "ok";
		}
		return "error";
	}

	@PostMapping(path = "/guardar-fortaleza", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarFortalezas(@RequestBody Map<String, String> obj, HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");

		String cveFortaleza = obj.get("ed-eveFortaleza");
		if (cveFortaleza == null) {
			String fort = obj.get("fortaleza").trim();
			if (cveGrupo != null) {
				if (fort.isEmpty() || fort == null) {
					return "FNull";
				} else {
					Fortaleza fortaleza = new Fortaleza();
					fortaleza.setGrupo(new Grupo(cveGrupo));
					fortaleza.setFortaleza(fort);
					fortalezaService.guardar(fortaleza);
					return "ok";
				}
			}
			return "noGrupo";
		} else {
			String edFortaleza = obj.get("ed-fortaleza").trim();

			Fortaleza UpFortaleza = fortalezaService.buscarPorId(Integer.parseInt(cveFortaleza));
			UpFortaleza.setFortaleza(edFortaleza);
			fortalezaService.guardar(UpFortaleza);

			return "upFortaleza";
		}
	}

	@GetMapping("/cargar-tema-grupal/{cveTemaTutoria}")
	public String temaGrupal(@PathVariable(name = "cveTemaTutoria", required = false) Integer cveTemaTutoria,
			Model model) {
		TemaGrupal temaGrupal = temaGrupalService.bucarPorId(cveTemaTutoria);

		if (temaGrupal != null) {
			List<AlumnoAsistenciaDTO> alumnos = asisTemaGruService
					.buscarPorTemaGrupalYGrupo(temaGrupal.getGrupo().getId(), temaGrupal.getId());
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("temaGrupal", temaGrupal);
		}

		return "tutorias/detalleTutoriaGrupal";
	}

	@GetMapping("/cargar-fortalezas")
	public String cargarFortalezas(Model model, HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		List<Fortaleza> fortalezas = null;
		if (cveGrupo != null) {
			fortalezas = fortalezaService.buscarPorGrupo(new Grupo(cveGrupo));
		}
		model.addAttribute("fortalezas", fortalezas);
		return "fragments/tutorias :: cargar-fortalezas";
	}

	@PostMapping(path = "/eliminar-fortaleza", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarFortalezas(@RequestBody Map<String, String> obj) {
		Integer cveFortaleza = Integer.parseInt(obj.get("id"));
		fortalezaService.eliminar(new Fortaleza(cveFortaleza));
		return "ok";
	}

	@PostMapping(path = "/guardar-foco", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarFoco(@RequestBody Map<String, String> obj, HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");

		String cveFoco = obj.get("ed-evefocoAtencion");
		String edFocoAtencion = obj.get("ed-focoAtencion");
		String idFoco = obj.get("ed-foco");

		if (cveFoco == null) {
			String focoAtencion = obj.get("focoAtencion").trim();
			Integer foco = Integer.parseInt(obj.get("foco"));
			if (cveGrupo != null) {
				if (foco == 1 || foco == 2 || foco == 3) {
					if (focoAtencion.isEmpty() || focoAtencion == null) {
						return "FANull";
					} else {
						FocosAtencion focosAtencion = new FocosAtencion();
						focosAtencion.setDescripcion(focoAtencion);
						focosAtencion.setFoco(new Foco(foco));
						focosAtencion.setGrupo(new Grupo(cveGrupo));
						focoAtenService.guardar(focosAtencion);
						return "ok";
					}
				} else {
					return "Df";
				}
			}
			return "noGrupo";
		} else {
			FocosAtencion upFocosAtencion = focoAtenService.buscarPorId(Integer.parseInt(cveFoco));
			upFocosAtencion.setDescripcion(edFocoAtencion);
			upFocosAtencion.setFoco(new Foco(Integer.parseInt(idFoco)));
			focoAtenService.guardar(upFocosAtencion);
			return "upFoco";
		}
	}
	

	@PostMapping(path = "/eliminar-foco", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarFoco(@RequestBody Map<String, String> obj) {
		Integer cveFoco = Integer.parseInt(obj.get("id"));
		focoAtenService.eliminar(new FocosAtencion(cveFoco));
		return "ok";
	}

	@GetMapping("/cargar-focos")
	public String cargarFocos(Model model, HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		List<FocosAtencion> focosAtencion = null;
		if (cveGrupo != null) {
			focosAtencion = focoAtenService.buscarPorGrupo(new Grupo(cveGrupo));
		}
		model.addAttribute("focosAtencion", focosAtencion);
		return "fragments/tutorias :: cargar-focos";
	}
	

	@PostMapping(path = "/programar-tema", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarTema(@RequestBody Map<String, String> obj, HttpSession session) throws ParseException {
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		String tema = obj.get("tema").trim();
		Integer foco = Integer.parseInt(obj.get("tipoVul"));
		String actividades = obj.get("actividades").trim();

		SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaProgramada = dFDia.parse(obj.get("fechaProgramada"));

		if (cveGrupo != null) {
			if (foco == 1 || foco == 2 || foco == 3) {
				if (tema.isEmpty() || tema == null) {
					return "noTema";
				} else {
					TemaGrupal temaGrupal = new TemaGrupal();
					temaGrupal.setTema(tema);
					temaGrupal.setFoco(new Foco(foco));
					temaGrupal.setGrupo(new Grupo(cveGrupo));
					temaGrupal.setActividad(actividades);
					temaGrupal.setFechaProgramada(fechaProgramada);
					temaGrupal.setFechaRegistro(new Date());
					temaGrupalService.guardar(temaGrupal);

					List<Alumno> alumno = alumnoService.buscarPorGrupo(cveGrupo);
					for (Alumno al : alumno) {
						AsistenciaTemaGrupal asistencia = new AsistenciaTemaGrupal();
						asistencia.setTemaGrupal(temaGrupal);
						asistencia.setAlumno(al);
						asistencia.setAsistencia("A");
						asisTemaGruService.guardar(asistencia);
					}
					return "ok";
				}
			} else {
				return "Df";
			}
		}
		return "noGrupo";
	}
	
	@GetMapping("/cargar-temas")
	public String cargarTemas(Model model, HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		List<TemaGrupal> temasGrupales = null;
		if (cveGrupo != null) {
			temasGrupales = temaGrupalService.buscarPorGrupo(new Grupo(cveGrupo));
		}
		model.addAttribute("temasGrupales", temasGrupales);
		return "fragments/tutorias :: cargar-temas";
	}
	

	@PostMapping(path = "/eliminar-tema", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarTema(@RequestBody Map<String, String> obj) {
		String cveTema = obj.get("id");
		if (cveTema != null) {
			TemaGrupal temaGrupal = temaGrupalService.bucarPorId(Integer.parseInt(cveTema));
			// elimina el historial de asistencias asociadas a al tema
			List<AsistenciaTemaGrupal> asistencias = asisTemaGruService.buscarPorTemaGrupal(temaGrupal);
			for (AsistenciaTemaGrupal asistencia : asistencias) {
				asisTemaGruService.eliminar(asistencia);
			}
			temaGrupalService.eliminar(temaGrupal);
			return "ok";
		}
		return "error";
	}

	@PostMapping(path = "/asistencia-tutoria", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String asistenciaTutoria(@RequestBody Map<String, String> obj) {
		Integer idTemaGrupal = Integer.parseInt(obj.get("idTemaGrupal"));
		Integer idAlumno = Integer.parseInt(obj.get("idAlumno"));
		String tipoAsistencia = obj.get("tipoAsistencia");

		if (idTemaGrupal != null && idAlumno != null && tipoAsistencia != null) {
			AsistenciaTemaGrupal asistenciaTemaGrupal = asisTemaGruService
					.buscarPorTemaGrupalYAlumno(new TemaGrupal(idTemaGrupal), new Alumno(idAlumno));

			if (asistenciaTemaGrupal == null) {
				AsistenciaTemaGrupal asistencia = new AsistenciaTemaGrupal();
				asistencia.setTemaGrupal(new TemaGrupal(idTemaGrupal));
				asistencia.setAlumno(new Alumno(idAlumno));
				asistencia.setAsistencia(tipoAsistencia);
				asisTemaGruService.guardar(asistencia);
				return "ok";
			} else {
				asistenciaTemaGrupal.setAsistencia(tipoAsistencia);
				asisTemaGruService.guardar(asistenciaTemaGrupal);
				return "ok";
			}

		}
		return "Error";
	}
	
	@PostMapping(path = "/solicitar-asesoria", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String solistaAsesoriaGrupal(@RequestBody AsesoriaSolicitud asesoria, HttpSession session)
			throws ParseException {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		Persona persona = personaService.buscarPorId(cvePersona);
		Integer cveGrupo = asesoria.getIdGrupo();
		String razon = asesoria.getRazon();
		String comentario = asesoria.getComentarios();
		if (cveGrupo != null) {
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			if (asesoria.getMaterias() != null) {
				// Recibo los motivos de la tutoría como un string y los convierto a una
				// ArrayList<Integer>
				String ma = asesoria.getMaterias();
				String[] mat = ma.split(",");
				List<String> mater = Arrays.asList(mat);
				ArrayList<Integer> materias = new ArrayList<Integer>();// lista de motivos seleccionados
				for (int i = 0; i < mater.size(); i++) {
					materias.add(Integer.parseInt(mater.get(i)));
				}
				String materiasGuardar = "";
				boolean enviado = false;
				for (Integer cveMateria : materias) {
					CargaHoraria cargaHoraria = cargaHorariaService.buscarPorIdCarga(cveMateria);
					Mail mail = new Mail();
					String de = correo;
					String para = cargaHoraria.getProfesor().getEmail();
					materiasGuardar+= cargaHoraria.getMateria().getNombre()+", ";
					mail.setDe(de);
					mail.setPara(new String[] { para });
					// Email title
					mail.setTitulo("Canalización grupal");
					// Variables a plantilla
					Map<String, Object> variables = new HashMap<>();
					variables.put("titulo", "Solicitud de canalización grupal");

					variables.put("cuerpoCorreo", "El tutor(a) " + persona.getNombreCompletoConNivelEstudio()
							+ ", solicita una canalización para " + grupo.getNombre() + ", para la materia "
							+ cargaHoraria.getMateria().getNombre() + " que imparte" + ", por esta razón: " + razon
							+ ". " + comentario + ". Espero su confirmación" + persona.getEmail() + ".");

					mail.setVariables(variables);
					try {
						emailService.sendEmail(mail);
						enviado = true;
					} catch (Exception e) {
						return "errorCorre";
					}
				}
				if(enviado) {
					AsesoriaSolicitud asesoriaS = new AsesoriaSolicitud();
					asesoriaS.setIdGrupo(cveGrupo);
					asesoriaS.setComentarios(comentario);
					asesoriaS.setFecha(asesoria.getFecha());
					asesoriaS.setHora(asesoria.getHora());
					asesoriaS.setMaterias(materiasGuardar);
					asesoriaS.setRazon(razon);
					asesoriaService.guardarAsesoriaSolicitud(asesoriaS);
				}
				return "ok";
			}
			return "noMa";
		}
		return "noGru";
	}
	
}
