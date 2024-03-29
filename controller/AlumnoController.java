package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Asistencia;
import edu.mx.utdelacosta.model.Calificacion;
import edu.mx.utdelacosta.model.CalificacionCorte;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.model.Documento;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.MecanismoAlumno;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaDocumento;
import edu.mx.utdelacosta.model.PersonaReferencia;
import edu.mx.utdelacosta.model.ProgramacionTutoria;
import edu.mx.utdelacosta.model.ProrrogaAdeudo;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.model.Turno;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.CorteEvaluativoDTO;
import edu.mx.utdelacosta.model.dto.DiaDTO;
import edu.mx.utdelacosta.model.dto.DocumentoDTO;
import edu.mx.utdelacosta.model.dto.GrupoDTO;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.HorarioDiaDTO;
import edu.mx.utdelacosta.model.dto.MesDTO;
import edu.mx.utdelacosta.model.dto.ReferenciaBanamexDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsistenciaService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IDiaService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IMecanismoAlumnoService;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaDocumentoService;
import edu.mx.utdelacosta.service.IPersonaReferenciaService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IProgramacionTutoriaService;
import edu.mx.utdelacosta.service.IProrrogaAdeudoService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.ITutoriaIndividualService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.CodificarTexto;
import edu.mx.utdelacosta.util.NumberToLetterConverter;
import edu.mx.utdelacosta.util.ReferenciaFondos;
import edu.mx.utdelacosta.util.ReferenciaSEP;
import edu.mx.utdelacosta.util.SubirArchivo;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Alumno')")
@RequestMapping("/alumno")
public class AlumnoController {

	@Autowired
	private IAlumnoService serviceAlumno;

	@Autowired
	private IGrupoService serviceGrupo;

	@Autowired
	private ICargaHorariaService serviceCargaHor;

	@Autowired
	private IPagoGeneralService servicePagoGeneral;

	@Autowired
	private ICalificacionCorteService serviceCalCorte;

	@Autowired
	private ICalificacionService serviceCalificacion;

	@Autowired
	private IMecanismoAlumnoService serviceMecaAlum;

	@Autowired
	private IRemedialAlumnoService serviceRemAlum;

	@Autowired
	private ICorteEvaluativoService serviceCorte;
	
	@Autowired
	private IHorarioService serviceHorario;
	
	@Autowired
	private IDiaService serviceDia;
	
	@Autowired
	private IAsistenciaService serviceAsistencia;
	
	@Autowired
	private ReferenciaSEP generarReferenciaSEP;
	
	@Autowired
	private ReferenciaFondos generarReferenciaFondos;
	
	@Autowired
	private IPersonaDocumentoService servicePersonaDoc;
	
	@Autowired
	private IAlumnoGrupoService serviceAlumGrupo;
	
	@Autowired
	private IMecanismoInstrumentoService serviceMecaIntru;
	
	@Autowired
	private ICarrerasServices carrerasService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IAlumnoGrupoService alumnoGrService;
	
	@Autowired
	private IPeriodosService periodoService;
	
	@Autowired
	private IPersonaReferenciaService personaReferenciaService;
	
	@Autowired
	private IProrrogaAdeudoService prorrogaAdeudoService;
	
	@Value("${siestapp.ruta.docs}")
    private String ruta;
	
	@Value("${spring.mail.username}")
	private String correo;

	@Autowired
	private EmailSenderService emailService;
	
	@Autowired
	private ITutoriaIndividualService tutoriaIndService;
	
	@Autowired
	private IUsuariosService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IProgramacionTutoriaService programacionTutoriaService;
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Servicios Escolares','Informatica', 'Rector')")
	@GetMapping("/search/{id}")
	public ResponseEntity<AlumnoDTO> leer(@PathVariable("id") int id) {
		Alumno alumno = serviceAlumno.buscarPorId(id);
		
		if (alumno == null) {
			return ResponseEntity.notFound().build();
		} else {
			AlumnoDTO alumnoDTO = new AlumnoDTO();
			Grupo grupo = new Grupo();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = simpleDateFormat.format(alumno.getPersona().getDatosPersonales().getFechaNacimiento());
			alumnoDTO.setNombre(alumno.getPersona().getNombre());
			alumnoDTO.setPrimerApellido(alumno.getPersona().getPrimerApellido());
			alumnoDTO.setSegundoApellido(alumno.getPersona().getSegundoApellido());
			alumnoDTO.setIdCarrera(alumno.getCarreraInicio().getId());
			alumnoDTO.setCurp(alumno.getPersona().getDatosPersonales().getCurp());
			alumnoDTO.setFechaNacimiento(date);
			alumnoDTO.setEmail(alumno.getPersona().getEmail());
			alumnoDTO.setCelular(alumno.getPersona().getDatosPersonales().getCelular());
			if (alumno.getTurno()!=null) {
				alumnoDTO.setIdTurno(alumno.getTurno().getId());				
			}
			grupo = serviceGrupo.buscarUltimoDeAlumno(id);
			if(grupo  == null)
			{
				
			}else {
				alumnoDTO.setIdGrupo(grupo.getId());
			}
			
			return ResponseEntity.ok(alumnoDTO);
		}
	}
	
	@GetMapping("/asistencias")
	public String asistencias(Model model, HttpSession session, Authentication authentication) {
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			//lista de grupos asociados al alumno
			List<Grupo> grupos = serviceGrupo.buscarTodosDeAlumnosOrdenPorPeriodoDesc(alumno.getId());
			model.addAttribute("grupos", grupos);
			
			// Extrae él id del grupo a partir del cveGrupo guardado en sesión
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");
			// Envía el id del grupo para validar si ya se ha seleccionado un grupo
			model.addAttribute("cveGrupo", cveGrupo);
			
			//----------------			
			if (cveGrupo != null) {
				Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);
				
				List<CorteEvaluativo> corteEvaluativos = serviceCorte.buscarPorPeridoYCarreraFechaInicioAsc(grupo.getPeriodo(), grupo.getCarrera());								
				
				List<CorteEvaluativoDTO> corteDto = new ArrayList<>();								
				
				for (CorteEvaluativo corte : corteEvaluativos) {
					
					CorteEvaluativoDTO cortesEvaluativosDTO = new CorteEvaluativoDTO();
					cortesEvaluativosDTO.setIdCorte(corte.getId());
					cortesEvaluativosDTO.setFechaInicio(corte.getFechaInicio());
					cortesEvaluativosDTO.setFechaFin(corte.getFechaFin());
					cortesEvaluativosDTO.setConsecutivo(corte.getConsecutivo());			
					corteDto.add(cortesEvaluativosDTO);
					
					//se igual la fecha de inicio del corte al dia 1 del mes de inicio
					Calendar cal = Calendar.getInstance();
					cal.setTime(corte.getFechaInicio());
					cal.set(Calendar.DAY_OF_MONTH, 1);
					Date fechaInicio = cal.getTime();
					
					//se buscan los meses usando el dia 1 del mes del corte para obtener todos los meses sin excepcion
					List<Date> meses = serviceAsistencia.mesesEntreFechaInicioYFechaFinAsc(fechaInicio,	corte.getFechaFin());
						
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
				}
				//----------------
				
				// Se extraen la carga horaria a partir del grupo seleccinado	
				List<CargaHoraria> cargasHor = serviceCargaHor.buscarPorGrupoYPeriodoYCalificacionSi(cveGrupo, grupo.getPeriodo().getId());				

				List<Asistencia> asistencias = serviceAsistencia.buscarPorGrupoYalumno(cveGrupo, alumno.getId());
				
				model.addAttribute("corteDto", corteDto);
				model.addAttribute("cargasHor", cargasHor);
				model.addAttribute("asistencias", asistencias);
			}				
			
		}
		model.addAttribute("alumno", alumno);
		return "alumno/asistencias";
	}

	@GetMapping(path = "/aprobar-instrumento/{carga}")
	public String AprobarInstrumento(@PathVariable(name = "carga", required = true) String idCarga,
	Model model, HttpSession session) {
		Integer cargaID = Integer.parseInt(idCarga);
		// Carga de los grupos asociados al usuario(alumno).
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));

		// Se busca la carga Horaria del correspondiente al ID recibido
		CargaHoraria cargaHoraria = serviceCargaHor.buscarPorIdCarga(cargaID);

		// Se extrae los datos del mecanismo alumno para validar el mecanismo de evaluación
		// ya ha sido aceptado por el alumno
		MecanismoAlumno mecanismo = serviceMecaAlum.buscarPorAlumnoYCargaHoraria(alumno, cargaHoraria);

		// Válida si validar mecanismo ya existe para aceptarlo
		if (mecanismo != null) {
			boolean acepto = true;
			Date fecha = new Date();
			mecanismo.setFechaAcepto(fecha);
			mecanismo.setAcepto(acepto);
			serviceMecaAlum.guardar(mecanismo);

			// Carga las calificaciones de los dos cortes que puede haber por carga
			List<CalificacionCorte> calificacionesCorte = serviceCalCorte.buscarPorCargaYAlumno(cargaHoraria, alumno);
			model.addAttribute("CalCortes", calificacionesCorte);
			
			// extrae las calificaciones por instrumento
			List<Calificacion> calInstrumento = serviceCalificacion.buscarPorAlumnoYCargaH(alumno.getId(), cargaID);
			model.addAttribute("calInstru", calInstrumento);
					
			// Se extraen el grupo a partir del grupo cargado en sesión
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");
			Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);

			// Lista de cortes asociados a la carga horaria seleccionada y a la carrera
			List<CorteEvaluativo> cortes = serviceCorte.buscarPorPeridoYCarreraFechaInicioAsc(cargaHoraria.getPeriodo(),grupo.getCarrera());
			
			//Definición del nivel de cada corte evaluativo		
			String nivel1 = "";
			String nivel2 = "";
			for(CorteEvaluativo corte : cortes) {			
				List<RemedialAlumno> remedialesCor = serviceRemAlum.buscarPorAlumnoCargaYCorte(alumno, cargaHoraria, corte);
				if(remedialesCor.size() == 0) {
					if (corte.getConsecutivo() == 1) {
						nivel1 = "Ordinario";
					}else{
						nivel2 = "Ordinario";
					}				
				}else{
					RemedialAlumno remedial = remedialesCor.get(0);
					if (corte.getConsecutivo() == 1) {
						nivel1 = remedial.getRemedial().getDescripcion();
					}else{
						nivel1 = remedial.getRemedial().getDescripcion();
					}
				}
			}
			List<MecanismoInstrumento> mecanismos = serviceMecaIntru.buscarPorIdCargaHorariaYActivo(cargaID, true);
			model.addAttribute("nivel1", nivel1);
			model.addAttribute("nivel2", nivel2);
			
			model.addAttribute("cortes", cortes);
			model.addAttribute("MecanismoInstrumento", mecanismos);
			model.addAttribute("valMecanismo", mecanismo);

		}

		return "fragments/calificacion-corte:: calificacionCorte";
	}

	@GetMapping(path = "/cargar-calificacion-corte/{carga}")
	public String cargarCalificacionCorte(@PathVariable(name = "carga", required = true) String idCarga,
	Model model, HttpSession session) {
		// Guarda los datos enviados de la vista en variable
		Integer cargaID = Integer.parseInt(idCarga);
		
		// Se genera el objeto usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));

		// Extrae la carga Horaria a partir del id recibido
		CargaHoraria cargaHoraria = serviceCargaHor.buscarPorIdCarga(cargaID);		

		// Extrae los datos del mecanismo alumno para validar el mecanismo de evaluación
		// ya ha sido aceptado por el alumno
		MecanismoAlumno validarMecanismo = serviceMecaAlum.buscarPorAlumnoYCargaHoraria(alumno, cargaHoraria);

		// Valida si el mecanismo ya tiene un registro en "mecanismo alumno"
		if (validarMecanismo == null) {
			boolean acepto = false;
			// Date fecha = new Date();
			boolean activo = true;

			validarMecanismo = new MecanismoAlumno();
			validarMecanismo.setCargaHoraria(cargaHoraria);
			validarMecanismo.setAlumno(alumno);
			validarMecanismo.setAcepto(acepto);
			validarMecanismo.setFechaAcepto(null);
			validarMecanismo.setActivo(activo);
			serviceMecaAlum.guardar(validarMecanismo);
		}

		// válida si el mecanismo ya ha sido aceptado
		if (validarMecanismo.getAcepto() == true) {
			// Carga las calificaciones de los dos cortes que puede haber por carga
			List<CalificacionCorte> calificacionesCorte = serviceCalCorte.buscarPorCargaYAlumno(cargaHoraria, alumno);

			// extrae las calificaciones por instrumento
			List<Calificacion> calInstrumento = serviceCalificacion.buscarPorAlumnoYCargaH(alumno.getId(), cargaID);

			model.addAttribute("calInstru", calInstrumento);
			model.addAttribute("CalCortes", calificacionesCorte);
		} else {
			model.addAttribute("idAlumno", alumno.getId());
			model.addAttribute("idCarga", cargaID);
		}

		// Se extrae el grupo cargado en sesión
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");
		Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);

		// Lista de cortes asociados a la carga horaria seleccionada y carrera
		List<CorteEvaluativo> cortes = serviceCorte.buscarPorPeridoYCarreraFechaInicioAsc(cargaHoraria.getPeriodo(),grupo.getCarrera());
		
		//defincion del nivel de cada corte evaluativo		
		String nivel1 = "";
		String nivel2 = "";
		for(CorteEvaluativo corte : cortes) {			
			List<RemedialAlumno> remedialesCor = serviceRemAlum.buscarPorAlumnoCargaYCorte(alumno, cargaHoraria, corte);
			if(remedialesCor.size() == 0) {
				if (corte.getConsecutivo() == 1) {
					nivel1 = "Ordinario";
				}else{
					nivel2 = "Ordinario";
				}				
			}else{
				RemedialAlumno remedial = remedialesCor.get(0);
				if (corte.getConsecutivo() == 1) {
					nivel1 = remedial.getRemedial().getDescripcion();
				}else{
					nivel1 = remedial.getRemedial().getDescripcion();
				}
			}
		}
		List<MecanismoInstrumento> mecanismos = serviceMecaIntru.buscarPorIdCargaHorariaYActivo(cargaID, true);
		model.addAttribute("nivel1", nivel1);
		model.addAttribute("nivel2", nivel2);
		
		model.addAttribute("cortes", cortes);
		model.addAttribute("MecanismoInstrumento", mecanismos);
		model.addAttribute("valMecanismo", validarMecanismo);

		return "fragments/calificacion-corte:: calificacionCorte";
	}

	@GetMapping("/calificaciones")
	public String calificaciones(Model model, HttpSession session, Authentication authentication) {
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			List<Grupo> grupos = serviceGrupo.buscarTodosDeAlumnosOrdenPorPeriodoDesc(alumno.getId());
			model.addAttribute("grupos", grupos);
			
			// Se extrae el id del grupo a partir del id del grupo guardado en sesión			
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");			
			// Envía el id del grupo a la vista para validar si ya se a seleccionado un grupo
			model.addAttribute("cveGrupo", cveGrupo);		
			if (cveGrupo != null) {		
				Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);
				// Se extraen la carga horaria a partir del grupo seleccionado				
				List<CargaHoraria> cargasHor = serviceCargaHor.buscarPorGrupoYPeriodoYCalificacionSi(cveGrupo, grupo.getPeriodo().getId());
				model.addAttribute("cargasHor", cargasHor);			
			}
		}
		model.addAttribute("alumno", alumno);
		return "alumno/calificaciones";
	}
	
	@PatchMapping(path = "/enviar-documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody	
	public String guardarDocumento(@RequestParam("documento") MultipartFile multiPart, @RequestParam("idDocumento") String idDocumento, HttpSession session) {				 
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;		
		Integer cveDocumento = Integer.parseInt(idDocumento);		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		if (!multiPart.isEmpty()) {
			if (cveDocumento == 1 || cveDocumento == 2 || cveDocumento == 3) {
				
				PersonaDocumento perDoc = servicePersonaDoc.buscarPorPersonaYdocumento(new Persona(cvePersona), new Documento(cveDocumento));	
				
				if (perDoc == null) {					
					List<PersonaDocumento> docs = servicePersonaDoc.buscarActaCurpCerbachiPorPersona(cvePersona);
					
					PersonaDocumento personaDoc = new PersonaDocumento();
					personaDoc.setPersona(new Persona(cvePersona));
					personaDoc.setDocumento(new Documento(cveDocumento));	
					personaDoc.setEntregado("No Entregado");
					personaDoc.setValidado(false);
					personaDoc.setPrestado(false);
					personaDoc.setUrlPdf(null);
					servicePersonaDoc.guardar(personaDoc);
					
					//Cuando se sube el tercer documento se envía un correo a escolares.
					if(docs.size()==2) {
						Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
						Mail mail = new Mail();
						String de = correo;
						String para = "servicios.escolares@utnay.edu.mx";

						mail.setDe(de);
						mail.setPara(new String[] {para});		
						//Email title
						mail.setTitulo("Nuevos documentos de recibidos.");		
						//Variables a plantilla
						Map<String, Object> variables = new HashMap<>();
						variables.put("titulo", "El alumno (a): "+alumno.getPersona().getNombreCompleto()+" ha subido sus documentos principales.");						
						variables.put("cuerpoCorreo","El alumno (a) "+alumno.getPersona().getNombreCompleto()
								+" con matrícula: "+alumno.getMatricula()+", ha subido sus tres documentos principales desde el panel del alumno,"
								+" diríjase al panel de servicios escolares para aprobarlos.");

						mail.setVariables(variables);			
						try {							
							emailService.sendEmail(mail);													
						}catch (Exception e) {
							return "errMail";
					  	}
					}
					
				}else{
					if (perDoc.getValidado() == true) {
						return "aceptado";
					}					
				}
				
				perDoc = servicePersonaDoc.buscarPorPersonaYdocumento(new Persona(cvePersona), new Documento(cveDocumento));
				
				if(perDoc.getUrlPdf() == null) {
					
					String archivo = SubirArchivo.guardarArchivo(multiPart, ruta+"/alumnos/inscripcion/"+cvePersona+"-"+perDoc.getId());
					if (archivo != null) {				
						//Actualiza la ruta del archivo y se cambia el estado en entregado
						perDoc.setUrlPdf(cvePersona+"-"+perDoc.getId()+archivo);
						perDoc.setEntregado("Entregado");
						servicePersonaDoc.guardar(perDoc);
						 
						return "ok";
					}
					
				}else{				
					SubirArchivo.borrarArchivo(ruta+"/alumnos/inscripcion/"+perDoc.getUrlPdf());
					String archivo = SubirArchivo.guardarArchivo(multiPart, ruta+"/alumnos/inscripcion/"+cvePersona+"-"+perDoc.getId());
					if (archivo != null) {				
						//Actualiza la ruta del archivo y se cambia el estado en entregado
						perDoc.setUrlPdf(cvePersona+"-"+perDoc.getId()+archivo);
						perDoc.setEntregado("Entregado");
						servicePersonaDoc.guardar(perDoc);
						 
						return "ok";
					}
				}
			}
		}
		return "error";
				
	}
		
	@GetMapping("/documentacion")
	public String documentacion(Model model, HttpSession session, Authentication authentication) {
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			
			//Extrae los 3 documentos básicos asociados al alumno 1=acta, 2=CURP, 3=Certificado de bachillerato
			List<PersonaDocumento> documentos = servicePersonaDoc.buscarActaCurpCerbachiPorPersona(cvePersona);						
			if (documentos == null || documentos.size() == 0) {	
				model.addAttribute("estadoDocs", null);
			}else{				
				//Sé genera una lista de los estados de los documentos para validar si todos están validados
				//y comprobar así si tiene su expediente completo			
				List<Boolean> docValidado = new ArrayList<>();			
				for (PersonaDocumento personaDocumento : documentos) {				
					docValidado.add(personaDocumento.getValidado());
				}
				
				if (new HashSet<Boolean>(docValidado).size() <= 1) {
					if(documentos.size()==3) {
						model.addAttribute("estadoDocs", documentos.get(0).getValidado());
					}else{
						model.addAttribute("estadoDocs", false);
					}
				}else{
					model.addAttribute("estadoDocs", false);
				}
							
				model.addAttribute("documentos", documentos);
			}
		}
		
		model.addAttribute("alumno", alumno);		
		return "alumno/documentacion";
	}

	@GetMapping("/horarios")
	public String horarios(Model model, HttpSession session, Authentication authentication) {
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			List<Grupo> grupos = serviceGrupo.buscarTodosDeAlumnosOrdenPorPeriodoDesc(alumno.getId());
			model.addAttribute("grupos", grupos);
		
			// Extrae el id del grupo a partir del cveGrupo guardado en sesión
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");
			// Envía el id del grupo para validar si ya se a seleccionado un grupo
			model.addAttribute("cveGrupo", cveGrupo);
			
			if (cveGrupo != null) {					
				//Se extrae la lista de días guardados en la BD				
				List<Dia> dias = serviceDia.buscarDias();				
				model.addAttribute("dias", dias);			
				DateFormat formatoVista = new SimpleDateFormat("HH:mm");
				   DateFormat formatoBusqueda = new SimpleDateFormat("HH:mm:ss");
				   //se buscan las horas de cada dia del profesor
				   List<Horario> horas = serviceHorario.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
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
				    	horarioDia = serviceHorario.buscarPorHoraInicioDiaYGrupo(formatoBusqueda.format(hora.getHoraInicio()), dia.getId(), cveGrupo);
				    	diasClase.add(horarioDia);
				    }
				    horario.setHorarios(diasClase);
				    horarios.add(horario);
				   }		
				   
				   model.addAttribute("horarios", horarios);
				   
			}
										
		}
		
		model.addAttribute("alumno", alumno);
		return "alumno/horarios";
	}

	@GetMapping(path = "/cargar-remediales/{grupo}")
	public String cargarRemediales(@PathVariable(name = "grupo", required = true) String idGrupo,
	Model model, HttpSession session) {
		// Guarda los datos enviados de la vista en variables;
		Integer grupoID = Integer.parseInt(idGrupo);
		
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		//Extrae la lista de remediarles del alumno por grupo	
		List<RemedialAlumno> remediales = serviceRemAlum.buscarPorGrupoYAlumno(grupoID, alumno.getId());
		model.addAttribute("remediales", remediales);
		return "fragments/remediales-alumno :: remedialesAlumno";
	}

	@GetMapping("/informacionGeneral")
	public String informacionGeneral(Model model, HttpSession session) {
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));

		if (alumno != null) {
			// Extrae el último grupo del alumno y se envía al modelo de grupo
			Grupo ultimoGrupo = serviceGrupo.buscarUltimoDeAlumno(alumno.getId());
			
			alumno.setUltimoGrupo(ultimoGrupo);
			//Se extraen la lista de grupos 	
			List<Grupo> grupos = serviceGrupo.buscarTodosDeAlumnosOrdenPorPeriodoDesc(alumno.getId());
						
			DecimalFormat df = new DecimalFormat("###");
			
			List<GrupoDTO> gruposDTO = new ArrayList<>();
			// Se calcula el promedio de cada grupo
			for (Grupo grupo : grupos) {
				
				double promedio = serviceGrupo.obtenerPromedioAlumn(alumno.getId(), grupo.getId());				
				int promedioRed = (int) Math.round(promedio);
				
				GrupoDTO grupoDTO = new GrupoDTO();
				grupoDTO.setIdGrupo(grupo.getId());
				grupoDTO.setNombreGrupo(grupo.getNombre());
				grupoDTO.setPromedio(Double.parseDouble(df.format(promedio)));
				grupoDTO.setPromedioRed(promedioRed);
				grupoDTO.setNombrePeriodo(grupo.getPeriodo().getNombre());
				gruposDTO.add(grupoDTO);
				
			}
			
			//Validación de los documentos del expediente alumno acta, CURP y certificado															
			List<DocumentoDTO> docsExp = servicePersonaDoc.buscarActaCurpCerbachiPorPersonaParaDto(cvePersona);		
			
			// Validación del estado del los documentos			
			List<Boolean> valDoc = new ArrayList<>();			
			for(DocumentoDTO documentoDTO : docsExp){
				valDoc.add(documentoDTO.getValidado());
			}
			//Validación de los documentos del expediente  
			boolean estadoDocs = false;			
			if(new HashSet<Boolean>(valDoc).size() <= 1) {
				boolean validado = valDoc.get(0);
				if(validado==true) {
					estadoDocs = true;
					model.addAttribute("estadoDocs", estadoDocs);
				}
			}
			model.addAttribute("estadoDocs", estadoDocs);
			model.addAttribute("docsExp", docsExp);
				
			if(ultimoGrupo!=null) {
				AlumnoGrupo alumnoGrupo = serviceAlumGrupo.buscarPorAlumnoYGrupo(alumno, ultimoGrupo);
				Boolean reinscripcion = null;
				boolean FechaInscripcion = false;
				Date fechaHoy = new Date();	
				//se valida que el alumno no este incrito en el grupo actual		
				if(alumnoGrupo!=null && alumnoGrupo.getFechaInscripcion() == null) {									
					if(ultimoGrupo.getPeriodo().getFinInscripcion()!=null && ultimoGrupo.getPeriodo().getFinInscripcion().after(fechaHoy)) {
						reinscripcion = false;
						if(grupos.size()>1) {
							
							//lista de adeudos 										
							int cantidadAdeudos = servicePagoGeneral.contarPorAlumnoYStatus(alumno.getId(), 0);
							//promedio del grupo anterior
							Grupo penultimoGrupo = serviceGrupo.buscarPorAlumnoPenultimoGrupo(alumno.getId());
							double promedio = serviceGrupo.obtenerPromedioAlumn(alumno.getId(), penultimoGrupo.getId());				
							int promedioRed = (int) Math.round(promedio);
							
							//Se valida si hay un convenio para alguno que extiende el paso de entrega de documentos
							boolean convenio = false; 
							for(DocumentoDTO doc :docsExp) {	
								if (doc.getConvenio()!=null) {
									
								if(doc.getConvenio() == true) {
									convenio = true;
								}
								}
							}	
							//Validacion de los requisitos de reinscripcion
							if(ultimoGrupo.getPeriodo().getId() >= 10 || convenio == true) {											
								if((alumnoGrupo.getFechaInscripcion() == null) && (cantidadAdeudos == 0) && (promedioRed >= 8)) {							
									reinscripcion = true;
								}						
							}else{						
								//Validación de los requisitos para la reinscripción
								if((alumnoGrupo.getFechaInscripcion() == null) && (estadoDocs == true) && (cantidadAdeudos == 0) && (promedioRed >= 8)) {							
									reinscripcion = true;
								}													
							}	
							
						}																
					}
					
				}else{									
						FechaInscripcion = true;
					}			
				model.addAttribute("FechaInscripcion", FechaInscripcion);
				model.addAttribute("reincripsion", reinscripcion);
				model.addAttribute("grupos", gruposDTO);
			}
			model.addAttribute("grupo", ultimoGrupo);
		}
		model.addAttribute("alumno", alumno);
		return "alumno/informacionGeneral";
	}

	@GetMapping("/adeudos")
	public String adeudos(Model model, HttpSession session, Authentication authentication) {
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			// List<Adeudo> pagos = serviceAdeudo.buscarPagosPorPersona(persona);
			List<PagoGeneral> adeudos = servicePagoGeneral.buscarPorAlumno(alumno.getId(), 0);
			// Calcula el adeudo total
			Double suma = 0.0;
			for (PagoGeneral pagoGeneral : adeudos) {
				suma = suma + pagoGeneral.getMonto();
			}
			
			Boolean periodoAct = false;
			Date fechaHoy = new Date();	
			Periodo periodo = periodoService.buscarUltimo();
			
			if(periodo.getFinInscripcion()!=null && periodo.getFinInscripcion().after(fechaHoy)) {
				periodoAct = true;
			}
			
			//busca si tiene una prorroga de adeudos
			ProrrogaAdeudo prorroga = prorrogaAdeudoService.buscarUltimaPorPersona(cvePersona);
			if(prorroga!=null && prorroga.getFechaCompromiso().after(fechaHoy)) {
				periodoAct = true;
			} 
			
			model.addAttribute("totalAdeudo", suma);
			model.addAttribute("adeudos", adeudos);
			model.addAttribute("periodoAct", periodoAct);
		}
		
		model.addAttribute("alumno", alumno);
		return "alumno/adeudos";
	}

	@GetMapping("/pagos")
	public String pagos(Model model, HttpSession session, Authentication authentication) {
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			// List<Adeudo> pagos = serviceAdeudo.buscarPagosPorPersona(persona);
			List<PagoGeneral> pagos = servicePagoGeneral.buscarPorAlumno(alumno.getId(), 1);
			// Calcula el adeudo total
			Double suma = 0.0;
			for (PagoGeneral pagoGeneral : pagos) {
				suma = suma + pagoGeneral.getMonto();
			}
	
			model.addAttribute("pagoTotal", suma);
			model.addAttribute("pagos", pagos);
		}
		
		model.addAttribute("alumno", alumno);
		return "alumno/pagos";
	}

	@GetMapping("/manual")
	public String manual() {
		return "alumno/manual";
	}
	
	@GetMapping("/referencia-pago")
	public String referenciaPago(Model model, HttpSession session, Authentication authentication) {
		// Carga el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		// Extrae el adeudo seleccionado que se cargó en sesión
		Integer idAdeudo = (Integer) session.getAttribute("cveAdeudo");
		PagoGeneral adeudo = servicePagoGeneral.buscarPorId(idAdeudo);
		Date fechaHoy = new Date();
		
		// Válida si la referencia fondos está vacía	
		if (adeudo.getReferenciaFondos() == null || adeudo.getReferenciaFondos().isEmpty()) {			
			
			ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();			
			refereciaDTO.setCarrera(alumno.getCarreraInicio());			
			refereciaDTO.setMatricula(alumno.getMatricula());			
			refereciaDTO.setPago(adeudo.getMonto());			
			String cadena = generarReferenciaFondos.referenciaFondos(refereciaDTO);				
			adeudo.setReferenciaFondos(cadena);			
			servicePagoGeneral.guardar(adeudo);
			
		}else {
			
		}
		
		//Se valida si el adeudo ya tienen una referencia bancaria		 
		if (adeudo.getReferencia() == null || adeudo.getReferencia().isEmpty()) {							
			
			//Extrae la fecha actual y le suma 3 días
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");				
			Date dt = new Date();		        		        
	        Calendar c = Calendar.getInstance();
	        c.setTime(dt);
	        c.add(Calendar.DATE, 30);		        
	        String fechaLimite = format.format(c.getTime());
	        Date fechaLimiteP = c.getTime();
	        		        		      		   
			ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();			
			refereciaDTO.setCarrera(alumno.getCarreraInicio());			
			refereciaDTO.setMatricula(alumno.getMatricula());
			refereciaDTO.setFechaLimite(fechaLimite);
			refereciaDTO.setPago(adeudo.getMonto());	
			String cadena = generarReferenciaSEP.generaReferencia(refereciaDTO);	
			
			adeudo.setReferencia(cadena);
			adeudo.setFechaLimite(fechaLimiteP);
			servicePagoGeneral.guardar(adeudo);
			
		}else{
			//Se valida si la fecha limite de pago			
			if (adeudo.getFechaLimite().before(fechaHoy)) {										
				//Extrae la fecha actual y le suma 3 días
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");				
				Date dt = new Date();		        		        
		        Calendar c = Calendar.getInstance();
		        c.setTime(dt);
		        c.add(Calendar.DATE, 30);		        
		        String fechaLimite = format.format(c.getTime());
		        Date fechaL = c.getTime();
		        		        		      		   
				ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();			
				refereciaDTO.setCarrera(alumno.getCarreraInicio());				
				refereciaDTO.setMatricula(alumno.getMatricula());				
				refereciaDTO.setFechaLimite(fechaLimite);
				refereciaDTO.setPago(adeudo.getMonto());
				
				String cadena = generarReferenciaSEP.generaReferencia(refereciaDTO);
				adeudo.setReferencia(cadena);
				adeudo.setFechaLimite(fechaL);
				servicePagoGeneral.guardar(adeudo);
				
			}
			
		}
		
		// Extrae el monto del adeudo y lo convierte a letras
		String montoLetras = NumberToLetterConverter.convertNumberToLetter(adeudo.getMonto());
		model.addAttribute("montoLetras", montoLetras);
		
		Periodo pe = periodoService.buscarUltimo();
		String periodo = pe.getNombre();
		model.addAttribute("periodo", periodo);

		// Extrae la fecha actual y le da formato
		LocalDate fecha = LocalDate.now();
		String fechaActual = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		model.addAttribute("fechaActual", fechaActual);
		model.addAttribute("adeudo", adeudo);
		
		return "alumno/referencia-pago";
	}

	@GetMapping("/referencia-multiple-pago")
	public String referenciaMultiplePago(Model model, HttpSession session, Authentication authentication) {
		// carga el usuario apartir del usuario cargado en cesion.
				Usuario usuario = (Usuario) session.getAttribute("usuario");
				int cvePersona;
				try {
					cvePersona = (Integer) session.getAttribute("cvePersona");
				} catch (Exception e) {
					cvePersona = usuario.getPersona().getId();
				}
				
				Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
				
				Date fechaHoy = new Date();		
				try {
					if (alumno != null) {
						// Extrae el ultmi grupo
						Grupo ultimoGrupo = serviceGrupo.buscarUltimoDeAlumno(alumno.getId());

						List<PagoGeneral> adeudos = servicePagoGeneral.buscarPorAlumno(alumno.getId(), 0);

						// Se recorre la lista de adeudos para extraer las referesias fondos y
						// comparar todas son iguales
						List<String> referenciasFondos = new ArrayList<>();
						for (PagoGeneral pagoGeneral : adeudos) {
							referenciasFondos.add(pagoGeneral.getReferenciaFondos());
						}
						
						// valida que todas las refencias de fodos sean iguales
						if (new HashSet<String>(referenciasFondos).size() <= 1) {

							PagoGeneral adeudo = adeudos.get(0);
							
							//suma de adeudos para referencia 
							double suma = 0.0;
							for (PagoGeneral pagoGeneral : adeudos) {
								suma = suma + pagoGeneral.getMonto();
							}

							if (adeudo.getReferenciaFondos() == null || adeudo.getReferenciaFondos().isEmpty()) {
								ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();
								refereciaDTO.setCarrera(ultimoGrupo.getCarrera());
								refereciaDTO.setMatricula(alumno.getMatricula());
								refereciaDTO.setPago(suma);
								String cadena = generarReferenciaFondos.referenciaFondos(refereciaDTO);
								for (PagoGeneral pagoGeneral : adeudos) {
									pagoGeneral.setReferenciaFondos(cadena);
									servicePagoGeneral.guardar(pagoGeneral);
								}
							}
						} else {
							
							//suma de adeudos para referencia 
							double suma = 0.0;
							for (PagoGeneral pagoGeneral : adeudos) {
								suma = suma + pagoGeneral.getMonto();
							}
							
							ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();
							refereciaDTO.setCarrera(ultimoGrupo.getCarrera());
							refereciaDTO.setMatricula(alumno.getMatricula());
							refereciaDTO.setPago(suma);
							String cadena = generarReferenciaFondos.referenciaFondos(refereciaDTO);
							for (PagoGeneral pagoGeneral : adeudos) {
								pagoGeneral.setReferenciaFondos(cadena);
								servicePagoGeneral.guardar(pagoGeneral);
							}
						}

						// Se recorrere la lista de adeudos para extraer las referecias de pago y
						// comparar que todas son iguales
						List<String> referencias = new ArrayList<>();
						for (PagoGeneral pagoGeneral : adeudos) {
							referencias.add(pagoGeneral.getReferencia());
						}

						// se valida si todos los adeudos tiene la misma referencia de pago
						if (new HashSet<String>(referencias).size() <= 1) {
							// extrae uno de los adeudos para determinar la fecha de pago y referencia
							// ya que todos los adeudos comparter la misma referencia de pago
							PagoGeneral adeudo = adeudos.get(0);

							if (adeudo.getFechaLimite() == null || adeudo.getFechaLimite().before(fechaHoy)) {
								// Calcula el adeudo total
								Double suma = 0.0;
								for (PagoGeneral pagoGeneral : adeudos) {
									suma = suma + pagoGeneral.getMonto();
								}

								// extrae la fecha actual y le suma 30 dias
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
								Date dt = new Date();
								Calendar c = Calendar.getInstance();
								c.setTime(dt);
								c.add(Calendar.DATE, 30);
								String fechaLimite = format.format(c.getTime());
								Date fechaLimiteP = c.getTime();

								ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();
								refereciaDTO.setCarrera(ultimoGrupo.getCarrera());
								refereciaDTO.setMatricula(alumno.getMatricula());
								refereciaDTO.setFechaLimite(fechaLimite);
								refereciaDTO.setPago(suma);
								String cadena = generarReferenciaSEP.generaReferencia(refereciaDTO);

								for (PagoGeneral pagoGeneral : adeudos) {
									pagoGeneral.setReferencia(cadena);
									pagoGeneral.setFechaLimite(fechaLimiteP);
									servicePagoGeneral.guardar(pagoGeneral);
								}
							}

						} else {
							// Calcula el adeudo total
							Double suma = 0.0;
							for (PagoGeneral pagoGeneral : adeudos) {
								suma = suma + pagoGeneral.getMonto();
							}

							// extrae la fecha actual y le suma 7 dias
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							Date dt = new Date();
							Calendar c = Calendar.getInstance();
							c.setTime(dt);
							c.add(Calendar.DATE, 30);
							String fechaLimite = format.format(c.getTime());
							Date fechaLimiteP = c.getTime();

							ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();
							refereciaDTO.setCarrera(ultimoGrupo.getCarrera());
							refereciaDTO.setMatricula(alumno.getMatricula());
							refereciaDTO.setFechaLimite(fechaLimite);
							refereciaDTO.setPago(suma);
							String cadena = generarReferenciaSEP.generaReferencia(refereciaDTO);

							for (PagoGeneral pagoGeneral : adeudos) {
								pagoGeneral.setReferencia(cadena);
								pagoGeneral.setFechaLimite(fechaLimiteP);
								servicePagoGeneral.guardar(pagoGeneral);
							}
						}

						// buelbe a consultar la lista de adeudos por si estos fueron actulizados
						List<PagoGeneral> adeudosActulizado = servicePagoGeneral.buscarPorAlumno(alumno.getId(), 0);

						PagoGeneral adeudo = adeudosActulizado.get(0);

						model.addAttribute("ultimoGrupo", ultimoGrupo);
						String periodo = ultimoGrupo.getPeriodo().getNombre();
						model.addAttribute("periodo", periodo);

						// Calcula el adeudo total
						Double suma = 0.0;
						for (PagoGeneral pagoGeneral : adeudos) {
							suma = suma + pagoGeneral.getMonto();
						}

						// extrae el monto del adeudo y lo comvierte a letras
						String montoLetras = NumberToLetterConverter.convertNumberToLetter(suma);
						model.addAttribute("montoLetras", montoLetras);

						model.addAttribute("adeudo", adeudo);
						model.addAttribute("totalAdeudo", suma);
						model.addAttribute("adeudos", adeudos);
						//se crea el objeto de persona referencia
						PersonaReferencia peRef = personaReferenciaService.buscarPorReferencia(adeudo.getReferencia());
						if(peRef == null) {
							peRef = new PersonaReferencia();
						}
						String conceptos = ""; // cadena de concepto de todos los pagos(como en pago persona)
						for (PagoGeneral ad : adeudosActulizado) {
							String cadena = "";
							cadena += ad.getConcepto().getId().toString() + "._."; //concepto
							cadena += ad.getCantidad().toString() + "._."; //cantidad
							cadena += ad.getMonto().toString() + "._."; //monto o importe
							cadena += ad.getDescuento().toString() + "._."; //descuento
							if (ad.getPagoAsignatura() != null) {
								cadena += ad.getPagoAsignatura().getCargaHoraria().getId() + "._."; //si es nive (cargaHoraria.id)
								cadena += ad.getPagoAsignatura().getIdCorteEvaluativo() + "._."; //corte_evaluativo (id)
							} else {
								cadena += "0" + "._."; // carga_horaria
								cadena += "0"; // corte
							}
							// para separar las cadenas
							cadena += "A";
							// se añade la cadena a los conceptos para guardarlos todos
							conceptos += cadena;
						}
						//se guarda la referencia y fecha limite en el objeto de personaReferencia
						peRef.setReferencia(adeudo.getReferencia());
						peRef.setReferenciaFondos(adeudo.getReferenciaFondos());
						peRef.setFechaVencimiento(adeudo.getFechaLimite());
						// se guarda el registro de la persona referencia
						peRef.setTotal(0.0);
						peRef.setImporte(suma);
						peRef.setConceptos(conceptos);
						peRef.setFechaAlta(fechaHoy);
						peRef.setFechaPago(null);
						peRef.setPagado(false);
						peRef.setFolioPago("");
						peRef.setPersona(new Persona(cvePersona));
						//se guarda el objeto de persona referencia
						personaReferenciaService.guardar(peRef);
					}
				} catch (NullPointerException e) {
					// TODO: handle exception
				}
				model.addAttribute("alumno", alumno);
		return "alumno/referencia-multiple-pago";
	}

	@GetMapping("/recibo")
	public String recibopago(Model model, HttpSession session, Authentication authentication) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			// Extrae el pago seleccionado a partir del cvePago guardado en sesión
			Integer idPago = (Integer) session.getAttribute("cvePago");			
			PagoGeneral pago = servicePagoGeneral.buscarPorId(idPago);			
			model.addAttribute("pagoSeleccionado", pago);
			
			// Extrae el último grupo
			Grupo ultimoGrupo = serviceGrupo.buscarUltimoDeAlumno(alumno.getId());
			model.addAttribute("ultimoGrupo", ultimoGrupo);
			
			// Se actualiza de acuerdo a si ahí uno o más adeudos asociados al folio
			Double totalPago = 0.0;
			String folioCifrado = "";
			if(pago.getFolio() == null) {
				//Como el folio es null solo se carga el pago seleccionado 
				//ya que otra forma traería todos los pagos cuyo folio sea null 							
				model.addAttribute("pagosFolio", pago);				
				totalPago = pago.getMonto();	
				//El folio cifrado no se actualiza, ya que es null y no se puede cifrar				
			}else {
				//extrae la lista de pagos con el mismo folio			
				List<PagoGeneral> pagosFolio = servicePagoGeneral.buscarPorAlumnoYFolio(alumno.getId(), pago.getFolio(), 1);
				model.addAttribute("pagosFolio", pagosFolio);
				
				for (PagoGeneral pagoF : pagosFolio) {
					totalPago = totalPago + pagoF.getMonto();
				}	
				
				//Codifica el folio 			
				try {
					String folio = pago.getFolio();			
					folioCifrado = CodificarTexto.encriptAES(folio);								
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
			//extrae el monto del adeudo y lo convierte a letras la lista de pagos con el mismo folio
			String montoLetras = NumberToLetterConverter.convertNumberToLetter(totalPago);
			List<String> dos = new ArrayList<>();
			dos.add("");
			dos.add("RPt-3");
			
			model.addAttribute("montoLetras", montoLetras);
			model.addAttribute("totalPago", totalPago);	
			model.addAttribute("folioCifrado", folioCifrado);			
			model.addAttribute("dos", dos);
		}
		model.addAttribute("alumno", alumno);
		return "alumno/recibo";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Servicios Escolares')")
    @PostMapping(path = "/reserva-matricula", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String reservaMatricula(@RequestBody AlumnoDTO dto) {
		Alumno alumno = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mazatlan"));
		Date fechaNac = null;
		try {
			fechaNac = formatter.parse(dto.getFechaNacimiento());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(dto.getIdAlumno()>0) {
    		alumno = serviceAlumno.buscarPorId(dto.getIdAlumno());
    		alumno.getPersona().setNombre(dto.getNombre());
    		alumno.getPersona().setPrimerApellido(dto.getPrimerApellido());
    		alumno.getPersona().setSegundoApellido(dto.getSegundoApellido());
    		alumno.getPersona().getDatosPersonales().setCurp(dto.getCurp());
    		alumno.getPersona().getDatosPersonales().setFechaNacimiento(fechaNac);
    		alumno.getPersona().setEmail(dto.getEmail());
    		alumno.getPersona().getDatosPersonales().setCelular(dto.getCelular());
    		if (dto.getIdTurno()!=null) {
    			alumno.setTurno(new Turno(dto.getIdTurno()));				
			}
    		if(alumno.getCarreraInicio().getId() != dto.getIdCarrera()) {
    			
    			//Esto indica que se mando desde prospectosAceptados y genera un adeudo
    			//Queda como pendiente porque no existe un adeudo para poder cambiar el grupo del alumno
    			if(dto.getPaginaDatos() ==1) {
    				//PagoGeneral pagoGeneral = new PagoGeneral();
    			}
    			String[] textoSeparado = alumno.getMatricula().split("-");
    			String clave = carrerasService.buscarPorId(dto.getIdCarrera()).getClave();
    			//Se busca el numero de la siguiente matricula
    			String numMatricula = serviceAlumno.buscarMatriculaSiguiente(clave+'-'+textoSeparado[1].substring(0,2));
    			String matricula=(clave+'-'+textoSeparado[1].substring(0,2)+numMatricula);
    			//Se inserta la matricula en la tabla de descontinuos
    			serviceAlumno.insertarMatriculaEnReserva(alumno.getMatricula());
    			alumno.setMatricula(matricula);
    			alumno.setCarreraInicio(new Carrera(dto.getIdCarrera()));
    			
    		}
    		
    		if(dto.getGrupo()!=null) {
    			
    		AlumnoGrupo alGrupo = alumnoGrService.buscarPorIdAlumnoYidGrupo(alumno.getId(), dto.getIdGrupo());
    			if (alGrupo==null) {
					alGrupo = alumnoGrService.buscarPrimerGrupoProspecto(alumno.getId());
					alGrupo.setGrupo(new Grupo(dto.getIdGrupo()));
					alumnoGrService.guardar(alGrupo);
				}
    		}
    		
    		serviceAlumno.guardar(alumno);
    	}
		return "ok";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Servicios Escolares')")
    @GetMapping("/cargar-alumno/{dato}")
   	public String vercomo(@PathVariable(name = "dato", required = false) String dato,  Model model) { 
    	List<Alumno> alumnos = new ArrayList<>();
    	if(dato!=null) {
    		alumnos = serviceAlumno.buscarPorNombreOMatricula(dato);
    	}else {
    		alumnos = serviceAlumno.buscarTodos();
    	}
		model.addAttribute("alumnos", alumnos);
		return "fragments/control-alumnos :: lista-alumnos";
   	}
	
	//método para cambiar nombre del alumno 
	 @PostMapping(path = "/cambiar-nombre", consumes = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseBody
	 public String cambiarNombreAlumno(@RequestBody Map<String, String> obj, HttpSession session) {
	  Integer idAlumno =Integer.parseInt(obj.get("idAlumno"));
	  String cadena = obj.get("cadena");
	  String operacion = obj.get("operacion");
	  //se construye la persona para editar 
	  Persona persona = personaService.buscarPorId(idAlumno);
	  switch(operacion) {
	  case "nom" : 
	   persona.setNombre(cadena);
	   break;
	  case "pa" : 
	   persona.setPrimerApellido(cadena);
	   break;
	  case "sa" : 
	   persona.setSegundoApellido(cadena);
	   break;
	  }
	  //se guarda la persona 
	  personaService.guardar(persona);
	  return "ok";
	 }
	 
	@PostMapping(path = "/reinscripcion-alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String reinscripcionAlumno(@RequestBody Map<String, Integer> obj, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));		
		Integer idGrupo = obj.get("idGrupo");
		Grupo ultimoGrupo = serviceGrupo.buscarUltimoDeAlumno(alumno.getId());
		
		if(ultimoGrupo.getId().toString().equals(idGrupo.toString())) {						
			AlumnoGrupo ag = alumnoGrService.checkInscrito(alumno.getId(), ultimoGrupo.getPeriodo().getId());		
			if(ag == null) {
				return "inv";
			}
			else {
				ag.setFechaInscripcion(new Date());
				alumnoGrService.guardar(ag);
			}
			return "ok";
		}
		return "fail";
	}
	
	@GetMapping("/tutorias")
	public String tutoriasNoAprobadas(Model model, HttpSession session) {
		Date fechaHoy = new Date();
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		if(alumno!=null) {
			//tutorias no aprobadas
			List<TutoriaIndividual> naTutorias = tutoriaIndService.buscarPorAlumnoYValidada(alumno, false);
			//tutorias aprobadas
			List<TutoriaIndividual> aTutorias = tutoriaIndService.buscarPorAlumnoYValidada(alumno, true);
			//busqueda de tutorias programadas del ultimo grupo asociado al alumno
			Grupo grupo = serviceGrupo.buscarUltimoDeAlumno(alumno.getId());
			List<ProgramacionTutoria> ptutorias = programacionTutoriaService.buscarPorAlumnoYGrupo(alumno, grupo);
			Boolean tp = false;
			ProgramacionTutoria pTutoria = null;
			List<TutoriaIndividual> tutorias = tutoriaIndService.buscarPorAlumno(alumno);
			
			if(ptutorias.size()>0) {
				pTutoria = ptutorias.get(0);
				for(TutoriaIndividual tutoria : tutorias) {
					//Se valida si hay algun registro de tutoria impartida, el dia en el que el profesor le agendo una al alumno
					if(ptutorias.get(0).getFecha().equals(tutoria.getFechaTutoria())){
						pTutoria = null;
					//se valida si la fecha en la que se agendo al tutoria ya paso
					}else if(ptutorias.get(0).getFecha().after(fechaHoy)){
						tp=true;
					}
				}
			}
	
			model.addAttribute("naTutorias", naTutorias);
			model.addAttribute("aTutorias", aTutorias);
			model.addAttribute("pTutoria", pTutoria);
			model.addAttribute("pTutoriaPasada", tp);
		}
		model.addAttribute("alumno", alumno);
		return "alumno/tutorias";		
	}
	
	@PostMapping(path="/aprobar-tutoria", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String aprobarTutoria(@RequestBody Map<String, String> obj, HttpSession session) {
		Integer cveTutoria = 0;
		try {
			cveTutoria = Integer.parseInt(obj.get("id"));
		} catch (Exception e) {
			cveTutoria = 0;
		}
		
		if(cveTutoria!=0) {
			TutoriaIndividual tutoria = tutoriaIndService.buscarPorId(cveTutoria);
			tutoria.setValidada(true);
			tutoriaIndService.guardar(tutoria);
			return "ok";
		}
		return "error";
	}
	
	@PostMapping(path="/validar-contra", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String validarContrasena(@RequestBody Map<String, String> obj) {
		String contra = obj.get("contra");
		String matricula = obj.get("matricula");
		if(matricula!=null && !matricula.isEmpty() && contra!=null) {
			Usuario usuario = usuarioService.buscarPorUsuario(matricula);
			Boolean valContra =  passwordEncoder.matches(contra, usuario.getContrasenia());
			if(valContra == true) {
				return "ok";
			}	
		}
		return "Error";
	}
}
