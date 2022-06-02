package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.CargaEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Evaluacion;

public interface ICargaEvaluacionService {
	CargaEvaluacion buscarCargaEvaluacion(Integer id);
	void guardar(CargaEvaluacion cargaEvaluacion);
	CargaEvaluacion buscarPorCargaHorariaYEvaluacion(CargaHoraria cargaHoraria, Evaluacion evaluacion);
}
