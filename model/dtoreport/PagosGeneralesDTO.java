package edu.mx.utdelacosta.model.dtoreport;

import java.util.Date;

public interface PagosGeneralesDTO {

	//usada para reporte de caja detallado módulo caja
	String getFolio();
	
	String getMatricula();
	
	String getNombre();
	
	String getConcepto();
	
	Integer getEstatus();
	
	String getCajero();
	
	Double getMonto();
	
	Date getFecha();
	
	Boolean getFactura();
	
	Integer getTipoPago();
}
