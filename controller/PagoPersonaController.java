package edu.mx.utdelacosta.controller;

import java.sql.Timestamp;
import java.text.ParseException;
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

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Bitacora;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.ConceptoAbono;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.PagoPersona;
import edu.mx.utdelacosta.model.PagoRecibe;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.model.dto.AlumnoPersonalDTO;
import edu.mx.utdelacosta.model.dto.CalificacionCorteDTO;
import edu.mx.utdelacosta.model.dto.CalificacionMateriaDTO;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IBitacoraService;
import edu.mx.utdelacosta.service.ICalificacionCorteService;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IConceptoAbonoService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/pago-persona")
public class PagoPersonaController {

	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IConceptoService conceptoService;
	
	@Autowired
	private IPagoGeneralService pagoGeneralService;
	
	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;
	
	@Autowired
	private IAlumnoGrupoService alGrService;
	
	@Autowired
	private IBitacoraService bitacoraService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private ICargaHorariaService cargaHorariaService;
	
	@Autowired
	private ICorteEvaluativoService corteEvaluativoService;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private ICalificacionCorteService calificacionCorteService;
	
	@Autowired
	private IConceptoAbonoService conceptoAbonoService;
	
	@GetMapping("/buscar-cliente/{like}")
	public String buscarCliente(@PathVariable("like") String like, Model model) {
		//lista que traera el personal o alumnos de la busqueda
		List<AlumnoPersonalDTO> clientes = personaService.buscarPorMatriculaONoEmpledoONombre(like);
		model.addAttribute("clientes", clientes);
		return "fragments/buscar-caja :: lista-personas";
	}
	
	//busca las calificaciones del alumno en su ultimo grupo
	@GetMapping("/calificaciones-alumno/{alumno}/{grupo}")
	public String calificacionesAlumno(@PathVariable("alumno") Integer cveAlumno, @PathVariable("grupo") Integer grupo, Model model,
			HttpSession session) {
		Alumno alumno = alumnoService.buscarPorId(cveAlumno);
		Grupo grupoActual = grupoService.buscarPorId(grupo);
		//lista de cargas horarias
		List<CargaHoraria> cargasHorarias = cargaHorariaService.buscarPorGrupoYPeriodo(grupoActual.getId(), grupoActual.getPeriodo().getId());
		//lista de cortes evaluativos de la carrera del alumno
		List<CorteEvaluativo> cortes = corteEvaluativoService.buscarPorCarreraYPeriodo(new Carrera(grupoActual.getCarrera().getId()), grupoActual.getPeriodo());
		//lista que guardará las calificaciones de las materias
		List<CalificacionMateriaDTO> calificaciones = new ArrayList<>();
		for (CargaHoraria ch : cargasHorarias) { //se iteran las cargas horarias (materias del grupo)
			//objeto que guardará las calificaciones de la materia
			CalificacionMateriaDTO calificacionMateria = new CalificacionMateriaDTO();
			calificacionMateria.setNombre(ch.getMateria().getNombre());
			//variable para sumar calificacion
			double promedio = 0;
			//lista de calificacion corte 
			List<CalificacionCorteDTO> calificacionesCorte = new ArrayList<>();
			for (CorteEvaluativo corte : cortes) { //se iteran los cortes evaluativos
				//Objeto que guardará las calificaciones de cada corte
				CalificacionCorteDTO cc = new CalificacionCorteDTO();
				//se setea la calificacion
				cc.setCalificacion(calificacionCorteService.buscarPorAlumnoCargaHorariaYCorteEvaluativo(
						alumno.getId(), ch.getId(), corte.getId()).floatValue());
				//se setea el estatus de la materia
				RemedialAlumno remedial = remedialAlumnoService
						.buscarUltimoPorAlumnoYCargaHorariaYCorteEvaluativo(alumno.getId(), ch.getId(),
								corte.getId());
				if (remedial != null) {
					if (remedial.getRemedial().getId() == 1) {
						cc.setEstatus("R");
					} else {
						cc.setEstatus("E");
					}
				} else {
					cc.setEstatus("O");
				}
				promedio = promedio + cc.getCalificacion();
				//se guarda la calificacion corte 
				calificacionesCorte.add(cc);
			}
			Integer pg = (int) Math.round(promedio / cortes.size());
			calificacionMateria.setCalificacion(pg);
			// se guarda la calificacion de cada corte en calificacion materia
			calificacionMateria.setCortes(calificacionesCorte);
			//se agregan las calificaciones a la lista
			calificaciones.add(calificacionMateria);
		}
		model.addAttribute("date", new Date());
		model.addAttribute("calificaciones", calificaciones);
		model.addAttribute("grupoName", grupoActual.getNombre());
		return "caja/plantillaFactura :: modal-calificaciones";
	}
	
	//para agregar un nuevo concepto (pago general)
	@PostMapping(path = "/generar-adeudo", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String generarAdeudo(@RequestBody Map<String, String> obj, HttpSession session) throws ParseException {
		//se crea el usuario logueado(cajero)
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		//parseo de hora
		String fecha = obj.get("fecha");
		String hora = obj.get("hora");
		//variable para asiganr o no factura
		String factura = String.valueOf(obj.get("factura"));
		Date fechaHora = parsearfecha(fecha, hora);
		//variable para alumnos grupo y checar si no tiene adeudos para reinscribirlo
		int ag = 0;
		//variable para guardar el id de alumno
		int idAlumno = 0;
		//generá el folio para setearlo a los pagos
		String folio = pagoGeneralService.generarFolio();
		ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
		//se itera la lista de todas las llaves enviadas por el formulario
		for (String string : keyList) {
			//guarda el valor de la clave de la iteración
			String clave = obj.get(string);
			//compara si es un adeudo existente 
			if(clave.equals("on")) {
				PagoGeneral pagoExiste = pagoGeneralService.buscarPorId(Integer.parseInt(string));
				pagoExiste.setFolio(folio);
				pagoExiste.setStatus(1);
				pagoExiste.setFactura(Boolean.valueOf(factura));
				//se guarda el pago recibe
				
				PagoRecibe pr = new PagoRecibe();
				pr.setCajero(persona);
				pr.setFechaCobro(new Date());
				pr.setPagoGeneral(pagoExiste);
				pagoExiste.setPagoRecibe(pr);
				
				if(pagoExiste.getPagoAsignatura() != null && pagoExiste.getPagoAsignatura().getIdCorteEvaluativo() != null) {
					RemedialAlumno remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
							pagoExiste.getPagoAlumno().getAlumno(), pagoExiste.getPagoAsignatura().getCargaHoraria(),
							new Remedial(2), new CorteEvaluativo(pagoExiste.getPagoAsignatura().getIdCorteEvaluativo()));
					// se compara si el extraordinario es diferente a nulo
					if(remedial != null) {
						remedial.setPagado(true);
						remedialAlumnoService.guardar(remedial);
					}

					remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
							pagoExiste.getPagoAlumno().getAlumno(), pagoExiste.getPagoAsignatura().getCargaHoraria(),
							new Remedial(1), new CorteEvaluativo(pagoExiste.getPagoAsignatura().getIdCorteEvaluativo()));
					if(remedial != null) {
						remedial.setPagado(true);
						remedialAlumnoService.guardar(remedial);
					}
				}
				//para sacar variables del la reinscripcion autoamtica
				if(pagoExiste.getPagoCuatrimestre() != null && pagoExiste.getPagoCuatrimestre().getAlumnoGrupo() != null) {
					//se actualiza la variable de alumno grupo 
					ag = pagoExiste.getPagoCuatrimestre().getAlumnoGrupo().getId();
					idAlumno = pagoExiste.getPagoCuatrimestre().getAlumnoGrupo().getAlumno().getId();
				}
				//se guarda el pago 
				pagoGeneralService.guardar(pagoExiste);
			}
			else {
				//se separa el resultado
				String[] cadena = clave.split("._.");
				//se compara la longitud del contenido, si este es igual a 10 se trata de una cadena que agregara nuevos conceptos
				if(cadena.length==10) {
					//variables para determinar si es alumno o personal
					int cveAlumno = Integer.valueOf(obj.get("cveAlumno"));
					int cvePersona = Integer.valueOf(obj.get("cvePersona"));//en caso de ser personal de envia el id de la persona
					int tipoPago = Integer.valueOf(obj.get("tipoPago")); //tipo de pago 
					
					//se separan los datos de la cadena
					int idConcepto = Integer.parseInt(cadena[0]);
					int cantidad = Integer.parseInt(cadena[1]);
					Double monto = Double.valueOf(cadena[2]);
					//int tipoDescuento = Integer.parseInt(cadena[3]);
					Double descuento = Double.valueOf(cadena[4]);
					//String tipoConcepto = cadena[5];
					//int idAlumnoGrupo = Integer.parseInt(cadena[6]);
					//int idCarga = Integer.parseInt(cadena[7]);
					//int idCorte = Integer.parseInt(cadena[8]);
			        String comentario = cadena[9]; 
					//para guardar los pagos nuevos (creados al momento de pagar)
					//se generará el pago del alumno
					PagoGeneral pago = new PagoGeneral();
					//se busca el nuevo concepto
					Concepto concepto = conceptoService.buscarPorId(idConcepto);
					pago.setConcepto(concepto);
					
					//para generar el abano de colegiatura
			        if(cveAlumno > 0 && idConcepto == 7 || idConcepto == 8 || idConcepto == 9 || idConcepto == 10) { //se comprueba que sea un alumno
			        	String comentarioAbono = generarAbono(cveAlumno, idConcepto, monto, folio);
			        	if(comentarioAbono.equals("fail-menor")) {
			        		return "fail-menor";
			        	}
			        	else {
			        		comentario = comentarioAbono;
			        		
			        	}
			        }
			      //para generar el abano de titulo
			        if(cveAlumno > 0 && idConcepto == 50 || idConcepto == 51) { //se comprueba que sea un alumno
			        	String comentarioAbono = generarAbono(cveAlumno, idConcepto, monto, folio);
			        	if(comentarioAbono.equals("fail-menor")) {
			        		return "fail-menor";
			        	}
			        	else {
			        		comentario = comentarioAbono;
			        		
			        	}
			        }
					pago.setCantidad(cantidad);
					pago.setDescripcion(concepto.getConcepto());
					pago.setFolio(folio);
					pago.setTipo(tipoPago);
					pago.setReferencia("");
					pago.setFechaLimite(null);
					pago.setFechaAlta(fechaHora);
					pago.setMontoUnitario(concepto.getMonto());
					pago.setMonto(monto);
					pago.setCliente(0);
					pago.setActivo(true);
					pago.setDescuento(descuento);
					pago.setComentario(comentario.equals("NOCOMENT") ? "" : comentario); 
					pago.setStatus(1);
					pago.setFactura(Boolean.valueOf(factura));
					//creamos el pago recibe
					PagoRecibe pr = new PagoRecibe();
					pr.setCajero(persona);
					pr.setFechaCobro(new Date());
					pr.setPagoGeneral(pago);
					pago.setPagoRecibe(pr);
					//condición para saber si es pago de alumno o pago de personal
					if(cveAlumno > 0) {
						PagoAlumno pa = new PagoAlumno();
						pa.setAlumno(new Alumno(cveAlumno));
						pa.setPagoGeneral(pago);
						pago.setPagoAlumno(pa);
					}
					else {
						PagoPersona pp = new PagoPersona();
						pp.setPersona(new Persona(cvePersona));
						pp.setPagoGeneral(pago);
						pago.setPagoPersona(pp);
					}
					pagoGeneralService.guardar(pago); 
				}	
			}
		}
		
		//para reinscribir el alumno en caso de que haya hecho pago de cuatrimestre y no tenga adeudos
		int adeudosAlumno = pagoGeneralService.buscarPorAlumno(idAlumno, 0).size();
		if(adeudosAlumno < 1 && ag > 0) {
			AlumnoGrupo alumnoG = alGrService.buscarPorId(ag);
			alumnoG.setPagado(true);
			alumnoG.setFechaInscripcion(new Date());
			alGrService.guardar(alumnoG);
		}
		
		return "ok-"+folio;
	}
	
	//metodo para parsear la fecha y hora
	public Date parsearfecha(String fecha, String hora) throws ParseException {
		String date = fecha + " " + hora;
		Timestamp timestamp = Timestamp.valueOf(date);
		return timestamp;
	}
	
	//para dar desactivar un adeudo
	@PostMapping(path = "/desactivar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String desactivarAdeudo(@RequestBody Map<String, Integer> obj, HttpSession session) {
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		PagoGeneral adeudo = pagoGeneralService.buscarPorId(obj.get("id"));
		adeudo.setActivo(false);
		pagoGeneralService.guardar(adeudo);
		//se guarda en bitacora 
		Bitacora bitacora = new Bitacora();
		bitacora.setPersona(persona);
		bitacora.setAccion("BAJA DE ADEUDO");
		bitacora.setDetalle("LA PERSONA : "+persona.getId()+", DIO DE BAJA EL ADEUDO : " +adeudo.getId());
		bitacora.setFechaAlta(new Date());
		bitacoraService.guardar(bitacora);
		return "ok";
	}
	
	@PostMapping(path = "/editar-comentario", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editarComentario(@RequestBody Map<String, String> obj) {
		String comentario = obj.get("comentario");
		Integer idPago = Integer.valueOf(obj.get("idPago"));
		if (idPago != null && comentario != null) {
			PagoGeneral pago = pagoGeneralService.buscarPorId(idPago);
			pago.setComentario(comentario);
			pagoGeneralService.guardar(pago);
		}
		return "up";
	}

	@PostMapping(path = "/editar-descuento", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editarDescuento(@RequestBody Map<String, String> obj, HttpSession session) {
		Double descuento = Double.valueOf(obj.get("descuento"));
		Integer idPago = Integer.valueOf(obj.get("idPago"));
		if (idPago != null && descuento != null) {
			PagoGeneral pago = pagoGeneralService.buscarPorId(idPago);
			   pago.setDescuento(descuento);
			   Double precioDescuento = pago.getConcepto().getMonto() - (pago.getConcepto().getMonto()*(descuento/100));
			   Double importe = pago.getCantidad() * precioDescuento;
			   pago.setMonto(importe);
			   pagoGeneralService.guardar(pago);
		}
		return "up";
	}
	
	//funcion para registrar abonos
	public String generarAbono(Integer idAlumno, Integer idConcepto, double monto, String folio) {
		double adeudo = 0.0; //adeudo que tendra el pago 
    	List<PagoGeneral> adeudos = pagoGeneralService.buscarPorAlumno(idAlumno, 0);
    	//se crea el objeto del abono
    	ConceptoAbono abono = new ConceptoAbono();
    	//comentario para el abono
    	String comentario = "";
    	//se iteran los adeudos
    	for (PagoGeneral a : adeudos) {
    		//abono de colegiatura
    		if(idConcepto == 7 || idConcepto == 8 || idConcepto == 9 || idConcepto == 10) {
    			if(a.getConcepto().getId() == 7 || a.getConcepto().getId() == 8 || a.getConcepto().getId() == 9
			        || a.getConcepto().getId() == 10) {
    				//se guardan el pago y concepto
    				abono.setPago(a);//se a�ade el pago general
        			abono.setConcepto(new Concepto(idConcepto)); 
        			comentario = "ABONO " + a.getDescripcion();
    			}
    		}
    		//abono de titulo
    		if(idConcepto == 50 || idConcepto == 51) {
    			if(a.getConcepto().getId() == 50 || a.getConcepto().getId() == 51) {
    				abono.setPago(a);//se a�ade el pago general
        			abono.setConcepto(new Concepto(idConcepto));
        			comentario = "ABONO " + a.getDescripcion();
    			}
    		}
    		//se guardan los demas datos 
    		adeudo = a.getMonto() - monto;
			a.setMonto(adeudo);
			//para saber si ya se completo de pago 
			if(adeudo == 0) {
				a.setFolio(folio);
				a.setStatus(1);
				a.setComentario("PAGADO EN PARCIALIDADES");
			}
			if(adeudo < 0) {
				return "fail-menor";
			}
			//se termina de guardar el objeto de abono concepto
			abono.setFolio(folio);
			abono.setCantidad(monto); //monto que viene en la cadena del adeudo
			conceptoAbonoService.guardar(abono);
			//se guarda el pagoGeneral actualizado
			pagoGeneralService.guardar(a);
		}
    	
    	return comentario;
	}
}
