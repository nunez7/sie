package edu.mx.utdelacosta.model.dto;

import java.util.List;


public class MesDTO{
	
	private String mes;
	
	List<DiaDTO> dias;

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<DiaDTO> getDias() {
		return dias;
	}

	public void setDias(List<DiaDTO> dias) {
		this.dias = dias;
	}

	
	
}
