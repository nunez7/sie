package edu.mx.utdelacosta.model.dto;

public class CalificacionDTO {
	
	Integer idMecanismo;
	
	Float valor;
	
	float caliCorte;
	
	String status;

	public Integer getIdMecanismo() {
		return idMecanismo;
	}

	public void setIdMecanismo(Integer idMecanismo) {
		this.idMecanismo = idMecanismo;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public float getCaliCorte() {
		return caliCorte;
	}

	public void setCaliCorte(float caliCorte) {
		this.caliCorte = caliCorte;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
