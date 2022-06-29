package edu.mx.utdelacosta.model.dto;

import java.util.List;

import edu.mx.utdelacosta.model.dtoreport.CalificacionesMateriasParcialesDTO;

public class AlumnoCalificacionDTO {
	private Integer id;
	
	private String nombre;
	
	private String matricula;
	
	List<CalificacionDTO> calificaciones;
	
	List<CalificacionesMateriasParcialesDTO> calificacionesMaterias;
	
	private float calificacionTotal;
	
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getCalificacionTotal() {
		return calificacionTotal;
	}

	public void setCalificacionTotal(float calificacionTotal) {
		this.calificacionTotal = calificacionTotal;
	}
	
	public AlumnoCalificacionDTO() {
		
	}

	public List<CalificacionDTO> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<CalificacionDTO> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public List<CalificacionesMateriasParcialesDTO> getCalificacionesMaterias() {
		return calificacionesMaterias;
	}

	public void setCalificacionesMaterias(List<CalificacionesMateriasParcialesDTO> calificacionesMaterias) {
		this.calificacionesMaterias = calificacionesMaterias;
	}	
	
}
