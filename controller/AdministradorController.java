package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dtoreport.UpdatePasswordsAlumnoDTO;
import edu.mx.utdelacosta.model.dtoreport.UpdatePasswordsPersonalDTO;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.Utileria;

@Controller
@PreAuthorize("hasRole('Administrador')")
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private ICarrerasServices carrerasServices;
	
	@Autowired
	private IPeriodosService periodosService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@Autowired
	private IGrupoService grupoService;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/passwords-alumno")
	public String actulizarPasswordAlumno(Model model) {		
		List<Carrera> carreras = carrerasServices.buscarTodasMenosIngles();
		List<Periodo> periodos = periodosService.buscarTodos();
		List<UpdatePasswordsAlumnoDTO> usuarios = new ArrayList<>();
		model.addAttribute("carreras", carreras);
		model.addAttribute("periodos", periodos);
		model.addAttribute("usuarios", usuarios);
		return "administrador/password-alumno";
	}
	
	@GetMapping("/actualizar-passwords-alumnos/{idCarrera}/{idPeriodo}")
	public String actualizarPasswordAlumnos(@PathVariable(name = "idCarrera", required = false) Integer idCarrera, @PathVariable(name = "idPeriodo", required = false) Integer idPeriodo,  Model model) {
		List<UpdatePasswordsAlumnoDTO> UpdateUsuarios = new ArrayList<>();
		if(idCarrera>0 && idPeriodo>0) {
			List<Usuario> usuarios = usuariosService.buscarAlumnosPorCarreraYPeriodo(idCarrera, idPeriodo);
			
			for(Usuario usuario : usuarios) {
				UpdatePasswordsAlumnoDTO user = new UpdatePasswordsAlumnoDTO();
				Alumno alumno = alumnoService.buscarPorPersona(usuario.getPersona());
				Grupo grupo = grupoService.buscarUltimoDeAlumno(alumno.getId());
				user.setCarrera(grupo.getCarrera().getNombre());
				user.setGrupo(grupo.getNombre());
				user.setNombre(usuario.getPersona().getPrimerApellido()+" "+usuario.getPersona().getSegundoApellido()+" "+usuario.getPersona().getNombre());
				user.setMatricula(alumno.getMatricula());
				String contra = Utileria.generateRandomPassword(8);
				user.setContrasenia(contra);
				UpdateUsuarios.add(user);
				
				//Se encripta la contrasena y se le actualiza al usuario
				String passwordEncrypt = passwordEncoder.encode(contra);
				usuario.setContrasenia(passwordEncrypt);
				usuariosService.guardar(usuario);
			}
			
		}
		model.addAttribute("usuarios", UpdateUsuarios);
		return "fragments/reset-passwords :: cargar-usuarios-alumnos";
	}
	
	@GetMapping("/passwords-personal")
	public String actulizarPasswordPersonal(Model model) {	
		List<UpdatePasswordsPersonalDTO> usuarios = new ArrayList<>();
		model.addAttribute("usuarios", usuarios);
		return "administrador/password-personal";
	}
	
	@GetMapping("/actualizar-passwords-personal")
	public String actualizarPasswordPersonal(Model model) {
		List<UpdatePasswordsPersonalDTO> UpdateUsuarios = new ArrayList<>();
		//Solo los profesores
		List<Usuario> usuarios = usuariosService.buscarPorRol(2);	
		
		for(Usuario usuario : usuarios) {
			UpdatePasswordsPersonalDTO user = new UpdatePasswordsPersonalDTO();
			user.setRol(usuario.getRoles().get(0).getDescripcion());
			user.setNombre(usuario.getPersona().getNombre() +" "+usuario.getPersona().getPrimerApellido()+" "+usuario.getPersona().getSegundoApellido());
			user.setUsuario(usuario.getUsuario());
			String contra = Utileria.generateRandomPassword(8);
			user.setContrasenia(contra);
			UpdateUsuarios.add(user);
			
			//Se encripta la contrasena y se le actualiza al usuario
			String passwordEncrypt = passwordEncoder.encode(contra);
			usuario.setContrasenia(passwordEncrypt);
			usuariosService.guardar(usuario);
		}
		
		model.addAttribute("usuarios", UpdateUsuarios);
		return "fragments/reset-passwords :: cargar-usuarios-personal";
	}
	
}
