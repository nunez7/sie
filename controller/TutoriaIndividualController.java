package edu.mx.utdelacosta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Canalizacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.Motivo;
import edu.mx.utdelacosta.model.MotivoTutoria;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.ProgramacionTutoria;
import edu.mx.utdelacosta.model.Servicio;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.Select2DTO;
import edu.mx.utdelacosta.model.dto.TutoriaIndividualDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICanalizacionService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IMotivoService;
import edu.mx.utdelacosta.service.IMotivoTutoriaService;
import edu.mx.utdelacosta.service.IProgramacionTutoriaService;
import edu.mx.utdelacosta.service.IServicioService;
import edu.mx.utdelacosta.service.ITutoriaIndividualService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Academia') and hasRole('Director')")
@RequestMapping("/tutoria-individual")
public class TutoriaIndividualController {

	@Autowired
	private IMotivoService motivoService;

	@Autowired
	private ITutoriaIndividualService tutoriaIndiService;

	@Value("${spring.mail.username}")
	private String correo;

	@Autowired
	private IGrupoService grupoService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IMotivoTutoriaService motivoTutoService;

	@Autowired
	private IProgramacionTutoriaService proTutoriaService;

	@Autowired
	private ICargaHorariaService cargaHorariaService;

	@Autowired
	private EmailSenderService emailService;

	@Autowired
	private ICanalizacionService canalizacionService;

	@Autowired
	private IServicioService servicioService;

	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarIndividual(@RequestBody Map<String, String> obj, HttpSession session, Model model)
			throws ParseException {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		if (obj.get("idAlumno") != null) {
			SimpleDateFormat dFHora = new SimpleDateFormat("hh:mm");
			SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");

			Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
			String cveAlumno = obj.get("idAlumno");
			Date fecha = new Date();
			Date fechaTutoria = dFDia.parse(obj.get("fechaInicioTutoria"));
			Date horaInicio = dFHora.parse(obj.get("horaInicio"));
			Date horaFin = dFHora.parse(obj.get("horaFin"));

			// Recibo los motivos de la tutoría como un string y los convierto a una
			// ArrayList<Integer>
			String mo = obj.get("motivos");
			String s = ",";
			String[] mot = mo.split(s);
			List<String> motiv = Arrays.asList(mot);
			ArrayList<Integer> motivos = new ArrayList<Integer>();// lista de motivos seleccionados
			for (int i = 0; i < motiv.size(); i++) {
				motivos.add(Integer.parseInt(motiv.get(i)));
			}

			String puntosRelevantes = obj.get("puntosRelevantes");
			String compromisosAcuerdos = obj.get("compromisosAcuerdos");
			String usuarioMatricula = obj.get("usuarioMatricula");
			String userNip = obj.get("userNip");

			Integer tipo = Integer.parseInt(obj.get("tipo-tutoria"));
			// Se valida el nivel de atención recibido desde la vista
			String nivelAtencion = obj.get("nivelAtencion");
			if (cveAlumno != null) {
				Usuario usuarioAl = usuarioService.buscarPorUsuario(usuarioMatricula);
				Boolean valContra = passwordEncoder.matches(userNip, usuarioAl.getContrasenia());

				TutoriaIndividual tutInd = new TutoriaIndividual();
				tutInd.setAlumno(new Alumno(Integer.parseInt(cveAlumno)));
				tutInd.setTutor(new Persona(cvePersona));
				tutInd.setGrupo(new Grupo(cveGrupo));
				tutInd.setFechaTutoria(fechaTutoria);
				tutInd.setFechaRegistro(fecha);
				tutInd.setHoraInicio(horaInicio);
				tutInd.setHoraFin(horaFin);
				tutInd.setPuntosImportantes(puntosRelevantes);
				tutInd.setCompromisosAcuerdos(compromisosAcuerdos);
				tutInd.setNivelAtencion(nivelAtencion);
				if (valContra == true) {
					tutInd.setValidada(true);
				} else {
					tutInd.setValidada(false);
				}
				tutoriaIndiService.guardar(tutInd);

				for (int i = 0; i < motivos.size(); i++) {
					MotivoTutoria motivoTutoria = new MotivoTutoria();
					motivoTutoria.setTutoriaIndividual(tutInd);
					motivoTutoria.setMotivo(new Motivo(motivos.get(i)));
					motivoTutoService.guardar(motivoTutoria);
				}
				// Se valida si la tutoría se canalizara
				if (tipo == 1) {
					TutoriaIndividual tutoria = tutoriaIndiService.ultimoRegistro();
					return String.valueOf(tutoria.getId());
				} else {
					return "ok";
				}
			} else {
				return "NoUser";
			}
		}
		return "NoAl";
	}
	
	@PatchMapping(path = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String update(@RequestBody TutoriaIndividualDTO tutoria) {
		//Buscamos la tutoria y la actualizamos
		TutoriaIndividual miTutoria = tutoriaIndiService.buscarPorId(tutoria.getId());
		//DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		if(miTutoria!=null) {
			miTutoria.setFechaTutoria(tutoria.getFechaTutoria());
			miTutoria.setCompromisosAcuerdos(tutoria.getCompromisosAcuerdos());
			miTutoria.setHoraInicio(tutoria.getHoraInicio());
			miTutoria.setHoraFin(tutoria.getHoraFin());
			miTutoria.setPuntosImportantes(tutoria.getPuntosImportantes());
			miTutoria.setNivelAtencion(tutoria.getNivelAtencion());
			String array [] = tutoria.getMotivos().split(",");
			//Eliminamos los motivos
			List<MotivoTutoria> motivos = motivoTutoService.findByIdTutoria(tutoria.getId());
			for(MotivoTutoria motivo: motivos) {
				motivoTutoService.eliminar(motivo.getId());
			}
			//Agregamos los nuevos motivos
			for(String d : array) {
				try {
					MotivoTutoria motivoTutoria = new MotivoTutoria();
					motivoTutoria.setTutoriaIndividual(miTutoria);
					motivoTutoria.setMotivo(new Motivo(Integer.parseInt(d)));
					motivoTutoService.guardar(motivoTutoria);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			tutoriaIndiService.guardar(miTutoria);
		}
		return "ok";
	}

	@PostMapping(path = "/canalizar-alumno", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String canalizarAlumno(@RequestBody Map<String, String> obj, HttpSession session) throws ParseException {
		SimpleDateFormat dFHora = new SimpleDateFormat("hh:mm");
		SimpleDateFormat dFDia = new SimpleDateFormat("dd/MM/yyyy");
		String cveTutoria = obj.get("idTutoria");
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		String cveAlumno = obj.get("idAlumno");
		Date fechaRegistro = new Date();
		Date fecha = dFDia.parse(obj.get("fechaCanalizacion"));
		Date hora = dFHora.parse(obj.get("horaCanalizacion"));
		Integer servicio = Integer.parseInt(obj.get("servicio"));
		// Solo se utiliza para correo como complemento del servicio solicitado
		String resumen = obj.get("resumen");
		// Integer materia = Integer.parseInt(obj.get("materia"));
		//
		String razon = obj.get("razon");
		String comentario = obj.get("comentario");

		if (cveGrupo != null) {
			Grupo grupo = grupoService.buscarPorId(cveGrupo);
			if (cveAlumno != null) {
				if (cveTutoria != null) {
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

					if (servicio == 3) {

						// Recibo los motivos de la tutoría como un string y los convierto a una
						// ArrayList<Integer>
						String ma = obj.get("materias");
						String s = ",";
						String[] mat = ma.split(s);
						List<String> mater = Arrays.asList(mat);
						ArrayList<Integer> materias = new ArrayList<Integer>();// lista de motivos seleccionados
						for (int i = 0; i < mater.size(); i++) {
							materias.add(Integer.parseInt(mater.get(i)));
						}

						// se recorreo las materias seleccinadas para generar un correo para cada una de
						// ellas
						for (Integer cveMateria : materias) {
							mail = new Mail();
							CargaHoraria cargaHoraria = cargaHorariaService.buscarPorIdCarga(cveMateria);
							String para = cargaHoraria.getProfesor().getEmail();
							mail.setDe(de);
							mail.setPara(new String[] { para });
							// Email title
							mail.setTitulo("Canalización de alumnos");
							// Variables a plantilla
							Map<String, Object> variables = new HashMap<>();
							variables.put("titulo",
									"Canalización del alumno(a) " + alumno.getPersona().getNombreCompleto());

							variables.put("cuerpoCorreo",
									"El tutor(a) " + grupo.getProfesor().getNombreCompletoConNivelEstudio()
											+ ", solicita una canalización para el alumno "
											+ alumno.getPersona().getNombreCompleto() + ", para la materia de "
											+ cargaHoraria.getMateria().getNombre() + " que imparte"
											+ ", por esta razón: " + razon + ". " + comentario
											+ ". Espero su confirmación" + grupo.getProfesor().getEmail() + ".");

							mail.setVariables(variables);
							try {
								emailService.sendEmail(mail);
							} catch (Exception e) {
								return "errorCorre";
							}
						}
						return "ok";
					} else {

						String para = servicioService.buscarPorId(servicio).getCorreo();
						if (servicio == 4) {
							para = grupo.getCarrera().getEmailCarrera();
						}
						mail.setDe(de);
						String[] correos = para.split(",");
						mail.setPara(correos);
						// Email title
						mail.setTitulo("Canalización de alumnos");
						// Variables a plantilla
						Map<String, Object> variables = new HashMap<>();
						variables.put("titulo",
								"Canalización del alumno(a) " + alumno.getPersona().getNombreCompleto());

						variables.put("cuerpoCorreo",
								"El tutor(a) " + grupo.getProfesor().getNombreCompletoConNivelEstudio()
										+ ", solicita una canalización para el alumno "
										+ alumno.getPersona().getNombreCompleto() + ", debido a que " + resumen
										+ ", por esta razón: " + razon + ". " + comentario + ". Espero su confirmación "
										+ grupo.getProfesor().getEmail() + ".");

						mail.setVariables(variables);
						try {
							emailService.sendEmail(mail);
							return "ok";
						} catch (Exception e) {
							return "errorCorre";
						}
					}
				}
				return "noTuto";
			}
			return "noAl";
		}
		return "noGru";
	}
	

	@GetMapping("/cargar/{idAlumno}")
	public String cargaTutorias(@PathVariable(name = "idAlumno", required = false) String idAlumno, Model model) {
		if (idAlumno != null) {
			Integer cveAlumno = Integer.parseInt(idAlumno);
			List<TutoriaIndividual> tutorias = tutoriaIndiService.buscarPorAlumno(new Alumno(cveAlumno));
			model.addAttribute("tutorias", tutorias);
			model.addAttribute("alumno", tutorias.get(0).getAlumno().getPersona().getNombreCompletoPorApellido());
			model.addAttribute("matricula", tutorias.get(0).getAlumno().getMatricula());
		}
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "tutorias/reporteTutoriasAlumno";
	}
	
	@PostMapping(path="/validar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String validarContrasena(@RequestBody Map<String, String> obj) {
		String contra = obj.get("contrasenia");
		String matricula = obj.get("matricula");
		int idTutoria = Integer.parseInt(obj.get("id"));
		if(matricula!=null && !matricula.isEmpty() && contra!=null) {
			Usuario usuario = usuarioService.buscarPorUsuario(matricula);
			Boolean valContra =  passwordEncoder.matches(contra, usuario.getContrasenia());
			if(valContra == true) {
				TutoriaIndividual tutoria = tutoriaIndiService.buscarPorId(idTutoria);
				tutoria.setValidada(true);
				tutoriaIndiService.guardar(tutoria);
				return "ok";
			}	
		}
		return "Error";
	}

	@GetMapping("/get/{id}")
	public String getTutoriaIndividualAlumno(@PathVariable(name = "id", required = true) Integer id, Model model) {
		// busca el objeto de la baja
		TutoriaIndividual tutoria = tutoriaIndiService.buscarPorId(id);
		// lista causas de baja
		List<Motivo> allMotivos = motivoService.buscarTodo();
		List<Select2DTO> motivos = new ArrayList<Select2DTO>();
		boolean selector = false;
		for (int i = 0; i < allMotivos.size(); i++) {
			Motivo motivo = allMotivos.get(i);
			for (Motivo motivoT : tutoria.getMotivos()) {
				if (motivoT.getId() == motivo.getId()) {
					selector = true;
				}
			}
			motivos.add(new Select2DTO(motivo.getId(), motivo.getMotivo(), selector));
			selector = false;
		}
		model.addAttribute("motivos", motivos);
		model.addAttribute("tutoria", tutoria);
		return "tutorias/formEditarTutoriaIndividual";
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseBody
	public String eliminar(@PathVariable Integer id) {
		try {
			List<MotivoTutoria> motivos = motivoTutoService.findByIdTutoria(id);
			Canalizacion canalizacion = canalizacionService.buscarPorTutoria(new TutoriaIndividual(id));
				if(canalizacion==null) {
					for(MotivoTutoria motivo: motivos) {
						motivoTutoService.eliminar(motivo.getId());
					}
					tutoriaIndiService.eliminar(id);
					return "delete";
				}else {
					return "canalizacion";
				}
		} catch (DataAccessException e) {
			return "error"+e.getCause();
		}
	}

	@PostMapping(path = "/actualizar-tutoria-programada", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String actualizarFechaTema(@RequestBody Map<String, String> obj, HttpSession session) throws ParseException {
		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");
		Integer cveAlumno = Integer.parseInt(obj.get("idAlumno"));
		SimpleDateFormat dFDia = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaProgramada = dFDia.parse(obj.get("fecha"));
		Date fechaHoy = new Date();

		if (cveGrupo != null) {
			List<ProgramacionTutoria> tutoria = proTutoriaService.buscarPorAlumnoYGrupo(new Alumno(cveAlumno),
					new Grupo(cveGrupo));
			if (tutoria.isEmpty()) {
				ProgramacionTutoria pT = new ProgramacionTutoria();
				pT.setGrupo(new Grupo(cveGrupo));
				pT.setAlumno(new Alumno(cveAlumno));
				pT.setFechaAlta(fechaHoy);
				pT.setFecha(fechaProgramada);
				proTutoriaService.guardar(pT);
				return "ok";
			} else {
				tutoria.get(0).setFecha(fechaProgramada);
				proTutoriaService.guardar(tutoria.get(0));
				return "ok";
			}
		}
		return "error";
	}

}
