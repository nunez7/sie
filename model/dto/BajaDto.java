package edu.mx.utdelacosta.model.dto;

import java.util.Date;

public class BajaDto {
	
	private Integer idBaja;
	private Integer idAlumno;
	private Date ultimaFechaAsistio;
	private Date fechaSolicitud;
	private Integer idTipoBaja;
	private Integer idCausaBaja;
	private String descripcion;
	private String motivo;
	
	public Integer getIdBaja() {
		return idBaja;
	}
	public void setIdBaja(Integer idBaja) {
		this.idBaja = idBaja;
	}
	public Integer getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}
	
	public Date getUltimaFechaAsistio() {
		return ultimaFechaAsistio;
	}
	public void setUltimaFechaAsistio(Date ultimaFechaAsistio) {
		this.ultimaFechaAsistio = ultimaFechaAsistio;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public Integer getIdTipoBaja() {
		return idTipoBaja;
	}
	public void setIdTipoBaja(Integer idTipoBaja) {
		this.idTipoBaja = idTipoBaja;
	}
	public Integer getIdCausaBaja() {
		return idCausaBaja;
	}
	public void setIdCausaBaja(Integer idCausaBaja) {
		this.idCausaBaja = idCausaBaja;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
}
