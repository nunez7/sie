package edu.mx.utdelacosta.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import edu.mx.utdelacosta.model.AsistenciaTemaGrupal;
import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.Canalizacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CausaBaja;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.Foco;
import edu.mx.utdelacosta.model.FocosAtencion;
import edu.mx.utdelacosta.model.Fortaleza;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.Motivo;
import edu.mx.utdelacosta.model.MotivoTutoria;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.ProgramacionTutoria;
import edu.mx.utdelacosta.model.RespuestaEvaluacionInicial;
import edu.mx.utdelacosta.model.Servicio;
import edu.mx.utdelacosta.model.TemaGrupal;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoAsistenciaDTO;
import edu.mx.utdelacosta.model.dto.AlumnoInfoDTO;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;
import edu.mx.utdelacosta.model.dto.GrupoDTO;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;
import edu.mx.utdelacosta.model.dto.PreguntaDTO;
import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;
import edu.mx.utdelacosta.model.dto.TutoriasProgramadasDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioDTO;
import edu.mx.utdelacosta.model.dtoreport.GruposEvaluacionTutorDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorMateriaDTO;
import edu.mx.utdelacosta.model.dtoreport.IndicadorParcialDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsistenciaTemaGrupalService;
import edu.mx.utdelacosta.service.IBajaService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICanalizacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IComentarioEvaluacionTutorService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IDiaService;
import edu.mx.utdelacosta.service.IEvaluacionesService;
import edu.mx.utdelacosta.service.IFocosAtencionService;
import edu.mx.utdelacosta.service.IFortalezaService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IMotivoService;
import edu.mx.utdelacosta.service.IMotivoTutoriaService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPreguntaService;
import edu.mx.utdelacosta.service.IProgramacionTutoriaService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionInicialService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionTutorService;
import edu.mx.utdelacosta.service.ITemaGrupalService;
import edu.mx.utdelacosta.service.ITutoriaIndividualService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Director')")
@RequestMapping("/tutorias")
public class TutorController {
	
	@Value("${spring.mail.username}")
	private String correo;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private IAlumnoService alumnoService;	
	
	@Autowired
	private IUsuariosService usuarioService;
	
	@Autowired
	private ICalificacionMateriaService calMatService;
	
	@Autowired
	private IPagoGeneralService pagoGenService;
	
	@Autowired
	private IDiaService serviceDia;
		
	@Autowired
	private IHorarioService serviceHorario;
	
	@Autowired
	private IEvaluacionesService evaluacionService;
	
	@Autowired
	private IRespuestaEvaluacionTutorService resEvaTutorService;
	
	@Autowired
	private IComentarioEvaluacionTutorService comEvaTurtorService;
	
	@Autowired
	private IPreguntaService preguntasService;
	
	@Autowired
	private IPeriodosService periodoService;
	
	@Autowired
	private IMotivoService motivoService;
	
	@Autowired
	private ITutoriaIndividualService tutoriaIndiService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IMotivoTutoriaService motivoTutoService;
	
	@Autowired
	private IFortalezaService fortalezaService;
	
	@Autowired
	private IFocosAtencionService focoAtenService;
	
	@Autowired
	private ITemaGrupalService temaGrupalService;
	
	@Autowired
	private IAsistenciaTemaGrupalService asisTemaGruService;
	
	@Autowired
	private IProgramacionTutoriaService proTutoriaService;
	
	@Autowired
	private ICargaHorariaService cargaHorariaService;
	
	@Autowired
	private EmailSenderService emailService;
	
	@Autowired
	private ICanalizacionService canalizacionService;
	
	@Autowired
	private IBajaService bajaService;
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";
	
	@Autowired
	private ICalificacionMateriaService calificacionMateriaService;
	
	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;
	
	@Autowired
	private ICalificacionCorteService calificacionCorteService;
	
	@Autowired
	private IAlumnoGrupoService alumnoGrupoService;
	
	@Autowired
	private IRespuestaEvaluacionInicialService resEvaIniService;
	
	@Autowired
	private IPersonaService personaService;

    @GetMapping("/cargar-alumno/{dato}")
   	public String cargarAlumnos(@PathVariable(name = "dato", required = false) String dato,  Model model, HttpSession session, Authentication auth) { 
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		String rol = auth.getAuthorities().toString();
		rol = rol.replace("[", "").replace("]", "");
		//comparamos el rol 
		List<AlumnoInfoDTO> alumnos = new ArrayList<>();
		if(rol.equals("Profesor")) {
			if(dato!=null) {    		    		
	    		alumnos = alumnoService.buscarPorProfesorPeriodoYNombreOMatricula(cvePersona, usuario.getPreferencias().getIdPeriodo(), dato);
	    	}else {    		
	    		alumnos = alumnoService.buscarPorProfesorYPeriodo(cvePersona, usuario.getPreferencias().getIdPeriodo());
	    	}
		}
		else if (rol.equals("Director")) {
			if(dato!=null) {    		
	    		alumnos = alumnoService.buscarPorPersonaCarreraYPeriodoYNombreOMatricula(cvePersona, usuario.getPreferencias().getIdPeriodo(), dato);
	    	}else {    		
	    		alumnos = alumnoService.buscarPorPersonaCarreraYPeriodoYActivos(cvePersona, usuario.getPreferencias().getIdPeriodo());
	    	}
		}
		else {
			if(dato!=null) {    		    		
	    		alumnos = alumnoService.buscarTodosPorNombreOMatriculaYPeriodoYActivos(dato, usuario.getPreferencias().getIdPeriodo());
	    	}else {    		
	    		alumnos = alumnoService.buscarTodosPorPeriodo(usuario.getPreferencias().getIdPeriodo());
	    	}
		}
    	
		model.addAttribute("alumnos", alumnos);
		
		return "fragments/control-alumnos :: res-alumnos";
   	}
	
	@GetMapping("/tutorias")
	public String tutorias() {
		return "tutorias/gruposTutorados";
	}
	
	@GetMapping("/tutoriaIndividual")
	public String tutoriaIndividual(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
	
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");		
		List<Alumno> alumnos = new ArrayList<>();				
		if (cveGrupo != null) {						
			alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());		
		}
		
		List<Motivo> motivos = motivoService.buscarTodo();		
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("motivos", motivos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/tutoriaIndividual";		
	}
	
	@PostMapping(path = "/save-individual", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarIndividual(@RequestBody Map<String, String> obj,HttpSession session, Model model) throws ParseException {			
		if(obj.get("idAlumno")!=null) {
			SimpleDateFormat dFHora = new SimpleDateFormat("hh:mm");		
			SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");
			
			Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
			String cveAlumno = obj.get("idAlumno");
			Date fecha = new Date();	
			Date fechaTutoria = dFDia.parse(obj.get("fechaInicioTutoria"));
			Date horaInicio = dFHora.parse(obj.get("horaInicio"));
			Date horaFin = dFHora.parse(obj.get("horaFin"));
			
			//Recibo los motivos de la tutoría como un string y los convierto a una ArrayList<Integer>
			String mo = obj.get("motivos");							
		    String s = ",";
		    String[] mot = mo.split(s);		
			List<String> motiv = Arrays.asList(mot);				
			ArrayList<Integer> motivos = new ArrayList<Integer>();//lista de motivos seleccionados
			for(int i = 0; i < motiv.size(); i++) {
				motivos.add(Integer.parseInt(motiv.get(i)));   
			}						
			
			String puntosRelevantes = obj.get("puntosRelevantes");
			String compromisosAcuerdos = obj.get("compromisosAcuerdos");
			String usuarioMatricula = obj.get("usuarioMatricula");
			String userNip = obj.get("userNip");
			
			Integer tipo = Integer.parseInt(obj.get("tipo-tutoria"));			
			//Se valida el nivel de atención recibido desde la vista
			Integer nivelAtencion = Integer.parseInt(obj.get("nivelAtencion"));			
			String nvAtencion = null;			
			switch (nivelAtencion) {
			  case 1:
			    nvAtencion = "ALTO";
			    break;
			  case 2:
				nvAtencion = "MEDIO";
			    break;
			  case 3:
				nvAtencion = "BAJO";
			    break;
			}
																				
			if(cveAlumno!=null) {	
				Usuario usuario = usuarioService.buscarPorUsuario(usuarioMatricula);							
				Boolean valContra =  passwordEncoder.matches(userNip, usuario.getContrasenia());				
																	
				TutoriaIndividual tutInd = new TutoriaIndividual();
				tutInd.setAlumno(new Alumno(Integer.parseInt(cveAlumno)));
				tutInd.setGrupo(new Grupo(cveGrupo));
				tutInd.setFechaTutoria(fechaTutoria);
				tutInd.setFechaRegistro(fecha);
				tutInd.setHoraInicio(horaInicio);
				tutInd.setHoraFin(horaFin);
				tutInd.setPuntosImportantes(puntosRelevantes);
				tutInd.setCompromisosAcuerdos(compromisosAcuerdos);
				tutInd.setNivelAtencion(nvAtencion);
				if(valContra==true) {
					tutInd.setValidada(true);
				}else{
					tutInd.setValidada(false);
				}
				tutoriaIndiService.guardar(tutInd);
				
				for(int i = 0; i < motivos.size(); i++) {
					MotivoTutoria motivoTutoria = new MotivoTutoria();
					motivoTutoria.setTutoriaIndividual(tutInd);
					motivoTutoria.setMotivo(new Motivo(motivos.get(i)));
					motivoTutoService.guardar(motivoTutoria);
				}
				//Se valida si la tutoría se canalizara								
				if(tipo==1) {		
					TutoriaIndividual tutoria = tutoriaIndiService.ultimoRegistro();						
					return  String.valueOf(tutoria.getId());
				}else{
					return "ok";
				}
				
			}else{
				return "NoUser";
			}
		}
		return "NoAl";
	}
	
	@GetMapping("/canalizacion/{cveTutoria}")	
	public String canalizacion(@PathVariable(name = "cveTutoria", required = false) Integer cveTutoria, Model model, HttpSession session) {				
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");	
		List<Alumno> alumnos = new ArrayList<>();
		List<CargaHoraria> materias = new ArrayList<>();
		if (cveGrupo != null) {									
			materias = cargaHorariaService.buscarPorGrupo(new Grupo(cveGrupo));
			TutoriaIndividual tutoriaIndividual = tutoriaIndiService.buscarPorId(cveTutoria);
			model.addAttribute("tutoria", tutoriaIndividual);
		}
		
		model.addAttribute("alumnos", alumnos);		
		model.addAttribute("materias", materias);
		model.addAttribute("cveGrupo", cveGrupo);	
		return "tutorias/canalizacion";
	}

	@PostMapping(path = "/canalizar-alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String canalizarAlumno(@RequestBody Map<String, String> obj,HttpSession session) throws ParseException {
		SimpleDateFormat dFHora = new SimpleDateFormat("hh:mm");	
		SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");		
		
		String cveTutoria =  obj.get("idTutoria");		
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
		String cveAlumno = obj.get("idAlumno");
		Date fechaRegistro = new Date();
		Date fecha = dFDia.parse(obj.get("fechaCanalizacion"));
		Date hora = dFHora.parse(obj.get("horaCanalizacion"));
		Integer servicio =  Integer.parseInt(obj.get("servicio"));
		// Solo se utiliza para correo como complemento del servicio solicitado		
		String resumen =  obj.get("resumen");
		Integer materia =  Integer.parseInt(obj.get("materia"));
		//		
		String razon =  obj.get("razon");
		String comentario =  obj.get("comentario");
		
		if(cveGrupo!=null) {
			Grupo grupo = grupoService.buscarPorId(cveGrupo);			
			if(cveAlumno!=null) {	
				if(cveTutoria!=null) {
					Canalizacion canalizacion = new Canalizacion();
					canalizacion.setAlumno(new Alumno(Integer.parseInt(cveAlumno)));
					canalizacion.setTutoriaIndividual(new TutoriaIndividual(Integer.parseInt(cveTutoria)));
					canalizacion.setPeriodo(grupo.getPeriodo());
					canalizacion.setFechaRegistro(fechaRegistro);
					canalizacion.setFechaCanalizar(fecha);
					canalizacion.setHoraCanalizar(hora);
					canalizacion.setServicio(new Servicio(servicio));
					canalizacion.setRazones(razon);
					canalizacion.setComentarios(comentario);
					canalizacion.setStatus(1);
					canalizacionService.guardar(canalizacion);
					
					Alumno alumno = alumnoService.buscarPorId(Integer.parseInt(cveAlumno));				
					// Sé crear un correo y se envía al director correspondiente			
					Mail mail = new Mail();
					String de = correo;
					String para = "brayan.bg499@gmail.com";
					
					//Se valida el tipo de servicio al que se canalizara para enviar el correo			
//					if(servicio==1){
//						
//					}
//					else if(servicio==2){
//						
//					}
//					else if(servicio==3){
					CargaHoraria cargaHoraria = cargaHorariaService.buscarPorIdCarga(materia);
//					para = cargaHoraria.getProfesor().getEmail();
//					}
//					else if(servicio==4){
//					
//					}
//					else if(servicio==5){
//						
//					}
					
					mail.setDe(de);
					mail.setPara(new String[] {para});		
					//Email title
					mail.setTitulo("Canalización de alumnos");		
					//Variables a plantilla
					Map<String, Object> variables = new HashMap<>();
					variables.put("titulo", "Canalización del alumno(a) "+alumno.getPersona().getNombreCompleto());
					//La canalización a profesor cambia un poco la estructura del correo enviado
					if(servicio==3){
						variables.put("cuerpoCorreo", "El tutor(a) "+grupo.getProfesor().getNombreCompletoConNivelEstudio()
								+", solicita una canalización para el día "+dFDia.format(fecha)+" a las "+dFHora.format(hora)
								+" para la materia "+cargaHoraria.getMateria().getNombre()+" que imparte"
								+", por esta razón: "+razon
								+". "+comentario+". Espero su confirmación"+grupo.getProfesor().getEmail()+".");
					}else{
						variables.put("cuerpoCorreo", "El tutor(a) "+grupo.getProfesor().getNombreCompletoConNivelEstudio()
								+", solicita una canalización para el día "+dFDia.format(fecha)+" a las "+dFHora.format(hora)
								+" debido a que "+resumen
								+", por esta razón: "+razon
								+". "+comentario+". Espero su confirmación"+grupo.getProfesor().getEmail()+".");
					}
					mail.setVariables(variables);			
					try {
						emailService.sendEmail(mail);
						return "ok";
					}catch (MessagingException | IOException e) {
						return "errorCorre";
				  	}
				}				
				return "noTuto";
			}
			return "noAl";
		}
		return "noGru";
	}
	
	@GetMapping("/bajaAlumno")
	public String bajaAlumno(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));		
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");		
		List<Alumno> alumnos = new ArrayList<>();		
		
		if (cveGrupo != null) {						
			alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());				
		}
		
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/bajaAlumno";		
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
	@PostMapping(path = "/guardar-baja", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarBaja(@RequestBody Map<String, String> obj,HttpSession session) throws ParseException {	
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}		
		SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = new Date();	
		
		String cveAlumno = obj.get("idAlumno");
		String ultimaFechaAsistio = obj.get("ultimaFechaAsistio");
		String fechaSolicitud = obj.get("fechaSolicitud");
		String tipoBaja = obj.get("tipoBaja");
		String causaBaja = obj.get("causaBaja");
		String expTipoBaja = obj.get("exp-tipoBaja");
		String motivoBaja = obj.get("motivoBaja");	
						
		if(cveAlumno!=null) {
			if(causaBaja!=null) {
				Baja ComprobarBaja = bajaService.buscarPorEstadoAlumnoYFechaAutorizacion(0, new Alumno(Integer.parseInt(cveAlumno)), null);
				if(ComprobarBaja==null) {
					Periodo periodo = periodoService.buscarUltimo();
					Baja baja = new Baja();
					baja.setPeriodo(periodo);
					baja.setPersona(new Persona(cvePersona));
					baja.setAlumno(new Alumno(Integer.parseInt(cveAlumno)));
					baja.setTipoBaja(Integer.parseInt(tipoBaja));
					baja.setCausaBaja(new CausaBaja(Integer.parseInt(causaBaja)));
					baja.setOtraCausa(expTipoBaja);
					baja.setFechaAsistencia(dFDia.parse(ultimaFechaAsistio));
					baja.setFechaSolicitud(dFDia.parse(fechaSolicitud));
					baja.setFechaAutorizacion(null);
					baja.setDescripcion(motivoBaja);
					baja.setEstatus(0);
					baja.setFechaRegistro(fecha);
					bajaService.guardar(baja);
					
					Alumno alumno = alumnoService.buscarPorId(Integer.parseInt(cveAlumno));
					Persona persona = personaService.buscarPorId(cvePersona);
					//correo
					Mail mail = new Mail();
					String de = correo;
					//String para = alumno.getCarreraInicio().getEmailCarrera();
					String para = "brayan.bg499@gmail.com";
					mail.setDe(de);
					mail.setPara(new String[] {para});		
					//Email title
					mail.setTitulo("Nueva solicitud de baja.");		
					//Variables a plantilla
					Map<String, Object> variables = new HashMap<>();
					variables.put("titulo", "Solicitud de baja del alumno(a) "+alumno.getPersona().getNombreCompleto());						
					variables.put("cuerpoCorreo","El Tutor "+persona.getNombreCompleto()+" realizó una solicitud de baja para el alumno con matrícula "+alumno.getMatricula()+", diríjase al apartado de bajas en el panel del director para ver más detalles al respecto.");
					mail.setVariables(variables);			
					try {							
						emailService.sendEmail(mail);													
					}catch (MessagingException | IOException e) {
						
				  	}
					
					return "ok";
				}
				return "bajaActiva";
			}
			return "NoCausaBaja";
		}
		return "NoAl";
	}
	
	@GetMapping("/tutoriaGrupal")
	public String tutoriaGrupal(Model model, HttpSession session) {
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
					
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));		
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");		
		List<Alumno> alumnos = new ArrayList<>();
		List<Fortaleza> fortalezas = null;
		List<FocosAtencion> focosAtencion = null;
		List<TemaGrupal> temasGrupales = null;
		
		if (cveGrupo != null) {	
			Boolean GrupoEnPeriodo = grupoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			if(GrupoEnPeriodo==true) {
				alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());	
				fortalezas = fortalezaService.buscarPorGrupo(new Grupo(cveGrupo));
				focosAtencion = focoAtenService.buscarPorGrupo(new Grupo(cveGrupo));
				temasGrupales = temaGrupalService.buscarPorGrupo(new Grupo(cveGrupo));	
				model.addAttribute("cveGrupo", cveGrupo);
			}
		}
		
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("fortalezas", fortalezas);
		model.addAttribute("focosAtencion", focosAtencion);
		model.addAttribute("temasGrupales", temasGrupales);
		model.addAttribute("grupos", grupos);
		
		return "tutorias/tutoriaGrupal";
	}
	
	@PostMapping(path = "/guardar-fortaleza", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarFortalezas(@RequestBody Map<String, String> obj,HttpSession session) {			
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
		String fort = obj.get("fortaleza").trim();
		
		if(cveGrupo!=null) {
			if(fort.isEmpty() || fort==null) {							
				return "FNull";
			}else{				
				Fortaleza fortaleza = new Fortaleza();
				fortaleza.setGrupo(new Grupo(cveGrupo));
				fortaleza.setFortaleza(fort);
				fortalezaService.guardar(fortaleza);				
				return "ok";
			}
		}
		return "noGrupo";
	}
	
	@PostMapping(path="/eliminar-fortaleza", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarFortalezas(@RequestBody Map<String, String> obj) {			
		Integer cveFortaleza = Integer.parseInt(obj.get("id"));		
		fortalezaService.eliminar(new Fortaleza(cveFortaleza));
		return "ok";
	}
	
	@GetMapping("/cargar-fortalezas")
	public String cargarFortalezas(Model model, HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");	
		List<Fortaleza> fortalezas = null;
		if(cveGrupo!=null) {
			fortalezas = fortalezaService.buscarPorGrupo(new Grupo(cveGrupo));			
		}
		model.addAttribute("fortalezas", fortalezas);
		return "fragments/tutorias :: cargar-fortalezas";
	}
	
	@PostMapping(path = "/guardar-foco", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarFoco(@RequestBody Map<String, String> obj,HttpSession session) {			
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");		
		String focoAtencion = obj.get("focoAtencion").trim();
		Integer foco = Integer.parseInt(obj.get("foco"));
		
		if(cveGrupo!=null) {
			if(foco==1 || foco==2 || foco==3) {
				if(focoAtencion.isEmpty() || focoAtencion==null) {
					return "FANull";
				}else{
					FocosAtencion focosAtencion = new FocosAtencion();
					focosAtencion.setDescripcion(focoAtencion);
					focosAtencion.setFoco(new Foco(foco));
					focosAtencion.setGrupo(new Grupo(cveGrupo));
					focoAtenService.guardar(focosAtencion);
					return "ok";
				}
			}else{
				return "Df";
			}
		}
		return "noGrupo";
	}
	
	@PostMapping(path="/eliminar-foco", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarFoco(@RequestBody Map<String, String> obj) {			
		Integer cveFoco = Integer.parseInt(obj.get("id"));		
		focoAtenService.eliminar(new FocosAtencion(cveFoco));
		return "ok";
	}
	
	@GetMapping("/cargar-focos")
	public String cargarFocos(Model model, HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");	
		List<FocosAtencion> focosAtencion = null;
		if(cveGrupo!=null) {
			focosAtencion = focoAtenService.buscarPorGrupo(new Grupo(cveGrupo));			
		}
		model.addAttribute("focosAtencion", focosAtencion);
		return "fragments/tutorias :: cargar-focos";
	}
	
	@PostMapping(path = "/programar-tema", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarTema(@RequestBody Map<String, String> obj,HttpSession session) throws ParseException {	
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
		String tema = obj.get("tema").trim();
		Integer foco = Integer.parseInt(obj.get("tipoVul"));
		String actividades = obj.get("actividades").trim();
		
		SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaProgramada = dFDia.parse(obj.get("fechaProgramada"));
		
		if(cveGrupo!=null) {
			if(foco==1 || foco==2 || foco==3) {
				if(tema.isEmpty() || tema==null) {
					return "noTema";
				}else{
					TemaGrupal temaGrupal = new TemaGrupal();
					temaGrupal.setTema(tema);
					temaGrupal.setFoco(new Foco(foco));
					temaGrupal.setGrupo(new Grupo(cveGrupo));
					temaGrupal.setActividad(actividades);
					temaGrupal.setFechaProgramada(fechaProgramada);	
					temaGrupal.setFechaRegistro(new Date());	
					temaGrupalService.guardar(temaGrupal);
					
					List<Alumno> alumno = alumnoService.buscarPorGrupo(cveGrupo);
					for(Alumno al: alumno) {
						AsistenciaTemaGrupal asistencia = new AsistenciaTemaGrupal();
						asistencia.setTemaGrupal(temaGrupal);
						asistencia.setAlumno(al);
						asistencia.setAsistencia("A");
						asisTemaGruService.guardar(asistencia);
					}
					return "ok";
				}
			}else{
				return "Df";
			}			
		}		
		return "noGrupo";
	}
	
	@GetMapping("/cargar-tema-grupal/{cveTemaTutoria}")
	public String temaGrupal(@PathVariable(name = "cveTemaTutoria", required = false) Integer cveTemaTutoria, Model model) {
		TemaGrupal temaGrupal = temaGrupalService.bucarPorId(cveTemaTutoria);
		
		if(temaGrupal!=null) {				
			List<AlumnoAsistenciaDTO> alumnos = asisTemaGruService.buscarPorTemaGrupalYGrupo(temaGrupal.getGrupo().getId(), temaGrupal.getId());			
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("temaGrupal", temaGrupal);			
		}
		
		return "tutorias/detalleTutoriaGrupal";
	}
	
	@PostMapping(path="/asistencia-tutoria", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String asistenciaTutoria(@RequestBody Map<String, String> obj) {
		Integer idTemaGrupal = Integer.parseInt(obj.get("idTemaGrupal"));
		Integer idAlumno = Integer.parseInt(obj.get("idAlumno"));
		String tipoAsistencia = obj.get("tipoAsistencia");
		
		if(idTemaGrupal!=null && idAlumno!=null && tipoAsistencia!=null) {
			AsistenciaTemaGrupal asistenciaTemaGrupal = asisTemaGruService.buscarPorTemaGrupalYAlumno(new TemaGrupal(idTemaGrupal), new Alumno(idAlumno));
			
			if(asistenciaTemaGrupal==null) {
				AsistenciaTemaGrupal asistencia = new AsistenciaTemaGrupal();
				asistencia.setTemaGrupal(new TemaGrupal(idTemaGrupal));
				asistencia.setAlumno(new Alumno(idAlumno));
				asistencia.setAsistencia(tipoAsistencia);
				asisTemaGruService.guardar(asistencia);
				return "ok";
			}else{
				asistenciaTemaGrupal.setAsistencia(tipoAsistencia);
				asisTemaGruService.guardar(asistenciaTemaGrupal);
				return "ok";
			}
			
		}
		
		return "Error";
	}
	
	@PostMapping(path = "/actualizar-tema", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String actualizarTema(@RequestBody Map<String, String> obj,HttpSession session) throws ParseException {			
//		SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dFHora = new SimpleDateFormat("hh:mm");
		Date fechaHoy = new Date();	
		
		Integer cveTemaGrupal = Integer.parseInt(obj.get("cveTemaGrupal"));
//		Date fechaProgramada = dFDia.parse(obj.get("fechaProgramada"));
		Date horaInicio = dFHora.parse(obj.get("horaInicio"));
//		String tema = obj.get("tema").trim();
//		Integer foco = Integer.parseInt(obj.get("vulnerabilidad"));				
//		String actividad = obj.get("actividad").trim();
		
		String subActividad = obj.get("subActividad").trim();
		String objetivo = obj.get("objetivo").trim();
		String observaciones = obj.get("observaciones").trim();
		String seguimiento = obj.get("seguimiento").trim();			
		
		TemaGrupal temaGrupal = temaGrupalService.bucarPorId(cveTemaGrupal);
		if(temaGrupal!=null) {		
			if(temaGrupal.getFechaRealizada()==null) {
				temaGrupal.setFechaRealizada(fechaHoy);
			}
//			temaGrupal.setFechaProgramada(fechaProgramada);			
			temaGrupal.setHoraInicio(horaInicio);
//			temaGrupal.setTema(tema);
//			temaGrupal.setFoco(new Foco(foco));
//			temaGrupal.setActividad(actividad);
			temaGrupal.setSubActividad(subActividad);
			temaGrupal.setObjetivo(objetivo);
			temaGrupal.setObservaciones(observaciones);
			temaGrupal.setSeguimiento(seguimiento);		
			temaGrupalService.guardar(temaGrupal);
			return "ok";
		}
		
	return "error";			
	}
		
	@GetMapping("/cargar-temas")
	public String cargarTemas(Model model, HttpSession session) {
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");	
		List<TemaGrupal> temasGrupales = null;
		if(cveGrupo!=null) {
			temasGrupales = temaGrupalService.buscarPorGrupo(new Grupo(cveGrupo));			
		}
		model.addAttribute("temasGrupales", temasGrupales);
		return "fragments/tutorias :: cargar-temas";
	}
	
	@GetMapping("/programacionTutorias")
	public String programacionTutorias(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");  
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));		
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");		
		List<Alumno> alumnos = new ArrayList<>();	
				
		if (cveGrupo != null) {				
			Grupo grupo = grupoService.buscarPorId(cveGrupo);			
			alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());				
			
			List<Date> DiasAlviles = periodoService.buscarDiasPorFechaInicioYFechafin(f.format(grupo.getPeriodo().getInicio()), f.format(grupo.getPeriodo().getFin()));
			
			List<TutoriasProgramadasDTO> tutorias = new ArrayList<>();
			Integer n = 0;
			for(Alumno alumno : alumnos) {	
				++n;				
				Integer intervaloDias = Math.round(DiasAlviles.size()/alumnos.size());
				if(intervaloDias == 0) {
					intervaloDias = 1;
				}
				
				//Donde cortaremos el ciclo
		        Integer breakNumero = intervaloDias * n;
		        //Si el break es mayor al numero de días, le asignamos el último día
		        if (breakNumero > DiasAlviles.size()) {
		            breakNumero = DiasAlviles.size();
		        }
		        
		        int contador = 0;
		        String fechaProgramada = "";		        
		        for (Date dia: DiasAlviles) {
		            contador++;
		            if (contador == breakNumero) {
		                fechaProgramada = f.format(dia);
		                break;
		            }
		        }
		        
		        List<ProgramacionTutoria> pTutorias = proTutoriaService.buscarPorAlumnoYGrupo(alumno, grupo);		        
		        if(pTutorias.isEmpty()) {
		        	
		        }else{
		        	fechaProgramada = f.format(pTutorias.get(0).getFecha());
		        }		
		        
		        List<TutoriaIndividual> tutoriasInd = tutoriaIndiService.buscarPorAlumnoYGrupo(alumno, grupo);
		        
		        TutoriasProgramadasDTO tutoria = new TutoriasProgramadasDTO();
		        tutoria.setIdAlumno(alumno.getId());
		        tutoria.setNombreAlumno(alumno.getPersona().getNombreCompleto());
		        tutoria.setMatricula(alumno.getMatricula());
		        tutoria.setFechaProgramada(fechaProgramada);
		        tutoria.setTutoriaIndividuales(tutoriasInd);
		        tutoria.setEstadoAlumno(alumno.getEstatusGeneral());
		        tutorias.add(tutoria);		       
			}			
			model.addAttribute("tutorias", tutorias);
		}
		
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/programacionTutorias";
	}
	
	@PostMapping(path = "/actualizar-fecha-tutoria-programada", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String actualizarFechaTema(@RequestBody Map<String, String> obj,HttpSession session) throws ParseException {	
		Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");	
		Integer cveAlumno = Integer.parseInt(obj.get("idAlumno"));
		SimpleDateFormat dFDia = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaProgramada = dFDia.parse(obj.get("fecha"));
		Date fechaHoy = new Date();	
		
		if(cveGrupo!=null) {
			List<ProgramacionTutoria> tutoria = proTutoriaService.buscarPorAlumnoYGrupo(new Alumno(cveAlumno), new Grupo(cveGrupo));			
			if(tutoria.isEmpty()) {
				ProgramacionTutoria pT = new ProgramacionTutoria();
				pT.setGrupo(new Grupo(cveGrupo));
				pT.setAlumno(new Alumno(cveAlumno));
				pT.setFechaAlta(fechaHoy);
				pT.setFecha(fechaProgramada);
				proTutoriaService.guardar(pT);
				return "ok";
			}else{
				tutoria.get(0).setFecha(fechaProgramada);
				proTutoriaService.guardar(tutoria.get(0));
				return "ok";
			}
		}
		return "error";		
	}
	
	@GetMapping("/cargar-tutoria/{idTutoria}")
	public String cargarAlumnos(@PathVariable(name = "idTutoria", required = false) String idAlumno,  Model model) { 
		if(idAlumno!=null){
			TutoriaIndividual tutoria = tutoriaIndiService.buscarPorId(Integer.parseInt(idAlumno));
			model.addAttribute("tutoria", tutoria);
		}
		return "fragments/tutorias :: cargar-tutoria";
	}
	
	@GetMapping("/reporte-evaluacion-tutor")
	public String encuestaEvaluacionTutor(Model model, HttpSession session) {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
					
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("ret-cveGrupo");				
		int aluEncuestados=0;	
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		Evaluacion evaluacion = evaluacionService.buscar(4);
		
		if(cveGrupo != null) {	
			// Validación por si el reporte es de todos los grupos tutorados o de solo uno en particular
			List<Grupo> Rgrupos = new ArrayList<>();
			if(cveGrupo==0) {
				Rgrupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
			}else {
				Grupo Rgrupo = grupoService.buscarPorId(cveGrupo);
				Rgrupos.add(Rgrupo);
			}
			
			List<GruposEvaluacionTutorDTO> gruposEvaDto = new ArrayList<>();
			for(Grupo grupo : Rgrupos) {
				GruposEvaluacionTutorDTO grupoEvaDto =  new GruposEvaluacionTutorDTO();
				aluEncuestados = resEvaTutorService.contarEncuestadosPorGrupo(4, grupo.getId());			
				List<ComentarioDTO> comentarios = comEvaTurtorService.buscarComentariosPorGrupo(4, grupo.getId());
				//Se calculan los promedios de cada una de las preguntas para cada uno de los grupos en lo que el profesor imparte dicha materia en determinada carrera
				List<PreguntaDTO> preguntasDto = new ArrayList<>();	
				double promedioPre=0.0;
				for(Pregunta pre :evaluacion.getPreguntas()) {
					PromedioPreguntaDTO promedioPreguntaDTOs = preguntasService.ObtenerPromedioEvaTuPorPregunta(4, pre.getId(), grupo.getId());
					promedioPre=promedioPreguntaDTOs.getPromedio()+promedioPre;
					
					List<GrupoDTO> gruposDTO = new ArrayList<>();
					GrupoDTO grupoDto = new GrupoDTO();
					grupoDto.setIdGrupo(grupo.getId());
					grupoDto.setNombreGrupo(grupo.getNombre());
					grupoDto.setPromedioPre(promedioPreguntaDTOs.getPromedio());
					gruposDTO.add(grupoDto);
					
					PreguntaDTO preguntaDto = new PreguntaDTO();
					preguntaDto.setIdPregunta(pre.getId());
					preguntaDto.setDescripcion(pre.getDescripcion());
					preguntaDto.setConsecutivo(pre.getConsecutivo());	
					preguntaDto.setGruposDTO(gruposDTO);
					preguntasDto.add(preguntaDto);
				}
				
				List<GrupoDTO> gruposDTOs = new ArrayList<>();
				Double proGe=0.0;					
				for(PreguntaDTO preDTO : preguntasDto) {
					proGe =  preDTO.getGruposDTO().get(0).getPromedioPre()+proGe;
				}		
				
				proGe=proGe/evaluacion.getPreguntas().size();
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(0);
				grupoDto.setNombreGrupo("0");
				grupoDto.setPromedioPre(proGe);
				gruposDTOs.add(grupoDto);					
							
				PreguntaDTO preguntaDto = new PreguntaDTO();
				preguntaDto.setIdPregunta(evaluacion.getPreguntas().size()+1);
				preguntaDto.setDescripcion("Promedio");
				preguntaDto.setConsecutivo(evaluacion.getPreguntas().size()+1);	
				preguntaDto.setGruposDTO(gruposDTOs);
				preguntasDto.add(preguntaDto);
				
				
				grupoEvaDto.setPromedioGeneral(proGe);
				grupoEvaDto.setPreguntas(preguntasDto);
				grupoEvaDto.setGrupo(grupo);
				grupoEvaDto.setComentarios(comentarios);
				grupoEvaDto.setDirectorCarrera(grupo.getCarrera().getDirectorCarrera());
				grupoEvaDto.setAlumnosEncuestados(aluEncuestados);
				gruposEvaDto.add(grupoEvaDto);
			
			}
			model.addAttribute("gruposEvaDto", gruposEvaDto);
		}				
		
		model.addAttribute("grupos", grupos);
		model.addAttribute("periodo", periodo);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteEvaluacionTutor";
	}
	  
	 @GetMapping("/reporte-tutorias-grupales") 
	 public String reporteTutoriasGrupales(Model model, HttpSession session) { 
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		int h=0;
		int m=0;			
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));			
		List<TemaGrupal> temasGrupales = new ArrayList<>();	
		Integer cveGrupo = (Integer) session.getAttribute("rtg-cveGrupo");						
		
		if(cveGrupo!=null) {									
			if((String) session.getAttribute("rtg-fechaInicio")!=null) {					
				Date fechaInicio = java.sql.Date.valueOf((String) session.getAttribute("rtg-fechaInicio"));				
				if((String) session.getAttribute("rtg-fechaFin")!=null) {						
					Date fechaFin = java.sql.Date.valueOf((String) session.getAttribute("rtg-fechaFin"));
					temasGrupales = temaGrupalService.buscarEntreFechasPorGrupo(cveGrupo,fechaInicio, fechaFin);
					List<Alumno> alumnos = alumnoService.buscarPorGrupo(cveGrupo);
					
					for(Alumno al : alumnos) {
						if(al.getPersona().getSexo().equals("M")){
							++m;
						}else{
							++h;
						}
					}
					
					model.addAttribute("fechaFin", fechaFin);
				}
				model.addAttribute("fechaInicio", fechaInicio);
			}															
		}
		
		model.addAttribute("mujeres", m);
		model.addAttribute("hombre", h);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("grupos", grupos);		
		model.addAttribute("temas", temasGrupales);	
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteTutoriaGrupal"; 
	 } 
	  
	 @GetMapping("/reporte-tutoria-individual") 
	 public String reporteTutoriaIndividual(Model model, HttpSession session) {	
			// Extrae el usuario a partir del usuario cargado en sesión.
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			int cvePersona;
			try {
				cvePersona = (Integer) session.getAttribute("cvePersona");
			} catch (Exception e) {
				cvePersona = usuario.getPersona().getId();
			}
			int h=0;
			int m=0;			
			List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));			
			List<TutoriaIndividual> tutorias = new ArrayList<>();	
			Integer cveGrupo = (Integer) session.getAttribute("rtg-cveGrupo");
			Integer cveAlumno = (Integer) session.getAttribute("rti-cveAlumno");	
			List<Alumno> alumnos = new ArrayList<>();
			Boolean allAlumnos = false;
			if(cveGrupo!=null) {				
				alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());	
				if((String) session.getAttribute("rti-fechaInicio")!=null) {					
					Date fechaInicio = java.sql.Date.valueOf((String) session.getAttribute("rti-fechaInicio"));				
					if((String) session.getAttribute("rti-fechaFin")!=null) {						
						Date fechaFin = java.sql.Date.valueOf((String) session.getAttribute("rti-fechaFin"));
						if(cveAlumno==null || cveAlumno==0) {							
							tutorias = tutoriaIndiService.buscarEntreFechasPorGrupo(cveGrupo, fechaInicio, fechaFin);							
							allAlumnos = true;
						}else{
							tutorias = tutoriaIndiService.buscarEntreFechasPorGrupoYAlumno(cveGrupo, cveAlumno, fechaInicio, fechaFin);
						}
						for(TutoriaIndividual tutoria : tutorias) {
							if(tutoria.getAlumno().getPersona().getSexo().equals("M")){
								++m;
							}else{
								++h;
							}
						}
						model.addAttribute("fechaFin", fechaFin);
					}
					model.addAttribute("fechaInicio", fechaInicio);
				}				
			}
			
			model.addAttribute("mujeres", m);
			model.addAttribute("hombre", h);
			model.addAttribute("allAlumnos", allAlumnos);				
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("cveAlumno", cveAlumno);
			model.addAttribute("grupos", grupos);	
			model.addAttribute("alumnos", alumnos);	
			model.addAttribute("tutorias", tutorias);	
			model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		 return "reportes/reporteTutoriaIndividual"; 
	 } 
	  
	 @GetMapping("/reporte-informacion-estudiante/{cveAlumno}") 
	 public String reporteInformacionEstudiante(@PathVariable("cveAlumno") Integer cveAlumno, Model model, HttpSession session) {		
		 Usuario usuario = (Usuario) session.getAttribute("usuario");
		 Date fechaActual = new Date();		 
		if(cveAlumno!=null) {
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			//Grupo ultimoGrupo = grupoService.buscarUltimoDeAlumno(alumno.getId());
			
			AlumnoGrupo AlGrupo = alumnoGrupoService.checkInscrito(cveAlumno, usuario.getPreferencias().getIdPeriodo());
			List<MateriaPromedioDTO> promediosMate = new ArrayList<>();
			
			if(AlGrupo!=null) {
				promediosMate = calMatService.buscarPorGrupoAlumno(AlGrupo.getGrupo().getId(), cveAlumno);			
				model.addAttribute("grupo", AlGrupo.getGrupo());
				model.addAttribute("calificasiones", promediosMate);
			}
			
			List<PagoGeneral> pagos = pagoGenService.buscarPorAlumno(cveAlumno, 1);
			List<PagoGeneral> adeudos = pagoGenService.buscarPorAlumno(cveAlumno, 0);
			List<TutoriaIndividual> tutorias =  tutoriaIndiService.buscarPorAlumno(alumno);
			List<Canalizacion> canalizaciones =  canalizacionService.buscarPorAlumno(alumno);
			
			model.addAttribute("alumno", alumno);
			model.addAttribute("pagos", pagos);
			model.addAttribute("adeudos", adeudos);
			model.addAttribute("tutorias", tutorias);
			model.addAttribute("canalizaciones", canalizaciones);			
		}		 
	  model.addAttribute("fechaActual", fechaActual);
	  model.addAttribute("NOMBRE_UT", NOMBRE_UT);
	  return "reportes/reporteInformacionEstudiante"; 
	 } 
	  
	 @GetMapping("/reporte-datos-contacto") 
	 public String reporteDatosContacto(Model model, HttpSession session) { 
			// Extrae el usuario a partir del usuario cargado en sesión.
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			int cvePersona;
			try {
				cvePersona = (Integer) session.getAttribute("cvePersona");
			} catch (Exception e) {
				cvePersona = usuario.getPersona().getId();
			}
						
			List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));			
			Integer cveGrupo = (Integer) session.getAttribute("rdc-cveGrupo");			
			List<Alumno> alumnos = new ArrayList<>();
			
			if (cveGrupo != null) {						
				alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());				
			}
			
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("grupos", grupos);
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("NOMBRE_UT", NOMBRE_UT);
	  return "reportes/reporteDatosContacto"; 
	 } 
	 	 
	 @GetMapping("/reporte-horario-clases") 
	 public String reporteHorarioClases(Model model, HttpSession session) { 
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
					
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));		
		Integer cveGrupo = (Integer) session.getAttribute("rhr-cveGrupo");
		
		if(cveGrupo!=null) {
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			//se extrae la lista de dias guardados en la BD				
			List<Dia> dias = serviceDia.buscarDias();				
			model.addAttribute("dias", dias);			
			
			//formato de hora 				
			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss"); 
			
			//Se extrae una lista de las horas unicas de la lista de horas asociadas al grupo			
			List<Horario> horas = serviceHorario.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
			model.addAttribute("horas", horas);
			
			//se crea una lista vacía para colocarle los datos de las horas de clase				
			List<HorarioDTO> horasDto = new ArrayList<>();
			
			//crea el horario con las horas vinculados al grupo			
			for (Horario hora : horas) {
														
				for (Dia dia : dias) {
					String horaI = dateFormat.format(hora.getHoraInicio());				
					//se genera el horario al comparar la lista de horas únicas y la lista de días 						
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
			model.addAttribute("nomGrupo", grupo.getNombre());
		}			
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
	  return "reportes/reporteHorarioClases"; 
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
					
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));		
		Integer cveGrupo = (Integer) session.getAttribute("rb-cveGrupo");
		List<Baja> bajas = new ArrayList<>();
		
		if(cveGrupo!=null) {
			Boolean GrupoEnPeriodo = grupoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			if(GrupoEnPeriodo==true) {
				bajas = bajaService.buscarPorTipoStatusGrupoYPeriodo(2, 2, cveGrupo, usuario.getPreferencias().getIdPeriodo());
			}
		}
		model.addAttribute("rol", 1);
		model.addAttribute("bajas", bajas);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteBajas"; 
	 } 
	 
	 @GetMapping("/reporte-canalizaciones") 
	 public String reporteCanalizaciones(Model model, HttpSession session) { 
		// extrae el usuario a partir del usuario cargado en sesión
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
					
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		List<Alumno> alumnos = new ArrayList<>();
		Integer cveGrupo = (Integer) session.getAttribute("rc-cveGrupo");
		Integer cveAlumno = (Integer) session.getAttribute("rc-cveAlumno");
		List<Canalizacion> canalizaciones = new ArrayList<>();
		Boolean allAlumnos = false;
		if(cveGrupo!=null) {
			alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			if(cveAlumno==null || cveAlumno==0) {
				canalizaciones = canalizacionService.buscarPorGrupoPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
				allAlumnos = true;
			}else{
				canalizaciones = canalizacionService.buscarPorGrupoPeriodoYAlumno(cveGrupo, usuario.getPreferencias().getIdPeriodo(), cveAlumno);
			}
		}
		
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("cveAlumno", cveAlumno);
		model.addAttribute("allAlumnos", allAlumnos);
		model.addAttribute("canalizaciones", canalizaciones);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteCanalizaciones"; 
	 }
	 
	 @GetMapping("/reporte-cal-grupo") 
	 public String reporteCalificacionesPorGrupo(Model model, HttpSession session) { 
		// extrae el usuario a partir del usuario cargado en sesión
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
					
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("rcg-cveGrupo");
			
		if (cveGrupo != null) {
			model.addAttribute("cveGrupo", cveGrupo);
			model.addAttribute("grupoActual", grupoService.buscarPorId(cveGrupo));
			//proceso para sacar las materias
			List<CargaHoraria> cargaHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());
			model.addAttribute("cargasHorarias", cargaHorarias);
			List<CorteEvaluativo> corte = corteEvaluativoService.buscarPorCarreraYPeriodo(usuario.getPreferencias().getIdCarrera(), usuario.getPreferencias().getIdPeriodo());
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
					alumnoDTO.setNombre(alumno.getPersona().getNombreCompleto());
					List<IndicadorMateriaDTO> indicadoresMaterias = new ArrayList<IndicadorMateriaDTO>();
					for (CargaHoraria ch : cargaHorarias) {
						IndicadorMateriaDTO im = new IndicadorMateriaDTO();
						im.setIdMateria(ch.getId());
						im.setNombre(ch.getMateria().getNombre());
						// variables para guardar promedios y remediales de la materia
						double promedioFinal = 0;
						double promedioparciales = 0;
						List<IndicadorParcialDTO> indicaroresParcial = new ArrayList<IndicadorParcialDTO>();
						for (CorteEvaluativo c : corte) {
							IndicadorParcialDTO ip = new IndicadorParcialDTO();
							// para sacar el promedio de la materia y sus indicadores
							double calificacionTotal = calificacionCorteService.buscarPorAlumnoCargaHorariaYCorteEvaluativo(alumno.getId(), ch.getId(), c.getId());
							// para guardar el promedio de los dos parciales
							promedioparciales = promedioparciales + calificacionTotal;
							ip.setIdMateria(ch.getMateria().getId());
							ip.setParcial(c.getId());
							ip.setPromedio(calificacionTotal);
							// se agrega el objeto a la lista de indicador parcial
							indicaroresParcial.add(ip);
						}
						//promedio final de la materia
						promedioFinal = Math.round(promedioparciales / corte.size());
						im.setPromedio(promedioFinal);
						//se egraga la lista de indicacires materia
						im.setParciales(indicaroresParcial);
						//se agrega el estatus de la materia
						im.setEstatus(calificacionMateriaService.buscarPorAlumnoYCarga(alumno.getId(), ch.getId()));
						// se agrega el objeto de indficador materia
						indicadoresMaterias.add(im);
					}
					//se agrega la lista de indicadores materia al alumno
					alumnoDTO.setMaterias(indicadoresMaterias);
					//se agregan los alumnos al arreglo
					alumnosCalificaciones.add(alumnoDTO);
				}
				model.addAttribute("carrera", grupoService.buscarPorId(cveGrupo).getCarrera().getNombre());
				model.addAttribute("alumnosCali", alumnosCalificaciones);
			}
		}
		model.addAttribute("grupos", grupos);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteCalificacionesPorGrupo"; 
	}
	 
	 @GetMapping("/reporte-entrevista-inicial") 
	 public String reporteEntrevistaInicial(Model model, HttpSession session) { 
		// extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
					
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));
		Integer cveGrupo = (Integer) session.getAttribute("rei-cveGrupo");
		Evaluacion evaluacion = evaluacionService.buscar(5);
		
		if (cveGrupo != null) {			
			List<Alumno> alumnos = alumnoService.buscarPorGrupoYPeriodo(cveGrupo, usuario.getPreferencias().getIdPeriodo());	
			Integer cvePersonaAl = (Integer) session.getAttribute("rei-cvePersonaAl");
			if(cvePersonaAl!=null) {
				//Se inyectan las respuestas asociadas a cada pregunta de la evaluación seleccionada 
				//asi como su repuesta si es que la ahi.		
				for (Pregunta pregunta : evaluacion.getPreguntas()) {
					
					//Se buscan las opciones de respuesta serrada
					List<OpcionRespuestaDTO> OpcionesRepuesta =  new ArrayList<OpcionRespuestaDTO>();				
					OpcionRespuestaDTO OpRes = resEvaIniService.buscarRespuestaPorPregunta(pregunta.getId(), cvePersonaAl, evaluacion.getId(), cveGrupo);
					OpcionesRepuesta.add(OpRes);
					pregunta.setOpcionesRespuesta(OpcionesRepuesta);					
					
					//Se buscan las opciones de respuesta abierta
					if(pregunta.getAbierta()==true) {
						RespuestaEvaluacionInicial respuestaEI = resEvaIniService.buscarRespuestaAbiertaPorPregunta(5, pregunta.getId(), cvePersonaAl, cveGrupo);
						pregunta.setComentarioRespuesta(respuestaEI != null ? respuestaEI.getRespuestaComentario().getComentario().getComentario() : null);
					}
					
				}	
			}
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("cvePersonaAl", cvePersonaAl);
		}
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);		
		model.addAttribute("evaluacion", evaluacion);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "reportes/reporteEntrevistaInicial"; 
	 }  
	
	// consulta	
	@GetMapping("/informacionEstudiante")
	public String informacionEstudiante() {		
		return "tutorias/informacionEstudiante";
	}
	
	// consulta
	@GetMapping("/manual")
	public String manual() {
		return "tutorias/manual";
	}
	
	//consulta
	@GetMapping("/reportes") 
	 public String reportesTutoria() { 
		return "tutorias/reportes"; 
	 } 
}
