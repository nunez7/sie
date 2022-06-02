package edu.mx.utdelacosta.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "respuesta_carga_evaluacion")
public class RespuestaCargaEvaluacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_respuesta")
	Respuesta respuesta;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})	
	@JoinColumn(name = "id_carga_evaluacion")
	CargaEvaluacion cargaEvaluacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Respuesta getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Respuesta respuesta) {
		this.respuesta = respuesta;
	}

	public CargaEvaluacion getCargaEvaluacion() {
		return cargaEvaluacion;
	}

	public void setCargaEvaluacion(CargaEvaluacion cargaEvaluacion) {
		this.cargaEvaluacion = cargaEvaluacion;
	}
	
}
