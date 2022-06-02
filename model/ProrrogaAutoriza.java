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
	private int idAutoriza;
	
	@Column(name = "id_prorroga")
	private int idProrroga;
	
	@Column(name = "fecha_alta")
	private Date fechaAlta;

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getIdAutoriza() {
		return idAutoriza;
	}

	public void setIdAutoriza(int idAutoriza) {
		this.idAutoriza = idAutoriza;
	}

	public int getIdProrroga() {
		return idProrroga;
	}

	public void setIdProrroga(int idProrroga) {
		this.idProrroga = idProrroga;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	
}
