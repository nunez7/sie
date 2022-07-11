package edu.mx.utdelacosta.controller;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IPagoGeneralService;
import edu.mx.utdelacosta.service.IPersonaDocumentoService;
import edu.mx.utdelacosta.util.Reinscribir;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Servicios Escolares')")
@RequestMapping("/aspirante")
public class AspiranteController {
		
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private IPersonaDocumentoService personaDocService;
	
	@Autowired
	private IPagoGeneralService pagoGeneralService;
	
	@Autowired
	private Reinscribir reinscripcion;
	
	@PostMapping(path = "/inscribir-en-grupo", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String inscribirEnGrupo(@RequestBody Map<String, String> obj, HttpSession session) {
		Integer idAlumno  = Integer.parseInt(obj.get("idAlumno"));
		Alumno alumno = alumnoService.buscarPorId(idAlumno);
		
		Integer valor = personaDocService.documentosValidosPorPersona(alumno.getPersona().getId());
		if(valor ==3) {
			Integer pagado =  pagoGeneralService.validarPagoExamenAdmision(alumno.getPersona().getId());
			if (pagado==null || pagado == 0) {
				return "noPago";
			}
		}else {
			return "noDocs";
		}
		
		reinscripcion.reinscribir(alumno, (Integer) session.getAttribute("cvePersona"));
		
		return "ok";
	}

}
