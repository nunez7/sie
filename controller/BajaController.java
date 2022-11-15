package edu.mx.utdelacosta.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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
import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.BajaAutoriza;
import edu.mx.utdelacosta.model.BajaEstatus;
import edu.mx.utdelacosta.model.CausaBaja;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.BajaDto;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IBajaAutorizaService;
import edu.mx.utdelacosta.service.IBajaService;
import edu.mx.utdelacosta.service.ICausaBajaService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.util.CodificarTexto;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Academia') and hasRole('Director')")
@RequestMapping("/baja")
public class BajaController {

	@Value("${spring.mail.username}")
	private String correo;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private EmailSenderService emailService;

	@Autowired
	private IBajaService bajaService;

	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private ICausaBajaService causaBajaService;
	
	@Autowired
	private IBajaAutorizaService bajaAutorizaService;

	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
	@PostMapping(path = "/guardar-baja", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarBaja(@RequestBody BajaDto baja, HttpSession session) throws ParseException {
		// Extrae el usuario a partir del usuario cargado en sesión.
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int cvePersona;
		try {
			cvePersona = (Integer) session.getAttribute("cvePersona");
		} catch (Exception e) {
			cvePersona = usuario.getPersona().getId();
		}
		Date fecha = new Date();

		Integer cveGrupo = (Integer) session.getAttribute("cveGrupoTutor");

		if (baja.getIdAlumno() != null) {
			if (baja.getIdCausaBaja() != null) {
				Baja ComprobarBaja = bajaService.buscarPorEstadoAlumnoYFechaAutorizacion(1, baja.getIdAlumno());
				if (ComprobarBaja == null) {
					Baja newbaja = new Baja();
					newbaja.setGrupo(new Grupo(cveGrupo));
					newbaja.setPeriodo(new Periodo(usuario.getPreferencias().getIdPeriodo()));
					newbaja.setPersona(new Persona(cvePersona));
					newbaja.setAlumno(new Alumno(baja.getIdAlumno()));
					newbaja.setTipoBaja(baja.getIdTipoBaja());
					newbaja.setCausaBaja(new CausaBaja(baja.getIdCausaBaja()));
					newbaja.setOtraCausa(baja.getDescripcion());
					newbaja.setFechaAsistencia(baja.getUltimaFechaAsistio());
					newbaja.setFechaSolicitud(baja.getFechaSolicitud());
					newbaja.setFechaAutorizacion(null);
					newbaja.setDescripcion(baja.getMotivo());
					newbaja.setBajaEstatus(new BajaEstatus(2));// estatus de solicitada
					newbaja.setFechaRegistro(fecha);
					bajaService.guardar(newbaja);

					Alumno alumno = alumnoService.buscarPorId(baja.getIdAlumno());
					Persona persona = personaService.buscarPorId(cvePersona);
					// correo
					Mail mail = new Mail();
					String de = correo;
					String para = alumno.getCarreraInicio().getEmailCarrera();
					mail.setDe(de);
					mail.setPara(new String[] { para });
					// Email title
					mail.setTitulo("Nueva solicitud de baja.");
					// Variables a plantilla
					Map<String, Object> variables = new HashMap<>();
					variables.put("titulo",
							"Solicitud de baja del alumno(a) " + alumno.getPersona().getNombreCompleto());
					variables.put("cuerpoCorreo", "El Tutor " + persona.getNombreCompleto()
							+ " realizó una solicitud de baja para el alumno con matrícula " + alumno.getMatricula()
							+ ", diríjase al apartado de bajas en el panel del director para ver más detalles al respecto.");
					mail.setVariables(variables);
					try {
						emailService.sendEmail(mail);
					} catch (Exception e) {
						return "errorMen";
					}

					return "ok";
				}
				return "bajaActiva";
			}
			return "NoCausaBaja";
		}
		return "NoAl";
	}

	// para editar el registro de la baja
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Profesor')")
	@PostMapping(path = "/editar-baja", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editarBaja(@RequestBody BajaDto baja, HttpSession session) throws ParseException {
		// se busca el objeto de la baja y se rellenan los datos
		Baja b = bajaService.buscarPorId(baja.getIdBaja());
		b.setTipoBaja(baja.getIdTipoBaja());
		b.setCausaBaja(new CausaBaja(baja.getIdCausaBaja()));
		b.setOtraCausa(baja.getDescripcion());
		b.setFechaAsistencia(baja.getUltimaFechaAsistio());
		b.setFechaSolicitud(baja.getFechaSolicitud());
		b.setDescripcion(baja.getMotivo());
		bajaService.guardar(b);
		return "ok";
	}

	@GetMapping("/cargar-baja/{idBaja}")
	public String cargaBaja(@PathVariable(name = "idBaja", required = false) String idBaja, Model model) {
		if (idBaja != null) {
			Integer cveBaja = Integer.parseInt(idBaja);
			BajaAutoriza bajaAutoriza = bajaAutorizaService.buscarPorBaja(new Baja(cveBaja));
			// Codifica el ususario
			String firmaTutor = null;
			String firmaDirector = null;
			String firmaAlumno = null;
			try {
				String firma1 = bajaAutoriza.getBaja().getPersona().getUsuarios().get(0).getUsuario();
				firmaTutor = CodificarTexto.encriptAES(firma1);

				String firma2 = bajaAutoriza.getPersona().getUsuarios().get(0).getUsuario();
				firmaDirector = CodificarTexto.encriptAES(firma2);

				String firma3 = bajaAutoriza.getBaja().getAlumno().getPersona().getUsuarios().get(0).getUsuario();
				firmaAlumno = CodificarTexto.encriptAES(firma3);

			} catch (Exception e) {
				e.printStackTrace();
			}

			model.addAttribute("baja", bajaAutoriza.getBaja());
			model.addAttribute("autoriza", bajaAutoriza.getPersona().getNombreCompletoConNivelEstudio());
			model.addAttribute("grupo",
					bajaAutoriza.getBaja().getGrupo() != null ? bajaAutoriza.getBaja().getGrupo() : null);
			model.addAttribute("firmaTutor", firmaTutor);
			model.addAttribute("firmaDirector", firmaDirector);
			model.addAttribute("firmaAlumno", firmaAlumno);
		}
		return "tutorias/solicitudBaja";
	}

	// para editar la baja
	@GetMapping("/baja-get/{id}")
	public String getBajaAlumno(@PathVariable(name = "id", required = true) Integer idBaja, Model model) {
		// busca el objeto de la baja
		Baja baja = bajaService.buscarPorId(idBaja);
		// lista causas de baja
		List<CausaBaja> causas = causaBajaService.buscarActivas();
		model.addAttribute("causas", causas);
		model.addAttribute("baja", baja);
		return "tutorias/editarBajaAlumno";
	}
}
