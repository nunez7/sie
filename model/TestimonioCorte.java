package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "testimonio_corte")
public class TestimonioCorte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@Column(name="id_corte_evaluativo")
	private Integer corteEvaluativo;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "id_carga_horaria")
	private Integer cargaHoraria;
	
	//@OneToOne(fetch = FetchType.LAZY)
	@Column(name="id_alumno")
	private Integer alumno;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_testimonio")
	private Testimonio testimonio;
	
	@Column(name = "fecha_ultima_edicion")
	private Date fechaUltimaEdicion;
	
	@Column(name = "sin_derecho")
	private Boolean sinDerecho;
	
	private Boolean reprobado;
	
	private Boolean editable;
	
	public TestimonioCorte() {
	}
	
	public TestimonioCorte(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCorteEvaluativo() {
		return corteEvaluativo;
	}

	public void setCorteEvaluativo(Integer corteEvaluativo) {
		this.corteEvaluativo = corteEvaluativo;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Integer getAlumno() {
		return alumno;
	}

	public void setAlumno(Integer alumno) {
		this.alumno = alumno;
	}

	public Testimonio getTestimonio() {
		return testimonio;
	}

	public void setTestimonio(Testimonio testimonio) {
		this.testimonio = testimonio;
	}

	public Date getFechaUltimaEdicion() {
		return fechaUltimaEdicion;
	}

	public void setFechaUltimaEdicion(Date fechaUltimaEdicion) {
		this.fechaUltimaEdicion = fechaUltimaEdicion;
	}

	public Boolean getSinDerecho() {
		return sinDerecho;
	}

	public void setSinDerecho(Boolean sinDerecho) {
		this.sinDerecho = sinDerecho;
	}

	public Boolean getReprobado() {
		return reprobado;
	}

	public void setReprobado(Boolean reprobado) {
		this.reprobado = reprobado;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	
	
}
