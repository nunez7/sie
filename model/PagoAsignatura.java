package edu.mx.utdelacosta.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pago_asignatura")
public class PagoAsignatura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_pago", referencedColumnName = "id")
	private PagoGeneral pagoGeneral;
	
	@OneToOne
	@JoinColumn(name = "id_carga_horaria")
	private CargaHoraria cargaHoraria;
	
	private Integer oportunidad;
	
	@Column(name = "id_corte_evaluativo")
	private Integer idCorteEvaluativo;
	
	public PagoAsignatura() {
		
	}

	public PagoAsignatura(Integer id) {
		this.id=id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PagoGeneral getPagoGeneral() {
		return pagoGeneral;
	}

	public void setPagoGeneral(PagoGeneral pagoGeneral) {
		this.pagoGeneral = pagoGeneral;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Integer getOportunidad() {
		return oportunidad;
	}

	public void setOportunidad(Integer oportunidad) {
		this.oportunidad = oportunidad;
	}

	public Integer getIdCorteEvaluativo() {
		return idCorteEvaluativo;
	}

	public void setIdCorteEvaluativo(Integer idCorteEvaluativo) {
		this.idCorteEvaluativo = idCorteEvaluativo;
	}
	
	
	

}
