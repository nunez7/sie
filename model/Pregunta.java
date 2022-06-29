package edu.mx.utdelacosta.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

@Entity
@Table(name = "preguntas")
public class Pregunta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String descripcion;
	
	private Integer consecutivo;
	
	private Boolean abierta;
	
	private String input;
	
	private Integer limite;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_evaluacion")
	private Evaluacion evaluacion;
	
	@Transient
	private List<OpcionRespuestaDTO> OpcionesRespuesta;
	
	@Transient
	private String comentarioRespuesta;
	
	public Pregunta() {
		
	}
	
	public Pregunta(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Boolean getAbierta() {
		return abierta;
	}

	public void setAbierta(Boolean abierta) {
		this.abierta = abierta;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public Integer getLimite() {
		return limite;
	}

	public void setLimite(Integer limite) {
		this.limite = limite;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public List<OpcionRespuestaDTO> getOpcionesRespuesta() {
		return OpcionesRespuesta;
	}

	public void setOpcionesRespuesta(List<OpcionRespuestaDTO> opcionesRespuesta) {
		OpcionesRespuesta = opcionesRespuesta;
	}

	public String getComentarioRespuesta() {
		return comentarioRespuesta;
	}

	public void setComentarioRespuesta(String comentarioRespuesta) {
		this.comentarioRespuesta = comentarioRespuesta;
	}

}
