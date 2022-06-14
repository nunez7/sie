package edu.mx.utdelacosta.model.dto;

import java.util.List;

public class CalificacionMateriaDTO {
	
	private Integer id;
	
	private String abreviatura;
	
	private Integer calificacion;
	
	private String status;
	
	// agregado Raul 14/06/2022
	private String nombre;
	
	private List<CalificacionCorteDTO> cortes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Integer getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	// agregado Raul 14/06/2022
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CalificacionCorteDTO> getCortes() {
		return cortes;
	}

	public void setCortes(List<CalificacionCorteDTO> cortes) {
		this.cortes = cortes;
	}
	
}
