package edu.mx.utdelacosta.controller;

import java.util.Date;
import java.util.List;
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
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;
import edu.mx.utdelacosta.service.IGrupoService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Servicios Escolares')")
@RequestMapping("/alumno-grupo")
public class AlumnoGrupoController {

	@Autowired
	private IAlumnoGrupoService alumnoGrupoService;
	
	@Autowired
	private IGrupoService grupoService;

	@PreAuthorize("hasAnyAuthority('Administrador', 'Informatica', 'Rector', 'Servicios Escolares')")
	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardar(@RequestBody Map<String, String> obj) {
		AlumnoGrupo alumnoGrupo = null;
		if (Integer.parseInt(obj.get("idAlGr")) > 0) {
			alumnoGrupo = alumnoGrupoService.buscarPorId(Integer.parseInt(obj.get("idAlGr")));
		} else {
			alumnoGrupo = new AlumnoGrupo();
			alumnoGrupo.setAlumno(new Alumno(Integer.parseInt(obj.get("idAl"))));
			alumnoGrupo.setActivo(true);
		}
		Grupo grupo = grupoService.buscarPorId(Integer.parseInt(obj.get("idGr")));
		Integer totalAlumno = alumnoGrupoService.contarAlumnosGruposPorGrupo(Integer.parseInt(obj.get("idGr")));
		if (totalAlumno == grupo.getCapacidadMaxima()) {
			return "limit";
		}
		alumnoGrupo.setGrupo(grupo);
		alumnoGrupo.setFechaAlta(new Date());
		alumnoGrupoService.guardar(alumnoGrupo);
		return "ok";
	}

	@PostMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editar(@RequestBody Map<String, String> obj, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Boolean valor = Boolean.parseBoolean(obj.get("estatus"));
		if (valor == false) {
			Integer idAlumno;
			try {
				idAlumno = Integer.parseInt(obj.get("id"));
			} catch (Exception e) {
				idAlumno = 0;
			}
			if (idAlumno>0) {
				
			// para consultar si ya hay un registro de alumno grupo activo
			List<AlumnoGrupo> grupos = alumnoGrupoService.buscarPorAlumnoYPeriodo(idAlumno,
					usuario.getPreferencias().getIdPeriodo());
			// compara si tiene un grupo activo en ese periodo para no dejar activar otro
			if (grupos.size() > 0) {
				return "fail";
			}
			}
		}
		// si no tiene grupo activo
		AlumnoGrupo alumnoGrupo = alumnoGrupoService.buscarPorId(Integer.parseInt(obj.get("id")));
		alumnoGrupo.setActivo(!valor);
		alumnoGrupoService.guardar(alumnoGrupo);
		return "ok";
	}

}
