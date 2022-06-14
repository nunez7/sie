package edu.mx.utdelacosta.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "focos_atencion")
public class FocosAtencion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String descripcion;
	 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_foco")
	private Foco foco;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_grupo")
	private Grupo grupo;
		
	public FocosAtencion() {
	}
	
	public FocosAtencion(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Foco getFoco() {
		return foco;
	}

	public void setFoco(Foco foco) {
		this.foco = foco;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	} 
	
}
