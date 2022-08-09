package edu.mx.utdelacosta.model.dto;

import java.util.List;

import edu.mx.utdelacosta.model.DosificacionTema;

public class DosificacionUnidadDTO {
	
	private String unidad;
	
	private Integer consecutivo;
	
	private List<DosificacionTema> temas;

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	public List<DosificacionTema> getTemas() {
		return temas;
	}

	public void setTemas(List<DosificacionTema> temas) {
		this.temas = temas;
	}
	
	

}
