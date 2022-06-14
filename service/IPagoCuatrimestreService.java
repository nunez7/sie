package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.PagoCuatrimestre;
import edu.mx.utdelacosta.model.PagoGeneral;

public interface IPagoCuatrimestreService {
	
	PagoCuatrimestre buscarPorPagoGeneral(PagoGeneral pagoGeneral);
	
	void guardar (PagoCuatrimestre pagoCuatrimestre);

}
