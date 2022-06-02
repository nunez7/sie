package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.PagoAsignatura;
import edu.mx.utdelacosta.model.PagoGeneral;

public interface IPagoAsignaturaService {
	
	PagoAsignatura buscarPorId(Integer id);

	PagoAsignatura buscarPorPago(PagoGeneral pago);
	
	void guardar(PagoAsignatura pagoAsignatura);
	
	void eliminar(PagoAsignatura pagoAsignatura);
}
