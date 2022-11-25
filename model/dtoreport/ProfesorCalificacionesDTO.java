package edu.mx.utdelacosta.model.dtoreport;

public class ProfesorCalificacionesDTO {
	
	private Integer idPersona;
	private String nombre;
	private String noEmpleado;
	private Boolean instrumentos;
	private Boolean calificaciones;
	
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNoEmpleado() {
		return noEmpleado;
	}
	public void setNoEmpleado(String noEmpleado) {
		this.noEmpleado = noEmpleado;
	}
	public Boolean getInstrumentos() {
		return instrumentos;
	}
	public void setInstrumentos(Boolean instrumentos) {
		this.instrumentos = instrumentos;
	}
	public Boolean getCalificaciones() {
		return calificaciones;
	}
	public void setCalificaciones(Boolean calificaciones) {
		this.calificaciones = calificaciones;
	} 
}
