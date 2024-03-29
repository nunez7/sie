package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.AlumnoReingreso;
import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.BajaAutoriza;
import edu.mx.utdelacosta.model.BajaEliminada;
import edu.mx.utdelacosta.model.Bitacora;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Documento;
import edu.mx.utdelacosta.model.Escuela;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.PrestamoDocumento;
import edu.mx.utdelacosta.model.Testimonio;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.AlumnoDocumentoDTO;
import edu.mx.utdelacosta.model.dto.DocumentoDTO;
import edu.mx.utdelacosta.model.dto.MateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoReingresoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IBajaAutorizaService;
import edu.mx.utdelacosta.service.IBajaEliminadaService;
import edu.mx.utdelacosta.service.IBajaService;
import edu.mx.utdelacosta.service.IBitacoraService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IEscuelaService;
import edu.mx.utdelacosta.service.IEstadoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaDocumentoService;
import edu.mx.utdelacosta.service.IPrestamoDocumentoService;
import edu.mx.utdelacosta.service.ITestimonioService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.Reinscribir;

@Controller
@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Servicios Escolares')")
@RequestMapping("/control-alumno")
public class ControlAlumnoController {

	@Autowired
	private IPeriodosService periodosService;

	@Autowired
	private ICarrerasServices carreraService;

	@Autowired
	private IEstadoService estadosService;

	@Autowired
	private IEscuelaService escuelasService;

	@Autowired
	private IGrupoService grupoService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private IAlumnoGrupoService alumnoGrService;

	@Autowired
	private ICalificacionMateriaService calificacionMateriaService;

	@Autowired
	private ITestimonioService testimonioService;

	@Autowired
	private ICargaHorariaService cargaService;

	@Autowired
	private IPersonaDocumentoService personaDocumentoService;

	@Autowired
	private IPrestamoDocumentoService prestamoDocumento;

	@Autowired
	private IPagoGeneralService pagoGeneralService;

	@Autowired
	private IAlumnoReingresoService alumnoReingresoService;
	
	@Autowired
	private IBajaService bajaService;

	@Autowired
	private IBajaAutorizaService bajaAutorizaService;

	@Autowired
	private IBajaEliminadaService bajaEliminadaService;

	@Autowired
	private EmailSenderService emailService;

	@Value("${spring.mail.username}")
	private String correo;

	@Autowired
	private IAlumnoGrupoService alumnoGrupoService;

	@Autowired
	private IUsuariosService usuariosService;
	
	@Autowired
	private IBitacoraService bitacoraService;

	@Value("${siestapp.ruta.docs}")
	private String rutaDocs;

	private Alumno alumno;
	
	@Autowired
	private Reinscribir reinscripcion;

	@GetMapping("/editarAlumno")
	public String editarAlumno(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveAlumno;
		try {
			cveAlumno = (Integer) session.getAttribute("cveAlumno");
		} catch (Exception e) {
			cveAlumno = 0;
		}
		// para mandar los datos del alumno
		if (cveAlumno > 0) {
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			// para sacar los aduedos del alumno
			List<PagoGeneral> adeudos = pagoGeneralService.buscarPorAlumno(cveAlumno, 0);
			// para sacar los pagos
			List<PagoGeneral> pagos = pagoGeneralService.buscarPorAlumno(cveAlumno, 1);
			// para sacar los grupos del alumno
			List<AlumnoGrupo> grupos = alumnoGrService.buscarPorIdAlumnoDesc(cveAlumno);
			// Validacion de los documentos del expediente alumno acta, curp y certificado
			List<DocumentoDTO> docsExp = personaDocumentoService
					.buscarActaCurpCerbachiPorPersonaParaDto(alumno.getPersona().getId());

			// validacion de expediente completo o incompleto
			List<Boolean> valDoc = new ArrayList<>();
			for (DocumentoDTO documentoDTO : docsExp) {
				valDoc.add(documentoDTO.getValidado());
			}

			// variable para documentos expediente
			Boolean validado = false;
			if (new HashSet<Boolean>(valDoc).size() <= 1) {
				validado = valDoc.get(0);
				if (validado == true) {
					model.addAttribute("estadoDocs", true);
				} else {
					model.addAttribute("estadoDocs", false);
				}
			} else {
				model.addAttribute("estadoDocs", false);
			}

			// calificaciones pendientes
			Grupo grupoAnterior = grupoService.buscarPorAlumnoPenultimoGrupo(cveAlumno);
			if(grupoAnterior.getId() == null) {
				//para cuando no tenga grupo anterior no tomar datos de él
				model.addAttribute("grupoAnterior", 0);
			}
			else {
				model.addAttribute("grupoAnterior", grupoAnterior);
			}
			// lista de materias
			Boolean calificacionPendiente = false;
			// para verificar si hay grupo anterior sino enviamos la calificacion pendiente
			// en false(por ser aspirante)
			if (grupoAnterior.getId() != null
					&& grupoAnterior.getPeriodo().getId() != usuario.getPreferencias().getIdPeriodo()) {
				List<CargaHoraria> materias = cargaService.buscarPorGrupo(grupoAnterior);
				for (CargaHoraria ch : materias) {
					float calificacion = calificacionMateriaService.buscarCalificacionPorAlumnoYCargaHoraria(cveAlumno,
							ch.getId());
					if (calificacion < 8) {
						calificacionPendiente = true;
						break;
					}
				}
			}

			// documentos prestados
			List<DocumentoDTO> documentos = personaDocumentoService
					.buscarPrestadosPorPersona(alumno.getPersona().getId());
			// requisito inscripcion
			Boolean reqInscripcion = false;
			// perioro 10 hacia delante
			if (usuario.getPreferencias().getIdPeriodo() >= 10) {
				if (adeudos.size() == 0 && calificacionPendiente == false && validado == true) {
					reqInscripcion = true;
				}
			} else {
				if (adeudos.size() == 0) {
					reqInscripcion = true;
				}
			}

			// lista de grupos para inscribir
			List<Grupo> gruposInscribir;
			if (grupos.size() > 0) {
				/*
				 * gruposInscribir =
				 * grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(
				 * ), grupoAnterior.getCarrera().getId());
				 */
				gruposInscribir = grupoService.buscarTodoPorPeriodoOrdenPorId(usuario.getPreferencias().getIdPeriodo());
			} else {
				/*
				 * gruposInscribir =
				 * grupoService.buscarPorPeriodoyCarrera(usuario.getPreferencias().getIdPeriodo(
				 * ), alumno.getCarreraInicio().getId());
				 */
				gruposInscribir = grupoService.buscarTodoPorPeriodoOrdenPorId(usuario.getPreferencias().getIdPeriodo());
			}
			
			//lista para historial de bajas
			List<Baja> bajas = bajaService.buscarPorAlumno(alumno);
			
			// para checar que no este inscrito
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
			model.addAttribute("bajas", bajas);
		}
		model.addAttribute("cveAlumno", cveAlumno);
		return "escolares/editarAlumno";
	}

	@GetMapping("/calificacionAlumno")
	public String calificacionAlumno(HttpSession session, Model model) {
		int cveAlumno;

		try {
			cveAlumno = (Integer) session.getAttribute("cveAlumno");
		} catch (Exception e) {
			cveAlumno = 0;
		}

		if (cveAlumno > 0) {
			List<Grupo> grupos = grupoService.buscarTodosDeAlumnosOrdenPorPeriodoAsc(cveAlumno);
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			model.addAttribute("alumno", alumno);
			model.addAttribute("grupos", grupos);
		}
		model.addAttribute("cveAlumno", cveAlumno);
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
		if (cveAlumno > 0) {
			// codigo de la pagina
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			AlumnoDocumentoDTO documentosAlumno = new AlumnoDocumentoDTO();
			documentosAlumno.setIdAlumno(cveAlumno);
			documentosAlumno.setNombre(alumno.getPersona().getNombreCompletoPorApellido());
			documentosAlumno.setMatricula(alumno.getMatricula());
			documentosAlumno.setActaNacimiento(
					personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(1)));
			documentosAlumno.setConstanciaEstudio(
					personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(5)));
			documentosAlumno.setCertificadoBachillerato(
					personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(3)));
			documentosAlumno
					.setIfe(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(6)));
			documentosAlumno
					.setImss(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(4)));
			documentosAlumno.setRequisitoTsu(
					personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(8)));
			documentosAlumno.setCedulaTsu(
					personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(9)));
			documentosAlumno.setConvenio(
					personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(7)));
			documentosAlumno.setCedulaIngenieria(
					personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(10)));
			documentosAlumno
					.setCurp(personaDocumentoService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(2)));
			model.addAttribute("documentosAlumno", documentosAlumno);

			List<PrestamoDocumento> prestamos = prestamoDocumento.buscarPorAlumno(alumno.getId());

			if (prestamos != null) {
				model.addAttribute("prestamos", prestamos);
			}
		}
		model.addAttribute("cveAlumno", cveAlumno);
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
			String folio = grupoAnterior.getCarrera().getNivelEstudio().getAbreviatura().replace(".", "").toUpperCase()
					+ alumno.getMatricula().replaceAll("-", "");
			model.addAttribute("folio", folio);
			model.addAttribute("fechaHoy", new Date());
			model.addAttribute("estados", estados);
			model.addAttribute("carreras", carreras);
			model.addAttribute("escuelas", escuelas);
			model.addAttribute("alumno", alumno);
		}
		return "escolares/tituloAlumno";
	}

	// metodo para reinscribir o inscribir a grupo a un solo alumno
	@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares')")
	@PostMapping(path = "/inscripcion-alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String inscripcionAlumno(@RequestBody Map<String, String> obj, HttpSession session) {
		AlumnoGrupo grupoBuscar;
		// PagoGeneral pGeneral = null;
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
			grupoBuscar = new AlumnoGrupo();
			grupoBuscar.setAlumno(alumno);
			grupoBuscar.setGrupo(grupoNuevo);
			grupoBuscar.setFechaAlta(new Date());
			grupoBuscar.setActivo(true);
			alumnoGrService.guardar(grupoBuscar);

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
			
			//sacamos los conceptos (pagos) del cuatrimestrea ingresar
			List<Integer> conceptos = reinscripcion.obtenerConceptosReinscripcion(grupoNuevo.getCuatrimestre().getConsecutivo());
			
			//se procede a buscar el pago del periodo actual 
			if (pagoGeneralService.contarAdeudoCutrimestreAlumno(grupoBuscar.getId())== 0) {
				for (Integer concepto : conceptos) {
					reinscripcion.crearPagoGenerico(concepto, usuario.getPreferencias().getIdPeriodo(), grupoBuscar);
				}
			}
		}
		return "ok";
	}
	
	// para consultar el historial
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
		// para guardar la cantidad de materias
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
	
	@PostMapping(path = "/deshacer-baja", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String deshacerBaja(@RequestBody Map<String, String> obj, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		String cveBaja = obj.get("id");
		String motivo = obj.get("motivo");
		Date fechaHoy = new Date();
		if (cveBaja != null) {
			if (motivo != null && !motivo.isEmpty()) {
				Baja baja = bajaService.buscarPorId(Integer.parseInt(cveBaja));
				String MailTutor = baja.getPersona().getEmail();

				// se cambia el estado del alumno de 0 a 1
				Alumno alumno = baja.getAlumno();
				alumno.setEstatusGeneral(1);
				alumnoService.guardar(alumno);

				// se reactiva el alumno grupo del ultimo grupo que fue desactivado al aprobar
				// la baja
				AlumnoGrupo alumnoGrupo = alumnoGrupoService.buscarPorAlumnoYGrupo(alumno, baja.getGrupo());
				alumnoGrupo.setActivo(true);
				alumnoGrupoService.guardar(alumnoGrupo);

				// se reactiva el usuario del alumno
				Usuario userAlumno = usuariosService.buscarPorPersona((baja.getAlumno().getPersona()));
				userAlumno.setActivo(true);
				usuariosService.guardar(userAlumno);
				
				// se registra que se deshizo el registro de baja
				BajaEliminada bajaEliminada = new BajaEliminada();
				bajaEliminada.setBaja(baja.getId());
				bajaEliminada.setPersona(usuario.getPersona());
				bajaEliminada.setAlumno(alumno);
				bajaEliminada.setMotivo(motivo);
				bajaEliminada.setFechaRegistro(fechaHoy);
				bajaEliminadaService.guardar(bajaEliminada);
				
				
				//se registra en bitacora quien elimino la baja
				Bitacora bitacora = new Bitacora();
				bitacora.setPersona(usuario.getPersona());//persona que elimino la baja
				bitacora.setAccion("ELIMINACI�N DE BAJA");
				bitacora.setDetalle("LA PERSONA: "+usuario.getPersona().getId() + ", ELIMINO LA BAJA DEL ALUMNO: "+baja.getAlumno().getId()); //id del alumno
				bitacora.setFechaAlta(new Date());
				bitacoraService.guardar(bitacora);
				
				// se envia un correo notificando que deshizo la baja y el motivo
				
				Mail mail = new Mail();
				String de = correo;
				String para1 = MailTutor;
				String para2 = alumno.getPersona().getEmail();
				
					// se eliminan los registros de baja autoriza si los ahi y se envia un correo al director que aprobo la baja cundo lo alla
					BajaAutoriza bajaAutoriza = bajaAutorizaService.buscarPorBaja(baja);
					if(bajaAutoriza!=null) {
						bajaAutorizaService.eliminar(bajaAutoriza);
						String para3 = bajaAutoriza.getPersona().getEmail();
						mail.setPara(new String[] {para1, para2, para3});
					}else{
						mail.setPara(new String[] {para1, para2});
					}
					//se eliminan el registro de la baja baja 
					bajaService.eliminar(baja);
				
				mail.setDe(de);
				// Email title
				mail.setTitulo("Baja eliminada.");
				// Variables a plantilla
				Map<String, Object> variables = new HashMap<>();
				variables.put("titulo", "Baja eliminada por escolares.");
				variables.put("cuerpoCorreo",
						"La solicitud de baja realizada para el alumno(a) " + alumno.getPersona().getNombreCompleto()
								+ " fue eliminada por escolares debido al siguiente motivo : " + motivo);
				mail.setVariables(variables);
				try {
					emailService.sendEmail(mail);
				} catch (Exception e) {
					return "errorMen";
				} 
				return "ok";
			}
			return "noMo";
		}
		return "error";
	}
	
	@PostMapping(path= "/cambiar-matricula", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String cambiarMatricula(@RequestBody Map<String, String> obj) {
		Integer idAlumno = 0;
		
		try {
			idAlumno = Integer.parseInt(obj.get("idAlumno"));
		} catch (Exception e) {
			return "err"+e;
		}
		
		String matricula = obj.get("matricula");
		
		if (alumnoService.buscarPorMatricula(matricula)!=null) {
			return "dupli";
		}
		
		Alumno alumno = alumnoService.buscarPorId(idAlumno);
		alumno.setMatricula(matricula);
		alumnoService.guardar(alumno);
		
		Usuario usuario = usuariosService.buscarPorPersona(alumno.getPersona());
		usuario.setUsuario(matricula);
		usuariosService.guardar(usuario);
		
		return "ok";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares')")
	@PostMapping(path = "/regresar-aspirante", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String regresarAspirante(@RequestBody Map<String, Integer> obj, HttpSession session) {
		int cveAlumno = obj.get("idAlumno");	
		//cuenta los registro de alumno grupo activos del alumno
		Integer g = alumnoGrupoService.contarGruposActivosPorAlumno(cveAlumno);
		if (g == 1) {
			// se desactivan los adeudos
			List<PagoGeneral> adeudos = pagoGeneralService.buscarPorAlumno(cveAlumno, 0);
			for (PagoGeneral adeudo : adeudos) {
				if (adeudo.getStatus() == 0) {
					// se desactiva el adeudo
					adeudo.setActivo(false);
					pagoGeneralService.guardar(adeudo);
				}
			}
			// se eliminan los registro de alumno grupo
			List<AlumnoGrupo> grupos = alumnoGrService.buscarPorIdAlumnoDesc(cveAlumno);
			for (AlumnoGrupo ag : grupos) {
				ag.setActivo(false);
				alumnoGrService.guardar(ag);
			}
			// se desactiva el alumno
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			alumno.setEstatusGeneral(0);
			alumnoService.guardar(alumno);
			return "ok";
		}
		else {
			return "na";
		}
	}
	
	//funcion activar alumno
	@PostMapping(path = "/activar-alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String activarAlumno(@RequestBody Map<String, Integer> obj, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cveAlumno = obj.get("idAlumno");
		//para traer las bajas del alumno
		Integer bajas = bajaService.contarBajasActivasPorAlumno(cveAlumno);
		//si tiene menos de dos bajas se activa
		if(bajas > 1) {
			return "na";
		}
		else {
			//se activa el alumno
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			//si el alumno esta activo se desactivar�
			if(alumno.getEstatusGeneral() == 1) {
				alumno.setEstatusGeneral(0);
				//se busca el alumno grupo del periodo actual
				AlumnoGrupo ag = alumnoGrService.buscarPorIdAlumnoYIdPeriodo(alumno.getId(), usuario.getPreferencias().getIdPeriodo());
				if(ag != null) {
					//se desactiva el alumno grupo
					ag.setActivo(false);
					alumnoGrService.guardar(ag);
				}
				
			}
			// si el alumno esta inactivo se activar�
			else {
				alumno.setEstatusGeneral(1);
			}
			alumnoService.guardar(alumno);
			return "ok";
		}
	}

}