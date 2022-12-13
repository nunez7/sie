package edu.mx.utdelacosta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoFamiliar;
import edu.mx.utdelacosta.model.DatosPersonales;
import edu.mx.utdelacosta.model.Domicilio;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Municipio;
import edu.mx.utdelacosta.model.NivelEstudio;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;
import edu.mx.utdelacosta.model.Rol;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.UsuarioPreferencia;
import edu.mx.utdelacosta.model.dto.AlumnoDTO;
import edu.mx.utdelacosta.model.dto.PersonaDTO;
import edu.mx.utdelacosta.model.dto.UsuarioDTO;
import edu.mx.utdelacosta.security.DatabaseWebSecurity;
import edu.mx.utdelacosta.service.IAlumnoService;
import edu.mx.utdelacosta.service.ICarrerasServices;
import edu.mx.utdelacosta.service.IEstadoCivilService;
import edu.mx.utdelacosta.service.IEstadoService;
import edu.mx.utdelacosta.service.IGrupoService;
import edu.mx.utdelacosta.service.ILocalidadesService;
import edu.mx.utdelacosta.service.IModuloService;
import edu.mx.utdelacosta.service.IMunicipiosService;
import edu.mx.utdelacosta.service.INivelEstudioService;
import edu.mx.utdelacosta.service.IPeriodosService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IPersonalService;
import edu.mx.utdelacosta.service.ISexosService;
import edu.mx.utdelacosta.service.IUsuarioPreferenciaService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@RequestMapping("/usuario")
public class UsuariosController {

	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private DatabaseWebSecurity context;
	
	@Autowired
	private IModuloService modulosService;
	
	@Autowired
	private ICarrerasServices carrerasService;
	
	@Autowired
	private IPeriodosService periodosService;
	
	@Autowired
	private IUsuarioPreferenciaService servicePreferencias;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private INivelEstudioService nivelService;
	
	@Autowired
	private ISexosService sexoService;
	
	@Autowired
	private IEstadoCivilService estadoCivService;
	
	@Autowired
	private IEstadoService estadosService;
	
	@Autowired
	private IMunicipiosService municipiosService;
	
	@Autowired
	private ILocalidadesService localidadesService;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private IPersonalService personalService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IGrupoService grupoService;

	@PreAuthorize("hasRole('Administrador')")
	@GetMapping("/restablecer-all")
	public void restablecer() {
        PasswordEncoder passwordEncoder = context.passwordEncoder();
		List<Usuario> usuarios = serviceUsuarios.buscarTodos();

		for (int i = 0; i < usuarios.size(); i++) {
			Integer id = (usuarios.get(i).getId());
			String contra = (usuarios.get(i).getContrasenia());
			try {
				contra = passwordEncoder.encode(contra);
			} catch (Exception e) {
				// TODO: handle exception
				contra = passwordEncoder.encode("12345678");
			}
			//System.out.println(id + " " + contra);
			serviceUsuarios.actualizarContrasenia(id, contra);
		}
	}

	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Academia', 'Director')")
    @GetMapping("/vercomo/{dato}")
   	public String vercomo(@PathVariable(name = "dato", required = false) String dato,  Model model) { 
    	List<Usuario> usuarios = new ArrayList<>();
    	if(dato!=null) {
    		usuarios = serviceUsuarios.buscarPorPersonaOUsuario(dato);
    	}else {
    		usuarios = serviceUsuarios.buscarTodos();
    	}
		model.addAttribute("usuarios", usuarios);
		return "fragments/modal-vercomo :: lm-usuarios";
   	}
	
	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector')")
    @GetMapping("/buscauser/{dato}")
   	public String buscaUser(@PathVariable(name = "dato", required = false) String dato,  Model model) { 
		model.addAttribute("usuarios", serviceUsuarios.buscarPorPersonaOUsuario(dato));
		return "personal/usuarios :: list-users";
   	}
    
    @GetMapping("/addsession/{id}")
    @ResponseBody
    public String addSession(@PathVariable("id") int id, HttpSession session) {
    	Usuario user = serviceUsuarios.buscarPorId(id);
    	if(user!=null) {
    		 session.setAttribute("nombrePersona", null);
    		 session.setAttribute("cvePersona", null);
    		 session.setAttribute("cvePersona", user.getPersona().getId());
    		 session.setAttribute("nombrePersona", user.getPersona().getNombre());
    	}
    	return "ok";
    }
    
    @GetMapping("/contrasenia")
    public String cambiarContrasenia(){
    	return "perfil/contrasenia";
    }
    
    @GetMapping("/preferencias")
    public String preferencias(Model model,  HttpSession session){
    	Usuario usuario = (Usuario) session.getAttribute("usuario");
    	int rol = usuario.getRoles().get(0).getId();
		model.addAttribute("modulos", modulosService.buscarModulosPorRol(rol));
		model.addAttribute("periodos", rol == 1 || rol == 2 ? periodosService.buscarLiberados() : periodosService.buscarTodos());
		model.addAttribute("carreras", carrerasService.buscarTodasMenosIngles());
    	return "perfil/preferencias";
    }
    

    @PatchMapping(path="/resetpassword",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String resetpassword(@RequestBody Map<String, String> obj , Authentication authentication) {
    	//Obtener la contrasenia de la cadena 
    	String contrasenia = obj.get("contrasenia");
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);	
    	String updatedPassword = passwordEncoder.encode(contrasenia);
    	serviceUsuarios.actualizarContrasenia(usuario.getId(), updatedPassword);
    	return "ok";
    }
    
    @PatchMapping(path="/changeperiodo",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String changePeriodo(@RequestBody Map<String, Integer> obj ,  Authentication authentication,  HttpSession session) {
    	int periodo = obj.get("periodo");
    	
    	//Usuario usuario = (Usuario) session.getAttribute("usuario"); 
    	
    	/*
    	int cvePersona; 
    	Usuario usuario = new Usuario();
    	try { 
    		cvePersona = (Integer) session.getAttribute("cvePersona"); 
    		usuario = serviceUsuarios.buscarPorPersona(new Persona(cvePersona));
    	} catch (Exception e) { 
    		String username = authentication.getName();
    		cvePersona = usuario.getPersona().getId(); 
    		usuario = serviceUsuarios.buscarPorUsuario(username);
    	} 
    	*/
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);	
    	UsuarioPreferencia up = usuario.getPreferencias();
    	up.setIdPeriodo(periodo);
    	usuario.setPreferencias(up);
    	session.setAttribute("usuario", usuario);
    	servicePreferencias.guardar(up);
    	return "ok";
    }
    
    @PatchMapping(path="/changecarrera",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String changeCarrera(@RequestBody Map<String, Integer> obj ,  Authentication authentication,  HttpSession session) {
    	int carrera = obj.get("carrera");
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);	
    	UsuarioPreferencia up = usuario.getPreferencias();
    	up.setIdCarrera(carrera);
    	usuario.setPreferencias(up);
    	session.setAttribute("usuario", usuario);
    	servicePreferencias.guardar(up);
    	return "career";
    }
    
    @PatchMapping(path="/changepreferences",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String changePreferences(@RequestBody Map<String, Integer> obj ,  Authentication authentication,  HttpSession session) {
    	int carrera = obj.get("carrera");
    	int modulo = obj.get("modulo");
    	int periodo = obj.get("periodo");
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);	
    	UsuarioPreferencia up = usuario.getPreferencias();
    	up.setIdCarrera(carrera);
    	up.setIdModulo(modulo);
    	up.setIdPeriodo(periodo);
    	usuario.setPreferencias(up);
    	session.setAttribute("usuario", usuario);
    	servicePreferencias.guardar(up);
    	return "ok";
    }
    
    @GetMapping("/datos")
    public String datos(Model model, Authentication authentication){
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);
    	//Validamos el rol
    	boolean hasUserRole = authentication.getAuthorities().stream()
    	          .anyMatch(r -> r.getAuthority().equals("Alumno"));
		Alumno alumno = alumnoService.buscarPorPersona(usuario.getPersona());
    	if(hasUserRole) {
        	Grupo ultimoGrupo = grupoService.buscarUltimoDeAlumno(alumno.getId());
        	alumno.setUltimoGrupo(ultimoGrupo);
    	}
    	Personal personal = personalService.buscarPorPersona(usuario.getPersona());
    	
    	Estado estado = new Estado();
    	estado.setId(usuario.getPersona().getDomicilio()!=null ? usuario.getPersona().getDomicilio().getLocalidad().getMunicipio().getEstado().getId():18);
    	Municipio municipio = new Municipio();
    	municipio.setId(usuario.getPersona().getDomicilio()!=null ? usuario.getPersona().getDomicilio().getLocalidad().getMunicipio().getId(): 2293);

    	Estado estadoF = new Estado();
    	estadoF.setId(alumno!=null ? alumno.getFamiliar().getLocalidad().getMunicipio().getEstado().getId():18);
    	Municipio municipioF = new Municipio();
    	municipioF.setId(alumno!=null ? alumno.getFamiliar().getLocalidad().getMunicipio().getId(): 2293);

    	model.addAttribute("alumno", alumno);
    	model.addAttribute("personal",personal);
    	model.addAttribute("persona", usuario.getPersona());
    	model.addAttribute("nivelesEstudio", nivelService.buscarTodos());
    	model.addAttribute("sexos", sexoService.buscarTodos());
    	model.addAttribute("estadosCivil", estadoCivService.buscarTodos());
    	model.addAttribute("estados", estadosService.buscarTodos());
    	model.addAttribute("municipios", municipiosService.buscarPorEstado(estado));
    	model.addAttribute("localidades", localidadesService.buscarPorMunicipio(municipio));
    	model.addAttribute("municipiosF", municipiosService.buscarPorEstado(estadoF));
    	model.addAttribute("localidadesF", localidadesService.buscarPorMunicipio(municipioF));
    	return "perfil/datos";
    }
    
    @PostMapping(path="/datospersonales",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String datosPersonales(@RequestBody PersonaDTO datos,  Authentication authentication) {
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);
    	boolean hasUserRole = authentication.getAuthorities().stream()
  	          .anyMatch(r -> r.getAuthority().equals("Alumno"));
    	if(usuario!=null) {
        	Persona persona = usuario.getPersona();
        	persona.setEmail(datos.getEmail());
        	persona.setSexo(datos.getSexo());
        	persona.setNivelEstudio(new NivelEstudio(datos.getNivelEstudio()));
        	
        	DatosPersonales datosP = persona.getDatosPersonales();
        	//Si no ay datos actualizamos, si ay guardamos un nuevo dato
        	if(datosP==null) {
        		datosP = new DatosPersonales();
        	}

    		datosP.setCelular(datos.getCelular());
        	datosP.setTelefono(datos.getTelefono());
        	datosP.setEstadoCivil(datos.getEstadoCivil());
        	//Si no es alumno puede guardar curp y fecha de nacimiento
    		if(!hasUserRole) {
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    			formatter.setTimeZone(TimeZone.getTimeZone("America/Mazatlan"));
    			Date fechaNac = null;
    			try {
    				fechaNac = formatter.parse(datos.getFechaNacimiento());
    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			datosP.setCurp(datos.getCurp());
    			datosP.setFechaNacimiento(fechaNac);
    			datosP.setEstadoNacimiento(new Estado(18));
    			datosP.setEmailVerificado(false);
    			datosP.setEdad(0);
        	}
    		datosP.setPersona(persona);
    		persona.setDatosPersonales(datosP);
    		personaService.guardar(persona);
    		
    		//Datos personales
    		Personal personal = personalService.buscarPorPersona(persona);
    		if(personal!=null) {
    			personal.setExtension(datos.getExtension());
    			personalService.guardar(personal);
    		}
    	}
    	
    	return "ok";
    }

    @PostMapping(path="/domicilio", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String domicilio(@RequestBody PersonaDTO datos, Authentication authentication) {
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);
    	Persona persona = usuario.getPersona();
    	if(persona!=null) {
        	Domicilio domicilio = persona.getDomicilio();
        	//Si no ay datos actualizamos, si ay guardamos un nuevo dato
        	if(domicilio==null) {
        		domicilio = new Domicilio();
        	}
        	domicilio.setColonia(datos.getColonia());
        	domicilio.setCp(datos.getCp());
        	domicilio.setLocalidad(new Localidad(datos.getLocalidad()));
        	domicilio.setDomicilio(datos.getDomicilio());
        	domicilio.setPersona(persona);
        	persona.setDomicilio(domicilio);
        	personaService.guardar(persona);
    	}
    	return "ok";
    }
    
    @PatchMapping(path="/datosfamilia", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String datosfamilia(@RequestBody AlumnoDTO alumno, Authentication authentication){
    	String username = authentication.getName();
    	Usuario usuario = serviceUsuarios.buscarPorUsuario(username);
    	Alumno alumnoSave = alumnoService.buscarPorPersona(usuario.getPersona());
		AlumnoFamiliar familiar = alumnoSave.getFamiliar();
    	if(familiar!=null) {
    		familiar.setColonia(alumno.getColoniaFamiliar());
    		familiar.setCp(alumno.getCpFamiliar());
    		familiar.setLocalidad(new Localidad(alumno.getIdLocalidadFamiliar()));
    		familiar.setNombre(alumno.getNombreFamiliar());
    		familiar.setTelefono(alumno.getTelefonoFamiliar());
    		familiar.setDomicilio(alumno.getDomicilioFamiliar());
    		//Si existe la relacion no es necesaria la siguiente linea
        	//familiar.setAlumno(alumnoSave);
        	alumnoSave.setFamiliar(familiar);
        	alumnoService.guardar(alumnoSave);
    	}
    	return "ok";
    }
    
    @PostMapping("/check")
    @ResponseBody
    public String check(@RequestBody Map<String, String> obj ) {
    	Usuario userF = serviceUsuarios.buscarPorUsuario(obj.get("usuario"));
    	if(userF!=null) {
    		return "nd";
    	}
    	return "ok";
    }
    
    @PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector')")
    @GetMapping("/unlock/{id}")
    @ResponseBody
	public String activar(@PathVariable("id") int idUsuario) {		
    	serviceUsuarios.activar(idUsuario);	
		return "ok";
	}
    
    @PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector')")
	@GetMapping("/lock/{id}")
	@ResponseBody
	public String bloquear(@PathVariable("id") int idUsuario) {		
		serviceUsuarios.bloquear(idUsuario);	
		return "ok";
	}
	
    @PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector')")
	@GetMapping("/search/{id}")
	public ResponseEntity<Usuario> leer(@PathVariable("id") int id) {
	    Usuario usuario = serviceUsuarios.buscarPorId(id);
	    if (usuario == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(usuario);
	    }
	}
    
    @PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector')")
    @PostMapping("/save")
	@ResponseBody
	public String save(@RequestBody UsuarioDTO dto) {
    	Usuario usuario = null;
    	if(dto.getIdUsuario()>0) {
    		usuario = serviceUsuarios.buscarPorId(dto.getIdUsuario());
    		//Si esta seleccionada la opcion de restablecer
    		if(dto.isRestablecer()) {
    			usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
    		}
    		//Si el rol no es el mismo actualizamos las preferencias
    		if(dto.getIdRol()!=usuario.getRoles().get(0).getId()) {
    			UsuarioPreferencia up = usuario.getPreferencias();
    			up.setIdModulo(10);
    			usuario.setPreferencias(up);
    			List <Rol> roles = usuario.getRoles();
    			roles.set(0, new Rol(dto.getIdRol()));
    			usuario.setRoles(roles);
    		}
    	}
    	//Creamos un user
    	if(usuario==null) {
    		usuario = new Usuario();
    		usuario.setActivo(true);
    		usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
    		usuario.setUsuario(dto.getUsuario());
    		usuario.setFechaAlta(new Date());
			//Asignamos Roles
			List <Rol> roles = new ArrayList<>();
			roles.add(new Rol(dto.getIdRol()));
			usuario.setRoles(roles);
			//Asignamos preferencias
			UsuarioPreferencia up = new UsuarioPreferencia(5, 9, 10);
			up.setUsuario(usuario);
			usuario.setPreferencias(up);
			usuario.setPersona(new Persona(dto.getIdPersona()));
    	}
    	
    	serviceUsuarios.guardar(usuario);
    	return "ok";
    }

	/*@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return texto + " Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
	}*/
    
 // Se utiliza para formatear los date antes de recibir los datos
 	@InitBinder
 	public void initBinder(WebDataBinder webDataBinder) {
 		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
 		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
 	}
}
