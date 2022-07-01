package edu.mx.utdelacosta.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import edu.mx.utdelacosta.model.AlumnoReingreso;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.Documento;
import edu.mx.utdelacosta.model.Escuela;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.FolioCeneval;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.Municipio;
import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaDocumento;
import edu.mx.utdelacosta.model.PrestamoDocumento;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.model.Testimonio;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.AlumnoDocumentoDTO;
import edu.mx.utdelacosta.model.dto.DocumentoDTO;
import edu.mx.utdelacosta.model.dto.MateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoMatriculaInicialDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioEscolaresDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoRegularDTO;
import edu.mx.utdelacosta.model.dtoreport.CalificacionInstrumentoDTO;
import edu.mx.utdelacosta.model.dtoreport.CalificacionParcial;
import edu.mx.utdelacosta.model.dtoreport.IndicadorMateriaProfesorDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorProfesorDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoReingresoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICalificacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.ICicloService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.ICuatrimestreService;
import edu.mx.utdelacosta.service.IEscuelaService;
import edu.mx.utdelacosta.service.IEstadoCivilService;
import edu.mx.utdelacosta.service.IEstadoService;
import edu.mx.utdelacosta.service.IFolioCenevalService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.ILocalidadesService;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IMunicipiosService;
import edu.mx.utdelacosta.service.INivelEstudioService;
import edu.mx.utdelacosta.service.IPagoAlumnoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodoInscripcionService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaDocumentoService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPlanEstudioService;
import edu.mx.utdelacosta.service.IPrestamoDocumentoService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.ITestimonioCorteService;
import edu.mx.utdelacosta.service.ITestimonioService;
import edu.mx.utdelacosta.service.IUsuariosService;
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
	private IEstadoService estadosService;

	@Autowired
	private IMunicipiosService municipiosService;

	@Autowired
	private ILocalidadesService localidadesService;

	@Autowired
	private IEstadoCivilService edoCivilService;

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
	private IPersonaDocumentoService personaDocumentoService;
	
	@Autowired
	private IPrestamoDocumentoService prestamoDocumento;

	@Autowired
	private ICalificacionCorteService calificacionCorteService;
	
	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;
	
	@Autowired
	private IPagoGeneralService pagoGeneralService;
	
	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;
	
	@Autowired
	private IConceptoService conceptoService;
	
	@Value("${siestapp.ruta.docs}")
	private String rutaDocs;

	private Alumno alumno;
	
	@Autowired
	private IPagoAlumnoService pagoAlumnoService;
	
	@Autowired
	private EmailSenderService emailService;
	
	@Autowired
	private IAlumnoReingresoService alumnoReingresoService;
	
	@Value("${spring.mail.username}")
	private String correo;

	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";

	@GetMapping("/aceptarAspirantes")
	public String aceptarAspirantes(Model model, HttpSession session) {
		List<Alumno> prospectos = alumnoService.buscarProspectosRegular();
		List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		List<PersonaDocumento> documentos = new ArrayList<>();
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);

		List<Grupo> grupos = grupoService.buscarTodoPorPeriodoOrdenPorId(usuario.getPreferencias().getIdPeriodo());
		model.addAttribute("alumnos", prospectos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("carreras", carreras);
		model.addAttribute("documentos", documentos);
		return "escolares/aceptarAspirantes";
	}

	@GetMapping("/aspirantesAceptados")
	public String aspirantesAceptados(Model model, HttpSession session) {
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Integer idCarrera = ((Integer) session.getAttribute("cveCarrera"));
		Carrera carIni = new Carrera();
		if (idCarrera == null) {
			carIni = new Carrera(2);
		} else {
			carIni = new Carrera(idCarrera);
		}
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = periodosService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		List<Grupo> grupos = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), usuario.getPreferencias().getIdCarrera());
		List<Alumno> alumnos = alumnoService.buscarTodoAceptarPorCarreraYPeriodo(carIni.getId(), periodo.getId());
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("carreras", carreras);
		model.addAttribute("carreraIni", carIni);
		return "escolares/aspirantesAceptados";
	}

	@GetMapping("/nuevoProspecto")
	public String nuevoProspecto(Model model) {
		List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		Estado estado = new Estado();
		estado.setId(18);
		Municipio municipio = new Municipio();
		municipio.setId(2293);
		Escuela escuela = new Escuela();
		escuela.setId(7350);
		Estado estadoF = new Estado();
		estadoF.setId(18);
		Municipio municipioF = new Municipio();
		municipioF.setId(2293);
		Escuela escuelaF = new Escuela();
		escuelaF.setId(7350);

		model.addAttribute("carreras", carreras);
		model.addAttribute("estados", estadosService.buscarTodos());
		model.addAttribute("municipios", municipiosService.buscarPorEstado(estado));
		model.addAttribute("localidades", localidadesService.buscarPorMunicipio(municipio));
		model.addAttribute("municipiosF", municipiosService.buscarPorEstado(estadoF));
		model.addAttribute("localidadesF", localidadesService.buscarPorMunicipio(municipioF));
		model.addAttribute("escuelas", escuelasService.buscarTodoPorEstado(estadoF));
		model.addAttribute("escuelasF", escuelasService.buscarTodoPorEstado(estadoF));
		model.addAttribute("edosCivil", edoCivilService.buscarTodos());
		return "escolares/nuevoProspecto";
	}

	@GetMapping("/editarAlumno")
	public String editarAlumno(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveAlumno;
		try {
			cveAlumno = (Integer) session.getAttribute("cveAlumno");
		} catch (Exception e) {
			cveAlumno = 0;
		}
		//para mandar los datos del alumno
		if(cveAlumno > 0) {
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			//para sacar los aduedos del alumno
			List<PagoGeneral> adeudos = pagoGeneralService.buscarPorAlumno(cveAlumno, 0);
			//para sacar los pagos
			List<PagoGeneral> pagos = pagoGeneralService.buscarPorAlumno(cveAlumno, 1);
			//para sacar los grupos del alumno
			List<AlumnoGrupo> grupos = alumnoGrService.buscarPorIdAlumnoDesc(cveAlumno);
			//Validacion de los documentos del expediente alumno acta, curp y certificado																
			List<DocumentoDTO> docsExp = personaDocumentoService.buscarActaCurpCerbachiPorPersonaParaDto(alumno.getPersona().getId());
			
			// validacion de expediente completo o incompleto			
			List<Boolean> valDoc = new ArrayList<>();			
			for(DocumentoDTO documentoDTO : docsExp){
				valDoc.add(documentoDTO.getValidado());
			}		
			
			//variable para documentos expediente
			Boolean validado = false;
			if(new HashSet<Boolean>(valDoc).size() <= 1) {
				validado = valDoc.get(0);
				if(validado==true) {
					model.addAttribute("estadoDocs", true);
				}else{
					model.addAttribute("estadoDocs", false);
				}
			}else{
				model.addAttribute("estadoDocs", false);
			}
			
			//calificaciones pendientes
			Grupo grupoAnterior = grupoService.buscarPorAlumnoPenultimoGrupo(cveAlumno);
			if(grupoAnterior.getId() == null) {
				//para cuando no tenga grupo anterior no tomar datos de él
				model.addAttribute("grupoAnterior", 0);
			}
			else {
				model.addAttribute("grupoAnterior", grupoAnterior);
			}
			//lista de materias
			Boolean calificacionPendiente = false;
			//para verificar si hay grupo anterior sino enviamos la calificacion pendiente en false(por ser aspirante)
			if(grupoAnterior.getId() != null && grupoAnterior.getPeriodo().getId() != usuario.getPreferencias().getIdPeriodo()) {
				List<CargaHoraria> materias = cargaService.buscarPorGrupo(grupoAnterior);
				for (CargaHoraria ch : materias) {
					float calificacion = calificacionMateriaService.buscarCalificacionPorAlumnoYCargaHoraria(cveAlumno, ch.getId());
					if(calificacion < 8) {
						calificacionPendiente = true;
						break;
					}
				}
			}
			
			//documentos prestados
			List<DocumentoDTO> documentos = personaDocumentoService.buscarPrestadosPorPersona(alumno.getPersona().getId());
			//requisito inscripcion
			Boolean reqInscripcion = false;
			//perioro 10 hacia delante
			if(usuario.getPreferencias().getIdPeriodo() >= 10) {
				if(adeudos.size() == 0 && calificacionPendiente == false && validado == true) {
					reqInscripcion = true;
				}
			} else {
				if(adeudos.size() == 0) {
					reqInscripcion = true;
				}
			}
			
			//lista de grupos para inscribir
			List<Grupo> gruposInscribir;
			if(grupos.size() > 0) {
				gruposInscribir = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), grupoAnterior.getCarrera().getId());
			}
			else {
				gruposInscribir = grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(), alumno.getCarreraInicio().getId());
			}
			
			//para checar que no este inscrito
			AlumnoGrupo isncrito = alumnoGrService.checkInscrito(cveAlumno, usuario.getPreferencias().getIdPeriodo());
			model.addAttribute("inscrito", isncrito);
			model.addAttribute("gruposC", gruposInscribir);
			model.addAttribute("reqInscripcion", reqInscripcion);
			model.addAttribute("documentos", documentos);
			model.addAttribute("calificacionPendiente", calificacionPendiente); 
			model.addAttribute("grupos", grupos);
			model.addAttribute("pagos", pagos);
			model.addAttribute("adeudos", adeudos);
			model.addAttribute("alumno", alumno);
		}
		model.addAttribute("cveAlumno", cveAlumno);
		return "escolares/editarAlumno";
	}

	@GetMapping("/aspirantesRegistrados")
	public String aspirantesRegistrados() {
		return "escolares/aspirantesRegistrados";
	}

	@GetMapping("/promedios")
	public String promedios() {
		return "escolares/promedios";
	}

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

		List<AlumnoRegularDTO> alumnos = alumnoService.obtenerRegulares(
				(grupos.size() > 0 ? usuario.getPreferencias().getIdCarrera() : 0),
				usuario.getPreferencias().getIdPeriodo() - 1,
				cveGrupo > 0 ? (grupo.getCuatrimestre().getConsecutivo() - 1) : 2);

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
		//PagoGeneral pGeneral = null;
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveGrupo;
		try {
			cveGrupo = (Integer) session.getAttribute("cveGrupo");
		} catch (Exception e) {
			cveGrupo = 0;
		}

		Grupo grupoNuevo = new Grupo();
		grupoNuevo.setId(cveGrupo);

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
				alumnoGrService.guardar(grupoBuscar);
				// Insertamos el adeudo
				/*
				 * pGeneral = new PagoGeneral(); pGeneral.setCantidad(1);
				 * pGeneral.setDescuento(0.0); pGeneral.setConcepto(null);
				 * pGeneral.setDescripcion("CUATRIMESTRE MAYO - AGOSTO 2019");
				 * pGeneral.setFechaAlta(new Date()); pGeneral.setFolio("");
				 * pGeneral.setMonto(null); pGeneral.setMontoUnitario(null);
				 * pGeneral.setStatus(0); pGeneral.setTipo(0); pGeneral.setActivo(true);
				 * PagoAlumno pa = new PagoAlumno(); pa.setAlumno(alumno);
				 * pa.setPagoGeneral(pGeneral); pGeneral.setPagoAlumno(pa);
				 */
			}
		}
		return "ok";
	}

	@GetMapping("/pagosPorCarrera")
	public String pagosPorCarrera() {
		return "escolares/pagosPorCarrera";
	}

	@GetMapping("/calificacionAlumno")
	public String calificacionAlumno(HttpSession session, Model model) {
		int cveAlumno;
		
		try {
			cveAlumno = (Integer) session.getAttribute("cveAlumno");
		} catch (Exception e) {
		   cveAlumno = 0;
		}
		
		if (cveAlumno>0) {
			List<Grupo> grupos = grupoService.buscarTodosDeAlumnosOrdenPorPeriodoAsc(cveAlumno);
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			model.addAttribute("alumno", alumno);
			model.addAttribute("grupos", grupos);
		}
		return "escolares/calificacionAlumno";
	}

	@GetMapping("/documentacionAlumno")
	public String documentacionAlumno(HttpSession session, Model model) {
		int cveAlumno;
		  try {
		   cveAlumno = (Integer) session.getAttribute("cveAlumno");
		  } catch (Exception e) {
		   cveAlumno = 0;
		  }
		  if(cveAlumno > 0) {
		   // codigo de la pagina
			  Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			  AlumnoDocumentoDTO documentosAlumno = new AlumnoDocumentoDTO();
			  documentosAlumno.setIdAlumno(cveAlumno);
			  documentosAlumno.setNombre(alumno.getPersona().getNombreCompletoPorApellido());			  
			  documentosAlumno.setMatricula(alumno.getMatricula());			  
			  documentosAlumno.setActaNacimiento(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(),new Documento(1)));
			  documentosAlumno.setConstanciaEstudio(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(5)));
			  documentosAlumno.setCertificadoBachillerato(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(3)));
			  documentosAlumno.setIfe(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(6)));
			  documentosAlumno.setImss(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(4)));
			  documentosAlumno.setRequisitoTsu(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(8)));
			  documentosAlumno.setCedulaTsu(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(9)));
			  documentosAlumno.setConvenio(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(7)));
			  documentosAlumno.setCedulaIngenieria(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(10)));
			  documentosAlumno.setCurp(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(2)));
			  model.addAttribute("documentosAlumno", documentosAlumno);
			  
			  List<PrestamoDocumento> prestamos = prestamoDocumento.buscarPorAlumno(alumno.getId());

			  if (prestamos!=null) {
				model.addAttribute("prestamos",prestamos);
			}
		  }
		return "escolares/documentacionAlumno";
	}

	@GetMapping("/tituloAlumno")
	 public String tituloAlumno(HttpSession session, Model model) {
	  Integer idAlumno;
	  try {
	   idAlumno = (Integer) session.getAttribute("cveAlumno");
	  } catch (Exception e) {
	   idAlumno = 0;
	  }
	  if (idAlumno != null && idAlumno > 0) {
	   Alumno alumno = alumnoService.buscarPorId(idAlumno);
	   List<Estado> estados = estadosService.buscarTodos();
	   List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
	   List<Escuela> escuelas = escuelasService.buscarTodoPorEstado(new Estado(18));
	   Grupo grupoAnterior = grupoService.buscarPorAlumnoPenultimoGrupo(idAlumno);
	   String folio = grupoAnterior.getCarrera().getNivelEstudio().getAbreviatura().replace(".", "").toUpperCase()+alumno.getMatricula().replaceAll("-", "");
	   model.addAttribute("folio", folio);
	   model.addAttribute("fechaHoy", new Date());
	   model.addAttribute("estados", estados);
	   model.addAttribute("carreras", carreras);
	   model.addAttribute("escuelas", escuelas);
	   model.addAttribute("alumno", alumno);
	  }
	  return "escolares/tituloAlumno";
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
				for (MateriaPromedioDTO cm : calificacionMateriaService.buscarPorGrupoAlumno(cveGrupo, al.getId())) {
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
	public String boletaAlumno(@PathVariable("alumno") int idAlumno, @PathVariable("grupo") int idGrupo, Model model, HttpSession session) {
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
	
	//para reinscribir alumno
		@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares')")
		@PostMapping(path = "/reinscripcion-alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public String reinscripcionAlumno(@RequestBody Map<String, Integer> obj, HttpSession session) {
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			Integer idAlumno = obj.get("idAlumno");
			AlumnoGrupo ag;
			ag = alumnoGrService.checkInscrito(idAlumno, usuario.getPreferencias().getIdPeriodo());
			if(ag == null) {
				//para indicarle al usuario que el alumno no tiene grupo inscrito
				return "fail";
			}
			else if(ag.getFechaInscripcion() != null) {
				//para indicarle al usuario que ya fue reinscrito
				return "reinscrito";
			}
			else {
				ag.setFechaInscripcion(new Date());
				alumnoGrService.guardar(ag);
			}
			return "ok";
		}
		
		//metodo para reinscribir o inscribir a grupo a un solo alumno
		@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares')")
		@PostMapping(path = "/inscripcion-alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public String inscripcionAlumno(@RequestBody Map<String, String> obj, HttpSession session) {
			AlumnoGrupo grupoBuscar;
			//PagoGeneral pGeneral = null;
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			Integer idAlumno = Integer.parseInt(obj.get("idAlumno"));
			Integer cveGrupo = Integer.parseInt(obj.get("idGrupo"));
		
			Grupo grupoNuevo = grupoService.buscarPorId(cveGrupo);
			grupoNuevo.setId(cveGrupo);
			alumno = alumnoService.buscarPorId(idAlumno);
			grupoBuscar = alumnoGrService.checkInscrito(idAlumno, usuario.getPreferencias().getIdPeriodo());
			if (grupoBuscar != null) {
				// Si el grupo buscado no es el que esta seleccionado hacemos el update de datos
				if (grupoBuscar.getGrupo().getId() != cveGrupo && cveGrupo > 0) {
					grupoBuscar.setGrupo(grupoNuevo);
					alumnoGrService.guardar(grupoBuscar);
				}
			} else {
				//para cuando sea reingreso
				String generacion = obj.get("generacion");
				int ultimoPeriodo = 0;
				if(generacion != null) {
					ultimoPeriodo = Integer.parseInt(obj.get("ultimoPeriodo"));
					//se inserta el registro de alumnoReingreso
					AlumnoReingreso ar = new AlumnoReingreso();
					ar.setAlumno(alumno);
					ar.setGrupo(grupoNuevo);//grupo a ingresar
					ar.setGeneracion(generacion);
					ar.setPeriodoReingreso(grupoNuevo.getPeriodo());
					ar.setUltimoPeriodo(new Periodo(ultimoPeriodo));
					alumnoReingresoService.guardar(ar);
					//se le cambia el estatus al alumno a alta
					Alumno alumno = alumnoService.buscarPorId(idAlumno);
					alumno.setEstatusGeneral(1);
					alumnoService.guardar(alumno);
				}
				
				grupoBuscar = new AlumnoGrupo();
				grupoBuscar.setAlumno(alumno);
				grupoBuscar.setGrupo(grupoNuevo);
				grupoBuscar.setFechaAlta(new Date());
				grupoBuscar.setActivo(true);
				alumnoGrService.guardar(grupoBuscar);
				
				// Insertamos el adeudo
				PagoGeneral pGeneral = new PagoGeneral(); 
				pGeneral.setCantidad(1);
				pGeneral.setDescuento(0.0); 
				Concepto concepto = new Concepto();
				//comparamos que carrera y tipo es el grupo (tsu/lic)
				//gastronomia tsu
				if(grupoNuevo.getCarrera().getNivelEstudio().getId() == 1 && grupoNuevo.getCarrera().getId() == 9) {
					concepto = conceptoService.buscarPorId(7);
					pGeneral.setConcepto(concepto);
					pGeneral.setMonto(concepto.getMonto());
					pGeneral.setMontoUnitario(concepto.getMonto());
				}
				//gastronomia lic
				else if(grupoNuevo.getCarrera().getNivelEstudio().getId() == 2 && grupoNuevo.getCarrera().getId() == 22) {
					concepto = conceptoService.buscarPorId(9);
					pGeneral.setConcepto(concepto);
					pGeneral.setMonto(concepto.getMonto());
					pGeneral.setMontoUnitario(concepto.getMonto());
				}
				//carreras tsu excepto gastronomia
				else if(grupoNuevo.getCarrera().getNivelEstudio().getId() == 1 && grupoNuevo.getCarrera().getId() != 9) {
					concepto = conceptoService.buscarPorId(10);
					pGeneral.setConcepto(concepto);
					pGeneral.setMonto(concepto.getMonto());
					pGeneral.setMontoUnitario(concepto.getMonto());
				}
				//carreras ing/lic excepto gastronomia
				else if(grupoNuevo.getCarrera().getNivelEstudio().getId() == 2 && grupoNuevo.getCarrera().getId() != 22) {
					concepto = conceptoService.buscarPorId(8);
					pGeneral.setConcepto(concepto);
					pGeneral.setMonto(concepto.getMonto());
					pGeneral.setMontoUnitario(concepto.getMonto());
				}
				else {
					concepto = conceptoService.buscarPorId(10);
					pGeneral.setConcepto(concepto);
					pGeneral.setMonto(concepto.getMonto());
					pGeneral.setMontoUnitario(concepto.getMonto());
				}
				Periodo periodo = periodosService.buscarPorId(grupoNuevo.getPeriodo().getId());
				pGeneral.setDescripcion(concepto.getConcepto()+" "+periodo.getNombre());
				pGeneral.setFechaAlta(new Date()); 
				pGeneral.setCliente(0);
				pGeneral.setFolio("");
				pGeneral.setStatus(0); 
				pGeneral.setTipo(0); 
				pGeneral.setReferencia("");
				pGeneral.setActivo(true);
				
				PagoAlumno pa = new PagoAlumno(); 
				pa.setAlumno(alumno);
				pa.setPagoGeneral(pGeneral);
				pagoAlumnoService.guardar(pa);
				
				//pago de reinscripción
				PagoGeneral pg = new PagoGeneral();
				pg.setCantidad(1);
				pg.setDescuento(0.0); 
				Concepto con = new Concepto();
				if(grupoNuevo.getCarrera().getNivelEstudio().getId() == 1) {
					con = conceptoService.buscarPorId(21);//reinscripcion TSU
					pg.setConcepto(con);
					pg.setMonto(con.getMonto());
					pg.setMontoUnitario(con.getMonto());
				}
				else {
					con = conceptoService.buscarPorId(20);//reinscripcion ING/LIC
					pg.setConcepto(con);
					pg.setMonto(con.getMonto());
					pg.setMontoUnitario(con.getMonto());
				}
				pg.setDescripcion(con.getConcepto()+" "+periodo.getNombre());
				pg.setFechaAlta(new Date()); 
				pg.setCliente(0);
				pg.setFolio("");
				pg.setStatus(0); 
				pg.setTipo(0); 
				pg.setReferencia("");
				pg.setActivo(true);
				
				PagoAlumno pAl = new PagoAlumno();
				pAl.setAlumno(alumno);
				pAl.setPagoGeneral(pg);
				pagoAlumnoService.guardar(pAl);
			}
			return "ok";
		}
		
		//para regresar aspirante alumno
		@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares')")
		@PostMapping(path = "/regresar-aspirante", consumes = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public String regresarAspirante(@RequestBody Map<String, Integer> obj, HttpSession session) {
			int cveAlumno = obj.get("idAlumno"); 
			//se desactivan los adeudos
			List<PagoGeneral> adeudos = pagoGeneralService.buscarPorAlumno(cveAlumno, 0);
			for (PagoGeneral adeudo : adeudos) {
				if(adeudo.getStatus() == 0) {
					//se desactiva el adeudo
					adeudo.setActivo(false);
					pagoGeneralService.guardar(adeudo);
				}
			}
			//se eliminan los registro de alumno grupo
			List<AlumnoGrupo> grupos = alumnoGrService.buscarPorIdAlumnoDesc(cveAlumno);
			for (AlumnoGrupo ag : grupos) {
				alumnoGrService.eliminar(ag);
			}
			return "ok";
		}
		
		//para consultar el historial
		@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares')")
		@GetMapping(path = "/consultar-historial/{grupos}")
		public String consultarHistorial(@PathVariable("grupos") String grupos, HttpSession session, Model model) {
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			String[] partes = grupos.split("-");
			int cveAlumno;
			try {
				cveAlumno = (Integer) session.getAttribute("cveAlumno");
			} catch (Exception e) {
				cveAlumno = 0;
			}
			AlumnoDTO alumno;
			List<MateriaDTO> materiasDT = new ArrayList<MateriaDTO>();
			Alumno al = alumnoService.buscarPorId(cveAlumno);
			alumno = new AlumnoDTO();
			alumno.setId(al.getId());
			alumno.setNombre(al.getPersona().getNombreCompletoPorApellido());
			alumno.setMatricula(al.getMatricula());
			//para guardar la cantidad de materias
			int materias = 0;
			for (String string : partes) {
				Integer grupo = Integer.parseInt(string);			
				for (MateriaPromedioDTO cm : calificacionMateriaService.buscarPorGrupoAlumno(grupo, al.getId())) {
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
					materias++;
				}
				// Agregamos las materias al alumno
				alumno.setMaterias(materiasDT);
				
				model.addAttribute("periodo", periodosService.buscarPorId(usuario.getPreferencias().getIdPeriodo()));
				model.addAttribute("materias", materias);
				model.addAttribute("grupo", grupo);
				model.addAttribute("carrera", al.getCarreraInicio());
				model.addAttribute("alumno", alumno);
				model.addAttribute("al", al);
				model.addAttribute("fechaHoy", new Date());
			}
			
			return "reportes/escolares/historialCalificaciones";
		}

	@GetMapping("/grupos")
	public String grupos(Model model, HttpSession session) {

		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		List<Grupo> grupos = grupoService.buscarTodoPorPeriodoOrdenPorId(usuario.getPreferencias().getIdPeriodo());

		List<Carrera> carreras = carreraService.buscarTodasMenosIngles();
		List<Cuatrimestre> cuatrimestres = cuatrimestreService.buscarTodos();
		List<Periodo> periodos = periodosService.buscarTodos();

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
	
	@GetMapping("manual-control")
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
				System.out.println("=> " + sheet.getSheetName());
			});
			// Obteniendo la Hoja en el índice cero
			Sheet sheet = workbook.getSheetAt(0);
			// Crea un DataFormatter para formatear y obtener el valor de cada celda
			DataFormatter dataFormatter = new DataFormatter();

			for (Row row : sheet) {
				int n = 0;
				String matricula = "";
				String folio = "";
				//String fechaRegistro = null;
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
							//fechaRegistro = cellValue;
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

		List<AlumnoPromedioEscolaresDTO> alumnos = cveCarrera == 0
				? alumnoService.buscarTodoPromedioEscolaresPorPeriodo(idPeriodo)
				: alumnoService.buscarTodoPromedioEscolaresPorPeriodoyCarrera(idPeriodo, cveCarrera);

		model.addAttribute("utName", NOMBRE_UT);
		model.addAttribute("cveCarrera", cveCarrera);
		model.addAttribute("carreras", carreraService.buscarTodasMenosIngles());
		model.addAttribute("alumnos", alumnos);
		return "escolares/reporteCalificacionPromedio";
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

				List<CorteEvaluativo> cortes = corteEvaluativoService
						.buscarPorCarreraYPeriodo(new Carrera(usuario.getPreferencias().getIdCarrera()),new Periodo(usuario.getPreferencias().getIdPeriodo()));
				if (session.getAttribute("parcialActual") != null) {
					int parcialActual = (Integer) session.getAttribute("parcialActual");

					if (parcialActual > 0) {
						List<Alumno> alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(grupoActual);
						List<MecanismoInstrumento> mecanismoInstrumento = mecanismoService
								.buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(cargaActual, parcialActual, true);
						List<CalificacionParcial> calificaciones = new ArrayList<>();
						for (Alumno alumno : alumnos) {
							CalificacionParcial calificacion = new CalificacionParcial();
							calificacion.setMatricula(alumno.getMatricula());
							calificacion.setNombre(alumno.getPersona().getNombreCompleto());
							List<CalificacionInstrumentoDTO> mecanismos = calificacionService
									.findByCargaHorariaAndCorteEvaluativo(alumno.getId(), cargaActual, parcialActual);
							calificacion.setMecanismos(mecanismos);
							calificacion.setCalificacionOrdinaria(calificacionCorteService
									.buscarPorAlumnoCargaHorariaYCorteEvaluativo(alumno.getId(),
											cargaActual, parcialActual).floatValue());
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
		Persona persona = new Persona((Integer)session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		int cveCarrera;
		try {
			cveCarrera = (Integer) session.getAttribute("cveCarrera");
		} catch (Exception e) {
			cveCarrera = 500;
		}
		List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(new Carrera(cveCarrera),new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<IndicadorProfesorDTO> indicadoresSD = new ArrayList<>();
		List<IndicadorProfesorDTO> indicadoresRemedial = new ArrayList<>();
		List<IndicadorProfesorDTO> indicadoresExtra = new ArrayList<>();
		Integer totalSD = 0;
		Integer totalRemedial = 0;
		Integer totalExtra = 0;
		Integer numSD = 0;
		Integer numRem = 0;
		Integer numExt = 0;
		Integer alumnos = alumnoService.countAlumnosByCarrera(cveCarrera, usuario.getPreferencias().getIdPeriodo());
		for (CorteEvaluativo corte : cortes) {
			
			IndicadorProfesorDTO indicadorExt = new IndicadorProfesorDTO();
			IndicadorProfesorDTO indicadorSD = new IndicadorProfesorDTO();
			IndicadorProfesorDTO indicadorRem = new IndicadorProfesorDTO();

			numSD = testimonioCorteService.countAlumnosSDByCarrera(cveCarrera, corte.getId());
			indicadorSD.setNumero(numSD); //se busca el numero de alumnos en SD y se agrega al indicador
			indicadorSD.setPromedio((numSD*100)/alumnos); //se promedia en base al alumno
			totalSD = totalSD+numSD; //se suma el numero de SD para la variable general
			indicadoresSD.add(indicadorSD); //se agrega el indicador a la lista correspondiente
			
			numRem = remedialAlumnoService.countByCarreraAndCorteEvaluativo(cveCarrera, 1, corte.getId());
			indicadorRem.setNumero(numRem); //se obtiene el numero de remediales					
			indicadorRem.setPromedio((numRem*100)/alumnos); //se promedia					
			totalRemedial =  totalRemedial + numRem; //se suma a la lista general
			indicadoresRemedial.add(indicadorRem); //se agrega a la lista

			
			numExt = remedialAlumnoService.countByCarreraAndCorteEvaluativo(cveCarrera, 2, corte.getId());
			indicadorExt.setNumero(numExt); //se obtiene el numero de remediales					
			indicadorExt.setPromedio((numExt*100)/alumnos); //se promedia			
			totalExtra = totalExtra + numExt; //se suma a la lista general
			indicadoresExtra.add(indicadorExt); //se agrega a la lista

		}
		IndicadorMateriaProfesorDTO indicadores = new IndicadorMateriaProfesorDTO();
		Integer noAlumnos = alumnoService.countInscritosByCarreraAndPeriodo(cveCarrera, usuario.getPreferencias().getIdPeriodo());
		indicadores.setNumeroAlumnos(noAlumnos);	
		try {
			indicadores.setPorcentajeAlumnos((noAlumnos*100)/alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeAlumnos(0);
		}
		
		noAlumnos = alumnoService.countBajaByCarreraAndPeriodo(cveCarrera, usuario.getPreferencias().getIdPeriodo());
		indicadores.setNumeroBajas(noAlumnos);
		try {
			indicadores.setPorcentajeBajas((noAlumnos*100)/alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeBajas(0);
		}
		
		noAlumnos = alumnoService.obtenerRegularesByCarreraPeriodo(cveCarrera, usuario.getPreferencias().getIdPeriodo()).size();
		indicadores.setNumeroRegulares(noAlumnos);
		
		try {
			indicadores.setPorcentajeRegulares((noAlumnos*100)/alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeRegulares(0);
		}
		indicadores.setNumeroSD(totalSD);
		try {
			indicadores.setPorcentajeSD((totalSD*100)/alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeSD(0);
		}
		indicadores.setIndicadoresSD(indicadoresSD);
		
		indicadores.setNumeroRemediales(totalRemedial);
		try {
			indicadores.setPorcentajeRemediales((totalRemedial*100)/alumnos);
		} catch (ArithmeticException e) {
			indicadores.setPorcentajeRemediales(0);
		}
		indicadores.setIndicadoresRemediales(indicadoresRemedial);
		
		indicadores.setNumeroExtra(totalExtra);
		try {
			indicadores.setPorcentajeExtra((totalExtra*100)/alumnos);
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
	
	@PostMapping(path = "/enviar-correo", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String enviarCorreo(@RequestBody Map<String, String> obj, HttpSession session) {
		String mensaje;
		try {
			mensaje = obj.get("correo");
		} catch (Exception e) {
			mensaje = "";
		}
		
		if (!mensaje.isEmpty()) {
			Alumno alumno = alumnoService.buscarPorId((Integer)session.getAttribute("cveAlumno"));
			if (alumno!=null) {
				
				if (alumno.getPersona().getEmail()==null || alumno.getPersona().getEmail().isEmpty()) {
					return "noMail";
				}			
				
				/*
				Mail mail = new Mail();
				String de = correo;
				String para = "nadie";
				//String para = alumno.getPersona().getEmail();
				mail.setDe(de);
				mail.setPara(new String[] {para});
				
				//Email title
				mail.setTitulo("Dosificación pendiente por validar");
				
				//Variables a plantilla
				Map<String, Object> variables = new HashMap<>();
				variables.put("titulo", "Documento ");
				variables.put("cuerpoCorreo", "Tienes un comentario sobre un documento ");
				mail.setVariables(variables);
				try {
					emailService.sendEmail(mail);
					System.out.println("Enviado");
				}catch (MessagingException | IOException e) {
					System.out.println("Error "+e);
				  	}
				
				*/
				
			}
			
		}else {
			return "blank";
		}
		
		return "ok";
	}
	
	//solicitudes de bajas
	@GetMapping("/bajas")
	public String bajas(Model model) {
		return "escolares/bajas";
	}
	
	
}
