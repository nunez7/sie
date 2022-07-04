package edu.mx.utdelacosta.model.dtoreport;

import java.util.List;

import edu.mx.utdelacosta.model.dto.CalificacionDTO;

public class CalificacionesMateriasParcialesDTO {
	
	List<CalificacionDTO> calificaciones;
	
	private float calificacionTotal;
	
	private String status;
	
	private String nombreMateria;
	
	public CalificacionesMateriasParcialesDTO() {
		
	}

	public List<CalificacionDTO> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<CalificacionDTO> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public float getCalificacionTotal() {
		return calificacionTotal;
	}

	public void setCalificacionTotal(float calificacionTotal) {
		this.calificacionTotal = calificacionTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	} 
	
	
	
	

}
