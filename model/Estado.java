package edu.mx.utdelacosta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estados")
public class Estado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String clave;
	
	private String nombre;
	
	@Column(name="abrev")
	private String abreviatura;
	
	private Integer activo;
	//estado_id es la columna que tiene municipios
	/*@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "estado_id", referencedColumnName = "id")
	private List<Municipio> municipios; */
	
	public Estado() {
	}

	public Estado(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	/*public List<Municipio> getMunicipios() {
		return municipios;
	}*/
	
	
}
