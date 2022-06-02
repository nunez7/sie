package edu.mx.utdelacosta.model.dto;

public class CambiarGrupoDTO {
	
	private Integer idAlumno;
	
	private Integer grupoAnterior;
	
	private Integer grupoNuevo;

	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}

	public Integer getGrupoAnterior() {
		return grupoAnterior;
	}

	public void setGrupoAnterior(Integer grupoAnterior) {
		this.grupoAnterior = grupoAnterior;
	}

	public Integer getGrupoNuevo() {
		return grupoNuevo;
	}

	public void setGrupoNuevo(Integer grupoNuevo) {
		this.grupoNuevo = grupoNuevo;
	}
}
