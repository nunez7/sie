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
@Table(name = "planes_estudio")
public class PlanEstudio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "id_carrera", referencedColumnName = "id")
	private Carrera carrera;
	
	private String plan;
	
	@Column(name="horas_estadia")
	private Integer horasEstadia;
	
	@OneToOne
	@JoinColumn(name = "nivel", referencedColumnName = "id")
	private NivelEstudio nivelEstudio;
	
	@Column(name="created")
	private Date fechaAlta;
	
	private Boolean activo;
	
	public PlanEstudio() {
	}

	public PlanEstudio(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Integer getHorasEstadia() {
		return horasEstadia;
	}

	public void setHorasEstadia(Integer horasEstadia) {
		this.horasEstadia = horasEstadia;
	}

	public NivelEstudio getNivelEstudio() {
		return nivelEstudio;
	}

	public void setNivelEstudio(NivelEstudio nivelEstudio) {
		this.nivelEstudio = nivelEstudio;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
