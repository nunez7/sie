package edu.mx.utdelacosta.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoFamiliar;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.model.DatosAlumno;
import edu.mx.utdelacosta.model.DatosPersonales;
import edu.mx.utdelacosta.model.Escuela;
import edu.mx.utdelacosta.model.EscuelaProcedencia;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Mail;
import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.PeriodoInscripcion;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Rol;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.UsuarioPreferencia;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.ReferenciaBanamexDTO;
import edu.mx.utdelacosta.service.EmailSenderService;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IConceptoService;
import edu.mx.utdelacosta.service.IPagoAlumnoService;
import edu.mx.utdelacosta.service.IPeriodoInscripcionService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.NumberToLetterConverter;
import edu.mx.utdelacosta.util.PdfGenerador;
import edu.mx.utdelacosta.util.ReferenciaFondos;
import edu.mx.utdelacosta.util.ReferenciaSEP;
import edu.mx.utdelacosta.util.Utileria;


@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private EmailSenderService emailService;
	
	@Value("${spring.mail.username}")
	private String EMAIL_DEFAULT;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	 private IAlumnoService alumnoService;
	
	@Autowired
	private IPeriodoInscripcionService periodoService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private ICarrerasServices carreraService;
	
	
	@Autowired
	private PdfGenerador pdfGenerator;
	
	@Autowired 
	private ReferenciaFondos fondosReferencia;
	
	@Autowired 
	private ReferenciaSEP SEPreferencia;
	
	@Autowired
	private IConceptoService serviceConcepto;
		
	@Autowired
	private IPagoAlumnoService pagoAlumnoService;
	
	@Autowired
	private IUsuariosService usuarioService;
		
	
	@PostMapping("/genera-matricula")
	@ResponseBody
	public Map<String, String> nuevaMatricula(@RequestBody Map<String, String> obj) {
		Integer carreraId = Integer.parseInt(obj.get("carreraId"));
		Carrera career = carreraService.buscarPorId(carreraId);
		String grupo = career.getClave();
		Integer periodo = periodoService.buscarPorId(periodoService.ultimoId()).getGeneracion();
		String numMatricula = alumnoService.buscarMatriculaSiguiente(grupo+'-'+periodo);
		Map<String, String> map = new HashMap<>();
		map.put("matricula", grupo+'-'+periodo+numMatricula);
		return map;
	}
	
	@PostMapping(path="/guardar-persona-alumno")
	@ResponseBody
	public String personaAlumnoSave(@RequestBody AlumnoDTO datos, HttpServletRequest request) {
		ZoneId systemTimeZone = ZoneId.systemDefault();
		Persona persona = new Persona();
		PeriodoInscripcion periodo = periodoService.buscarPorId(periodoService.ultimoId());
		DatosPersonales datosP = new DatosPersonales();
		DatosAlumno datosA = new DatosAlumno();
		EscuelaProcedencia escProc = new EscuelaProcedencia();
		Alumno alumno = new Alumno();
		AlumnoFamiliar alumnoFam = new AlumnoFamiliar();
		Date fechaAlta = new Date();
		Carrera carrera = carreraService.buscarPorId(datos.getIdCarrera());
		Usuario usuario = new Usuario();
		Concepto concepto = serviceConcepto.buscarPorId(12);
		PagoGeneral pagoG = new PagoGeneral();
		PagoAlumno pagoA = new PagoAlumno();
		//Se construye persona
		persona.setNombre(datos.getNombre());
		persona.setPrimerApellido(datos.getPrimerApellido());
		persona.setSegundoApellido(datos.getSegundoApellido());
		persona.setNivelEstudio(carrera.getNivelEstudio());
		if(datos.getSexo().equals("Hombre")) {
			persona.setSexo("H");
		}else {
			persona.setSexo("M");
		}
		persona.setEmail(datos.getEmail());
		persona.setFechaAlta(fechaAlta);
	
		//Se construye datosPersona
		datosP.setCurp(datos.getCurp());
		datosP.setTelefono(datos.getTelefono());
		datosP.setEmailVerificado(false);
		datosP.setEstadoCivil(datos.getEstadoCivil());
		datosP.setEdad(datos.getEdad());
		datosP.setEstadoNacimiento(new Estado(Integer.parseInt(datos.getEdoNacimiento())));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaNac = null;
		try {
			fechaNac = formatter.parse(datos.getFechaNacimiento());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		datosP.setFechaNacimiento(fechaNac);
		datosP.setCelular(datos.getCelular());
		//Se insertan datosP en persona y persona en datosP
		datosP.setPersona(persona);
		persona.setDatosPersonales(datosP);
		
		
		//Se construye alumno
		//Se vuelve a generar la matricula, para no tener repeticiones por peticiones al mismo tiempo
		String claveCarrera = carrera.getClave();
		Integer generacion = periodo.getGeneracion();
		LocalDate fechaLim = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(periodo.getFin()));
		String matricula = alumnoService.buscarMatriculaSiguiente(claveCarrera+'-'+generacion);
		String matriculaCompleta = (claveCarrera+'-'+generacion+matricula);

		alumno.setMatricula(matriculaCompleta);
		datos.setMatricula(matriculaCompleta);
		alumno.setCeneval(0);
		alumno.setEstadoDocumentosIngreso(0);
		alumno.setCarreraInicio(carrera);
		alumno.setEstatusGeneral(1);
		//Se construye Alumno Familiar
		alumnoFam.setNombre(datos.getNombreFamiliar());
		alumnoFam.setTelefono(datos.getTelefonoFamiliar());
		alumnoFam.setDomicilio(datos.getDomicilioFamiliar());
		alumnoFam.setCp(datos.getCpFamiliar());
		alumnoFam.setColonia(datos.getColoniaFamiliar());
		alumnoFam.setLocalidad(new Localidad(datos.getIdLocalidadFamiliar()));
		alumnoFam.setAlumno(alumno);
		//Se anexa alumnoFam a alumno
		alumno.setFamiliar(alumnoFam);
		//Se construye el objeto Datos Alumno
		datosA.setHijos(datos.getHijos());
		datosA.setDiscapacitado(datos.getDiscapacitado());
		datosA.setTipoDiscapacidad(datos.getTipoDiscapacidad());
		datosA.setIndigena(datos.getIndigena());
		datosA.setTipoBeca(datos.getTipoBeca());
		datosA.setPromocion(datos.getPromocion());
		datosA.setAlumno(alumno);
		//Se anexa alumnoFam a alumno
		alumno.setDatosAlumno(datosA);
		//Se construye escuelaProcedencia
		escProc.setEscuela(new Escuela(datos.getIdEscuelaProcedencia()));
		escProc.setPromedio(Float.parseFloat(datos.getPromedio()));
		escProc.setAlumno(alumno);
		escProc.setFechaFinal(fechaAlta);
		escProc.setFechaInicio(fechaAlta);
		//Se anexa escProc a alumno
		alumno.setEscuelaProcedencia(escProc);	
		//Se llenan los datos de BanamexDto
		ReferenciaBanamexDTO referencia = new ReferenciaBanamexDTO();
		referencia.setMatricula(matriculaCompleta);
		referencia.setCarrera(carrera);
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatterPat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter vistaFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fecha = localDate.format(formatterPat);
		String fecha2 = fechaLim.format(formatterPat);
		//
		DateTimeFormatter formattter = DateTimeFormatter.ofPattern("yyyy");
		String formattedLocalDate = localDate.format(formattter);
		referencia.setAnio(formattedLocalDate);
		referencia.setFechaHoy(localDate.format(vistaFecha));
		referencia.setFechaPago(fechaLim.format(vistaFecha));
		referencia.setFechaLimite(fecha2);
		referencia.setFechaInscripcion(fecha);
		referencia.setConcep(concepto.getConcepto());
		DecimalFormat formatoPago = new DecimalFormat("0.00");
		referencia.setPago(Double.valueOf(concepto.getMonto()));
		String montoLetras =  NumberToLetterConverter.convertNumberToLetter(Double.valueOf(concepto.getMonto()));
		referencia.setEscritoDinero(montoLetras);
		referencia.setDineros(formatoPago.format(referencia.getPago()));
		//Segun yo, se manda a hablar el metodo
		String cadena =SEPreferencia.generaReferencia(referencia);
		referencia.setReferencia(cadena);
		//Se supone, se genera el pdf
		String fondos=fondosReferencia.referenciaFondos(referencia);
		referencia.setFondos(fondos);
		String pdfname =pdfGenerator.generadorPdfReferencia(referencia, datos);
		//Se genera el usuario
		String contrasenia = (Utileria.ramdomNumeric(6));
		usuario.setContrasenia(passwordEncoder.encode(contrasenia));
		usuario.setUsuario(matriculaCompleta);
		usuario.setActivo(true);
		usuario.setFechaAlta(new Date());
		List <Rol> roles = new ArrayList<>();
		roles.add(new Rol(1));
		
		usuario.setRoles(roles);
		UsuarioPreferencia up = new UsuarioPreferencia(datos.getIdCarrera(), 9, 10);
		up.setUsuario(usuario);
		usuario.setPreferencias(up);
		//Se anexa alumno a persona
		alumno.setPersona(persona);
		usuario.setPersona(persona);
		//Se guarda el alumno
		alumnoService.guardar(alumno);
		usuarioService.guardar(usuario);
		//Se construye pagoGeneral
		pagoG.setActivo(true);
		pagoG.setCantidad(1);
		pagoG.setCliente(0);
		pagoG.setDescripcion(concepto.getConcepto());
		pagoG.setConcepto(concepto);
		pagoG.setFechaAlta(fechaAlta);
		pagoG.setDescuento(0.0);
		pagoG.setStatus(0);
		//pagoG.setFechaImportacion(); se deja como null
		//Parseo de LocalDate a Date porque jaja no se deja en la base de datos xd
		ZonedDateTime zonedDateTime = fechaLim.atStartOfDay(systemTimeZone);
		Date parseoFechaLimite = Date.from(zonedDateTime.toInstant());
		pagoG.setFechaLimite(parseoFechaLimite);
		pagoG.setMonto(referencia.getPago());
		pagoG.setMontoUnitario(concepto.getMonto());
		pagoG.setReferencia(cadena);
		pagoG.setReferenciaFondos(fondos);
		pagoG.setTipo(1);
		pagoA.setAlumno(alumno);
		pagoA.setPagoGeneral(pagoG);
		pagoAlumnoService.guardar(pagoA);
		
		// Create the email
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		Mail mail = new Mail();
		mail.setDe(EMAIL_DEFAULT);
		mail.setPara(new String[] {datos.getEmail() });
		//Email title
		mail.setTitulo("¡Proceso de inscripción!");
		//Variables a plantilla
		Map<String, Object> variables = new HashMap<>();
		variables.put("titulo", "Bienvenido a la Universidad Tecnológica de Nayarit");
		variables.put("cuerpoCorreo",
				"Hola "+datos.getNombre()+" : <br>"
						+ "Se ha iniciado tu registro para ingresar a nuestra institución para el Cuatrimestre Septiembre-Diciembre "+formattedLocalDate+".<br>"
						+ "Además, te hemos generado un acceso a nuestro sistema con los siguiente datos: <br>"
				+ "Usuario/ matrícula: "+matriculaCompleta+"<br>Contraseña: "+contrasenia+"<br>"
						+ "<a style ='color:white' href='"+url+"/login' class='btn' target='_blank'>Link de acceso a sistema</a><br>"
						+ "NOTA: En el documento adjunto encontrarás tu ficha para el depósito bancario.");
		mail.setVariables(variables);
		try {
			emailService.sendEmailWithFichaPago(mail, pdfname);
		} catch (MessagingException | IOException e) {
			System.out.println("Error "+e);
		}
		
		return "ok";
	}
	
	@PostMapping("/verificar-email")
	@ResponseBody
	public  Map<String, String> verficaEmail(@RequestBody Map<String, String> obj){
		String email = obj.get("email");	
		Map<String, String> map = new HashMap<>();
		map.put("matricula", String.valueOf(personaService.buscarEmailExistente(email)));
		return map;		
	}
	
	@PostMapping("/verificar-curp")
	@ResponseBody
	public Map<String, String> verificaCurp(@RequestBody Map<String, String> obj){
		String curp = obj.get("curp");
		Map<String, String> map = new HashMap<>();
		map.put("matricula", String.valueOf(alumnoService.buscarCurpExistente(curp)));
		return map;
	}
}
