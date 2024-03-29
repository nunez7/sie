package edu.mx.utdelacosta.controller;

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
import edu.mx.utdelacosta.model.CalificacionCorte;
import edu.mx.utdelacosta.model.CalificacionMateria;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoAsignatura;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.model.Testimonio;
import edu.mx.utdelacosta.model.TipoProrroga;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IProrrogaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.service.ITestimonioService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.ActualizarCalificacion;
import edu.mx.utdelacosta.util.ActualizarRemedial;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor') and hasRole('Rector') and hasRole('Informatica') and hasRole('Director') and hasRole('Asistente')")
@RequestMapping("/remedial")
public class RemedialController {

	@Autowired
	private ICargaHorariaService cargaService;

	@Autowired
	private ICalificacionCorteService calificacionCorteService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;

	@Autowired
	private ITestimonioService testimonioService;

	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;

	@Autowired
	private IPagoGeneralService pagoGeneralService;

	@Autowired
	private IConceptoService conceptoService;
	
	@Autowired
	private ICalificacionMateriaService calificacionMateriaService;
	
	@Autowired
	private IProrrogaService prorrogaService;
	
	@Autowired
	private ActualizarRemedial actualizarRemedial;
	
	@Autowired ActualizarCalificacion actualizaCal;

	// cargar modal remedial
	@GetMapping("/cargar/{alumno}/{tipo}/{carga}/{corte}")
	public String verInstrumentos(@PathVariable(name = "alumno", required = true) Integer idAlumno,
			@PathVariable(name = "tipo", required = true) String tipo,
			@PathVariable(name = "corte", required = true) Integer corteActual,
			@PathVariable(name = "carga", required = true) Integer idCarga, Model model, HttpSession session) {
		Date fechaHoy = new Date();
		CorteEvaluativo corte = corteEvaluativoService.buscarPorId(corteActual);
		Alumno alumno = new Alumno(idAlumno);
		CargaHoraria carga = new CargaHoraria(idCarga);
		
		// se compara el tipo de remdial, si es remedial o extraordinario
		if (tipo.equals("remedial")) {
			
			// se busca si el periodo de remediales esta activo
			if (corteEvaluativoService.buscarPorCorteYFechaRemedial(corte, fechaHoy)==null) {
				Prorroga prorroga = prorrogaService.buscarPorCargaHorariaYTipoProrrogaYAceptada(
						new CargaHoraria(idCarga),new TipoProrroga(1), new Date(),corte);
				if (prorroga == null) {
						model.addAttribute("respuesta", "Actualmente no se encuentra en periodo de remediales");
						return "fragments/modal-calificar:: verRemedialExtra";
				}

			}
			
			// se busca si el alumno tiene un remedial
			RemedialAlumno remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(alumno, 
					new CargaHoraria(idCarga), new Remedial(1),corte);

			if (remedial==null) {
						
				//en caso de no tener se busca si el alumno es apto para generar un remedial
				boolean aptoRemedial = comprobarRemedial(idAlumno, idCarga, corteActual);
				if (aptoRemedial == false) {		
					// caso negativo se devuelve la vista
					model.addAttribute("respuesta", "El alumno no se encuentra en remedial");
				}else {
					carga = cargaService.buscarPorIdCarga(idCarga);
					List<Testimonio> testimonios = testimonioService.buscarTodosPorIntegradora(carga.getMateria().getIntegradora());
					model.addAttribute("remedial", remedial);					
					model.addAttribute("tipo", 1);
					model.addAttribute("cActual", idCarga);
					model.addAttribute("testimonios", testimonios);
				}
				
			}else{
					
				//se construye la carga y se buscan los testimonios en base a la materia (integradora o no)
				carga = cargaService.buscarPorIdCarga(idCarga);
				List<Testimonio> testimonios = testimonioService.buscarTodosPorIntegradora(carga.getMateria().getIntegradora());

				model.addAttribute("remedial", remedial);					
				model.addAttribute("tipo", 1);
				model.addAttribute("cActual", idCarga);
				model.addAttribute("testimonios", testimonios);
		
			}
				
		}

		if (tipo.equals("extraordinario")) {
			if (corteEvaluativoService.buscarPorCorteYFechaExtraordinario(corte, fechaHoy)==null) {
				Prorroga prorroga = prorrogaService.buscarPorCargaHorariaYTipoProrrogaYAceptada(
						new CargaHoraria(idCarga),new TipoProrroga(1), new Date(), corte);
				if (prorroga == null) {
					model.addAttribute("respuesta", "Actualmente no se encuentra en periodo de remediales");
						return "fragments/modal-calificar:: verRemedialExtra";
				}
			}
			
				boolean remedial = comprobarExtraordinario(idAlumno, idCarga, corteActual);
				if (remedial == true) {
					carga = cargaService.buscarPorIdCarga(idCarga);
					List<Testimonio> testimonios = testimonioService
							.buscarTodosPorIntegradoraExtra(carga.getMateria().getIntegradora());
					RemedialAlumno extra = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedial(
							new Alumno(idAlumno), new CargaHoraria(idCarga), new Remedial(2));
					if (extra != null) {
						model.addAttribute("remedial", extra);
					}
					model.addAttribute("tipo", 2);
					model.addAttribute("cActual", idCarga);
					model.addAttribute("testimonios", testimonios);
				} else {
					model.addAttribute("respuesta", "El alumno no se encuentra en extraordinario");
				}
		}

		model.addAttribute("corteActual", corteActual);
		model.addAttribute("alumno", idAlumno);
		return "fragments/modal-calificar:: verRemedialExtra";
	}

	@PostMapping(path = "/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarRemedialExtra(@RequestBody Map<String, String> obj, HttpSession session, Model model) {
		// se obtienen los datos de la vista
		CargaHoraria carga = cargaService.buscarPorIdCarga((Integer) session.getAttribute("cveCarga"));
		Integer idRemedial = Integer.parseInt(obj.get("remedial"));
		RemedialAlumno remedial = remedialAlumnoService.buscarPorId(idRemedial);
		
		String respuesta = actualizarRemedial.eliminar(carga, remedial);
		
		return respuesta;
	}

	@PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarRemedialExtra(@RequestBody Map<String, String> obj, HttpSession session, Model model) {
		// se obtienen los datos de la vista
		Persona persona = new Persona(Integer.parseInt(session.getAttribute("cvePersona").toString()));
		Usuario usuario = usuarioService.buscarPorPersona(persona);
		CargaHoraria carga = cargaService.buscarPorIdCarga(Integer.parseInt(obj.get("carga")));
		Integer tipoRemedial = Integer.parseInt(obj.get("tipo"));
		Integer idAlumno = Integer.parseInt(obj.get("alumno"));
		Alumno alumno = new Alumno(idAlumno);
		Integer idCorte = Integer.parseInt(obj.get("corte"));
		String respuesta = null;

		if (obj.get("testimonio") == null) {
			return "inv";
		}

		Testimonio testimonio = testimonioService.buscarPorId(Integer.parseInt(obj.get("testimonio")));

		// se busca si existe un remedial
		RemedialAlumno remedialAlumno = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(alumno, carga,
				new Remedial(tipoRemedial), new CorteEvaluativo(idCorte));

		Integer contarRemediales = remedialAlumnoService.contarPorAlumnoYPeriodoYTipo(idAlumno,
				usuario.getPreferencias().getIdPeriodo(), tipoRemedial);

		if (tipoRemedial == 1) {
			if (contarRemediales >= 5) {
				respuesta = "baja";
			}
		} else {
			if (contarRemediales >= 3) {
				respuesta = "baja";
			}
		}
		
		// si no existe se crea, si ya existe se actualiza
		if (remedialAlumno == null) {
			

			remedialAlumno = new RemedialAlumno();
			remedialAlumno.setAlumno(alumno);
			remedialAlumno.setCargaHoraria(new CargaHoraria(carga.getId()));
			remedialAlumno.setRemedial(new Remedial(tipoRemedial));
			remedialAlumno.setTestimonio(testimonio);
			remedialAlumno.setPagado(false);
			remedialAlumno.setCorteEvaluativo(new CorteEvaluativo(idCorte));
			remedialAlumnoService.guardar(remedialAlumno);
		} else {
			remedialAlumno.setTestimonio(testimonio);
			remedialAlumnoService.guardar(remedialAlumno);
		}

		//se actualiza el valor de la calificacion corte
		CalificacionCorte caCorte = calificacionCorteService.buscarPorAlumnoYCargaHorariaYCorteEvaluativo(new Alumno(idAlumno), new CargaHoraria(carga.getId()), new CorteEvaluativo(idCorte));
		caCorte.setValor(testimonio.getNumero().floatValue());
		calificacionCorteService.guardar(caCorte);
		
		//se busca la calificacion materia y se actualiza su estatus 
		actualizaCal.actualizaCalificacionMateria(idAlumno, carga.getId());
		
		CalificacionMateria caMateria = calificacionMateriaService.buscarPorCargayAlumno(new CargaHoraria(carga.getId()), new Alumno(idAlumno));

		if (tipoRemedial == 1) {
			caMateria.setEstatus("R");			
		} else {			
			caMateria.setEstatus("E");
		}
		calificacionMateriaService.guardar(caMateria);
		
		
		
		// se busca si ya esxiste un adeudo
		PagoGeneral pago = new PagoGeneral();
		Concepto concepto = new Concepto();
		if (tipoRemedial == 1) {
			concepto = conceptoService.buscarPorId(22);
		} else {
			concepto = conceptoService.buscarPorId(13);
		}
		pago = pagoGeneralService.buscarPorAlumnoYConceptoYCargaHorariaYCorte(idAlumno, concepto.getId(),
				carga.getId(), idCorte);

		if (pago == null) {
			pago = new PagoGeneral();
			pago.setCantidad(1);
			pago.setCliente(0);
			pago.setActivo(true);
			pago.setDescripcion(concepto.getConcepto() +" "+ carga.getMateria().getNombre().toUpperCase()+" - PARCIAL "+corteEvaluativoService.buscarPorId(idCorte).getConsecutivo());
			pago.setConcepto(concepto);
			pago.setDescuento(0.0);
			pago.setFechaAlta(new Date());
			pago.setFechaImportacion(null);
			pago.setFirmaDigital("");
			pago.setFolio("");
			pago.setMonto(concepto.getMonto());
			pago.setMontoUnitario(concepto.getMonto());
			pago.setReferencia("");
			pago.setReferenciaFondos("");
			pago.setRefReconciliacion("");
			pago.setStatus(0);
			pago.setTipo(1);
			
			PagoAsignatura pAsignatura = new PagoAsignatura();
			pAsignatura.setIdCorteEvaluativo(idCorte);
			pAsignatura.setCargaHoraria(new CargaHoraria(carga.getId()));
			pAsignatura.setOportunidad(1);
			pAsignatura.setPagoGeneral(pago);
			
			PagoAlumno pAlumno = new PagoAlumno();
			pAlumno.setAlumno(alumno);
			pAlumno.setPagoGeneral(pago);
			
			pago.setPagoAlumno(pAlumno);
			
			pago.setPagoAsignatura(pAsignatura);
			
			pagoGeneralService.guardar(pago);


		} else {

		}
		
		return respuesta!=null ? respuesta : "ok";
	}
	
	@PostMapping(path = "/eliminar-escolares", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarRemedialEscolares(@RequestBody Map<String, String> obj, Model model) {
		CargaHoraria carga = cargaService.buscarPorIdCarga(Integer.valueOf(obj.get("idCarga")));
		RemedialAlumno remedial = remedialAlumnoService.buscarPorId(Integer.valueOf(obj.get("idRemedial")));
		
		String respuesta;
		if (carga!=null && remedial!=null) {
			respuesta = actualizarRemedial.eliminar(carga, remedial);
		}else {
			respuesta = "null";
		}
		
		return respuesta;
	}

	// metodos genericos
	public boolean comprobarRemedial(Integer idAlumno, Integer idCarga, Integer idCorte) {
		
		CalificacionCorte calificacionCorte = calificacionCorteService.buscarPorAlumnoYCargaHorariaYCorteEvaluativo(new Alumno(idAlumno), new CargaHoraria(idCarga), new CorteEvaluativo(idCorte));
		if (calificacionCorte == null) {
			return false;
		}
		if (calificacionCorte.getValor() < 7.5) {
			return true;
		} else {
			return false;
		}
	}

	public boolean comprobarExtraordinario(Integer idAlumno, Integer idCarga, Integer idCorte) {
		RemedialAlumno remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
				new Alumno(idAlumno), new CargaHoraria(idCarga), new Remedial(1), new CorteEvaluativo(idCorte));
		if (remedial != null && remedial.getTestimonio()!=null) {
			if (remedial.getTestimonio().getAbreviatura().equals("NA")) {
				return true;
			}
		}
		return false;
	}

}
