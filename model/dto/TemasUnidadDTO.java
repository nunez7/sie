package edu.mx.utdelacosta.model.dto;

public class TemasUnidadDTO {

	private Integer idTema;
	private Integer idUnidad;
	private String  nombreTema;
	private Integer consecutivoTema;
	
	public Integer getIdTema() {
		return idTema;
	}
	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}
	public Integer getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}
	public String getNombreTema() {
		return nombreTema;
	}
	public void setNombreTema(String nombreTema) {
		this.nombreTema = nombreTema;
	}
	public Integer getConsecutivoTema() {
		return consecutivoTema;
	}
	public void setConsecutivoTema(Integer consecutivoTema) {
		this.consecutivoTema = consecutivoTema;
	}
	
	
}
