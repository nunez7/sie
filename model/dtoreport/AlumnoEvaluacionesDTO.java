package edu.mx.utdelacosta.model.dtoreport;

import java.util.List;

public class AlumnoEvaluacionesDTO {
	
	private Integer idAlumno;
	private String matricula;
	private String nombre;
	private String grupo;
	private List<EvaluacionMateriaDTO> materias;
	private Boolean evaluacionTutor;
	
	public Integer getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}
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
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public List<EvaluacionMateriaDTO> getMaterias() {
		return materias;
	}
	public void setMaterias(List<EvaluacionMateriaDTO> materias) {
		this.materias = materias;
	}
	public Boolean getEvaluacionTutor() {
		return evaluacionTutor;
	}
	public void setEvaluacionTutor(Boolean evaluacionTutor) {
		this.evaluacionTutor = evaluacionTutor;
	}
	
}

