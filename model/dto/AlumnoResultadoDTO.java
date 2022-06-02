package edu.mx.utdelacosta.model.dto;

public class AlumnoResultadoDTO {
	
	private Integer id;
	
	private String nombreCompleto;
	
	private String valor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public AlumnoResultadoDTO() {
	}

}
