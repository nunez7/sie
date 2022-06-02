package edu.mx.utdelacosta.model.dto;

public class MateriaDTO {
	
	private Integer idMateria;
	private String nombre;
	private String abreviatura;
	private Integer planEstudio;
	private Integer cuatrimestre;
	private Integer areaConocimiento;
	private Integer horasPracticas;
	private Integer horasTeoria;
	private Integer horasSemanales;
	private String objetivo;
	private String competencia;
	private boolean activo;
	private boolean integradora;
	private boolean curricular;
	private boolean calificacion;
	private boolean extracurricular;
	
	private double promedio;
	private String estatusPromedio;
	private String escala;

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
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public Integer getPlanEstudio() {
		return planEstudio;
	}
	public void setPlanEstudio(Integer planEstudio) {
		this.planEstudio = planEstudio;
	}
	public Integer getAreaConocimiento() {
		return areaConocimiento;
	}
	public void setAreaConocimiento(Integer areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}
	public Integer getHorasPracticas() {
		return horasPracticas;
	}
	public void setHorasPracticas(Integer horasPracticas) {
		this.horasPracticas = horasPracticas;
	}
	public Integer getHorasTeoria() {
		return horasTeoria;
	}
	public void setHorasTeoria(Integer horasTeoria) {
		this.horasTeoria = horasTeoria;
	}
	public Integer getHorasSemanales() {
		return horasSemanales;
	}
	public void setHorasSemanales(Integer horasSemanales) {
		this.horasSemanales = horasSemanales;
	}
	
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getCompetencia() {
		return competencia;
	}
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}
	public Integer getCuatrimestre() {
		return cuatrimestre;
	}
	public void setCuatrimestre(Integer cuatrimestre) {
		this.cuatrimestre = cuatrimestre;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public boolean isIntegradora() {
		return integradora;
	}
	public void setIntegradora(boolean integradora) {
		this.integradora = integradora;
	}
	public boolean isCurricular() {
		return curricular;
	}
	public void setCurricular(boolean curricular) {
		this.curricular = curricular;
	}
	public boolean isCalificacion() {
		return calificacion;
	}
	public void setCalificacion(boolean calificacion) {
		this.calificacion = calificacion;
	}
	public boolean isExtracurricular() {
		return extracurricular;
	}
	public void setExtracurricular(boolean extracurricular) {
		this.extracurricular = extracurricular;
	}
	public double getPromedio() {
		return promedio;
	}
	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}
	public String getEstatusPromedio() {
		return estatusPromedio;
	}
	public void setEstatusPromedio(String estatusPromedio) {
		this.estatusPromedio = estatusPromedio;
	}
	public String getEscala() {
		return escala;
	}
	public void setEscala(String escala) {
		this.escala = escala;
	}
	
}
