package edu.mx.utdelacosta.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TutoriaIndividualDTO implements Serializable {

	private Integer id;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date fechaTutoria;

	@JsonFormat(pattern="HH:mm")
	private Date horaInicio;

	@JsonFormat(pattern="HH:mm")
	private Date horaFin;

	private String motivos;

	private String puntosImportantes;

	private String compromisosAcuerdos;

	private String nivelAtencion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaTutoria() {
		return fechaTutoria;
	}

	public void setFechaTutoria(Date fechaTutoria) {
		this.fechaTutoria = fechaTutoria;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public String getMotivos() {
		return motivos;
	}

	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}

	public String getPuntosImportantes() {
		return puntosImportantes;
	}

	public void setPuntosImportantes(String puntosImportantes) {
		this.puntosImportantes = puntosImportantes;
	}

	public String getCompromisosAcuerdos() {
		return compromisosAcuerdos;
	}

	public void setCompromisosAcuerdos(String compromisosAcuerdos) {
		this.compromisosAcuerdos = compromisosAcuerdos;
	}

	public String getNivelAtencion() {
		return nivelAtencion;
	}

	public void setNivelAtencion(String nivelAtencion) {
		this.nivelAtencion = nivelAtencion;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
