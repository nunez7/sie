package edu.mx.utdelacosta.model.dto;

import java.util.List;

public class CalificacionAlumnoDto {
	private Integer id;
	
	private String nombreInstrumento;
	
	List<AlumnoCalificacionDTO> calificaciones;
	
	private Integer calificacionInstrumento;
	
	private Integer idInstrumento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreInstrumento() {
		return nombreInstrumento;
	}

	public void setNombreInstrumento(String nombreInstrumento) {
		this.nombreInstrumento = nombreInstrumento;
	}

	public Integer getCalificacionInstrumento() {
		return calificacionInstrumento;
	}

	public void setCalificacionInstrumento(Integer calificacionInstrumento1) {
		this.calificacionInstrumento = calificacionInstrumento1;
	}

	public Integer getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Integer idInstrumento1) {
		this.idInstrumento = idInstrumento1;
	}

	public List<AlumnoCalificacionDTO> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<AlumnoCalificacionDTO> calificaciones) {
		this.calificaciones = calificaciones;
	}
	
}
