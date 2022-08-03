package edu.mx.utdelacosta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "municipios")
public class Municipio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String clave;
	
	private String nombre;
	
	private Integer activo;
	
	@ManyToOne
	@JoinColumn(name = "estado_id", referencedColumnName = "id")
	private Estado estado;
	
	/*@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "municipio_id", referencedColumnName = "id")
	private List<Localidad> localidades; */
	
	public Municipio() {}
	
	public Municipio(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	/*public List<Localidad> getLocalidades() {
		return localidades;
	}*/
	
}
