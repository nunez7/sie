package edu.mx.utdelacosta.model.dtoreport;

import java.util.List;

public class AlumnoPromedioDTO {
	private Integer idAlumno;
	private String nombre;
	private String matricula;
	private List<IndicadorMateriaDTO> materias;
	
	public Integer getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<IndicadorMateriaDTO> getMaterias() {
		return materias;
	}
	public void setMaterias(List<IndicadorMateriaDTO> materias) {
		this.materias = materias;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
