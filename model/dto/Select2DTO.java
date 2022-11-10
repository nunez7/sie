package edu.mx.utdelacosta.model.dto;

import java.io.Serializable;

public class Select2DTO implements Serializable{

	private Integer id;
	
	private String descripcion;
	
	private Boolean seleccion;
	
	public Select2DTO () {}

	public Select2DTO(Integer id, String descripcion, Boolean seleccion) {
		this.id = id;
		this.descripcion = descripcion;
		this.seleccion = seleccion;
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

	public Boolean getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Boolean seleccion) {
		this.seleccion = seleccion;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
