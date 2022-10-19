package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CalendarioEvaluacion;
import edu.mx.utdelacosta.model.Calificacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.Instrumento;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICalendarioEvaluacionService;
import edu.mx.utdelacosta.service.ICalificacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IDosificacionService;
import edu.mx.utdelacosta.service.IInstrumentoService;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.ActualizarCalificacion;
import edu.mx.utdelacosta.util.SubirArchivo;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica')")
@RequestMapping("/mecanismo")
public class MecanismoInstrumentoController {

	@Autowired
	private IDosificacionService dosificacionService;

	@Autowired
	private ICalendarioEvaluacionService calendarioService;

	@Autowired
	private ICargaHorariaService cargaService;

	@Autowired
	private ICorteEvaluativoService corteService;

	@Autowired
	private IInstrumentoService instrumentoService;

	@Autowired
	private IMecanismoInstrumentoService mecanismoService;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private IPeriodosService periodoService;

	@Autowired
	private ICalificacionService calificacionService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private ActualizarCalificacion actualizarCalificacion;

	@Value("${siestapp.ruta.docs}")
	private String rutaDocs;

	@GetMapping("/ver-instrumento-archivo/{idMecanismo}")
	public String verEditarInstrumentos(@PathVariable(name = "idMecanismo", required = false) Integer idMecanismo,
			Model model) {
		MecanismoInstrumento mecanismo = mecanismoService.buscarPorIdYActivo(idMecanismo, true);
		model.addAttribute("mecanismo", mecanismo);
		return "fragments/modal-dosificacion:: subirArchivo";
	}

	@GetMapping("/ver-instrumentos/{carga}/{corte}")
	public String verInstrumentosDinamica(@PathVariable(name = "carga", required = false) Integer idCarga,
			@PathVariable(name = "corte", required = false) Integer idCorte, Model model, HttpSession session) {
		
		List<MecanismoInstrumento> mecanismos = mecanismoService.buscarPorIdCargaHorariaYActivo(idCarga, true);

		model.addAttribute("corte", corteService.buscarPorId(idCorte));
		model.addAttribute("cActual", new CargaHoraria(idCarga));
		model.addAttribute("mecanismos", mecanismos);

		return "fragments/modal-dosificacion:: listaInstrumentosDinamica";
	}

	@GetMapping("/lista-instrumentos")
	public String verInstrumentos(Model model, HttpSession session) {
		Integer cveCarga = (Integer) session.getAttribute("cveCarga");
		CargaHoraria carga = cargaService.buscarPorIdCarga(cveCarga); 
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		Periodo periodo = periodoService.buscarPorId(usuario.getPreferencias().getIdPeriodo());
		List<Instrumento> instrumentos = instrumentoService.buscarTodos();
		List<CorteEvaluativo> cortes = corteService.buscarPorCarreraYPeriodo(carga.getGrupo().getCarrera(),periodo);
		List<Integer> totales = new ArrayList<>();
		for (CorteEvaluativo corte : cortes) {
			totales.add(mecanismoService.sumaPonderacionPorIdCargaHorariaEIdCorteEvaluativo(cveCarga, corte.getId()));
		}
		List<MecanismoInstrumento> mecanismos = mecanismoService.buscarPorIdCargaHorariaYActivo(cveCarga, true);
		model.addAttribute("mecanismos", mecanismos);
		model.addAttribute("cortes", cortes);
		model.addAttribute("totales", totales);
		model.addAttribute("instrumentos", instrumentos);
		model.addAttribute("cActual", new CargaHoraria((Integer) session.getAttribute("cveCarga")));

		return "fragments/modal-dosificacion:: cargaInstrumento";
	}

	@PostMapping(path = "/guardar-instrumento", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardaInstrumento(@RequestBody Map<String, String> obj, Model model) {
		Integer carga = Integer.parseInt(obj.get("idCargaHoraria"));
		Integer corte = Integer.parseInt(obj.get("idCorteEvaluativo"));
		Integer instrumento = Integer.parseInt(obj.get("idInstrumento"));
		//MecanismoInstrumento mecanismo = mecanismoService.buscarPorIdCargaHorariaEIdCorteEvaluativoEInstrumentoYActivo(
		//		carga, corte, instrumentoService.buscarPorId(instrumento), true);
		
		//comprobamos si la dosificacion esta validada
		boolean dosificacionValida = comprobarDosificacionValida(carga, corte);
		if (dosificacionValida == true) {
			return "val";
		}
		
		//obtenemos el total de instrumentos y comparamos si se alcanzo el limite maximo
		List<MecanismoInstrumento> total = mecanismoService.buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(carga,
				corte, true);
		if (total.size() > 2) {
			return "max";
		}
		
		Integer provicional = null;
		if(total.size()==2) {
			provicional = 0;
			for (MecanismoInstrumento mi : total) {
				provicional = provicional+mi.getPonderacion();
			}
			provicional = 100 - provicional;
		}

		// comparamos si el corte tiene asignado una unidad
		List<CalendarioEvaluacion> calendarios = calendarioService
				.buscarPorCargaHorariaYCorteEvaluativo(new CargaHoraria(carga), new CorteEvaluativo(corte));
		if (calendarios.size() == 0) {
			return "non";
		}

		// se guarda el istrumento - se retiro la limitacion de 1 instrumento por tipo
		MecanismoInstrumento mecanismo = new MecanismoInstrumento();
		mecanismo.setActivo(true);
		mecanismo.setIdCargaHoraria(carga);
		mecanismo.setIdCorteEvaluativo(corte);
		mecanismo.setInstrumento(new Instrumento(instrumento));
		if (provicional!=null) {
			mecanismo.setPonderacion(provicional>0 && provicional<=80 ? provicional : 0);
		}else {
			mecanismo.setPonderacion(0);
		}
		mecanismoService.guardar(mecanismo);
		return "ok";
		
	}

	@PostMapping(path = "/eliminar-instrumento", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminaInstrumento(@RequestBody Map<String, String> obj, HttpSession session) {
		MecanismoInstrumento mecanismo = mecanismoService.buscarPorId(Integer.parseInt(obj.get("idInstrumento")));
		boolean dosificacionValida = comprobarDosificacionValida((Integer) session.getAttribute("cveCarga"), mecanismo.getIdCorteEvaluativo());
		if (dosificacionValida == true) {
			return "val";
		}
		List<Calificacion> calificaciones = calificacionService.buscarPorMecanismoInstrumento(mecanismo);
		if (calificaciones.size()>0) {
			
			for (Calificacion calificacion : calificaciones) {
				calificacionService.eliminar(calificacion);
			}
			
			
			
			CargaHoraria carga = cargaService.buscarPorIdCarga(mecanismo.getIdCargaHoraria());
			List<Alumno> alumnos = alumnoService.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(carga.getGrupo().getId());
			
			
			for (Alumno alumno : alumnos) {
				float calificacionCorte = actualizarCalificacion.actualizaCalificacionCorte(alumno.getId(),
						mecanismo.getIdCargaHoraria(), mecanismo.getIdCorteEvaluativo());
				actualizarCalificacion.actualizaTestimonioCorte(alumno.getId(), calificacionCorte,
						mecanismo.getIdCargaHoraria(), mecanismo.getIdCorteEvaluativo());
				actualizarCalificacion.actualizaCalificacionMateria(alumno.getId(), mecanismo.getIdCargaHoraria());
			}
			
		}
		

		mecanismoService.eliminar(mecanismo);
		return "ok";
	}

	@PostMapping(path = "/guardar-ponderacion", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardaPonderacion(@RequestBody Map<String, String> obj, Model model, HttpSession session) {
		Integer ponderacion = Integer.parseInt(obj.get("ponderacion")); // se recibe el dato
		Integer idMeca = Integer.parseInt(obj.get("idMecanismo")); // se recibe el dato
	//	if (ponderacion > 0 && ponderacion <= 85) { // se comprueba que su valor sea mayor a 0 y menor o igual a 85
		if (ponderacion > 0) { // se comprueba que su valor sea mayor a 0
			MecanismoInstrumento mecanismo = mecanismoService.buscarPorIdYActivo(idMeca, true); // se busca si el
																								// mecanismo
			boolean dosificacionValida = comprobarDosificacionValida((Integer) session.getAttribute("cveCarga"), mecanismo.getIdCorteEvaluativo());
			if (dosificacionValida == true) {
				return "val";
			}
			
			List<MecanismoInstrumento> lista = mecanismoService.buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(
					Integer.parseInt(obj.get("cargaActual")), Integer.parseInt(obj.get("corteEvaluativo")), true);
			if (lista != null) { // se compara el tamano de la lista
				Integer ponderacionMaxima = 0;
				for (MecanismoInstrumento mecanismos : lista) { // se cuenta la ponderacion maxima en base
					if (mecanismos.getPonderacion() != null && !idMeca.equals(mecanismos.getId())) {
						ponderacionMaxima = ponderacionMaxima + mecanismos.getPonderacion();
					}
				}
				Integer ponderacionTotal = ponderacionMaxima + ponderacion;
				if (ponderacionTotal > 100) {
					return "limit";
				}
				if (mecanismo != null) { // en caso de no exsitir se crea uno nuevo, se agregan sus valores y se guarda
					mecanismo.setPonderacion(ponderacion);
					mecanismoService.guardar(mecanismo);
					
					boolean existe = actualizarCalificacion.existeCalificacion(mecanismo.getId());
					if (existe==true) {
						
						CargaHoraria carga = cargaService.buscarPorIdCarga(mecanismo.getIdCargaHoraria());
						List<Alumno> alumnos = alumnoService
								.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(carga.getGrupo().getId());
						for (Alumno alumno : alumnos) {
							float calificacionCorte = actualizarCalificacion.actualizaCalificacionCorte(alumno.getId(),
									mecanismo.getIdCargaHoraria(), mecanismo.getIdCorteEvaluativo());
							actualizarCalificacion.actualizaTestimonioCorte(alumno.getId(), calificacionCorte,
									mecanismo.getIdCargaHoraria(), mecanismo.getIdCorteEvaluativo());
							actualizarCalificacion.actualizaCalificacionMateria(alumno.getId(),
									mecanismo.getIdCargaHoraria());
						}
					}

					return "ok-" + ponderacionTotal;
				}
			} else {
				if (mecanismo != null) { // en caso de no exsitir se crea uno nuevo, se agregan sus valores y se guarda
					mecanismo.setPonderacion(ponderacion);
					mecanismoService.guardar(mecanismo);

					CargaHoraria carga = cargaService.buscarPorIdCarga(mecanismo.getIdCargaHoraria());
					List<Alumno> alumnos = alumnoService
							.buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(carga.getGrupo().getId());
					for (Alumno alumno : alumnos) {
						float calificacionCorte = actualizarCalificacion.actualizaCalificacionCorte(alumno.getId(),
								mecanismo.getIdCargaHoraria(), mecanismo.getIdCorteEvaluativo());
						actualizarCalificacion.actualizaTestimonioCorte(alumno.getId(), calificacionCorte,
								mecanismo.getIdCargaHoraria(), mecanismo.getIdCorteEvaluativo());
						actualizarCalificacion.actualizaCalificacionMateria(alumno.getId(),
								mecanismo.getIdCargaHoraria());
					}

					return "ok-" + ponderacion;
				}
			}

		} // en caso de que el valor sea difirente se manda error

		return "err-0";
	}

	@PostMapping(path = "/copiar-instrumento", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String copiarInstrumento(@RequestBody Map<String, String> obj, HttpSession session) {

		// *************** SE OBTIENEN LAS VARIABLES ***************
		CargaHoraria cActual = cargaService.buscarPorIdCarga(Integer.parseInt(obj.get("cActual")));

		List<MecanismoInstrumento> mecanismos = mecanismoService.buscarPorIdCargaHorariaYActivo(cActual.getId(), true);

		ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
		Integer idKey;
		for (String string : keyList) {
			idKey = 0;
			try {
				idKey = Integer.parseInt(string);
			} catch (Exception e) {
				idKey = 0;
			}
			
			if (idKey != 0) {
				if (mecanismoService.buscarPorIdCargaHorariaYActivo(idKey, true).size()==0) {
					for (MecanismoInstrumento mecanismo : mecanismos) {
						MecanismoInstrumento nuevoMecanismo = new MecanismoInstrumento();
						nuevoMecanismo.setInstrumento(mecanismo.getInstrumento());
						nuevoMecanismo.setPonderacion(mecanismo.getPonderacion());
						nuevoMecanismo.setIdCargaHoraria(idKey);
						nuevoMecanismo.setIdCorteEvaluativo(corteService.buscarPorCargaHorariaYCalendarioEvaluacion(idKey, mecanismo.getIdCorteEvaluativo()));
						nuevoMecanismo.setActivo(true);
						mecanismoService.guardar(nuevoMecanismo);
					}
				}else{
					return "notEmp";
				}
			}
		}			
		
		return "ok";
	}

	@PostMapping(path = "/guardar-instrumento-archivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String guardarInstrumentoArchivo(@RequestParam("archivoInstrumento") MultipartFile multiPart,
			@RequestParam("idInstrumento") Integer idInstrumento, HttpSession session) {
		MecanismoInstrumento mecanismo = new MecanismoInstrumento();
		if (idInstrumento != null) {
			mecanismo = mecanismoService.buscarPorIdYActivo(idInstrumento, true);
		}
		if (comprobarDosificacionValida((Integer) session.getAttribute("cveCarga"), mecanismo.getIdCorteEvaluativo()) == true) {
			return "limit";
		}
		if (!multiPart.isEmpty()) {
			String nombreImagen = SubirArchivo.guardarArchivo(multiPart, rutaDocs + "/profesor/instrumento/");
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				mecanismo.setArchivo(nombreImagen);
				mecanismoService.guardar(mecanismo);
			}
		}
		return "ok";
	}

	@PostMapping(path = "/eliminar-instrumento-archivo", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarInstrumentoArchivo(@RequestBody Map<String, String> obj, HttpSession session) {
		Integer idInstrumento = Integer.parseInt(obj.get("idInstrumento"));
		MecanismoInstrumento mecanismo = new MecanismoInstrumento();
		if (idInstrumento != null) {
			mecanismo = mecanismoService.buscarPorIdYActivo(idInstrumento, true);
			
			
			if (comprobarDosificacionValida((Integer) session.getAttribute("cveCarga"), mecanismo.getIdCorteEvaluativo()) == true) {
				return "limit";
			}
			
			SubirArchivo.borrarArchivo(rutaDocs + "/profesor/instrumento/" + mecanismo.getArchivo());
			mecanismo.setArchivo(null);
			mecanismoService.guardar(mecanismo);
		}
		return "ok";
	}

	// metodos genericos
	public boolean comprobarDosificacionValida(Integer idCarga, Integer idCorte) {
		List<Dosificacion> dosificaciones = dosificacionService.buscarPorIdCargaHoraria(idCarga);
		for (Dosificacion dosificacion : dosificaciones) {
			if (dosificacion.getValidaDirector() == true && dosificacion.getIdCorteEvaluativo()==idCorte) {
				return true;
			}
		}
		return false;
	}
}