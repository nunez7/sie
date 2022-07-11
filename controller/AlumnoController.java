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
import edu.mx.utdelacosta.model.MecanismoAlumno;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaDocumento;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.model.Turno;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.CorteEvaluativoDTO;
import edu.mx.utdelacosta.model.dto.DiaDTO;
import edu.mx.utdelacosta.model.dto.DocumentoDTO;
import edu.mx.utdelacosta.model.dto.GrupoDTO;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.MesDTO;
import edu.mx.utdelacosta.model.dto.ReferenciaBanamexDTO;
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
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
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
	
	@Value("${siestapp.ruta.docs}")
    private String ruta;
	
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
			alumnoDTO.setIdTurno(alumno.getTurno().getId());
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
		// carga el usuario apartir del usuario cargado en cesion.
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
			
			// Extrae el id del grupo a partir del cveGrupo guardado en cesion
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");
			// Envía el id del grupo para validar si ya se a seleccionado un grupo
			model.addAttribute("cveGrupo", cveGrupo);
			
			//----------------			
			if (cveGrupo != null) {
				Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);
				
				List<CorteEvaluativo> corteEvaluativos = serviceCorte.buscarPorPeridoYCarreraFechaInicioAsc(grupo.getPeriodo(), grupo.getCarrera());								
				
				List<CorteEvaluativoDTO> corteDto = new ArrayList<>();								
				
				Integer c = 0;
				for (CorteEvaluativo corte : corteEvaluativos) {
					
					CorteEvaluativoDTO cortesEvaluativosDTO = new CorteEvaluativoDTO();
					cortesEvaluativosDTO.setIdCorte(corte.getId());
					cortesEvaluativosDTO.setFechaInicio(corte.getFechaInicio());
					cortesEvaluativosDTO.setFechaFin(corte.getFechaFin());
					cortesEvaluativosDTO.setConsecutivo(corte.getConsecutivo());			
					corteDto.add(cortesEvaluativosDTO);
					
					List<Date> meses = serviceAsistencia.mesesEntreFechaInicioYFechaFinAsc(corte.getFechaInicio(), corte.getFechaFin());
					
					Integer n = 0;
					Integer zm = meses.size();
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
						List<Date> dias = new ArrayList<>();
						if(zm==1) {
							dias = serviceAsistencia.diasEntreFechaInicioYFechaFin(sdf.format(corte.getFechaInicio()), sdf.format(corte.getFechaFin()));							
						}else{								
							if(c==0) {
								if (n==(zm-1)){
									dias = serviceAsistencia.diasEntreFechaInicioYFechaFin(primerDia, sdf.format(corte.getFechaFin()));
								}else{
									dias = serviceAsistencia.diasEntreFechaInicioYFechaFin(primerDia, ultimoDia);
								}
								
							}else{
								if (n==0){
									dias = serviceAsistencia.diasEntreFechaInicioYFechaFin(sdf.format(corte.getFechaInicio()), ultimoDia);
								}else{
									dias = serviceAsistencia.diasEntreFechaInicioYFechaFin(primerDia, ultimoDia);
								}
							}																				
						}
							
						List<DiaDTO> diasDto = new ArrayList<>();
                        for (Date dia : dias) {
                        	
                            DiaDTO diaDto = new DiaDTO();
                            diaDto.setDia(dia);
                            diasDto.add(diaDto);
                            mesDTO.setDias(diasDto);                          
                            
                        } 
						++n;
					}
					++c;
				}
				//----------------
				
				// Se extraen la carga horaria a partir del grupo seleccinado				
				List<CargaHoraria> cargasHor = serviceCargaHor.buscarPorGrupo(grupo);				

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
		// carga de los grupos asociados al usuario(alumno).
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));

		// extraer los obgetos cargaHoraria del id recividos
		CargaHoraria cargaHoraria = serviceCargaHor.buscarPorIdCarga(cargaID);

		// extrae los datos del mecanismo alumno para validar el mecanismo de evaluasion
		// ya a sido aseptado por el alumno
		MecanismoAlumno mecanismo = serviceMecaAlum.buscarPorAlumnoYCargaHoraria(alumno, cargaHoraria);

		// Valida si validar mecanismo ya existe para aseptarlo
		if (mecanismo != null) {
			boolean acepto = true;
			Date fecha = new Date();
			mecanismo.setFechaAcepto(fecha);
			mecanismo.setAcepto(acepto);
			serviceMecaAlum.guardar(mecanismo);

			// Carga las calificaciones de los dos cortes que puede haber por carga
			List<CalificacionCorte> calificacionesCorte = serviceCalCorte.buscarPorCargaYAlumno(cargaHoraria, alumno);
			model.addAttribute("CalCortes", calificacionesCorte);
			
			// extrae las calificaciones por intrumento
			List<Calificacion> calInstrumento = serviceCalificacion.buscarPorAlumnoYCargaH(alumno.getId(), cargaID);
			model.addAttribute("calInstru", calInstrumento);
					
			// Se extraen el grupo a partir del grupo cargado en cesion
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");
			Grupo grupo = serviceGrupo.buscarPorId(cveGrupo);

			// Lista de cortes asociados a la carga horaria seleccinada y a la carrera
			List<CorteEvaluativo> cortes = serviceCorte.buscarPorPeridoYCarreraFechaInicioAsc(cargaHoraria.getPeriodo(),grupo.getCarrera());
			
			//defincion del nivel de cada corte evaluativo		
			String nivel1 = "";
			String nivel2 = "";
			for(CorteEvaluativo corte : cortes) {			
				List<RemedialAlumno> remedialesCor = serviceRemAlum.buscarPorAlumnoCargaYCorte(alumno, cargaHoraria, corte);
				if(remedialesCor.size() == 0) {
					if (corte.getConsecutivo() == 1) {
						nivel1 = "ordinario";
					}else{
						nivel2 = "ordinario";
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
		// Gurda los datos enviados de la vista en variables;
		Integer cargaID = Integer.parseInt(idCarga);
		
		// se genera el obgeto usuario a partir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));

		// extraer la carga Horaria apartir del id recivido
		CargaHoraria cargaHoraria = serviceCargaHor.buscarPorIdCarga(cargaID);		

		// extrae los datos del mecanismo alumno para validar el mecanismo de evaluasion
		// ya a sido aseptado por el alumno
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

		// valida si el mecanismo ya ha sido aseptado
		if (validarMecanismo.getAcepto() == true) {
			// Carga las calificaciones de los dos cortes que puede haber por carga
			List<CalificacionCorte> calificacionesCorte = serviceCalCorte.buscarPorCargaYAlumno(cargaHoraria, alumno);

			// extrae las calificaciones por intrumento
			List<Calificacion> calInstrumento = serviceCalificacion.buscarPorAlumnoYCargaH(alumno.getId(), cargaID);

			model.addAttribute("calInstru", calInstrumento);
			model.addAttribute("CalCortes", calificacionesCorte);
		} else {
			model.addAttribute("idAlumno", alumno.getId());
			model.addAttribute("idCarga", cargaID);
		}

		// Se extrae el grupo cargado en cesion
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
					nivel1 = "ordinario";
				}else{
					nivel2 = "ordinario";
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
		// carga el usuario apartir del usuario cargado en cesion.
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
			
			// Se extrae el id del grupo a partir del id del grupo guardado en cesion			
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");			
			// Envía el id del grupo a la vista para validar si ya se a seleccionado un grupo
			model.addAttribute("cveGrupo", cveGrupo);		
			if (cveGrupo != null) {					
				// Se extraen la carga horaria a partir del grupo seleccinado				
				List<CargaHoraria> cargasHor = serviceCargaHor.buscarPorGrupo(new Grupo(cveGrupo));
				model.addAttribute("cargasHor", cargasHor);			
			}
		}
		model.addAttribute("alumno", alumno);
		return "alumno/calificaciones";
	}
	
	@PatchMapping(path = "/enviar-documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody	
	public String guardarDocumento(@RequestParam("documento") MultipartFile multiPart, @RequestParam("idDocumento") String idDocumento, HttpSession session) {				 
		// carga el usuario apartir del usuario cargado en cesion.
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
					PersonaDocumento personaDoc = new PersonaDocumento();
					personaDoc.setPersona(new Persona(cvePersona));
					personaDoc.setDocumento(new Documento(cveDocumento));	
					personaDoc.setEntregado("No Entregado");
					personaDoc.setValidado(false);
					personaDoc.setPrestado(false);
					personaDoc.setUrlPdf(null);
					servicePersonaDoc.guardar(personaDoc);					 
				}else{
					if (perDoc.getValidado() == true) {
						return "aceptado";
					}					
				}
				
				perDoc = servicePersonaDoc.buscarPorPersonaYdocumento(new Persona(cvePersona), new Documento(cveDocumento));
				
				if(perDoc.getUrlPdf() == null) {
					
					String archivo = SubirArchivo.guardarArchivo(multiPart, ruta+"/alumnos/inscripcion/"+cvePersona+"-"+perDoc.getId());
					if (archivo != null) {				
						//actualizar la ruta del archivo y se canvia el estado en entregado
						perDoc.setUrlPdf(cvePersona+"-"+perDoc.getId()+archivo);
						perDoc.setEntregado("Entregado");
						servicePersonaDoc.guardar(perDoc);
						 
						return "ok";
					}
					
				}else{				
					SubirArchivo.borrarArchivo(ruta+"/alumnos/inscripcion/"+perDoc.getUrlPdf());
					String archivo = SubirArchivo.guardarArchivo(multiPart, ruta+"/alumnos/inscripcion/"+cvePersona+"-"+perDoc.getId());
					if (archivo != null) {				
						//actualizar la ruta del archivo y se canvia el estado en entregado
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
		// carga el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			
			//extrae los 3 docuemtos basicos asociados al alumno 1=acta, 2=CURP, 3=Certificado de bachillerato
			List<PersonaDocumento> documentos = servicePersonaDoc.buscarActaCurpCerbachiPorPersona(cvePersona);			
			
			if (documentos == null || documentos.size() == 0) {	
				model.addAttribute("estadoDocs", null);
			}else{
				//Se generar una lista de los estados de los documentos para validar si todos estan validados
				//y comprobar asi si tiene su expediente completo			
				List<Boolean> docValidado = new ArrayList<>();			
				for (PersonaDocumento personaDocumento : documentos) {				
					docValidado.add(personaDocumento.getValidado());
				}
				
				if (new HashSet<Boolean>(docValidado).size() <= 1) {
					model.addAttribute("estadoDocs", documentos.get(0).getValidado());
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
		// carga el usuario apartir del usuario cargado en cesion.
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
		
			// Extrae el id del grupo a partir del cveGrupo guardado en cesion
			Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");
			// Envía el id del grupo para validar si ya se a seleccionado un grupo
			model.addAttribute("cveGrupo", cveGrupo);
			
			if (cveGrupo != null) {					
				//se extrae la lista de dias guardados en la BD				
				List<Dia> dias = serviceDia.buscarDias();				
				model.addAttribute("dias", dias);			
				
				//formato de hora 				
				DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss"); 
				
				//Se extrae una lista de las horas unicas de la lista de horas asociadas al grupo			
				List<Horario> horas = serviceHorario.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
				model.addAttribute("horas", horas);
				
				//se crea una lista vacia para colocarle los datos de las horas de calse				
				List<HorarioDTO> horasDto = new ArrayList<>();
				
				//crea el horario con las horarios vinculados al grupo				
				for (Horario hora : horas) {
															
					for (Dia dia : dias) {
						String horaI = dateFormat.format(hora.getHoraInicio());				
						//se genera el horario al compara la lista de horas unicas y la lista de dias 						
						List<Horario> horario = serviceHorario.buscarPorHoraInicioDiaYGrupo(horaI, dia.getId(), cveGrupo);						
						for(Horario hr : horario) {
							HorarioDTO horaDto = new HorarioDTO();						
							if (horario == null) {							
								horaDto.setHoraInicio("");
								horaDto.setHoraFin("");
								horaDto.setDia("");
								horaDto.setProfesor("");
								horaDto.setMateria("");
								horaDto.setAbreviaturaMateria("");	
								horasDto.add(horaDto);							
							}else{	
								//se formatea la hora de "Date" a "String"					
								String horaInicio = dateFormat.format(hr.getHoraInicio());
								String horaFin = dateFormat.format(hr.getHoraFin()); 
								
								horaDto.setHoraInicio(horaInicio);
								horaDto.setHoraFin(horaFin);
								horaDto.setDia(dia.getDia());
								horaDto.setProfesor(hr.getCargaHoraria().getProfesor().getNombreCompleto());
								horaDto.setMateria(hr.getCargaHoraria().getMateria().getNombre());
								horaDto.setAbreviaturaMateria(hr.getCargaHoraria().getMateria().getAbreviatura());
								horasDto.add(horaDto);
							}
						}
					}
					
				}				
				model.addAttribute("horasDto", horasDto);				
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
		
		// carga el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		//Extrae la lista de remediales del alumno por grupo 		
		List<RemedialAlumno> remediales = serviceRemAlum.buscarPorGrupoYAlumno(grupoID, alumno.getId());
		model.addAttribute("remediales", remediales);
		return "fragments/remediales-alumno :: remedialesAlumno";
	}

	@GetMapping("/informacionGeneral")
	public String informacionGeneral(Model model, HttpSession session) {
		// carga el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}

		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));

		if (alumno != null) {
			// Extrae el ultimo grupo del alumno y se envia al modelo de grupo
			Grupo ultimoGrupo = serviceGrupo.buscarUltimoDeAlumno(alumno.getId());
			
			alumno.setUltimoGrupo(ultimoGrupo);
			//Se extraen la lista de grupos 	
			List<Grupo> grupos = serviceGrupo.buscarTodosDeAlumnosOrdenPorPeriodoDesc(alumno.getId());
						
			DecimalFormat df = new DecimalFormat("###.##");
			
			List<GrupoDTO> gruposDTO = new ArrayList<>();
			// se Calcula el promedio de cada grupo
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
			
			//Validasion de los documentos del expediente alumno acta, curp y sertificado																
			List<DocumentoDTO> docsExp = servicePersonaDoc.buscarActaCurpCerbachiPorPersonaParaDto(cvePersona);		
			
			// validasion del estado del los documentos			
			List<Boolean> valDoc = new ArrayList<>();			
			for(DocumentoDTO documentoDTO : docsExp){
				valDoc.add(documentoDTO.getValidado());
			}
			//Validasion de los ducumentos del expediente  
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
				Boolean reincripsion = null;
				boolean FechaInscripcion = false;
				Date fechaHoy = new Date();	
				//se valida que el alumno no este incrito en el grupo actual			
				if(ultimoGrupo.getPeriodo().getFinInscripcion()!=null && ultimoGrupo.getPeriodo().getFinInscripcion().after(fechaHoy)) {
					reincripsion = false;
					if(alumnoGrupo!=null && alumnoGrupo.getFechaInscripcion() == null) {									
						if(grupos.size()>1) {
							
							//lista de adeudos 										
							int cantidadAdeudos = servicePagoGeneral.contarPorAlumnoYStatus(alumno.getId(), 0);
							System.out.println(cantidadAdeudos);
							//promedio del grupo anterior
							Grupo penultimoGrupo = serviceGrupo.buscarPorAlumnoPenultimoGrupo(alumno.getId());
							double promedio = serviceGrupo.obtenerPromedioAlumn(alumno.getId(), penultimoGrupo.getId());				
							int promedioRed = (int) Math.round(promedio);
							
							//Se valida si ahi un convenio para alguno que extiende el palso de entrega de documetos
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
									reincripsion = true;
								}						
							}else{						
								//Validasion de los requisitos para la reincripsion
								if((alumnoGrupo.getFechaInscripcion() == null) && (estadoDocs == true) && (cantidadAdeudos == 0) && (promedioRed >= 8)) {							
									reincripsion = true;
								}													
							}	
							
						}																
					}else{									
						FechaInscripcion = true;
					}
				}			
				model.addAttribute("FechaInscripcion", FechaInscripcion);
				model.addAttribute("reincripsion", reincripsion);
				model.addAttribute("grupos", gruposDTO);
			}
			model.addAttribute("grupo", ultimoGrupo);
		}
		model.addAttribute("alumno", alumno);
		return "alumno/informacionGeneral";
	}

	@GetMapping("/adeudos")
	public String adeudos(Model model, HttpSession session, Authentication authentication) {
		// carga el usuario apartir del usuario cargado en cesion.
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
			
			model.addAttribute("totalAdeudo", suma);
			model.addAttribute("adeudos", adeudos);
			model.addAttribute("periodoAct", periodoAct);
		}
		
		model.addAttribute("alumno", alumno);
		return "alumno/adeudos";
	}

	@GetMapping("/pagos")
	public String pagos(Model model, HttpSession session, Authentication authentication) {
		// carga el usuario apartir del usuario cargado en cesion.
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
		// carga el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		// Extrae el adeudo seleccinado que se cargo en cecion
		Integer idAdeudo = (Integer) session.getAttribute("cveAdeudo");
		PagoGeneral adeudo = servicePagoGeneral.buscarPorId(idAdeudo);
		Date fechaHoy = new Date();
		
		// valida si la refensia fondos esta vacia		
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
		
		//Se valida si el adeudo ya tienen una referencia vancaria		 
		if (adeudo.getReferencia() == null || adeudo.getReferencia().isEmpty()) {							
			
			//extrae la fecha actual y le suma 	7 dias
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");				
			Date dt = new Date();		        		        
	        Calendar c = Calendar.getInstance();
	        c.setTime(dt);
	        c.add(Calendar.DATE, 3);		        
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
				//extrae la fecha actual y le suma 	7 dias
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");				
				Date dt = new Date();		        		        
		        Calendar c = Calendar.getInstance();
		        c.setTime(dt);
		        c.add(Calendar.DATE, 3);		        
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
		
		// extrae el monto del adeudo y lo comvierte a letras
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
			List<PagoGeneral> adeudos = servicePagoGeneral.buscarPorAlumno(alumno.getId(), 0);
			
			//Se recorrere la lista de  adeudos para extraer las referesias fondos y comparar todas son iguales			
			List<String> referenciasFondos = new ArrayList<>();			
			for (PagoGeneral pagoGeneral : adeudos) {				
				referenciasFondos.add(pagoGeneral.getReferenciaFondos());
			}
			
			//valida que todas las refencias de fodos sean iguales			
			if (new HashSet<String>(referenciasFondos).size() <= 1) {
				
				PagoGeneral adeudo = adeudos.get(0);
				
				if(adeudo.getReferenciaFondos() == null || adeudo.getReferenciaFondos().isEmpty()) {
					ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();			
					refereciaDTO.setCarrera(alumno.getCarreraInicio());			
					refereciaDTO.setMatricula(alumno.getMatricula());			
					refereciaDTO.setPago(adeudo.getMonto());
					String cadena = generarReferenciaFondos.referenciaFondos(refereciaDTO);				
					for (PagoGeneral pagoGeneral : adeudos) {																		
						pagoGeneral.setReferenciaFondos(cadena);					
						servicePagoGeneral.guardar(pagoGeneral);					
					}
					
				}
						
			}else{				
				PagoGeneral adeudo = adeudos.get(0);
				
				ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();			
				refereciaDTO.setCarrera(alumno.getCarreraInicio());			
				refereciaDTO.setMatricula(alumno.getMatricula());			
				refereciaDTO.setPago(adeudo.getMonto());			
				String cadena = generarReferenciaFondos.referenciaFondos(refereciaDTO);	
								
				for (PagoGeneral pagoGeneral : adeudos) {																		
					pagoGeneral.setReferenciaFondos(cadena);					
					servicePagoGeneral.guardar(pagoGeneral);					
				}
				
			}
			
			//Se recorrere la lista de  adeudos para extraer las referesias de pago y comparar todas son iguales			
			List<String> referencias = new ArrayList<>();			
			for (PagoGeneral pagoGeneral : adeudos) {				
				referencias.add(pagoGeneral.getReferencia());
			}
			
			//se valida si todos los adeudos tiene la misma referencia de pago 			
			if (new HashSet<String>(referencias).size() <= 1) {
				//extrae uno de los adeudos para determinar la fecha de pago y referencia 
				//ya que todos los adeudos comparter la misma referencia de pago
				PagoGeneral adeudo = adeudos.get(0);
				
				if (adeudo.getFechaLimite() == null || adeudo.getFechaLimite().before(fechaHoy)) {																				
					// Calcula el adeudo total
					Double suma = 0.0;
					for (PagoGeneral pagoGeneral : adeudos) {
						suma = suma + pagoGeneral.getMonto();
					}
					
					//extrae la fecha actual y le suma 	7 dias
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");				
					Date dt = new Date();		        		        
			        Calendar c = Calendar.getInstance();
			        c.setTime(dt);
			        c.add(Calendar.DATE, 3);		        
			        String fechaLimite = format.format(c.getTime());
			        Date fechaLimiteP = c.getTime();
			        		        		      		   
					ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();			
					refereciaDTO.setCarrera(alumno.getCarreraInicio());
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
				
			}else{									
				// Calcula el adeudo total
				Double suma = 0.0;
				for (PagoGeneral pagoGeneral : adeudos) {
					suma = suma + pagoGeneral.getMonto();
				}
				
				//extrae la fecha actual y le suma 	7 dias
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");				
				Date dt = new Date();		        		        
		        Calendar c = Calendar.getInstance();
		        c.setTime(dt);
		        c.add(Calendar.DATE, 3);		        
		        String fechaLimite = format.format(c.getTime());
		        Date fechaLimiteP = c.getTime();
		        		        		      		   
				ReferenciaBanamexDTO refereciaDTO = new ReferenciaBanamexDTO();			
				refereciaDTO.setCarrera(alumno.getCarreraInicio());
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
			
			//buelbe a consultar la lista de adeudos por si estos fueron actulizados 			
			List<PagoGeneral> adeudosActulizado = servicePagoGeneral.buscarPorAlumno(alumno.getId(), 0);
			
			PagoGeneral adeudo = adeudosActulizado.get(0);
			
			Periodo pe = periodoService.buscarUltimo();
			String periodo = pe.getNombre();
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
									
		}
		}catch (NullPointerException e) {
			// TODO: handle exception
			System.out.println("WTF"+ e.getMessage());
		}
		model.addAttribute("alumno", alumno);
		return "alumno/referencia-multiple-pago";
	}

	@GetMapping("/recibo")
	public String recibopago(Model model, HttpSession session, Authentication authentication) {
		// carga el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		Alumno alumno = serviceAlumno.buscarPorPersona(new Persona(cvePersona));
		
		if (alumno != null) {
			// Extrae el pago seleccinado a partir del cvePago guardado en cesion
			Integer idPago = (Integer) session.getAttribute("cvePago");			
			PagoGeneral pago = servicePagoGeneral.buscarPorId(idPago);			
			model.addAttribute("pagoSeleccionado", pago);
			
			// Extrae el ultmi grupo
			Grupo ultimoGrupo = serviceGrupo.buscarUltimoDeAlumno(alumno.getId());
			model.addAttribute("ultimoGrupo", ultimoGrupo);
			
			// se actulisa decuerdo a si ahi uno o mas adeudos asociados al folio
			Double totalPago = 0.0;
			String folioCifrado = "";
			if(pago.getFolio() == null) {
				//como el folio es null solo se carga el pago seleccinado 
				//ya que otra forma traeria todos los pagos cullo folio sea null 							
				model.addAttribute("pagosFolio", pago);				
				totalPago = pago.getMonto();	
				//el folio sifrado no se actuliza ya que es null y no se puede sifrar				
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
			// extrae el monto del adeudo y lo comvierte a letras
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
    		alumno.setTurno(new Turno(dto.getIdTurno()));
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
    		
    		AlumnoGrupo alGrupo = alumnoGrService.buscarPorIdAlumnoYidGrupo(alumno.getId(), dto.getIdGrupo());
    		if (alGrupo==null) {
				alGrupo = alumnoGrService.buscarPrimerGrupoProspecto(alumno.getId());
				alGrupo.setGrupo(new Grupo(dto.getIdGrupo()));
				alumnoGrService.guardar(alGrupo);
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
			// carga el usuario apartir del usuario cargado en cesion.
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
			
			System.err.println("ultimo grupo: "+ ultimoGrupo.getId());
			System.err.println("grupo actaul: "+ idGrupo );
			
			if(ultimoGrupo.getId().toString().equals(idGrupo.toString())) {				
//			if(ultimoGrupo.getId() == idGrupo) {			
				AlumnoGrupo ag = alumnoGrService.checkInscrito(alumno.getId(), ultimoGrupo.getPeriodo().getId());		
				System.out.println("alumno: "+alumno.getId()+", periodo: "+ultimoGrupo.getPeriodo().getId() + ", idGrupo: "+ultimoGrupo.getId());
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

}