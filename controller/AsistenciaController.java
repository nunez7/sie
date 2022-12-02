package edu.mx.utdelacosta.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.mx.utdelacosta.model.Asistencia;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.TestimonioCorte;
import edu.mx.utdelacosta.model.TipoProrroga;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoActivoDTO;
import edu.mx.utdelacosta.model.dto.AlumnoResultadoDTO;
import edu.mx.utdelacosta.model.dto.CorteEvaluativoDTO;
import edu.mx.utdelacosta.model.dto.DiaDTO;
import edu.mx.utdelacosta.model.dto.MesDTO;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsistenciaService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IProrrogaService;
import edu.mx.utdelacosta.service.ITestimonioCorteService;
import edu.mx.utdelacosta.service.ITestimonioService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.ActualizarCalificacion;
import edu.mx.utdelacosta.util.ActualizarRemedial;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Director') and hasRole('Asistente')")
@RequestMapping("/asistencia")
public class AsistenciaController {

	@Autowired
	private IAsistenciaService asistenciaService;

	@Autowired
	private ICorteEvaluativoService corteService;

	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private ICargaHorariaService cargaService;
		
	@Autowired
	private IUsuariosService usuarioService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private ICarrerasServices carrerasServices;
	
	@Autowired
	private IHorarioService horarioService;
	
	@Autowired
	private ITestimonioCorteService testimonioCorteService;

	@Autowired
	private ITestimonioService testimonioService;
	
	@Autowired
	private ActualizarCalificacion actualizaCalificacion;
	
	@Autowired
	private IProrrogaService prorrogaService;
	
	@Autowired
	private IPeriodosService periodoService;
	
	@Autowired
	private IAlumnoGrupoService agService;
	
	@Autowired
	private ActualizarRemedial actualizarRemedial;
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";

	@PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarAsistencia(@RequestBody Map<String, String> obj, Model model, HttpSession session) {

		// --------------- PROCESO DE OBTENCION DE DATOS ---------------
		Integer grupo = Integer.parseInt(obj.get("idGrupo"));
		Integer idCargaHoraria = Integer.parseInt(obj.get("idCargaHoraria"));
		CargaHoraria carga = cargaService.buscarPorIdCarga(idCargaHoraria);
		Date fecha = java.sql.Date.valueOf(obj.get("fecha"));
		Date fechaHoy = new Date();
		String valor = null;
		List<Alumno> alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(grupo);
		Horario horario = horarioService.buscarPorId(Integer.parseInt(obj.get("SesionAsistencia")));
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = new Periodo(usuario.getPreferencias().getIdPeriodo());
		// --------------- SE HACE UN CONTEO DEL NUEVO NUMERO DE ASISTENCIAS
		// ---------------

		//se busca al parcial que pertenece la fecha seleccionada 
		CorteEvaluativo corte = 
				corteService.buscarPorFechaInicioYFechaFinYPeriodoYCarrera(fecha, periodo, carga.getGrupo().getCarrera());
		
		// en caso de que el corte sea nulo, se devuleve el mensaje de que la fecha no pertenece a ningun parcial
		if (corte==null) {
			return "limit";
		}
		
		//se guarda el id del corte en una variable para buscar una prorroga para dicho parcial en caso de ser necesaria
		Integer idCorte = corte.getId();
		
		//se busca si existe el corte existente no ha sobrepasado la fecha limite de captura
		corte = corteService.buscarPorCorteYLimiteCaptura(fechaHoy, corte);
		
		
		//se busca una prorroga, en caso de nulo, se envia el mensaje de que el periodo de asistencias ya termino
		if (corte == null) {
			Prorroga prorroga = prorrogaService.buscarPorCargaHorariaYTipoProrrogaYAceptada(carga, new TipoProrroga(2), fechaHoy, new CorteEvaluativo(idCorte));
			if (prorroga != null) {
					corte = prorroga.getCorteEvaluativo();
			} else {
				return "inv";
			}
		}
		
		
		
		Integer noAsistencias = asistenciaService.contarPorFechaInicioYFechaFindYCargaHoraria(corte.getFechaInicio(),
				corte.getFechaFin(), carga.getId());
		
		// --------------- PROCESO DE GUARDADO DE ASISTENCIAS ---------------
		for (Alumno alumno : alumnos) {
			valor = obj.get(alumno.getId().toString());
			if (valor != null) {
				Integer asistenciasTotales = noAsistencias;
				Integer horariosDia = 0;
				horariosDia = horarioService.contarPorCargaHorariaYDia(idCargaHoraria, horario.getDia().getId());				
				Integer asistenciasDia = asistenciaService.contarTotalPorAlumnoYCargaHorariaYFecha(noAsistencias, idCargaHoraria, fechaHoy);
				Asistencia asistencia = asistenciaService.buscarPorFechaYAlumnoYHorario(fecha, alumno, horario);

				if (asistencia == null) {
					if (asistenciasDia <= horariosDia) {
					asistencia = new Asistencia();
					asistencia.setFecha(fecha);
					asistencia.setFechaAlta(fechaHoy);
					asistencia.setHorario(horario);
					asistencia.setAlumno(alumno);
					asistencia.setValor(valor);
					asistenciaService.guardar(asistencia); // guarda el nuevo valor de la asistencia
					asistenciasTotales++;
					}
				} else {
					asistencia.setFechaAlta(fechaHoy);
					asistencia.setValor(valor);
					asistenciaService.guardar(asistencia); // actualiza el valor de la asistencia
				}

				// *************** PROCESO DE CALCULO DEL ESTADO DEL ALUMNO ***************
				List<Asistencia> faltas = asistenciaService.buscarFaltasPorIdAlumnoYIdCargaHoraria(alumno.getId(),
						carga.getId(), corte.getFechaInicio(), corte.getFechaFin());
				List<Asistencia> retardos = asistenciaService.buscarRetardosPorIdAlumnoYIdCargaHoraria(alumno.getId(),
						carga.getId());
				Integer noFaltas = faltas.size() + (retardos.size()/3);
				TestimonioCorte testimonio = testimonioCorteService.buscarPorAlumnoYCargaHorariaYCorteEvaluativo(alumno.getId(),
						carga.getId(), corte.getId());

				boolean SD = false;
				if (noFaltas >= (asistenciasTotales * .15)) {
					SD = true;
					actualizarRemedial.generarPorCalificacionOrdinariaRemedial(alumno.getId(), idCargaHoraria, idCorte);
				}
				
				if (SD==false) {
					actualizarRemedial.eliminarPorCalificacionOrdinariaRemedial(alumno.getId(), idCargaHoraria, idCorte);
				}

				if (testimonio == null) {
					testimonio = new TestimonioCorte();
					testimonio.setAlumno(alumno.getId());
					testimonio.setCargaHoraria(carga.getId());
					testimonio.setCorteEvaluativo(corte.getId());
					testimonio.setEditable(true);
					testimonio.setFechaUltimaEdicion(fechaHoy);
					testimonio.setReprobado(false);
					testimonio.setSinDerecho(SD);
					testimonio.setTestimonio(SD==true ?  testimonioService.buscarPorId(12) : testimonioService.buscarPorId(1));
				} else {
					testimonio.setSinDerecho(SD);
					testimonio.setFechaUltimaEdicion(fechaHoy);
				}
				
				testimonioCorteService.guardar(testimonio);
				
				actualizaCalificacion.actualizaTestimonioCalificacion(alumno.getId(), carga.getId(), corte.getId(), SD);
				
			}

		}
		session.removeAttribute("sesionActual");
		return "ok";
	}
	

	@PostMapping(path = "/obtener-sesion-dia", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String obtenerSesionDia(@RequestBody Map<String, String> obj, Model model, HttpSession session) {
		Persona persona = new Persona((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = new Periodo(usuario.getPreferencias().getIdPeriodo());
		CargaHoraria carga = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cveCarga"));
		
		// --------------- PROCESO DE OBTENCION DE DATOS ---------------
		Date fechaSeleccionada = java.sql.Date.valueOf(obj.get("fecha"));
		Date fechaHoy = new Date();

		if (fechaSeleccionada.after(fechaHoy)) {
			return "max";
		}
		
		//se compara que la fecha pertenezca a algun corte
		CorteEvaluativo corte = corteService.buscarPorFechaInicioYFechaFinYPeriodoYCarrera(
				fechaSeleccionada, periodo, carga.getGrupo().getCarrera());
		
		if (corte==null) {
		}
		
		GregorianCalendar fechaCalendario = new GregorianCalendar();
		fechaCalendario.setTime(fechaSeleccionada);
		Integer diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK) - 1;

		session.setAttribute("diaSemana", diaSemana);
		session.setAttribute("fecha", fechaSeleccionada);
		session.removeAttribute("sesionActual");
		return "ok";
	}


	@GetMapping(path = "/obtener-lista-asistencias/{idHorario}/{fecha}")
	public String obtenerListaAsistencias(@PathVariable(name = "idHorario", required = true) Integer idHorario,
			@PathVariable(name = "fecha", required = true) String fecha, Model model, HttpSession session) {
		CargaHoraria cveCarga = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cveCarga"));
		Date fechaSeleccionda = java.sql.Date.valueOf(fecha);
		List<Asistencia> Asistencias = new ArrayList<>();
		List<AlumnoResultadoDTO> paseDeLista = new ArrayList<>();
		List<Alumno> alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(cveCarga.getGrupo().getId());
		boolean listaPrevia = false;
		if (idHorario != 0) {
			Asistencias = asistenciaService.buscarPorFechaYHorario(fechaSeleccionda, new Horario(idHorario));
			if (Asistencias.size() > 0) {
				for (Alumno alumno : alumnos) {
					AlumnoResultadoDTO infoAlumno = new AlumnoResultadoDTO();
					infoAlumno.setId(alumno.getId());
					infoAlumno.setNombreCompleto(alumno.getPersona().getNombreCompletoPorApellido());
					infoAlumno.setActivo(agService.buscarPorAlumnoYGrupo(alumno, cveCarga.getGrupo()).getActivo());
					for (Asistencia asistencia : Asistencias) {
						if (asistencia.getAlumno().getId().equals(alumno.getId())) {
							infoAlumno.setValor(asistencia.getValor());
						}

					}
					paseDeLista.add(infoAlumno);
					listaPrevia = true;
				}
			} else {
				for (Alumno alumno : alumnos) {
					AlumnoResultadoDTO infoAlumno = new AlumnoResultadoDTO();
					infoAlumno.setId(alumno.getId());
					infoAlumno.setNombreCompleto(alumno.getPersona().getNombreCompletoPorApellido());
					infoAlumno.setActivo(agService.buscarPorAlumnoYGrupo(alumno, cveCarga.getGrupo()).getActivo());
					paseDeLista.add(infoAlumno);
				}
			}
		} else {
			paseDeLista = null;
		}
		model.addAttribute("alumnos", paseDeLista);
		model.addAttribute("listaPrevia", listaPrevia);
		return "fragments/asistencias:: listaAsistencias";
	}

	@GetMapping("/reporte-profesor")
	public String verReporteProfesor(Model model, HttpSession session) {
		//se crean las variables
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		
		//se crea la lista de grupos
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodo(persona.getId(), usuario.getPreferencias().getIdPeriodo());
		
		int cveGrupo = 0;
		int cveParcial = 0;
		
		  //se compara si el grupo es o no vacio
		if (session.getAttribute("grupoActual")!=null) {
			cveGrupo = (Integer) session.getAttribute("grupoActual");
			
			//se obtienen las cargas (materias) del maestro
			List<CargaHoraria> cargasHorarias = null;	
			cargasHorarias = cargaService.buscarPorGrupoYProfesorYPeriodo(cveGrupo, persona.getId() ,usuario.getPreferencias().getIdPeriodo());
			List<AlumnoActivoDTO> alumnos = alumnoService.buscarAlumnoYEstatusPorGrupo(cveGrupo);
			if (session.getAttribute("cargaActual")!=null && (Integer) session.getAttribute("cargaActual")!=0) {
				CargaHoraria cveCarga = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cargaActual"));
				
				List<CorteEvaluativo> cortesEvaluativos = corteService.buscarPorCarreraYPeriodo(cveCarga.getGrupo().getCarrera(),new Periodo(usuario.getPreferencias().getIdPeriodo()));
				if (session.getAttribute("corteActual")!=null) {
					cveParcial = (Integer) session.getAttribute("corteActual");
					if (cveParcial > 0 && cveCarga.getId() > 0) {
						CorteEvaluativo corte = corteService.buscarPorId(cveParcial);
						
						CorteEvaluativoDTO cortesEvaluativosDTO = new CorteEvaluativoDTO();
						cortesEvaluativosDTO.setIdCorte(corte.getId());
						cortesEvaluativosDTO.setFechaInicio(corte.getFechaInicio());
						cortesEvaluativosDTO.setFechaFin(corte.getFechaFin());
						cortesEvaluativosDTO.setConsecutivo(corte.getConsecutivo());

						List<Date> meses = asistenciaService.mesesEntreFechaInicioYFechaFinAsc(corte.getFechaInicio(),
								corte.getFechaFin());
						List<Asistencia> asistencias = asistenciaService.buscarPorFechaInicioYFechaFinEIdCargaHorariaEIdGrupo(
								corte.getFechaInicio(), corte.getFechaFin(), cveCarga.getId(), cveGrupo);
						model.addAttribute("asistencias", asistencias);

						List<MesDTO> mesesDto = new ArrayList<>();
						for (Date mes : meses) {

							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
						    SimpleDateFormat sdfM=new SimpleDateFormat("MMMM"); 
						    
						    //Fecha       
						    Calendar fecha = Calendar.getInstance();  
						    fecha.setTime(mes); 
						          
						    // Obtenga el primer día del mes actual: 
						    fecha.add(Calendar.MONTH, 0); 
						    fecha.set (Calendar.DAY_OF_MONTH, 1); // Establecido en el 1, la fecha actual es el primer día del mes  
						    String primerDia = sdf.format(fecha.getTime());  
						          
						    //extraigo el primer dia del mes lo guardo como string y le doy formato de mes para el dto                
						    String mesLetra = sdfM.format(fecha.getTime()); 
						       
						    // Obtener el último día del mes actual           
						    fecha.set(Calendar.DAY_OF_MONTH, fecha.getActualMaximum(Calendar.DAY_OF_MONTH));   
						    String ultimoDia = sdf.format(fecha.getTime()); 
						          
						    MesDTO mesDTO = new MesDTO(); 
						    mesDTO.setMes(mesLetra);       
						    mesesDto.add(mesDTO); 
						    cortesEvaluativosDTO.setMeses(mesesDto);
							

							List<Date> dias = asistenciaService.diasEntreFechaInicioYFechaFin(primerDia, ultimoDia);

							List<DiaDTO> diasDto = new ArrayList<>();
							for (Date dia : dias) {
								DiaDTO diaDto = new DiaDTO();
								diaDto.setDia(dia);
								diasDto.add(diaDto);
								mesDTO.setDias(diasDto);
							}
						}
						model.addAttribute("corteActual", cveParcial);
						model.addAttribute("corte", cortesEvaluativosDTO);
					}
				}
				model.addAttribute("cortes", cortesEvaluativos);
				
				model.addAttribute("cargaActual",cveCarga);
			}
			
			model.addAttribute("cargasHorarias", cargasHorarias);
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("grupoActual", grupoService.buscarPorId(cveGrupo));
		} else {
			model.addAttribute("cveGrupo", cveGrupo); // se retorna el id del grupo seleccionado
		}
		
		// lista de cortesEvalutivos
		model.addAttribute("grupos", grupos); // retorna los grupos de nivel TSU
		model.addAttribute("utName", NOMBRE_UT);
		return "profesor/reporteAsistencias";
	}
	
	@PostMapping(path = "/obtener-asistencia", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String ObtenerAsistenciasSesion(@RequestBody Map<String, String> obj, Model model, HttpSession session) {
		Date fecha = java.sql.Date.valueOf(obj.get("fecha"));
		Integer idHorario = Integer.parseInt(obj.get("sesionAsistencia"));
		List<Asistencia> paseListaActual = new ArrayList<>();
		if (idHorario != 0) {
			paseListaActual = asistenciaService.buscarPorFechaYHorario(fecha, horarioService.buscarPorId(idHorario));
			if (paseListaActual.size() < 1) {
				paseListaActual = null;
			}
		} else {
			paseListaActual = null;
		}
		session.setAttribute("paseDeLista", paseListaActual);
		session.setAttribute("sesionActual", idHorario);
		return "ok";

	}
	
	@GetMapping("/reporte-director-asistente")
	public String verReporteDirector(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<CargaHoraria> cargasHorarias = null;
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera);
			int cveGrupo = 0;
			model.addAttribute("grupos",grupos); 
			model.addAttribute("cveCarrera", cveCarrera);
			if(session.getAttribute("cveGrupo") != null) {
				int cveParcial = 0;
				int cveCarga = 0;
				cveGrupo = (Integer) session.getAttribute("cveGrupo");
				model.addAttribute("grupoActual", grupoService.buscarPorId(cveGrupo));
				model.addAttribute("cveGrupo", cveGrupo);
				Grupo grupo = grupoService.buscarPorId(cveGrupo);
				cargasHorarias = cargaService.buscarPorGrupoYPeriodoYCalificacionSi(cveGrupo, grupo.getPeriodo().getId());
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
					CorteEvaluativo corte = corteService.buscarPorId(cveParcial);
					CorteEvaluativoDTO cortesEvaluativosDTO = new CorteEvaluativoDTO();
					cortesEvaluativosDTO.setIdCorte(corte.getId());
					cortesEvaluativosDTO.setFechaInicio(corte.getFechaInicio());
					cortesEvaluativosDTO.setFechaFin(corte.getFechaFin());
					cortesEvaluativosDTO.setConsecutivo(corte.getConsecutivo());			
					
					List<Date> meses = asistenciaService.mesesEntreFechaInicioYFechaFinAsc(corte.getFechaInicio(), corte.getFechaFin());
					List<Asistencia> asistencias = asistenciaService.buscarPorFechaInicioYFechaFinEIdCargaHorariaEIdGrupo(corte.getFechaInicio(), corte.getFechaFin(), cveCarga, cveGrupo);
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
						fecha.set(Calendar.DAY_OF_MONTH, 1); // Establecido en el 1, la fecha actual es el primer día
																// del mes
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
						
						List<Date> dias = asistenciaService.diasEntreFechaInicioYFechaFin(primerDia, ultimoDia);							 
						 
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
		}
		
		// lista de cortesEvalutivos
		List<CorteEvaluativo> cortesEvaluativos = corteService.buscarPorCarreraYPeriodo(usuario.getPreferencias().getIdCarrera(), usuario.getPreferencias().getIdPeriodo());
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cuatrimestre", periodo.getNombre());
		model.addAttribute("cortes", cortesEvaluativos);
		model.addAttribute("carreras", carrerasServices.buscarCarrerasPorIdPersona(persona.getId()));
		model.addAttribute("nombreUT", NOMBRE_UT);
		return "asistente/reporteAsistencias";
	}
}