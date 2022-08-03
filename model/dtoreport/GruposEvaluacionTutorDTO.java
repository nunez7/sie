package edu.mx.utdelacosta.model.dto;

import java.util.List;

import edu.mx.utdelacosta.model.Grupo;

public class GruposEvaluacionTutorDTO {
	
	private Double promedioGeneral;
	
	private List<PreguntaDTO> preguntas;
	
	private Grupo grupo;
	
	private List<ComentarioDTO> comentarios;
	
	private String directorCarrera;
	
	private int alumnosEncuestados;

	public Double getPromedioGeneral() {
		return promedioGeneral;
	}

	public void setPromedioGeneral(Double promedioGeneral) {
		this.promedioGeneral = promedioGeneral;
	}

	public List<PreguntaDTO> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaDTO> preguntas) {
		this.preguntas = preguntas;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<ComentarioDTO> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<ComentarioDTO> comentarios) {
		this.comentarios = comentarios;
	}

	public String getDirectorCarrera() {
		return directorCarrera;
	}

	public void setDirectorCarrera(String directorCarrera) {
		this.directorCarrera = directorCarrera;
	}

	public int getAlumnosEncuestados() {
		return alumnosEncuestados;
	}

	public void setAlumnosEncuestados(int alumnosEncuestados) {
		this.alumnosEncuestados = alumnosEncuestados;
	}
	
}
