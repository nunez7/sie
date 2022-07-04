package edu.mx.utdelacosta.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.ProrrogaAutoriza;
import edu.mx.utdelacosta.model.TipoProrroga;
import edu.mx.utdelacosta.model.dto.ProrrogasDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IProrrogaAutoriza;
import edu.mx.utdelacosta.service.IProrrogaService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Rector') and hasRole('Informatica') and hasRole('Director')")
@RequestMapping("/prorroga")
public class ProrrogaController {
	
	@Value("${spring.mail.username}")
	private String correo;
	
	@Autowired
	private IProrrogaService prorrogaService;
	
	@Autowired
	private IProrrogaAutoriza prorrogaAutorizaService;
	
	@Autowired
	private EmailSenderService emailService;
	
	@Autowired
	private IMateriasService materiasService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private ICargaHorariaService cargaService;

	@Autowired
	private ICorteEvaluativoService corteService;

	@PostMapping(path= "/aceptar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String aceptar(@RequestBody Map<String, String> obj) {		
		int idProrroga = Integer.valueOf(obj.get("id"));
		int idPersona = Integer.valueOf(obj.get("idPersona"));
		int idMateria = Integer.valueOf(obj.get("idMateria"));
		Date fechaLimite = Date.valueOf(obj.get("fechaLimite"));
		Materia materia = materiasService.buscarPorId(idMateria);
		Prorroga prorroga = prorrogaService.buscarPorId(idProrroga);
		prorroga.setFechaLimite(fechaLimite);
		prorroga.setAceptada(true);
		//se guarda el registro en prorrogaAutoriza
		ProrrogaAutoriza prorrogaAutoriza = new ProrrogaAutoriza();
		prorrogaAutoriza.setIdAutoriza(idPersona);
		prorrogaAutoriza.setIdProrroga(idProrroga);
		prorrogaAutoriza.setFechaAlta(new java.util.Date());
		//se guardan los datos
		prorrogaService.guardar(prorroga);
		prorrogaAutorizaService.guardar(prorrogaAutoriza); 
		Persona profesor = personaService.buscarPorId(prorroga.getCargaHoraria().getProfesor().getId());
		Mail mail = new Mail();
		String de = correo;
		String para = profesor.getEmail();
		mail.setDe(de);
		mail.setPara(new String[] {para});
		//Email title
		mail.setTitulo("¡Solicitud de Prórroga Aceptada!");
		//Variables a plantilla
		Map<String, Object> variables = new HashMap<>();
		variables.put("titulo", "Solicitud de Prórroga Aceptada");
		variables.put("cuerpoCorreo",
				"Tu solicitud de prórroga de la materia: " + materia.getNombre() + " fue aceptada.");
		mail.setVariables(variables);
		try {
			emailService.sendEmail(mail);
			System.out.println("Enviado");
		} catch (MessagingException | IOException e) {
			System.out.println("Error "+e);
		}
		return "ok";
	}
	
	@PatchMapping(path = "/rechazar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String rechazar(@RequestBody Map<String, String> obj) {
		int idProrroga = Integer.valueOf(obj.get("id")) ;
		int idMateria = Integer.valueOf(obj.get("idMateria"));
		String motivo = obj.get("motivo");
		Materia materia = new Materia(idMateria);
		//se trae la prorroga decuardo a su id
		Prorroga prorroga = prorrogaService.buscarPorId(idProrroga);
		//se setean los valores necesarios para desactivar y rechazar la solicitud
		prorroga.setAceptada(false);
		prorroga.setActivo(false);
		//se guarda la prorroga 
		prorrogaService.guardar(prorroga);
		//envió de correo
		Persona profesor = personaService.buscarPorId(prorroga.getCargaHoraria().getProfesor().getId());
		Mail mail = new Mail();
		String de = correo;
		String para = profesor.getEmail();
		mail.setDe(de);
		mail.setPara(new String[] {para});
		//Email title
		mail.setTitulo("¡Solicitud de Prórroga Rechazada!");
		//Variables a plantilla
		Map<String, Object> variables = new HashMap<>();
		variables.put("titulo", "Solicitud de Prórroga Rechazada");
		variables.put("cuerpoCorreo",
				"Tu solicitud de prórroga de la materia: " + materia.getNombre() +" fue rechaza por el siguiente motivo: " + motivo);
		mail.setVariables(variables);
		try {
			emailService.sendEmail(mail);
			System.out.println("Enviado");
		} catch (MessagingException | IOException e) {
			System.out.println("Error "+e);
		} 
		return "ok";
	}
	
	@PostMapping(path = "/prorrogasave", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String prorrogasSave(@RequestBody ProrrogasDTO prorrogaDto, Model model, HttpSession session) {
		CargaHoraria carga = cargaService.buscarPorIdCarga(prorrogaDto.getIdCargaHoraria());
		CorteEvaluativo corte = corteService.buscarPorId(prorrogaDto.getIdCorteEvaluativo());
		
		Prorroga existente = prorrogaService.buscarPorCargaHorariaYCorteEvaluativoYTipoProrrgaYActivo(carga, corte,
				new TipoProrroga(prorrogaDto.getIdTipoProrroga()), true);
		if (existente == null) {
			
			// se guarda la prorroga.
			Prorroga prorroga = new Prorroga();
			prorroga.setCargaHoraria(carga);
			prorroga.setFechaAlta(new java.util.Date());
			prorroga.setFechaLimite(prorrogaDto.getFechaLimite());
			prorroga.setComentario(prorrogaDto.getComentario());
			prorroga.setTipoProrroga(new TipoProrroga(prorrogaDto.getIdTipoProrroga()));
			prorroga.setActivo(true);
			prorroga.setAceptada(false);
			prorroga.setCorteEvaluativo(corte);
			prorrogaService.guardar(prorroga);
			
			// se crea el correo
			
			Persona director = personaService.buscarDirectorPorCarga(carga.getId());
			Mail mail = new Mail();
			String de = correo;
			String para = director.getEmail();
			mail.setDe(de);
			mail.setPara(new String[] {para});
			//Email title
			mail.setTitulo("Nueva Solicitud de Prórroga");
			//Variables a plantilla
			Map<String, Object> variables = new HashMap<>();
			variables.put("titulo", "Nueva Solicitud de Prórroga");
			variables.put("cuerpoCorreo",
					"El Profesor(a) "+carga.getProfesor().getNombreCompleto()+" ha solicitado una prorroga para la materia "+carga.getMateria().getNombre()+", <br> "
							+ "es necesario acceder al panel de director.");
			mail.setVariables(variables);
			
			try {
				emailService.sendEmail(mail);
			} catch (MessagingException | IOException e) {
				return "mailErr";
			}
			
			return "ok";
		}

		return "dupli";
	}
	
	@PostMapping(path = "/prorrogaEliminar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String prorrogaEliminar(@RequestBody Map<String, String> obj, Model model, HttpSession session) {
		Prorroga prorroga = prorrogaService.buscarPorId(Integer.parseInt(obj.get("id")));
		if (prorroga != null) {
			prorroga.setActivo(false);
			prorrogaService.guardar(prorroga);
			return "ok";
		}
		return "err";
	}
	
}
