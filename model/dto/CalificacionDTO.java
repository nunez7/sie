package edu.mx.utdelacosta.model.dto;

public class CalificacionDTO {
	
	Integer idMecanismo;
	
	Integer valor;
	
	float caliCorte;
	
	String status;

	public Integer getIdMecanismo() {
		return idMecanismo;
	}

	public void setIdMecanismo(Integer idMecanismo) {
		this.idMecanismo = idMecanismo;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
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
