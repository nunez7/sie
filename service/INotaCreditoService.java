package edu.mx.utdelacosta.service;

import java.util.Date;

import edu.mx.utdelacosta.model.NotaCredito;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.NotaCreditoDTO;

public interface INotaCreditoService {

	NotaCredito buscarPorPagoGeneral(PagoGeneral pagoGeneral);
	
	void guardar (NotaCredito notaCredito);
	
	NotaCreditoDTO BuscarPorFolio(String folio);
	
	Double buscarTotalPorFechaInicioYFechaFin(Date fechaInicio, Date fechaFin);
	
	Double buscarTotalPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin, Integer cajero);

	NotaCreditoDTO buscarConcentradoPorFechaInicioYFechaFin(Date fechaInicio, Date fechaFin);
	
	NotaCreditoDTO buscarConcentradoPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin, Integer cajero);
}
