package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Cliente;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dtoreport.AlumnoAdeudoDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IClienteService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.IEstadoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/caja")
public class CajaController {
	
	@Autowired
	private IConceptoService conceptoService;
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IEstadoService estadoService;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private IPersonaService personaService;
		
	@Autowired
	private IPeriodosService periodosService;
	
	@Autowired
	private ICarrerasServices carrerasServices;
	
	@Autowired
	private IPagoGeneralService pagoGeneralService;
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";
	
	@GetMapping("/archivosBanco")
	public String archivosBanco() {
		return "caja/archivosBanco";
	}
	
	@GetMapping("/pagosPersona")
	public String pagosPersona() {
		return "caja/pagosPersona";
	}
	
	@GetMapping("/pagosEmpresa")
	public String pagosEmpresa() {
		return "caja/pagosEmpresa";
	}
	
	@GetMapping("/folios")
	public String folios() {
		return "caja/folios";
	}
	
	@GetMapping("/clientes")
	public String clientes(Model model) {
		List<Cliente> clientes = clienteService.buscarTodos();
		List<Estado> estados = estadoService.buscarTodos();
		model.addAttribute("estados", estados);
		model.addAttribute("clientes", clientes);
		return "caja/clientes";
	}
	
	@GetMapping("/conceptos")
	public String conceptos(Model model) {
		List<Concepto> conceptos = conceptoService.buscarTodos();
		model.addAttribute("conceptos", conceptos);
		return "caja/conceptos";
	}
	
	@GetMapping("/descuentos")
	public String descuentos() {
		return "caja/descuentos";
	}
	
	@GetMapping("/pagosCuatrimestre")
	public String pagosCuatrimestre() {
		return "caja/pagosCuatrimestre";
	}
	
	@GetMapping("/reportes")
	public String reportes() {
		return "caja/reportes";
	}
	
	@GetMapping("/reporteCorteCaja")
	public String reporteCorteCaja(Model model) {
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteCorteCaja";
	}
	
	@GetMapping("/reporteCorteCajaDetallado")
	public String reporteCorteCajaDetallado(Model model, HttpSession session) {
		//cuando selecciona la fecha del inicio
		if(session.getAttribute("fechaInicio") != null) {
			Date fechaInicio = java.sql.Date.valueOf((String) session.getAttribute("fechaInicio"));
			model.addAttribute("fechaInicio", fechaInicio);
			//cuando selecciona la fecha de fin 
			if(session.getAttribute("fechaFin") != null) {
				Date fechaFin = java.sql.Date.valueOf((String) session.getAttribute("fechaFin"));
				model.addAttribute("fechaFin", fechaFin);
				if(session.getAttribute("cveCajero") != null) {
					int cveCajero = (Integer)session.getAttribute("cveCajero");
					model.addAttribute("cveCajero", cveCajero);
					//lista que guardará los pagos
					List<PagoGeneral> pagos = null;
					if(cveCajero > 0) {
						pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero);
					}
					else {
						pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTodosCajeros(fechaInicio, fechaFin);
					}
					model.addAttribute("pagos", pagos);
				}
			}
		}
		List<Persona> cajeros = personaService.buscarCajeros();
		model.addAttribute("fechaHoy", new Date());
		model.addAttribute("cajeros", cajeros);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteCorteCajaDetallado";
	}
	
	@GetMapping("/reportePoliza")
	public String reportePoliza(Model model) {
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reportePoliza";
	}
	
	@GetMapping("/reporteAdeudos")
	public String reporteAdeudos(Model model, HttpSession session) {
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		List<Periodo> periodos = periodosService.buscarTodos();
		List<Carrera> carreras = carrerasServices.buscarTodasMenosIngles();
		List<AlumnoAdeudoDTO> adeudos = new ArrayList<>();
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			if(session.getAttribute("cvePeriodo") != null) {
				int cvePeriodo = (Integer) session.getAttribute("cvePeriodo");
				adeudos = alumnoService.obtenerAlumnosAdeudoPorPersonaCarreraYPeriodo(persona.getId(), cvePeriodo);
				model.addAttribute("cveCarrera", cveCarrera);
				model.addAttribute("cvePeriodo", cvePeriodo);
				
			}
		}
		model.addAttribute("adeudos", adeudos);
		model.addAttribute("carreras", carreras);
		model.addAttribute("periodos", periodos);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteAdeudos";
	}
	
	@GetMapping("/manual")
	public String manual() {
		return "caja/manual";
	}
	
	@GetMapping("/cobro/{id}")
	public String cobro(@PathVariable("id") int id) {
		//El id que se recibe es el id de persona
		return "caja/plantillaFactura";
	}
	
	@GetMapping("/cobroempresa/{id}")
	public String cobroempresa(@PathVariable("id") int id) {
		//El id que se recibe es el id de persona
		return "caja/plantillaFacturaEmpresa";
	}
}
