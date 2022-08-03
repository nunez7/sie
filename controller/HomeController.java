package edu.mx.utdelacosta.controller;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Sesion;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.IBajaService;
import edu.mx.utdelacosta.service.IDosificacionCargaService;
import edu.mx.utdelacosta.service.IDosificacionComentarioService;
import edu.mx.utdelacosta.service.IDosificacionService;
import edu.mx.utdelacosta.service.IModuloService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IProrrogaService;
import edu.mx.utdelacosta.service.ISesionesService;
import edu.mx.utdelacosta.service.ISubmoduloService;
import edu.mx.utdelacosta.service.ITutoriaIndividualService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@SessionAttributes
public class HomeController {

	@Autowired
	private IModuloService modulosService;

	@Autowired
	private ISubmoduloService submodulosService;
	
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private ISesionesService sesionService;
	
	@Autowired
	private IPeriodosService periodosService;
	
	@Autowired
	private IBajaService bajaService;
	
	@Autowired
	private ITutoriaIndividualService tutoriaIndService;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired 
	private IProrrogaService prorrogaService;

	@Autowired
	private IDosificacionService dosificacionService;
	
	@Autowired
	private IDosificacionComentarioService dosiComentaService;

	@Autowired
	private IDosificacionCargaService dosiCargaService;

	@GetMapping("/index")
	public String mostrarHome(Model model, HttpSession session) {
		// Como el usuario ya ingreso, ya podemos agregar a la session el objeto
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		int moduloPreferido = usuario.getPreferencias().getIdModulo();
		model.addAttribute("moduloDefecto", modulosService.buscarPorId(moduloPreferido).getCveModulo());
		model.addAttribute("submoduloDefecto", submodulosService.getPorDefecto(moduloPreferido));
		model.addAttribute("submodulos", submodulosService.buscarPorModuloYEstatus(moduloPreferido, true));
		return "index";
	}

	@GetMapping("/index/{modulo}/{sub}")
	public String cargarHome(Model model, @PathVariable("modulo") int modulo,
			@PathVariable(name = "sub", required = false) int sub, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		//Verificamos acceso a modulo y si el submodulo es del modulo lo redirigimos
		if((usuario.getRoles().get(0).getId()!=6 && !modulosService.hasAccess(usuario.getId(), modulo)) || (!submodulosService.existeRelacion(modulo, sub) && modulo>0 && sub>0)) {
			return "redirect:/index";
		}
		model.addAttribute("moduloDefecto", modulosService.buscarPorId(modulo).getCveModulo());
		// comparar si tiene la autoridad
		model.addAttribute("submoduloDefecto", sub > 0 ? submodulosService.buscarPorId(sub) : submodulosService.getPorDefecto(modulo));
		model.addAttribute("submodulos", submodulosService.buscarPorModuloYEstatus(modulo, true));
		return "index";
	}
	
	@GetMapping("/")
	public String redirectIndex(Principal principal) {
		if (principal != null) {
			return "redirect:/index";
		}
		return "redirect:/";
	}

	@ModelAttribute
	public void setGenericos(Model model, HttpSession session, HttpServletRequest request, Authentication authentication) {
		String username = authentication.getName();
		Usuario usuario = null;
		if (session.getAttribute("usuario") == null){
			//Insertar la sesion
			usuario = serviceUsuarios.buscarPorUsuario(username);
			session.setAttribute("usuario", usuario);
			int cvePersona;
		    try {
		        cvePersona = (Integer) session.getAttribute("cvePersona");
		    } catch (Exception e) {
		        cvePersona = usuario.getPersona().getId();
		    }
		    session.setAttribute("cvePersona", cvePersona);
		    session.setAttribute("nombrePersona", usuario.getPersona().getNombre());
			//Insertar en bitacora de sesion
			Sesion sesion = new Sesion(usuario, request.getRemoteAddr(), session.getId(), new Date(), request.getRequestURL().toString());
			sesionService.guardar(sesion);
		}
		usuario = (Usuario) session.getAttribute("usuario");
		int rol = usuario.getRoles().get(0).getId();
		//notificaciones 
		model.addAttribute("bajasDirector", rol == 3 ? bajaService.buscarPorPersonaYEstatus(usuario.getPersona().getId(), 0).size():0);
		model.addAttribute("bajasEscolares", rol == 5 ? bajaService.buscarPorTipoYStatus(1, 1).size():0);
		model.addAttribute("apTutorias", rol == 1 ? tutoriaIndService.buscarPorAlumnoYValidada(alumnoService.buscarPorPersona(new Persona(usuario.getPersona().getId())), false).size():0);
		model.addAttribute("periodos", periodosService.buscarTodos());
		model.addAttribute("observacionesP", dosiComentaService.contarPorProfesorYPeriodo(usuario.getPersona().getId(), usuario.getPreferencias().getIdPeriodo()));
		model.addAttribute("dosificacionesP", dosiCargaService.contarNoEntregadas(usuario.getPersona().getId(), usuario.getPreferencias().getIdPeriodo()));
		model.addAttribute("prorrogas", prorrogaService.contarProrrogasPendientesPorPersonaYPeriodo(usuario.getPersona().getId(), usuario.getPreferencias().getIdPeriodo()));
		model.addAttribute("dosificaciones", dosificacionService.contarPendientesPorPersonaCarreraYPeriodo(usuario.getPersona().getId(), usuario.getPreferencias().getIdPeriodo()));
		model.addAttribute("modulos", modulosService.buscarModulosPorRol(rol));
		model.addAttribute("periodos", periodosService.buscarTodos());
	}
	
    
	/*
	 * InitBinder para String, si los detecta vacios en el Data Binding los settea a
	 * NULL
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}