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
import edu.mx.utdelacosta.model.Bitacora;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.NotaCredito;
import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoArea;
import edu.mx.utdelacosta.model.PagoAsignatura;
import edu.mx.utdelacosta.model.PagoCliente;
import edu.mx.utdelacosta.model.PagoCuatrimestre;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.PagoPersona;
import edu.mx.utdelacosta.model.PagoRecibe;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.model.dto.FolioDTO;
import edu.mx.utdelacosta.model.dto.NotaCreditoDTO;
import edu.mx.utdelacosta.model.dto.PagoConceptoDTO;
import edu.mx.utdelacosta.model.dto.ReciboDTO;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IBitacoraService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.INotaCreditoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.util.CodificarTexto;
import edu.mx.utdelacosta.util.NumberToLetterConverter;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/folio")
public class FolioController {

	@Autowired
	private IPagoGeneralService pagoGeneralService;

	@Autowired
	private IConceptoService conceptoService;

	@Autowired
	private INotaCreditoService notaCreditoService;
	
	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;
	
	@Autowired
	private IBitacoraService bitacoraService;
	
	@Autowired
	private IAlumnoGrupoService alGrService;
	
	@Autowired
	private IPeriodosService periodoService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@GetMapping("/ver-edicion/{folio}")
	public String vistaEdicion(@PathVariable("folio") String noFolio, Model model) {
		FolioDTO folio = pagoGeneralService.buscarPorFolio(noFolio);
		List<PagoGeneral> pagos = pagoGeneralService.buscarTodosPorFolio(noFolio);
		List<Concepto> conceptos = conceptoService.buscarOpcionales();
		model.addAttribute("conceptos", conceptos);
		model.addAttribute("pagos", pagos);
		model.addAttribute("folio", folio);
		return "caja/edicionFolio";
	}

	@GetMapping("/buscar/{dato}")
	public String buscar(@PathVariable("dato") String dato, Model model) {
		List<FolioDTO> folios = pagoGeneralService.buscarFolioPorFolioONombreOCliente(dato);
		model.addAttribute("folios", folios);
		return "fragments/buscar-caja :: lista-folios";
	}

	@PostMapping(path = "/editar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editar(@RequestBody Map<String, String> obj, HttpSession session) {
		String folio = obj.get("idFolio");
		Integer tipoPago = Integer.valueOf(obj.get("tipoPago"));
		if (folio != null && tipoPago != null) {
			List<PagoGeneral> pagos = pagoGeneralService.buscarTodosPorFolio(folio);
			for (PagoGeneral pago : pagos) {
				pago.setTipo(tipoPago);
				pagoGeneralService.guardar(pago);
			}
		}
		return "ok";
	}

	@PostMapping(path = "/editar-comentario", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editarComentario(@RequestBody Map<String, String> obj) {
		String comentario = obj.get("comentario");
		Integer idPago = Integer.valueOf(obj.get("idPago"));
		if (idPago != null && comentario != null) {
			PagoGeneral pago = pagoGeneralService.buscarPorId(idPago);
			pago.setComentario(comentario.toUpperCase());
			pagoGeneralService.guardar(pago);
		}
		return "ok";
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
		return "ok";
	}

	@GetMapping("/recibo")
	public String recibopago(Model model, HttpSession session) {

		// se obtienen los datos
		String folio = session.getAttribute("folio").toString();
		Double totalPago = 0.0;
		Double totalNota = 0.0;
		String folioCifrado = "";
		Grupo ultimoGrupo = new Grupo();
		List<NotaCredito> notas = new ArrayList<>();

		//se rellena la informacion del recibo
		FolioDTO infoRecibo = pagoGeneralService.buscarReciboPorFolio(folio);
		
		if (folio != null) {
			//se obtienen los pagos generales
			List<PagoGeneral> pagosFolio = pagoGeneralService.buscarTodosPorFolio(folio);
			model.addAttribute("pagosFolio", pagosFolio);
			
			//se obtiene el estado de los pagos
			Boolean estatus = pagosFolio.get(0).getActivo();
			//se comprara si el estado de recibo esta activo o cancelado, en caso de ser activo se obtiene el importe total, caso contrario se deja como 0.0 el importe
			NotaCredito nota = new NotaCredito(); 
			if (estatus==true) {	
				for (PagoGeneral pago : pagosFolio) {
					totalPago = totalPago + pago.getMonto();

					nota = notaCreditoService.buscarPorPagoGeneral(pago);
					if (nota!=null) {
						notas.add(nota);
						totalNota = totalNota + nota.getCantidad();
					}
				}
				
				totalPago = totalPago - totalNota;
			}
			
			try {
				folioCifrado = CodificarTexto.encriptAES(folio);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(infoRecibo.getIdAlumno()!=null && infoRecibo.getIdAlumno()!=0) {
				ultimoGrupo = grupoService.buscarUltimoDeAlumno(infoRecibo.getIdAlumno());
				model.addAttribute("ultimoGrupo", ultimoGrupo!=null ? ultimoGrupo : "");
				}
		}
		
		List<Integer> list = new ArrayList<>();
		//se crea una lista que se iterara para tener ambas partes del recibo
		if (infoRecibo.getActivo()==0) {
			list.add(1);
		}else {
			list.add(1);
			list.add(2);
		}
		
		// extrae el monto del adeudo y lo comvierte a letras
		String montoLetras = NumberToLetterConverter.convertNumberToLetter(totalPago);
		model.addAttribute("notas", notas);
		model.addAttribute("montoLetras", montoLetras);
		model.addAttribute("totalPago", totalPago);
		model.addAttribute("folioCifrado", folioCifrado);
		model.addAttribute("folio", infoRecibo);
		model.addAttribute("vueltas", list);
		model.addAttribute("periodo", periodoService.buscarUltimo());
		return "caja/recibo";
	}
	
	@GetMapping("/reciboMultiple")
	public String reciboPagoMultiple(Model model, HttpSession session) {

		//obtencion de datos
		Date fechaInicio = java.sql.Date.valueOf((String) session.getAttribute("fechaInicio"));
		Date fechaFin = java.sql.Date.valueOf((String) session.getAttribute("fechaFin"));
		
		//se buscan los folios de pago en base a las fechas
		List<String> folios = pagoGeneralService.buscarFoliosPorFechaInicioYFechaFinTodosCajeros(fechaInicio, fechaFin);
		List<ReciboDTO> recibos = new ArrayList<>();
		for (String folio : folios) {
			ReciboDTO recibo = new ReciboDTO();
			FolioDTO datosFolio = pagoGeneralService.buscarReciboPorFolio(folio);
			List<PagoConceptoDTO> pagos = pagoGeneralService.buscarConceptoPagoPorFolio(folio);
			NotaCreditoDTO nota = notaCreditoService.BuscarPorFolio(folio);
			
			String folioCifrado = "";
			
			try {
				folioCifrado = CodificarTexto.encriptAES(folio);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			recibo.setMontoLetras(NumberToLetterConverter.convertNumberToLetter(nota.getMontoTotal()));
			recibo.setMontoTotal(nota.getMontoTotal());
			recibo.setFolioCifrado(folioCifrado);
			recibo.setPagos(pagos);
			recibo.setNota(nota);
			recibo.setFolio(datosFolio);
			
			if (datosFolio.getIdAlumno()!=null) {
				Grupo grupo = grupoService.buscarUltimoDeAlumno(datosFolio.getIdAlumno());
				recibo.setNombreGrupo(grupo!=null ? grupo.getNombre() : "");
				recibo.setCarrera(grupo!=null ? grupo.getCarrera().getNombre() : "");
			}
			
			recibos.add(recibo);
		}
		
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		
		model.addAttribute("recibos", recibos);
		model.addAttribute("vueltas", list);
		model.addAttribute("periodo", periodoService.buscarUltimo());
		return "caja/reciboMultiple";
	}


	@PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarFolio(@RequestBody Map<String, String> obj, HttpSession session) {
		String folio = obj.get("numeroFolio");
		Bitacora bitacora = new Bitacora();
		bitacora.setFechaAlta(new Date());
		bitacora.setPersona(new Persona((Integer)session.getAttribute("cvePersona")));

		Integer estatusFolio;
		try {
			estatusFolio = Integer.valueOf(obj.get("estatusFolio"));
		} catch (Exception e) {
			return "err";
		}

		String respuesta;
		switch (estatusFolio) {
		// en caso de alta
		case 1:
			respuesta = alta(obj,bitacora);
			break;

		// en caso de baja
		case 0:
			respuesta = baja(obj,bitacora);
			break;

		// en caso de generar una nota de credito
		case 2:
			respuesta = guardaNotaCredito(obj, folio,bitacora);
			break;

		default:
			// nt = no se reconoce el tipo
			respuesta = "nt";
			break;
		}

		return respuesta;
	}

	public String alta(Map<String, String> obj, Bitacora bitacora) {
		//se obtiene el folio
		String folio = obj.get("numeroFolio");
		Boolean factura = Boolean.valueOf(obj.get("factura"));
		//se obtienen todas las claves de los datos enviados desde la vista
		ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
		PagoGeneral ultimoPago = pagoGeneralService.buscarUltimoPorFolio(folio);
		if (factura!=ultimoPago.getFactura()) {
			List<PagoGeneral> pagos = pagoGeneralService.buscarTodosPorFolio(folio);
			for (PagoGeneral pago : pagos) {
				pago.setFactura(factura);
				pagoGeneralService.guardar(pago);
			}			
		}
		//se iteran las claves 
		for (String string : keyList) {
			
			//se guarda en una variable cada valor de la clave obtenida en base a la iteracion
			String resultado = obj.get(string);
			
			//se separa el resultado
			String[] cadena = resultado.split("._.");
			
			//se compara la longitud del contenido, si este es igual a 10 se trata de una cadena que agregara nuevos conceptos
			
			if (cadena.length==10) {
				
				// se llama el metodo para guardar el pago general 
				guardarNuevoConcepto(cadena, ultimoPago, folio, factura);
				
			}
		}
		
		//guardar en bitacora
		bitacora.setDetalle("LA PERSONA "+bitacora.getPersona().getId()+" DIO DE ALTA EL FOLIO: "+folio);
		bitacora.setAccion("ALTA DE FOLIO");
		bitacoraService.guardar(bitacora);
		
		return "ok";
	}

	public String baja(Map <String, String> obj, Bitacora bitacora) {
		//se obtiene el folio
		String folio = obj.get("numeroFolio");
		
		//se obtienen la lista de pagos pertenecientes al folio
		List<PagoGeneral> pagos = pagoGeneralService.buscarTodosPorFolio(folio);
		
		//se iteran los pagos para desactivar sus respectivos conceptos
		for (PagoGeneral pago : pagos) {
			
			//se comprar si existen remediales en caso de ser asi estos se marcan como no pagados
			if (pago.getPagoAsignatura()!=null) {
				if (pago.getPagoAsignatura().getCargaHoraria()==null || pago.getPagoAsignatura().getIdCorteEvaluativo()==null) {
					return "null";
				}
		
				RemedialAlumno remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(pago.getPagoAlumno().getAlumno(), pago.getPagoAsignatura().getCargaHoraria(), new Remedial(2), new CorteEvaluativo(pago.getPagoAsignatura().getIdCorteEvaluativo()));
				//se compara si el extraordinario es diferente a nulo	
				if (remedial!=null) {
					remedial.setPagado(false);
					remedialAlumnoService.guardar(remedial);
				}
				
				remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(pago.getPagoAlumno().getAlumno(), pago.getPagoAsignatura().getCargaHoraria(), new Remedial(1), new CorteEvaluativo(pago.getPagoAsignatura().getIdCorteEvaluativo()));
				if (remedial!=null) {
					remedial.setPagado(false);
					remedialAlumnoService.guardar(remedial);
				}
			}
			
			
			if (pago.getPagoCuatrimestre()!=null) {
				AlumnoGrupo alumnoG = alGrService.buscarPorId(pago.getPagoCuatrimestre().getAlumnoGrupo().getId());
				alumnoG.setPagado(false);
				alGrService.guardar(alumnoG);
			}
			
			
			//se procede a duplicar el pagoGeneral para guardarlo como adeudo nuevamente
			PagoGeneral nuevoPago = clonarPagoGeneral(pago);
			
			//se desactiva el pago
			pago.setActivo(false);
			pago.setStatus(0);
			
			
			
			//se guarda el pago
			pagoGeneralService.guardar(pago);
			
			//se guarda el nuevo adeudo
			pagoGeneralService.guardar(nuevoPago);
			
			//guardar en bitacora
			bitacora.setDetalle("LA PERSONA "+bitacora.getPersona().getId()+" DIO DE BAJA EL FOLIO: "+folio);
			bitacora.setAccion("BAJA DE FOLIO");
			bitacoraService.guardar(bitacora);
		}
		
		return "ok";
	}

	public String guardaNotaCredito(Map<String, String> obj, String folio, Bitacora bitacora) {
		
		String comentario = obj.get("comentario");
		Double cantidad;
		try {
			cantidad = Double.valueOf(obj.get("notaCantidad"));
		} catch (Exception e) {
			cantidad = 0d;
		}

		if (folio != null && cantidad > 0) {

			Double total = pagoGeneralService.sumarTotalMontoPorFolio(folio);
			if (total == 0) {
				return "inv";
			}

			if (cantidad > 0 && cantidad <= total) {
				PagoGeneral pago = pagoGeneralService.buscarTodosPorFolio(folio).get(0);
				NotaCredito notaCredito = notaCreditoService.buscarPorPagoGeneral(pago);
				if (notaCredito == null) {
					notaCredito = new NotaCredito();
					notaCredito.setActivo(true);
					notaCredito.setCantidad(cantidad);
					notaCredito.setComentario(comentario.toUpperCase());
					notaCredito.setFechaAlta(new Date());
					notaCredito.setPagoGeneral(pago);
					notaCreditoService.guardar(notaCredito);
				} else {
					notaCredito.setCantidad(cantidad);
					notaCredito.setComentario(comentario);
					notaCreditoService.guardar(notaCredito);
				}
			} else {
				return "max";
			}
		} else {
			return "inv";
		}
		
		//guardar en bitacora
		bitacora.setDetalle("LA PERSONA "+bitacora.getPersona().getId()+" REGISTRÓ UNA NOTA DE CRÉDITO PARA EL FOLIO: "+folio+", CON CANTIDAD DE: "+cantidad);
		bitacora.setAccion("NOTA DE CRÉDITO");
		bitacoraService.guardar(bitacora);

		return "ok";
	}
	
	public PagoGeneral clonarPagoGeneral(PagoGeneral pagoOriginal) {
		//se crea un nuevo pago
		PagoGeneral pagoClon = new PagoGeneral();
		
		//se setean los datos del pago al nuevo
		pagoClon.setActivo(true);
		pagoClon.setStatus(0);
		pagoClon.setCantidad(pagoOriginal.getCantidad());
		pagoClon.setCliente(pagoOriginal.getCliente());
		pagoClon.setComentario(pagoOriginal.getComentario());
		pagoClon.setConcepto(pagoOriginal.getConcepto());
		pagoClon.setDescripcion(pagoOriginal.getDescripcion());
		pagoClon.setDescuento(pagoOriginal.getDescuento());
		pagoClon.setFechaAlta(new Date());
		pagoClon.setMonto(pagoOriginal.getMonto());
		pagoClon.setMontoUnitario(pagoOriginal.getMontoUnitario());
		pagoClon.setTipo(pagoOriginal.getTipo());
		pagoClon.setReferencia("");
		pagoClon.setReferenciaFondos("");
		
		// se compara si existe un pagoAlumno, de existir se crea el nuevo pagoAlumno y se setean los datos
		if (pagoOriginal.getPagoAlumno()!=null) {
			PagoAlumno pagoAlumno = new PagoAlumno();
			pagoAlumno.setAlumno(pagoOriginal.getPagoAlumno().getAlumno());
			pagoAlumno.setPagoGeneral(pagoClon);
			pagoClon.setPagoAlumno(pagoAlumno);
			
		}
		
		// se compara si existe un pagoAsignatura, de existir se crea el nuevo pagoAsignatura y se setan los datos
		if (pagoOriginal.getPagoAsignatura()!=null) {
			PagoAsignatura pagoAsignatura = new PagoAsignatura();
			pagoAsignatura.setCargaHoraria(pagoOriginal.getPagoAsignatura().getCargaHoraria());
			pagoAsignatura.setIdCorteEvaluativo(pagoOriginal.getPagoAsignatura().getIdCorteEvaluativo());
			pagoAsignatura.setOportunidad(pagoOriginal.getPagoAsignatura().getOportunidad());
			pagoAsignatura.setPagoGeneral(pagoClon);
			pagoClon.setPagoAsignatura(pagoAsignatura);
		}
		
		// se comprueba si existe un pago de un pago de cuatrimestre, si es este el caso se clona
		if (pagoOriginal.getPagoCuatrimestre()!=null) {
			PagoCuatrimestre pagoCuatrimestre = new PagoCuatrimestre();
			pagoCuatrimestre.setAlumnoGrupo(pagoOriginal.getPagoCuatrimestre().getAlumnoGrupo());
			pagoCuatrimestre.setPagoGeneral(pagoClon);
			pagoClon.setPagoCuatrimestre(pagoCuatrimestre);
		}
		
		// se comprueba si existe un pago de parte de cliente
		if (pagoOriginal.getPagoCliente()!=null) {
			PagoCliente pagoCliente = new PagoCliente();
			pagoCliente.setCliente(pagoOriginal.getPagoCliente().getCliente());
			pagoCliente.setPagoGeneral(pagoClon);
			pagoClon.setPagoCliente(pagoCliente);
		}
		
		if (pagoOriginal.getPagoArea()!=null) {
			PagoArea pagoArea = new PagoArea();
			pagoArea.setArea(pagoOriginal.getPagoArea().getArea());
			pagoArea.setPagoGeneral(pagoClon);
			pagoClon.setPagoArea(pagoArea);
		}
		
		if (pagoOriginal.getPagoPersona()!=null) {
			PagoPersona pagoPersona = new PagoPersona();
			pagoPersona.setPersona(pagoOriginal.getPagoPersona().getPersona());
			pagoPersona.setPagoGeneral(pagoClon);
			pagoClon.setPagoPersona(pagoPersona);
		}
		
		// regresa el pago duplicado
		return pagoClon;
	}
	
	public void guardarNuevoConcepto(String[] cadena, PagoGeneral ultimoPago, String folio, Boolean factura) {
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
        
        //se obtiene el concepto0
        Concepto concepto = conceptoService.buscarPorId(idConcepto);
        
        //se crea el pago general
        PagoGeneral pago = new PagoGeneral();
		pago.setCantidad(cantidad);
		pago.setCliente(0);
		pago.setActivo(true);
		pago.setComentario(comentario.equals("NOCOMENT") ? "" : comentario);
		pago.setDescripcion(concepto.getConcepto().toUpperCase());
		pago.setConcepto(concepto);
		pago.setDescuento(descuento);
		pago.setFechaAlta(ultimoPago.getFechaAlta());
		pago.setFechaImportacion(ultimoPago.getFechaImportacion()!=null ? ultimoPago.getFechaImportacion() : null);
		pago.setFirmaDigital(ultimoPago.getFirmaDigital()!=null ? ultimoPago.getFirmaDigital() : "");
		pago.setFechaLimite(ultimoPago.getFechaLimite());
		pago.setFolio(folio);
		pago.setMonto(monto);
		pago.setFactura(factura);
		pago.setMontoUnitario(concepto.getMonto());
		pago.setReferencia(ultimoPago.getReferencia()!=null ? ultimoPago.getReferencia() : "");
		pago.setReferenciaFondos(null);
		pago.setRefReconciliacion(null);
		pago.setStatus(1);
		pago.setTipo(1);
		
		if (ultimoPago.getPagoRecibe()!=null) {
			PagoRecibe pRecibe = new PagoRecibe();
			pRecibe.setCajero(ultimoPago.getPagoRecibe().getCajero());
			pRecibe.setFechaCobro(ultimoPago.getPagoRecibe().getFechaCobro());
			pRecibe.setPagoGeneral(pago);
			pago.setPagoRecibe(pRecibe);;
		}
		
		if (ultimoPago.getPagoAlumno()!=null) {
			PagoAlumno pAlumno = new PagoAlumno();
			pAlumno.setAlumno(ultimoPago.getPagoAlumno().getAlumno());
			pAlumno.setPagoGeneral(pago);
			pago.setPagoAlumno(pAlumno);
		}
		
		if (ultimoPago.getPagoCliente()!=null) {
			PagoCliente pCliente = new PagoCliente();
			pCliente.setCliente(ultimoPago.getPagoCliente().getCliente());
			pCliente.setPagoGeneral(pago);
			pago.setPagoCliente(pCliente);
		}
		
		//pago cliente
			
		pagoGeneralService.guardar(pago);
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
	
	
	

}
