package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.EvaluacionTutor;
import edu.mx.utdelacosta.model.Grupo;

public interface IEvaluacionTutorService {	
	void guardar(EvaluacionTutor evaluacionTutor);
	EvaluacionTutor buscarPorEvaluacionYGrupo(Evaluacion evaluacion, Grupo grupo);
}
