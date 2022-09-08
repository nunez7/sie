package edu.mx.utdelacosta.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.PagoRecibe;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaReferencia;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPersonaReferenciaService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;
import edu.mx.utdelacosta.util.SubirArchivo;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Caja')")
@RequestMapping("/banamex")
public class BanamexController {
	
	//Inyectando desde properties
	@Value("${siestapp.ruta.docs}")
	private String ruta;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IPersonaReferenciaService personaReferenciaService;
	
	@Autowired
	private IPagoGeneralService pagoGeneralService;
	
	@Autowired
	private IRemedialAlumnoService remedialAlumnoService;
	
	@Autowired
	private IAlumnoGrupoService alumnoGrupoService;
	
	Logger logger = Logger.getLogger(BanamexController.class.getName());

	@PostMapping(path = "/importar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String importarArchivo(@RequestParam("archivo") MultipartFile multiPart, @RequestParam("tipo") Integer tipo, HttpSession session) throws IOException {
		//se crea el usuario logueado(cajero)
		Persona persona = personaService.buscarPorId((Integer) session.getAttribute("cvePersona"));
		try {
			String nombreArchivo = "";
			//para guardar el archivo
			if(!multiPart.isEmpty()) {
				nombreArchivo = SubirArchivo.guardarArchivo(multiPart, ruta+"/banco/");
				if(!nombreArchivo.equals(null)) {
					String m = "";
					//banco
					if(tipo == 2) {
						m = generarFoliosNormal(ruta+"/banco/"+nombreArchivo, persona.getId());
					}
					//corresponsalias
					else {
						m = generarFoliosCorresponsalias(ruta+"/banco/"+nombreArchivo, persona.getId());
					}
				}
				else {
					logger.info("No se guardo el archivo");
				}
			}
		} catch (Exception e) {
			logger.warning(e.getLocalizedMessage());
		}
		return "ok";
	}
	
	//referencias corresponsalias
	public String generarFoliosCorresponsalias(String archivo, int cajero) throws IOException {
		FileReader fr = null;
		try {
			fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String linea = "";
			//se lee la linea 
			while((linea = br.readLine()) != null) {
				if(!linea.isEmpty()) {
					//se parte la linea para extraer los datos
					String [] cadena = linea.split("\\|");
					//preguntamos si la cadena es de 10 posiciones para saber que es una cadena de pago
					if(cadena.length == 10) {
						//se compara para que sea la referencia y no la comprobación del pago
						if(cadena[7].length() > 20) {
							//fecha, referencia, cajero, importe y tipo de pago
							generarFolio(cadena[1], cadena[7], cajero, cadena[8].replace(",", ""), 2);
						}
					}
					
				}
			}
		} catch (Exception e) {
			logger.warning(e.getLocalizedMessage());
		}
		finally {
			fr.close();
		}
		return "ok";
	}
	
	//referencias banco
	public String generarFoliosNormal(String archivo, int cajero) throws IOException {
		FileReader fr = null;
		try {
			fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String linea = "";
			while ((linea = br.readLine()) != null) {
				if (!linea.isEmpty()) {
					String fechaPago = "20" + linea.substring(1, 3) + "-" + linea.substring(3, 5) + "-" + linea.substring(5, 7);
					// importe
					String importe = "";
					String referencia = "";
					try {
						importe = linea.substring(7, 20) + "." + linea.substring(20, 22);
						referencia = linea.substring(47, 78);
						int tipoPago = 1;
						generarFolio(fechaPago, referencia, cajero, importe, tipoPago);

					} catch (Exception e) {
						importe = "NA";
						referencia = "NA";
						br.close();
						logger.warning(e.getLocalizedMessage());
					}

				}
			}
			br.close();
		} catch (Exception e) {
			logger.warning(e.getLocalizedMessage());
		}
		finally {
			fr.close();
		}
		return "ok";
	}
	
	
	//para generar las referencias 
	public String generarFolio(String fecha, String referencia, int cajero, String importe, int tipoPago) {
		//empieza el procedimiento para insertar los pagos
		try {
			//sacamos el objeto de persona referencia
			PersonaReferencia pr = null;
			pr = personaReferenciaService.buscarPorReferencia(referencia);
			//se compará que el objeto no sea nulo para empezar con la importación
			if(pr != null) {
				//se obtiene el folio para los adeudos
				String folio = pagoGeneralService.generarFolio();
				//variable para alumnos grupo y checar si no tiene adeudos para reinscribirlo
				int ag = 0;
				//variable para guardar el id de alumno
				int cveAlumno = 0;
				// se buscan los adeudos con la referencia 
				List<PagoGeneral> adeudos = pagoGeneralService.buscarPorReferencia(referencia);
				//recorre los adeudos 
				for (PagoGeneral pagoGeneral : adeudos) {
					PagoGeneral pg = pagoGeneralService.buscarPorId(pagoGeneral.getId());
					pg.setStatus(1); //estatus de pagado
					pg.setFolio(folio);
					pg.setTipo(tipoPago); //tipo de pago
					pg.setFechaImportacion(new Date()); // fecha en que se importo el archivo
					//compara si es tiene un pago de cuatrimestre para reinscribirlo
					if(pg.getPagoCuatrimestre() != null) {
						//se actualiza la variable de alumno grupo 
						ag = pg.getPagoCuatrimestre().getAlumnoGrupo().getId();
						cveAlumno = pg.getPagoCuatrimestre().getAlumnoGrupo().getAlumno().getId();
					}
					//compara si es un pago de asignatura
					if(pg.getPagoAsignatura()!=null) {
						RemedialAlumno remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
								pg.getPagoAlumno().getAlumno(), pg.getPagoAsignatura().getCargaHoraria(),
								new Remedial(2), new CorteEvaluativo(pg.getPagoAsignatura().getIdCorteEvaluativo()));
						// se compara si el extraordinario es diferente a nulo
						if(remedial != null) {
							remedial.setPagado(true);
							remedialAlumnoService.guardar(remedial);
						}

						remedial = remedialAlumnoService.buscarPorAlumnoYCargaHorariaYRemedialYCorte(
								pg.getPagoAlumno().getAlumno(), pg.getPagoAsignatura().getCargaHoraria(),
								new Remedial(1), new CorteEvaluativo(pg.getPagoAsignatura().getIdCorteEvaluativo()));
						if(remedial != null) {
							remedial.setPagado(true);
							remedialAlumnoService.guardar(remedial);
						}
					}
					//se crea el pago recibe 
					PagoRecibe pRecibe = new PagoRecibe();
					pRecibe.setCajero(new Persona(cajero)); // se crea el objeto de cajero
					pRecibe.setFechaCobro(parsearfecha(fecha)); // fecha de cobro
					pRecibe.setPagoGeneral(pg);
					//se guardar el pago recibe en el pago general 
					pg.setPagoRecibe(pRecibe);
					//se guarda el pago general
					pagoGeneralService.guardar(pg);
				}
				
				//se guarda la fecha de pago, estatus pagado y el folio en persona_referencia 
				pr.setFolioPago(folio);
				pr.setFechaPago(new Date());
				pr.setPagado(true);
				//se inserta el total pagado 
				pr.setTotal(Double.valueOf(importe));
				personaReferenciaService.guardar(pr);
				
				//para reinscribir el alumno en caso de que haya hecho pago de cuatrimestre y no tenga adeudos
				int adeudosAlumno = pagoGeneralService.buscarPorAlumno(cveAlumno, 0).size();
				if(adeudosAlumno < 1 && ag > 0) {
					AlumnoGrupo alumnoG = alumnoGrupoService.buscarPorId(ag);
					alumnoG.setPagado(true);
					alumnoG.setFechaInscripcion(new Date());
					alumnoGrupoService.guardar(alumnoG);
				}
			}
		} catch (Exception e) {
			logger.warning(e.getLocalizedMessage());
		}
		return "ok";
	}
	
	//metodo para parsear la fecha y hora
	public Date parsearfecha(String fecha) throws ParseException {
		String date = fecha.replace("-", "/");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");
		return format.parse(date);
	}
	
	
}
