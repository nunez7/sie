package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mecanismo_alumno")
public class MecanismoAlumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_carga_horaria", referencedColumnName = "id")
	private CargaHoraria cargaHoraria;
	
	@ManyToOne
	@JoinColumn(name = "id_alumno", referencedColumnName = "id")
	private Alumno alumno;
	
	private Boolean acepto;
	
	@Column(name = "fecha_acepto")
	private Date fechaAcepto;
	
	private Boolean activo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Boolean getAcepto() {
		return acepto;
	}

	public void setAcepto(Boolean acepto) {
		this.acepto = acepto;
	}

	public Date getFechaAcepto() {
		return fechaAcepto;
	}

	public void setFechaAcepto(Date fechaAcepto) {
		this.fechaAcepto = fechaAcepto;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
}
