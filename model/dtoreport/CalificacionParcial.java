package edu.mx.utdelacosta.model.dtoreport;

import java.util.List;

public class CalificacionParcial {
	
	private String matricula;
	
	private String nombre;
	
	private List<CalificacionInstrumentoDTO> mecanismos;
	
	private float calificacionOrdinaria;
	
	private Integer calificacionRemedial;
	
	private Integer calificacionExtraordinario;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CalificacionInstrumentoDTO> getMecanismos() {
		return mecanismos;
	}

	public void setMecanismos(List<CalificacionInstrumentoDTO> mecanismos) {
		this.mecanismos = mecanismos;
	}

	public float getCalificacionOrdinaria() {
		return calificacionOrdinaria;
	}

	public void setCalificacionOrdinaria(float calificacionOrdinaria) {
		this.calificacionOrdinaria = calificacionOrdinaria;
	}

	public Integer getCalificacionRemedial() {
		return calificacionRemedial;
	}

	public void setCalificacionRemedial(Integer calificacionRemedial) {
		this.calificacionRemedial = calificacionRemedial;
	}

	public Integer getCalificacionExtraordinario() {
		return calificacionExtraordinario;
	}

	public void setCalificacionExtraordinario(Integer calificacionExtraordinario) {
		this.calificacionExtraordinario = calificacionExtraordinario;
	}
	
	
	
	
}