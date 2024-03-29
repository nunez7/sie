package edu.mx.utdelacosta.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.CalificacionMateria;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.Escuela;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.FolioCeneval;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.Municipio;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.model.Testimonio;
import edu.mx.utdelacosta.model.Turno;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoCalificacionDTO;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.CalificacionDTO;
import edu.mx.utdelacosta.model.dto.MateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoMatriculaInicialDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoNoReinscritoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioEscolaresDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoRegularDTO;
import edu.mx.utdelacosta.model.dtoreport.CalificacionInstrumentoDTO;
import edu.mx.utdelacosta.model.dtoreport.CalificacionParcialDTO;
import edu.mx.utdelacosta.model.dtoreport.CalificacionesMateriasParcialesDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorMateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorMateriaProfesorDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorParcialDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorProfesorDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IBajaService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICalificacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.ICicloService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.ICuatrimestreService;
import edu.mx.utdelacosta.service.IEscuelaService;
import edu.mx.utdelacosta.service.IFolioCenevalService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.ILocalidadesService;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IMunicipiosService;
import edu.mx.utdelacosta.service.INivelEstudioService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodoInscripcionService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPlanEstudioService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.ITestimonioCorteService;
import edu.mx.utdelacosta.service.ITestimonioService;
import edu.mx.utdelacosta.service.ITurnoService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.Reinscribir;
import edu.mx.utdelacosta.util.ReporteXlsxView;
import edu.mx.utdelacosta.util.SubirArchivo;

@Controller
@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Servicios Escolares')")
@RequestMapping("/escolares")
public class EscolaresController {

	@Autowired
	private IPeriodosService periodosService;

	@Autowired
	private ICicloService cicloServise;

	@Autowired
	private ICarrerasServices carreraService;

	@Autowired
	private IMunicipiosService municipiosService;

	@Autowired
	private ILocalidadesService localidadesService;

	@Autowired
	private IEscuelaService escuelasService;

	@Autowired
	private ICuatrimestreService cuatrimestreService;

	@Autowired
	private IGrupoService grupoService;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private IFolioCenevalService cenevalService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private IMateriasService materiasService;

	@Autowired
	private INivelEstudioService nivelService;

	@Autowired
	private IPlanEstudioService planService;

	@Autowired
	private IAlumnoGrupoService alumnoGrService;

	@Autowired
	private ICalificacionMateriaService calificacionMateriaService;

	@Autowired
	private ITestimonioService testimonioService;

	@Autowired
	private ITestimonioCorteService testimonioCorteService;

	@Autowired
	private IPeriodoInscripcionService periodoInscripcionService;

	@Autowired
	private ICargaHorariaService cargaService;

	@Autowired
	private IMecanismoInstrumentoService mecanismoService;

	@Autowired
	private ICalificacionService calificacionService;

	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;

	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;

	@Autowired
	private ICalificacionCorteService calificacionesCorteService;
	
	@Autowired
	private ITurnoService turnoService;
	
	@Autowired
	private IPagoGeneralService PGService;
	
	@Autowired
	private Reinscribir reinscripcion;

	@Value("${siestapp.ruta.docs}")
	private String rutaDocs;

	private Alumno alumno;
	
	@Value("${spring.mail.username}")
	private String correo;

	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";
	
	@Autowired
	private IBajaService bajaService;
	
	@Autowired
	private ReporteXlsxView reporte;

	@GetMapping("/reinscripcion")
	public String reinscripcion(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveGrupo;
		try {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
		} catch (Exception e) {
			cveGrupo = 0;
		}

		Grupo grupo = grupoService.buscarPorId(cveGrupo);
		
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(),
				usuario.getPreferencias().getIdCarrera());
		
		List<Integer> carreras = new ArrayList<>();
		
		if (grupo != null && grupo.getConsecutivo()==7 ) {
			carreras = carreraService.buscarCarreraAnterior(grupo.getCarrera().getId());
		}
		
		//se obtienen las variables
		List<AlumnoRegularDTO> alumnos = new ArrayList<>();
		
		// se buscan los alumnos en base al periodo y numero de cuatri anterior
		if (cveGrupo>0) {
		Cuatrimestre cuatri = grupo.getCuatrimestre();
		Integer periodo = usuario.getPreferencias().getIdPeriodo();
	
		if (cuatri.getConsecutivo() == 1) {
			alumnos = alumnoService.buscarTodosProspectoReinscripcion(grupos.size() > 0 ? usuario.getPreferencias().getIdCarrera() : 0,
					usuario.getPreferencias().getIdPeriodo());
		}else{
			if (carreras.isEmpty()) {
				alumnos.addAll(alumnoService.obtenerRegulares(grupo.getCarrera().getId(),
						periodo - 1, cuatri.getConsecutivo()== 7 ?  cuatri.getConsecutivo() : cuatri.getConsecutivo()-1));
			}else{
			for (Integer carrera  : carreras) {		
				alumnos.addAll(alumnoService.obtenerRegulares(carrera,
						periodo - 1, cuatri.getConsecutivo()== 7 ?  cuatri.getConsecutivo() : cuatri.getConsecutivo()-1));
				}
			}
		}
		}
		
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("carreras", carreraService.buscarTodasMenosIngles());
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		return "escolares/reinscripcion";
	}

	@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares')")
	@PostMapping(path = "/reinscripcion", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String reinscripcion(@RequestBody String body, HttpSession session) {
		JSONObject jsonObject = new JSONObject(body);
		AlumnoGrupo grupoBuscar;
		// PagoGeneral pGeneral = null;
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveGrupo;
		try {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
		} catch (Exception e) {
			cveGrupo = 0;
		}

		Grupo grupoNuevo = grupoService.buscarPorId(cveGrupo);
		grupoNuevo.setId(cveGrupo);

		List<Integer> conceptos = reinscripcion.obtenerConceptosReinscripcion(grupoNuevo.getCuatrimestre().getConsecutivo());
		
		// Recorremos los IDS
		for (String key : jsonObject.keySet()) {
			String idAlumno = (String) jsonObject.get(key);
			int idParse = Integer.parseInt(idAlumno);
			alumno = alumnoService.buscarPorId(idParse);
			grupoBuscar = alumnoGrService.checkInscrito(idParse, usuario.getPreferencias().getIdPeriodo());
			if (grupoBuscar != null) {
				// Si el grupo buscado no es el que esta seleccionado hacemos el update de datos
				if (grupoBuscar.getGrupo().getId() != cveGrupo && cveGrupo > 0) {
					grupoBuscar.setGrupo(grupoNuevo);
					alumnoGrService.guardar(grupoBuscar);
				}
			} else {
				grupoBuscar = new AlumnoGrupo();
				grupoBuscar.setAlumno(alumno);
				grupoBuscar.setGrupo(grupoNuevo);
				grupoBuscar.setFechaAlta(new Date());
				grupoBuscar.setActivo(true);
				grupoBuscar.setPagado(false);				
				alumnoGrService.guardar(grupoBuscar);
				
				//se procede a buscar el pago del periodo actual 
				if (PGService.contarAdeudoCutrimestreAlumno(grupoBuscar.getId())== 0) {
					for (Integer concepto : conceptos) {
						reinscripcion.crearPagoGenerico(concepto, usuario.getPreferencias().getIdPeriodo(), grupoBuscar);
					}
				}
			}
			
			if (alumno.getEstadoDocumentosIngreso()!=1) {
				alumno.setEstadoDocumentosIngreso(1);
				alumnoService.guardar(alumno);
			}
			
			
			
		}
		return "ok";
	}

	// Escolares
	@GetMapping("/planEstudio")
	public String planesEstudio(Model model) {
		// Se hace una consulta a todos los planes y se reutilizan
		model.addAttribute("carreras", carreraService.buscarTodasMenosIngles());
		model.addAttribute("niveles", nivelService.buscarTodos());
		model.addAttribute("planes", planService.buscarTodos());
		return "escolares/planesEstudio";
	}

	@GetMapping("/calificaciones")
	public String calificaciones(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveGrupo;
		try {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
		} catch (Exception e) {
			cveGrupo = 0;
		}

		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(),
				usuario.getPreferencias().getIdCarrera());

		List<Materia> materias = materiasService.buscarPorCargaActivaEnGrupo(cveGrupo,
				usuario.getPreferencias().getIdCarrera());
		List<AlumnoDTO> alumnos = new ArrayList<AlumnoDTO>();
		if (!materias.isEmpty()) {
			AlumnoDTO alumno;
			List<MateriaDTO> materiasDT;
			for (Alumno al : alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(cveGrupo)) {
				alumno = new AlumnoDTO();
				alumno.setId(al.getId());
				alumno.setNombre(al.getPersona().getNombreCompletoPorApellido());
				alumno.setMatricula(al.getMatricula());
				// Construimos las materias
				materiasDT = new ArrayList<MateriaDTO>();
				for (Materia materia : materias) {
					CalificacionMateria calMa = calificacionMateriaService.buscarPorAlumnoYGrupoYMateria(al.getId(), cveGrupo, materia.getId());
					// Agregamos todos los promedios de las materias del alumno
					MateriaDTO mNew = new MateriaDTO();
					mNew.setPromedio(calMa!=null ? calMa.getCalificacion() : 0);
					mNew.setEstatusPromedio(calMa!=null ? calMa.getEstatus() : "NA");
					materiasDT.add(mNew);
				}
				// Agregamos las materias al alumno
				alumno.setMaterias(materiasDT);
				alumnos.add(alumno);
			}
		}

		model.addAttribute("materias", materias);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("carreras", carreraService.buscarTodasMenosIngles());
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		return "escolares/calificacionesGenerales";
	}

	@GetMapping("/boleta/{id}")
	public String boleta(@PathVariable("id") int id, Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Carrera carrera = carreraService.buscarPorId(usuario.getPreferencias().getIdCarrera());
		int cveGrupo;
		try {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
		} catch (Exception e) {
			cveGrupo = 0;
		}
		Grupo grupo = grupoService.buscarPorId(cveGrupo);
		List<AlumnoDTO> alumnos = new ArrayList<AlumnoDTO>();
		AlumnoDTO alumno;
		List<MateriaDTO> materiasDT;
		// Si no ay ID, son todos
		if (id == 0) {
			for (Alumno al : alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(cveGrupo)) {
				alumno = new AlumnoDTO();
				alumno.setId(al.getId());
				alumno.setNombre(al.getPersona().getNombreCompletoPorApellido());
				alumno.setMatricula(al.getMatricula());
				// Construimos las materias
				materiasDT = new ArrayList<MateriaDTO>();
				for (MateriaPromedioDTO cm : calificacionMateriaService.buscarPorGrupoAlumno(cveGrupo, al.getId())) {
					// Agregamos todos los promedios de las materias del alumno
					MateriaDTO mNew = new MateriaDTO();
					int numero = Math.round(cm.getCalificacion());
					Testimonio test = testimonioService.buscarPorNumeroIntegradora(numero, cm.getIntegradora());
					mNew.setEscala(test.getAbreviatura());
					mNew.setNombre(cm.getNombre());
					mNew.setPromedio(cm.getCalificacion());
					mNew.setEstatusPromedio(cm.getEstatus());
					materiasDT.add(mNew);
				}
				// Agregamos las materias al alumno
				alumno.setMaterias(materiasDT);
				alumnos.add(alumno);
			}
		}
		if (id > 0) {
			Alumno al = alumnoService.buscarPorId(id);
			alumno = new AlumnoDTO();
			alumno.setId(al.getId());
			alumno.setNombre(al.getPersona().getNombreCompletoPorApellido());
			alumno.setMatricula(al.getMatricula());
			// Construimos las materias
			materiasDT = new ArrayList<MateriaDTO>();
			for (MateriaPromedioDTO cm : calificacionMateriaService.buscarPorGrupoAlumno(cveGrupo, al.getId())) {
				// Agregamos todos los promedios de las materias del alumno
				MateriaDTO mNew = new MateriaDTO();
				int numero = Math.round(cm.getCalificacion());
				Testimonio test = testimonioService.buscarPorNumeroIntegradora(numero, cm.getIntegradora());
				mNew.setEscala(test.getAbreviatura());
				mNew.setIntegradora(cm.getIntegradora());
				mNew.setNombre(cm.getNombre());
				mNew.setPromedio(cm.getCalificacion());
				mNew.setEstatusPromedio(cm.getEstatus());
				materiasDT.add(mNew);
			}
			// Agregamos las materias al alumno
			alumno.setMaterias(materiasDT);
			alumnos.add(alumno);
		}
		List<Materia> materias = materiasService.buscarPorCargaActivaEnGrupo(cveGrupo,
				usuario.getPreferencias().getIdCarrera());
		model.addAttribute("materias", materias);
		model.addAttribute("grupo", grupo);
		model.addAttribute("carrera", carrera);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("fechaHoy", new Date());
		return "reportes/escolares/boleta22";
	}

	@GetMapping("/boleta-alumno/{alumno}/{grupo}")
	public String boletaAlumno(@PathVariable("alumno") int idAlumno, @PathVariable("grupo") int idGrupo, Model model,
			HttpSession session) {
		// Carrera carrera = carreraService.bus
		Grupo grupo = grupoService.buscarPorId(idGrupo);
		List<AlumnoDTO> alumnos = new ArrayList<AlumnoDTO>();
		AlumnoDTO alumno;
		List<MateriaDTO> materiasDT;
		// Si no hay ID, son todos
		Alumno al = alumnoService.buscarPorId(idAlumno);
		alumno = new AlumnoDTO();
		alumno.setId(al.getId());
		alumno.setNombre(al.getPersona().getNombreCompletoPorApellido());
		alumno.setMatricula(al.getMatricula());
		// Construimos las materias
		materiasDT = new ArrayList<MateriaDTO>();
		for (MateriaPromedioDTO cm : calificacionMateriaService.buscarPorGrupoAlumno(idGrupo, al.getId())) {
			// Agregamos todos los promedios de las materias del alumno
			MateriaDTO mNew = new MateriaDTO();
			int numero = Math.round(cm.getCalificacion());
			Testimonio test = testimonioService.buscarPorNumeroIntegradora(numero, cm.getIntegradora());
			mNew.setEscala(test.getAbreviatura());
			mNew.setIntegradora(cm.getIntegradora());
			mNew.setNombre(cm.getNombre());
			mNew.setPromedio(cm.getCalificacion());
			mNew.setEstatusPromedio(cm.getEstatus());
			materiasDT.add(mNew);
		}
		// Agregamos las materias al alumno
		alumno.setMaterias(materiasDT);
		alumnos.add(alumno);
		List<Materia> materias = materiasService.buscarPorCargaActivaEnGrupo(idGrupo, grupo.getCarrera().getId());
		model.addAttribute("materias", materias);
		model.addAttribute("grupo", grupo);
		model.addAttribute("carrera", grupo.getCarrera());
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("fechaHoy", new Date());
		return "reportes/escolares/boleta22";
	}

	// para reinscribir alumno
	@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares')")
	@PostMapping(path = "/reinscripcion-alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String reinscripcionAlumno(@RequestBody Map<String, Integer> obj, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Integer idAlumno = obj.get("idAlumno");
		AlumnoGrupo ag;
		ag = alumnoGrService.checkInscrito(idAlumno, usuario.getPreferencias().getIdPeriodo());
		if (ag == null) {
			// para indicarle al usuario que el alumno no tiene grupo inscrito
			return "fail";
		} else if (ag.getFechaInscripcion() != null) {
			// para indicarle al usuario que ya fue reinscrito
			return "reinscrito";
		} else {
			ag.setFechaInscripcion(new Date());
			alumnoGrService.guardar(ag);
		}
		return "ok";
	}

	@GetMapping("/grupos")
	public String grupos(Model model, HttpSession session) {

		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarTodoPorPeriodoOrdenPorId(usuario.getPreferencias().getIdPeriodo());

		List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		List<Cuatrimestre> cuatrimestres = cuatrimestreService.buscarTodos();
		List<Periodo> periodos = periodosService.buscarTodos();
		List<Turno> turnos = turnoService.buscarTodos();

		model.addAttribute("turnos", turnos);
		model.addAttribute("carreras", carreras);
		model.addAttribute("grupos", grupos);
		model.addAttribute("periodos", periodos);
		model.addAttribute("cuatrimestres", cuatrimestres);
		return "escolares/grupos";
	}

	@GetMapping("materias")
	public String materias(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveGrupo;
		try {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
		} catch (Exception e) {
			cveGrupo = 0;
		}
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("carreras", carreraService.buscarTodasMenosIngles());
		model.addAttribute("grupos", grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(),
				usuario.getPreferencias().getIdCarrera()));
		model.addAttribute("materias",
				materiasService.buscarPorCargaActivaEnGrupo(cveGrupo, usuario.getPreferencias().getIdCarrera()));
		return "escolares/materias";
	}

	@GetMapping("periodos")
	public String periodos(Model model) {
		model.addAttribute("periodos", periodosService.buscarTodos());
		model.addAttribute("ciclos", cicloServise.buscarTodos());
		return "escolares/periodos";
	}

	@GetMapping("ceneval")
	public String ceneval() {
		return "escolares/ceneval";
	}

	@GetMapping("manual")
	public String manual1(Model model) {
		model.addAttribute("manual", "servicios_escolares.pdf");
		return "escolares/manual1";
	}

	@GetMapping("manual2")
	public String manualControlAlumnos() {
		return "escolares/manual2";
	}

	@GetMapping("manualProspectos")
	public String manualProspecto(Model model) {
		model.addAttribute("manual", "servicios_escolares.pdf");
		return "escolares/manualProspectos";
	}

	@PostMapping(path = "/subirceneval", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String subirceneval(@RequestParam("documento") MultipartFile documento) {
		if (!documento.isEmpty()) {
			String nombreArchivo = SubirArchivo.guardarArchivo(documento, rutaDocs + "/escolares/ceneval/");
			if (nombreArchivo != null) {
				// Leer el archivo subido
				importarCeneval(rutaDocs + "/escolares/ceneval/" + nombreArchivo);
			}
		}
		return "ok";
	}

	public void importarCeneval(String urlArchivo) {
		try (Workbook workbook = WorkbookFactory.create(new File(urlArchivo))) {
			// Recuperando el número de hojas en el Libro de trabajo
			// System.out.println("El documento tiene " + workbook.getNumberOfSheets() + "
			/* Iterando sobre todas las hojas en el libro de trabajo (forEach con lambda) */
			workbook.forEach(sheet -> {
				//System.out.println("=> " + sheet.getSheetName());
			});
			// Obteniendo la Hoja en el índice cero
			Sheet sheet = workbook.getSheetAt(0);
			// Crea un DataFormatter para formatear y obtener el valor de cada celda
			DataFormatter dataFormatter = new DataFormatter();

			for (Row row : sheet) {
				int n = 0;
				String matricula = "";
				String folio = "";
				// String fechaRegistro = null;
				FolioCeneval folioC = null;
				Alumno alu = null;
				FolioCeneval fNuevo = null;
				if (row.getRowNum() > 2) {
					for (Cell cell : row) {
						String cellValue = dataFormatter.formatCellValue(cell);
						switch (n) {
						case 5:
							matricula = cellValue;
							break;
						case 6:
							folio = cellValue;
							break;
						case 13:
							// fechaRegistro = cellValue;
							break;
						}
						n++;
					}
					// Aqui buscamos e insertamos o actualizamos
					folioC = cenevalService.buscarPorMatriculaAlumno(matricula);
					// Si no existe el folio
					if (folioC == null) {
						alu = alumnoService.buscarPorMatricula(matricula);
						if (alu != null) {
							alu.setCeneval(1);
							fNuevo = new FolioCeneval();
							fNuevo.setFechaAlta(new Date());
							fNuevo.setFolio(folio);
							fNuevo.setAlumno(alu);
							alu.setFolioCeneval(fNuevo);
							alumnoService.guardar(alu);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR " + e.getMessage());
		}
	}

	@GetMapping("reportes")
	public String reportes() {
		return "escolares/reportes";
	}

	@GetMapping("rprospectos")
	public String rprospectos(Model model, HttpSession session) {
		int generacion;
		try {
			generacion = (Integer) session.getAttribute("generacion");
		} catch (Exception e) {
			generacion = 0;
		}

		String generacionParse = "-" + String.valueOf(generacion);

		model.addAttribute("alumnos",
				alumnoService.buscarProspectosPorGeneracion(generacion > 0 ? generacionParse : "-29"));
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("generacion", generacion > 0 ? generacion : 29);
		model.addAttribute("periodos", periodoInscripcionService.buscarActivos());
		return "escolares/reporteProspectos";
	}

	@GetMapping("rcalificacionpromedio")
	public String rcalificacionPromedio(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveCarrera;
		try {
			cveCarrera = (Integer) session.getAttribute("cveCarrera");
		} catch (Exception e) {
			cveCarrera = 500;
		}

		int idPeriodo = usuario.getPreferencias().getIdPeriodo();
		//lista de grupos por carrera
		List<Grupo> grupos = new ArrayList<>();
		if(cveCarrera > 0) {
			grupos = grupoService.buscarPorPeriodoyCarrera(idPeriodo, cveCarrera);
			//se crea una lista de alumnos
			List<AlumnoPromedioEscolaresDTO> promedios = new ArrayList<>();
			//se iteran los grupos para sacar las calificaciones de los alumnos
			for (Grupo g : grupos) {
				promedios.addAll(alumnoService.buscarTodoPromedioEscolaresPorPeriodoYGrupo(idPeriodo, g.getId()));
			}	
			model.addAttribute("alumnos", promedios);
		}
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("cveCarrera", cveCarrera);
		model.addAttribute("carreras", carreraService.buscarTodasMenosIngles());
		return "escolares/reporteCalificacionPromedio";
	}
	
	//reporte de calificaciones promedio para exporte en pdf
	@GetMapping("/rcalificacionpromedioPdf")
	@ResponseBody
	public String reporteDatosPersonalesPdf(HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveCarrera;
		try {
			cveCarrera = (Integer) session.getAttribute("cveCarrera");
		} catch (Exception e) {
			cveCarrera = 500;
		}
		int idPeriodo = usuario.getPreferencias().getIdPeriodo();
		List<Object> datos = new ArrayList<Object>();
		String reporteGenerar = null;
		if (cveCarrera > 0) {
			List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(idPeriodo, cveCarrera);
			// se crea una lista de alumnos
			List<AlumnoPromedioEscolaresDTO> promedios = new ArrayList<>();
			// se iteran los grupos para sacar las calificaciones de los alumnos
			for (Grupo g : grupos) { 
				promedios.addAll(alumnoService.buscarTodoPromedioEscolaresPorPeriodoYGrupo(idPeriodo, g.getId()));
			}
			//se crea arreglo de encabezados para el reporte
			String[] headers = new String[] { "No","Nombre", "Matrícula", "Grupo", "Cuatrimestre", "Carrera", "Promedio", "Tipo calificación" };
			int n = 0;
			// acomodamos los datos en el reporte
			for (AlumnoPromedioEscolaresDTO alumno : promedios) {
				n++;
				Arrays.asList(datos.add(new Object[] { n + "", alumno.getNombreCompleto(), 
						alumno.getMatricula(), alumno.getGrupo(), alumno.getCuatrimestre(),
						alumno.getCarrera(), alumno.getCalificacion().toString(), alumno.getEstatus() }));
			}
			Periodo periodo = periodosService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
			reporteGenerar = reporte.generarExcelGenerico("Reporte de promedios por carrera", NOMBRE_UT, "REPORTE DE PROMEDIOS POR CARRERA ",
					"Cuatrimestre: " + periodo.getNombre(), headers, datos);
		}
		return reporteGenerar;
	}

	@GetMapping("rcalificacionparcial")
	public String rcalificacionParcial(Model model, HttpSession session) {
		int grupoActual;
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarTodoPorPeriodoOrdenPorId(usuario.getPreferencias().getIdPeriodo());

		if (session.getAttribute("grupoActual") != null) {
			grupoActual = (Integer) session.getAttribute("grupoActual");
			List<CargaHoraria> cargasHorarias = cargaService.buscarPorGrupoYPeriodo(grupoActual,
					usuario.getPreferencias().getIdPeriodo());

			if (session.getAttribute("cargaActual") != null) {
				int cargaActual = (Integer) session.getAttribute("cargaActual");

				List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(
						new Carrera(usuario.getPreferencias().getIdCarrera()),
						new Periodo(usuario.getPreferencias().getIdPeriodo()));
				if (session.getAttribute("parcialActual") != null) {
					int parcialActual = (Integer) session.getAttribute("parcialActual");

					if (parcialActual > 0) {
						List<Alumno> alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(grupoActual);
						List<MecanismoInstrumento> mecanismoInstrumento = mecanismoService
								.buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(cargaActual, parcialActual, true);
						List<CalificacionParcialDTO> calificaciones = new ArrayList<>();
						for (Alumno alumno : alumnos) {
							CalificacionParcialDTO calificacion = new CalificacionParcialDTO();
							calificacion.setMatricula(alumno.getMatricula());
							calificacion.setNombre(alumno.getPersona().getNombreCompleto());

							List<CalificacionInstrumentoDTO> mecanismos = new ArrayList<>();
							for (MecanismoInstrumento meca : mecanismoInstrumento) {
								CalificacionInstrumentoDTO cali = calificacionService
										.buscarPorAlumnoYMecanismoInstrumentoActivo(alumno, meca);
								mecanismos.add(cali);
							}
							calificacion.setMecanismos(mecanismos);

							RemedialAlumno rem = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
									alumno, new CargaHoraria(cargaActual), new Remedial(1),
									new CorteEvaluativo(parcialActual));
							if (rem != null) {
								calificacion.setCalificacionRemedial(rem.getTestimonio().getNumero());
							} else {
								calificacion.setCalificacionRemedial(0);
							}
							RemedialAlumno ex = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
									alumno, new CargaHoraria(cargaActual), new Remedial(2),
									new CorteEvaluativo(parcialActual));
							if (ex != null) {
								calificacion.setCalificacionExtraordinario(ex.getTestimonio().getNumero());
							} else {
								calificacion.setCalificacionExtraordinario(0);
							}
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
		model.addAttribute("grupos", grupos);
		return "escolares/reporteCalificacionParcial";
	}

	@GetMapping("rindicadores")
	public String rindicadores(HttpSession session, Model model) {
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		int cveCarrera;
		try {
			cveCarrera = (Integer) session.getAttribute("cveCarrera");
		} catch (Exception e) {
			cveCarrera = 500;
		}
		List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(new Carrera(cveCarrera),
				new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<IndicadorProfesorDTO> indicadoresSD = new ArrayList<>();
		List<IndicadorProfesorDTO> indicadoresRemedial = new ArrayList<>();
		List<IndicadorProfesorDTO> indicadoresExtra = new ArrayList<>();
		Integer numSD = 0;
		Integer numRem = 0;
		Integer numExt = 0;
		Integer alumnos = alumnoService.countAlumnosByCarrera(cveCarrera, usuario.getPreferencias().getIdPeriodo());
		for (CorteEvaluativo corte : cortes) {

			IndicadorProfesorDTO indicadorExt = new IndicadorProfesorDTO();
			IndicadorProfesorDTO indicadorSD = new IndicadorProfesorDTO();
			IndicadorProfesorDTO indicadorRem = new IndicadorProfesorDTO();

			numSD = testimonioCorteService.contarAlumnoSDPorCarreraYCorteEvaluativo(cveCarrera, corte.getId());
			indicadorSD.setNumero(numSD); // se busca el numero de alumnos en SD y se agrega al indicador
			indicadorSD.setPromedio((numSD * 100) / alumnos); // se promedia en base al alumno
			indicadoresSD.add(indicadorSD); // se agrega el indicador a la lista correspondiente

			numRem = remedialAlumnoService.countByCarreraAndCorteEvaluativo(cveCarrera, 1, corte.getId());
			indicadorRem.setNumero(numRem); // se obtiene el numero de remediales
			indicadorRem.setPromedio((numRem * 100) / alumnos); // se promedia
			indicadoresRemedial.add(indicadorRem); // se agrega a la lista

			numExt = remedialAlumnoService.countByCarreraAndCorteEvaluativo(cveCarrera, 2, corte.getId());
			indicadorExt.setNumero(numExt); // se obtiene el numero de remediales
			indicadorExt.setPromedio((numExt * 100) / alumnos); // se promedia
			indicadoresExtra.add(indicadorExt); // se agrega a la lista

		}
		IndicadorMateriaProfesorDTO indicadores = new IndicadorMateriaProfesorDTO();
		Integer noAlumnos = alumnoService.countInscritosByCarreraAndPeriodo(cveCarrera,
				usuario.getPreferencias().getIdPeriodo());
		indicadores.setNumeroAlumnos(noAlumnos);
		try {
			indicadores.setPorcentajeAlumnos((noAlumnos * 100) / alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeAlumnos(0);
		}

		noAlumnos = alumnoService.countBajaByCarreraAndPeriodo(cveCarrera, usuario.getPreferencias().getIdPeriodo());
		indicadores.setNumeroBajas(noAlumnos);
		try {
			indicadores.setPorcentajeBajas((noAlumnos * 100) / alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeBajas(0);
		}

		noAlumnos = alumnoService.obtenerRegularesByCarreraPeriodo(cveCarrera, usuario.getPreferencias().getIdPeriodo())
				.size();
		indicadores.setNumeroRegulares(noAlumnos);

		try {
			indicadores.setPorcentajeRegulares((noAlumnos * 100) / alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeRegulares(0);
		}
		Integer alumnosSD = testimonioCorteService.contarAlumnoSDPorCarrera(cveCarrera);
		indicadores.setNumeroSD(alumnosSD);
		try {
			indicadores.setPorcentajeSD((alumnosSD * 100) / alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeSD(0);
		}
		indicadores.setIndicadoresSD(indicadoresSD);

		Integer alumnosRemedial = remedialAlumnoService.contarPorCarreraYTipoRemedial(cveCarrera,1);
		indicadores.setNumeroRemediales(alumnosRemedial);
		try {
			indicadores.setPorcentajeRemediales((alumnosRemedial * 100) / alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeRemediales(0);
		}
		indicadores.setIndicadoresRemediales(indicadoresRemedial);

		Integer alumnosExtra = remedialAlumnoService.contarPorCarreraYTipoRemedial(cveCarrera,1);
		indicadores.setNumeroExtra(alumnosExtra);
		try {
			indicadores.setPorcentajeExtra((alumnosExtra * 100) / alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeExtra(0);
		}
		indicadores.setIndicadoresExtra(indicadoresExtra);
		List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		model.addAttribute("indicadores", indicadores);
		model.addAttribute("cortes", cortes);
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("carreras", carreras);
		model.addAttribute("cveCarrera", cveCarrera);
		model.addAttribute("totalAlumnos", alumnos);
		return "escolares/reporteIndicadores";
	}

	@GetMapping("rrequisitosinscripcion")
	public String requisitosInscripcion() {
		return "escolares/reporteRequisitoInscripcion";
	}

	@GetMapping("rmatriculainicial")
	public String rmatriculaInicial(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveCarrera;
		try {
			cveCarrera = (Integer) session.getAttribute("cveCarrera");
		} catch (Exception e) {
			cveCarrera = 500;
		}
		List<AlumnoMatriculaInicialDTO> alumnos = alumnoService
				.buscarMatriculaInicial(usuario.getPreferencias().getIdPeriodo(), cveCarrera);

		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("cveCarrera", cveCarrera);
		model.addAttribute("carreras", carreraService.buscarTodasMenosIngles());
		model.addAttribute("alumnos", alumnos);
		return "escolares/reporteMatriculaInicial";
	}
	
	@GetMapping("ralumnosnoreinscritos")
	public String rAlumnosNoReinscritos(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		List<AlumnoNoReinscritoDTO> alumnos = alumnoService.BuscarNoReinscritosPorPeriodo(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("alumnos", alumnos);
		return "escolares/reporteNoReinscritos";
	}

	@GetMapping("/municipios/{estado}")
	public String municipios(@PathVariable(name = "estado", required = false) int estado, Model model) {
		List<Municipio> municipios = new ArrayList<>();
		Estado estadoB = new Estado();
		estadoB.setId(estado > 0 ? estado : 18);
		municipios = municipiosService.buscarPorEstado(estadoB);
		model.addAttribute("municipios", municipios);
		return "escolares/nuevoProspecto :: l-municipios";
	}

	@GetMapping("/localidades/{municipio}")
	public String localidades(@PathVariable(name = "municipio", required = false) int municipio, Model model) {
		List<Localidad> localidades = new ArrayList<>();
		Municipio municipioB = new Municipio();
		municipioB.setId(municipio > 0 ? municipio : 2293);
		localidades = localidadesService.buscarPorMunicipio(municipioB);
		model.addAttribute("localidades", localidades);
		return "escolares/nuevoProspecto :: l-localidades";
	}

	@GetMapping("/municipiosf/{estado}")
	public String municipiosF(@PathVariable(name = "estado", required = false) int estado, Model model) {
		List<Municipio> municipios = new ArrayList<>();
		Estado estadoB = new Estado();
		estadoB.setId(estado > 0 ? estado : 18);
		municipios = municipiosService.buscarPorEstado(estadoB);
		model.addAttribute("municipiosF", municipios);
		return "escolares/nuevoProspecto :: lf-municipios";
	}

	@GetMapping("/localidadesf/{municipio}")
	public String localidadesF(@PathVariable(name = "municipio", required = false) int municipio, Model model) {
		List<Localidad> localidades = new ArrayList<>();
		Municipio municipioB = new Municipio();
		municipioB.setId(municipio > 0 ? municipio : 2293);
		localidades = localidadesService.buscarPorMunicipio(municipioB);
		model.addAttribute("localidadesF", localidades);
		System.out.println(localidades);
		return "escolares/nuevoProspecto :: lf-localidades";
	}

	@GetMapping("/escuelasf/{estado}")
	public String estadosF(@PathVariable(name = "estado", required = false) int estado, Model model) {
		List<Escuela> escuelas = new ArrayList<>();
		Estado estadoB = new Estado();
		estadoB.setId(estado > 0 ? estado : 18);
		escuelas = escuelasService.buscarTodoPorEstado(estadoB);
		model.addAttribute("escuelasF", escuelas);
		return "escolares/nuevoProspecto :: lf-escuelas";
	}

	@PostMapping("/extraeCarrera")
	@ResponseBody
	public Map<String, String> valoresCarrera(@RequestBody Map<String, String> obj) {
		Integer carreraId = Integer.parseInt(obj.get("carreraId"));
		Carrera career = carreraService.buscarPorId(carreraId);
		String grupo = career.getClave();
		Map<String, String> map = new HashMap<>();
		map.put("carrera", grupo);
		return map;
	}

	@GetMapping("/reporte-seguimiento")
	public String reporteSeguimiento(Model model, HttpSession session) {
		Integer idGrupoActual = 0;
		idGrupoActual = (Integer) session.getAttribute("cveGrupoEsc");

		if (idGrupoActual == null) {
			idGrupoActual = 0;
		}
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarTodoPorPeriodoOrdenPorId(usuario.getPreferencias().getIdPeriodo());

		if (idGrupoActual > 0) {
			List<CargaHoraria> cargasHorarias = cargaService.buscarPorGrupoYPeriodo(idGrupoActual,
					usuario.getPreferencias().getIdPeriodo());

			List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(
					new Carrera(usuario.getPreferencias().getIdCarrera()),
					new Periodo(usuario.getPreferencias().getIdPeriodo()));

			List<Alumno> al = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(idGrupoActual);
			List<AlumnoCalificacionDTO> alumnos = new ArrayList<>();

			for (Alumno a : al) {
				AlumnoCalificacionDTO alumno = new AlumnoCalificacionDTO();
				alumno.setMatricula(a.getMatricula());
				alumno.setNombre(a.getPersona().getNombreCompleto());

				List<CalificacionesMateriasParcialesDTO> calMaterias = new ArrayList<>();
				for (CargaHoraria carga : cargasHorarias) {

					CalificacionesMateriasParcialesDTO calificaciones = new CalificacionesMateriasParcialesDTO();
					calificaciones.setNombreMateria(carga.getMateria().getNombre());

					// lista de calificaciones por corte y por materia
					List<CalificacionDTO> calificacion = new ArrayList<>();
					cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(carga.getGrupo().getCarrera(), carga.getPeriodo());
					// se iteran los cortes evaluativos
					for (CorteEvaluativo corte : cortes) {

						// lista de calificaciones

						// se crea el DTO de calificacion
						CalificacionDTO cali = new CalificacionDTO();

						// se busca la calificacion del corte y el remedial
						cali.setCaliCorte(calificacionesCorteService
								.buscarPorAlumnoCargaHorariaYCorteEvaluativo(a.getId(), carga.getId(), corte.getId())
								.floatValue());
						RemedialAlumno remedial = remedialAlumnoService
								.buscarUltimoPorAlumnoYCargaHorariaYCorteEvaluativo(a.getId(), carga.getId(),
										corte.getId());

						// se obtiene el estatus del remedial
						if (remedial != null) {
							if (remedial.getRemedial().getId() == 1) {
								cali.setStatus("R");
							} else {
								cali.setStatus("E");
							}
						} else {
							cali.setStatus("O");
						}

						// resultado de calificacion del corte y de materia
						calificacion.add(cali);
					}

					calificaciones.setCalificaciones(calificacion);

					// calificacion de la materia en general
					CalificacionMateria cm = calificacionMateriaService.buscarPorCargayAlumno(carga, a);
					if (cm != null) {
						calificaciones.setCalificacionTotal(cm.getCalificacion());
						calificaciones.setStatus(cm.getEstatus());
					} else {
						calificaciones.setCalificacionTotal(0);
						calificaciones.setStatus("NA");
					}

					calMaterias.add(calificaciones);
				}

				// se agrega las calificaciones al alumno
				alumno.setCalificacionesMaterias(calMaterias);

				// se agrega el alumno a la lista
				alumnos.add(alumno);
			}

			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cortes", cortes);
			model.addAttribute("grupoActual", grupoService.buscarPorId(idGrupoActual));
			model.addAttribute("cargas", cargasHorarias);
		}
		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("grupos", grupos);

		return "escolares/reporteSeguimiento";
	}
	
	///////////////////////////////////////////////////////////////////
	//-------------------------Brayan---------------------------------
	//////////////////////////////////////////////////////////////////	
	@GetMapping("/reporte-bajas") 
	public String reporteBajas(Model model, HttpSession session) { 
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Carrera> carreras = carreraService.buscarTodas();
		Integer cveCarrera = (Integer) session.getAttribute("rb-cveCarrera");
		List<Baja> bajas = new ArrayList<>();
		int h=0;
		int m=0;
		if((String) session.getAttribute("reb-fechaInicio")!=null) {					
			Date fechaInicio = java.sql.Date.valueOf((String) session.getAttribute("reb-fechaInicio"));				
			if((String) session.getAttribute("reb-fechaFin")!=null) {						
				Date fechaFin = java.sql.Date.valueOf((String) session.getAttribute("reb-fechaFin"));
				if(cveCarrera==null || cveCarrera==0) {
					bajas = bajaService.buscarPorTipoStatusEntreFechas( 1, fechaInicio, fechaFin);
				}else{
					bajas = bajaService.buscarPorTipoStatusCarreraEntreFechas(1, cveCarrera,usuario.getPreferencias().getIdPeriodo() , fechaInicio, fechaFin);
				}
				model.addAttribute("fechaFin", fechaFin);
			}
			model.addAttribute("fechaInicio", fechaInicio);
		}
		
		for(Baja baja : bajas) {
			if(baja.getAlumno().getPersona().getSexo().equals("M")){
				++m;
			}else{
				++h;
			}
		}
		
		Periodo periodo = periodosService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("periodo", periodo);
		model.addAttribute("mujeres", m);
		model.addAttribute("hombre", h);
		model.addAttribute("bajas", bajas);
		model.addAttribute("carreras", carreras);
		model.addAttribute("cveCarrera", cveCarrera);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "escolares/reporteBajas"; 
	 }
	
	//reporte de alumnos Inscritos por cuatrimestre y carrera
	@GetMapping("/reporte-alumnos-carrera")
	public String reporteAlumnosCarrera(Model model, HttpSession session) {
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		//se buscan las carreras
		List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		List<AlumnoRegularDTO> alumnos = new ArrayList<>();
		//se compara que no sea null la carrera
		if(session.getAttribute("cveCarreraEs") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarreraEs");
			model.addAttribute("cveCarreraEs", cveCarrera);
			//se buscan los cuatrimestres
			List<Cuatrimestre> cuatrimestres = cuatrimestreService.buscarTodos();
			model.addAttribute("cuatrimestres", cuatrimestres);
			//se compar� si el cuatrimestre no es nulo
			if(session.getAttribute("cveCuatrimestreEs") != null) {
				int cveCuatrimestre = (Integer) session.getAttribute("cveCuatrimestreEs");
				model.addAttribute("cveCuatrimestreEs", cveCuatrimestre);
				//busca los alumnos 
				alumnos = alumnoService.buscarTodosPorCarreraYCuatrimestreYPeriodo(cveCarrera, cveCuatrimestre, usuario.getPreferencias().getIdPeriodo());
			}
		}
		Periodo periodo = periodosService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cuatrimestre", periodo.getNombre());
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("carreras", carreras);
		model.addAttribute("nombreUT", NOMBRE_UT);
		return "escolares/reporteAlumnosCarrera";
	}
	
	@GetMapping("/reporte-calificaciones-generales")
	public String calificacionesGenerales(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), cveCarrera);
			model.addAttribute("grupos",grupos); 
			model.addAttribute("cveCarrera", cveCarrera);
			if (session.getAttribute("cveGrupo") != null) {
				int cveGrupo = (Integer) session.getAttribute("cveGrupo");
				model.addAttribute("cveGrupo", cveGrupo);
				//proceso para sacar las materias
				List<CargaHoraria> cargaHorarias = cargaService.buscarPorGrupoYPeriodoYCalificacionSi(cveGrupo, usuario.getPreferencias().getIdPeriodo());
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
									calificacionTotal = calificacionesCorteService.buscarPromedioCortePorMecanismoIntrumentoYCarga(ch.getId(), c.getId(), alumno.getId());						
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
								double calificacionTotal = calificacionesCorteService
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
		Periodo periodo = periodosService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("cuatrimestre", periodo.getNombre());
		model.addAttribute("carreras", carreras);
		model.addAttribute("nombreUT", NOMBRE_UT);
		return "escolares/reporteCalificacionesGenerales";
	}

}
