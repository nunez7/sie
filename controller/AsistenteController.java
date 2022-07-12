package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mx.utdelacosta.model.Actividad;
import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AreaConocimiento;
import edu.mx.utdelacosta.model.Asistencia;
import edu.mx.utdelacosta.model.CargaEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;
import edu.mx.utdelacosta.model.PlanEstudio;
import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;
import edu.mx.utdelacosta.model.dto.CorteEvaluativoDTO;
import edu.mx.utdelacosta.model.dto.DiaDTO;
import edu.mx.utdelacosta.model.dto.GrupoDTO;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.MateriaDTO;
import edu.mx.utdelacosta.model.dto.MesDTO;
import edu.mx.utdelacosta.model.dto.PreguntaDTO;
import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioDTO;
import edu.mx.utdelacosta.model.dtoreport.GruposEvaluacionTutorDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorMateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorParcialDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.IActividadService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAreaConocimientoService;
import edu.mx.utdelacosta.service.IAsistenciaService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICargaEvaluacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IComentarioEvaluacionTutorService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.ICuatrimestreService;
import edu.mx.utdelacosta.service.IDiaService;
import edu.mx.utdelacosta.service.IEvaluacionComentarioService;
import edu.mx.utdelacosta.service.IEvaluacionesService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPersonalService;
import edu.mx.utdelacosta.service.IPlanEstudioService;
import edu.mx.utdelacosta.service.IPreguntaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.IRespuestaCargaEvaluacionService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionTutorService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Rector') and hasRole('Informatica') and hasRole('Director') and hasRole('Asistente')")
@RequestMapping("/asistente")
public class AsistenteController {
	
	//Inyectando desde properties
	@Value("${siestapp.ruta.docs}")
	private String ruta;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private IAreaConocimientoService areaConocimientoService;
	
	@Autowired
	private IPersonalService personalService;
	
	@Autowired
	private IMateriasService materiasService;
	
	@Autowired 
	private IPlanEstudioService planEstudioService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@Autowired
	private ICargaHorariaService cargaHorariaService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IDiaService diaService;
	
	@Autowired
	private IActividadService actividadService;
	
	@Autowired
	private IHorarioService horarioService;
	
	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;
	
	@Autowired 
	private IAsistenciaService serviceAsistencia;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private ICalificacionMateriaService calificacionMateriaService;
	
	@Autowired
	private ICarrerasServices carrerasServices;
	
	@Autowired
	private ICuatrimestreService cuatrimestreService;
	
	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;
	
	@Autowired
	private ICalificacionCorteService calificacionCorteService;
	
	@Autowired
	private IPreguntaService servicePreguntas;
	
	@Autowired
	private IEvaluacionesService serviceEvaluacion;
	
	@Autowired
	private IRespuestaCargaEvaluacionService serviceResCarEva;
	
	@Autowired
	private IEvaluacionComentarioService serviceEvaCom;
	
	@Autowired
	private IPeriodosService servicePeriodo;
	
	@Autowired
	private IComentarioEvaluacionTutorService serviceComEvaTurtor;
	
	@Autowired
	private IRespuestaEvaluacionTutorService serviceResEvaTutor;
	
	@Autowired
	private ICargaEvaluacionService serviceCarEva;
	
	@Autowired
	private IPeriodosService periodoService;
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";
	
	@GetMapping("/carga")
	public String carga(Model model, HttpSession session) {
		//construimos el usuario
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), usuario.getPreferencias().getIdCarrera());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		int cveGrupo = 0;
		//lista de materias vacia para materias activas
		List<Materia> materias = new ArrayList<Materia>();
		//plan de estudio vacio 
		List<PlanEstudio> planes = new ArrayList<>();
		//cuatrimestre
		Cuatrimestre cuatrimestre = new Cuatrimestre();
		if(session.getAttribute("cveGrupo") != null) {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			if(grupo != null) {
				cuatrimestre = cuatrimestreService.buscarPorId(grupo.getCuatrimestre().getId());
				//aqui sacamos un arreglo de las materias del grupo par ala curricula
				materias = materiasService.buscarPorGrupoYCarrera(cveGrupo, usuario.getPreferencias().getIdCarrera());
				planes = planEstudioService.buscarPorCarrera(usuario.getPreferencias().getIdCarrera());
				//aqui sacamaos un arreglo de las materias por grupo pero que esten activas(para la carga) 
				List<Materia> materiasCH = materiasService.buscarPorGrupoYCarreraYActivos(cveGrupo, usuario.getPreferencias().getIdCarrera());
				//verificamos si hay carga horaria 
				List<CargaHoraria> cargaHoraria = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
				model.addAttribute("materiasCH", materiasCH);//para mandar las materias que tendran carga horaria
				model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
				model.addAttribute("cargasHorarias", cargaHoraria);
			}
		}
		else {
			model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
		}
		
		List<Personal> profesores = personalService.buscarProfesores();//para traer las areas de conocimiento
		List<AreaConocimiento> areas = areaConocimientoService.buscarAreasActivas();//para traer las areas de conocimiento
		model.addAttribute("profesores", profesores);
		model.addAttribute("materias", materias);//para mandar las materias al modal
		model.addAttribute("cuatrimestre", cuatrimestre);
		model.addAttribute("planes", planes); // se va hacer un inner join a cargahoraria y de ahí se va sacar el planes de estudio
		model.addAttribute("areas", areas);
		model.addAttribute("grupos",grupos); //retorna los grupos de nivel TSU
		model.addAttribute("carreras", carreras);
		return "asistente/carga_horaria";
	}
	
	@GetMapping("/calificaciones")
	public String calificaciones(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		int cveGrupo;
		try {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
		} catch (Exception e) {
			cveGrupo = 0;
		}

		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), usuario.getPreferencias().getIdCarrera());
		List<Materia> materias = materiasService.buscarPorCargaActivaEnGrupo(cveGrupo, usuario.getPreferencias().getIdCarrera());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		List<AlumnoDTO> alumnos = new ArrayList<AlumnoDTO>();
		if(!materias.isEmpty()) {
			AlumnoDTO alumno;
			List<MateriaDTO> materiasDT;
			for(Alumno al: alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(cveGrupo)) {
				alumno = new AlumnoDTO();
				alumno.setIdAlumno(al.getId());
				alumno.setNombre(al.getPersona().getNombreCompletoPorApellido());
				alumno.setMatricula(al.getMatricula());
				//Construimos las materias
				materiasDT = new ArrayList<MateriaDTO>();
				for(MateriaPromedioDTO cm : calificacionMateriaService.buscarPorGrupoAlumno(cveGrupo, al.getId())) {
					//Agregamos todos los promedios de las materias del alumno
					MateriaDTO mNew = new MateriaDTO();
					mNew.setPromedio(cm.getCalificacion());
					mNew.setEstatusPromedio(cm.getEstatus());
					materiasDT.add(mNew);
				}
				//Agregamos las materias al alumno
				alumno.setMaterias(materiasDT);
				alumnos.add(alumno);
			}
		}
		
		model.addAttribute("materias",
				materias);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("carreras", carrerasServices.buscarTodasMenosIngles());
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("carreras", carreras);
		return "asistente/calificaciones";
	}
	
	@GetMapping("/profesores")
	public String profesores(Model model, HttpSession session) {
		//se construye la persona y usuario por la sesion
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		//para traer la lista de profesores
		List<Personal> profesores = personalService.buscarProfesores();
		List<CargaHoraria> cargasHorarias = null;
		int cveProfesor = 0;
		if(session.getAttribute("cveProfesor") != null) {
			cveProfesor = (Integer) session.getAttribute("cveProfesor");
			Persona profesor = personaService.buscarPorId(cveProfesor);
			Periodo periodo =  new Periodo(usuario.getPreferencias().getIdPeriodo());
			cargasHorarias = cargaHorariaService.buscarPorProfesorYPeriodoYActivo(profesor, periodo, true);
			List<Actividad> actividades = actividadService.buscarTodas();
			model.addAttribute("cargasHorarias", cargasHorarias);
			model.addAttribute("cveProfesor", cveProfesor);
			model.addAttribute("actividades", actividades);
			/////////******** SE HACE EL PROCESO DE DIBUJAR EL HORARIO *******/////////////
			List<Dia> dias = diaService.buscarDias();
			//formato para horas
			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
			//Se extrae una lista de las horas que ahi asociadas a cada hora de calse con un disting por hora inicio y hora fin				
			List<Horario> horas = horarioService.buscarPorProfesorDistinctPorHoraInicio(cveProfesor, usuario.getPreferencias().getIdPeriodo());
			//se crea una lista vacia para colocarle los datos de las horas de calse				
			List<HorarioDTO> horasDto = new ArrayList<>();
			//crea el horario con las horarios vinculados al grupo				
			for (Horario hora : horas) {
				//se formatea la hora de "Date" a "String"					
				String horaInicio = dateFormat.format(hora.getHoraInicio());
				String horaFin = dateFormat.format(hora.getHoraFin());
				for (Dia dia : dias) {
					List<Horario> horarios = horarioService.buscarPorHoraInicioDiaYProfesor(horaInicio, dia.getId(), cveProfesor, usuario.getPreferencias().getIdPeriodo());						
					for(Horario horario : horarios) {
						HorarioDTO horaDto = new HorarioDTO();
						if (horario == null) {	
							horaDto.setIdHorario(0); //cambios en DTOHorario
							horaDto.setHoraInicio("");
							horaDto.setHoraFin("");
							horaDto.setDia(dia.getDia());
							horaDto.setProfesor("");
							horaDto.setMateria("");
							horaDto.setAbreviaturaMateria("");
							horaDto.setGrupo(""); //cambios en DTOHorario
							horaDto.setIdCargaHoraria(null); //cambios en DTOHorario
							horaDto.setIdActividad(null); //cambios en DTOHorario
							horaDto.setIdDia(null);
							horasDto.add(horaDto);
						}else{						
							horaDto.setIdHorario(horario.getId()); //cambios en DTOHorario
							horaDto.setHoraInicio(horaInicio);
							horaDto.setHoraFin(horaFin);
							horaDto.setDia(dia.getDia());
							horaDto.setProfesor(horario.getCargaHoraria().getProfesor().getNombreCompleto());
							horaDto.setMateria(horario.getCargaHoraria().getMateria().getNombre());
							horaDto.setAbreviaturaMateria(horario.getCargaHoraria().getMateria().getAbreviatura());
							horaDto.setGrupo(horario.getCargaHoraria().getGrupo().getNombre()); //cambios en DTOHorario
							horaDto.setIdCargaHoraria(horario.getCargaHoraria().getId());//cambios en DTOHorario
							horaDto.setIdActividad(horario.getActividad().getId());
							horaDto.setIdDia(dia.getId());
							horasDto.add(horaDto);
						}
					}
				}
				
			}
			model.addAttribute("dias", dias);
			model.addAttribute("horas", horas);
			model.addAttribute("horasDto", horasDto);
		} 
		else {
			model.addAttribute("cveProfesor", cveProfesor);
		}
		model.addAttribute("profesores", profesores);
		return "asistente/profesores";
	}
	
	@GetMapping("/asistencias")
	public String asistencias(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), usuario.getPreferencias().getIdCarrera());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		int cveGrupo = 0;
		int cveParcial = 0;
		int cveCarga = 0;
		List<CargaHoraria> cargasHorarias = null;
		if(session.getAttribute("cveGrupo") != null) {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
			cargasHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			model.addAttribute("cargasHorarias", cargasHorarias);
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("alumnos", alumnos);
			if(session.getAttribute("cveParcial") != null) {
				cveParcial = (Integer) session.getAttribute("cveParcial");
				model.addAttribute("cveParcial", cveParcial);
			}
			if(session.getAttribute("cveCarga") != null) {
				cveCarga = (Integer) session.getAttribute("cveCarga");
				model.addAttribute("cveCarga", cveCarga);
			}
			if(cveParcial > 0 && cveCarga > 0) {	
				CorteEvaluativo corte = corteEvaluativoService.buscarPorId(cveParcial);
				CorteEvaluativoDTO cortesEvaluativosDTO = new CorteEvaluativoDTO();
				cortesEvaluativosDTO.setIdCorte(corte.getId());
				cortesEvaluativosDTO.setFechaInicio(corte.getFechaInicio());
				cortesEvaluativosDTO.setFechaFin(corte.getFechaFin());
				cortesEvaluativosDTO.setConsecutivo(corte.getConsecutivo());			
				List<Date> meses = serviceAsistencia.mesesEntreFechaInicioYFechaFinAsc(corte.getFechaInicio(), corte.getFechaFin());
				List<Asistencia> asistencias = serviceAsistencia.buscarPorFechaInicioYFechaFinEIdCargaHorariaEIdGrupo(corte.getFechaInicio(), corte.getFechaFin(), cveCarga, cveGrupo);
				model.addAttribute("asistencias", asistencias);
				List<MesDTO> mesesDto = new ArrayList<>();
				for (Date mes : meses) {									
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdfM = new SimpleDateFormat("MMMM");
					// Fecha
					Calendar fecha = Calendar.getInstance();
					fecha.setTime(mes);

					// Obtenga el primer día del mes actual:
					fecha.add(Calendar.MONTH, 0);
					fecha.set(Calendar.DAY_OF_MONTH, 1); // Establecido en el 1, la fecha actual es el primer día del
															// mes
					String primerDia = sdf.format(fecha.getTime());

					// extraigo el primer dia del mes lo guardo como string y le doy formato de mes
					// para el dto
					String mesLetra = sdfM.format(fecha.getTime());

					// Obtener el último día del mes actual
					fecha.set(Calendar.DAY_OF_MONTH, fecha.getActualMaximum(Calendar.DAY_OF_MONTH));
					String ultimoDia = sdf.format(fecha.getTime());

					MesDTO mesDTO = new MesDTO();
					mesDTO.setMes(mesLetra);
					mesesDto.add(mesDTO);
					cortesEvaluativosDTO.setMeses(mesesDto);
					 List<Date> dias = serviceAsistencia.diasEntreFechaInicioYFechaFin(primerDia, ultimoDia);							 
					 List<DiaDTO> diasDto = new ArrayList<>();
                    for (Date dia : dias) {
                        DiaDTO diaDto = new DiaDTO();
                        diaDto.setDia(dia);
                        diasDto.add(diaDto);
                        mesDTO.setDias(diasDto);                          
                    } 
				}	
				model.addAttribute("corte", cortesEvaluativosDTO);
			}
		}
		else {
			model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
		}
		// lista de cortesEvalutivos
		List<CorteEvaluativo> cortesEvaluativos = corteEvaluativoService.buscarPorCarreraYPeriodo(usuario.getPreferencias().getIdCarrera(), usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cortes", cortesEvaluativos);
		model.addAttribute("carreras", carreras);
		model.addAttribute("grupos",grupos); //retorna los grupos de nivel TSU
		return "asistente/asistencias";
	}
	
	@GetMapping("/grupos")
	public String grupos(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), usuario.getPreferencias().getIdCarrera());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		int cveGrupo = 0;
		List<CargaHoraria> cargasHorarias = null;
		if(session.getAttribute("cveGrupo") != null) {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
			//cargas horarias por profesor
			cargasHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			List<Actividad> actividades = actividadService.buscarTodas();
			List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			List<Personal> profesores = personalService.buscarProfesores();
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("actividades", actividades);
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cargasHorarias", cargasHorarias);
			model.addAttribute("profesores", profesores);
			/////****** proceso de creacion de horario
			List<Dia> dias = diaService.buscarDias();
			model.addAttribute("dias", dias);				
			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss"); 
			//Se extrae una lista de las horas que ahi asociadas a cada hora de calse con un disting por hora inicio y hora fin				
			List<Horario> horas = horarioService.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
			model.addAttribute("horas", horas);
			//se crea una lista vacia para colocarle los datos de las horas de calse				
			List<HorarioDTO> horasDto = new ArrayList<>();
			//crea el horario con las horarios vinculados al grupo				
			for (Horario hora : horas) {
				//se formatea la hora de "Date" a "String"					
				String horaInicio = dateFormat.format(hora.getHoraInicio());
				String horaFin = dateFormat.format(hora.getHoraFin()); 
				for (Dia dia : dias) {
					List<Horario> horarios = horarioService.buscarPorHoraInicioDiaYGrupo(horaInicio, dia.getId(), cveGrupo);			
					for(Horario horario : horarios) {
						HorarioDTO horaDto = new HorarioDTO();
						if (horario == null) {	
							horaDto.setIdHorario(0); //cambios en DTOHorario
							horaDto.setHoraInicio("");
							horaDto.setHoraFin("");
							horaDto.setDia(dia.getDia());
							horaDto.setProfesor("");
							horaDto.setMateria("");
							horaDto.setAbreviaturaMateria("");
							horaDto.setGrupo(""); //cambios en DTOHorario
							horaDto.setIdCargaHoraria(null); //cambios en DTOHorario
							horaDto.setIdActividad(null); //cambios en DTOHorario
							horaDto.setIdDia(null);
							horasDto.add(horaDto);
						}else{						
							horaDto.setIdHorario(horario.getId()); //cambios en DTOHorario
							horaDto.setHoraInicio(horaInicio);
							horaDto.setHoraFin(horaFin);
							horaDto.setDia(dia.getDia());
							horaDto.setProfesor(horario.getCargaHoraria().getProfesor().getNombreCompleto());
							horaDto.setMateria(horario.getCargaHoraria().getMateria().getNombre());
							horaDto.setAbreviaturaMateria(horario.getCargaHoraria().getMateria().getAbreviatura());
							horaDto.setGrupo(horario.getCargaHoraria().getGrupo().getNombre()); //cambios en DTOHorario
							horaDto.setIdCargaHoraria(horario.getCargaHoraria().getId());//cambios en DTOHorario
							horaDto.setIdActividad(horario.getActividad().getId());
							horaDto.setIdDia(dia.getId());
							horasDto.add(horaDto);
						}
					}
				}
			}		
			model.addAttribute("horasDto", horasDto);
		}
		else {
			model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
		}
		model.addAttribute("grupos",grupos); //retorna los grupos de nivel TSU
		model.addAttribute("carreras", carreras);
		return "asistente/grupos";
	}
	
	@GetMapping("/reportes")
	public String reportes() {
		return "asistente/reportes";
	}
	
	@GetMapping("/manual")
	public String manual() {
		return "asistente/manual";
	}
	
	@GetMapping("/datos-personales")
	public String reporteDatosPersonales(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			List<Alumno> alumnos = alumnoService.buscarPorCarreraYActivo(cveCarrera);
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cveCarrera", cveCarrera);
		}
		model.addAttribute("carreras", carrerasServices.buscarCarrerasPorIdPersona(persona.getId()));
		return "asistente/reporteDatosPersonales";
	}
	
	@GetMapping("/reporte-horario")
	public String reporteHorario(Model model, HttpSession session){
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		int cveCarrera = 0;
		if(session.getAttribute("cveCarrera") != null) {
			cveCarrera = (Integer) session.getAttribute("cveCarrera");
			model.addAttribute("cveCarrera", cveCarrera);
			int cveGrupo = 0;
			if(session.getAttribute("cveGrupo") != null ) {
				cveGrupo = (Integer) session.getAttribute("cveGrupo");
				if(cveGrupo > 0) {
					model.addAttribute("cveGrupo", cveGrupo);
					Grupo grupo = grupoService.buscarPorId(cveGrupo);
					model.addAttribute("tutor", grupo.getProfesor().getNombreCompletoConNivelEstudio());
					Carrera carrera = carrerasServices.buscarPorId(cveCarrera);
					model.addAttribute("director", carrera.getDirectorCarrera());
				}
				/////****** proceso de creacion de horario
				List<Dia> dias = diaService.buscarDias();
				model.addAttribute("dias", dias);				
				DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss"); 
				//Se extrae una lista de las horas que ahi asociadas a cada hora de calse con un disting por hora inicio y hora fin				
				List<Horario> horas = horarioService.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
				model.addAttribute("horas", horas);
				//se crea una lista vacia para colocarle los datos de las horas de calse				
				List<HorarioDTO> horasDto = new ArrayList<>();
				//crea el horario con las horarios vinculados al grupo				
				for (Horario hora : horas) {
					//se formatea la hora de "Date" a "String"					
					String horaInicio = dateFormat.format(hora.getHoraInicio());
					String horaFin = dateFormat.format(hora.getHoraFin()); 
					for (Dia dia : dias) {
						List<Horario> horarios = horarioService.buscarPorHoraInicioDiaYGrupo(horaInicio, dia.getId(), cveGrupo);			
						for(Horario horario : horarios) {
							HorarioDTO horaDto = new HorarioDTO();
							if (horario == null) {	
								horaDto.setIdHorario(0); //cambios en DTOHorario
								horaDto.setHoraInicio("");
								horaDto.setHoraFin("");
								horaDto.setDia(dia.getDia());
								horaDto.setProfesor("");
								horaDto.setMateria("");
								horaDto.setAbreviaturaMateria("");
								horaDto.setGrupo(""); //cambios en DTOHorario
								horaDto.setIdCargaHoraria(null); //cambios en DTOHorario
								horaDto.setIdActividad(null); //cambios en DTOHorario
								horaDto.setIdDia(null);
								horasDto.add(horaDto);
							}else{						
								horaDto.setIdHorario(horario.getId()); //cambios en DTOHorario
								horaDto.setHoraInicio(horaInicio);
								horaDto.setHoraFin(horaFin);
								horaDto.setDia(dia.getDia());
								horaDto.setProfesor(horario.getCargaHoraria().getProfesor().getNombreCompleto());
								horaDto.setMateria(horario.getCargaHoraria().getMateria().getNombre());
								horaDto.setAbreviaturaMateria(horario.getCargaHoraria().getMateria().getAbreviatura());
								horaDto.setGrupo(horario.getCargaHoraria().getGrupo().getNombre()); //cambios en DTOHorario
								horaDto.setIdCargaHoraria(horario.getCargaHoraria().getId());//cambios en DTOHorario
								horaDto.setIdActividad(horario.getActividad().getId());
								horaDto.setIdDia(dia.getId());
								horasDto.add(horaDto);
							}
						}
					}
				}		
				model.addAttribute("horasDto", horasDto);
			}
			else {
				model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
			}
			model.addAttribute("grupos",grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera)); //retorna los grupos de nivel TSU
		}
		model.addAttribute("carreras", carreras);
		return "asistente/reporteHorario";
	}
	
	@GetMapping("/reporte-indicadores")
	public String reporteIndicadores(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera);
			model.addAttribute("grupos",grupos); 
			model.addAttribute("cveCarrera", cveCarrera);
			if (session.getAttribute("cveGrupo") != null) {
				  int cveGrupo = (Integer) session.getAttribute("cveGrupo");
				model.addAttribute("cveGrupo", cveGrupo);
				model.addAttribute("grupoActual", grupoService.buscarPorId(cveGrupo));
				//proceso para sacar las materias
				List<CargaHoraria> cargaHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
				model.addAttribute("ch", cargaHorarias);
				List<CorteEvaluativo> corte = corteEvaluativoService.buscarPorCarreraYPeriodo(usuario.getPreferencias().getIdCarrera(), usuario.getPreferencias().getIdPeriodo());
				List<IndicadorMateriaDTO> indicadoresMaterias = new ArrayList<IndicadorMateriaDTO>();
				int alumnos = alumnoService.buscarPorGrupo(cveGrupo).size();
				model.addAttribute("alumnos", alumnos);
				if (alumnos > 0) {
					if (corte != null) {
						for (CargaHoraria ch : cargaHorarias) {
							IndicadorMateriaDTO im = new IndicadorMateriaDTO();
							im.setIdMateria(ch.getId());
							im.setNombre(ch.getMateria().getNombre());
							// variables para guardar promedios y remediales de la materia
							int tRemediales = 0;
							int tExtras = 0;
							double pRemes = 0;
							double pExtras = 0;
							double promedioFinal = 0;
							List<IndicadorParcialDTO> indicaroresParcial = new ArrayList<IndicadorParcialDTO>();
							for (CorteEvaluativo c : corte) {
								IndicadorParcialDTO ip = new IndicadorParcialDTO();
								// para sacar el promedio de la materia y sus indicadores
								double calificacionTotal = calificacionCorteService.buscarPorCargaHorariaIdCorteEvaluativoIdGrupo(ch.getId(), c.getId());
								double promedio = calificacionTotal / alumnos;
								int remediales = remedialAlumnoService.contarRemedialesAlumno(ch.getId(), 1);
								double pr = remediales / alumnos;
								int extraordernarios = remedialAlumnoService.contarRemedialesAlumno(ch.getId(), 2);
								double pe = extraordernarios / alumnos;
								// para guaredar lor promedios finales de extras y remediales
								tRemediales = tRemediales + remediales;
								tExtras = tExtras + extraordernarios;
								// para guardar los porcentajes finales de extras y remediales
								pRemes = pRemes + pr;
								pExtras = pExtras + pe;
								// para guardar el promedio de los dos parciales
								promedioFinal = promedioFinal + promedio;
								// se guardan los objetos
								ip.setIdMateria(ch.getMateria().getId());
								ip.setPromedio(promedio);
								ip.setRemediales(remediales);
								ip.setpRemediales(pr);
								ip.setExtraordinarios(extraordernarios);
								ip.setpExtraordinarios(pe);
								ip.setParcial(c.getConsecutivo());
								// se agrega el objeto a la lista de indicador parcial
								indicaroresParcial.add(ip);
							}
							// se agregan los promedios y procentajes generales
							im.setPromedio(Math.round(promedioFinal / corte.size()));
							im.setRemediales(tRemediales / corte.size());
							im.setpRemediales(pRemes / corte.size());
							im.setExtraordinarios(tExtras / corte.size());
							im.setpExtraordinarios(pExtras / corte.size());
							im.setParciales(indicaroresParcial);
							// se agrega el objeto de indficador materia
							indicadoresMaterias.add(im);
						}
						// se retorna la lista de indicadores materia
						model.addAttribute("materias", indicadoresMaterias);
					}
				}
			}
		}
		model.addAttribute("carreras", carreras);
		return "asistente/reporteIndicadores";
	}
	
	@GetMapping("/reporte-calificaciones-generales")
	public String calificacionesGenerales(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera);
			model.addAttribute("grupos",grupos); 
			model.addAttribute("cveCarrera", cveCarrera);
			if (session.getAttribute("cveGrupo") != null) {
				  int cveGrupo = (Integer) session.getAttribute("cveGrupo");
				model.addAttribute("cveGrupo", cveGrupo);
				model.addAttribute("grupoActual", grupoService.buscarPorId(cveGrupo));
				//proceso para sacar las materias
				List<CargaHoraria> cargaHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
				model.addAttribute("cargasHorarias", cargaHorarias);
				List<CorteEvaluativo> corte = corteEvaluativoService.buscarPorCarreraYPeriodo(usuario.getPreferencias().getIdCarrera(), usuario.getPreferencias().getIdPeriodo());
				List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
				model.addAttribute("alumnos", alumnos);
				//lista para rellenar alumnos con calificaciones
				List<AlumnoPromedioDTO> alumnosCalificaciones =  new ArrayList<AlumnoPromedioDTO>();
				if (alumnos.size() > 0) {
					for (Alumno alumno : alumnos) {
						//AlumnoCalificacionMateriaDTO alumnoDTO = new AlumnoCalificacionMateriaDTO();
						AlumnoPromedioDTO alumnoDTO = new AlumnoPromedioDTO();
						//se rellena el alumno y sus calificaciones
						alumnoDTO.setIdAlumno(alumno.getId());
						alumnoDTO.setNombre(alumno.getPersona().getNombreCompleto());
						List<IndicadorMateriaDTO> indicadoresMaterias = new ArrayList<IndicadorMateriaDTO>();
						for (CargaHoraria ch : cargaHorarias) {
							IndicadorMateriaDTO im = new IndicadorMateriaDTO();
							im.setIdMateria(ch.getId());
							im.setNombre(ch.getMateria().getNombre());
							// variables para guardar promedios y remediales de la materia
							double promedioFinal = 0;
							double promedioparciales = 0;
							List<IndicadorParcialDTO> indicaroresParcial = new ArrayList<IndicadorParcialDTO>();
							for (CorteEvaluativo c : corte) {
								IndicadorParcialDTO ip = new IndicadorParcialDTO();
								// para sacar el promedio de la materia y sus indicadores
								double calificacionTotal = calificacionCorteService.buscarPorAlumnoCargaHorariaYCorteEvaluativo(alumno.getId(), ch.getId(), c.getId());
								// para guardar el promedio de los dos parciales
								promedioparciales = promedioparciales + calificacionTotal;
								ip.setIdMateria(ch.getMateria().getId());
								ip.setParcial(c.getId());
								ip.setPromedio(calificacionTotal);
								// se agrega el objeto a la lista de indicador parcial
								indicaroresParcial.add(ip);
							}
							//promedio final de la materia
							promedioFinal = Math.round(promedioparciales / corte.size());
							im.setPromedio(promedioFinal);
							//se egraga la lista de indicacires materia
							im.setParciales(indicaroresParcial);
							//se agrega el estatus de la materia
							im.setEstatus(calificacionMateriaService.buscarPorAlumnoYCarga(alumno.getId(), ch.getId()));
							// se agrega el objeto de indficador materia
							indicadoresMaterias.add(im);
						}
						//se agrega la lista de indicadores materia al alumno
						alumnoDTO.setMaterias(indicadoresMaterias);
						//se agregan los alumnos al arreglo
						alumnosCalificaciones.add(alumnoDTO);
					}
					
					model.addAttribute("alumnosCali", alumnosCalificaciones);
				}
			}
		}
		model.addAttribute("carreras", carreras);
		return "asistente/reporteCalificacionesGenerales";
	}
	
	@GetMapping("/reporte-evaluacion-docente")
	public String reporteEvaluacionDocente(HttpSession session, Model model) {		
		Persona persona = new Persona((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		Integer cveCarrera = (Integer) session.getAttribute("cveCarrera");
		Integer cvePerido = (Integer) session.getAttribute("red-cvePerido");	
		Integer cveProfesor = (Integer) session.getAttribute("cveProfesor");
		
		Evaluacion evaluacion = serviceEvaluacion.buscar(3);	
		int aluEncuestados=0;		
		Periodo periodo = servicePeriodo.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		List<Periodo> periodos = periodoService.buscarTodos();
		List<Persona> profesores = new ArrayList<>();
		
		if(cveCarrera != null) {
			Carrera carrera = carrerasServices.buscarPorId(cveCarrera);
			if(cvePerido != null) {	
				profesores = personaService.buscarProfesoresPorCarreraYPeriodo(cveCarrera, cvePerido);
				if(cveProfesor!=null) {						
					//se extraen los cargas horararias (grupos) por profesor y perido  
					List<CargaHoraria> ChGrupos = cargaHorariaService.buscarPorCarreraProfesorYPeriodo(cveCarrera, cveProfesor, cvePerido);
					List<ComentarioDTO> comentarios = serviceEvaCom.buscarComentariosPorCarreraProfesorPeridoYEvaluacion(cveCarrera, cveProfesor, cvePerido, 3);
					//se obtiene el numero de alumnos que an relisado la encueta
					Boolean vista = false;
					for(CargaHoraria ch: ChGrupos) {
						aluEncuestados = serviceResCarEva.contarPorGrupoYCargaHoraria(3, ch.getGrupo().getId(), ch.getId())+aluEncuestados;
						CargaEvaluacion CaEva = serviceCarEva.buscarPorCargaHorariaYEvaluacion(ch, evaluacion);
						if(CaEva!=null) {
							vista = CaEva.getVista();
						}
					}
					
					//se caulculan los promedios de cada una de las preguntas para cada uno de los grupos en lo que el profesor imparte dicha 
					//materia en determinda carrera
					List<PreguntaDTO> preguntasDto = new ArrayList<>();				
					for(Pregunta pre :evaluacion.getPreguntas()) {	
															
						List<GrupoDTO> gruposDTO = new ArrayList<>();
						double promedioGenPre=0;					
						for(CargaHoraria ch: ChGrupos) {					
							PromedioPreguntaDTO promedioPreguntaDTOs = servicePreguntas.ObtenerPromedioPorPregunta(3, pre.getId(), ch.getId(), ch.getGrupo().getId());
							promedioGenPre=promedioPreguntaDTOs.getPromedio()+promedioGenPre;
							GrupoDTO grupoDto = new GrupoDTO();
							grupoDto.setIdGrupo(ch.getId());
							grupoDto.setNombreGrupo(ch.getMateria().getAbreviatura()+"-"+ch.getGrupo().getNombre());
							grupoDto.setPromedioPre(promedioPreguntaDTOs.getPromedio());
							gruposDTO.add(grupoDto);						
						}
						
						//se crea un grupo fantasma el cual contendra el promedio general de cada pregunta 
						//a partir de los grupos promediados  
						promedioGenPre=promedioGenPre/ChGrupos.size();
						GrupoDTO grupoDto = new GrupoDTO();
						grupoDto.setIdGrupo(0);
						grupoDto.setNombreGrupo("Promedio");
						grupoDto.setPromedioPre(promedioGenPre);
						gruposDTO.add(grupoDto);
						
						PreguntaDTO preguntaDto = new PreguntaDTO();
						preguntaDto.setIdPregunta(pre.getId());
						preguntaDto.setDescripcion(pre.getDescripcion());
						preguntaDto.setConsecutivo(pre.getConsecutivo());
						preguntaDto.setGruposDTO(gruposDTO);
						preguntasDto.add(preguntaDto);					
						model.addAttribute("grupos", gruposDTO);
					}
					//se crea una pregunta fantasma a cual contendra el promedio final de cada uno de los grupos
					//promediados y del promedio final de cada pregunta 
					List<GrupoDTO> gruposDTOs = new ArrayList<>();
					for(int i=0; i<=ChGrupos.size();) {					
						double pro=0.0;					
						for(PreguntaDTO preDTO : preguntasDto) {
							pro =  preDTO.getGruposDTO().get(i).getPromedioPre()+pro;
						}						
						GrupoDTO grupoDto = new GrupoDTO();
						grupoDto.setIdGrupo(i);
						grupoDto.setNombreGrupo("i");
						grupoDto.setPromedioPre(pro/evaluacion.getPreguntas().size());
						gruposDTOs.add(grupoDto);					
						i++;					
					}												
					PreguntaDTO preguntaDto = new PreguntaDTO();
					preguntaDto.setIdPregunta(evaluacion.getPreguntas().size()+1);
					preguntaDto.setDescripcion("Promedio");
					preguntaDto.setConsecutivo(evaluacion.getPreguntas().size()+1);
					preguntaDto.setGruposDTO(gruposDTOs);
					preguntasDto.add(preguntaDto);
					
					model.addAttribute("preguntas", preguntasDto);
					//se carga el numero de grupos para extrer el prmedio final desde el scrip 
					model.addAttribute("numGrupos", ChGrupos.size());
					//se carga el promedio total final 
					model.addAttribute("promedioTotal", gruposDTOs.get(ChGrupos.size()).getPromedioPre());
					model.addAttribute("evaVista", vista);
					model.addAttribute("comentarios", comentarios);
					
				}	
			}
			model.addAttribute("profesores",null);
			model.addAttribute("drCarrera", carrera.getDirectorCarrera());				
		}
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		model.addAttribute("usuario", usuario);
		model.addAttribute("periodo", periodo);
		model.addAttribute("aluEncuestados", aluEncuestados);			
		model.addAttribute("cveCarrera", cveCarrera);
		model.addAttribute("cveProfesor", cveProfesor);
		model.addAttribute("cvePerido", cvePerido);
		model.addAttribute("evaluacion", evaluacion);	
		model.addAttribute("carreras", carreras);
		model.addAttribute("profesores", profesores);
		model.addAttribute("periodos", periodos);
		return "asistente/reporteEvaluacionDocente";
	}
	
	@GetMapping("/reporte-evaluacion-tutor")
	public String reporteEvaluacionTutor(HttpSession session, Model model) {		
		Persona persona = new Persona((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		Integer cveCarrera = (Integer) session.getAttribute("cveCarrera");
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");				
		
		Evaluacion evaluacion = serviceEvaluacion.buscar(4);	
		int aluEncuestados=0;		
		Periodo periodo = servicePeriodo.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		List<Grupo> grupos = null;		
		
		if(cveCarrera != null) {
			grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera);
			if(cveGrupo != null) {	
				// Validación por si el reporte es de todos los grupos tutorados o de solo uno en particular
				List<Grupo> Rgrupos = new ArrayList<>();
				if(cveGrupo==0) {
					Rgrupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera);
				}else {
					Grupo Rgrupo = grupoService.buscarPorId(cveGrupo);
					Rgrupos.add(Rgrupo);
				}
				
				List<GruposEvaluacionTutorDTO> gruposEvaDto = new ArrayList<>();
				for(Grupo grupo : Rgrupos) {
					GruposEvaluacionTutorDTO grupoEvaDto =  new GruposEvaluacionTutorDTO();
					aluEncuestados = serviceResEvaTutor.contarEncuestadosPorGrupo(4, grupo.getId());				
					List<ComentarioDTO> comentarios = serviceComEvaTurtor.buscarComentariosPorGrupo(4, grupo.getId());
					//se caulculan los promedios de cada una de las preguntas para cada uno de los grupos en lo que el profesor imparte dicha materia en determinda carrera
					List<PreguntaDTO> preguntasDto = new ArrayList<>();	
					double promedioPre=0.0;
					for(Pregunta pre :evaluacion.getPreguntas()) {
						PromedioPreguntaDTO promedioPreguntaDTOs = servicePreguntas.ObtenerPromedioEvaTuPorPregunta(4, pre.getId(), grupo.getId());
						promedioPre=promedioPreguntaDTOs.getPromedio()+promedioPre;
						
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
					double proGe=0.0;					
					for(PreguntaDTO preDTO : preguntasDto) {
						proGe =  preDTO.getGruposDTO().get(0).getPromedioPre()+proGe;
					}		
					
					proGe=proGe/evaluacion.getPreguntas().size();
					GrupoDTO grupoDto = new GrupoDTO();
					grupoDto.setIdGrupo(0);
					grupoDto.setNombreGrupo("0");
					grupoDto.setPromedioPre(proGe);
					gruposDTOs.add(grupoDto);					
					
					
					PreguntaDTO preguntaDto = new PreguntaDTO();
					preguntaDto.setIdPregunta(evaluacion.getPreguntas().size()+1);
					preguntaDto.setDescripcion("Promedio");
					preguntaDto.setConsecutivo(evaluacion.getPreguntas().size()+1);	
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
		}
		
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		model.addAttribute("carreras", carreras);
		model.addAttribute("grupos", grupos);				
		model.addAttribute("periodo", periodo);
		model.addAttribute("usuario", usuario);		
		model.addAttribute("aluEncuestados", aluEncuestados);			
		model.addAttribute("cveCarrera", cveCarrera);
		model.addAttribute("cveGrupo", cveGrupo);		
		model.addAttribute("evaluacion", evaluacion);			
		return "asistente/reporteEvaluacionTutor";
	}
	
}
