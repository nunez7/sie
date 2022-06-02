package edu.mx.utdelacosta.model.dto;

import java.util.List;

import edu.mx.utdelacosta.model.CalificacionMateria;

public class AlumnoCalificacionMateriaDTO {
 
 private Integer id;
 
 private String nombreAlumno;
 
 private String matricula;
 
 private List<CalificacionMateria> calificaciones;
 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreAlumno() {
		return nombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public List<CalificacionMateria> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<CalificacionMateria> calificaciones) {
		this.calificaciones = calificaciones;
	}

}