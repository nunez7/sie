package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.Date;
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
import edu.mx.utdelacosta.model.CalificacionCorte;
import edu.mx.utdelacosta.model.CalificacionMateria;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.model.Testimonio;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoActivoDTO;
import edu.mx.utdelacosta.model.dto.AlumnoCalificacionDTO;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.CalificacionDTO;
import edu.mx.utdelacosta.model.dto.MateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.CalificacionInstrumentoDTO;
import edu.mx.utdelacosta.model.dtoreport.CalificacionParcialDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsistenciaService;
import edu.mx.utdelacosta.service.ICalendarioEvaluacionService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICalificacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IProrrogaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.ITestimonioCorteService;
import edu.mx.utdelacosta.service.ITestimonioService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.ActualizarCalificacion;
import edu.mx.utdelacosta.util.ActualizarRemedial;

@Controller
@RequestMapping("/calificacion")
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Director') and hasRole('Asistente')")
public class CalificacionController {

	@Autowired
	private ICargaHorariaService cargaService;

	@Autowired
	private IMecanismoInstrumentoService mecanismoService;

	@Autowired
	private ICalificacionService calificacionService;

	@Autowired
	private ICalificacionCorteService calificacionCorteService;

	@Autowired
	private IGrupoService grupoService;

	@Autowired
	private IMateriasService materiasService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private ICalificacionMateriaService calificacionMateriaService;

	@Autowired
	private ICarrerasServices carrerasServices;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IUsuariosService usuariosService;

	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;

	@Autowired
	private IProrrogaService prorrogaService;

	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;

	@Autowired
	private ICalendarioEvaluacionService calendarioService;
	
	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private ITestimonioService testimonioService;
	
	@Autowired
	private IPeriodosService periodoService;
	
	@Autowired
	private IAsistenciaService asistenciaService;
		
	@Autowired
	private ActualizarCalificacion actualizarCalificacion;
	
	@Autowired
	private ActualizarRemedial actualizarRemedial;
	
	@Autowired
	private ITestimonioCorteService testimonioCorteService;
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";

	// metodo que devuelve la tabla de calificaciones e instrumentos de una materia
	// y corte seleccionado
	@GetMapping("/cargar/{idCarga}/{idCorte}")
	public String cargarCalificaciones(@PathVariable(name = "idCarga", required = true) Integer idCarga,
			@PathVariable(name = "idCorte", required = true) Integer idCorte, Model model) {
		CorteEvaluativo corteActual = new CorteEvaluativo(idCorte);
		CargaHoraria cargaActual = cargaService.buscarPorIdCarga(idCarga);

		// se valida que haya calendarios (fechas de entrega) registradas de parte del profesor
		Integer calendarios = calendarioService.contarPorCargaHorariaYCorteEvaluativo(idCarga, idCorte);
		if (calendarios > 0) {

			//se obtiene la lista de alumnos del grupo
			List<AlumnoActivoDTO> alumnos = alumnoService.buscarAlumnoYEstatusPorGrupo(cargaActual.getGrupo().getId());
			
			// se consulta si ya hay al menos 1 calificacion 
			if(calificacionCorteService.contarCalificacionesPorCargaHorariaYCorteEvaluativo(cargaActual, corteActual)==0){
			
				// en caso de que no haya calificaciones, se procede a crear las calificaciones de los instrumentos para todo el parcial
					actualizarCalificacion.crearCalificacionesParcial(alumnos, cargaActual, corteActual);
			}
			
			List<MecanismoInstrumento> mecanismos = mecanismoService
					.buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(cargaActual.getId(), corteActual.getId(), true);
			List<CalificacionCorte> calificacionesCortes = calificacionCorteService
					.buscarPorCargaHorariaYCorteEvaluativo(cargaActual, corteActual);
			List<AlumnoCalificacionDTO> alCalCorte = new ArrayList<>();

			for (AlumnoActivoDTO alumno : alumnos) {
				AlumnoCalificacionDTO al = new AlumnoCalificacionDTO();
				al.setId(alumno.getIdAlumno());
				al.setNombre(alumno.getNombre());
				al.setActivo(alumno.getActivo());

				List<CalificacionDTO> calificaciones = new ArrayList<>();
				for (MecanismoInstrumento mecanismo : mecanismos) {
					CalificacionDTO c = new CalificacionDTO();
					Float valor = calificacionService.buscarCalificacionPorAlumnoYMecanismoInstrumento(alumno.getIdAlumno(),
							mecanismo.getId());
					c.setIdMecanismo(mecanismo.getId());
					c.setValor(valor);
					calificaciones.add(c);
				}

				al.setCalificaciones(calificaciones);

				Integer editable = calificacionCorteService.buscarRevalidadaPorAlumnoYCargaHorariaYCorteEvaluativo(alumno.getIdAlumno(), idCarga, idCorte);
				al.setRevalidada(editable!=null ? editable : 1);
				
				Integer status = testimonioCorteService.validarSDPorAlumnoYCargaHorariaYCorteEvaluativo(alumno.getIdAlumno(), idCarga, idCorte);
				al.setStatus(status!=null ? status.toString() : "0");
				
				al.setCalificacionTotal(calificacionCorteService.buscarPorAlumnoCargaHorariaYCorteEvaluativo(alumno.getIdAlumno(), idCarga, idCorte).floatValue());
				alCalCorte.add(al);
				
				// se buscan los remediales y extraordinarios por alumno 
				al.setRemedial(remedialAlumnoService.exsisteRemedialAlumno(alumno.getIdAlumno(), idCarga, idCorte, 1));
				al.setExtraordinario(remedialAlumnoService.exsisteRemedialAlumno(alumno.getIdAlumno(), idCarga, idCorte, 2));
			}
			
			Boolean corteValido =  corteEvaluativoService.buscarParcialConPlazoValido(new Date(), idCorte);
			if (corteValido == false) {
				corteValido = prorrogaService.existeDeCalificacionPorFechaYCargaHorariaYCorte(new Date(), idCarga, idCorte);
			}
			
			model.addAttribute("corteValido", corteValido);
			model.addAttribute("mecanismos", mecanismos);
			model.addAttribute("corteValido", corteValido);
			model.addAttribute("cActual", cargaActual.getId());
			model.addAttribute("alumnos", alCalCorte);
			model.addAttribute("calCorte", calificacionesCortes);
		}
		return "fragments/modal-calificar:: listaCalificacion";
	}

	@PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarCalificacion(@RequestBody Map<String, String> obj, HttpSession session) {
		// se obtienen las variables
		Integer idCorteEvaluativo = Integer.parseInt(obj.get("idCorteEvaluativo"));
		Integer idCargaHoraria = Integer.parseInt(obj.get("idCargaHoraria"));
		Integer idAlumno = Integer.parseInt(obj.get("idAlumno"));
		Integer idMecanismo = Integer.parseInt(obj.get("idInstrumento"));
		
		// se valida que el profesor haya capturado al menos una asistencia del parcial
		if (asistenciaService.contarAsistenciasPorCargaYCorte(new CargaHoraria(idCargaHoraria), new CorteEvaluativo(idCorteEvaluativo))==0) {
			return "noAsistencia";
		}
		
		// se compara que el alumno no tenga un remedial pagado para eviatar editar la calificacion
		RemedialAlumno remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
				new Alumno(idAlumno), new CargaHoraria(idCargaHoraria), new Remedial(1),
				new CorteEvaluativo(idCorteEvaluativo));
		
		if (remedial != null && remedial.getTestimonio()!=null) {
			return "forbidden";
		}

		// se comprueba que la ponderacion sea un numero valido 
		Float ponderacion;
		try {
			 ponderacion = Float.parseFloat(obj.get("valor"));
		} catch (Exception e) {
			return "notNum";
		}

		// se obtiene el mecanismo del alumno
		MecanismoInstrumento mecanismo = mecanismoService.buscarPorIdYActivo(idMecanismo, true); //
		// se actualiza la calificacion del instrumento en singular
		actualizarCalificacion.actualizaCalificacionInstrumento(idAlumno, mecanismo, ponderacion, idCargaHoraria, idCorteEvaluativo);


		// se actualiza la calificacion del corte y se obtiene la calificacion total del corte
		float calificacionTotal = actualizarCalificacion.actualizaCalificacionCorte(idAlumno, idCargaHoraria,
				idCorteEvaluativo);
		
		if (calificacionTotal <7.5) {
			actualizarRemedial.generarPorCalificacionOrdinariaRemedial(idAlumno, idCargaHoraria, idCorteEvaluativo);
		}else {
			actualizarRemedial.eliminarPorCalificacionOrdinariaRemedial(idAlumno, idCargaHoraria, idCorteEvaluativo);
		}

		// se actualiza el testimonio del corte
		actualizarCalificacion.actualizaTestimonioCorte(idAlumno, calificacionTotal, idCargaHoraria, idCorteEvaluativo);

		String resultado = actualizarCalificacion.actualizaCalificacionMateria(idAlumno, idCargaHoraria);

		return resultado + (calificacionTotal);

	}

	// reporte

	@GetMapping("/reporte-parcial-profesor")
	public String reporteCalificaciones(Model model, HttpSession session) {
		int grupoActual;
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodo(persona.getId(),
				usuario.getPreferencias().getIdPeriodo());

		if (session.getAttribute("grupoActual") != null) {
			grupoActual = (Integer) session.getAttribute("grupoActual");
			List<CargaHoraria> cargasHorarias = cargaService.buscarPorGrupoYProfesorYPeriodo(grupoActual,persona.getId() ,usuario.getPreferencias().getIdPeriodo());

			if (session.getAttribute("cargaActual") != null  && (Integer) session.getAttribute("cargaActual")!=0) {
				CargaHoraria cargaActual = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cargaActual"));

				List<CorteEvaluativo> cortes = corteEvaluativoService
						.buscarPorCarreraYPeriodo(cargaActual.getGrupo().getCarrera(),new Periodo(usuario.getPreferencias().getIdPeriodo()));
				if (session.getAttribute("parcialActual") != null) {
					int parcialActual = (Integer) session.getAttribute("parcialActual");

					if (parcialActual > 0) {
						List<AlumnoActivoDTO> alumnos = alumnoService.buscarAlumnoYEstatusPorGrupo(grupoActual);
						List<MecanismoInstrumento> mecanismoInstrumento = mecanismoService
								.buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(cargaActual.getId(), parcialActual, true);
						List<CalificacionParcialDTO> calificaciones = new ArrayList<>();
						for (AlumnoActivoDTO alumno : alumnos) {
							CalificacionParcialDTO calificacion = new CalificacionParcialDTO();
							calificacion.setMatricula(alumno.getMatricula());
							calificacion.setNombre(alumno.getNombre());
							calificacion.setBaja(!alumno.getActivo());
							
							List<CalificacionInstrumentoDTO> mecanismos = new ArrayList<>();
							for (MecanismoInstrumento meca : mecanismoInstrumento) {
								CalificacionInstrumentoDTO cali = calificacionService.buscarPorAlumnoYMecanismoInstrumentoActivo(new Alumno(alumno.getIdAlumno()), meca);
								mecanismos.add(cali);
							}
							
							calificacion.setMecanismos(mecanismos);
							RemedialAlumno rem = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
									new Alumno(alumno.getIdAlumno()), cargaActual, new Remedial(1),
									new CorteEvaluativo(parcialActual));
							if (rem != null) {
								calificacion.setCalificacionRemedial(rem.getTestimonio()!=null ? rem.getTestimonio().getNumero() : 0);
							} else {
								calificacion.setCalificacionRemedial(null);
							}
							RemedialAlumno ex = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
									new Alumno(alumno.getIdAlumno()), cargaActual, new Remedial(2),
									new CorteEvaluativo(parcialActual));
							if (ex != null) {
								calificacion.setCalificacionExtraordinario(ex.getTestimonio().getNumero());
							} else {
								calificacion.setCalificacionExtraordinario(null);
							}
							if (rem!=null || ex!=null) {
								Float sum = 0f;
								for (CalificacionInstrumentoDTO m : mecanismos) {
									sum = sum + m.getPonderacion();
								}
								sum = sum / mecanismos.size();
								calificacion.setCalificacionOrdinaria(sum);
							}else {								
							calificacion.setCalificacionOrdinaria(calificacionCorteService
									.buscarPorAlumnoCargaHorariaYCorteEvaluativo(alumno.getIdAlumno(),
											cargaActual.getId(), parcialActual).floatValue());
							}
							
							boolean bajaRem = false;
							if (remedialAlumnoService.contarPorAlumnoYPeriodoYTipo(alumno.getIdAlumno(), cargaActual.getPeriodo().getId(), 1)>=5) {
								bajaRem = true;
							}
							if (remedialAlumnoService.contarPorAlumnoYPeriodoYTipo(alumno.getIdAlumno(), cargaActual.getPeriodo().getId(), 2)>=3) {
								bajaRem = true;
							}
							calificacion.setBajaRem(bajaRem);
							calificaciones.add(calificacion);
						}
						model.addAttribute("alumnos", calificaciones);
						model.addAttribute("instrumentos", mecanismoInstrumento);
					}

					model.addAttribute("parcialActual", parcialActual);
				}
				model.addAttribute("cargaActual", cargaActual);
				model.addAttribute("cortes", cortes);
			}
			model.addAttribute("grupoActual", grupoService.buscarPorId(grupoActual));
			model.addAttribute("cargas", cargasHorarias);
		}
		model.addAttribute("cuatrimestre", periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo()).getNombre());
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("grupos", grupos);
		return "profesor/reporteCalificaciones";
	}
	
	@GetMapping("/reporte-seguimiento-profesor")
	public String reporteSeguimiento(Model model, HttpSession session) {
		int grupoActual;
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodo(persona.getId(),
				usuario.getPreferencias().getIdPeriodo());

		if (session.getAttribute("grupoActual") != null) {
			grupoActual = (Integer) session.getAttribute("grupoActual");
			List<CargaHoraria> cargasHorarias = cargaService.buscarPorGrupoYProfesorYPeriodo(grupoActual,persona.getId() ,usuario.getPreferencias().getIdPeriodo());

			if (session.getAttribute("cargaActual") != null && (Integer) session.getAttribute("cargaActual") > 0) {
				CargaHoraria cargaActual = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cargaActual"));
				List<CorteEvaluativo> cortes = corteEvaluativoService
						.buscarPorCarreraYPeriodo(cargaActual.getGrupo().getCarrera(),new Periodo(usuario.getPreferencias().getIdPeriodo()));

				List<AlumnoActivoDTO> alumnos = alumnoService.buscarAlumnoYEstatusPorGrupo(grupoActual);
				List<AlumnoCalificacionDTO> calificaciones = new ArrayList<>();
				for (AlumnoActivoDTO alumno : alumnos) {
					AlumnoCalificacionDTO calificacion = new AlumnoCalificacionDTO();
					calificacion.setMatricula(alumno.getMatricula());
					calificacion.setNombre(alumno.getNombre());
					calificacion.setActivo(alumno.getActivo());
					
					List<CalificacionDTO> cal = new ArrayList<>();
					for (CorteEvaluativo corte : cortes) {
						CalificacionDTO cali = new CalificacionDTO();
						cali.setCaliCorte(
								calificacionCorteService.buscarPorAlumnoCargaHorariaYCorteEvaluativo(
										alumno.getIdAlumno(), cargaActual.getId(), corte.getId()).floatValue());
						RemedialAlumno remedial = remedialAlumnoService
								.buscarUltimoPorAlumnoYCargaHorariaYCorteEvaluativo(alumno.getIdAlumno(), cargaActual.getId(),
										corte.getId());
						if (remedial != null) {
							if (remedial.getRemedial().getId() == 1) {
								cali.setStatus("R");
							} else {
								cali.setStatus("E");
							}
						} else {
							cali.setStatus("O");
						}
						cal.add(cali);
					}
					calificacion.setCalificaciones(cal);
					CalificacionMateria cm = calificacionMateriaService.buscarPorCargayAlumno(cargaActual, new Alumno(alumno.getIdAlumno()));
					if (cm != null) {
						calificacion.setCalificacionTotal(cm.getCalificacion());
						calificacion.setStatus(cm.getEstatus());
					} else {
						calificacion.setCalificacionTotal(0);
						calificacion.setStatus("NA");
					}
					calificaciones.add(calificacion);
				}

				model.addAttribute("alumnos", calificaciones);

				model.addAttribute("cargaActual", cargaActual);
				model.addAttribute("cortes", cortes);
			}
			model.addAttribute("grupoActual", grupoService.buscarPorId(grupoActual));
			model.addAttribute("cargas", cargasHorarias);
		}
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("grupos", grupos);
		return "profesor/reporteSeguimiento";
	}

	@GetMapping("/reporte-director-asistente")
	public String calificaciones(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		int cveGrupo = 0;
		List<Materia> materias = new ArrayList<Materia>();
		if (session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			model.addAttribute("grupos",
					grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera));

			model.addAttribute("cveCarrera", cveCarrera);
			if (session.getAttribute("cveGrupo") != null) {
				cveGrupo = (Integer) session.getAttribute("cveGrupo");
				materias = materiasService.buscarPorCargaActivaEnGrupo(cveGrupo,
						usuario.getPreferencias().getIdCarrera());
				model.addAttribute("grupoActual", grupoService.buscarPorId(cveGrupo));
				List<AlumnoDTO> alumnos = new ArrayList<AlumnoDTO>();
				if (!materias.isEmpty()) {
					AlumnoDTO alumno;
					List<MateriaDTO> materiasDT;
					for (Alumno al : alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(cveGrupo)) {
						alumno = new AlumnoDTO();
						alumno.setIdAlumno(al.getId());
						alumno.setNombre(al.getPersona().getNombreCompletoPorApellido());
						alumno.setMatricula(al.getMatricula());
						// Construimos las materias
						materiasDT = new ArrayList<MateriaDTO>();
						for (MateriaPromedioDTO cm : calificacionMateriaService.buscarPorGrupoAlumno(cveGrupo,
								al.getId())) {
							// Agregamos todos los promedios de las materias del alumno
							MateriaDTO mNew = new MateriaDTO();
							mNew.setPromedio(cm.getCalificacion());
							mNew.setEstatusPromedio(cm.getEstatus());
							materiasDT.add(mNew);
						}
						// Agregamos las materias al alumno
						alumno.setMaterias(materiasDT);
						alumnos.add(alumno);
					}
				}
				model.addAttribute("cveGrupo", cveGrupo);
				model.addAttribute("alumnos", alumnos);
			}
		}
		model.addAttribute("materias", materias);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("carreras", carrerasServices.buscarCarrerasPorIdPersona(persona.getId()));
		return "asistente/reporteCalificaciones";
	}
	
	//Metodos de control de alumnos
	
	@GetMapping("/ver-cm-alumno-escolares/{idCarga}")
	public String verCalificacionMateriaAlumnoEscolares(@PathVariable(name = "idCarga", required = true) Integer idCarga, Model model, HttpSession session) {
		
		//se declaran las variables
		Alumno alumno = new Alumno ((Integer) session.getAttribute("cveAlumno"));
		CargaHoraria carga = cargaService.buscarPorIdCarga(idCarga);
		
		//se crea la calificacion materia
		List<CalificacionCorte> calificacionesCorte = calificacionCorteService.buscarPorCargaYAlumno(carga, alumno);
		for (CalificacionCorte cc : calificacionesCorte) {
			model.addAttribute("calificacion"+cc.getCorteEvaluativo().getConsecutivo(), Math.round(cc.getValor()));
		}
		
		//se buscan los testimonios y cortes
		List<Testimonio> testimonios = testimonioService.buscarTodosPorIntegradora(carga.getMateria().getIntegradora());
		
		List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(carga.getGrupo().getCarrera(), carga.getPeriodo());
		
		//List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(carga.getGrupo().getCarrera(), periodo);
		
		for (CorteEvaluativo corte : cortes) {
			RemedialAlumno remedial =  remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(alumno,carga, new Remedial(1), corte);
			if (remedial!=null) {				
				model.addAttribute("remedial"+corte.getConsecutivo(), remedial);
			}
			
			RemedialAlumno extra =  remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(alumno,carga, new Remedial(2), corte);
			if (extra!=null) {				
				model.addAttribute("extra"+corte.getConsecutivo(), extra);
			}
		}
		
		
		//se determina si la materia tiene 1 o mas cortes activos y se envia la lista/corte
		Integer noUnidades = carga.getMateria().getUnidadesTematicas().size();		
		if (noUnidades==1) {
			model.addAttribute("corteUnico", true);
		}
		
		model.addAttribute("calificaciones", calificacionesCorte);
		model.addAttribute("cargaHoraria", carga.getId());
		model.addAttribute("testimonios", testimonios);
		
		return "fragments/control-alumnos :: calificacion-escolares";
	}
	
	@PostMapping("/modificar-cm-alumno-escolares")
	@ResponseBody
	public String modificarCalificacionMateriaAlumnoEscolares(@RequestBody Map <String, String> obj, Model model, HttpSession session) {
		
		Integer idAlumno = (Integer)session.getAttribute("cveAlumno");
		Alumno alumno = alumnoService.buscarPorId(idAlumno);
		CargaHoraria carga = cargaService.buscarPorIdCarga((Integer.valueOf(obj.get("cargaHoraria"))));
		
		String revalidado =  (obj.get("revalidado"));
		boolean editable = true;
		if (revalidado!=null && revalidado.equals("on")) {
			editable = false;
		}
		
		List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(carga.getGrupo().getCarrera(), carga.getPeriodo());
		
			
		for (CorteEvaluativo corte : cortes) {
			Integer ordinario = 0;
			Integer testimonioRemedial = 0;
			Integer testimonioExtra = 0;
			
			if (obj.get("extra"+corte.getConsecutivo())!=null && Integer.valueOf(obj.get("extra"+corte.getConsecutivo()))>0) {
				testimonioExtra = Integer.valueOf(obj.get("extra"+corte.getConsecutivo())); 
				Testimonio testimonio = testimonioService.buscarPorId(testimonioExtra);
				String resultado = actualizarRemedial.remedialAlumno(alumno, carga, corte, testimonio, new Remedial(2));
				if (resultado.equals("noExt")) {
					return resultado;
				}


			}else if (obj.get("remedial"+corte.getConsecutivo())!=null && Integer.valueOf(obj.get("remedial"+corte.getConsecutivo()))>0) {
				testimonioRemedial = Integer.valueOf(obj.get("remedial"+corte.getConsecutivo())); 
				Testimonio testimonio = testimonioService.buscarPorId(testimonioRemedial);
				String resultado = actualizarRemedial.remedialAlumno(alumno, carga, corte, testimonio, new Remedial(1));
				if (resultado.equals("noRem")) {
					return resultado;
					
				}
				
			}else if (obj.get("ordinario"+corte.getConsecutivo())!=null && Integer.valueOf(obj.get("ordinario"+corte.getConsecutivo()))>0) {
				ordinario = Integer.valueOf(obj.get("ordinario"+corte.getConsecutivo()));
				//se actualiza la calificacion
				actualizarCalificacion.actualizaCalificacionCorteEscolares(alumno, carga, corte, ordinario-1, editable);
				
			}
			
		}
		
		return "ok";
	}
	
	@GetMapping("/calificacion-alumno-grupo-escolares/{grupo}")
	public String calificacionAlumnoGrupoEscolares(@PathVariable(name = "grupo", required = true) Integer idGrupo, 
			Model model, HttpSession session) {
		
		Alumno alumno = alumnoService.buscarPorId((Integer) session.getAttribute("cveAlumno"));
		
		
		Grupo grupo = grupoService.buscarPorId(idGrupo);	
		
		List<CargaHoraria> cargas = cargaService.buscarPorGrupo(grupo);
		//List<CargaHoraria> cargas = cargaService.buscarPorGrupoYPeriodo(grupo.getId(), grupo.getPeriodo().getId());
		List<CalificacionMateria> calificaciones = new ArrayList<>();
		CalificacionMateria cal = new CalificacionMateria();
		if (cargas!=null) {
			
		for (CargaHoraria carga : cargas) {
			cal = calificacionMateriaService.buscarPorCargayAlumno(carga, alumno);
			if (cal==null) {
				cal = new CalificacionMateria();
				cal.setId(0);
				cal.setCalificacion(0f);
				cal.setEstatus("NA");
				cal.setCargaHoraria(carga);
			}
			calificaciones.add(cal);
			cal=null;
		}		
		model.addAttribute("calificaciones", calificaciones);
		}
		return "fragments/control-alumnos :: calificaciones";
	}
}