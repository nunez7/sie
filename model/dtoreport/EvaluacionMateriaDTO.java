package edu.mx.utdelacosta.model.dtoreport;

public class EvaluacionMateriaDTO {

	private Integer idMateria;
	private Integer idCargaEvaluacion;
	private Integer respuestas;
	private Boolean terminada;
	
	public Integer getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}
	public Integer getIdCargaEvaluacion() {
		return idCargaEvaluacion;
	}
	public void setIdCargaEvaluacion(Integer idCargaEvaluacion) {
		this.idCargaEvaluacion = idCargaEvaluacion;
	}
	public Integer getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(Integer respuestas) {
		this.respuestas = respuestas;
	}
	public Boolean getTerminada() {
		return terminada;
	}
	public void setTerminada(Boolean terminada) {
		this.terminada = terminada;
	}
}
