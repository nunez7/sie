package edu.mx.utdelacosta.model.dto;

public class PeriodoDTO {
	
	private int idPeriodo;
	private String nombre;
	private String fechaInicio;
	private String fechaFin;
	private String inicioInscripcion;
	private String finInscripcion;
	private int idCiclo;
	
	public int getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getInicioInscripcion() {
		return inicioInscripcion;
	}
	public void setInicioInscripcion(String inicioInscripcion) {
		this.inicioInscripcion = inicioInscripcion;
	}
	public String getFinInscripcion() {
		return finInscripcion;
	}
	public void setFinInscripcion(String finInscripcion) {
		this.finInscripcion = finInscripcion;
	}
	public int getIdCiclo() {
		return idCiclo;
	}
	public void setIdCiclo(int idCiclo) {
		this.idCiclo = idCiclo;
	}	
	
}
