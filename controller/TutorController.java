package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.AsesoriaSolicitud;
import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.Canalizacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CausaBaja;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.FocosAtencion;
import edu.mx.utdelacosta.model.Fortaleza;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Motivo;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.ProgramacionTutoria;
import edu.mx.utdelacosta.model.RespuestaEvaluacionInicial;
import edu.mx.utdelacosta.model.Servicio;
import edu.mx.utdelacosta.model.TemaGrupal;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoInfoDTO;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;
import edu.mx.utdelacosta.model.dto.GrupoDTO;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;
import edu.mx.utdelacosta.model.dto.PreguntaDTO;
import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;
import edu.mx.utdelacosta.model.dto.TutoriasProgramadasDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioDTO;
import edu.mx.utdelacosta.model.dtoreport.GruposEvaluacionTutorDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorMateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorParcialDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsesoriaService;
import edu.mx.utdelacosta.service.IBajaService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICanalizacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICausaBajaService;
import edu.mx.utdelacosta.service.IComentarioEvaluacionTutorService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IDiaService;
import edu.mx.utdelacosta.service.IEvaluacionesService;
import edu.mx.utdelacosta.service.IFocosAtencionService;
import edu.mx.utdelacosta.service.IFortalezaService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IMotivoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPreguntaService;
import edu.mx.utdelacosta.service.IProgramacionTutoriaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionInicialService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionTutorService;
import edu.mx.utdelacosta.service.IServicioService;
import edu.mx.utdelacosta.service.ITemaGrupalService;
import edu.mx.utdelacosta.service.ITutoriaIndividualService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Director')")
@RequestMapping("/tutorias")
public class TutorController {

	@Value("${spring.mail.username}")
	private String correo;

	@Autowired
	private IGrupoService grupoService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private ICalificacionMateriaService calMatService;

	@Autowired
	private IPagoGeneralService pagoGenService;

	@Autowired
	private IDiaService serviceDia;

	@Autowired
	private IHorarioService serviceHorario;

	@Autowired
	private IEvaluacionesService evaluacionService;

	@Autowired
	private IRespuestaEvaluacionTutorService resEvaTutorService;

	@Autowired
	private IComentarioEvaluacionTutorService comEvaTurtorService;

	@Autowired
	private IPreguntaService preguntasService;

	@Autowired
	private IPeriodosService periodoService;

	@Autowired
	private ITutoriaIndividualService tutoriaIndiService;

	@Autowired
	private IFortalezaService fortalezaService;

	@Autowired
	private IFocosAtencionService focoAtenService;

	@Autowired
	private ITemaGrupalService temaGrupalService;

	@Autowired
	private IProgramacionTutoriaService proTutoriaService;

	@Autowired
	private ICargaHorariaService cargaHorariaService;

	@Autowired
	private ICanalizacionService canalizacionService;

	@Autowired
	private IBajaService bajaService;

	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";

	@Autowired
	private ICalificacionMateriaService calificacionMateriaService;

	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;

	@Autowired
	private ICalificacionCorteService calificacionCorteService;

	@Autowired
	private IAlumnoGrupoService alumnoGrupoService;

	@Autowired
	private IRespuestaEvaluacionInicialService resEvaIniService;
	
	@Autowired
	private IMotivoService motivoService;

	@Autowired
	private IServicioService servicioService;

	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;

	@Autowired
	private ICausaBajaService causaBajaService;
	
	@Autowired
	private IAsesoriaService asesoriaService;

	@GetMapping("/cargar-alumno/{dato}")
	public String cargarAlumnos(@PathVariable(name = "dato", required = false) String dato, Model model,
			HttpSession session, Authentication auth) {
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		String rol = auth.getAuthorities().toString();
		rol = rol.replace("[", "").replace("]", "");
		// comparamos el rol
		List<AlumnoInfoDTO> alumnos = new ArrayList<>();
		if (rol.equals("Profesor")) {
			if (dato != null) {
				alumnos = alumnoService.buscarPorProfesorPeriodoYNombreOMatricula(cvePersona,
						usuario.getPreferencias().getIdPeriodo(), dato);
			} else {
				alumnos = alumnoService.buscarPorProfesorYPeriodo(cvePersona, usuario.getPreferencias().getIdPeriodo());
			}
		} else if (rol.equals("Director")) {
			if (dato != null) {
				alumnos = alumnoService.buscarPorPersonaCarreraYPeriodoYNombreOMatricula(cvePersona,
						usuario.getPreferencias().getIdPeriodo(), dato);
			} else {
				alumnos = alumnoService.buscarPorPersonaCarreraYPeriodoYActivos(cvePersona,
						usuario.getPreferencias().getIdPeriodo());
			}
		} else {
			if (dato != null) {
				alumnos = alumnoService.buscarTodosPorNombreOMatriculaYPeriodoYActivos(dato,
						usuario.getPreferencias().getIdPeriodo());
			} else {
				alumnos = alumnoService.buscarTodosPorPeriodo(usuario.getPreferencias().getIdPeriodo());
			}
		}

		model.addAttribute("alumnos", alumnos);

		return "fragments/control-alumnos :: res-alumnos";
	}

	@GetMapping("/tutorias")
	public String tutorias() {
		return "tutorias/gruposTutorados";
	}
	
	@GetMapping("/tutoriaIndividual")
	public String tutoriaIndividual(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		List<Alumno> alumnos = new ArrayList<>();
		List<TutoriaIndividual> tutorias = new ArrayList<>();
		if (cveGrupo != null) {
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			tutorias = tutoriaIndiService.buscarPorGrupo(new Grupo(cveGrupo));
		}

		List<Motivo> motivos = motivoService.buscarTodo();
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("motivos", motivos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("tutorias", tutorias);
		return "tutorias/tutoriaIndividual";
	}

	@GetMapping("/canalizacion/{cveTutoria}")
	public String canalizacion(@PathVariable(name = "cveTutoria", required = false) Integer cveTutoria, Model model,
			HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		List<Alumno> alumnos = new ArrayList<>();
		List<CargaHoraria> materias = new ArrayList<>();
		List<Servicio> servicios = servicioService.buscarTodos();
		if (cveGrupo != null) {
			materias = cargaHorariaService.buscarPorGrupo(new Grupo(cveGrupo));
			TutoriaIndividual tutoriaIndividual = tutoriaIndiService.buscarPorId(cveTutoria);
			model.addAttribute("tutoria", tutoriaIndividual);
		}

		model.addAttribute("alumnos", alumnos);
		model.addAttribute("materias", materias);
		model.addAttribute("servicios", servicios);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/canalizacion";
	}

	@GetMapping("/bajaAlumno")
	public String bajaAlumno(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		List<Alumno> alumnos = new ArrayList<>();
		List<Baja> bajas = new ArrayList<>();

		if (cveGrupo != null) {
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			bajas = bajaService.buscarPorGrupo(cveGrupo);
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<CausaBaja> causas = causaBajaService.buscarActivas();
		model.addAttribute("causas", causas);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("bajas", bajas);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("fechaHoy", dateFormat.format(new Date()));
		return "tutorias/bajaAlumno";
	}


	@GetMapping("/tutoriaGrupal")
	public String tutoriaGrupal(Model model, HttpSession session) {
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		List<Alumno> alumnos = new ArrayList<>();
		List<Fortaleza> fortalezas = null;
		List<FocosAtencion> focosAtencion = null;
		List<TemaGrupal> temasGrupales = null;

		if (cveGrupo != null) {
			Boolean GrupoEnPeriodo = grupoService.buscarPorGrupoYPeriodo(cveGrupo,
					usuario.getPreferencias().getIdPeriodo());
			if (GrupoEnPeriodo == true) {
				alumnos = alumnoService.buscarPorGrupo(cveGrupo);
				fortalezas = fortalezaService.buscarPorGrupo(new Grupo(cveGrupo));
				focosAtencion = focoAtenService.buscarPorGrupo(new Grupo(cveGrupo));
				temasGrupales = temaGrupalService.buscarPorGrupo(new Grupo(cveGrupo));
				model.addAttribute("cveGrupo", cveGrupo);
			}
		}

		model.addAttribute("alumnos", alumnos);
		model.addAttribute("fortalezas", fortalezas);
		model.addAttribute("focosAtencion", focosAtencion);
		model.addAttribute("temasGrupales", temasGrupales);
		model.addAttribute("grupos", grupos);

		return "tutorias/tutoriaGrupal";
	}

	@GetMapping("/programacionTutorias")
	public String programacionTutorias(Model model, HttpSession session) throws ParseException {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		Date fechaHoy = new Date();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		List<Alumno> alumnos = new ArrayList<>();

		if (cveGrupo != null) {
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(cveGrupo);

			List<Date> diasHabiles = periodoService.buscarDiasPorFechaInicioYFechafin(
					f.format(grupo.getPeriodo().getInicio()), f.format(grupo.getPeriodo().getFin()));

			List<TutoriasProgramadasDTO> tutorias = new ArrayList<>();
			List<ProgramacionTutoria> programacionTutorias = proTutoriaService.buscarPorGrupo(grupo);
			// valida si ya existen regitros de fechas de tutorias para los alumnos de este
			// grupo
			if (programacionTutorias.size() == 0) {
				// --------------------
				Integer n = 0;
				for (Alumno alumno : alumnos) {
					++n;
					Integer intervaloDias = Math.round(diasHabiles.size() / alumnos.size());
					if (intervaloDias == 0) {
						intervaloDias = 1;
					}

					// Donde cortaremos el ciclo
					Integer breakNumero = intervaloDias * n;
					// Si el break es mayor al numero de días, le asignamos el último día
					if (breakNumero > diasHabiles.size()) {
						breakNumero = diasHabiles.size();
					}

					int contador = 0;
					String fechaProgramada = "";
					for (Date dia : diasHabiles) {
						contador++;
						if (contador == breakNumero) {
							fechaProgramada = f.format(dia);
							break;
						}
					}

					List<ProgramacionTutoria> pTutorias = proTutoriaService.buscarPorAlumnoYGrupo(alumno, grupo);
					if (pTutorias.isEmpty()) {

					} else {
						fechaProgramada = f.format(pTutorias.get(0).getFecha());
					}

					List<TutoriaIndividual> tutoriasInd = tutoriaIndiService.buscarPorAlumnoYGrupo(alumno, grupo);

					TutoriasProgramadasDTO tutoria = new TutoriasProgramadasDTO();
					tutoria.setIdAlumno(alumno.getId());
					tutoria.setNombreAlumno(alumno.getPersona().getNombreCompleto());
					tutoria.setMatricula(alumno.getMatricula());
					tutoria.setFechaProgramada(fechaProgramada);
					tutoria.setTutoriaIndividuales(tutoriasInd);
					tutoria.setEstadoAlumno(alumno.getEstatusGeneral());
					tutorias.add(tutoria);
					// --------------------
					// guarda una tutoria programada para cada alumno ya que no encontro registros
					ProgramacionTutoria pT = new ProgramacionTutoria();
					pT.setGrupo(new Grupo(cveGrupo));
					pT.setAlumno(new Alumno(alumno.getId()));
					pT.setFechaAlta(fechaHoy);
					pT.setFecha(f.parse(fechaProgramada));
					proTutoriaService.guardar(pT);

				}
			} else {
				// rellena el dto que envia los datos a la vista con los regitros de bd
				for (ProgramacionTutoria pTutoria : programacionTutorias) {
					List<TutoriaIndividual> tutoriasInd = tutoriaIndiService.buscarPorAlumnoYGrupo(pTutoria.getAlumno(),
							grupo);
					TutoriasProgramadasDTO tutoria = new TutoriasProgramadasDTO();
					tutoria.setIdAlumno(pTutoria.getAlumno().getId());
					tutoria.setNombreAlumno(pTutoria.getAlumno().getPersona().getNombreCompleto());
					tutoria.setMatricula(pTutoria.getAlumno().getMatricula());
					tutoria.setFechaProgramada(f.format(pTutoria.getFecha()));
					tutoria.setTutoriaIndividuales(tutoriasInd);
					tutoria.setEstadoAlumno(pTutoria.getAlumno().getEstatusGeneral());
					tutorias.add(tutoria);
				}
			}
			model.addAttribute("tutorias", tutorias);
		}

		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/programacionTutorias";
	}

	@GetMapping("/cargar-tutoria/{idTutoria}")
	public String cargarAlumnos(@PathVariable(name = "idTutoria", required = false) String idAlumno, Model model) {
		if (idAlumno != null) {
			TutoriaIndividual tutoria = tutoriaIndiService.buscarPorId(Integer.parseInt(idAlumno));
			model.addAttribute("tutoria", tutoria);
		}
		return "fragments/tutorias :: cargar-tutoria";
	}

	@GetMapping("/reporte-evaluacion-tutor")
	public String encuestaEvaluacionTutor(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("recveGrupoTutor");
		int aluEncuestados = 0;
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		Evaluacion evaluacion = evaluacionService.buscar(4);

		if (cveGrupo != null) {
			// Validación por si el reporte es de todos los grupos tutorados o de solo uno
			// en particular
			List<Grupo> Rgrupos = new ArrayList<>();
			if (cveGrupo == 0) {
				Rgrupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
						new Periodo(usuario.getPreferencias().getIdPeriodo()));
			} else {
				Grupo Rgrupo = grupoService.buscarPorId(cveGrupo);
				Rgrupos.add(Rgrupo);
			}

			List<GruposEvaluacionTutorDTO> gruposEvaDto = new ArrayList<>();
			for (Grupo grupo : Rgrupos) {
				GruposEvaluacionTutorDTO grupoEvaDto = new GruposEvaluacionTutorDTO();
				aluEncuestados = resEvaTutorService.contarEncuestadosPorGrupo(4, grupo.getId());
				List<ComentarioDTO> comentarios = comEvaTurtorService.buscarComentariosPorGrupo(4, grupo.getId());
				// Se calculan los promedios de cada una de las preguntas para cada uno de los
				// grupos en lo que el profesor imparte dicha materia en determinada carrera
				List<PreguntaDTO> preguntasDto = new ArrayList<>();
				double promedioPre = 0.0;
				for (Pregunta pre : evaluacion.getPreguntas()) {
					PromedioPreguntaDTO promedioPreguntaDTOs = preguntasService.ObtenerPromedioEvaTuPorPregunta(4,
							pre.getId(), grupo.getId());
					promedioPre = promedioPreguntaDTOs.getPromedio() + promedioPre;

					List<GrupoDTO> gruposDTO = new ArrayList<>();
					GrupoDTO grupoDto = new GrupoDTO();
					grupoDto.setIdGrupo(grupo.getId());
					grupoDto.setNombreGrupo(grupo.getNombre());
					grupoDto.setPromedioPre(promedioPreguntaDTOs.getPromedio());
					gruposDTO.add(grupoDto);

					PreguntaDTO preguntaDto = new PreguntaDTO();
					preguntaDto.setIdPregunta(pre.getId());
					preguntaDto.setDescripcion(pre.getDescripcion());
					preguntaDto.setConsecutivo(pre.getConsecutivo());
					preguntaDto.setGruposDTO(gruposDTO);
					preguntasDto.add(preguntaDto);
				}

				List<GrupoDTO> gruposDTOs = new ArrayList<>();
				Double proGe = 0.0;
				for (PreguntaDTO preDTO : preguntasDto) {
					proGe = preDTO.getGruposDTO().get(0).getPromedioPre() + proGe;
				}

				proGe = proGe / evaluacion.getPreguntas().size();
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(0);
				grupoDto.setNombreGrupo("0");
				grupoDto.setPromedioPre(proGe);
				gruposDTOs.add(grupoDto);

				PreguntaDTO preguntaDto = new PreguntaDTO();
				preguntaDto.setIdPregunta(evaluacion.getPreguntas().size() + 1);
				preguntaDto.setDescripcion("Promedio");
				preguntaDto.setConsecutivo(evaluacion.getPreguntas().size() + 1);
				preguntaDto.setGruposDTO(gruposDTOs);
				preguntasDto.add(preguntaDto);

				grupoEvaDto.setPromedioGeneral(proGe);
				grupoEvaDto.setPreguntas(preguntasDto);
				grupoEvaDto.setGrupo(grupo);
				grupoEvaDto.setComentarios(comentarios);
				grupoEvaDto.setDirectorCarrera(grupo.getCarrera().getDirectorCarrera());
				grupoEvaDto.setAlumnosEncuestados(aluEncuestados);
				gruposEvaDto.add(grupoEvaDto);

			}
			model.addAttribute("gruposEvaDto", gruposEvaDto);
		}

		model.addAttribute("grupos", grupos);
		model.addAttribute("periodo", periodo);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteEvaluacionTutor";
	}

	@GetMapping("/reporte-tutorias-grupales")
	public String reporteTutoriasGrupales(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<TemaGrupal> temasGrupales = new ArrayList<>();
		Integer cveGrupo = (Integer) session.getAttribute("rtg-cveGrupo");

		if (cveGrupo != null) {
			if ((String) session.getAttribute("rtg-fechaInicio") != null) {
				Date fechaInicio = java.sql.Date.valueOf((String) session.getAttribute("rtg-fechaInicio"));
				if ((String) session.getAttribute("rtg-fechaFin") != null) {
					Date fechaFin = java.sql.Date.valueOf((String) session.getAttribute("rtg-fechaFin"));
					temasGrupales = temaGrupalService.buscarEntreFechasPorGrupo(cveGrupo, fechaInicio, fechaFin);
					model.addAttribute("fechaFin", fechaFin);
				}
				model.addAttribute("fechaInicio", fechaInicio);
			}
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("grupos", grupos);
		model.addAttribute("temas", temasGrupales);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteTutoriaGrupal";
	}

	@GetMapping("/reporte-tutoria-individual")
	public String reporteTutoriaIndividual(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		int h = 0;
		int m = 0;
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<TutoriaIndividual> tutorias = new ArrayList<>();
		Integer cveGrupo = (Integer) session.getAttribute("rtg-cveGrupo");
		Integer cveAlumno = (Integer) session.getAttribute("rti-cveAlumno");
		List<Alumno> alumnos = new ArrayList<>();
		Boolean allAlumnos = false;
		if (cveGrupo != null) {
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			if ((String) session.getAttribute("rti-fechaInicio") != null) {
				Date fechaInicio = java.sql.Date.valueOf((String) session.getAttribute("rti-fechaInicio"));
				if ((String) session.getAttribute("rti-fechaFin") != null) {
					Date fechaFin = java.sql.Date.valueOf((String) session.getAttribute("rti-fechaFin"));
					if (cveAlumno == null || cveAlumno == 0) {
						tutorias = tutoriaIndiService.buscarEntreFechasPorGrupo(cveGrupo, fechaInicio, fechaFin);
						allAlumnos = true;
					} else {
						tutorias = tutoriaIndiService.buscarEntreFechasPorGrupoYAlumno(cveGrupo, cveAlumno, fechaInicio,
								fechaFin);
					}
					for (TutoriaIndividual tutoria : tutorias) {
						if (tutoria.getAlumno().getPersona().getSexo().equals("M")) {
							++m;
						} else {
							++h;
						}
					}
					model.addAttribute("fechaFin", fechaFin);
				}
				model.addAttribute("fechaInicio", fechaInicio);
			}
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("mujeres", m);
		model.addAttribute("hombre", h);
		model.addAttribute("allAlumnos", allAlumnos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("cveAlumno", cveAlumno);
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("tutorias", tutorias);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "tutorias/reporteTutoriaIndividual";
	}

	@GetMapping("/reporte-informacion-estudiante/{cveAlumno}")
	public String reporteInformacionEstudiante(@PathVariable("cveAlumno") Integer cveAlumno, Model model,
			HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Date fechaActual = new Date();
		if (cveAlumno != null) {
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			// Grupo ultimoGrupo = grupoService.buscarUltimoDeAlumno(alumno.getId());

			AlumnoGrupo AlGrupo = alumnoGrupoService.buscarPorIdAlumnoYIdPeriodo(cveAlumno,
					usuario.getPreferencias().getIdPeriodo());
			List<MateriaPromedioDTO> promediosMate = new ArrayList<>();

			if (AlGrupo != null) {
				promediosMate = calMatService.buscarPorGrupoAlumno(AlGrupo.getGrupo().getId(), cveAlumno);
				model.addAttribute("grupo", AlGrupo.getGrupo());
				model.addAttribute("calificaciones", promediosMate);
			}

			List<PagoGeneral> pagos = pagoGenService.buscarPorAlumno(cveAlumno, 1);
			List<PagoGeneral> adeudos = pagoGenService.buscarPorAlumno(cveAlumno, 0);
			List<TutoriaIndividual> tutorias = tutoriaIndiService.buscarPorAlumno(alumno);
			List<Canalizacion> canalizaciones = canalizacionService.buscarPorAlumno(alumno);

			model.addAttribute("alumno", alumno);
			model.addAttribute("pagos", pagos);
			model.addAttribute("adeudos", adeudos);
			model.addAttribute("tutorias", tutorias);
			model.addAttribute("canalizaciones", canalizaciones);
		}
		model.addAttribute("fechaActual", fechaActual);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteInformacionEstudiante";
	}

	@GetMapping("/reporte-datos-contacto")
	public String reporteDatosContacto(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("rdc-cveGrupo");
		List<Alumno> alumnos = new ArrayList<>();

		if (cveGrupo != null) {
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteDatosContacto";
	}

	@GetMapping("/reporte-horario-clases")
	public String reporteHorarioClases(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("rhr-cveGrupo");

		if (cveGrupo != null) {
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			// se extrae la lista de dias guardados en la BD
			List<Dia> dias = serviceDia.buscarDias();
			model.addAttribute("dias", dias);

			// formato de hora
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

			// Se extrae una lista de las horas unicas de la lista de horas asociadas al
			// grupo
			List<Horario> horas = serviceHorario.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
			model.addAttribute("horas", horas);

			// se crea una lista vacía para colocarle los datos de las horas de clase
			List<HorarioDTO> horasDto = new ArrayList<>();

			// crea el horario con las horas vinculados al grupo
			for (Horario hora : horas) {

				for (Dia dia : dias) {
					String horaI = dateFormat.format(hora.getHoraInicio());
					// se genera el horario al comparar la lista de horas únicas y la lista de días
					List<Horario> horario = serviceHorario.buscarPorHoraInicioDiaYGrupo(horaI, dia.getId(), cveGrupo);
					for (Horario hr : horario) {
						HorarioDTO horaDto = new HorarioDTO();
						if (horario == null) {
							horaDto.setHoraInicio("");
							horaDto.setHoraFin("");
							horaDto.setDia("");
							horaDto.setProfesor("");
							horaDto.setMateria("");
							horaDto.setAbreviaturaMateria("");
							horasDto.add(horaDto);
						} else {
							// se formatea la hora de "Date" a "String"
							String horaInicio = dateFormat.format(hr.getHoraInicio());
							String horaFin = dateFormat.format(hr.getHoraFin());

							horaDto.setHoraInicio(horaInicio);
							horaDto.setHoraFin(horaFin);
							horaDto.setDia(dia.getDia());
							horaDto.setProfesor(hr.getCargaHoraria().getProfesor().getNombreCompleto());
							horaDto.setMateria(hr.getCargaHoraria().getMateria().getNombre());
							horaDto.setAbreviaturaMateria(hr.getCargaHoraria().getMateria().getAbreviatura());
							horasDto.add(horaDto);
						}
					}
				}

			}
			model.addAttribute("horasDto", horasDto);
			model.addAttribute("nomGrupo", grupo.getNombre());
			model.addAttribute("turno", grupo.getTurno() != null ? grupo.getTurno().getNombre() : null);
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "reportes/reporteHorarioClases";
	}

	@GetMapping("/reporte-bajas")
	public String reporteBajas(Model model, HttpSession session) {
		// extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("rb-cveGrupo");
		List<Baja> bajas = new ArrayList<>();

		if (cveGrupo != null) {
			Boolean GrupoEnPeriodo = grupoService.buscarPorGrupoYPeriodo(cveGrupo,
					usuario.getPreferencias().getIdPeriodo());
			if (GrupoEnPeriodo == true) {
				bajas = bajaService.buscarPorGrupo(cveGrupo);
			}
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("rol", 1);
		model.addAttribute("bajas", bajas);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "tutorias/reporteBajas";
	}

	@GetMapping("/reporte-canalizaciones")
	public String reporteCanalizaciones(Model model, HttpSession session) {
		// extrae el usuario a partir del usuario cargado en sesión
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<Alumno> alumnos = new ArrayList<>();
		Integer cveGrupo = (Integer) session.getAttribute("rc-cveGrupo");
		Integer cveAlumno = (Integer) session.getAttribute("rc-cveAlumno");
		Integer cveServicio = (Integer) session.getAttribute("rc-cveServicio");
		List<Canalizacion> canalizaciones = new ArrayList<>();
		Boolean allAlumnos = false;
		if (cveGrupo != null) {
			alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			if (cveAlumno == null || cveAlumno == 0) {
				if (cveServicio == null || cveServicio == 0) {
					canalizaciones = canalizacionService.buscarPorGrupoPeriodo(cveGrupo,
							usuario.getPreferencias().getIdPeriodo());
				} else {
					canalizaciones = canalizacionService.buscarPorGrupoPeriodoYServicio(cveGrupo,
							usuario.getPreferencias().getIdPeriodo(), cveServicio);
				}
				allAlumnos = true;
			} else {
				if (cveServicio == null || cveServicio == 0) {
					canalizaciones = canalizacionService.buscarPorGrupoPeriodoYAlumno(cveGrupo,
							usuario.getPreferencias().getIdPeriodo(), cveAlumno);
				} else {
					canalizaciones = canalizacionService.buscarPorGrupoPeriodoAlumnoYServicio(cveGrupo,
							usuario.getPreferencias().getIdPeriodo(), cveAlumno, cveServicio);
				}
			}
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("cveAlumno", cveAlumno);
		model.addAttribute("allAlumnos", allAlumnos);
		model.addAttribute("canalizaciones", canalizaciones);
		model.addAttribute("cveServicio", cveServicio);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "tutorias/reporteCanalizaciones";
	}

	@GetMapping("/reporte-cal-grupo")
	public String reporteCalificacionesPorGrupo(Model model, HttpSession session) {
		// extrae el usuario a partir del usuario cargado en sesión
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("rcg-cveGrupo");

		if (cveGrupo != null) {
			model.addAttribute("cveGrupo", cveGrupo);
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			// proceso para sacar las materias
			List<CargaHoraria> cargaHorarias = cargaHorariaService.buscarPorGrupoYPeriodoYCalificacionSi(cveGrupo,
					usuario.getPreferencias().getIdPeriodo());
			model.addAttribute("cargasHorarias", cargaHorarias);
			List<CorteEvaluativo> corte = corteEvaluativoService.buscarPorCarreraYPeriodo(grupo.getCarrera().getId(),
					usuario.getPreferencias().getIdPeriodo());
			List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			model.addAttribute("alumnos", alumnos);
			// lista para rellenar alumnos con calificaciones
			List<AlumnoPromedioDTO> alumnosCalificaciones = new ArrayList<AlumnoPromedioDTO>();
			if (alumnos.size() > 0) {
				for (Alumno alumno : alumnos) {
					// AlumnoCalificacionMateriaDTO alumnoDTO = new AlumnoCalificacionMateriaDTO();
					AlumnoPromedioDTO alumnoDTO = new AlumnoPromedioDTO();
					// se rellena el alumno y sus calificaciones
					alumnoDTO.setIdAlumno(alumno.getId());
					alumnoDTO.setNombre(alumno.getPersona().getNombreCompletoPorApellido());
					alumnoDTO.setMatricula(alumno.getMatricula());
					List<IndicadorMateriaDTO> indicadoresMaterias = new ArrayList<IndicadorMateriaDTO>();
					for (CargaHoraria ch : cargaHorarias) {
						IndicadorMateriaDTO im = new IndicadorMateriaDTO();
						im.setIdMateria(ch.getId());
						im.setNombre(ch.getMateria().getNombre());
						// variables para guardar promedios y remediales de la materia
						double promedioFinal = 0;
						double promedioparciales = 0;

						// se agrega el estatus de la materia
						im.setEstatus(calificacionMateriaService.buscarPorAlumnoYCarga(alumno.getId(), ch.getId()));
						List<IndicadorParcialDTO> indicaroresParcial = new ArrayList<IndicadorParcialDTO>();
						for (CorteEvaluativo c : corte) {
							IndicadorParcialDTO ip = new IndicadorParcialDTO();
							ip.setIdMateria(ch.getMateria().getId());
							ip.setParcial(c.getId());
							// para guardar si tiene remediales o extraordinarios
							ip.setRemediales(
									remedialAlumnoService.buscarCalificacionPorAlumnoYCargaHorariaYCorteEvaluativoYTipo(
											alumno.getId(), ch.getId(), c.getId(), 1));
							ip.setExtraordinarios(
									remedialAlumnoService.buscarCalificacionPorAlumnoYCargaHorariaYCorteEvaluativoYTipo(
											alumno.getId(), ch.getId(), c.getId(), 2));

							double calificacionTotal = 0f;
							if (im.getEstatus().equals("R") || im.getEstatus().equals("E")) {
								calificacionTotal = calificacionCorteService
										.buscarPromedioCortePorMecanismoIntrumentoYCarga(ch.getId(), c.getId(),
												alumno.getId());
							} else {
								// para sacar el promedio de la materia y sus indicadores
								calificacionTotal = calificacionCorteService
										.buscarPorAlumnoCargaHorariaYCorteEvaluativo(alumno.getId(), ch.getId(),
												c.getId());
								promedioparciales = promedioparciales + calificacionTotal;
							}
							// para guardar el promedio de los dos parciales
							ip.setPromedio(calificacionTotal);

							// se agrega el objeto a la lista de indicador parcial
							indicaroresParcial.add(ip);
						}
						// promedio final de la materia
						promedioFinal = Math.round(promedioparciales / corte.size());
						im.setPromedio(promedioFinal);
						// se egraga la lista de indicacires materia
						im.setParciales(indicaroresParcial);

						// se agrega el objeto de indficador materia
						indicadoresMaterias.add(im);
					}
					// se agrega la lista de indicadores materia al alumno
					alumnoDTO.setMaterias(indicadoresMaterias);
					// se cuentan los remediales por alumno y corte
					Boolean excedeRemediales = false;
					for(CorteEvaluativo c: corte) {
						Integer remediales = remedialAlumnoService.contarRemedialesPorAlumnoYCorteEvaluativoYTipoIntegerRemedial(alumno.getId(), c.getId(), 1);
						if(remediales > 4 ) {
							excedeRemediales = true;
							break;
						}
					}
					alumnoDTO.setExcedeRemediales(excedeRemediales);
					// se agregan los alumnos al arreglo
					alumnosCalificaciones.add(alumnoDTO);
				}
				model.addAttribute("carrera", grupoService.buscarPorId(cveGrupo).getCarrera().getNombre());
				model.addAttribute("alumnosCali", alumnosCalificaciones);
			}
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("grupos", grupos);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "tutorias/reporteCalificacionesPorGrupo";
	}

	@GetMapping("/reporte-entrevista-inicial")
	public String reporteEntrevistaInicial(Model model, HttpSession session) {
		// extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("rei-cveGrupo");
		Evaluacion evaluacion = null;

		if (cveGrupo != null) {
			List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			Integer cvePersonaAl = (Integer) session.getAttribute("rei-cvePersonaAl");
			// se valida si el cuatrimestre al que pertenece el grupo seleccionado

			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			if (grupo.getCuatrimestre().getConsecutivo() == 1) {
				evaluacion = evaluacionService.buscar(6);
			} else if (grupo.getCuatrimestre().getConsecutivo() == 7) {
				evaluacion = evaluacionService.buscar(7);
			} else {
				evaluacion = evaluacionService.buscar(5);
			}

			if (cvePersonaAl != null) {
				// Se inyectan las respuestas asociadas a cada pregunta de la evaluación
				// seleccionada
				// asi como su repuesta si es que la ahi.
				for (Pregunta pregunta : evaluacion.getPreguntas()) {

					List<OpcionRespuestaDTO> OpcionesRepuesta = resEvaIniService
							.buscarRespuestaPorPregunta(pregunta.getId(), cvePersonaAl, evaluacion.getId(), cveGrupo);
					pregunta.setOpcionesRespuesta(OpcionesRepuesta);

					// Se buscan las opciones de respuesta abierta
					if (pregunta.getAbierta() == true) {
						RespuestaEvaluacionInicial respuestaEI = resEvaIniService.buscarRespuestaAbiertaPorPregunta(
								evaluacion.getId(), pregunta.getId(), cvePersonaAl, cveGrupo);
						pregunta.setComentarioRespuesta(respuestaEI != null
								? respuestaEI.getRespuestaComentario().getComentario().getComentario()
								: null);
					}

				}
			}
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cvePersonaAl", cvePersonaAl);
			model.addAttribute("evaluacion", evaluacion);
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteEntrevistaInicial";
	}

	@GetMapping("/reporte-entrevista-inicial-primera-septima")
	public String reporteEntrevistaInicialPrimeraSeptima(Model model, HttpSession session) {
		// extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("rei2-cveGrupo");
		Evaluacion evaluacion = null;

		if (cveGrupo != null) {
			List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			Integer cvePersonaAl = (Integer) session.getAttribute("rei2-cvePersonaAl");
			if (cvePersonaAl != null) {
				Integer numEvaluacion = (Integer) session.getAttribute("rei2-numEvaluacion");

				if (numEvaluacion != null && numEvaluacion == 2) {
					evaluacion = evaluacionService.buscar(7);
				} else {
					evaluacion = evaluacionService.buscar(6);
				}
				// Se inyectan las respuestas asociadas a cada pregunta de la evaluación
				// seleccionada
				// asi como su repuesta si es que la ahi.
				for (Pregunta pregunta : evaluacion.getPreguntas()) {

					List<OpcionRespuestaDTO> OpcionesRepuesta = resEvaIniService
							.buscarRespuestaPorPregunta(pregunta.getId(), cvePersonaAl, evaluacion.getId(), cveGrupo);
					pregunta.setOpcionesRespuesta(OpcionesRepuesta);

					// Se buscan las opciones de respuesta abierta
					if (pregunta.getAbierta() == true) {
						RespuestaEvaluacionInicial respuestaEI = resEvaIniService.buscarRespuestaAbiertaPorPregunta(
								evaluacion.getId(), pregunta.getId(), cvePersonaAl, cveGrupo);
						pregunta.setComentarioRespuesta(respuestaEI != null
								? respuestaEI.getRespuestaComentario().getComentario().getComentario()
								: null);
					}

				}

				model.addAttribute("numEvaluacion", numEvaluacion);
			}
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cvePersonaAl", cvePersonaAl);
			model.addAttribute("evaluacion", evaluacion);
		}

		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reportePrimeraySeptimaEntrevistaInicial";
	}

	// consulta
	@GetMapping("/informacionEstudiante")
	public String informacionEstudiante() {
		return "tutorias/informacionEstudiante";
	}

	// consulta
	@GetMapping("/manual")
	public String manual() {
		return "tutorias/manual";
	}

	// consulta
	@GetMapping("/reportes")
	public String reportesTutoria() {
		return "tutorias/reportes";
	}

	@GetMapping("/asesoria-grupal")
	public String asesoriaGrupal(Model model, HttpSession session) {
		// extrae el usuario a partir del usuario cargado en sesión.
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<CargaHoraria> materias = new ArrayList<>();
		List<AsesoriaSolicitud> asesorias = new ArrayList<>();
		if (cveGrupo != null) {
			materias = cargaHorariaService.buscarPorGrupo(new Grupo(cveGrupo));
			asesorias = asesoriaService.buscarAsesoriasSolicitudPorGrupo(cveGrupo);
		}
		SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat objHora = new SimpleDateFormat("HH:mm");
		
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("grupos", grupos);
		model.addAttribute("materias", materias);
		model.addAttribute("asesorias", asesorias);
		model.addAttribute("fecha", objSDF.format(new Date()));
		model.addAttribute("hora", objHora.format(new Date()));
		return "tutorias/solicitarAsesoriaGrupal";
	}

}
