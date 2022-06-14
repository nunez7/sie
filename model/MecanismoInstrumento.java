package edu.mx.utdelacosta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mecanismo_instrumento")
public class MecanismoInstrumento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne()
	@JoinColumn(name = "id_instrumento")
	private Instrumento instrumento;

	private Integer ponderacion;

	@Column(name = "id_carga_horaria")
	private Integer idCargaHoraria;

	@Column(name = "id_corte_evaluativo")
	private Integer idCorteEvaluativo;
	
	private String archivo;
	
	private Boolean activo;
	
	public MecanismoInstrumento() {
	}
	
	public MecanismoInstrumento(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Integer getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Integer ponderacion) {
		this.ponderacion = ponderacion;
	}

	public Integer getIdCargaHoraria() {
		return idCargaHoraria;
	}

	public void setIdCargaHoraria(Integer idCargaHoraria) {
		this.idCargaHoraria = idCargaHoraria;
	}

	public Integer getIdCorteEvaluativo() {
		return idCorteEvaluativo;
	}

	public void setIdCorteEvaluativo(Integer idCorteEvaluativo) {
		this.idCorteEvaluativo = idCorteEvaluativo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getRubrica() {
		return rubrica;
	}

	public void setRubrica(String rubrica) {
		this.rubrica = rubrica;
	}
	
}
