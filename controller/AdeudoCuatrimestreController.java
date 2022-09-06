package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
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

import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoCuatrimestre;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.dto.PagoCuatrimestreDTO;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.ICuatrimestreService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.util.Reinscribir;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/adeudo-cuatrimestre")
public class AdeudoCuatrimestreController {
	
	@Autowired
	private IPagoGeneralService pagoGeneralService;

	@Autowired
	private IConceptoService conceptoService;
	
	@Autowired
	private ICuatrimestreService cuatrimestreService;
	
	@Autowired
	private IAlumnoGrupoService alGrService;
	
	@Autowired
	private IPeriodosService periodoService;
	
	@Autowired
	private Reinscribir reinscripcion;
	
	@PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardar(@RequestBody Map<String, String> obj, HttpSession session) {
		
		// varaibles de la vista
		Integer idCuatrimestre = Integer.valueOf(obj.get("cuatrimestre"));
		Integer idPeriodo = Integer.valueOf(obj.get("periodo"));
		
		if (idCuatrimestre==null || idPeriodo == null || idCuatrimestre == 0 || idPeriodo == 0) {
			//en caso de que falte algun alumno se regresa a la vista
			return "null";
		}
		
		if (idCuatrimestre==1) {
			// en caso de que el cuatrimestre sea el primero, se regresa pues estos se generan al pagar o e
			return "inv";
		}
		
		Periodo periodo = periodoService.buscarPorId(idPeriodo);
		
		Cuatrimestre cuatrimestre = cuatrimestreService.buscarPorId(idCuatrimestre);
		
		
		// se obtiene la fecha en base a los 3 selectores de datos
		Date fecha = java.sql.Date.valueOf(obj.get("annioLimite")+"-"+obj.get("mesLimite")+"-"+obj.get("diaLimite"));
		
		List<AlumnoGrupo> alumnos = new ArrayList<>();
		//LISTA DE ALUMNOS DEL CUATRIMESTRE
		if (cuatrimestre.getId()!=1) {
			//en caso de que el cuatrimestre no se 1 , se buscan los alumnos del periodo anterior
		alumnos = alGrService.buscarPorPeriodoYCuatrimestre(periodo.getId()-1, cuatrimestre.getId()-1);
		}
		
		//se obtinen los conceptos dependiendo del cuatrimestre en el que se esta
		List<Integer> conceptos = reinscripcion.obtenerConceptosReinscripcion(cuatrimestre.getConsecutivo());
		
		
		// si el cuatrimestre no es 1 se proceden a inscribir los alumnos en sus respectivos grupos
		if (cuatrimestre.getId()!=1) {
			for (AlumnoGrupo ag : alumnos) {
				reinscripcion.reinscribir(ag, cuatrimestre.getId(), periodo.getId());
			}
		}
		
		//se itera la lista de alumnos
		for (AlumnoGrupo ag : alGrService.buscarPorPeriodoYCuatrimestre(periodo.getId(), cuatrimestre.getId())) {
			
			//se itera la lista de conceptos
			for (Integer idConcepto : conceptos) {
				
				//se busca el concepto
				Concepto concepto = conceptoService.buscarPorId(idConcepto);
				
				//se crea el pago general
				PagoGeneral pago = crearPagoGenerico(concepto.getConcepto(), concepto.getId(), fecha, periodo);
				
				//se crea el pago alumno
				PagoAlumno pAlumno = new PagoAlumno();
				pAlumno.setAlumno(ag.getAlumno());
				pAlumno.setPagoGeneral(pago);
				pago.setPagoAlumno(pAlumno);
				
				
				//se crea el pago del cuatrimestre
				PagoCuatrimestre pCuatri = new PagoCuatrimestre();
				pCuatri.setAlumnoGrupo(ag);
				pCuatri.setPagoGeneral(pago);
				pago.setPagoCuatrimestre(pCuatri);
				
				
				//se comprueba si el pago pertenece a una colegiatura y si el alumno es de gastronomia
				if ((pago.getConcepto().getId() == 10 || pago.getConcepto().getId() == 8) && ag.getAlumno().getCarreraInicio().getId()==9) {
					// en caso de ser correcto se procede a ajustar el costo del mismo	
					pago.setMonto(pago.getConcepto().getId() == 8 ? 2000d : 1550d);
				}
				
				pagoGeneralService.guardar(pago);
			}
		}
		
		return "ok";
	}
	
	public PagoGeneral crearPagoGenerico(String comentario, Integer idConcepto, Date fechaLimite , Periodo periodo) {
		PagoGeneral pagoGenerico = new PagoGeneral();
		
		Concepto concepto = conceptoService.buscarPorId(idConcepto);
		
		//se crea el pago general
        pagoGenerico.setCantidad(1);
        pagoGenerico.setCliente(0);
        pagoGenerico.setActivo(true);
        pagoGenerico.setComentario(comentario);
		pagoGenerico.setDescripcion(idConcepto==8 || idConcepto == 10 ? concepto.getConcepto().toUpperCase()+" "+periodo.getNombre() : concepto.getConcepto().toUpperCase());
		pagoGenerico.setConcepto(concepto);
		pagoGenerico.setDescuento(0.0);
		pagoGenerico.setFechaAlta(new Date());
		pagoGenerico.setFechaImportacion(null);
		pagoGenerico.setFirmaDigital("");
		pagoGenerico.setFechaLimite(fechaLimite);
		pagoGenerico.setFolio("");
		pagoGenerico.setMonto(concepto.getMonto());
		pagoGenerico.setMontoUnitario(concepto.getMonto());
		pagoGenerico.setReferencia("");
		pagoGenerico.setReferenciaFondos(null);
		pagoGenerico.setRefReconciliacion(null);
		pagoGenerico.setStatus(0);
		pagoGenerico.setTipo(1);
		
		return pagoGenerico;
	}
	
	@PostMapping(path = "/consultar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String consultarAdeudosGenerado(@RequestBody Map<String, String> obj) {
		Integer cuatrimestre = 0;
		Integer periodo = 0;
		
		try {
			cuatrimestre = Integer.valueOf(obj.get("cuatrimestre"));
		} catch (Exception e) {
			cuatrimestre = 0;
		}
		
		try {
			periodo = Integer.valueOf(obj.get("periodo"));
		} catch (Exception e) {
			periodo = 0;
		}
		
		if (cuatrimestre > 0 && periodo > 0) {
			
			if (cuatrimestre == 1) {
				return "inv";
			}
			
			if (alGrService.contarPorPeriodoAndCuatrimestre(periodo, cuatrimestre)==0) {
			    return "emp";
			}
			
			Integer alumnosConPago = alGrService.contarPorPeriodoYCuatrimestreYPagoGenerado(periodo, cuatrimestre);
			if (alumnosConPago>0) {
				return "gene";
			}
			
			return "notGene";
		}
		
		return "err";
	
	}
	
	@GetMapping("/consultar-cuatri/{periodo}")
	public String consultarCuatrimestres(Model model, @PathVariable("periodo") Integer idPeriodo) {
		if (idPeriodo!=null) {			
		List<PagoCuatrimestreDTO> cuatrimestres = cuatrimestreService.buscarConPagosGenerados(idPeriodo);
		model.addAttribute("cuatrimestres", cuatrimestres);
		}
		return "fragments/buscar-caja :: lista-adeudoCutrimestres";
	}

}
