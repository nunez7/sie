package edu.mx.utdelacosta.model.dto;

public class ProfesorProrrogaDTO {
	
	private Integer idProfesor;
	private String nombre;
	private Integer grupos;
	private Integer prorrogas;
	
	public Integer getIdProfesor() {
		return idProfesor;
	}
	public void setIdProfesor(Integer idProfesor) {
		this.idProfesor = idProfesor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getGrupos() {
		return grupos;
	}
	public void setGrupos(Integer grupos) {
		this.grupos = grupos;
	}
	public Integer getProrrogas() {
		return prorrogas;
	}
	public void setProrrogas(Integer prorrogas) {
		this.prorrogas = prorrogas;
	}
}
