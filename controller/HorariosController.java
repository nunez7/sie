package edu.mx.utdelacosta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mx.utdelacosta.model.Actividad;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.model.dto.SesionDTO;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IHorarioService;
import edu.mx.utdelacosta.service.IPersonaService;
import edu.mx.utdelacosta.service.IUsuariosService;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Rector') and hasRole('Informatica') and hasRole('Director') and hasRole('Asistente')")
@RequestMapping("/horarios")
public class HorariosController {
	
	@Autowired
	private ICargaHorariaService cargaHorariaService;
	
	@Autowired
	private IHorarioService horarioService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@Autowired
	private IPersonaService personaService;
	
	@PostMapping(value = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String guardarHorario (@RequestBody SesionDTO horarioDTO, HttpSession session) throws ParseException {
		//se crea el objeto de horario
		Horario horario = null;
		//se crea objeto de carga horaria 
		CargaHoraria cargaHoraria = cargaHorariaService.buscarPorIdCarga(horarioDTO.getCargaHoraria());
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		String horaInicioDTO = horarioDTO.getHoraInicio()+":00";
		String horaFinDTO = horarioDTO.getHoraFin()+":00";
		if(horarioDTO.getIdHorario() > 0) {
			Horario horarioProfesor = horarioService.buscarPorHoraInicioHorafinYPeriodoYProfesorYDia(horaInicioDTO, horaFinDTO, usuario.getPreferencias().getIdPeriodo(), cargaHoraria.getProfesor().getId(), horarioDTO.getDia());
			if(horarioProfesor != null) {
				return "hp";
			}
			Horario horarioGrupo = horarioService.buscarPorHoraInicioHoraFinYGrupoYdia(horaInicioDTO, horaFinDTO, cargaHoraria.getGrupo().getId(), horarioDTO.getDia());
			if(horarioGrupo != null) {
				return "hg";
			}
			horario = horarioService.buscarPorId(horarioDTO.getIdHorario());
			horario.setActivo(horarioDTO.getActivo());
		}
		else {
			Horario horarioProfesor = horarioService.buscarPorHoraInicioHorafinYPeriodoYProfesorYDia(horaInicioDTO, horaFinDTO, usuario.getPreferencias().getIdPeriodo(), cargaHoraria.getProfesor().getId(), horarioDTO.getDia());
			if(horarioProfesor != null) {
				return "hp";
			}
			Horario horarioGrupo = horarioService.buscarPorHoraInicioHoraFinYGrupoYdia(horaInicioDTO, horaFinDTO, cargaHoraria.getGrupo().getId(), horarioDTO.getDia());
			if(horarioGrupo != null) {
				return "hg";
			}
			horario = new Horario();
			horario.setActivo(true);
		}
		horario.setCargaHoraria(cargaHoraria);
		Actividad actividad = new Actividad(horarioDTO.getActividad());
		horario.setActividad(actividad);
		Dia dia = new Dia(horarioDTO.getDia());
		horario.setDia(dia);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			Date horaInicio = sdf.parse(horarioDTO.getHoraInicio());
			Date horaFin = sdf.parse(horarioDTO.getHoraFin());
			horario.setHoraInicio(horaInicio);
			horario.setHoraFin(horaFin);
		} catch (ParseException e) {
			System.err.println("Error de parseo: " + e);
		}
		horarioService.guardar(horario);
		return "ok";
	}

}
