package edu.mx.utdelacosta.model.dto;

public class GrupoDTO {

	private int idCarrera;
	private int idGrupo;
	private int idPeriodo;
	private int idCuatrimestre;
	private String nombreGrupo;
	private boolean activo;
	private Integer cantidad;
	private Integer idTurno;
	private Integer consecutivo;

	private double promedio;
	private int promedioRed;
	private String nombrePeriodo;

	private Double promedioPre;

	public int getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}

	public int getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}

	public int getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public int getIdCuatrimestre() {
		return idCuatrimestre;
	}

	public void setIdCuatrimestre(int idCuatrimestre) {
		this.idCuatrimestre = idCuatrimestre;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public double getPromedio() {
		return promedio;
	}

	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}

	public int getPromedioRed() {
		return promedioRed;
	}

	public void setPromedioRed(int promedioRed) {
		this.promedioRed = promedioRed;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}

	public Double getPromedioPre() {
		return promedioPre;
	}

	public void setPromedioPre(Double promedioPre) {
		this.promedioPre = promedioPre;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

}
