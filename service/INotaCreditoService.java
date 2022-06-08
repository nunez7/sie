package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.NotaCredito;
import edu.mx.utdelacosta.model.PagoGeneral;

public interface INotaCreditoService {

	NotaCredito buscarPorPagoGeneral(PagoGeneral pagoGeneral);
	
	void guardar (NotaCredito notaCredito);
}
