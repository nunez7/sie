package edu.mx.utdelacosta.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.utdelacosta.model.AreaConocimiento;
import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.PlanEstudio;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.MateriaDTO;
import edu.mx.utdelacosta.service.IAreaConocimientoService;
import edu.mx.utdelacosta.service.ICuatrimestreService;
import edu.mx.utdelacosta.service.IMateriasService;
import edu.mx.utdelacosta.service.IPlanEstudioService;
import edu.mx.utdelacosta.service.IUsuariosService;
import edu.mx.utdelacosta.util.SubirArchivo;

@Controller
@RequestMapping("/materia")
public class MateriasController {
	
	//Inyectando desde properties
	@Value("${siestapp.ruta.docs}")
	private String ruta;
	
	@Autowired
	private IMateriasService materiasService;
	
	@Autowired
	private IPlanEstudioService planEstudioService;
	
	@Autowired 
	private ICuatrimestreService cuatrimestreService;
	
	@Autowired
	private IAreaConocimientoService areaConocimientoService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@GetMapping("/get/{id}")
	public String obtenerMateria(@PathVariable("id") int id, Model model, HttpSession session, Authentication auth) {
		//nos traemos el usuario de la session 
		String username = auth.getName();
		Usuario usuario = usuariosService.buscarPorUsuario(username);
		int idPersona = usuario.getPersona().getId();
		//se trae la materia de acuerdo al id
		Materia materia = materiasService.buscarPorId(id);
		//planes de estudio de acuerdo a la carrera de la sesión 
		List<PlanEstudio> planesEstudio = planEstudioService.buscarPorPersonaCarrera(idPersona);
		//lista de cuatrimestres
		List<Cuatrimestre> cuatrimestres = cuatrimestreService.buscarTodos();
		//lista de areas de conocimiento
		List<AreaConocimiento> areas = areaConocimientoService.buscarAreasActivas();
		model.addAttribute("unidades", materia.getUnidadesTematicas());
		model.addAttribute("areas", areas);
		model.addAttribute("cuatrimestres", cuatrimestres);
		model.addAttribute("planesEst", planesEstudio);
		model.addAttribute("materia", materia);
		return "asistente/editar_materia";
	}
	
	@PostMapping(path = "/agregar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String agregar(@RequestParam("hojaAsignatura") MultipartFile multiPart, @ModelAttribute MateriaDTO materiaDTO) {
		PlanEstudio plan = new PlanEstudio(materiaDTO.getPlanEstudio());
		Materia nomMateria = materiasService.buscarPorPlanEstudioYNombre(plan, materiaDTO.getNombre());
		Materia abrevMateria = materiasService.buscarPorPlanEstudioYAbreviatura(plan, materiaDTO.getAbreviatura());
		// comparamos si ya existe el nombre o abreviatura en una materia
		if (nomMateria == null) {
			if (abrevMateria == null) {
				// regresar un mensaje de que ya esta en uso
				Materia materia = new Materia();
				// Si hay una imagen la guardamos
				if (!multiPart.isEmpty()) {
					String nombreArchivo = SubirArchivo.guardarArchivo(multiPart, ruta+"/materias/");
					if (nombreArchivo != null) { // La imagen si se subio
						// Procesamos la variable nombreImagen
						materia.setHojaAsignatura(nombreArchivo);
					}
				}
				// insertamos los atributos a la materia
				materia.setAbreviatura(materiaDTO.getAbreviatura());
				materia.setActivo(true);
				// buscamos y construimos el area conocimientos
				AreaConocimiento area = new AreaConocimiento(materiaDTO.getAreaConocimiento()); 
				materia.setAreaConocimiento(area);
				materia.setCalificacion(materiaDTO.isCalificacion());
				materia.setCompetencia(null);
				// buscamos y construimos el cuatrimestre
				Cuatrimestre cuatrimestre = new Cuatrimestre(materiaDTO.getCuatrimestre());
				materia.setCuatrimestre(cuatrimestre);
				materia.setCurricular(materiaDTO.isCurricular());
				materia.setExtracurricular(false);
				materia.setFechaAlta(new Date());
				materia.setHorasPractica(materiaDTO.getHorasPracticas());
				materia.setHorasSemanales(materiaDTO.getHorasSemanales());
				materia.setHorasTeoria(materiaDTO.getHorasTeoria());
				// proceso para sumar horas totales
				int horasTotales = 0;
				horasTotales = materiaDTO.getHorasPracticas() + materiaDTO.getHorasTeoria();
				materia.setHorasTotales(horasTotales);
				materia.setIntegradora(materiaDTO.isIntegradora());
				materia.setNombre(materiaDTO.getNombre());
				materia.setObjetivo(null);
				// buscamos y construimos el plan de estudio
				materia.setPlanEstudio(plan);
				materiasService.guardar(materia);
				return "ok";
			} else {
				return "abreviatura";
			}
		} else {
			return "nombre";
		}
	}
	
	@PatchMapping(path = "/editar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String editar(@RequestParam("hojaAsignatura") MultipartFile multiPart, @ModelAttribute MateriaDTO materiaDTO) {
		Materia materia = materiasService.buscarPorId(materiaDTO.getIdMateria());
		
		// Si hay una imagen la guardamos
		if (!multiPart.isEmpty()) {
			//para borrar registro de la hoja de asignatura
			if(materia.getHojaAsignatura() != null) {
				SubirArchivo.borrarArchivo(ruta+"/materias/"+materia.getHojaAsignatura());
			}
			String nombreArchivo = SubirArchivo.guardarArchivo(multiPart, ruta+"/materias/");
			if (nombreArchivo != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				materia.setHojaAsignatura(nombreArchivo);
			}
		}
		// insertamos los atributos a la materia
		materia.setAbreviatura(materiaDTO.getAbreviatura());
		// buscamos y construimos el area conocimientos
		AreaConocimiento area = new AreaConocimiento(materiaDTO.getAreaConocimiento());
		materia.setAreaConocimiento(area);
		materia.setActivo(materiaDTO.isActivo());
		materia.setCalificacion(materiaDTO.isCalificacion());
		// buscamos y construimos el cuatrimestre
		Cuatrimestre cuatrimestre = new Cuatrimestre(materiaDTO.getCuatrimestre());
		materia.setCuatrimestre(cuatrimestre);
		materia.setCurricular(materiaDTO.isCurricular());
		materia.setExtracurricular(false);
		materia.setFechaAlta(new Date());
		materia.setHorasPractica(materiaDTO.getHorasPracticas());
		materia.setHorasSemanales(materiaDTO.getHorasSemanales());
		materia.setHorasTeoria(materiaDTO.getHorasTeoria());
		// proceso para sumar horas totales
		int horasTotales = 0;
		horasTotales = materiaDTO.getHorasPracticas() + materiaDTO.getHorasTeoria();
		materia.setHorasTotales(horasTotales);
		materia.setIntegradora(materiaDTO.isIntegradora());
		materia.setNombre(materiaDTO.getNombre());
		// buscamos y construimos el plan de estudio
		PlanEstudio planEstudio = new PlanEstudio(materiaDTO.getPlanEstudio());
		materia.setPlanEstudio(planEstudio);
		materiasService.guardar(materia);
		return "ok";
	}
	
	@PostMapping(path = "/cambiar-propiedades", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String cambiarPropiedades(@RequestBody Map<String, String> obj) {
		int idMateria = Integer.parseInt(obj.get("idMateria"));
		String propiedad = obj.get("propiedad");
		Boolean valor = Boolean.valueOf(obj.get("valor"));
		//buscamos la materia por su id
		Materia materia = materiasService.buscarPorId(idMateria);
		//regresamos el valor con un map
		switch(propiedad) {
		case "activo":
			materia.setActivo(!valor);
			break;
		case "integradora":
			materia.setIntegradora(!valor);
			break;
		case "curricular":
			materia.setCurricular(!valor);
			break;
		case "extracurricular":
			materia.setExtracurricular(!valor);
			break;
		case "calificacion":
			materia.setCalificacion(!valor);
			break;
		}
		//se guardan los cambios 
		materiasService.guardar(materia);
		return "ok";
	}
	
	@PatchMapping(path = "/escolares", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String escolares(@RequestBody MateriaDTO dto) {
		Materia materia = materiasService.buscarPorId(dto.getIdMateria());
		materia.setNombre(dto.getNombre());
		materia.setAbreviatura(dto.getAbreviatura());
		materia.setIntegradora(dto.isIntegradora());
		materia.setHorasPractica(dto.getHorasPracticas());
		materia.setHorasTeoria(dto.getHorasTeoria());
		materia.setHorasSemanales(dto.getHorasSemanales());
		materia.setHorasTotales(materia.getHorasPractica()+materia.getHorasTeoria());
		materiasService.guardar(materia);
		return "up";
	}

}
