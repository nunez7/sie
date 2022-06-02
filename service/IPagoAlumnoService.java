package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoGeneral;

public interface IPagoAlumnoService {

	void guardar (PagoAlumno pagoAlumno);
	
	void eliminar (PagoAlumno pagoAlumno);
	
	PagoAlumno buscarPorPagoGeneral(PagoGeneral pagoGeneral);
}
