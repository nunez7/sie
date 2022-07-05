package edu.mx.utdelacosta.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Actividad;
import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Asistencia;
import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.BajaAutorizada;
import edu.mx.utdelacosta.model.CambioGrupo;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;
import edu.mx.utdelacosta.model.PlanEstudio;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.CambiarGrupoDTO;
import edu.mx.utdelacosta.model.dto.CorteEvaluativoDTO;
import edu.mx.utdelacosta.model.dto.DiaDTO;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.MateriaDTO;
import edu.mx.utdelacosta.model.dto.MesDTO;
import edu.mx.utdelacosta.model.dto.ProfesorProrrogaDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoAdeudoDTO;
import edu.mx.utdelacosta.model.dtoreport.DosificacionPendienteDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IActividadService;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsistenciaService;
import edu.mx.utdelacosta.service.IBajaAutorizadaService;
import edu.mx.utdelacosta.service.IBajaService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICambioGrupoService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IDiaService;
import edu.mx.utdelacosta.service.IDosificacionService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPersonalService;
import edu.mx.utdelacosta.service.IPlanEstudioService;
import edu.mx.utdelacosta.service.IProrrogaService;
import edu.mx.utdelacosta.service.ITutoriaIndividualService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@RequestMapping("/director")
public class DirectorController {
	
	@Autowired
	private IPlanEstudioService planEstudioService;
	
	@Autowired
	private ICarrerasServices carrerasServices;
	
	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;
	
	@Autowired
	private IProrrogaService prorrogaService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private ICargaHorariaService cargaHorariaService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IHorarioService horarioService;
	
	@Autowired
	private IPersonalService personalService;
	
	@Autowired
	private IDiaService diaService;
	
	@Autowired
	private IActividadService actividadService;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired 
	private IAsistenciaService serviceAsistencia;
	
	@Autowired
	private ICalificacionMateriaService calificacionMateriaService;
	
	@Autowired
	private IMateriasService materiasService;
	
	@Autowired
	private IAlumnoGrupoService alumnoGrupoService;
	
	@Autowired
	private ICambioGrupoService cambioGrupoService;
	
	@Autowired
	private IDosificacionService dosificacionService;
	
	@Autowired
	private IMecanismoInstrumentoService mecanismoInstrumentoService;
	
	@Autowired
	private IBajaService bajaService;
	
	@Autowired
	private ITutoriaIndividualService tutoriaIndService;
	
	@Autowired
	private IBajaAutorizadaService bajaAutorizaService;
	
	@Autowired
	private EmailSenderService emailService;
	
	@Value("${spring.mail.username}")
	private String correo;
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";
	
	@GetMapping("/dosificacion")
	public String dosificacion(Model model, HttpSession session) {
		//objetos de la persona en sesión 
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona")); 
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera( usuario.getPreferencias().getIdPeriodo(),usuario.getPreferencias().getIdCarrera()); //se tomara de la session la carrera y el periodo
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		int cveGrupo = 0;
		if(session.getAttribute("cveGrupo") != null) {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
			model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
			List<CargaHoraria> cargasHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			model.addAttribute("cargasHorarias", cargasHorarias);
		}
		else {
			model.addAttribute("cveGrupo", cveGrupo); //se retorna el id del grupo seleccionado
		}
		model.addAttribute("grupos",grupos); //retorna los grupos de la carrera
		model.addAttribute("carreras", carreras);
		return "director/dosificacion";
	}

	@GetMapping("/fechas")
	public String fechas(Model model, HttpSession session) {
		//creamos el usuario de acuerdo a la authenticación 
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona")); 
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		//buscamos las carreras de acuerdo a las preferencias y permisos del usuario
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(usuario.getPersona().getId());
		List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(carreras.get(0).getId(), usuario.getPreferencias().getIdPeriodo());
		if(cortes.size() > 0) {
			model.addAttribute("corte1", cortes.get(0));
			model.addAttribute("corte2", cortes.get(1));
		}
		else {
			CorteEvaluativo corte1 = new CorteEvaluativo(); 
			CorteEvaluativo corte2 = new CorteEvaluativo();
			model.addAttribute("corte1", corte1);
			model.addAttribute("corte2", corte2); 
		}
		return "director/fechas";
	}
	
	@GetMapping("/listProfesor")
	public String profesores(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Persona> profesores = personaService.buscarPorPersonaCarreraAndPeriodo(cvePersona, usuario.getPreferencias().getIdPeriodo());
		List<ProfesorProrrogaDTO> profesoresDTO = new ArrayList<ProfesorProrrogaDTO>();
		for(Persona profesor : profesores) {
			ProfesorProrrogaDTO profesorDTO = new ProfesorProrrogaDTO();
			profesorDTO.setIdProfesor(profesor.getId());
			profesorDTO.setNombre(profesor.getNombreCompleto());
			profesorDTO.setProrrogas(prorrogaService.prorrogasPorProfesorAndPeriodo(profesor.getId(), usuario.getPreferencias().getIdPeriodo()));
			profesorDTO.setGrupos(grupoService.gruposPorProfesorYPeriodo(profesor.getId(), usuario.getPreferencias().getIdPeriodo()));
			profesoresDTO.add(profesorDTO);
		}
		model.addAttribute("profesores", profesoresDTO);
		return "director/profesores";
	}
	
	@GetMapping("/listAlumno")
	public String alumnos(Model model, HttpSession session) {
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
		return "director/alumnos";
	}
	
	@GetMapping("/horarios")
	public String horarios(Model model, HttpSession session) {
		//se construye la persona y usuario por la sesion
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Personal> profesores = personalService.buscarProfesores();
		List<CargaHoraria> cargasHorarias = null;
		int cveProfesor = 0;
		if(session.getAttribute("cveProfesor") != null) {
			cveProfesor = (Integer) session.getAttribute("cveProfesor");
			model.addAttribute("cveProfesor", cveProfesor);
			//se construye el objeto del profesor
			Persona profesor = personaService.buscarPorId(cveProfesor);
			//se construye el periodo 
			Periodo periodo =  new Periodo(usuario.getPreferencias().getIdPeriodo());
			//cargas horarias por profesor
			cargasHorarias = cargaHorariaService.buscarPorProfesorYPeriodoYActivo(profesor, periodo, true);
			model.addAttribute("cargasHorarias", cargasHorarias);
			List<Actividad> actividades = actividadService.buscarTodas();
			model.addAttribute("actividades", actividades);
			/////////******** SE HACE EL PROCESO DE DIBUJAR EL HORARIO *******/////////////
			List<Dia> dias = diaService.buscarDias();
			model.addAttribute("dias", dias);
			//List<Horario> cantHoras = horarioService.buscarPorIdProfesor(cveProfesor);
			//formato para horas
			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
			//Se extrae una lista de las horas que ahi asociadas a cada hora de calse con un disting por hora inicio y hora fin				
			List<Horario> horas = horarioService.buscarPorProfesorDistinctPorHoraInicio(cveProfesor, usuario.getPreferencias().getIdPeriodo());
			model.addAttribute("horas", horas);
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
			model.addAttribute("horasDto", horasDto);
		} 
		else {
			model.addAttribute("cveProfesor", cveProfesor);
		}
		model.addAttribute("profesores", profesores);
		return "director/horarios";
	}
	
	@GetMapping("/asistencias")
	public String asistencias(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera( usuario.getPreferencias().getIdPeriodo(),usuario.getPreferencias().getIdCarrera());
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId());
		int cveGrupo = 0;
		int cveParcial = 0;
		int cveCarga = 0;
		List<CargaHoraria> cargasHorarias = null;
		if(session.getAttribute("cveGrupo") != null) {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
			model.addAttribute("cveGrupo", cveGrupo);
			cargasHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			model.addAttribute("cargasHorarias", cargasHorarias);
			List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
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
		model.addAttribute("grupos",grupos); //retorna los grupos de nivel TSU
		model.addAttribute("carreras", carreras);
		return "director/asistencias";
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
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera( usuario.getPreferencias().getIdPeriodo(),usuario.getPreferencias().getIdCarrera());
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
		return "director/calificaciones";
	}
	
	@GetMapping("/planEstudio")
	public String planesEstudio(Model model, HttpSession session) {
		//creamos el usuario de acuerdo a la session de usuario
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona")); 
		PlanEstudio planEstudio =  new PlanEstudio();
		model.addAttribute("planEstudio", planEstudio); //objeto vacio para la cuando se inserte un nuevo plan
		List<PlanEstudio> planesEstudio = planEstudioService.buscarPorPersonaCarrera(persona.getId()); //busca todos los planes de estudio //aqui buscara solo los planes del director
		model.addAttribute("planes", planesEstudio); //retorna los planes de estudio
		List<Carrera> carreras = carrerasServices.buscarCarrerasPorIdPersona(persona.getId()); //busca las carreras de acuerdo al acceso del director
		model.addAttribute("carreras", carreras); //retorna todas las carreras 
		return "director/planesEstudio";
	}
	
	@GetMapping("/prorrogas")
	public String prorrogas(Model model, HttpSession session) {
		//creamos el usuario de acuerdo a la session de usuario
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona")); 
		List<Prorroga> prorrogas = prorrogaService.buscarPorCarreraYPendientes(persona.getId());
		model.addAttribute("prorrogas", prorrogas);
		model.addAttribute("idPersona", persona.getId());
		return "director/prorrogas";
	}
	
	//para cambiar de grupo el alumno
	@PostMapping(path = "/cambiar-grupo", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String cambiarGrupo(@RequestBody CambiarGrupoDTO dto) {
		AlumnoGrupo alumnoGrupo = alumnoGrupoService.buscarPorIdAlumnoYidGrupo(dto.getIdAlumno(), dto.getGrupoAnterior());
		alumnoGrupo.setGrupo(new Grupo(dto.getGrupoNuevo()));
		alumnoGrupoService.guardar(alumnoGrupo);
		//se insertar el registro de cambiosGrupos
		CambioGrupo cambioGrupo = new CambioGrupo();
		cambioGrupo.setIdAlumno(dto.getIdAlumno());
		cambioGrupo.setIdGrupoAnterior(dto.getGrupoAnterior());
		cambioGrupo.setIdGrupoNuevo(dto.getGrupoNuevo());
		cambioGrupo.setEstatus(1);
		cambioGrupo.setFechaAlta(new Date());
		cambioGrupoService.guardar(cambioGrupo);
		return "ok";
	}
	
	@GetMapping("/reportes")
	public String reportes() {
		return "director/reportes";
	}
	
	@GetMapping("/manual")
	public String manual() {
		return "director/manual";
	}
	
	@GetMapping("/imprimir-dosificacion")
	public String imprimirDosificacion(Model model, HttpSession session) {
		Integer idDosificacion = (Integer) session.getAttribute("Dosificacion");
		Integer idCargaHoraria = (Integer) session.getAttribute("DosificacionCarga");
		Dosificacion dosificacion = dosificacionService.buscarPorId(idDosificacion);
		CargaHoraria carga = cargaHorariaService.buscarPorIdCarga(idCargaHoraria);
		int ponderacion = 0;
		List<MecanismoInstrumento> mecanismos = mecanismoInstrumentoService.buscarPorIdCargaHorariaYActivo(idCargaHoraria, true);
		for (MecanismoInstrumento mecanismo : mecanismos) {
			if (mecanismo.getIdCorteEvaluativo() == dosificacion.getIdCorteEvaluativo()) {
				ponderacion = ponderacion + mecanismo.getPonderacion();
			}
		}
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		//para enviar el nombre del director de la carrera
		model.addAttribute("director", persona.getNombreCompleto());
		model.addAttribute("mecanismos", mecanismos);
		model.addAttribute("ponderacion", ponderacion);
		model.addAttribute("ds", dosificacion);
		model.addAttribute("cargaHoraria", carga);
		model.addAttribute("fecha", new Date());
		return "director/impresionDosificacion";
	}
	
	@GetMapping("/dosificaciones-pendientes")
	public String reporteDosificacionesPendientes(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		//cambiar el periodo al del usuario
		List<DosificacionPendienteDTO> dosificaciones = dosificacionService.obtenerPendientesPorPersonaCarreraYPeriodo(cvePersona, usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("dosificaciones", dosificaciones);
		return "director/reporteDosificaciones";
	}
	
	@GetMapping("/reporte-adeudos")
	public String reporteAdedudos(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<AlumnoAdeudoDTO> alumnos = alumnoService.obtenerAlumnosAdeudoPorPersonaCarreraYPeriodo(cvePersona, usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("alumnos", alumnos);
		return "director/reporteAdeudos";
	}	
	
	///////////////////////////////////////////////////////////////////
	//-------------------------Brayan---------------------------------
	//////////////////////////////////////////////////////////////////
	
	@GetMapping("/bajas")
	 public String bajasAlumnos(Model model, HttpSession session) {
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}	
		
		List<Baja> bajas = bajaService.buscarPorPersonaYEstatus(cvePersona, 0);
		model.addAttribute("bajas", bajas);
		return "director/bajas";
	}
	
	@GetMapping("/bajas-cargar-tutorias/{idAlumno}")
	public String cargarAlumnos(@PathVariable(name = "idAlumno", required = false) String idAlumno,  Model model) { 
		if(idAlumno!=null){
			Alumno alumno = alumnoService.buscarPorId(Integer.parseInt(idAlumno));
			List<TutoriaIndividual> tutorias =  tutoriaIndService.buscarUltimas5PorAlumno(alumno);
			// Extrae el último grupo del alumno y se inserta en el modelo del alumno
			Grupo ultimoGrupo = grupoService.buscarUltimoDeAlumno(alumno.getId());
			alumno.setUltimoGrupo(ultimoGrupo);
			model.addAttribute("alumno", alumno);
			model.addAttribute("tutorias", tutorias);
		}
		return "fragments/tutorias :: cargar-tutorias";
	}
	
	@PostMapping(path="/aprobar-baja", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String aprobarBaja(@RequestBody Map<String, String> obj, HttpSession session) {
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Date fechaHoy = new Date();	
		String cveBaja = obj.get("id");		
		if(cveBaja!=null){
			
			Baja baja = bajaService.buscarPorId(Integer.parseInt(cveBaja));
			baja.setEstatus(1);
			baja.setFechaAutorizacion(fechaHoy);
			bajaService.guardar(baja);
			
			BajaAutorizada  bajaAutorizada = new BajaAutorizada();
			bajaAutorizada.setBaja(baja);
			bajaAutorizada.setFechaRegistro(fechaHoy);
			bajaAutorizada.setPersona(new Persona(cvePersona));
			bajaAutorizada.setTipo(1);
			bajaAutorizaService.guardar(bajaAutorizada);
			
			//correo
			Mail mail = new Mail();
			String de = correo;
			//String para = "servicios.escolares@utnay.edu.mx";
			String para = "brayan.bg499@gmail.com";
			mail.setDe(de);
			mail.setPara(new String[] {para});		
			//Email title
			mail.setTitulo("Nueva solicitud de baja.");		
			//Variables a plantilla
			Map<String, Object> variables = new HashMap<>();
			variables.put("titulo", "Solitud de baja del alumn(a) "+baja.getAlumno().getPersona().getNombreCompleto());						
			variables.put("cuerpoCorreo","El director(a) de carrera "+baja.getAlumno().getCarreraInicio().getDirectorCarrera()+" aprobó la solicitud de baja para el alumno con matrícula "+baja.getAlumno().getMatricula()+", diríjase al apartado de bajas en el panel del escolares para rechazar o aprobar la abaja.");
			mail.setVariables(variables);			
			try {							
				emailService.sendEmail(mail);													
			}catch (MessagingException | IOException e) {
				
		  	}
			
			return "ok";
		}
		return "error";
	}
	
	@PostMapping(path="/rechazar-baja", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String rechazarBaja(@RequestBody Map<String, String> obj, HttpSession session) {
		String cveBaja = obj.get("id");	
		String motivo = obj.get("motivo");	
		if(cveBaja!=null){
			Baja baja = bajaService.buscarPorId(Integer.parseInt(cveBaja));
			baja.setEstatus(1);
			bajaService.guardar(baja);
			//correo
			Mail mail = new Mail();
			String de = correo;
			//String para = baja.getPersona().getEmail();
			String para = "brayan.bg499@gmail.com";
			mail.setDe(de);
			mail.setPara(new String[] {para});		
			//Email title
			mail.setTitulo("Rechazo de solicitud de baja.");		
			//Variables a plantilla
			Map<String, Object> variables = new HashMap<>();
			variables.put("titulo", "Baja rechazada por el director de la carrera.");						
			variables.put("cuerpoCorreo","La solicitud de baja fue rechazada por el director de la carrera, debido al siguiente motivo: "+motivo);
			mail.setVariables(variables);			
			try {							
				emailService.sendEmail(mail);													
			}catch (MessagingException | IOException e) {
				return "errorMen";
		  	}
			return "ok";
		}
		return "error";
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
					
		List<Grupo> grupos = grupoService.buscarPorCarreraPeriodoAndPersonaCarrera(cvePersona, usuario.getPreferencias().getIdPeriodo());
		Integer cveGrupo = (Integer) session.getAttribute("rb-cveGrupo");
		List<Baja> bajas = new ArrayList<>();
		
		if(cveGrupo!=null) {
			Boolean GrupoEnPeriodo = grupoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			if(GrupoEnPeriodo==true) {
				bajas = bajaService.buscarPorTipoStatusGrupoYPeriodo(2, 2, cveGrupo, usuario.getPreferencias().getIdPeriodo());
			}
		}
		
		model.addAttribute("rol", 2);
		model.addAttribute("bajas", bajas);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteBajas"; 
	 } 

}
