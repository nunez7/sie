package edu.mx.utdelacosta.model.dto;

public class PersonalDTO extends PersonaDTO{
	private int idPersonal;
	private String noEmpleado;
	private boolean coordinadorEstadia;
	private boolean jefe;
	private int idPuesto;
	private int idJefe;
	private boolean activo;
	private String extracto;
		
	public int getIdPersonal() {
		return idPersonal;
	}
	public void setIdPersonal(int idPersonal) {
		this.idPersonal = idPersonal;
	}
	public String getNoEmpleado() {
		return noEmpleado;
	}
	public void setNoEmpleado(String noEmpleado) {
		this.noEmpleado = noEmpleado;
	}
	public boolean isCoordinadorEstadia() {
		return coordinadorEstadia;
	}
	public void setCoordinadorEstadia(boolean coordinadorEstadia) {
		this.coordinadorEstadia = coordinadorEstadia;
	}
	public boolean isJefe() {
		return jefe;
	}
	public void setJefe(boolean jefe) {
		this.jefe = jefe;
	}
	public int getIdPuesto() {
		return idPuesto;
	}
	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}
	public int getIdJefe() {
		return idJefe;
	}
	public void setIdJefe(int idJefe) {
		this.idJefe = idJefe;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getExtracto() {
		return extracto;
	}
	public void setExtracto(String extracto) {
		this.extracto = extracto;
	}
	
}
