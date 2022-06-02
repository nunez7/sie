package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.EvaluacionTutor;
import edu.mx.utdelacosta.model.Grupo;

public interface EvaluacionTutorRepository extends CrudRepository<EvaluacionTutor, Integer>{
	EvaluacionTutor findByEvaluacionAndGrupo(Evaluacion evaluacion, Grupo grupo);
}
