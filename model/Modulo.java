package edu.mx.utdelacosta.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "modulos")
public class Modulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cve_modulo")
	private int cveModulo;

	private String nombre;

	private boolean activo;

	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cve_modulo_padre")
    private List<Submodulo> submodulos;

	public int getCveModulo() {
		return cveModulo;
	}

	public void setCveModulo(int cveModulo) {
		this.cveModulo = cveModulo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<Submodulo> getSubmodulos() {
		return submodulos;
	}

	public void setSubmodulos(List<Submodulo> submodulos) {
		this.submodulos = submodulos;
	}

	@Override
	public String toString() {
		return "Modulo [cveModulo=" + cveModulo + ", nombre=" + nombre + ", activo=" + activo + "]";
	}


}
