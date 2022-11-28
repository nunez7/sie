package edu.mx.utdelacosta.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import edu.mx.utdelacosta.model.dto.HorarioDiaDTO;
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
		
		//para validar la carga horaria y dia que no sean nulos
		CargaHoraria cargaHoraria;
		Dia dia;
		try {
			cargaHoraria = cargaHorariaService.buscarPorIdCarga(horarioDTO.getCargaHoraria());
		}catch (Exception e) {
			cargaHoraria = null;
		}
		
		try {
			dia = diaService.buscarPorId(horarioDTO.getDia());
		}catch (Exception e) {
			dia = null;
		}
		
		if(cargaHoraria == null || dia == null) {
			return "null";
		}
		
		//se crea objeto de carga horaria 
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
			//para cuando es profesor de receso
			if(cargaHoraria.getProfesor().getId() != 12915) {
				Horario horarioProfesor = horarioService.buscarPorHoraInicioHorafinYPeriodoYProfesorYDia(horaInicioDTO, horaFinDTO, usuario.getPreferencias().getIdPeriodo(), cargaHoraria.getProfesor().getId(), horarioDTO.getDia());
				if(horarioProfesor != null) {
					return "hp";
				}
			}
			Horario horarioGrupo = horarioService.buscarPorHoraInicioHoraFinYGrupoYdia(horaInicioDTO, horaFinDTO, cargaHoraria.getGrupo().getId(), horarioDTO.getDia());
			if(horarioGrupo != null) {
				return "hg";
			}
		}
		else {
			horaInicioDTO = horarioDTO.getHoraInicio()+":00";
			horaFinDTO = horarioDTO.getHoraFin()+":00"; 
			if(cargaHoraria.getProfesor().getId() != 12915) {
				Horario horarioProfesor = horarioService.buscarPorHoraInicioHorafinYPeriodoYProfesorYDia(horaInicioDTO, horaFinDTO, usuario.getPreferencias().getIdPeriodo(), cargaHoraria.getProfesor().getId(), horarioDTO.getDia());
				if(horarioProfesor != null) {
					return "hp";
				}
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
		horario.setDia(dia);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			Date horaInicio = sdf.parse(horarioDTO.getHoraInicio());
			Date horaFin = sdf.parse(horarioDTO.getHoraFin());
			horario.setHoraInicio(horaInicio);
			horario.setHoraFin(horaFin);
		} catch (ParseException e) {
			//logger.log(Level.WARNING, "Error de parseo: " + e);
		}
		horarioService.guardar(horario);
		return "ok";
	}
	
	@GetMapping(path = "/horario-grupal/{id}")
	public String cargarHorario(@PathVariable("id") int cveGrupo, Model model) {
		///// ****** proceso de creacion de horario
		List<Dia> dias = diaService.buscarDias();
		model.addAttribute("dias", dias);
		DateFormat formatoVista = new SimpleDateFormat("HH:mm");
		   DateFormat formatoBusqueda = new SimpleDateFormat("HH:mm:ss");
		   //se buscan las horas de cada dia del profesor
		   List<Horario> horas = horarioService.buscarPorGrupoDistinctPorHoraInicio(cveGrupo);
		   //se crean las listas de datos
		   List<HorarioDiaDTO> horarios = new ArrayList<>();
		   for (Horario hora : horas) {
			HorarioDiaDTO horario = new HorarioDiaDTO();
			// se agregan las fechas y horas de inicio
			horario.setHoraInicio(formatoVista.format(hora.getHoraInicio()));
		    horario.setHoraFin(formatoVista.format(hora.getHoraFin()));
		    // se crea la lista de horas por dia y hora
		    List<HorarioDTO> diasClase = new ArrayList<>();
		    for (Dia dia : dias) {
		    	HorarioDTO horarioDia = null;
		    	//se busca el horario por dia y hora y se agregan al DTO 
		    	horarioDia = horarioService.buscarPorHoraInicioDiaYGrupo(formatoBusqueda.format(hora.getHoraInicio()), dia.getId(), cveGrupo);
		    	diasClase.add(horarioDia);
		    }
		    horario.setHorarios(diasClase);
		    horarios.add(horario);
		   }		
		   
		   model.addAttribute("horarios", horarios);
		
		model.addAttribute("horarios", horarios);
		return "asistente/grupos :: horario";
	}

	@GetMapping("/horario-profesor/{id}")
	public String horarioProfesor(@PathVariable("id") int cveProfesor, Model model, HttpSession session) {
		// se construye la persona y usuario por la sesion
		int cvePersona = (Integer) session.getAttribute("cvePersona");
		Persona persona = personaService.buscarPorId(cvePersona);
		Usuario usuario = usuariosService.buscarPorPersona(persona);
		List<Dia> dias = diaService.buscarDias();
		DateFormat formatoVista = new SimpleDateFormat("HH:mm");
		DateFormat formatoBusqueda = new SimpleDateFormat("HH:mm:ss");
		List<Horario> horas = horarioService.buscarPorProfesorDistinctPorHoraInicio(cveProfesor, usuario.getPreferencias().getIdPeriodo());
		List<HorarioDiaDTO> horarios = new ArrayList<>();			   
		for (Horario hora : horas) {
			HorarioDiaDTO horario = new HorarioDiaDTO();
			horario.setHoraInicio(formatoVista.format(hora.getHoraInicio()));
		    horario.setHoraFin(formatoVista.format(hora.getHoraFin()));
		    List<HorarioDTO> diasClase = new ArrayList<>();
		    for (Dia dia : dias) {
		    	HorarioDTO horarioDia = null;
		    	horarioDia = horarioService.buscarPorHoraInicioDiaYProfesor(formatoBusqueda.format(hora.getHoraInicio()), dia.getId(),cveProfesor, usuario.getPreferencias().getIdPeriodo());
		    	diasClase.add(horarioDia);
		    }
		    horario.setHorarios(diasClase);
		    horarios.add(horario);
		   }
		model.addAttribute("horarios", horarios);
		
		model.addAttribute("dias", dias);
		return "asistente/profesores :: horario";
	}

}
