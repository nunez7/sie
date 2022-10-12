package edu.mx.utdelacosta.model.dto;

import java.util.List;

import edu.mx.utdelacosta.model.CalendarioEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;

public class CargaUnidadDTO {
	
	CargaHoraria carga;
	
	List<CorteEvaluativo> cortes;
	
	List<CalendarioEvaluacion> calendarios;
	
	List<DosificacionTemaDto> dosificacionTema;

	public List<CorteEvaluativo> getCortes() {
		return cortes;
	}

	public void setCortes(List<CorteEvaluativo> cortes) {
		this.cortes = cortes;
	}

	public List<CalendarioEvaluacion> getCalendarios() {
		return calendarios;
	}

	public void setCalendarios(List<CalendarioEvaluacion> calendarios) {
		this.calendarios = calendarios;
	}

	public List<DosificacionTemaDto> getDosificacionTema() {
		return dosificacionTema;
	}

	public void setDosificacionTema(List<DosificacionTemaDto> dosificacionTema) {
		this.dosificacionTema = dosificacionTema;
	}

	public CargaHoraria getCarga() {
		return carga;
	}

	public void setCarga(CargaHoraria carga) {
		this.carga = carga;
	}

}
