package edu.mx.utdelacosta.model.dto;

import java.util.Date;

public class DosificacionTemaDto {
	
	private Integer idCorte;
	
	private Integer id;
	
	private Integer idUnidad;

	private String nombre;
	
	private Integer horasTeoricas;
	
	private Integer horasPracticas;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private Integer consecutivo;
	
	public Integer getIdCorte() {
		return idCorte;
	}

	public void setIdCorte(Integer idCorte) {
		this.idCorte = idCorte;
	}

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

	public Integer getHorasTeoricas() {
		return horasTeoricas;
	}

	public void setHorasTeoricas(Integer horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}

	public Integer getHorasPracticas() {
		return horasPracticas;
	}

	public void setHorasPracticas(Integer horasPracticas) {
		this.horasPracticas = horasPracticas;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}
	
}
