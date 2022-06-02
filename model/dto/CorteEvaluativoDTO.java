package edu.mx.utdelacosta.model.dto;

import java.util.Date;
import java.util.List;

public class CorteEvaluativoDTO {
	// horario alumno
	private int idCorte;
	
	private int consecutivo;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	List<MesDTO> meses;

	public int getIdCorte() {
		return idCorte;
	}

	public void setIdCorte(int idCorte) {
		this.idCorte = idCorte;
	}

	public int getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<MesDTO> getMeses() {
		return meses;
	}

	public void setMeses(List<MesDTO> meses) {
		this.meses = meses;
	}

	
}
