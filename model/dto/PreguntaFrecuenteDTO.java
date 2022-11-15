package edu.mx.utdelacosta.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.mx.utdelacosta.model.Modulo;
import edu.mx.utdelacosta.model.PreguntaFrecuente;

public class PreguntaFrecuenteDTO implements Serializable {

	private Modulo modulo;

	private List<PreguntaFrecuente> preguntas;
	
	public PreguntaFrecuenteDTO() {
		preguntas = new ArrayList<PreguntaFrecuente>();
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<PreguntaFrecuente> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaFrecuente> preguntas) {
		this.preguntas = preguntas;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
