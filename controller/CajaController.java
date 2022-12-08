package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Cliente;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;
import edu.mx.utdelacosta.model.ProrrogaAdeudo;
import edu.mx.utdelacosta.model.dto.NotaCreditoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoAdeudoDTO;
import edu.mx.utdelacosta.model.dtoreport.CajaConcentradoDTO;
import edu.mx.utdelacosta.model.dtoreport.PagosGeneralesDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IClienteService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.ICuatrimestreService;
import edu.mx.utdelacosta.service.IEstadoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.INotaCreditoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPersonalService;
import edu.mx.utdelacosta.service.IProrrogaAdeudoService;
import edu.mx.utdelacosta.util.PageRender;
import edu.mx.utdelacosta.util.ReporteXlsxView;

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
	private ICuatrimestreService cuatrimestreService;
	
	@Autowired
	private IProrrogaAdeudoService prorrogaAdeudoService;
	
	@Autowired
	private INotaCreditoService notaCreditoService;
	
	@Autowired
	private ReporteXlsxView generarReporte;
	
	private String NOMBRE_UT = "UNIVERSIDAD TECNOLÓGICA DE NAYARIT";
	
	//se usa para delimitar la cantidad de registros por pagina en los reportes con paginado
	private Integer REGISTROS_REPORTE = 500;
	
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
	public String pagosCuatrimestre(Model model) {
		List<Periodo> periodos = periodosService.buscarUltimosCaja();
		List<Cuatrimestre> cuatrimestres = cuatrimestreService.buscarTodos();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		List<Integer> anios = new ArrayList<>();
		anios.add(calendar.get(Calendar.YEAR));
		anios.add(calendar.get(Calendar.YEAR)+1);
		model.addAttribute("periodos", periodos);
		model.addAttribute("cuatrimestres", cuatrimestres);
		model.addAttribute("annios", anios);
		return "caja/pagosCuatrimestre";
	}
	
	@GetMapping("/reportes")
	public String reportes() {
		return "caja/reportes";
	}
	
	@GetMapping("/reporteCorteCaja")
	public String reporteCorteCaja(Model model, HttpSession session) {
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
			NotaCreditoDTO nota;
			int cveCajero = (Integer) session.getAttribute("cveCajero");
			model.addAttribute("cveCajero", cveCajero);
			// lista que guardará los pagos
			List<CajaConcentradoDTO> pagos = null;
			if (cveCajero > 0) {
				pagos = pagoGeneralService.findCajaConcentradoByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin,
						cveCajero);
				nota = notaCreditoService.buscarConcentradoPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero);
			} else {
				pagos = pagoGeneralService.findCajaConcentradoByFechaInicioAndFechaFin(fechaInicio, fechaFin);
				nota = notaCreditoService.buscarConcentradoPorFechaInicioYFechaFin(fechaInicio, fechaFin);
			}
			model.addAttribute("nota", nota);
			model.addAttribute("pagos", pagos);
		}

		List<Persona> cajeros = personaService.buscarCajeros();
		model.addAttribute("fechaInicio", fechaInicio);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("fechaHoy", new Date());
		model.addAttribute("cajeros", cajeros);
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
			Page<PagosGeneralesDTO> pagos = null;
			Double notaCredito = null;
			Double total = null;
			Pageable page = PageRequest.of(0, REGISTROS_REPORTE);
			if (cveCajero > 0) {
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYCajeroPaginado(fechaInicio, fechaFin, cveCajero, page);
			} else {
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTodosCajerosPaginado(fechaInicio, fechaFin, page);
			}
			PageRender<PagosGeneralesDTO> pageRender = new PageRender<>(pagos);
			if (1 == pageRender.getTotalPaginas()) {
				if (cveCajero > 0) {
					total = pagoGeneralService.ObtenerTotalMontoPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero);
					notaCredito = notaCreditoService.buscarTotalPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero);					
				}else {					
					total = pagoGeneralService.ObtenerTotalMontoPorFechaInicioYFechaFinTodosCajeros(fechaInicio, fechaFin);
					notaCredito = notaCreditoService.buscarTotalPorFechaInicioYFechaFin(fechaInicio, fechaFin);
				}
			}
			model.addAttribute("total", total);
			model.addAttribute("notaCredito", notaCredito);
			model.addAttribute("pagos", pagos);
			model.addAttribute("totalNota", notaCredito);
			model.addAttribute("page", pageRender);
		}
		List<Persona> cajeros = personaService.buscarCajeros();
		model.addAttribute("fechaHoy", new Date());
		model.addAttribute("fechaInicio", fechaInicio);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("cajeros", cajeros);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteCorteCajaDetallado";
	}
	
	@GetMapping("/reporte-corte-caja-detallado-frag/{page}")
	public String reporteCorteCajaDetalladoFrag(Model model, HttpSession session, @PathVariable("page") Integer pagina) {
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
			Page<PagosGeneralesDTO> pagos = null;
			Double notaCredito = 0d;
			Pageable page = PageRequest.of(pagina, REGISTROS_REPORTE);
			Double total = 0d;
			if (cveCajero > 0) {
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYCajeroPaginado(fechaInicio, fechaFin, cveCajero, page);
				//notaCredito = notaCreditoService.buscarTotalPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero);
			} else {
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTodosCajerosPaginado(fechaInicio, fechaFin, page);
				//notaCredito = notaCreditoService.buscarTotalPorFechaInicioYFechaFin(fechaInicio, fechaFin);
			}
			PageRender<PagosGeneralesDTO> pageRender = new PageRender<>(pagos);
			if (pagina+1 == pageRender.getTotalPaginas()) {
				if (cveCajero > 0) {
					total = pagoGeneralService.ObtenerTotalMontoPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero);
					notaCredito = notaCreditoService.buscarTotalPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero);					
				}else {					
					total = pagoGeneralService.ObtenerTotalMontoPorFechaInicioYFechaFinTodosCajeros(fechaInicio, fechaFin);
					notaCredito = notaCreditoService.buscarTotalPorFechaInicioYFechaFin(fechaInicio, fechaFin);
				}
			}
			model.addAttribute("total", total);
			model.addAttribute("notaCredito", notaCredito);
			model.addAttribute("totalNota", notaCredito);
			model.addAttribute("page", pageRender);
			model.addAttribute("pagos", pagos);
			model.addAttribute("limitePagina", REGISTROS_REPORTE*pagina);
		}
		return "fragments/buscar-caja :: reporte-corte-detallado-paginado";
	}
	
	@GetMapping("/reporte-detalladoexcel/{page}")
	@ResponseBody
	public String reporteCorteDetalladoExcel(HttpSession session, @PathVariable("page") Integer paginas) {		
		// se obtienen las variables
		Page<PagosGeneralesDTO> pagos = null;
		List<PagosGeneralesDTO> pagosList = new ArrayList<>();
		List<Object> datos = new ArrayList<Object>();
		String reporteGenerar = null;
		String[] headers = null;
		Date fechaInicio = session.getAttribute("fechaInicio")!=null ? java.sql.Date.valueOf((String) session.getAttribute("fechaInicio")) : new Date();
		Date fechaFin = session.getAttribute("fechaFin")!=null ? java.sql.Date.valueOf((String) session.getAttribute("fechaFin")) : new Date();
		if (session.getAttribute("cveCajero") != null) {
			int cveCajero = (Integer) session.getAttribute("cveCajero");
				// se itera la consulta paginada segun el numero de paginas que pageRender nos devuelve
				for(int i=0;i<paginas;i+=1) {
				Pageable page = PageRequest.of(i, REGISTROS_REPORTE);
				if (cveCajero > 0) {
					pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYCajeroPaginado(fechaInicio, fechaFin, cveCajero, page);
				} else {
					pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTodosCajerosPaginado(fechaInicio, fechaFin, page);
				}
				pagosList.addAll(pagos.getContent());
				}
				headers = new String[]{ "No", "Folio", "ID", "Nombre", "Concepto", "Descripción", "Estatus", "Tipo pago", "Cajero", "Factura", "Fecha", "Total"};
				
				int n = 0;
				Double total=0d;
				Double totalDesc =0d;
				// se itera el resultado de la busqueda para rellenar la data del reporte
				for (PagosGeneralesDTO p : pagosList) {
					n++;
					if (p.getEstatus()==1) {						
						total = total + p.getMonto();
					}
					totalDesc = p.getEstatus()!=1 ? totalDesc + p.getMonto() : totalDesc;
					Boolean factura = p.getFactura()!=null ? p.getFactura() : false;
					Arrays.asList(datos.add(new Object[] {n+"", p.getFolio(), p.getMatricula(), p.getNombre(), p.getConcepto(), p.getDescripcion(), 
							p.getEstatus() == 1 ? "Pagado" : "Cancelado", p.getTipoPago().toString(), p.getCajero(),factura==true ? "Si" : "No",p.getFecha().toString(), p.getMonto().toString() }));
				}
				Double notaCredito = cveCajero > 0 ? notaCreditoService.buscarTotalPorFechaInicioYFechaFinYCajero(fechaInicio, fechaFin, cveCajero) :
					notaCreditoService.buscarTotalPorFechaInicioYFechaFin(fechaInicio, fechaFin);
				Arrays.asList(datos.add(new Object[] {"","","","","","","","","","","Nota de credito", notaCredito.toString()}));
				total = total - notaCredito;
				Arrays.asList(datos.add(new Object[] {"","","","","","","","","","","Total",  total.toString()}));
				//se condiciona de que carrera se obtuvieron los datos
				String cajero = cveCajero ==0 ? "Todos" : personaService.buscarPorId(cveCajero).getNombreCompleto();
				//se genera el reporte
				reporteGenerar = generarReporte.generarExcelGenerico("Corte de caja detallado", NOMBRE_UT, "REPORTE DE CORTE DE CAJA DETALALDO ", "De: "+fechaInicio+" A: "+fechaFin+" | Cajero: "+cajero, headers, datos);
		}
		return reporteGenerar;
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
		Page<AlumnoAdeudoDTO> adeudos = null;
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			if(session.getAttribute("cvePeriodo") != null) {
				int cvePeriodo = (Integer) session.getAttribute("cvePeriodo");
				Pageable page = PageRequest.of(0, REGISTROS_REPORTE);
				if(cveCarrera == 0) {
					adeudos = alumnoService.reporteTodosAlumnosAdeudosPaginado(cvePeriodo, page);
				} 
				else {
					adeudos = alumnoService.reporteAlumnosAdeudosPaginadoPorCarrera(cvePeriodo, cveCarrera, page);
				}
				PageRender<AlumnoAdeudoDTO> pageRender = new PageRender<>(adeudos);
				model.addAttribute("page", pageRender);
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
	
	@GetMapping("/reporteAdeudos-frag/{page}")
	public String reporteAdeudosFragment(@PathVariable("page") Integer pagina, Model model, HttpSession session) {
		// se crean las variables
		Page<AlumnoAdeudoDTO> adeudos = null;
		int cveCarrera = (Integer) session.getAttribute("cveCarrera");
		int cvePeriodo = (Integer) session.getAttribute("cvePeriodo");
		Pageable page = PageRequest.of(pagina, REGISTROS_REPORTE);
		// se compara si solo se usa una carrera o todas
		if(cveCarrera == 0) {
			adeudos = alumnoService.reporteTodosAlumnosAdeudosPaginado(cvePeriodo, page);
		} 
			else {
			adeudos = alumnoService.reporteAlumnosAdeudosPaginadoPorCarrera(cvePeriodo, cveCarrera, page);
		}
		//se crear la funcion de render para los botones del paginado
		PageRender<AlumnoAdeudoDTO> pageRender = new PageRender<>(adeudos);
		model.addAttribute("page", pageRender);
		model.addAttribute("adeudos", adeudos);
		model.addAttribute("limitePagina", REGISTROS_REPORTE*pagina);
		return "fragments/buscar-caja :: reporte-adeudos-paginado";
	}
	
	@GetMapping("/reporte-adeudosexcel/{page}")
	@ResponseBody
	public String reporteAdeudosExcel(HttpSession session, @PathVariable("page") Integer paginas) {		
		// se crean las varibles
		Page<AlumnoAdeudoDTO> adeudos = null;
		List<AlumnoAdeudoDTO> adeudosList = new ArrayList<>();
		int cvePeriodo = 0;
		List<Object> datos = new ArrayList<Object>();
		String reporteGenerar = null;
		String[] headers = null;
		//se obtiene la carrera
		if(session.getAttribute("cveCarrera") != null) {
			int cveCarrera = (Integer) session.getAttribute("cveCarrera");
			if(session.getAttribute("cvePeriodo") != null) {
				cvePeriodo = (Integer) session.getAttribute("cvePeriodo");
				//se itera desde el 0 hasta el numero de paginas obtenidas del pageRender del metodo reporteAdeudosFragment | reporteAdeudos
				for(int i=0;i<paginas;i+=1) {
				/* se crea la page (limites de busqueda) para delimitar desde que numero se inicia la busqueda 
					y que tiene de limite (declarada al inicio del controller) */
				Pageable page = PageRequest.of(i, REGISTROS_REPORTE);
				if(cveCarrera == 0) {
					adeudos = alumnoService.reporteTodosAlumnosAdeudosPaginado(cvePeriodo, page);
				} 
				else {
					adeudos = alumnoService.reporteAlumnosAdeudosPaginadoPorCarrera(cvePeriodo, cveCarrera, page);
				}
				// tras realizar la consulta, se convierten de Page<T> a List<T> y se agregan a la lista para cerrar ciclo
				adeudosList.addAll(adeudos.getContent());
				}
				//titulos del reporte
				headers = new String[]{
		                "No",
		                "Alumno",
		                "Matrícula",
		                "Concepto",
		                "Descripción",
		                "Costo"
		        };
			}
			int n = 0;
			Float total=0f;
			// se itera el resultado de la busqueda para rellenar la data del reporte
			for (AlumnoAdeudoDTO adeudo : adeudosList) {
				n++;
				total = adeudo.getCantidad()!=null ? total + adeudo.getCantidad() : total;
				Arrays.asList(datos.add(new Object[] {n+"", adeudo.getAlumno(), adeudo.getMatricula(),adeudo.getConcepto(),
						adeudo.getDescripcion(), adeudo.getCantidad().toString()}));
			}
			Arrays.asList(datos.add(new Object[] {"", "","","","Total",  total.toString()}));
			//se condiciona de que carrera se obtuvieron los datos
			String carrera = cveCarrera==0 ? "Todos" : carrerasServices.buscarPorId(cveCarrera).getNombre();
			//se genera el reporte
			reporteGenerar = generarReporte.generarExcelGenerico("Adeudos ", NOMBRE_UT, "REPORTE DE ADEUDOS ", "Cuatrimestre: "+periodosService.buscarPorId(cvePeriodo).getNombre() +" | Carrrera: "+carrera, headers, datos);
		}
		return reporteGenerar;
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
	
	//reporte pagos de banco
	@GetMapping("/reporte-banco-detallado")
	public String reporteBancoDetallado(Model model, HttpSession session) {
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
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTipoPagoYCajero(fechaInicio, fechaFin, 1, cveCajero);
			} else {
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTipoPagoYTodosCajeros(fechaInicio, fechaFin, 1);
			}
			model.addAttribute("pagos", pagos);
		}
		List<Persona> cajeros = personaService.buscarCajeros();
		model.addAttribute("fechaHoy", new Date());
		model.addAttribute("fechaInicio", fechaInicio);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("cajeros", cajeros);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteBancoDetallado";
	}
	
	@GetMapping("/reporte-corresponsalias-detallado")
	public String reporteCorresponsaliasDetallado(Model model, HttpSession session) {
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
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTipoPagoYCajero(fechaInicio, fechaFin, 2, cveCajero);
			} else {
				pagos = pagoGeneralService.buscarPorFechaInicioYFechaFinYTipoPagoYTodosCajeros(fechaInicio, fechaFin, 2);
			}
			model.addAttribute("pagos", pagos);
		}
		List<Persona> cajeros = personaService.buscarCajeros();
		model.addAttribute("fechaHoy", new Date());
		model.addAttribute("fechaInicio", fechaInicio);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("cajeros", cajeros);
		model.addAttribute("NOMBRE_UT", NOMBRE_UT);
		return "caja/reporteCorresponsaliasDetallado";
	}
}
