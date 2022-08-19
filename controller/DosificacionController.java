package edu.mx.utdelacosta.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.CalendarioEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionCarga;
import edu.mx.utdelacosta.model.DosificacionComentario;
import edu.mx.utdelacosta.model.DosificacionImportada;
import edu.mx.utdelacosta.model.DosificacionTema;
import edu.mx.utdelacosta.model.DosificacionValida;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.TemaUnidad;
import edu.mx.utdelacosta.model.TipoProrroga;
import edu.mx.utdelacosta.model.UnidadTematica;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.DosificacionDTO;
import edu.mx.utdelacosta.model.dto.DosificacionTemaDto;
import edu.mx.utdelacosta.model.dto.DosificacionUnidadDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.ICalendarioEvaluacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IDosificacionCargaService;
import edu.mx.utdelacosta.service.IDosificacionComentarioService;
import edu.mx.utdelacosta.service.IDosificacionImportadaService;
import edu.mx.utdelacosta.service.IDosificacionService;
import edu.mx.utdelacosta.service.IDosificacionTemaService;
import edu.mx.utdelacosta.service.IDosificacionValida;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IProrrogaService;
import edu.mx.utdelacosta.service.IUnidadTematicaService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.CodificarTexto;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Director')")
@RequestMapping("/dosificacion")
public class DosificacionController {

	@Value("${spring.mail.username}")
	private String correo;

	@Autowired
	private IDosificacionService dosificacionService;

	@Autowired
	private ICalendarioEvaluacionService calendarioService;

	@Autowired
	private ICargaHorariaService cargaService;

	@Autowired
	private ICorteEvaluativoService corteService;

	@Autowired
	private IMecanismoInstrumentoService mecanismoService;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IDosificacionTemaService dosiTemaService;

	@Autowired
	private IDosificacionCargaService dosiCargaService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private IPeriodosService periodoService;

	@Autowired
	private IDosificacionComentarioService dosificacionComentarioService;

	@Autowired
	private EmailSenderService emailService;

	@Autowired
	private ICargaHorariaService cargaHorariaService;

	@Autowired
	private IDosificacionValida dosificacionValidaService;
	
	@Autowired
	private IProrrogaService prorrogaService;
	
	@Autowired
	private IDosificacionImportadaService dosificacionImportadaService;
	
	@Autowired
	private IUnidadTematicaService unidadTematicaService;

	@PostMapping(path = "/asignar-corte-unidad", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String asignarCorteUnidad(@RequestBody Map<String, String> obj, Model model, HttpSession session) {
		Integer unidad = Integer.parseInt(obj.get("unidad"));
		Integer carga = Integer.parseInt(obj.get("carga"));
		Integer corte = Integer.parseInt(obj.get("corte"));
		UnidadTematica unidadT = new UnidadTematica(unidad);
		CargaHoraria cargaH = new CargaHoraria(carga);
		CorteEvaluativo corteE = new CorteEvaluativo(corte);
		boolean dosificacionValida = comprobarDosificacionValida(carga);
		if (dosificacionValida == true) {
			return "val";
		}
		CalendarioEvaluacion calendario = calendarioService.buscarPorCargaHorariaYUnidadTematica(cargaH, unidadT);
		if (calendario == null) {
			calendario = new CalendarioEvaluacion();
			calendario.setCargaHoraria(cargaH);
			calendario.setCorteEvaluativo(corteE);
			calendario.setUnidadTematica(unidadT);
		} else {
			calendario.setCorteEvaluativo(corteE);
		}
		calendarioService.guarda(calendario);
		return "ok";
	}

	@PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardaDosificacion(@RequestBody Map<String, String> obj, HttpSession session) {

		CargaHoraria carga = cargaService.buscarPorIdCarga(Integer.parseInt(obj.get("idCargaHoraria")));
		Integer existeCorte = corteService.contarPorFechaDosificacionYPeriodoYCarreraYCorteEvaluativo(new Date(),
				carga.getPeriodo().getId(), carga.getGrupo().getCarrera().getId(),
				Integer.parseInt(obj.get("idCorteEvaluativo")));

		if (existeCorte == 0) {
			Prorroga prorroga = prorrogaService.buscarPorCargaHorariaYTipoProrrogaYCorteEvaluativoYActivoYAceptada(
					carga, new TipoProrroga(3), new CorteEvaluativo(Integer.parseInt(obj.get("idCorteEvaluativo"))),
					true, true);
			if (prorroga != null) {
				if (prorroga.getFechaLimite().before(new Date())) {
					return "limit";
				}
			} else {
				return "limit";
			}
		}

		CorteEvaluativo corte = corteService.buscarPorId(Integer.parseInt(obj.get("idCorteEvaluativo")));
		Persona profesor = personaService.buscarPorId(Integer.parseInt(obj.get("idProfesor")));
		Dosificacion dosificacion = dosificacionService.buscarPorIdCargaHorariaEIdCorteEvaluativo(carga.getId(),
				corte.getId());
		// si la dosificacion es nueva
		if (dosificacion == null) {

			// se crea la dosificacion y sus datos
			dosificacion = new Dosificacion();
			dosificacion.setIdCorteEvaluativo(corte.getId());
			dosificacion.setPersona(profesor);
			dosificacion.setFechaAlta(new Date());
			dosificacion.setValidaDirector(false);
			dosificacion.setTerminada(false);
			dosificacion.setActivo(true);

			DosificacionCarga dosiCargas = new DosificacionCarga();
			dosiCargas.setCargaHoraria(carga);
			dosiCargas.setActivo(true);
			dosiCargas.setDosificacion(dosificacion);
			dosiCargaService.guardar(dosiCargas);

		// en caso de que la dosificacion no este vacia
		}  else {

			if(!profesor.getId().equals(dosificacion.getPersona().getId())) {
				return "dif";
			}
			
			// si la dosificacion ya esta validada
			if (dosificacion.getValidaDirector() == true) {
				dosificacion.setTerminada(true);
				dosificacionService.guardar(dosificacion);
				return "up";
			} 

		}

		List<CalendarioEvaluacion> calendarios = calendarioService.buscarPorCargaHorariaYCorteEvaluativo(carga, corte);

		for (CalendarioEvaluacion calendarioEvaluacion : calendarios) {
			for (TemaUnidad temaUnidad : calendarioEvaluacion.getUnidadTematica().getTemasUnidad()) {
				DosificacionTema tema = dosiTemaService.buscarPorTemaYDosificacion(temaUnidad, dosificacion);
				if (tema == null) {
					tema = new DosificacionTema();
					tema.setTema(temaUnidad);
					tema.setDosificacion(dosificacion);
					tema.setHorasTeoricas(Integer.parseInt(obj.get("ht" + temaUnidad.getId())));
					tema.setHorasPracticas(Integer.parseInt(obj.get("hp" + temaUnidad.getId())));
					tema.setFechaInicio(java.sql.Date.valueOf(obj.get("fi" + temaUnidad.getId())));
					tema.setFechaFin(java.sql.Date.valueOf(obj.get("ff" + temaUnidad.getId())));
					dosiTemaService.guardar(tema);
				} else {
					tema.setHorasTeoricas(Integer.parseInt(obj.get("ht" + temaUnidad.getId())));
					tema.setHorasPracticas(Integer.parseInt(obj.get("hp" + temaUnidad.getId())));
					tema.setFechaInicio(java.sql.Date.valueOf(obj.get("fi" + temaUnidad.getId())));
					tema.setFechaFin(java.sql.Date.valueOf(obj.get("ff" + temaUnidad.getId())));
					dosiTemaService.guardar(tema);
				}
			}
		}

		// se crear un correo y se envia al director correspondiente

		Mail mail = new Mail();
		String de = correo;
		String para = carga.getGrupo().getCarrera().getEmailCarrera();
		mail.setDe(de);
		mail.setPara(new String[] { para });

		// Email title
		mail.setTitulo("Programación pendiente por validar");

		// Variables a plantilla
		Map<String, Object> variables = new HashMap<>();
		variables.put("titulo", "Programación de asignatura pendiente por validar");
		variables.put("cuerpoCorreo",
				"Tienes una programación de asignatura por validar de la materia " + carga.getMateria().getNombre());
		mail.setVariables(variables);
		try {
			emailService.sendEmail(mail);
			return "ok";
		} catch (MessagingException | IOException e) {
			return "mail";
		}

	}

	@GetMapping("/editar/{carga}/{corte}")
	public String editarDosificacion(@PathVariable(name = "carga", required = false) Integer carga,
			@PathVariable(name = "corte", required = false) Integer corte, 
			Model model, HttpSession session) {
		CargaHoraria cargaActual = cargaService.buscarPorIdCarga(carga);
		CorteEvaluativo corteActual = new CorteEvaluativo(corte);
		List<CalendarioEvaluacion> calendarios = calendarioService.buscarPorCargaHorariaYCorteEvaluativo(cargaActual,
				corteActual);
		if (!calendarios.isEmpty()) {

			List<DosificacionTema> temas = new ArrayList<>();

			Dosificacion dosificacion;
			
			dosificacion = dosificacionService.buscarPorIdCargaHorariaEIdCorteEvaluativo(cargaActual.getId(), corteActual.getId());				
			
			if (dosificacion != null) {
				temas = dosiTemaService.buscarPorDosificacion(dosificacion);
				model.addAttribute("dosiTemas", temas);
				model.addAttribute("dosificacion", dosificacion);
			}

			List<DosificacionTemaDto> temasDto = new ArrayList<>();
			for (UnidadTematica unidad : cargaActual.getMateria().getUnidadesTematicas()) {
				for (TemaUnidad temaU : unidad.getTemasUnidad()) {
					DosificacionTemaDto temaDto = new DosificacionTemaDto();
					temaDto.setId(temaU.getId());
					temaDto.setIdUnidad(unidad.getId());
					temaDto.setNombre(temaU.getTema());

					for (DosificacionTema temaD : temas) {
						if (temaU.getId().equals(temaD.getTema().getId())) {
							temaDto.setHorasPracticas(temaD.getHorasPracticas());
							temaDto.setHorasTeoricas(temaD.getHorasTeoricas());
							temaDto.setFechaFin(temaD.getFechaFin());
							temaDto.setFechaInicio(temaD.getFechaInicio());
						}
					}
					temasDto.add(temaDto);
				}
			}
			model.addAttribute("temasDto", temasDto);
			model.addAttribute("calendarios", calendarios);
		} else {
			model.addAttribute("activo", false);
		}

		model.addAttribute("unidades", cargaActual);
		model.addAttribute("corteActual", corteActual);
		model.addAttribute("cargaActual", cargaActual);

		return "fragments/modal-dosificacion:: editarDosificacion";
	}

	@PostMapping(path = "/importar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String importarDosificacion(@RequestBody Map<String, String> obj, HttpSession session) {
		Integer idCargaHoraria = Integer.parseInt(obj.get("idCargaHoraria"));
		Integer idCargaCompartida = Integer.parseInt(obj.get("idDosificacion"));
		if (idCargaHoraria != null && idCargaCompartida != null) {
			CargaHoraria cargaHoraria = new CargaHoraria(idCargaHoraria);
			CargaHoraria cargaCompartida = new CargaHoraria(idCargaCompartida);

			// SE COMPARA QUE TANTO LAS UNIDADES COMO LOS INSTRUMENTOS ESTÉN VACÍOS
			List<CalendarioEvaluacion> calendarios = calendarioService.buscarPorCargaHoraria(cargaHoraria);
			if (calendarios.size()>0) {
				return "calInv";
			}
			
			List<MecanismoInstrumento> mecanismos = mecanismoService.buscarPorIdCargaHorariaYActivo(cargaHoraria.getId(),
					true);
			if (mecanismos.size()>0) {
				return "mecInv";
			}
			
			// SE OBTIENEN LAS DOSIFICACIONES Y SE GUARDAN COMO NUEVAS USANDO LA CARGA
			// COMPARTIDA
			List<DosificacionCarga> dosificacionesCargas = dosiCargaService.buscarPorCargaHoraria(cargaCompartida);
			for (DosificacionCarga dosificacionCarga : dosificacionesCargas) {
		
				DosificacionImportada dImportada = new DosificacionImportada();
				dImportada.setFecha(new Date());
				dImportada.setCargaHoraria(cargaHoraria);
				dImportada.setDosificacion(dosificacionCarga.getDosificacion());
				dosificacionImportadaService.guardar(dImportada);
			}
			
			
			calendarios = calendarioService.buscarPorCargaHoraria(cargaCompartida);
			for (CalendarioEvaluacion calendario : calendarios) {
				CalendarioEvaluacion calen = new CalendarioEvaluacion();
				calen.setCargaHoraria(cargaHoraria);
				calen.setCorteEvaluativo(calendario.getCorteEvaluativo());
				calen.setUnidadTematica(calendario.getUnidadTematica());
				calendarioService.guarda(calen);
			}

			mecanismos = mecanismoService.buscarPorIdCargaHorariaYActivo(idCargaCompartida,
					true);
			for (MecanismoInstrumento mecanismo : mecanismos) {
				MecanismoInstrumento meca = new MecanismoInstrumento();
				meca.setActivo(true);
				meca.setArchivo(mecanismo.getArchivo());
				meca.setIdCargaHoraria(idCargaHoraria);
				meca.setIdCorteEvaluativo(mecanismo.getIdCorteEvaluativo());
				meca.setInstrumento(mecanismo.getInstrumento());
				meca.setPonderacion(mecanismo.getPonderacion());
				mecanismoService.guardar(meca);
			}
		}
		return "ok";
	}


	@PostMapping(path = "/validar-comentario", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String validarComentario(@RequestBody Map<String, String> obj, HttpSession session) {
		if (obj.size() != 0) {
			CargaHoraria cargaActual = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cveCarga"));
			for (DosificacionComentario comentario : cargaActual.getDosificacionComentarios()) {
				if (comentario.getSolucionado() == false) {
					String respuesta = obj.get(comentario.getId().toString());
					if (respuesta.equals("on")) {
						comentario.setLeido(true);
						comentario.setSolucionado(true);
						dosificacionComentarioService.guardar(comentario);
					}
				}
			}

			return "ok";
		}
		return "emp";

	}

	@GetMapping("/imprimir")
	public String imprimirDosificacionProfesor(Model model, HttpSession session) {
		// se obtienen las variab;es
		Integer idDosificacion = (Integer) session.getAttribute("Dosificacion");
		Integer idCargaHoraria = (Integer) session.getAttribute("DosificacionCarga");
		Dosificacion dosificacion = dosificacionService.buscarPorId(idDosificacion);
		CargaHoraria carga = cargaService.buscarPorIdCarga(idCargaHoraria);
		int ponderacion = 0;
		
		//se consultan los mecanismos por carga y que esten activos
		List<MecanismoInstrumento> mecanismos = mecanismoService.buscarPorIdCargaHorariaYActivo(idCargaHoraria, true);
		for (MecanismoInstrumento mecanismo : mecanismos) {
			if (mecanismo.getIdCorteEvaluativo() == dosificacion.getIdCorteEvaluativo()) {
				ponderacion = ponderacion + mecanismo.getPonderacion();
			}
		}
		
		//se crea la lista de dosificacion
		List<DosificacionUnidadDTO> unidadesDTO = new ArrayList<>();
		
		//se obtienen las unidades en base a la dosificacion
		List<UnidadTematica> unidades = unidadTematicaService.buscarPorDosificacion(idDosificacion);
	
		//se iteran las unidades
		for (UnidadTematica unidad : unidades) {
			DosificacionUnidadDTO unidadDTO = new DosificacionUnidadDTO();
			
			//se rellena el DTO con la informacion del tema de la unidad y la dosificacion correspondiente al tema
			unidadDTO.setConsecutivo(unidad.getConsecutivo());
			unidadDTO.setUnidad(unidad.getNombre());
			List<DosificacionTema> tema = dosiTemaService.buscarPorUnidadTematicaYDosificacion(unidad.getId(), idDosificacion);
			unidadDTO.setTemas(tema);
			unidadesDTO.add(unidadDTO);
			tema = null;
		}
		
		//se obtienen los colaboradores de la dosificacion
		String firmaProfesor = "";
		List<Persona> colaboradores = personaService.buscarColaboradoresPorDosificacion(idDosificacion);
		
		//se agrega el profesor actual a los colaboradores
		colaboradores.add(carga.getProfesor());
		for (Persona persona : colaboradores) {			
			//se codifica el id de cada colaborador y se agrega a la cadena
			try {
				firmaProfesor += " - "+CodificarTexto.encriptAES(persona.getId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//se obtienen el id del director en base a la carga
		Persona director = personaService.buscarDirectorPorCarga(carga.getId());
 		String firmaDirector = "";
		//se cifra el id del director
 		try {
			firmaDirector = CodificarTexto.encriptAES(director.getId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("unidades", unidadesDTO);
		model.addAttribute("firmaProfesor", firmaProfesor);
		model.addAttribute("firmaDirector", firmaDirector);
		model.addAttribute("colaboradores", dosificacionService.buscarColaboradoresPorDosificacion(idDosificacion));
		model.addAttribute("director", director.getNombreCompletoConNivelEstudio());
		model.addAttribute("mecanismos", mecanismos);
		model.addAttribute("ponderacion", ponderacion);
		model.addAttribute("ds", dosificacion);
		model.addAttribute("cargaHoraria", carga);
		model.addAttribute("fecha", new Date());
		return "profesor/impresionDosificacion";
	}

	// METODOS DE VISUALIZACION DE MODALES

	@GetMapping("/ver-fechas-entrega")
	public String verFechasEntrega(Model model, HttpSession session) {
		// se crea la carga horaria actual para obtener los temas de la materia
		CargaHoraria cargaActual = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cveCarga"));
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = new Periodo(usuario.getPreferencias().getIdPeriodo());

		// se buscan los cortes del periodo actual
		// List<CorteEvaluativo> cortes = corteService.buscarPorPeriodo(periodo);
		List<CorteEvaluativo> cortes = corteService.buscarPorCarreraYPeriodo(cargaActual.getGrupo().getCarrera(),
				periodo);

		// se consulta el calendario evaluacion para obtener a que corte pertenece que
		// tema
		List<CalendarioEvaluacion> calendarios = calendarioService.buscarPorCargaHoraria(cargaActual);

		// se crea una lista de temaDto
		List<DosificacionTemaDto> temaDto = new ArrayList<>();
		for (UnidadTematica unidad : cargaActual.getMateria().getUnidadesTematicas()) {

			// se crea un objeto de temaDto y se rellena con la informacion necesaria
			DosificacionTemaDto tema = new DosificacionTemaDto();
			tema.setId(unidad.getId());
			tema.setNombre(unidad.getNombre());

			// en caso de existir un calendario que coincida con el tema se consulta y se
			// agrega el corte perteneciente al DTO
			for (CalendarioEvaluacion calendario : calendarios) {
				if (unidad.getId().equals(calendario.getUnidadTematica().getId())) {
					tema.setIdCorte(calendario.getCorteEvaluativo().getId());
				}
			}
			temaDto.add(tema);
		}
		model.addAttribute("cActual", cargaActual);
		model.addAttribute("cortes", cortes);
		model.addAttribute("unidades", temaDto);
		return "fragments/modal-dosificacion:: editarFechasEntrega";
	}

	@GetMapping("/ver-copiar-instrumentos")
	public String verCopiarInstrumentos(Model model, HttpSession session) {
		// obtencion de variables
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		
		//se construye la carga actual
		CargaHoraria cActual = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cveCarga"));
		
		// se buscan las cargas apatas para el copiado de instrumentos
		List<CargaHoraria> cargaFinal = cargaService.buscarPorProfesorYPeriodoYCalendarioEvaluacion(persona.getId(), periodo.getId(), cActual.getId());
		model.addAttribute("cActual", cActual);
		model.addAttribute("cargasFinal", cargaFinal);
		return "fragments/modal-dosificacion:: verCopiarInstrumentos";
	}

	// funcion que actualiza la vista principal de los cortes
	@GetMapping("/cargar")
	public String cargarDosificacion(Model model, HttpSession session) {
		CargaHoraria cargaActual = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cveCarga"));
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = new Periodo(usuario.getPreferencias().getIdPeriodo());

		List<CorteEvaluativo> cortes = corteService.buscarPorCarreraYPeriodo(cargaActual.getGrupo().getCarrera(),
				periodo);
		List<MecanismoInstrumento> mecanismos = mecanismoService.buscarPorIdCargaHorariaYActivo(cargaActual.getId(),
				true);
		List<Dosificacion> dosificaciones = dosificacionService.buscarPorIdCargaHoraria(cargaActual.getId());

		model.addAttribute("dosificaciones", dosificaciones);
		model.addAttribute("mecanismos", mecanismos);
		model.addAttribute("cortes", cortes);
		model.addAttribute("cActual", cargaActual);
		return "fragments/modal-dosificacion:: InstrumentosDosificacion";
	}

	// metodos genericos
	public boolean comprobarDosificacionValida(Integer idCarga) {
		List<Dosificacion> dosificaciones = dosificacionService.buscarPorIdCargaHoraria(idCarga);
		for (Dosificacion dosificacion : dosificaciones) {
			if (dosificacion.getValidaDirector() == true) {
				return true;
			}
		}
		return false;
	}

	@GetMapping(path = "/get/{id}")
	public String getDosificacion(@PathVariable(name = "id", required = false) Integer idCarga, Model model, HttpSession session) {
		//se trae las dosificaciones por carga horaria 
		List<Dosificacion> dosificaciones = dosificacionService.buscarPorCargaHoraria(idCarga);
		CargaHoraria carga = cargaService.buscarPorIdCarga(idCarga);
		//se crea una lista de dosificacionDTO
		List<DosificacionDTO> dosificacionesDTO = new ArrayList<>();
		// se iteran las dosificaciones y se guardan en un dto
		for (Dosificacion d : dosificaciones) {
			//se crea objeto de dosificacionDTO
			DosificacionDTO dosiDTO = new DosificacionDTO();
			//se agrega el objeto de dosificacion
			dosiDTO.setDosificacion(d);
			
			//se crea la lista de instrumentos
			List<MecanismoInstrumento> mecanismos = mecanismoService.buscarPorIdCargaHorariaYActivo(idCarga, true);
			//se agregan los mecanismos a la dosificacion
			dosiDTO.setMecanismos(mecanismos);

			//se crea la lista de unidades temáticas
			List<DosificacionUnidadDTO> unidadesDTO = new ArrayList<>();
			List<UnidadTematica> unidades = unidadTematicaService.buscarPorDosificacion(d.getId());
			for (UnidadTematica unidad : unidades) {
				//se agregan las unidades y temas 
				DosificacionUnidadDTO unidadDTO = new DosificacionUnidadDTO();
				unidadDTO.setConsecutivo(unidad.getConsecutivo());
				unidadDTO.setUnidad(unidad.getNombre());
				List<DosificacionTema> tema = dosiTemaService.buscarPorUnidadTematicaYDosificacion(unidad.getId(),
						d.getId());
				//se agrega la lista de temas a la unidad
				unidadDTO.setTemas(tema);
				//se agrega la unidad a la lista de unidades
				unidadesDTO.add(unidadDTO);
				tema = null;
			}
			//se agrega la lista de unidades a la dosificación
			dosiDTO.setUnidades(unidadesDTO);
			// se agrega la dosificación a la lista de dosificaciones
			dosificacionesDTO.add(dosiDTO);
		}
		model.addAttribute("dosificaciones", dosificacionesDTO);
		model.addAttribute("cargaHoraria", carga);
		model.addAttribute("idCarga", idCarga);
		return "fragments/panel-dosificacion :: panel-dosificacion";
	}

	@PostMapping(path = "/rechazar")
	@ResponseBody
	public String rechazarDosificacion(@RequestBody Map<String, String> obj, HttpSession session) {
		// objetos de la persona en sesión
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		// Usuario usuario = usuarioService.buscarPorPersona(persona);
		int idCargaHoraria = Integer.valueOf(obj.get("idCargaHoraria"));
		int idProfesor = Integer.valueOf(obj.get("idPersona"));
		String comentario = obj.get("comentario");
		// se crea el objeto de dosificacionComentario para insertar sus datos
		DosificacionComentario dosificacionComentario = new DosificacionComentario();
		dosificacionComentario.setIdCargaHoraria(idCargaHoraria);
		dosificacionComentario.setIdPersona(persona.getId());
		dosificacionComentario.setFechaAlta(new Date());
		dosificacionComentario.setLeido(false);
		dosificacionComentario.setSolucionado(false);
		dosificacionComentario.setComentario(comentario);
		dosificacionComentarioService.guardar(dosificacionComentario);
		// se envia correo al profesor
		Persona profesor = personaService.buscarPorId(idProfesor);
		CargaHoraria cargaHoraria = cargaHorariaService.buscarPorIdCarga(idCargaHoraria);

		Mail mail = new Mail();
		String de = correo;
		// se deberá enviar el correo al profesor
		String para = profesor.getEmail();
		mail.setDe(de);
		mail.setPara(new String[] { para });
		// Email title
		mail.setTitulo("¡Dosificación rechazada!");
		// Variables a plantilla
		Map<String, Object> variables = new HashMap<>();
		variables.put("titulo", "Dosificación rechazada");
		variables.put("cuerpoCorreo", "Tu dosificación de la materia: " + cargaHoraria.getMateria().getNombre()
				+ " fue rechazada por el siguiente motivo: " + comentario + ".");
		mail.setVariables(variables);
		try {
			emailService.sendEmail(mail);
		} catch (MessagingException | IOException e) {
		}

		return "ok";
	}

	@PostMapping(path = "/validar")
	@ResponseBody
	public String validarDosificacion(@RequestBody Map<String, Integer> obj, HttpSession session) {
		// objetos de la persona en sesión
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		int idCargaHoraria = obj.get("idCargaHoraria");
		int idDosificacion = obj.get("idDosificacion");
		CargaHoraria cargaHoraria = cargaHorariaService.buscarPorIdCarga(idCargaHoraria);
		// para enviar el correo al director
		Persona profesor = personaService.buscarPorId(cargaHoraria.getProfesor().getId());
		// se crea el objeto de la dosificacion
		Dosificacion dosificacion = dosificacionService.buscarPorId(idDosificacion);
		dosificacion.setValidaDirector(true);
		dosificacionService.guardar(dosificacion);
		// se crea el objeto dosificacionValida para guardar los datos
		DosificacionValida dosificacionValida = new DosificacionValida();
		dosificacionValida.setDosificacion(dosificacion);
		dosificacionValida.setDirector(persona);
		dosificacionValida.setFechaAlta(new Date());
		dosificacionValidaService.guardar(dosificacionValida);
		// se envia correo al profesor

		Mail mail = new Mail();
		String de = correo; // se deberá enviar el correo al profesor
		String para = profesor.getEmail();
		mail.setDe(de);
		mail.setPara(new String[] { para }); // Email title
		mail.setTitulo("¡Dosificación validada!");
		// Variables a plantilla
		Map<String, Object> variables = new HashMap<>();
		variables.put("titulo", "Dosificación validada");
		variables.put("cuerpoCorreo",
				"Tu dosificación de la materia: " + cargaHoraria.getMateria().getNombre() + " fue validada.");
		mail.setVariables(variables);
		try {
			emailService.sendEmail(mail);
		} catch (MessagingException | IOException e) {
		}

		return "ok";
	}

}