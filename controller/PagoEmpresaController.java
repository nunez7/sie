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

import edu.mx.utdelacosta.model.Cliente;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.PagoArea;
import edu.mx.utdelacosta.model.PagoCliente;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.PagoRecibe;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.service.IClienteService;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPersonaService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/pago-empresa")
public class PagoEmpresaController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IPagoGeneralService pagoGeneralService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IConceptoService conceptoService;

	@GetMapping("/buscar-cliente/{like}")
	public String buscarCliente(@PathVariable("like") String like, Model model) {
		//lista que traera el personal o alumnos de la busqueda
		List<Cliente> clientes = clienteService.buscarPorNombreORfc(like);
		model.addAttribute("clientes", clientes);
		return "fragments/buscar-caja :: lista-clientes";
	}
	
	//para agregar un nuevo concepto (pago general)
		@PostMapping(path = "/generar-pago", consumes = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public String generarAdeudo(@RequestBody Map<String, String> obj, HttpSession session) throws ParseException {
			//se crea el usuario logueado(cajero)
			Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
			//parseo de hora
			String fecha = obj.get("fecha");
			String hora = obj.get("hora");
			Date fechaHora = parsearfecha(fecha, hora);
			
			//generá el folio para setearlo a los pagos
			String folio = pagoGeneralService.generarFolio();
			ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
			//se itera la lista de todas las llaven enviadas por el formulario
			for (String string : keyList) {
				// guarda el valor de la clave de la iteración
				String clave = obj.get(string);
				// compara si es un adeudo existente

				// se separa el resultado
				String[] cadena = clave.split("._.");
				// se compara la longitud del contenido, si este es igual a 10 se trata de una
				// cadena que agregara nuevos conceptos
				if (cadena.length == 10) {
					// variables para determinar si es alumno o personal
					int cveCliente = Integer.valueOf(obj.get("cveCliente"));
					int tipoPago = Integer.valueOf(obj.get("tipoPago")); // tipo de pago
					String area = obj.get("area");
					if(area==null) { 
						return "emp"; 
					}

					// se separan los datos de la cadena
					int idConcepto = Integer.parseInt(cadena[0]);
					int cantidad = Integer.parseInt(cadena[1]);
					Double monto = Double.valueOf(cadena[2]);
					// int tipoDescuento = Integer.parseInt(cadena[3]);
					Double descuento = Double.valueOf(cadena[4]);
					// String tipoConcepto = cadena[5];
					// int idAlumnoGrupo = Integer.parseInt(cadena[6]);
					// int idCarga = Integer.parseInt(cadena[7]);
					// int idCorte = Integer.parseInt(cadena[8]);
					String comentario = cadena[9];
					// para guardar los pagos nuevos (creados al momento de pagar)
					// se generará el pago del alumno
					PagoGeneral pago = new PagoGeneral();
					// se busca el nuevo concepto
					Concepto concepto = conceptoService.buscarPorId(idConcepto);
					pago.setConcepto(concepto);
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
					//creamos el pago cliente
					PagoCliente pc = new PagoCliente();
					pc.setCliente(new Cliente(cveCliente));
					pc.setPagoGeneral(pago);
					//se agrega el pago cliente al pago general
					pago.setPagoCliente(pc);
					//creamos el pago area
					PagoArea pa = new PagoArea();
					pa.setPagoGeneral(pago);
					pa.setArea(area);
					//se agrega el pago area al pago general
					pago.setPagoArea(pa);
					// creamos el pago recibe
					PagoRecibe pr = new PagoRecibe();
					pr.setCajero(persona);
					pr.setFechaCobro(new Date());
					pr.setPagoGeneral(pago);
					//se agrega el pago recibe al pago general
					pago.setPagoRecibe(pr);
					pagoGeneralService.guardar(pago);
				}
			}
			return "ok-"+folio;
		}
		
		//metodo para parsear la fecha y hora
		public Date parsearfecha(String fecha, String hora) throws ParseException {
			String date = fecha + " " + hora;
			Timestamp timestamp = Timestamp.valueOf(date);
			return timestamp;
		}
}
