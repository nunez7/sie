package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.ProrrogaAdeudo;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IProrrogaAdeudoService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/prorroga-adeudo")
public class ProrrogaAdeudosController {
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private IPagoGeneralService pagoGeneralService;
	
	@Autowired
	private IProrrogaAdeudoService prorrogaAdeudoService;
	
	@Autowired
	private IPersonaService personaService;
	
	@PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarProrroga(@RequestBody Map<String, String> obj, HttpSession session) {
		int cvePersona = Integer.parseInt(obj.get("idPersona"));
		String comentario = obj.get("comentario");
		Date fechaCompromiso = java.sql.Date.valueOf(obj.get("fechaCompromiso"));
		//se construye el alumno
		Alumno alumno = alumnoService.buscarPorPersona(new Persona(cvePersona));
		//se sacan los adeudos del alumno
		List<PagoGeneral> adeudos = pagoGeneralService.buscarPorAlumno(alumno.getId(), 0);
		// se crea el objeto de prorrogaAdeudo
		ProrrogaAdeudo prorroga = new ProrrogaAdeudo();
		prorroga.setPersona(new Persona(cvePersona));
		prorroga.setComentario(comentario);
		//se iteran los adeudos para crear la cadena de conceptos y el importe
		String conceptos = "";
		double importe = 0.0;
		for (PagoGeneral ad : adeudos) {
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
			// se suma el importe
			importe = importe + ad.getMonto();
		}
		prorroga.setConceptos(conceptos);
		prorroga.setImporte(importe);
		prorroga.setFechaCompromiso(fechaCompromiso);
		prorroga.setFechaAlta(new Date());
		//se guarda la prorroga
		prorrogaAdeudoService.guardar(prorroga);
		return "ok";
	}
	
	@GetMapping("/get/{idPersona}")
	public String buscarPorPersona(@PathVariable("idPersona") Integer cvePersona, Model model) {
		// se busca el alumno por persona
		Persona persona = personaService.buscarPorId(cvePersona);
		// se busca el alumno poer persona
		Alumno alumno = alumnoService.buscarPorPersona(persona);
		//buscamos las prorrogas por persona
		List<ProrrogaAdeudo> prorrogas = prorrogaAdeudoService.buscarPorIdPersona(cvePersona);
		//se sacan los adeudos del alumno
		List<PagoGeneral> adeudos = pagoGeneralService.buscarPorAlumno(alumno.getId(), 0);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("adeudos", adeudos);
		model.addAttribute("fechaHoy", dateFormat.format(new Date()));
		model.addAttribute("prorrogas", prorrogas);
		model.addAttribute("hoy", new Date());
		model.addAttribute("nombrePersona", persona.getNombreCompleto());
		model.addAttribute("cvePersona", persona.getId());
		return "caja/plantillaFactura :: modal-prorrogas";
	}

}
