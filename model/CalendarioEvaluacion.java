package edu.mx.utdelacosta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "calendario_evaluacion")
public class CalendarioEvaluacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_carga_horaria", nullable = false)
	private CargaHoraria cargaHoraria;
	
	@OneToOne()
	@LazyCollection(LazyCollectionOption.TRUE)
	@JoinColumn(name = "id_unidad_tematica")
	private UnidadTematica unidadTematica;
	
	@OneToOne()
	@LazyCollection(LazyCollectionOption.TRUE)
	@JoinColumn(name = "id_corte_evaluativo")
	private CorteEvaluativo corteEvaluativo;
	
	public CalendarioEvaluacion() {
	}
	
	public CalendarioEvaluacion(int id) {
		this.id = id;
	}
	
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

	public UnidadTematica getUnidadTematica() {
		return unidadTematica;
	}

	public void setUnidadTematica(UnidadTematica undiadTematica) {
		this.unidadTematica = undiadTematica;
	}

	public CorteEvaluativo getCorteEvaluativo() {
		return corteEvaluativo;
	}

	public void setCorteEvaluativo(CorteEvaluativo corteEvaluativo) {
		this.corteEvaluativo = corteEvaluativo;
	}
}
