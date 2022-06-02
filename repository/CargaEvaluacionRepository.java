package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.CargaEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Evaluacion;

public interface CargaEvaluacionRepository extends CrudRepository<CargaEvaluacion, Integer>{
	CargaEvaluacion findByCargaHorariaAndEvaluacion(CargaHoraria cargaHoraria, Evaluacion evaluacion);
}
