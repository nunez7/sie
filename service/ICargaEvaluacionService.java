package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.CargaEvaluacion;

public interface ICargaEvaluacionService {
	CargaEvaluacion buscarCargaEvaluacion(Integer id);
	void guardar(CargaEvaluacion cargaEvaluacion);
	CargaEvaluacion buscarPorCargaHorariaYEvaluacion(Integer idCargaHoraria, Integer idEvaluacionO);
}
