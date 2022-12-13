package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Actividad;
import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AreaConocimiento;
import edu.mx.utdelacosta.model.Asistencia;
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
import edu.mx.utdelacosta.model.dto.HorarioDiaDTO;
import edu.mx.utdelacosta.model.dto.MateriaDTO;
import edu.mx.utdelacosta.model.dto.MesDTO;
import edu.mx.utdelacosta.model.dto.PreguntaDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoEvaluacionesDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioDTO;
import edu.mx.utdelacosta.model.dtoreport.EvaluacionMateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorMateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorParcialDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.model.dtoreport.ProfesorCalificacionesDTO;
import edu.mx.utdelacosta.service.IActividadService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAreaConocimientoService;
import edu.mx.utdelacosta.service.IAsistenciaService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
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
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPersonalService;
import edu.mx.utdelacosta.service.IPlanEstudioService;
import edu.mx.utdelacosta.service.IPreguntaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.IRespuestaCargaEvaluacionService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionTutorService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.ReporteXlsxView;

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
	private IPeriodosService periodoService;
  
	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;
	
	@Autowired
	private IMecanismoInstrumentoService mecanismoInstrumentoService;
	
	@Autowired
	private ReporteXlsxView reporte;
	
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
			Grupo grupo;
			try {
				grupo = grupoService.buscarPorId(cveGrupo);
			}catch (Exception e) {
				grupo = null;
			}
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
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona")); 
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveGrupo;
		try {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
		} catch (Exception e) {
			cveGrupo = 0;
		}
		Grupo grupo = grupoService.buscarPorId(cveGrupo);
		if(grupo != null) {
			//debo de buscar porcuatrimestre, carrera y periodo
			List<Grupo> listGrupos = grupoService.buscarPorCuatrimestreCarreraYPeriodo(grupo.getCuatrimestre().getId(), grupo.getCarrera().getId(), usuario.getPreferencias().getIdPeriodo());
			model.addAttribute("gruposC", listGrupos);
		}
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera( usuario.getPreferencias().getIdPeriodo(),usuario.getPreferencias().getIdCarrera()); //se tomara de la session la carrera y el periodo
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		List<Materia> materias = materiasService.buscarPorCargaActivaEnGrupo(cveGrupo, usuario.getPreferencias().getIdCarrera());
		//para mandar los grupos a cambiar
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
		model.addAttribute("carreras", carreras);
		model.addAttribute("alumnos", alumnos);
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
		List<HorarioDiaDTO> horarios = new ArrayList<>();
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
			 // se obtienen los dias y el formate
			   List<Dia> dias = diaService.buscarDias();
			   DateFormat formatoVista = new SimpleDateFormat("HH:mm");
			   DateFormat formatoBusqueda = new SimpleDateFormat("HH:mm:ss");
			  
			   //se buscan las horas de cada dia del profesor
			   List<Horario> horas = horarioService.buscarPorProfesorDistinctPorHoraInicio(cveProfesor, usuario.getPreferencias().getIdPeriodo());
			   
			   //se crean las listas de datos
			   horarios = new ArrayList<>();
			   
			   for (Horario hora : horas) {
				HorarioDiaDTO horario = new HorarioDiaDTO();
			   
				// se agregan las fechas y horas de inicio
				horario.setHoraInicio(formatoVista.format(hora.getHoraInicio()));
			    horario.setHoraFin(formatoVista.format(hora.getHoraFin()));
			    
			    // se crea la lista de horas por dia y hora
			    List<HorarioDTO> diasClase = new ArrayList<>();
			    for (Dia dia : dias) {
			    	HorarioDTO horarioDia = null;
			    	//se busca el horario por dia y hora y se agregan al DTO 
			    	horarioDia = horarioService.buscarPorHoraInicioDiaYProfesor(formatoBusqueda.format(hora.getHoraInicio()), dia.getId(), cveProfesor, usuario.getPreferencias().getIdPeriodo());
			    	diasClase.add(horarioDia);
			    }
			    horario.setHorarios(diasClase);
			    horarios.add(horario);
			   }
			   model.addAttribute("dias", dias);
		} 
		else {
			model.addAttribute("cveProfesor", cveProfesor);
		}
		model.addAttribute("horarios", horarios);
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
			cargasHorarias = cargaHorariaService.buscarPorGrupoYPeriodoYCalificacionSi(cveGrupo, usuario.getPreferencias().getIdPeriodo());
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
				
				//se igual la fecha de inicio del corte al dia 1 del mes de inicio
				Calendar cal = Calendar.getInstance();
				cal.setTime(corte.getFechaInicio());
				cal.set(Calendar.DAY_OF_MONTH, 1);
				Date fechaInicio = cal.getTime();
				
				//se buscan los meses usando el dia 1 del mes del corte para obtener todos los meses sin excepcion
				List<Date> meses = serviceAsistencia.mesesEntreFechaInicioYFechaFinAsc(fechaInicio,	corte.getFechaFin());
				List<Asistencia> asistencias = serviceAsistencia.buscarPorFechaInicioYFechaFinEIdCargaHorariaEIdGrupo(
						corte.getFechaInicio(), corte.getFechaFin(), cveCarga, cveGrupo);
				model.addAttribute("asistencias", asistencias);
				List<MesDTO> mesesDto = new ArrayList<>();
				for (Date mes : meses) {									
					//se obtiene la fecha del ultimo dia del mes
					Calendar mesFor = Calendar.getInstance();
					mesFor.setTime(mes);
					mesFor.set(Calendar.DAY_OF_MONTH, mesFor.getActualMaximum(Calendar.DATE));
					Date finDeMes = mesFor.getTime();
					
					/* se cargan las fechas de los meses, para esto se compara si la fecha de inicio del mes 
					 * es menor a la fecha de inicio del corte, lo mismo con la fecha de fin de mes y delimitar
					 * el numero de dias que aparecen en la vista
					*/
					Date inicioMes = mes.compareTo(corte.getFechaInicio())<0 ? corte.getFechaInicio() : mes;
					Date finMes = finDeMes.compareTo(corte.getFechaFin())>0 ? corte.getFechaFin() : finDeMes;

				    MesDTO mesDTO = new MesDTO(); 
				    mesDTO.setMes(new SimpleDateFormat("MMMM").format(mesFor.getTime()));       
				    mesesDto.add(mesDTO); 
				    cortesEvaluativosDTO.setMeses(mesesDto);
					
				    //se obtienen los dias en base a las fechas asignadas de inicio y fin
					List<Date> dias = serviceAsistencia.diasEntreFechaInicioYFechaFin(inicioMes, finMes);						 
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
		List<HorarioDiaDTO> horarios = new ArrayList<>();
		if(session.getAttribute("cveGrupo") != null) {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
			//cargas horarias por profesor
			cargasHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			List<Actividad> actividades = actividadService.buscarTodas();
			List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			List<Personal> profesores = personalService.buscarProfesores();
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("actividades", actividades);
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cargasHorarias", cargasHorarias);
			model.addAttribute("profesores", profesores);
			/////****** proceso de creacion de horario
			
			List<Dia> dias = diaService.buscarDias();
			horarios = generarHorario(cveGrupo, usuario.getPreferencias().getIdPeriodo(), dias);

			model.addAttribute("tutor", grupo!= null ? grupo.getProfesor().getId():0);
			model.addAttribute("jefeGrupo", grupo != null ? grupo.getJefeGrupo().getId():0);
			model.addAttribute("subJefe", grupo != null ? grupo.getSubjefeGrupo().getId():0);
			model.addAttribute("horarios", horarios);
			model.addAttribute("dias", dias);
		}
		else {
			model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
		}
		model.addAttribute("grupos",grupos); //retorna los grupos de nivel TSU
		model.addAttribute("carreras", carreras);
		return "asistente/grupos";
	}
	
	//fechas de entrega
	@GetMapping("/fechas")
	public String fechas(Model model, HttpSession session) {
		//creamos el usuario de acuerdo a la authenticaci�n 
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona")); 
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		// buscamos las carreras de acuerdo a las preferencias y permisos del usuario
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(usuario.getPersona().getId());
		if (carreras.size() > 0) {
			model.addAttribute("carreras", carreras.size());
			List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(carreras.get(0).getId(),
					usuario.getPreferencias().getIdPeriodo());
			if (cortes.size() > 0) {
				model.addAttribute("corte1", cortes.get(0));
				model.addAttribute("corte2", cortes.get(1));
			} else {
				CorteEvaluativo corte1 = new CorteEvaluativo();
				CorteEvaluativo corte2 = new CorteEvaluativo();
				model.addAttribute("corte1", corte1);
				model.addAttribute("corte2", corte2);
			}
		} else {
			// para decirle que no tiene permisos
			model.addAttribute("carreras", 0);
		}
		return "asistente/fechas";
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
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			List<Alumno> alumnos = new ArrayList<>();
			if(cveCarrera == 0) {
				alumnos = alumnoService.buscarPorPersonaCarreraAndActivo(usuario.getPersona().getId(), usuario.getPreferencias().getIdPeriodo());
			}
			else {
				alumnos = alumnoService.buscarPorCarreraAndPeriodoAndActivo(cveCarrera, usuario.getPreferencias().getIdPeriodo());
			}
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cveCarrera", cveCarrera);
		}
		Periodo periodo = servicePeriodo.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cuatrimestre", periodo.getNombre());
		model.addAttribute("carreras", carrerasServices.buscarCarrerasPorIdPersona(persona.getId()));
		model.addAttribute("nombreUT", NOMBRE_UT);
		return "asistente/reporteDatosPersonales";
	}
	
	@GetMapping("/datos-personalespdf")
	@ResponseBody
	public String reporteDatosPersonalesPdf(HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Object> datos = new ArrayList<Object>();
		String reporteGenerar = null;
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			List<Alumno> alumnos = new ArrayList<>();
			if(cveCarrera == 0) {
				alumnos = alumnoService.buscarPorPersonaCarreraAndActivo(usuario.getPersona().getId(), usuario.getPreferencias().getIdPeriodo());
			}
			else {
				alumnos = alumnoService.buscarPorCarreraAndPeriodoAndActivo(cveCarrera, usuario.getPreferencias().getIdPeriodo());
			}
			String[] headers = new String[]{
	                "No",
	                "Matrícula",
	                "Nombre",
	                "Sexo",
	                "Correo electrónico",
	                "Teléfono"
	        };
			int n = 0;
			//acomodamos los datos en el reporte
			for (Alumno alumno : alumnos) {
				n++;
				Arrays.asList(datos.add(new Object[] {n+"", alumno.getMatricula(), alumno.getPersona().getNombreCompletoPorApellido(), 
					(alumno.getPersona().getSexo().equals("H") ? "Masculino" : "Femenino"), alumno.getPersona().getEmail(), alumno.getPersona().getDatosPersonales().getTelefono()}));
			}
			Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
			reporteGenerar = reporte.generarExcelGenerico("Datos personales", NOMBRE_UT, "REPORTE DE DATOS PERSONALES ", "Cuatrimestre: "+periodo.getNombre(), headers, datos);
		}
		return reporteGenerar;
	}
	
	@GetMapping("/reporte-horario")
	public String reporteHorario(Model model, HttpSession session){
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		int cveCarrera = 0;
		int cve = 0;

		try {
			cve = (int) session.getAttribute("cveCarrera");
		} catch (Exception e) {

		}
		if(session.getAttribute("cveCarrera") != null && cve > 0) {
			cveCarrera = (Integer) session.getAttribute("cveCarrera");
			model.addAttribute("cveCarrera", cveCarrera);
			int cveGrupo = 0;
			if(session.getAttribute("cveGrupo") != null ) {
				cveGrupo = (Integer) session.getAttribute("cveGrupo");
				Grupo grupo = new Grupo();
				List<HorarioDiaDTO> horarios = new ArrayList<>();
				if(cveGrupo > 0) {
					model.addAttribute("cveGrupo", cveGrupo);
					grupo = grupoService.buscarPorId(cveGrupo);
					model.addAttribute("grupo", grupo);
					model.addAttribute("tutor", grupo.getProfesor().getNombreCompletoConNivelEstudio());
					Carrera carrera = carrerasServices.buscarPorId(cveCarrera);
					model.addAttribute("director", carrera.getDirectorCarrera());
					List<Dia> dias = diaService.buscarDias();
					horarios = generarHorario(grupo.getId(), grupo.getPeriodo().getId(), dias);
					model.addAttribute("dias", dias);				
				}
				/////****** proceso de creacion de horario
				model.addAttribute("horarios", horarios);
			}
			else {
				model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
			}
			model.addAttribute("grupos",grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera)); //retorna los grupos de nivel TSU
		}
		Periodo periodo = servicePeriodo.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cuatrimestre", periodo.getNombre());
		model.addAttribute("carreras", carreras);
		model.addAttribute("nombreUT", NOMBRE_UT);
		return "asistente/reporteHorario";
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
				//model.addAttribute("grupoActual", grupoService.buscarPorId(cveGrupo));
				//proceso para sacar las materias
				List<CargaHoraria> cargaHorarias = cargaHorariaService.buscarPorGrupoYPeriodoYCalificacionSi(cveGrupo, usuario.getPreferencias().getIdPeriodo());
				model.addAttribute("cargasHorarias", cargaHorarias);
				List<CorteEvaluativo> corte = corteEvaluativoService.buscarPorCarreraYPeriodo(cveCarrera, usuario.getPreferencias().getIdPeriodo());
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
						alumnoDTO.setNombre(alumno.getPersona().getNombreCompletoPorApellido());
						alumnoDTO.setMatricula(alumno.getMatricula());
						alumnoDTO.setActivo(alumno.getEstatusGeneral() == 0 ? false : true);
						List<IndicadorMateriaDTO> indicadoresMaterias = new ArrayList<IndicadorMateriaDTO>();
						for (CargaHoraria ch : cargaHorarias) {
							IndicadorMateriaDTO im = new IndicadorMateriaDTO();
							im.setIdMateria(ch.getId());
							im.setNombre(ch.getMateria().getNombre());
							//se agrega el estatus de la materia
							im.setEstatus(calificacionMateriaService.buscarPorAlumnoYCarga(alumno.getId(), ch.getId()));
							
							List<IndicadorParcialDTO> indicaroresParcial = new ArrayList<IndicadorParcialDTO>();
							for (CorteEvaluativo c : corte) {
								IndicadorParcialDTO ip = new IndicadorParcialDTO();
								ip.setIdMateria(ch.getMateria().getId());
								ip.setParcial(c.getId());
								// para guardar si tiene remediales o extraordinarios
								ip.setRemediales(remedialAlumnoService.buscarCalificacionPorAlumnoYCargaHorariaYCorteEvaluativoYTipo(alumno.getId(), ch.getId(), c.getId(), 1));
								ip.setExtraordinarios(remedialAlumnoService.buscarCalificacionPorAlumnoYCargaHorariaYCorteEvaluativoYTipo(alumno.getId(), ch.getId(), c.getId(), 2));
								
								double calificacionTotal = 0f;
									calificacionTotal = calificacionCorteService.buscarPromedioCortePorMecanismoIntrumentoYCarga(ch.getId(), c.getId(), alumno.getId());						
								// para guardar el promedio de los dos parciales
								ip.setPromedio(calificacionTotal);
								
								// se agrega el objeto a la lista de indicador parcial
								indicaroresParcial.add(ip);
							}
							//promedio final de la materia
							im.setPromedio(calificacionMateriaService.buscarCalificacionPorAlumnoYCargaHoraria(alumno.getId(), ch.getId()));
							//se egraga la lista de indicacires materia
							im.setParciales(indicaroresParcial);
							
							// se agrega el objeto de indficador materia
							indicadoresMaterias.add(im);
						}
						//se agrega la lista de indicadores materia al alumno
						alumnoDTO.setMaterias(indicadoresMaterias);
						//se agregan los alumnos al arreglo
						alumnosCalificaciones.add(alumnoDTO);
					}
					
					model.addAttribute("alumnosCali", alumnosCalificaciones);
					List<IndicadorMateriaDTO> indicadoresMaterias = new ArrayList<IndicadorMateriaDTO>();
					//proceso para tabla de indicadores
					if (corte.size() > 1) {
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
								double calificacionTotal = calificacionCorteService
										.buscarPorCargaHorariaIdCorteEvaluativoIdGrupo(ch.getId(), c.getId());
								double promedio = calificacionTotal / alumnos.size();
								promedioFinal = promedioFinal + promedio;
								int remediales = remedialAlumnoService.contarRemedialesAlumnoPorCargaHorariaYRemedialYCorteEvaluativo(ch.getId(), 1, c.getId());
								double pr = (remediales * 100) / alumnos.size();
								int extraordernarios = remedialAlumnoService.contarRemedialesAlumnoPorCargaHorariaYRemedialYCorteEvaluativo(ch.getId(), 2, c.getId());
								double pe = (extraordernarios * 100) / alumnos.size();
								// para guaredar lor promedios finales de extras y remediales
								tRemediales = tRemediales + remediales;
								tExtras = tExtras + extraordernarios;
								ip.setIdMateria(ch.getMateria().getId());
						        ip.setPromedio(promedio);
						        ip.setRemediales(remediales);
						        ip.setpRemediales(pr);
						        ip.setExtraordinarios(extraordernarios);
						        ip.setpExtraordinarios(pe);
						        ip.setParcial(c.getConsecutivo());
						        //se añade a la lista el indicador de materia
						        indicaroresParcial.add(ip);
						      //promedio remediales y extras para materia
								pRemes = pRemes + remediales;
								pExtras = pExtras + extraordernarios;
							}
							// se agregan los promedios y procentajes generales
							im.setPromedio(Math.round(promedioFinal / corte.size()));
							im.setRemediales(remedialAlumnoService.contarRemedialesAlumnoPorCargaHorariaYRemedial(ch.getId(), 1));
							im.setpRemediales((pRemes * 100) / alumnos.size());
							im.setExtraordinarios(remedialAlumnoService.contarRemedialesAlumnoPorCargaHorariaYRemedial(ch.getId(), 2));
							im.setpExtraordinarios((pExtras * 100) / alumnos.size());
							im.setParciales(indicaroresParcial);
							// se agrega el objeto de indficador materia
							indicadoresMaterias.add(im);
						}
						// se retorna la lista de indicadores materia
						model.addAttribute("materias", indicadoresMaterias);
						model.addAttribute("alumnos", alumnos.size());
					} 
				} 
			}
		}
		Periodo periodo = servicePeriodo.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cuatrimestre", periodo.getNombre());
		model.addAttribute("carreras", carreras);
		model.addAttribute("nombreUT", NOMBRE_UT);
		return "asistente/reporteCalificacionesGenerales";
	}
	
	@GetMapping("/reporte-evaluacion-docente")
	public String reporteEvaluacionDocente(HttpSession session, Model model) {		
		Persona persona = new Persona((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		//variables para la variables de sesion
		int cveCarrera = 0;
		int cvePeriodo = 0;	
		int cveProfesor = 0;
		try {
			cveCarrera = (int) session.getAttribute("cveCarrera");
		} catch (Exception e) {

		}
		
		Evaluacion evaluacion = serviceEvaluacion.buscar(3);
		Periodo periodo = servicePeriodo.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		List<Periodo> periodos = periodoService.buscarLiberados();
		List<Persona> profesores = new ArrayList<>();
		
		if(cveCarrera > 0) {
			Carrera carrera = carrerasServices.buscarPorId(cveCarrera);
			//variable de perodo
			try {
				cvePeriodo = (int) session.getAttribute("red-cvePerido");
			} catch (Exception e) {

			}
			//se compara que el periodo no sea cero
			if(cvePeriodo > 0) {
				//variable de profesor
				profesores = personaService.buscarProfesoresPorCarreraYPeriodo(cveCarrera, cvePeriodo);
				try {
					cveProfesor = (int) session.getAttribute("cveProfesor");
				} catch (Exception e) {

				}
				if(cveProfesor > 0) {	
					Persona profesor = personaService.buscarPorId(cveProfesor);
					//lista de preguntas 
					List<PreguntaDTO> preguntasDTO = new ArrayList<>();
					//se buscan las preguntas 
					List<Integer> preguntas = servicePreguntas.buscarPorIdEvaluacion(evaluacion.getId());
					//comentarios
					List<ComentarioDTO> comentarios = serviceEvaCom.buscarComentariosPorCarreraProfesorPeridoYEvaluacion(cveCarrera, cveProfesor, cvePeriodo, 3);
					//variable para promedio total
					double promedioTotal = 0;
					Integer encuestados = serviceResCarEva.contarAlumnosPorIdProfesorIdCarreraIdPeriodo(cveProfesor, cveCarrera, cvePeriodo);  
					//se iteran las preguntas
					for (Integer p : preguntas) {
						Pregunta pre = servicePreguntas.buscarPorId(p);
						//se crea el objeto de pregunta
						PreguntaDTO pregunta = new PreguntaDTO();
						pregunta.setIdPregunta(p);
						pregunta.setDescripcion(pre.getDescripcion());
						pregunta.setConsecutivo(pre.getConsecutivo());
						//se crea una lista de grupos
						List<GrupoDTO> grupos = new ArrayList<>();
						//variable ppara promedio por pregunta
						double pp = 0;
						List<Integer> idCargas = cargaHorariaService.buscarPorIdProfesorYidCarreraYidPeriodo(profesor.getId(), cveCarrera, periodo.getId());
						//se iteran las cargas horarias
						for(Integer ch: idCargas) {
							//se crea un objeto e grupo
							GrupoDTO grupo = new GrupoDTO();
							CargaHoraria carga = cargaHorariaService.buscarPorIdCarga(ch);
							Integer alumnos = serviceResCarEva.contarPorIdPreguntaIdCargaHorariaIdEvaluacion(p, ch, evaluacion.getId());
							Integer ponderacion = serviceResCarEva.sumarPonderacionPorIdPreguntaIdCargaHorariaIdEvaluacion(p, ch, evaluacion.getId());
							double promedio = 0;
							if(ponderacion > 0 && alumnos > 0) {
								promedio =  Double.valueOf(ponderacion) / Double.valueOf(alumnos);
							}
							grupo.setNombreGrupo(carga.getGrupo().getNombre()+"-"+carga.getMateria().getAbreviatura());
							grupo.setPromedioPre(promedio);//promedio de respuestas por grupo
							//se agrega el objeto a la lista de grupos
							grupos.add(grupo);
							//promedio
							pp = pp + promedio;
						}
						//se agrega la lista de grupos a la pregunta
						pregunta.setGruposDTO(grupos);
						//se agrega la pregunta a la lista de preguntas
						preguntasDTO.add(pregunta);
						//iteracion de grupos
						int divisor = 0;
						for (GrupoDTO g : pregunta.getGruposDTO()) {
							if(g.getPromedioPre() > 0) {
								divisor = divisor + 1;
							}
						}
						//promedio por pregunta
						pregunta.setPromedio(pp/divisor);
						
						//se enva la lista de grupos para los encabezados
						model.addAttribute("grupos", grupos);
						//promedio total de la evaluacion
						promedioTotal = promedioTotal + pregunta.getPromedio();
					}
					//promedio total de la evaluacion
					promedioTotal = promedioTotal / preguntas.size();
					model.addAttribute("promedioTotal", promedioTotal);
					//se envia a la vista la lista de preguntas
					model.addAttribute("preguntas", preguntasDTO);
					model.addAttribute("profesor", profesor.getNombreCompletoConNivelEstudio());
					model.addAttribute("comentarios", comentarios);
					model.addAttribute("encuestados", encuestados);
					model.addAttribute("cveProfesor", cveProfesor);
				}	
				model.addAttribute("cvePerido", cvePeriodo);
			}
			model.addAttribute("profesores",null);
			model.addAttribute("drCarrera", carrera.getDirectorCarrera());	
			model.addAttribute("cveCarrera", cveCarrera);
		}
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		model.addAttribute("periodo", periodo);			
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
		//variable de comparación
		int cveCarrera = 0;
		int cveGrupo = 0;
		//variable de carrera
		try {
			cveCarrera = (int) session.getAttribute("cveCarrera");
		} catch (Exception e) {

		}
	
		Periodo periodo = servicePeriodo.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		List<Grupo> grupos = null;		
		//se compara que la carrera no sea nula
		if(cveCarrera > 0) {
			//se construye la carrera
			Carrera carrera = carrerasServices.buscarPorId(cveCarrera);
			model.addAttribute("carrera", carrera);
			//variable de grupo
			try {
				cveGrupo = (int) session.getAttribute("cveGrupo");
			} catch (Exception e) {

			}
			//se buscan los gruppos por carrera
			grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera);
			//se compara que el grupo no sea nulo
			if(cveGrupo > 0) {	
				//se crea el objeto de la evaluacion tutor
				Evaluacion evaluacion = serviceEvaluacion.buscar(4);
				//lista de preguntas 
				List<PreguntaDTO> preguntasDTO = new ArrayList<>();
				//se buscan las preguntas 
				List<Integer> preguntas = servicePreguntas.buscarPorIdEvaluacion(evaluacion.getId());
				//comentarios de la evaluacion
				List<ComentarioDTO> comentarios = serviceComEvaTurtor.buscarComentariosPorGrupo(4, cveGrupo);
				//variable para promedio total
				double promedioTotal = 0;
				//cuenta los alumnos que respondieron la evaluacion
				Integer encuestados= serviceResEvaTutor.contarAlumnosPorIdGrupoIdPeriodo(cveGrupo, periodo.getId());
				//se iteran las preguntas
				for (Integer p : preguntas) {
					//se crea el objeto de la pregunta
					Pregunta pre = servicePreguntas.buscarPorId(p);
					//se crea el objeto de pregunta
					PreguntaDTO pregunta = new PreguntaDTO();
					pregunta.setIdPregunta(p);
					pregunta.setDescripcion(pre.getDescripcion());
					pregunta.setConsecutivo(pre.getConsecutivo());
					//se crea una lista de grupos
					List<GrupoDTO> gruposDto = new ArrayList<>();
					//se crea un objeto de grupos
					GrupoDTO grupoDto = new GrupoDTO();
					//se crea el objeto de grupo
					Grupo grupo = grupoService.buscarPorId(cveGrupo);
					//se envia el grupo a la pantalla
					model.addAttribute("grupo", grupo);
					//variable ppara promedio por pregunta
					double pp = 0;
					//sumatoria de las respuestas
					Integer ponderacion = serviceResEvaTutor.sumarPonderacionPorIdPreguntaIdGrupoIdEvaluacion(p, cveGrupo, 4);
					//cuenta los alumnos que respondieron a la evaluacion
					Integer alumnos = serviceResEvaTutor.contarPorIdPreguntaIdGrupoIdEvaluacion(p, cveGrupo, 4);
					double promedio = 0;
					if(ponderacion > 0 && alumnos > 0) {
						promedio =  Double.valueOf(ponderacion) / Double.valueOf(alumnos);
					}
					grupoDto.setNombreGrupo(grupo.getNombre());
					grupoDto.setPromedioPre(promedio);
					//se agrega el objeto a la lista de grupos
					gruposDto.add(grupoDto);
					//promedio
					pp = pp + promedio;
					//se agrega la lista de grupos a la pregunta
					pregunta.setGruposDTO(gruposDto);
					//se agrega la pregunta a la lista de preguntas
					preguntasDTO.add(pregunta);
					//iteracion de grupos
					int divisor = 0;
					for (GrupoDTO g : pregunta.getGruposDTO()) {
						if(g.getPromedioPre() > 0) {
							divisor = divisor + 1;
						}
					}
					//promedio por pregunta
					pregunta.setPromedio(pp/divisor);
					//se enva la lista de grupos para los encabezados
					model.addAttribute("gruposDto", gruposDto);
					//promedio total de la evaluacion
					promedioTotal = promedioTotal + pregunta.getPromedio();
				}
				promedioTotal = promedioTotal / preguntas.size();
				model.addAttribute("promedioTotal", promedioTotal);
				//se envia a la vista la lista de preguntas
				model.addAttribute("preguntas", preguntasDTO);
				model.addAttribute("comentarios", comentarios);
				model.addAttribute("evaluacion", evaluacion);
				model.addAttribute("encuestados", encuestados);
			}								
		}
		
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		model.addAttribute("carreras", carreras);
		model.addAttribute("grupos", grupos);				
		model.addAttribute("periodo", periodo);
		model.addAttribute("usuario", usuario);				
		model.addAttribute("cveCarrera", cveCarrera);
		model.addAttribute("cveGrupo", cveGrupo);					
		return "asistente/reporteEvaluacionTutor";
	}
	
	@GetMapping("/reporte-alumnos-encuestas")
	public String reporteAlumnosEncuentas(HttpSession session, Model model) {
		Persona persona = new Persona((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Alumno> alumnos = new ArrayList<>();
		if (session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			model.addAttribute("cveCarrera", cveCarrera);
			//lista de grupos por carrera
			List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera);
			model.addAttribute("grupos", grupos);
			if (session.getAttribute("cveGrupo") != null) {
				int cveGrupo = (Integer) session.getAttribute("cveGrupo");
				model.addAttribute("cveGrupo", cveGrupo);
				// se buscan los alumnos del grupo
				alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(cveGrupo);
				// se iteran los alumnos para saber si ya contestaron las evaluaciones.
				List<AlumnoEvaluacionesDTO> alumnosEvalua = new ArrayList<>();
				// se crea el objeto de evaluacion docente
				Evaluacion evaluacionDocente = serviceEvaluacion.buscar(3);
				for (Alumno alumno : alumnos) {
					// se rrelena el dto de alumnoEvaluacion
					AlumnoEvaluacionesDTO alum = new AlumnoEvaluacionesDTO();
					alum.setIdAlumno(alumno.getId());
					alum.setMatricula(alumno.getMatricula());
					alum.setNombre(alumno.getPersona().getNombreCompletoPorApellido());
					// se construye el grupo del alumno
					Grupo grupo = grupoService.buscarUltimoDeAlumno(alumno.getId());
					// se inserta el grupo al objet de alumno
					alum.setGrupo(grupo.getNombre());
					// lista de evaluacionMateria
					List<EvaluacionMateriaDTO> evaluaciones = new ArrayList<>();
					// se buscan las cargas horarias del grupo con configuracion calificacion si y
					// activas por periodo
					List<CargaHoraria> cargas = cargaHorariaService.buscarPorGrupoYPeriodoYCalificacionSi(grupo.getId(),
							usuario.getPreferencias().getIdPeriodo());
					//se envian a la vista las cargas horarias
					model.addAttribute("cargasHorarias", cargas);
					//se iteran las cargas horarias paa buscar las respuesta de la evaluacion
					for (CargaHoraria ch : cargas) {
						Integer respuestas = serviceResCarEva.contarPorIdPersonaYEvaluacionYIdCargaHoraria(
								alumno.getPersona().getId(), 3, ch.getId());
						// se crea el objeto de evaluacion materia
						EvaluacionMateriaDTO evaluacion = new EvaluacionMateriaDTO();
						evaluacion.setIdMateria(ch.getMateria().getId());
						evaluacion.setRespuestas(respuestas);
						evaluacion.setTerminada((respuestas == evaluacionDocente.getPreguntas().size()) ? true : false);
						// se añade el objeto a la lista de evaluaciones
						evaluaciones.add(evaluacion);
					}
					// se guardan los objetos de las evaluacionMateria en el objeto de
					// alumnoEvaluaciones
					alum.setMaterias(evaluaciones);
					//se verifica que la evaluacion tutor este completada
					Evaluacion evaluacionTutor = serviceEvaluacion.buscar(4);
					alum.setEvaluacionTutor((serviceResEvaTutor.contarPorIdPersonaYIdGrupoYActivo(alumno.getPersona().getId(), grupo.getId()) == evaluacionTutor.getPreguntas().size() ? true : false));
					// se agrega el objeto alumno a la lista de alumnosEvaluaciones
					alumnosEvalua.add(alum);
				}
				// se envia a la vista la lista de alumnos
				model.addAttribute("alumnos", alumnosEvalua);
			}
		}
		Periodo periodo = servicePeriodo.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		
		model.addAttribute("periodo", periodo);;
		model.addAttribute("carreras", carrerasServices.buscarCarrerasPorIdPersona(persona.getId()));
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "asistente/reporteAlumnosEncuestas";
	}
	
	@GetMapping("/reporte-profesores-faltantes")
	public String reporteProfesoresFaltantes(Model model, HttpSession session) {
		Persona persona = new Persona((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		//se buscan las carreras por usuario
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		model.addAttribute("carreras", carreras);
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			model.addAttribute("cveCarrera", cveCarrera);
			int cve = 0;
			try {
				cve = (int) session.getAttribute("cveParcial");
			} catch (Exception e) {

			}
			//buscan los cortes para las carreras
			List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(cveCarrera, periodo.getId());
			model.addAttribute("cortes", cortes);
			//se compara si hay corte seleccionado
			if(session.getAttribute("cveParcial") != null && cve > 0) {
				int cveParcial = (Integer) session.getAttribute("cveParcial");
				model.addAttribute("cveParcial", cveParcial);
				//se sacan los profesores por carrera
				List<Persona> profesores = personaService.buscarProfesoresPorCarreraYPeriodo(cveCarrera, periodo.getId());
				//lista de profesorescalificacionesDTO
				List<ProfesorCalificacionesDTO> profesCali = new ArrayList<>();
				//se iteran los profesores para sacar sus cargas horarias
				for (Persona p : profesores) {
					//se crea bjeta de profesorCalificacion
					ProfesorCalificacionesDTO profesor = new ProfesorCalificacionesDTO();
					profesor.setIdPersona(p.getId());
					profesor.setNombre(p.getNombreCompletoPorApellido());
					//booleano para progreso de captura
					Boolean instrumento = false;
					Boolean calificacion = false;
					//se buscan las cargas horarias por profesor
					List<CargaHoraria> cargas = cargaHorariaService.buscarPorProfesorYCarreraYPeriodo(p.getId(), cveCarrera, periodo.getId());
					///se iteran las cargas horarias para sacar los instrmentos
					for (CargaHoraria ch : cargas) {
						Integer instrumentos = mecanismoInstrumentoService.contarPorIdCargaHorariaYIdCorteEvaluativo(ch.getId(), cveParcial);
						//se compara que haya instrumentos 
						if(instrumentos > 0) {
							instrumento = true;
						} else {
							instrumento = false;
							break;
						}
					}
					//iteración para sacar las calificaciones del grupo
					for (CargaHoraria ch : cargas) {
						Integer alumnos = alumnoService.contarAlumnosPorGrupoYActivos(ch.getGrupo().getId());
						//se consutarán cuantas calificaciones corte por grupo hay y se compará por el numero de alumnos
						Integer calificaciones = calificacionCorteService.contarPorIdCargaHorariaYidCorte(ch.getId(), cveParcial);
						if(calificaciones >= alumnos) {
							calificacion = true;
						}
						else {
							calificacion = false;
							break;
						}
					}
					//se garda si hay instrmentos
					profesor.setInstrumentos(instrumento);
					//se guarda si hay calificaciones
					profesor.setCalificaciones(calificacion);
					//se añande el objeto a la lista de profeCali
					profesCali.add(profesor);
				}
				//se envia a la vista los profes cali
				model.addAttribute("profesores", profesCali);
			}
		}
		
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		model.addAttribute("periodo", periodo);
		return "asistente/reporteProfesoresFaltantes";
	}
	
	
	public List<HorarioDiaDTO> generarHorario(Integer cveGrupo, Integer periodo, List<Dia> dias){
			   DateFormat formatoVista = new SimpleDateFormat("HH:mm");
			   DateFormat formatoBusqueda = new SimpleDateFormat("HH:mm:ss");
			   //se buscan las horas de cada dia del profesor
			   List<Horario> horas = horarioService.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
			   //se crean las listas de datos
			   List<HorarioDiaDTO> horarios = new ArrayList<>();
			   for (Horario hora : horas) {
				HorarioDiaDTO horario = new HorarioDiaDTO();
				// se agregan las fechas y horas de inicio
				horario.setHoraInicio(formatoVista.format(hora.getHoraInicio()));
			    horario.setHoraFin(formatoVista.format(hora.getHoraFin()));
			    // se crea la lista de horas por dia y hora
			    List<HorarioDTO> diasClase = new ArrayList<>();
			    for (Dia dia : dias) {
			    	HorarioDTO horarioDia = null;
			    	//se busca el horario por dia y hora y se agregan al DTO 
			    	horarioDia = horarioService.buscarPorHoraInicioDiaYGrupo(formatoBusqueda.format(hora.getHoraInicio()), dia.getId(), cveGrupo);
			    	diasClase.add(horarioDia);
			    }
			    horario.setHorarios(diasClase);
			    horarios.add(horario);
			   }
			   return horarios;
	}
}
