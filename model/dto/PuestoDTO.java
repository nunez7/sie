package edu.mx.utdelacosta.model.dto;

public class PuestoDTO {
	private int idPuesto;
	private String nombre;
	private String abreviatura;
	
	public void setId(int idPuesto) {
		this.idPuesto = idPuesto;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public int getIdPuesto() {
		return idPuesto;
	}
	public String getNombre() {
		return nombre;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	
	
}
