package edu.mx.utdelacosta.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dosificacion_importada")
public class DosificacionImportada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "id_dosificacion")
	private Dosificacion dosificacion;
	
	@OneToOne
	@JoinColumn(name = "id_carga_horaria")
	private CargaHoraria cargaHoraria;
	
	@Column(name = "fecha_alta")
	private Date fecha;
	
	public DosificacionImportada() {
		
	}
	
	public DosificacionImportada(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Dosificacion getDosificacion() {
		return dosificacion;
	}

	public void setDosificacion(Dosificacion dosificacion) {
		this.dosificacion = dosificacion;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
