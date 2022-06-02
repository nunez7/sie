package edu.mx.utdelacosta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "submodulos")
public class Submodulo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cve_submodulo")
	private int cveSubmodulo;

	private String descripcion;

	private String plantilla;

	private String icono;

	private int consecutivo;

	private boolean activo;

	@Column(name = "cve_modulo_padre")
	private int moduloPadre;

	public int getCveSubmodulo() {
		return cveSubmodulo;
	}

	public void setCveSubmodulo(int cveSubmodulo) {
		this.cveSubmodulo = cveSubmodulo;
	}

	public int getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public int getModuloPadre() {
		return moduloPadre;
	}

	public void setModuloPadre(int moduloPadre) {
		this.moduloPadre = moduloPadre;
	}

}
