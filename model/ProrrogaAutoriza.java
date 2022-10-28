package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.GenerationType;

@Entity
@Table(name = "prorroga_autoriza")
public class ProrrogaAutoriza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_autoriza")
	private Integer idAutoriza;
	
	@Column(name = "id_prorroga")
	private Integer idProrroga;
	
	@Column(name = "fecha_alta")
	private Date fechaAlta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAutoriza() {
		return idAutoriza;
	}

	public void setIdAutoriza(Integer idAutoriza) {
		this.idAutoriza = idAutoriza;
	}

	public Integer getIdProrroga() {
		return idProrroga;
	}

	public void setIdProrroga(Integer idProrroga) {
		this.idProrroga = idProrroga;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
}
