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
@Table(name = "respuesta_evaluacion_tutor")
public class RespuestaEvaluacionTutor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_respuesta")
	Respuesta respuesta;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})	
	@JoinColumn(name = "id_evaluacion_tutor")
	EvaluacionTutor evaluacionTutor;

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

	public EvaluacionTutor getEvaluacionTutor() {
		return evaluacionTutor;
	}

	public void setEvaluacionTutor(EvaluacionTutor evaluacionTutor) {
		this.evaluacionTutor = evaluacionTutor;
	}
	
}
