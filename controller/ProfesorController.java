package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Asesoria;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.model.DosificacionComentario;
import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Instrumento;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.TipoProrroga;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoActivoDTO;
import edu.mx.utdelacosta.model.dto.DosificacionImportarDTO;
import edu.mx.utdelacosta.model.dto.GrupoDTO;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.HorarioDiaDTO;
import edu.mx.utdelacosta.model.dto.ParcialDosificacionDTO;
import edu.mx.utdelacosta.model.dto.PreguntaDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorMateriaProfesorDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorProfesorDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IDiaService;
import edu.mx.utdelacosta.service.IDosificacionComentarioService;
import edu.mx.utdelacosta.service.IDosificacionImportadaService;
import edu.mx.utdelacosta.service.IDosificacionService;
import edu.mx.utdelacosta.service.IEvaluacionesService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IInstrumentoService;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPreguntaService;
import edu.mx.utdelacosta.service.IProrrogaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.IRespuestaCargaEvaluacionService;
import edu.mx.utdelacosta.service.ITestimonioCorteService;
import edu.mx.utdelacosta.service.ITipoProrrogaService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Academia') and hasRole('Director')")
@RequestMapping("/profesor")
public class ProfesorController {
 
	@Autowired
	private ICargaHorariaService cargaService;
	
	@Autowired
	private IProrrogaService prorrogaService;
	
	@Autowired
	private IDosificacionComentarioService dosiComeService;
	
	@Autowired 
	private IPeriodosService periodoService;
	
	@Autowired
	private ICorteEvaluativoService corteService;

	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private IHorarioService horarioService;
	
	@Autowired
	private IInstrumentoService instrumentoService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IUsuariosService usuarioService;
	
	@Autowired
	private IMecanismoInstrumentoService mecanismoService;
	
	@Autowired
	private IDosificacionService dosificacionService;
	
	@Autowired
	private IDiaService diaService;
	
	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;
	
	@Autowired
	private ITestimonioCorteService testimonioCorteService;
	
	@Autowired
	private IGrupoService grupoService;	
	
	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;
	
	@Autowired
	private IPreguntaService servicePreguntas;
	
	@Autowired
	private IEvaluacionesService serviceEvaluacion;
	
	@Autowired
	private ICarrerasServices serviceCarrera;
	
	@Autowired
	private IRespuestaCargaEvaluacionService serviceResCarEva;
	
	@Autowired
	private ITipoProrrogaService tipoProrroService;
	
	@Autowired
	private IDosificacionImportadaService dosiImpoService;

	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";

	@GetMapping("/dosificacion")
	public String dosificacion(Model model, HttpSession session) {
		Integer cveCarga = (Integer) session.getAttribute("cveCarga"); 
		Persona persona = personaService.buscarPorId((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());

		//se obtienen las cargas del profesor
 		List<CargaHoraria> cargas = cargaService.buscarPorProfesorYPeriodo(usuario.getPersona(), new Periodo(usuario.getPreferencias().getIdPeriodo()));
 		
 		if (cveCarga != null) {
 	
			CargaHoraria cargaActual = cargaService.buscarPorIdCarga(cveCarga);
			List<Instrumento> instrumentos = instrumentoService.buscarTodos(); //se obtienen los instrumentos
					
			Integer existeDosi = 0; //se declara la validacion para importar/cargar la dosificacion
			
			List<ParcialDosificacionDTO> parciales = new ArrayList<>();
			List<CorteEvaluativo> cortes = corteService.buscarPorCarreraYPeriodo(cargaActual.getGrupo().getCarrera() ,periodo);
			
			for (CorteEvaluativo corte : cortes) {
				ParcialDosificacionDTO parcial = new ParcialDosificacionDTO();
				parcial.setIdCorteEvaluativo(corte.getId());
				parcial.setConsecutivoCorte(corte.getConsecutivo());
				parcial.setIdCargaHoraria(cveCarga);
				parcial.setDosificacion(dosificacionService.buscarPorIdCargaHorariaEIdCorteEvaluativo(cveCarga, corte.getId()));
				if (parcial.getDosificacion()==null) {
					
					//en caso de que la dosificacion no se encuentre, se procede a buscar alguna dosificacion compartida
					parcial.setDosificacion(dosificacionService.buscarImportadaPorCargaHoraria(cveCarga, corte.getId()));
					if (parcial.getDosificacion()==null) {
						existeDosi = existeDosi +1;
					}
				}
				parcial.setInstrumentos(mecanismoService.buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(cveCarga, corte.getId(), true));
				parciales.add(parcial);
			}
			
			if (existeDosi == 2) {
				List<DosificacionImportarDTO> importar = 
				dosiImpoService.buscarImportarPorMateriaYPeriodo(cargaActual.getMateria().getId(), cveCarga, periodo.getId());
				model.addAttribute("importar", importar);
			}
			
			model.addAttribute("cveCarga", cveCarga);
			model.addAttribute("cortes", cortes);
			model.addAttribute("parciales", parciales);
			model.addAttribute("instrumentos",instrumentos);
			model.addAttribute("cActual",cargaActual);
		}
		
 		//comentarios de dosificaciones
		List<DosificacionComentario> comentarios = dosiComeService.buscarPorIdCargaHoraria(cveCarga); // modal observaciones
		
		model.addAttribute("cargas", cargas);
		model.addAttribute("comentarios", comentarios);
		
		return "profesor/dosificacion";
	}

	@GetMapping("/asistencia")
	public String asistencia(Model model, HttpSession session) {
		Integer cveCarga = (Integer) session.getAttribute("cveCarga"); //se obtiene la carga
		
		//se obtiene el usuario y el periodo
		Persona persona = personaService.buscarPorId((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		
		Date fecha = new Date();
		List<CargaHoraria> cargas = cargaService.buscarPorProfesorYPeriodo(usuario.getPersona(), periodo);
 		
		//se compara si hay una carga seleccionada
 		if (cveCarga != null) {

 			CargaHoraria cargaActual = cargaService.buscarPorIdCarga(cveCarga);
			model.addAttribute("cveGrupo", cargaActual.getGrupo().getId());
			
			// ---------- SE OBTIENE EL DIA DE LA SEMANA DE LA FECHA SELECCIONADA Y SE COMPARA SI ES NULO
			Integer diaSemana = (Integer) session.getAttribute("diaSemana");
			if(diaSemana!=null) {	
			// SE OBTIENEN LAS SESIONES (HORARIOS) EN BASE A LA CARGA Y EL DIA
			List<Horario> horarios = null;
	
 			fecha = (Date)session.getAttribute("fecha");
 			horarios = horarioService.buscarPorIdCargaHorariaEIdDia(cveCarga, diaSemana);

			model.addAttribute("sesionActual",session.getAttribute("sesionActual"));
			model.addAttribute("horarios", horarios);
			}
		}
 		model.addAttribute("fecha",fecha);
		model.addAttribute("cveCarga", cveCarga);
		
		model.addAttribute("cargas", cargas);
		return "profesor/asistencia";
	}

	@GetMapping("/calificar")
	public String calificar(Model model, HttpSession session) {
		Integer cveCarga = (Integer) session.getAttribute("cveCarga");
		Persona persona = personaService.buscarPorId((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		Integer cActual = null;
 		List<CargaHoraria> cargas = cargaService.buscarPorProfesorYPeriodo(usuario.getPersona(), periodo); // se buscan las cargas por profesor y periodo
 		List<Alumno> alumnos = new ArrayList<>(); // se crea la lista de alumnos para enviarse vacia en caso de que no haya ninguno
		List<CorteEvaluativo> cortes = new ArrayList<>();
 		if (cveCarga != null) {
			cActual = cveCarga;
			CargaHoraria carga = cargaService.buscarPorIdCarga(cveCarga); // se crea la carga horaria actual
			alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(carga.getGrupo().getId()); // se buscan los alumnos			
			model.addAttribute("cveCarga", cveCarga); //se envia el id de la carga actual
			model.addAttribute("cargaActual", cActual); // se envia la carga actual
			cortes = corteService.buscarPorCarreraYPeriodo(carga.getGrupo().getCarrera() , carga.getPeriodo()); //modal fechas de entrega
		}
		model.addAttribute("cortes", cortes);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("cargas", cargas);
		return "profesor/calificar";
	}

	@GetMapping("/horario")
	public String horario(Model model, HttpSession session) {
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		   
		   // se obtienen los dias y el formate
		   List<Dia> dias = diaService.buscarDias();
		   DateFormat formatoVista = new SimpleDateFormat("HH:mm");
		   DateFormat formatoBusqueda = new SimpleDateFormat("HH:mm:ss");
		  
		   //se buscan las horas de cada dia del profesor
		   List<Horario> horas = horarioService.buscarPorProfesorDistinctPorHoraInicio(persona.getId(), usuario.getPreferencias().getIdPeriodo());
		   
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
		    	horarioDia = horarioService.buscarPorHoraInicioDiaYProfesor(formatoBusqueda.format(hora.getHoraInicio()), dia.getId(), persona.getId(), usuario.getPreferencias().getIdPeriodo());
		    	diasClase.add(horarioDia);
		    }
		    horario.setHorarios(diasClase);
		    horarios.add(horario);
		   }
		   
		   model.addAttribute("dias", dias);
		   model.addAttribute("horas", horas);
		   model.addAttribute("horarios", horarios);
		return "profesor/horario";
	}

	@GetMapping("/fechaEntrega")
	public String fechaEntrega(Model model, HttpSession session) {
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		List<Carrera> carreras = serviceCarrera.buscarCarrerasPorPersonaYPeriodo(persona.getId(), periodo.getId());
		Carrera carreraActual = null;
		if (carreras.size()>0) {
			for (Carrera carrera : carreras) {
				if (carrera.getId()==usuario.getPreferencias().getIdCarrera()) {
					carreraActual = carrera;
				}
			}
		}
		model.addAttribute("carreraActual", carreraActual!=null ? carreraActual.getId() : carreras.get(0).getId());
		model.addAttribute("carreras", carreras);
		model.addAttribute("cortes", carreraActual!=null ? corteService.buscarPorCarreraYPeriodo(carreraActual,periodo) : corteService.buscarPorCarreraYPeriodo(carreras.get(0) ,periodo));
		return "profesor/fechasEntrega";
	}

	@GetMapping("/asesoria")
	public String asesoria(Model model, HttpSession session) {
		Integer cveCarga = (Integer) session.getAttribute("cveCarga"); 
		Persona persona = personaService.buscarPorId((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<CargaHoraria> cargas = cargaService.buscarPorProfesorYPeriodo(usuario.getPersona(), new Periodo(usuario.getPreferencias().getIdPeriodo())); 
		List<AlumnoActivoDTO> alumnos = new ArrayList<>();
		if (cveCarga != null) {
			CargaHoraria cargaActual = cargaService.buscarPorIdCarga(cveCarga);
			alumnos = alumnoService.buscarAlumnoYEstatusPorGrupo(cargaActual.getGrupo().getId());
			model.addAttribute("cActual", cargaActual);
			model.addAttribute("cveCarga", cargaActual.getId());
		}
		Date fechaActual = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(fechaActual);
		model.addAttribute("fecha", format);
		model.addAttribute("asesoria", new Asesoria());
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("cargas", cargas);
		return "profesor/asesoria";
	}

	@GetMapping("/prorrogas")
	public String prorrogas(Model model, HttpSession session) {
		Persona persona = personaService.buscarPorId((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		CargaHoraria cargaActual = new CargaHoraria();
		List<CorteEvaluativo> cortes = new ArrayList<>();
		try {
			cargaActual = cargaService.buscarPorIdCarga((Integer)session.getAttribute("cveCarga"));
		} catch (Exception e) {
			cargaActual = null;
		}
		if (cargaActual!=null) {
			cortes = corteService.buscarPorCarreraYPeriodo(cargaActual.getGrupo().getCarrera() , cargaActual.getPeriodo());
		}

		List<TipoProrroga> tipos = tipoProrroService.buscarTodos();
		List<CargaHoraria> cargas = cargaService.buscarPorProfesorYPeriodo(usuario.getPersona(), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<Prorroga> prorrogas = prorrogaService.buscarPorProfesorYPeriodoYActivo(persona.getId(), usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cargas", cargas);
		model.addAttribute("tipos", tipos);
		model.addAttribute("prorrogas", prorrogas);
		model.addAttribute("cActual", cargaActual);
		model.addAttribute("cortes", cortes);
		return "profesor/prorroga";
	}

	@GetMapping("/datosContacto")
	public String datosContacto(Model model, HttpSession session) {
		Integer cveCarga = (Integer) session.getAttribute("cveCarga"); 
		Persona persona = personaService.buscarPorId((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<CargaHoraria> cargas = cargaService.buscarPorProfesorYPeriodo(usuario.getPersona(), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<AlumnoActivoDTO> alumnos = new ArrayList<>();
		if (cveCarga != null) {
			CargaHoraria cargaActual = cargaService.buscarPorIdCarga(cveCarga);
			alumnos = alumnoService.buscarAlumnoYEstatusPorGrupo(cargaActual.getGrupo().getId());
			model.addAttribute("cveCarga", cveCarga);
		}
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("cargas", cargas);
		return "profesor/datosContacto";
	}

	@GetMapping("/reportes")
	public String reportes(HttpSession session, Model model) {
		return "profesor/reportes";
	}

	@GetMapping("/reporte-indicadores")
	public String reporteIndicadores(HttpSession session, Model model) {
		int grupoActual;
		Persona persona = new Persona((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodo(persona.getId(), usuario.getPreferencias().getIdPeriodo());
		
		if (session.getAttribute("grupoActual")!=null) {
			grupoActual= (Integer) session.getAttribute("grupoActual");
			List<CargaHoraria> cargasHorarias = cargaService.buscarPorGrupoYProfesorYPeriodo(grupoActual,persona.getId() ,usuario.getPreferencias().getIdPeriodo());
			
			if (session.getAttribute("cargaActual")!=null  && (Integer) session.getAttribute("cargaActual")!=0) {
				//se crea la carga
				CargaHoraria cargaActual = cargaService.buscarPorIdCarga((Integer)session.getAttribute("cargaActual"));
				
				//se crea la lista de alumnos y se obtienen los cortes
				Integer alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(grupoActual).size();
				List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(cargaActual.getGrupo().getCarrera(),new Periodo(usuario.getPreferencias().getIdPeriodo()));
				
				//se crea la lista de indicadores
				List<IndicadorProfesorDTO> indicadoresSD = new ArrayList<>();
				List<IndicadorProfesorDTO> indicadoresRemedial = new ArrayList<>();
				List<IndicadorProfesorDTO> indicadoresExtra = new ArrayList<>();
				Integer totalSD = 0;
				Integer totalRemedial = 0;
				Integer totalExtra = 0;
				Integer numSD = 0;
				Integer numRem = 0;
				Integer numExt = 0;
				
				//se declara la variable a rellenar para la lista
				
				//se itera una lista por cada corte
				for (CorteEvaluativo corte : cortes) {
					
					IndicadorProfesorDTO indicadorExt = new IndicadorProfesorDTO();
					IndicadorProfesorDTO indicadorSD = new IndicadorProfesorDTO();
					IndicadorProfesorDTO indicadorRem = new IndicadorProfesorDTO();

					numSD = testimonioCorteService.contarAlumnoSD(cargaActual.getId(), corte.getId());
					indicadorSD.setNumero(numSD); //se busca el numero de alumnos en SD y se agrega al indicador
					indicadorSD.setPromedio((numSD*100)/alumnos); //se promedia en base al alumno
					totalSD = totalSD+numSD; //se suma el numero de SD para la variable general
					indicadoresSD.add(indicadorSD); //se agrega el indicador a la lista correspondiente
					
					numRem = remedialAlumnoService.contarRemedialesAlumnoPorCargaHorariaYRemedialYCorteEvaluativo(cargaActual.getId(), 1, corte.getId());
					indicadorRem.setNumero(numRem); //se obtiene el numero de remediales					
					indicadorRem.setPromedio((numRem*100)/alumnos); //se promedia					
					totalRemedial =  totalRemedial + numRem; //se suma a la lista general
					indicadoresRemedial.add(indicadorRem); //se agrega a la lista

					
					numExt = remedialAlumnoService.contarRemedialesAlumnoPorCargaHorariaYRemedialYCorteEvaluativo(cargaActual.getId(), 2, corte.getId());
					indicadorExt.setNumero(numExt); //se obtiene el numero de remediales					
					indicadorExt.setPromedio((numExt*100)/alumnos); //se promedia			
					totalExtra = totalExtra + numExt; //se suma a la lista general
					indicadoresExtra.add(indicadorExt); //se agrega a la lista

				}
				
				IndicadorMateriaProfesorDTO indicadores = new IndicadorMateriaProfesorDTO();
				Integer noAlumnos = alumnoService.contarAlumnosInscritosPorGrupoYActivo(cargaActual.getGrupo().getId());
				indicadores.setNumeroAlumnos(noAlumnos);	
				indicadores.setPorcentajeAlumnos((noAlumnos*100)/alumnos);
				
				noAlumnos = alumnoService.contarAlumnosBajasPorGrupoYActivo(cargaActual.getGrupo().getId());
				indicadores.setNumeroBajas(noAlumnos);
				indicadores.setPorcentajeBajas((noAlumnos*100)/alumnos);
								
				noAlumnos = alumnoService.contarAlumnosRegularesPorGrupo(cargaActual.getGrupo().getId()); 
				indicadores.setNumeroRegulares(noAlumnos);
				indicadores.setPorcentajeRegulares((noAlumnos*100)/alumnos);
				
				indicadores.setNumeroSD(totalSD);
				indicadores.setPorcentajeSD((totalSD*100)/alumnos);
				indicadores.setIndicadoresSD(indicadoresSD);
				
				indicadores.setNumeroRemediales(totalRemedial);
				indicadores.setPorcentajeRemediales((totalRemedial*100)/alumnos);
				indicadores.setIndicadoresRemediales(indicadoresRemedial);
				
				indicadores.setNumeroExtra(totalExtra);
				indicadores.setPorcentajeExtra((totalExtra*100)/alumnos);
				indicadores.setIndicadoresExtra(indicadoresExtra);
				
				
				model.addAttribute("indicadores", indicadores);
				model.addAttribute("cargaActual", cargaActual);
				model.addAttribute("cortes", cortes);	
				model.addAttribute("noAlumnos", alumnos);
			}
			model.addAttribute("grupoActual", grupoService.buscarPorId(grupoActual));
			model.addAttribute("cargas", cargasHorarias);
		}
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("grupos", grupos);
		return "profesor/reporteIndicadores";
	}
	
	@GetMapping("/reporte-evaluacion-docente") 
	public String reporteEvaluacionDocente(HttpSession session, Model model) {
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Integer cvePerido = (Integer) session.getAttribute("red-cvePerido");

		Evaluacion evaluacion = serviceEvaluacion.buscar(3);
		int aluEncuestados = 0;

		List<Periodo> periodos = periodoService.buscarLiberados();
		if (cvePerido != null) {
			Persona profesor = personaService.buscarPorId(persona.getId());
			//lista de preguntas 
			List<PreguntaDTO> preguntasDTO = new ArrayList<>();
			//se buscan las preguntas y sus promedio por grupo
			List<Integer> preguntas = servicePreguntas.buscarPorIdEvaluacion(evaluacion.getId());
			//variable para promedio total
			double promedioTotal = 0;
			Integer encuestados = serviceResCarEva.contarAlumnosPorIdProfesorIdPeriodo(persona.getId(), cvePerido);
			model.addAttribute("encuestados", encuestados);
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
				List<Integer> idCargas = cargaService.BuscarPorIdProfesorYIdPeriodo(persona.getId(), cvePerido);
				for(Integer ch: idCargas) {
					//se crea un objeto e grupo
					GrupoDTO grupo = new GrupoDTO();
					CargaHoraria carga = cargaService.buscarPorIdCarga(ch);
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
		}

		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		model.addAttribute("usuario", usuario);
		model.addAttribute("periodos", periodos);
		model.addAttribute("cvePerido", cvePerido);
		model.addAttribute("aluEncuestados", aluEncuestados);
		model.addAttribute("evaluacion", evaluacion);
		return "profesor/reporteEvaluacionDocente";
	}

	@GetMapping("/manual")
	public String manual() {		
		return "profesor/manual";
	}

}
