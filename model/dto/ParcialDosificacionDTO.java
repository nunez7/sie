package edu.mx.utdelacosta.model.dto;

import java.util.List;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.MecanismoInstrumento;

public class ParcialDosificacionDTO {
	
	Integer idCorteEvaluativo;
	
	Integer consecutivoCorte;
	
	Dosificacion dosificacion;
	
	Integer idCargaHoraria;
	
	List<MecanismoInstrumento> instrumentos;

	public Integer getIdCorteEvaluativo() {
		return idCorteEvaluativo;
	}

	public void setIdCorteEvaluativo(Integer idCorteEvaluativo) {
		this.idCorteEvaluativo = idCorteEvaluativo;
	}

	public Integer getConsecutivoCorte() {
		return consecutivoCorte;
	}

	public void setConsecutivoCorte(Integer consecutivoCorte) {
		this.consecutivoCorte = consecutivoCorte;
	}

	public Dosificacion getDosificacion() {
		return dosificacion;
	}

	public void setDosificacion(Dosificacion dosificacion) {
		this.dosificacion = dosificacion;
	}

	public Integer getIdCargaHoraria() {
		return idCargaHoraria;
	}

	public void setIdCargaHoraria(Integer idCargaHoraria) {
		this.idCargaHoraria = idCargaHoraria;
	}

	public List<MecanismoInstrumento> getInstrumentos() {
		return instrumentos;
	}

	public void setInstrumentos(List<MecanismoInstrumento> instrumentos) {
		this.instrumentos = instrumentos;
	}
}
