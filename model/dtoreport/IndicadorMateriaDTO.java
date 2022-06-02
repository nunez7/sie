package edu.mx.utdelacosta.model.dtoreport;

import java.util.List;

public class IndicadorMateriaDTO {
	
	private Integer idMateria;
	private String nombre;
	private double promedio;
	private Integer remediales;
	private double pRemediales;
	private Integer extraordinarios;
	private double pExtraordinarios;
	//se agrega para estatus de la materia
	private String estatus;
	//indicadores por parcial de la materia
	private List<IndicadorParcialDTO> parciales;
	public Integer getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPromedio() {
		return promedio;
	}
	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}
	public Integer getRemediales() {
		return remediales;
	}
	public void setRemediales(Integer remediales) {
		this.remediales = remediales;
	}
	public double getpRemediales() {
		return pRemediales;
	}
	public void setpRemediales(double pRemediales) {
		this.pRemediales = pRemediales;
	}
	public Integer getExtraordinarios() {
		return extraordinarios;
	}
	public void setExtraordinarios(Integer extraordinarios) {
		this.extraordinarios = extraordinarios;
	}
	public double getpExtraordinarios() {
		return pExtraordinarios;
	}
	public void setpExtraordinarios(double pExtraordinarios) {
		this.pExtraordinarios = pExtraordinarios;
	}
	public List<IndicadorParcialDTO> getParciales() {
		return parciales;
	}
	public void setParciales(List<IndicadorParcialDTO> parciales) {
		this.parciales = parciales;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
}
