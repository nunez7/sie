package edu.mx.utdelacosta.model.dto;

import java.util.List;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.MecanismoInstrumento;

public class DosificacionDTO {
	
	private Dosificacion dosificacion;
	
	private List<DosificacionUnidadDTO> unidades;
	
	private List<MecanismoInstrumento> mecanismos;

	public Dosificacion getDosificacion() {
		return dosificacion;
	}

	public void setDosificacion(Dosificacion dosificacion) {
		this.dosificacion = dosificacion;
	}

	public List<DosificacionUnidadDTO> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<DosificacionUnidadDTO> unidades) {
		this.unidades = unidades;
	}

	public List<MecanismoInstrumento> getMecanismos() {
		return mecanismos;
	}

	public void setMecanismos(List<MecanismoInstrumento> mecanismos) {
		this.mecanismos = mecanismos;
	}
	
}
