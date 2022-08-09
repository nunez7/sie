package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Cliente;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;
import edu.mx.utdelacosta.model.ProrrogaAdeudo;
import edu.mx.utdelacosta.model.dtoreport.AlumnoAdeudoDTO;
import edu.mx.utdelacosta.model.dtoreport.PagosGeneralesDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IClienteService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.IEstadoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPersonalService;
import edu.mx.utdelacosta.service.IProrrogaAdeudoService;

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
	
	@Autowired
	private IPersonalService personalService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private IProrrogaAdeudoService prorrogaAdeudoService;
	
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
		Date fechaInicio;
		Date fechaFin;
		if (session.getAttribute("fechaInicio") == null) {
			fechaInicio = new Date();
		} else {
			fechaInicio = java.sql.Date.valueOf((String) session.getAttribute("fechaInicio"));
		}

		// cuando selecciona la fecha de fin
		if (session.getAttribute("fechaFin") == null) {
			fechaFin = new Date();
		} else {
			fechaFin = java.sql.Date.valueOf((String) session.getAttribute("fechaFin"));
		}
		
		if (session.getAttribute("cveCajero") != null) {
			int cveCajero = (Integer) session.getAttribute("cveCajero");
			model.addAttribute("cveCajero", cveCajero);
			// lista que guardará los pagos
			List<PagosGeneralesDTO> pagos = null;
			if (cveCajero > 0) {
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero);
			} else {
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTodosCajeros(fechaInicio, fechaFin);
			}
			model.addAttribute("pagos", pagos);
		}
		List<Persona> cajeros = personaService.buscarCajeros();
		model.addAttribute("fechaHoy", new Date());
		model.addAttribute("fechaInicio", fechaInicio);
		model.addAttribute("fechaFin", fechaFin);
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
		List<Periodo> periodos = periodosService.buscarTodos();
		List<Carrera> carreras = carrerasServices.buscarTodasMenosIngles();
		List<AlumnoAdeudoDTO> adeudos = new ArrayList<>();
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			if(session.getAttribute("cvePeriodo") != null) {
				int cvePeriodo = (Integer) session.getAttribute("cvePeriodo");
				//para todas las carrera
				if(cveCarrera == 0) {
					adeudos = alumnoService.obtenerAlumnosAdeudoPorTodasCarreraYPeriodo(cvePeriodo);
				} 
				else {
					adeudos = alumnoService.obtenerAlumnosAdeudoPorCarreraYPeriodo(cveCarrera, cvePeriodo);
				}
				model.addAttribute("cvePeriodo", cvePeriodo);
			}
			model.addAttribute("cveCarrera", cveCarrera);
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
	
	@GetMapping("/cobro/{id}/{tipo}")
	public String cobro(@PathVariable("id") int persona, @PathVariable("tipo") int tipo, Model model) {
		//El id que se recibe es el id de persona
		List<PagoGeneral> adeudos = null;
		List<PagoGeneral> pagos = null;
		List<ProrrogaAdeudo> prorrogas = null;
		//variables para enviar a la vista
		int cveAlumno = 0;
		int cvePersonal = 0;
		List<Grupo> grupos = null;
		Grupo grupo =null;
		if(tipo > 0) {
			//se construye alumno
			Alumno alumno = alumnoService.buscarPorPersona(new Persona(persona));
			grupos = grupoService.buscarUltimosPorAlumno(alumno.getId()); //para calificaciones
			grupo = grupoService.buscarUltimoDeAlumno(alumno.getId()); //para la carrera
			cveAlumno = alumno.getId();
			//para sacar los aduedos del alumno
			adeudos = pagoGeneralService.buscarPorAlumno(alumno.getId(), 0);
			//para sacar los pagos
			pagos = pagoGeneralService.buscarPorAlumno(alumno.getId(), 1);
			//para sacar las prórrogas de adeudos
			prorrogas = prorrogaAdeudoService.buscarPorIdPersona(persona);
			model.addAttribute("grupos", grupos);//para calificaciones
			model.addAttribute("grupo", grupo);
			model.addAttribute("cliente", alumno);
		}
		else {
			//se contruye personal
			Personal personal = personalService.buscarPorPersona(new Persona(persona));
			cvePersonal = personal.getPersona().getId();
			//para sacar los aduedos del alumno
			adeudos = pagoGeneralService.buscarPorPersona(cvePersonal, 0);
			//para sacar los pagos
			pagos = pagoGeneralService.buscarPorPersona(cvePersonal, 1);
			model.addAttribute("cliente", personal);
		}
		//fecha y hora actual
		DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("conceptos", conceptoService.buscarOpcionales());
		model.addAttribute("fechaHoy", dateFormat.format(new Date()));
		model.addAttribute("hora", hourFormat.format(new Date()));
		model.addAttribute("pagos", pagos);
		model.addAttribute("adeudos", adeudos);
		model.addAttribute("cveAlumno", cveAlumno); //se regresa como variable a la vista
		model.addAttribute("cvePersonal", cvePersonal); //se regresa como variable a la vista
		model.addAttribute("cvePersona", persona); //se regresa como variable a la vista
		model.addAttribute("tipo", tipo); //se regresa como variable a la vista
		model.addAttribute("prorrogas", prorrogas); // regresa la lista de prórrogas
		model.addAttribute("persona", personaService.buscarPorId(persona));
		model.addAttribute("hoy", new Date()); //para comrporbar si la fecha esta activa
		return "caja/plantillaFactura";
	}
	
	@GetMapping("/cobroempresa/{id}")
	public String cobroempresa(@PathVariable("id") int id, Model model) {
		//El id que se recibe es el id del cliente
		Cliente cliente = clienteService.buscarPorId(id);
		//se buscan los pagos del cliente
		List<PagoGeneral> pagos = pagoGeneralService.buscarPorCliente(cliente.getId(), 1);
		DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("conceptos", conceptoService.buscarOpcionales());
		model.addAttribute("pagos", pagos);
		model.addAttribute("fechaHoy", dateFormat.format(new Date()));
		model.addAttribute("hora", hourFormat.format(new Date()));
		model.addAttribute("cliente", cliente);
		return "caja/plantillaFacturaEmpresa";
	}
}
