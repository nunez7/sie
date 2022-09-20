package edu.mx.utdelacosta.model.dto;

import java.util.List;

public class ReciboDTO {

	private FolioDTO folio;

	private List<PagoConceptoDTO> pagos;

	private NotaCreditoDTO nota;

	private String folioCifrado;

	private Double montoTotal;

	private String montoLetras;

	private String nombreGrupo;
	
	private String carrera;

	public FolioDTO getFolio() {
		return folio;
	}

	public void setFolio(FolioDTO folio) {
		this.folio = folio;
	}

	public List<PagoConceptoDTO> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagoConceptoDTO> pagos) {
		this.pagos = pagos;
	}

	public NotaCreditoDTO getNota() {
		return nota;
	}

	public void setNota(NotaCreditoDTO nota) {
		this.nota = nota;
	}

	public String getFolioCifrado() {
		return folioCifrado;
	}

	public void setFolioCifrado(String folioCifrado) {
		this.folioCifrado = folioCifrado;
	}

	public Double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getMontoLetras() {
		return montoLetras;
	}

	public void setMontoLetras(String montoLetras) {
		this.montoLetras = montoLetras;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}
	
	

}
