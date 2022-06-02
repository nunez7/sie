package edu.mx.utdelacosta.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('Administrador') and hasRole('Profesor')")
@RequestMapping("/tutorias")
public class TutorController {
	
	@GetMapping("/tutorias")
	public String tutorias() {
		return "tutorias/gruposTutorados";
	}
	
	@GetMapping("/tutoriaIndividual")
	public String tutoriaIndividual() {
		return "tutorias/tutoriaIndividual";
	}
	
	@GetMapping("/tutoriaGrupal")
	public String tutoriaGrupal() {
		return "tutorias/tutoriaGrupal";
	}
	
	@GetMapping("/detalleTutoriaGrupal")
	public String detalleTutoriaGrupal() {
		return "tutorias/detalleTutoriaGrupal";
	}
	
	@GetMapping("/programacionTutorias")
	public String programacionTutorias() {
		return "tutorias/programacionTutorias";
	}
	
	@GetMapping("/canalizacion")
	public String canalizacion() {
		return "tutorias/canalizacion";
	}
	
	@GetMapping("/informacionEstudiante")
	public String informacionEstudiante() {
		return "tutorias/informacionEstudiante";
	}
	
	@GetMapping("/manual")
	public String manual() {
		return "tutorias/manual";
	}
	
	@GetMapping("/encuestaEvaluacionDocente")
	public String encuestaEvaluacionDocente() {
		return "encuestas/encuestaEvaluacionDocente";
	}
	
	@GetMapping("/encuestaEntrevistaInicial")
	public String encuestaEntrevistaInicial() {
		return "encuestas/encuestaEntrevistaInicial";
	}
	
	@GetMapping("/encuestaEvaluacionTutor")
	public String encuestaEvaluacionTutor() {
		return "encuestas/encuestaEvaluacionTutor";
	}
	
	@GetMapping("/reportesTutoria") 
	 public String reportesTutoria() { 
	  return "tutorias/reportesTutoria"; 
	 } 
	  
	 @GetMapping("/reporteTutoriasGrupales") 
	 public String reporteTutoriasGrupales() { 
	  return "reportes/reporteTutoriaGrupal"; 
	 } 
	  
	 @GetMapping("/reporteTutoriaIndividual") 
	 public String reporteTutoriaIndividual() { 
	  return "reportes/reporteTutoriaIndividual"; 
	 } 
	  
	 @GetMapping("/reporteInformacionEstudiante") 
	 public String reporteInformacionEstudiante() { 
	  return "reportes/reporteInformacionEstudiante"; 
	 } 
	  
	 @GetMapping("/reporteDatosContacto") 
	 public String reporteDatosContacto() { 
	  return "reportes/reporteDatosContacto"; 
	 } 
	 
	 @GetMapping("/reporteEntrevistaInicial") 
	 public String reporteEntrevistaInicial() { 
	  return "reportes/reporteEntrevistaInicial"; 
	 } 
	  
	 @GetMapping("/reporteHorarioClases") 
	 public String reporteHorarioClases() { 
	  return "reportes/reporteHorarioClases"; 
	 }
}
