package edu.mx.utdelacosta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "calificacion_corte")
public class CalificacionCorte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_alumno", referencedColumnName = "id")
	private Alumno alumno;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_corte_evaluativo", referencedColumnName = "id")
	private CorteEvaluativo corteEvaluativo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_carga_horaria", referencedColumnName = "id")
	private CargaHoraria cargaHoraria;
	
	@Column(precision=2, scale=5)
	private Float valor;
	
	private Boolean editable;
	
	public CalificacionCorte() {
	}
	
	public CalificacionCorte(int id) {
		this.id = id;
	}
	
	@Column(name = "sin_derecho")
	private Boolean sinDerecho;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public CorteEvaluativo getCortesEvaluativo() {
		return corteEvaluativo;
	}

	public void setCortesEvaluativo(CorteEvaluativo cortesEvaluativo) {
		this.corteEvaluativo = cortesEvaluativo;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getSinDerecho() {
		return sinDerecho;
	}

	public void setSinDerecho(Boolean sinDerecho) {
		this.sinDerecho = sinDerecho;
	}

	public CorteEvaluativo getCorteEvaluativo() {
		return corteEvaluativo;
	}

	public void setCorteEvaluativo(CorteEvaluativo corteEvaluativo) {
		this.corteEvaluativo = corteEvaluativo;
	}	
	
	
}
