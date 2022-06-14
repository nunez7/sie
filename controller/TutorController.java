package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
import edu.mx.utdelacosta.model.AsistenciaTemaGrupal;
import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.Foco;
import edu.mx.utdelacosta.model.FocosAtencion;
import edu.mx.utdelacosta.model.Fortaleza;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Motivo;
import edu.mx.utdelacosta.model.MotivoTutoria;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.ProgramacionTutoria;
import edu.mx.utdelacosta.model.TemaGrupal;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.AlumnoAsistenciaDTO;
import edu.mx.utdelacosta.model.dto.AlumnoInfoDTO;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;
import edu.mx.utdelacosta.model.dto.GrupoDTO;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.PreguntaDTO;
import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;
import edu.mx.utdelacosta.model.dto.TutoriasProgramadasDTO;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IAsistenciaTemaGrupalService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.IComentarioEvaluacionTutorService;
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
import edu.mx.utdelacosta.service.IPreguntaService;
import edu.mx.utdelacosta.service.IProgramacionTutoriaService;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionTutorService;
import edu.mx.utdelacosta.service.ITemaGrupalService;
import edu.mx.utdelacosta.service.ITutoriaIndividualService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Informatica') ")
@RequestMapping("/tutorias")
public class TutorController {
	
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
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Servicios Escolares')")
    @GetMapping("/cargar-alumno/{dato}")
   	public String cargarAlumnos(@PathVariable(name = "dato", required = false) String dato,  Model model, HttpSession session) { 
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
				
    	List<AlumnoInfoDTO> alumnos = new ArrayList<>();
    	if(dato!=null) {    		    		
    		alumnos = alumnoService.buscarPorProfesorPeriodoYNombreOMatricula(cvePersona, usuario.getPreferencias().getIdPeriodo(), dato);
    	}else {    		
    		alumnos = alumnoService.buscarPorProfesorYPeriodo(cvePersona, usuario.getPreferencias().getIdPeriodo());
    	}
		model.addAttribute("alumnos", alumnos);
		
		return "fragments/control-alumnos :: res-alumnos";
   	}
	
	@GetMapping("/tutorias")
	public String tutorias() {
		return "tutorias/gruposTutorados";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
	@PostMapping(path = "/save-individual", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarIndividual(@RequestBody Map<String, String> obj,HttpSession session) throws ParseException {			
		if(obj.get("idAlumno")!=null) {
			SimpleDateFormat dFHora = new SimpleDateFormat("hh:mm");
			SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");
			
			Integer cveGrupo = (Integer) session.getAttribute("t-cveGrupo");
			Integer cveAlumno = Integer.parseInt(obj.get("idAlumno"));
			Date fecha = dFDia.parse(obj.get("fechaInicioTutoria"));			
			Date horaInicio = dFHora.parse(obj.get("horaInicio"));
			Date horaFin = dFHora.parse(obj.get("horaFin"));
			
			//recibo los motivos de la tutoria como un string y los comvierto a una ArrayList<Integer>
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
			
			Usuario usuario = usuarioService.buscarPorUsuario(usuarioMatricula);												
			
			if(usuario!=null) {								
				Boolean valContra =  passwordEncoder.matches(userNip, usuario.getContrasenia());				
				if(valContra == true) {													
					TutoriaIndividual tutInd = new TutoriaIndividual();
					tutInd.setAlumno(new Alumno(cveAlumno));
					tutInd.setGrupo(new Grupo(cveGrupo));
					tutInd.setFechaRegistro(fecha);
					tutInd.setHoraInicio(horaInicio);
					tutInd.setHoraFin(horaFin);
					tutInd.setPuntosImportantes(puntosRelevantes);
					tutInd.setCompromisosAcuerdos(compromisosAcuerdos);
					tutInd.setNivelAtencion(nvAtencion);
					tutInd.setValidada(false);
					tutoriaIndiService.guardar(tutInd);
					
					for(int i = 0; i < motivos.size(); i++) {
						MotivoTutoria motivoTutoria = new MotivoTutoria();
						motivoTutoria.setTutoriaIndividual(tutInd);
						motivoTutoria.setMotivo(new Motivo(motivos.get(i)));
						motivoTutoService.guardar(motivoTutoria);
					}
					
					return "ok";
				}else{
					return "PaError";
				}
			}else{
				return "NoUser";
			}
		}
		return "NoAl";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
	@PostMapping(path = "/save-baja", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarBaja(@RequestBody Map<String, String> obj) {			
		if(obj.get("idAlumno")!=null) {
			return "ok";
		}
		return "NoAl";
	}
	
	@GetMapping("/tutoriaIndividual")
	public String tutoriaIndividual(Model model, HttpSession session) {
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
		if (cveGrupo != null) {						
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);			
		}
		
		List<Motivo> motivos = motivoService.buscarTodo();		
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("motivos", motivos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/tutoriaIndividual";		
	}
	
	@GetMapping("/bajaAlumno")
	public String bajaAlumno(Model model, HttpSession session) {
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));		
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");		
		List<Alumno> alumnos = new ArrayList<>();		
		
		if (cveGrupo != null) {						
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);			
		}
		
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/bajaAlumno";		
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
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);
			fortalezas = fortalezaService.buscarPorGrupo(new Grupo(cveGrupo));
			focosAtencion = focoAtenService.buscarPorGrupo(new Grupo(cveGrupo));
			temasGrupales = temaGrupalService.buscarPorGrupo(new Grupo(cveGrupo));	
		}
		
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("fortalezas", fortalezas);
		model.addAttribute("focosAtencion", focosAtencion);
		model.addAttribute("temasGrupales", temasGrupales);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/tutoriaGrupal";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
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
		return "fragments/tutorias-grupales :: cargar-fortalezas";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
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
		return "fragments/tutorias-grupales :: cargar-focos";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
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
					temaGrupalService.guardar(temaGrupal);
					return "ok";
				}
			}else{
				return "Df";
			}			
		}		
		return "noGrupo";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Servicios Escolares')")
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
				asistencia.setAsiencia(tipoAsistencia);
				asisTemaGruService.guardar(asistencia);
				return "ok";
			}else{
				asistenciaTemaGrupal.setAsiencia(tipoAsistencia);
				asisTemaGruService.guardar(asistenciaTemaGrupal);
				return "ok";
			}
			
		}
		
		return "Error";
	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
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
			if(temaGrupal.getFechaRegistro()==null) {
				temaGrupal.setFechaRegistro(fechaHoy);
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
		return "fragments/tutorias-grupales :: cargar-temas";
	}
	
	@GetMapping("/programacionTutorias")
	public String programacionTutorias(Model model, HttpSession session) {
		// extrae el usuario apartir del usuario cargado en cesion.
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
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);			
			
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
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
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
	
	@GetMapping("/canalizacion")
	public String canalizacion(Model model, HttpSession session) {
		// extrae el usuario apartir del usuario cargado en cesion.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
					
		List<Grupo> grupos = grupoService.buscarPorProfesorYPeriodoAsc(new Persona(cvePersona), new Periodo(usuario.getPreferencias().getIdPeriodo()));		
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupo");		
		List<Alumno> alumnos = new ArrayList<>();
		
		if (cveGrupo != null) {						
			alumnos = alumnoService.buscarPorGrupo(cveGrupo);			
		}
		
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
		return "tutorias/canalizacion";
	}
	
	@GetMapping("/reporte-evaluacion-tutor")
	public String encuestaEvaluacionTutor(Model model, HttpSession session) {
		// extrae el usuario apartir del usuario cargado en cesion.
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
			aluEncuestados = resEvaTutorService.contarEncuestadosPorGrupo(4, cveGrupo);			
			List<ComentarioDTO> comentarios = comEvaTurtorService.buscarComentariosPorGrupo(4, cveGrupo);
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			//se caulculan los promedios de cada una de las preguntas para cada uno de los grupos en lo que el profesor imparte dicha materia en determinda carrera
			List<PreguntaDTO> preguntasDto = new ArrayList<>();	
			double promedioPre=0.0;
			for(Pregunta pre :evaluacion.getPreguntas()) {
				PromedioPreguntaDTO promedioPreguntaDTOs = preguntasService.ObtenerPromedioEvaTuPorPregunta(4, pre.getId(), cveGrupo);
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
			double proGe=0.0;					
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
			
			model.addAttribute("proGe", proGe);
			model.addAttribute("preguntas", preguntasDto);
			model.addAttribute("grupo", grupo);
			model.addAttribute("comentarios", comentarios);
			model.addAttribute("drCarrera", grupo.getCarrera().getDirectorCarrera());
		}				
		
		model.addAttribute("grupos", grupos);
		model.addAttribute("usuario", usuario);	
		model.addAttribute("periodo", periodo);
		model.addAttribute("cveGrupo", cveGrupo);
		model.addAttribute("aluEncuestados", aluEncuestados);
		return "reportes/reporteEvaluacionTutor";
	}
	  
	 @GetMapping("/reporte-tutorias-grupales") 
	 public String reporteTutoriasGrupales() { 
	  return "reportes/reporteTutoriaGrupal"; 
	 } 
	  
	 @GetMapping("/reporte-tutoria-individual") 
	 public String reporteTutoriaIndividual() {		
	  return "reportes/reporteTutoriaIndividual"; 
	 } 
	  
	 @GetMapping("/reporte-informacion-estudiante/{cveAlumno}") 
	 public String reporteInformacionEstudiante(@PathVariable("cveAlumno") Integer cveAlumno, Model model) {		
		Date fechaActual = new Date();		 
		if(cveAlumno!=null) {
			Alumno alumno = alumnoService.buscarPorId(cveAlumno);
			Grupo ultimoGrupo = grupoService.buscarUltimoDeAlumno(alumno.getId());
			
			if(ultimoGrupo!=null) {
				List<MateriaPromedioDTO> promediosMate = calMatService.buscarPorGrupoAlumno(ultimoGrupo.getId(), cveAlumno);			
				model.addAttribute("grupo", ultimoGrupo);
				model.addAttribute("calificasiones", promediosMate);
			}
			
			List<PagoGeneral> pagos = pagoGenService.buscarPorAlumno(cveAlumno, 1);
			List<PagoGeneral> adeudos = pagoGenService.buscarPorAlumno(cveAlumno, 0);
		
			model.addAttribute("alumno", alumno);
			model.addAttribute("pagos", pagos);
			model.addAttribute("adeudos", adeudos);
		}		 
	  model.addAttribute("fechaActual", fechaActual);
	  return "reportes/reporteInformacionEstudiante"; 
	 } 
	  
	 @GetMapping("/reporte-datos-contacto") 
	 public String reporteDatosContacto(Model model, HttpSession session) { 
			// extrae el usuario apartir del usuario cargado en cesion.
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
				alumnos = alumnoService.buscarPorGrupo(cveGrupo);			
			}
			
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("grupos", grupos);
			model.addAttribute("cveGrupo", cveGrupo);
	  return "reportes/reporteDatosContacto"; 
	 } 
	 
	  
	 @GetMapping("/reporte-horario-clases") 
	 public String reporteHorarioClases(Model model, HttpSession session) { 
		// extrae el usuario apartir del usuario cargado en cesion.
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
			model.addAttribute("nomGrupo", grupo.getNombre());
		}			
		model.addAttribute("grupos", grupos);
		model.addAttribute("cveGrupo", cveGrupo);
	  return "reportes/reporteHorarioClases"; 
	 }
	 
//		@GetMapping("/entrevistaInicial")
//		public String encuestaEntrevistaInicial() {
//			return "encuestas/encuestaEntrevistaInicial";
//		}
	 
//	 @GetMapping("/reporte-entrevista-inicial") 
//	 public String reporteEntrevistaInicial() { 
//	  return "reportes/reporteEntrevistaInicial"; 
//	 }  
	
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
