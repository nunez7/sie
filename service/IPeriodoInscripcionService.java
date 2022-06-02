package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.PeriodoInscripcion;

public interface IPeriodoInscripcionService {
	
	List<PeriodoInscripcion> buscarActivos();
	
	PeriodoInscripcion obtenerUltimo();
	
	PeriodoInscripcion buscarPorId(Integer id);
	
	Integer ultimoId();

}
