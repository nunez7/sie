package edu.mx.utdelacosta.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Documento;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaDocumento;
import edu.mx.utdelacosta.model.PrestamoDocumento;
import edu.mx.utdelacosta.model.dto.DocumentoDTO;
import edu.mx.utdelacosta.model.dto.PdfUrlPDO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IDocumentosService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPersonaDocumentoService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPrestamoDocumentoService;
import edu.mx.utdelacosta.util.Reinscribir;
import edu.mx.utdelacosta.util.SubirArchivo;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Servicios Escolares')")
@RequestMapping("/persona-documento")
public class PersonaDocumentoController {

	@Autowired
	private IPersonaDocumentoService personaDocService;

	@Autowired
	private IDocumentosService documentoService;

	@Autowired
	private EmailSenderService emailService;

	@Value("${spring.mail.username}")
	private String EMAIL_DEFAULT;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private IPrestamoDocumentoService prestamoDocumentoService;

	@Autowired
	private IPagoGeneralService pagoGeneralService;

	@Value("${siestapp.ruta.docs}")
	private String rutaDocs;
	
	@Autowired
	private Reinscribir reinscripcion;

	@GetMapping("/search-by-idpersona/{id}")
	public ResponseEntity<List<PdfUrlPDO>> buscarDocumentos(@PathVariable("id") int id, Model model) {
		List<PersonaDocumento> documentos = personaDocService.buscarActaCurpCerbachiPorPersona(id);
		List<PdfUrlPDO> pdfUrl = new ArrayList<>();
		for (PersonaDocumento ch : documentos) {
			PdfUrlPDO pdfDTO = new PdfUrlPDO();
			Documento doc = documentoService.buscarPorId(ch.getDocumento().getId());
			pdfDTO.setIdDocumento(doc.getId());
			pdfDTO.setUrl(ch.getUrlPdf());
			pdfDTO.setValidado(ch.getValidado());
			pdfUrl.add(pdfDTO);
		}
		if (pdfUrl.isEmpty()) {
			pdfUrl = null;
		}
		return ResponseEntity.ok(pdfUrl);
	}

	@PostMapping("/aprobar-documentos-prospecto")
	@ResponseBody
	public String aprobdocs(@RequestBody Map<String, String> obj, HttpServletRequest request, HttpSession session) {
		String value = (obj.get("value"));
		int idDoc = 0;
		Integer idAl = (Integer.parseInt(obj.get("idAl")));
		switch (value) {
		// Curp
		case "2":
			idDoc = 2;
			break;
		// Acta de nacimiento
		case "4":
			idDoc = 1;
			break;
		// Certificado
		case "6":
			idDoc = 3;
			break;
		}
		
		updateAndSendcorreoAceptar(idAl, idDoc, request, (Integer) session.getAttribute("cvePersona"));
		return "ok";
	}

	public void updateAndSendcorreoAceptar(Integer idAl, int idDoc, HttpServletRequest request, Integer idPersona) {
		
		
		PersonaDocumento pd = new PersonaDocumento();
		pd = personaDocService.buscarPorPersonaYIdDoc(idAl, idDoc);
		pd.setValidado(true);
		personaDocService.guardar(pd);
		int valor = personaDocService.documentosValidosPorPersona(idAl);
		if (valor == 3) {
						
			
			
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			String email = personaService.buscarPorId(idAl).getEmail();
			// Estado de que entrego los documentos
			Alumno al = alumnoService.buscarPorPersona(new Persona(idAl));
			al.setEstadoDocumentosIngreso(1);
			alumnoService.guardar(al);
			// Create the email
			Mail mail = new Mail();
			mail.setDe(EMAIL_DEFAULT);
			mail.setPara(new String[] { email });
			// Email title
			mail.setTitulo("Documentos en orden");
			// Variables a plantilla
			Map<String, Object> variables = new HashMap<>();
			variables.put("titulo", "Documentación aprobada");
			variables.put("cuerpoCorreo",
							  "Estimado(a) Alumno(a), <br>"
							+ "Se le notifica que sus documentos han sido aprobados por Servicios Escolares.<br>"
							+ "¡Ya eres parte de nuestra Universidad! <br>" + "<a style ='color:white' href='" + url
							+ "' class='btn' target='_blank'>Link de acceso a sistema</a><br>");
			mail.setVariables(variables);
			try {
				emailService.sendEmail(mail);
				System.out.println("Enviado");
			} catch (MessagingException | IOException e) {
				System.out.println("Error " + e);
			}
			

			Integer pagado = pagoGeneralService.validarPagoExamenAdmision(idAl);

			if (pagado != null && pagado == 1) {
				//se envian los datos al util de reinscribir, siendo idAl el id de persona del alumno e idPersona
				// el id de persona de quien realiza la accion
				reinscripcion.inscribirNuevoAlumno(alumnoService.buscarPorPersona(new Persona(idAl)), idPersona);
			}
			
		}

	}

	@PostMapping("/rechazar-documentos-prospecto")
	@ResponseBody
	public String rechdocs(@RequestBody Map<String, String> obj) {
		String value = (obj.get("value"));
		Integer idAl = (Integer.parseInt(obj.get("idAl")));
		String motivo = (obj.get("motivo"));
		String tipo;
		int idDoc;
		switch (value) {
		case "1":
			tipo = "CURP";
			idDoc = 2;
			updateAndSendcorreoRechazo(idAl, motivo, tipo, idDoc);
			break;
		case "3":
			tipo = "Acta de nacimiento";
			idDoc = 1;
			updateAndSendcorreoRechazo(idAl, motivo, tipo, idDoc);
			break;
		case "5":
			tipo = "Certificado de Bachillerato";
			idDoc = 3;
			updateAndSendcorreoRechazo(idAl, motivo, tipo, idDoc);
			break;
		}
		return "ok";
	}

	public void updateAndSendcorreoRechazo(Integer idAl, String motivo, String tipo, int idDoc) {
		PersonaDocumento pd = new PersonaDocumento();
		pd = personaDocService.buscarPorPersonaYIdDoc(idAl, idDoc);
		pd.setValidado(false);
		personaDocService.guardar(pd);

		String email = personaService.buscarPorId(idAl).getEmail();
		// Create the email
		Mail mail = new Mail();
		mail.setDe(EMAIL_DEFAULT);
		mail.setPara(new String[] { email });
		// Email title
		mail.setTitulo("Documentación rechazada");
		// Variables a plantilla
		Map<String, Object> variables = new HashMap<>();
		variables.put("titulo", "Problemas con tu documento");
		variables.put("cuerpoCorreo", "Estimado alumno, se le notifica que su documento: " + tipo
				+ " ha sido rechazado, debido a: " + motivo + ".<br>"
				+ "Puede subirlo nuevamente al sistema o acudir con tu documentación a la oficina de Servicios Escolares. <br>");
		mail.setVariables(variables);
		try {
			emailService.sendEmail(mail);
			System.out.println("Enviado");
		} catch (MessagingException | IOException e) {
			System.out.println("Error " + e);
		}
	}

	@GetMapping("/valida-por-prospecto/{id}")
	@ResponseBody
	public String validocs(@PathVariable("id") String id) {
		Integer valor = personaDocService.documentosValidosPorPersona(Integer.parseInt(id));
		if (valor == 3) {

			// se comprueba si se pago el cuatri
			// esto deberia ir en otro controller pero desde aqui hace la validacion de
			// documento y preferi no crear otro controller
			Integer pagado = pagoGeneralService.validarPagoExamenAdmision(Integer.parseInt(id));

			if (pagado == null || pagado == 0) {
				return "no";
			}

			return "ok";
		} else {

			return "no";
		}

	}

	@PostMapping(path = "/activar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String activar(@RequestBody Map<String, String> obj, HttpSession session) {
		// se crean las variables
		Integer idDocumento;
		String status;

		// se obtienen los valores
		try {
			status = (obj.get("valor"));
		} catch (Exception e) {
			status = "vacio";
		}

		try {
			idDocumento = Integer.parseInt(obj.get("documento"));
		} catch (Exception e) {
			idDocumento = 0;
		}

		// validacion de datos no vacios
		if (idDocumento > 0 && !status.equals("vacio")) {
			Documento documento = documentoService.buscarPorId(idDocumento);
			Alumno alumno = alumnoService.buscarPorId((Integer) session.getAttribute("cveAlumno"));

			// se valida que tanto el documento como el alumno no sean nulos
			if (alumno != null || documento != null) {

				// se busca el registro especifico de la persona
				PersonaDocumento personaDoc = personaDocService.buscarPorPersonaYdocumento(alumno.getPersona(),
						documento);

				// se determina el estado del documento
				boolean validado = false;
				String respuesta = "";
				if (status.equals("No Entregado")) {
					status = "Entregado";
					validado = true;
					respuesta = "on";
				} else if (status.equals("Entregado")) {
					status = "No Entregado";
					validado = false;
					respuesta = "off";
				}

				// se crea/guarda el documento
				if (personaDoc == null) {
					return "null";
				} else {
					personaDoc.setEntregado(status);
					personaDoc.setValidado(validado);
					personaDocService.guardar(personaDoc);
				}

				// se buscan los documentos entregados para validar el campo entrado en alumno
				List<DocumentoDTO> docsExp = personaDocService
						.buscarActaCurpCerbachiPorPersonaParaDto(alumno.getPersona().getId());
				List<Boolean> valDoc = new ArrayList<>();
				for (DocumentoDTO documentoDTO : docsExp) {
					valDoc.add(documentoDTO.getValidado());
				}

				// en caso de que los 3 documentos principales esten entregados se valida en
				// alumno
				Boolean validados = false;
				if (new HashSet<Boolean>(valDoc).size() <= 1) {
					validados = valDoc.get(0);
					if (validados == true) {
						alumno.setEstadoDocumentosIngreso(1);
					} else {
						alumno.setEstadoDocumentosIngreso(0);
					}
				} else {
					alumno.setEstadoDocumentosIngreso(0);
				}
				alumnoService.guardar(alumno);

				return respuesta;

			}

		} else {
			return "blank";
		}

		return "ok";
	}

	@PostMapping(path = "/prestar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String prestar(@RequestBody Map<String, String> obj, HttpSession session) {
		String acta = obj.get("acta");
		String certificado = obj.get("certificado");
		Persona persona = new Persona((Integer) session.getAttribute("cvePersona"));

		if (acta == null) {
			acta = "off";
		}

		if (certificado == null) {
			certificado = "off";
		}

		if (acta != null || certificado != null) {

			Alumno alumno = alumnoService.buscarPorId((Integer) session.getAttribute("cveAlumno"));
			if (acta != null) {
				boolean prestado = false;
				if (acta.equals("on")) {
					prestado = true;
				}

				PersonaDocumento actaDoc = personaDocService.buscarPorPersonaYdocumento(alumno.getPersona(),
						new Documento(1));

				if (actaDoc != null && actaDoc.getValidado() == true) {
					actaDoc.setPrestado(prestado);
					personaDocService.guardar(actaDoc);

					if (prestado == true) {
						PrestamoDocumento prestamo = new PrestamoDocumento();
						prestamo.setEstatus(true);
						prestamo.setFechaAlta(new Date());
						prestamo.setPersona(persona);
						prestamo.setPersonaDocumento(actaDoc);
						prestamoDocumentoService.guardar(prestamo);
					}

				}

			}

			if (certificado != null) {
				boolean prestado = false;
				if (certificado.equals("on")) {
					prestado = true;
				}

				PersonaDocumento certificadoDoc = personaDocService.buscarPorPersonaYdocumento(alumno.getPersona(),
						new Documento(3));

				if (certificadoDoc != null && certificadoDoc.getValidado() == true) {
					certificadoDoc.setPrestado(prestado);
					personaDocService.guardar(certificadoDoc);

					if (prestado == true) {
						PrestamoDocumento prestamo = new PrestamoDocumento();
						prestamo.setEstatus(true);
						prestamo.setFechaAlta(new Date());
						prestamo.setPersona(persona);
						prestamo.setPersonaDocumento(certificadoDoc);
						prestamoDocumentoService.guardar(prestamo);
					}
				}

			}

		}

		return "ok";
	}

	@PostMapping(path = "/devolver-prestamo", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String adevolverPrestamo(@RequestBody Map<String, String> obj, HttpSession session) {
		Integer idPrestamo;
		try {
			idPrestamo = Integer.valueOf(obj.get("idPrestamo"));
		} catch (Exception e) {
			idPrestamo = 0;
		}

		if (idPrestamo > 0) {
			PrestamoDocumento prestamo = prestamoDocumentoService.buscarPorId(idPrestamo);
			prestamo.setEstatus(false);
			prestamoDocumentoService.guardar(prestamo);

			PersonaDocumento personaDoc = personaDocService.buscarPorId(prestamo.getPersonaDocumento().getId());

			personaDoc.setPrestado(false);

			personaDocService.guardar(personaDoc);
		}

		return "ok";

	}

	@PostMapping(path = "/actualizar-detalle", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String actualizaDetalle(@RequestBody Map<String, String> obj, HttpSession session) {
		Integer numeroDetalle;
		Integer idDocumento = 0;
		try {
			numeroDetalle = Integer.parseInt(obj.get("valor"));
		} catch (Exception e) {
			numeroDetalle = 0;
		}
		
		try {
			idDocumento = Integer.parseInt(obj.get("documento"));
		} catch (Exception e) {
			idDocumento = 0;
		}

		if (idDocumento!=0 && numeroDetalle!=0) {
			Documento documento = documentoService.buscarPorId(idDocumento);

			if (documento == null) {
				return "null";
			}

			Alumno alumno = alumnoService.buscarPorId((Integer) session.getAttribute("cveAlumno"));
			PersonaDocumento docPersona = personaDocService.buscarPorPersonaYdocumento(alumno.getPersona(), documento);
			docPersona.setDetalle(numeroDetalle);
			personaDocService.guardar(docPersona);
			return "ok";

		} else {
			return "err";
		}

	}

	@GetMapping("/ver-escolares/{idDocumento}")
	public String verEscolares(@PathVariable("idDocumento") Integer idDocumento, Model model, HttpSession session) {
		Alumno alumno = alumnoService.buscarPorId((Integer)session.getAttribute("cveAlumno"));		
		PersonaDocumento documento = personaDocService.buscarPorPersonaYdocumento(alumno.getPersona(), new Documento(idDocumento));
		if (documento==null) {
			documento = new PersonaDocumento();
			documento.setEntregado("No Entregado");
			documento.setDocumento(documentoService.buscarPorId(idDocumento));
			documento.setPrestado(false);
			documento.setUrlPdf(null);
			documento.setValidado(false);
			documento.setPersona(alumno.getPersona());
			personaDocService.guardar(documento);
		}
		model.addAttribute("personaDocumento", documento);
		return "fragments/control-alumnos :: subirArchivo";
	}

	@PostMapping(path = "/guardar-escolares", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String guardarEscolares(@RequestParam("archivo") MultipartFile multiPart,
			@RequestParam("idPersonaDocumento") Integer idPersonaDocumento, HttpSession session) {
		PersonaDocumento documento = personaDocService.buscarPorId(idPersonaDocumento);

		if (!multiPart.isEmpty()) {
			String nombreImagen = SubirArchivo.guardarArchivo(multiPart, rutaDocs + "/alumnos/inscripcion/");
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				documento.setUrlPdf(nombreImagen);
				personaDocService.guardar(documento);
			}
		}
		return "ok";
	}

	@PostMapping(path = "/eliminar-escolares", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eliminarEscolares(@RequestBody Map<String, String> obj, HttpSession session) {
		PersonaDocumento documento = personaDocService.buscarPorId(Integer.valueOf(obj.get("idDocumento")));
		if (documento != null) {
			SubirArchivo.borrarArchivo(rutaDocs + "/alumnos/inscripcion/" + documento.getUrlPdf());
			documento.setUrlPdf(null);
			personaDocService.guardar(documento);
		}
		return "ok";
	}

}