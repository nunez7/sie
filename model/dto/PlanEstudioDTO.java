package edu.mx.utdelacosta.model.dto;

public class PlanEstudioDTO {
	
	private int idPlan;
	private String nombre;
	private int idCarrera;
	private int idNivelEstudio;
	private int horasEstadia;
	private boolean activo;
	
	public int getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getIdCarrera() {
		return idCarrera;
	}
	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}
	public int getIdNivelEstudio() {
		return idNivelEstudio;
	}
	public void setIdNivelEstudio(int idNivelEstudio) {
		this.idNivelEstudio = idNivelEstudio;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int getHorasEstadia() {
		return horasEstadia;
	}
	public void setHorasEstadia(int horasEstadia) {
		this.horasEstadia = horasEstadia;
	}
	
}
