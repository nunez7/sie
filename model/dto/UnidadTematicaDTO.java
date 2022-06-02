package edu.mx.utdelacosta.model.dto;

public class UnidadTematicaDTO {

	private Integer idUnidad;
	private Integer idMateria;
	private String nombre;
	private Integer consecutivo;
	private Integer horasPracticas;
	private Integer horasTeoricas;
	private Integer horasTotales;
	
	public Integer getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}
	public Integer getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}
	public Integer getHorasPracticas() {
		return horasPracticas;
	}
	public void setHorasPracticas(Integer horasPracticas) {
		this.horasPracticas = horasPracticas;
	}
	public Integer getHorasTeoricas() {
		return horasTeoricas;
	}
	public void setHorasTeoricas(Integer horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}
	public Integer getHorasTotales() {
		return horasTotales;
	}
	public void setHorasTotales(Integer horasTotales) {
		this.horasTotales = horasTotales;
	}
	
}
