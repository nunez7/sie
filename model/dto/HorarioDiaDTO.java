package edu.mx.utdelacosta.model.dto;

import java.util.List;

public class HorarioDiaDTO {

	private String horaInicio; 
	
	private String horaFin;
	
	List<HorarioDTO> horarios;

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public List<HorarioDTO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioDTO> horarios) {
		this.horarios = horarios;
	}
	
	
	
}
