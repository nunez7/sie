package edu.mx.utdelacosta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.utdelacosta.model.NivelEstudio;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;
import edu.mx.utdelacosta.model.Puesto;
import edu.mx.utdelacosta.model.Rol;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.UsuarioPreferencia;
import edu.mx.utdelacosta.model.dto.PersonalDTO;
import edu.mx.utdelacosta.service.IAreaService;
import edu.mx.utdelacosta.service.INivelEstudioService;
import edu.mx.utdelacosta.service.IPersonalService;
import edu.mx.utdelacosta.service.IPuestoService;
import edu.mx.utdelacosta.service.IRolService;
import edu.mx.utdelacosta.service.ISexosService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.SubirArchivo;

@Controller
@PreAuthorize("hasRole('Administrador') OR hasRole('R.Humanos') OR hasRole('Informatica')")
@RequestMapping("/personal")
public class PersonalController {

	@Autowired
	private IPersonalService personalService;

	@Autowired
	private IPuestoService puestoService;

	@Autowired
	private IAreaService areaService;

	@Autowired
	private ISexosService sexoService;

	@Autowired
	private INivelEstudioService nivelService;

	@Autowired
	private IRolService rolService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IUsuariosService usuariosService;
	
	@Value("${siestapp.ruta.docs}")
	private String rutaDocs;

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("personales", personalService.buscarTodosPorNombre());
		model.addAttribute("puestos", puestoService.buscarTodos());
		model.addAttribute("jefes", personalService.buscarJefes());
		model.addAttribute("sexos", sexoService.buscarTodos());
		model.addAttribute("nivelesEstudio", nivelService.buscarTodos());
		model.addAttribute("roles", rolService.buscarTodos());
		return "personal/list";
	}

	@PostMapping("/updatepuesto")
	@ResponseBody
	public String updatePuesto(@RequestBody PersonalDTO dto) {
		Personal personal = personalService.buscarPorId(dto.getIdPersonal());
		if (personal != null) {
			personal.setPuesto(new Puesto(dto.getIdPuesto()));
			personalService.guardar(personal);
		}
		return "up";
	}

	@PostMapping("/updatejefe")
	@ResponseBody
	public String updateJefe(@RequestBody PersonalDTO dto) {
		Personal personal = personalService.buscarPorId(dto.getIdPersonal());
		if (personal != null) {
			personal.setJefePersona(new Persona(dto.getIdJefe()));
			personalService.guardar(personal);
		}
		return "up";
	}

	@PostMapping("/coordina")
	@ResponseBody
	public String coordina(@RequestBody PersonalDTO dto) {
		Personal personal = personalService.buscarPorId(dto.getIdPersonal());
		if (personal != null) {
			personal.setCoordinador(dto.isCoordinadorEstadia());
			personalService.guardar(personal);
		}
		return "up";
	}

	@PostMapping("/esjefe")
	@ResponseBody
	public String esjefe(@RequestBody PersonalDTO dto) {
		Personal personal = personalService.buscarPorId(dto.getIdPersonal());
		if (personal != null) {
			personal.setJefe(dto.isJefe());
			personalService.guardar(personal);
		}
		return "up";
	}

	@GetMapping("/search/{id}")
	public ResponseEntity<Personal> leer(@PathVariable("id") int id) {
		Personal personal = personalService.buscarPorId(id);
		if (personal == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(personal);
		}
	}

	@PostMapping(path="/save", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String save(@RequestParam("expediente")  MultipartFile file, @ModelAttribute PersonalDTO dto) {
		Personal personal = null;
		Persona persona = null;
		String expedienteAnterior =null;
		if (dto.getIdPersonal() > 0) {
			personal = personalService.buscarPorId(dto.getIdPersonal());
			persona = personal.getPersona();
			expedienteAnterior = personal.getExpediente();
		}
		if (personal == null) {
			personal = new Personal();
			persona = new Persona();
			persona.setFechaAlta(new Date());
			// Creamos el usuario
			Usuario user = new Usuario();
			user.setActivo(true);
			user.setFechaAlta(new Date());
			user.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
			user.setUsuario(dto.getUsuario());
			// Asignamos Roles
			List<Rol> roles = new ArrayList<>();
			roles.add(new Rol(dto.getIdRol()));
			user.setRoles(roles);
			// Asignamos preferencias
			UsuarioPreferencia up = new UsuarioPreferencia(5, 9, 10);
			up.setUsuario(user);
			user.setPreferencias(up);
			user.setPersona(persona);
			List<Usuario> usuarios = new ArrayList<>();
			usuarios.add(user);
			persona.setUsuarios(usuarios);
		}
		// Si hay una imagen la guardamos
		if (!file.isEmpty()) {
			String nombreExpediente = SubirArchivo.guardarArchivo(file, rutaDocs+"/personal/expediente/");
			if (nombreExpediente != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				personal.setExpediente(nombreExpediente);
				//Borramos el archivo anterior
				if(dto.getIdPersonal()>0 && expedienteAnterior!=null) {
					SubirArchivo.borrarArchivo(rutaDocs+"/personal/expediente/"+expedienteAnterior);
				}
			}
		}else {
			personal.setExpediente(null);
		}
		// Datos de persona
		persona.setNivelEstudio(new NivelEstudio(dto.getNivelEstudio()));
		persona.setEmail(dto.getEmail());
		persona.setNombre(dto.getNombre());
		persona.setPrimerApellido(dto.getPrimerApellido());
		persona.setSegundoApellido(dto.getSegundoApellido());
		persona.setSexo(dto.getSexo());

		// Datos de personal
		personal.setExtracto(dto.getExtracto());
		personal.setActivo(dto.isActivo());
		personal.setCoordinador(dto.isCoordinadorEstadia());
		personal.setExtension(dto.getExtension());
		personal.setJefe(dto.isJefe());
		personal.setJefePersona(new Persona(dto.getIdJefe()));
		personal.setNoEmpleado(dto.getNoEmpleado());
		personal.setPuesto(new Puesto(dto.getIdPuesto()));
		personal.setPersona(persona);
		personalService.guardar(personal);
		return "ok";
	}

	@GetMapping("/puestos")
	public String puesos(Model model) {
		model.addAttribute("puestos", puestoService.buscarTodos());
		return "personal/puestos";
	}

	@GetMapping("/areas")
	public String areas(Model model) {
		model.addAttribute("areas", areaService.buscarTodas());
		model.addAttribute("jefes", personalService.buscarJefes());
		return "personal/areas";
	}

	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		model.addAttribute("usuarios", usuariosService.buscarUltimos10());
		model.addAttribute("roles", rolService.buscarTodos());
		model.addAttribute("personales", personalService.buscarTodosPorNombre());
		return "personal/usuarios";
	}

	@GetMapping("/manual")
	public String manual(Model model) {
		model.addAttribute("manual", "admin_usuarios.pdf");
		return "personal/manual";
	}
}
