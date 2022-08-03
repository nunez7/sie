package edu.mx.utdelacosta.model.dto;

import java.util.List;

import edu.mx.utdelacosta.model.TutoriaIndividual;

public class TutoriasProgramadasDTO {

	private Integer idAlumno;
	
	private String NombreAlumno;

	private String matricula;
	
	private String fechaProgramada;
	
	private List<TutoriaIndividual> tutoriaIndividuales;
	
	private Integer EstadoAlumno;

	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getNombreAlumno() {
		return NombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno) {
		NombreAlumno = nombreAlumno;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFechaProgramada() {
		return fechaProgramada;
	}

	public void setFechaProgramada(String fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}

	public List<TutoriaIndividual> getTutoriaIndividuales() {
		return tutoriaIndividuales;
	}

	public void setTutoriaIndividuales(List<TutoriaIndividual> tutoriaIndividuales) {
		this.tutoriaIndividuales = tutoriaIndividuales;
	}

	public Integer getEstadoAlumno() {
		return EstadoAlumno;
	}

	public void setEstadoAlumno(Integer estadoAlumno) {
		EstadoAlumno = estadoAlumno;
	}
	
}
