package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cambios_grupo")
public class CambioGrupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_alumno")
	private Integer idAlumno;
	
	@Column(name="id_grupo_old")
	private Integer idGrupoAnterior;
	
	@Column(name="id_grupo_nvo")
	private Integer idGrupoNuevo;
	
	private Integer estatus;
	
	@Column(name="created")
	private Date fechaAlta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}

	public Integer getIdGrupoAnterior() {
		return idGrupoAnterior;
	}

	public void setIdGrupoAnterior(Integer idGrupoAnterior) {
		this.idGrupoAnterior = idGrupoAnterior;
	}

	public Integer getIdGrupoNuevo() {
		return idGrupoNuevo;
	}

	public void setIdGrupoNuevo(Integer idGrupoNuevo) {
		this.idGrupoNuevo = idGrupoNuevo;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
}
