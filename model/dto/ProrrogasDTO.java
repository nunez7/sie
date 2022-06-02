package edu.mx.utdelacosta.model.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ProrrogasDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer idCargaHoraria;
	
	private Integer idCorteEvaluativo;
	
	private Date fechaLimite;
	
	private String comentario;
	
	private Integer idTipoProrroga;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCargaHoraria() {
		return idCargaHoraria;
	}

	public void setIdCargaHoraria(Integer idCargaHoraria) {
		this.idCargaHoraria = idCargaHoraria;
	}

	public Integer getIdCorteEvaluativo() {
		return idCorteEvaluativo;
	}

	public void setIdCorteEvaluativo(Integer idCorteEvaluativo) {
		this.idCorteEvaluativo = idCorteEvaluativo;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getIdTipoProrroga() {
		return idTipoProrroga;
	}

	public void setIdTipoProrroga(Integer idTipoProrroga) {
		this.idTipoProrroga = idTipoProrroga;
	}
	
	
}
