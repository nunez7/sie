package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.model.dto.SesionDTO;
import edu.mx.utdelacosta.service.ICargaHorariaService;
import edu.mx.utdelacosta.service.IDiaService;
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
	
	@Autowired
	private IDiaService diaService;
	
	Logger logger = Logger.getLogger(HorariosController.class.getName());
	
	
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
		String horaInicioDTO = "";
		String horaFinDTO = "";
		if(horarioDTO.getIdHorario() > 0) {
			horario = horarioService.buscarPorId(horarioDTO.getIdHorario());
			//para desactivar el horario
			if(!horarioDTO.getActivo()) {
				horario = horarioService.buscarPorId(horarioDTO.getIdHorario());
				horario.setActivo(horarioDTO.getActivo());
				horarioService.guardar(horario);
				return "baja";
			}
			horaInicioDTO = horarioDTO.getHoraInicio();
			horaFinDTO = horarioDTO.getHoraFin();
			//cuando se va editar el horario
			if(horarioDTO.getHoraInicio().length() == 5) {
				horaInicioDTO = horaInicioDTO+":00";
			}
			if(horarioDTO.getHoraFin().length() == 5) {
				horaFinDTO = horaFinDTO+":00";
			}
			Horario horarioProfesor = horarioService.buscarPorHoraInicioHorafinYPeriodoYProfesorYDia(horaInicioDTO, horaFinDTO, usuario.getPreferencias().getIdPeriodo(), cargaHoraria.getProfesor().getId(), horarioDTO.getDia());
			if(horarioProfesor != null) {
				return "hp";
			}
			Horario horarioGrupo = horarioService.buscarPorHoraInicioHoraFinYGrupoYdia(horaInicioDTO, horaFinDTO, cargaHoraria.getGrupo().getId(), horarioDTO.getDia());
			if(horarioGrupo != null) {
				return "hg";
			}
		}
		else {
			horaInicioDTO = horarioDTO.getHoraInicio()+":00";
			horaFinDTO = horarioDTO.getHoraFin()+":00"; 
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
			logger.log(Level.WARNING, "Error de parseo: " + e);
		}
		horarioService.guardar(horario);
		return "ok";
	}
	
	@GetMapping(path = "/horario-grupal/{id}")
	public String cargarHorario(@PathVariable("id") int cveGrupo, Model model) {
		///// ****** proceso de creacion de horario
		List<Dia> dias = diaService.buscarDias();
		model.addAttribute("dias", dias);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); // formati de 24hrs
		// Se extrae una lista de las horas que ahi asociadas a cada hora de calse con
		// un disting por hora inicio y hora fin
		List<Horario> horas = horarioService.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
		model.addAttribute("horas", horas);
		// se crea una lista vacia para colocarle los datos de las horas de calse
		List<HorarioDTO> horasDto = new ArrayList<>();
		// crea el horario con las horarios vinculados al grupo
		for (Horario hora : horas) {
			// se formatea la hora de "Date" a "String"
			String horaInicio = dateFormat.format(hora.getHoraInicio());
			String horaFin = dateFormat.format(hora.getHoraFin());
			for (Dia dia : dias) {
				List<Horario> horarios = horarioService.buscarPorHoraInicioDiaYGrupo(horaInicio, dia.getId(), cveGrupo);
				for (Horario horario : horarios) {
					HorarioDTO horaDto = new HorarioDTO();
					if (horario == null) {
						horaDto.setIdHorario(0); // cambios en DTOHorario
						horaDto.setHoraInicio("");
						horaDto.setHoraFin("");
						horaDto.setDia(dia.getDia());
						horaDto.setProfesor("");
						horaDto.setMateria("");
						horaDto.setAbreviaturaMateria("");
						horaDto.setGrupo(""); // cambios en DTOHorario
						horaDto.setIdCargaHoraria(null); // cambios en DTOHorario
						horaDto.setIdActividad(null); // cambios en DTOHorario
						horaDto.setIdDia(null);
						horasDto.add(horaDto);
					} else {
						horaDto.setIdHorario(horario.getId()); // cambios en DTOHorario
						horaDto.setHoraInicio(horaInicio);
						horaDto.setHoraFin(horaFin);
						horaDto.setDia(dia.getDia());
						horaDto.setProfesor(horario.getCargaHoraria().getProfesor().getNombreCompleto());
						horaDto.setMateria(horario.getCargaHoraria().getMateria().getNombre());
						horaDto.setAbreviaturaMateria(horario.getCargaHoraria().getMateria().getAbreviatura());
						horaDto.setGrupo(horario.getCargaHoraria().getGrupo().getNombre()); // cambios en DTOHorario
						horaDto.setIdCargaHoraria(horario.getCargaHoraria().getId());// cambios en DTOHorario
						horaDto.setIdActividad(horario.getActividad().getId());
						horaDto.setIdDia(dia.getId());
						horasDto.add(horaDto);
					}
				}
			}
		}
		model.addAttribute("horasDto", horasDto);
		return "asistente/grupos :: horario";
	}

	@GetMapping("/horario-profesor/{id}")
	public String horarioProfesor(@PathVariable("id") int cveProfesor, Model model, HttpSession session) {
		// se construye la persona y usuario por la sesion
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Dia> dias = diaService.buscarDias();
		// formato para horas
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		// Se extrae una lista de las horas que ahi asociadas a cada hora de calse con
		// un disting por hora inicio y hora fin
		List<Horario> horas = horarioService.buscarPorProfesorDistinctPorHoraInicio(cveProfesor,
				usuario.getPreferencias().getIdPeriodo());
		// se crea una lista vacia para colocarle los datos de las horas de calse
		List<HorarioDTO> horasDto = new ArrayList<>();
		// crea el horario con las horarios vinculados al grupo
		for (Horario hora : horas) {
			// se formatea la hora de "Date" a "String"
			String horaInicio = dateFormat.format(hora.getHoraInicio());
			String horaFin = dateFormat.format(hora.getHoraFin());
			for (Dia dia : dias) {
				List<Horario> horarios = horarioService.buscarPorHoraInicioDiaYProfesor(horaInicio, dia.getId(),
						cveProfesor, usuario.getPreferencias().getIdPeriodo());
				for (Horario horario : horarios) {
					HorarioDTO horaDto = new HorarioDTO();
					if (horario == null) {
						horaDto.setIdHorario(0); // cambios en DTOHorario
						horaDto.setHoraInicio("");
						horaDto.setHoraFin("");
						horaDto.setDia(dia.getDia());
						horaDto.setProfesor("");
						horaDto.setMateria("");
						horaDto.setAbreviaturaMateria("");
						horaDto.setGrupo(""); // cambios en DTOHorario
						horaDto.setIdCargaHoraria(null); // cambios en DTOHorario
						horaDto.setIdActividad(null); // cambios en DTOHorario
						horaDto.setIdDia(null);
						horasDto.add(horaDto);
					} else {
						horaDto.setIdHorario(horario.getId()); // cambios en DTOHorario
						horaDto.setHoraInicio(horaInicio);
						horaDto.setHoraFin(horaFin);
						horaDto.setDia(dia.getDia());
						horaDto.setProfesor(horario.getCargaHoraria().getProfesor().getNombreCompleto());
						horaDto.setMateria(horario.getCargaHoraria().getMateria().getNombre());
						horaDto.setAbreviaturaMateria(horario.getCargaHoraria().getMateria().getAbreviatura());
						horaDto.setGrupo(horario.getCargaHoraria().getGrupo().getNombre()); // cambios en DTOHorario
						horaDto.setIdCargaHoraria(horario.getCargaHoraria().getId());// cambios en DTOHorario
						horaDto.setIdActividad(horario.getActividad().getId());
						horaDto.setIdDia(dia.getId());
						horasDto.add(horaDto);
					}
				}
			}

		}
		model.addAttribute("dias", dias);
		model.addAttribute("horas", horas);
		model.addAttribute("horasDto", horasDto);
		return "asistente/profesores :: horario";
	}

}
