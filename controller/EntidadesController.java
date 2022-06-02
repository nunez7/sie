package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Municipio;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ILocalidadesService;
import edu.mx.utdelacosta.service.IMunicipiosService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@RequestMapping("/entidad")
public class EntidadesController {
	
	@Autowired
	private IMunicipiosService municipiosService;
	
	@Autowired
	private ILocalidadesService localidadesService;
	
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@GetMapping("/municipios/{estado}")
   	public String municipios(@PathVariable(name = "estado", required = false) int estado,  Model model, Authentication authentication) { 
    	List<Municipio> municipios = new ArrayList<>();
    	Estado estadoB = new Estado();
		estadoB.setId(estado>0 ? estado:18);
    	municipios = municipiosService.buscarPorEstado(estadoB);
    	
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);
    	model.addAttribute("persona", usuario.getPersona());
		model.addAttribute("municipios", municipios);
		return "perfil/datos :: l-municipios";
   	}
	
	@GetMapping("/localidades/{municipio}")
   	public String localidades(@PathVariable(name = "municipio", required = false) int municipio,  Model model, Authentication authentication) { 
    	List<Localidad> localidades = new ArrayList<>();
    	Municipio municipioB = new Municipio();
    	municipioB.setId(municipio>0 ? municipio:2293);
		localidades = localidadesService.buscarPorMunicipio(municipioB);
		String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);
		model.addAttribute("persona", usuario.getPersona());
		model.addAttribute("localidades", localidades);
		return "perfil/datos :: l-localidades";
   	}

	@GetMapping("/municipiosf/{estado}")
   	public String municipiosF(@PathVariable(name = "estado", required = false) int estado,  Model model, Authentication authentication) { 
    	List<Municipio> municipios = new ArrayList<>();
    	Estado estadoB = new Estado();
		estadoB.setId(estado>0 ? estado:18);
    	municipios = municipiosService.buscarPorEstado(estadoB);
    	
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);
    	Alumno alumno = alumnoService.buscarPorPersona(usuario.getPersona());
    	model.addAttribute("alumno", alumno);
		model.addAttribute("municipiosF", municipios);
		return "perfil/datos :: lf-municipios";
   	}
	
	@GetMapping("/localidadesf/{municipio}")
   	public String localidadesF(@PathVariable(name = "municipio", required = false) int municipio,  Model model, Authentication authentication) { 
    	List<Localidad> localidades = new ArrayList<>();
    	Municipio municipioB = new Municipio();
    	municipioB.setId(municipio>0 ? municipio:2293);
		localidades = localidadesService.buscarPorMunicipio(municipioB);
		String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);
    	Alumno alumno = alumnoService.buscarPorPersona(usuario.getPersona());
    	model.addAttribute("alumno", alumno);
		model.addAttribute("localidadesF", localidades);
		return "perfil/datos :: lf-localidades";
   	}
}
