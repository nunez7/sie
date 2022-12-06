package edu.mx.utdelacosta.model.dto;

import java.util.List;

public class PreguntaDTO {
	
	private int idPregunta;
	
	private String descripcion;
	
	private int consecutivo;
	
	private List<GrupoDTO> gruposDTO;
	
	private Double promedio;

	public int getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}

	public List<GrupoDTO> getGruposDTO() {
		return gruposDTO;
	}

	public void setGruposDTO(List<GrupoDTO> gruposDTO) {
		this.gruposDTO = gruposDTO;
	}

	public Double getPromedio() {
		return promedio;
	}

	public void setPromedio(Double promedio) {
		this.promedio = promedio;
	}
}
