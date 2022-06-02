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
@Table(name = "cuatrimestres")
public class Cuatrimestre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "id_nivel_estudio", referencedColumnName = "id")
	private NivelEstudio nivelEstudio;
	
	private String descripcion;
	
	private Integer consecutivo;
	
	@Column(name = "numero_cuatrimestre")
	private int numeroCuatrimestre;
	
	public Cuatrimestre() {
	}

	public Cuatrimestre(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public NivelEstudio getNivelEstudio() {
		return nivelEstudio;
	}

	public void setNivelEstudio(NivelEstudio nivelEstudio) {
		this.nivelEstudio = nivelEstudio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	public int getNumeroCuatrimestre() {
		return numeroCuatrimestre;
	}

	public void setNumeroCuatrimestre(int numeroCuatrimestre) {
		this.numeroCuatrimestre = numeroCuatrimestre;
	}
	
}
