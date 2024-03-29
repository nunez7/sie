package edu.mx.utdelacosta.model.dtoreport;

import java.util.List;

public class AlumnoPromedioDTO {
	private Integer idAlumno;
	private String nombre;
	private String matricula;
	private List<IndicadorMateriaDTO> materias;
	private Boolean activo;
	//si excede remediales permitidos
	private Boolean excedeRemediales;
	
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
	public Boolean getExcedeRemediales() {
		return excedeRemediales;
	}
	public void setExcedeRemediales(Boolean excedeRemediales) {
		this.excedeRemediales = excedeRemediales;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
