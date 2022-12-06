package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Pregunta;

public interface PreguntaRepository extends CrudRepository<Pregunta, Integer>{
	
	//busca las id de pregunta por idEvaluacion
	@Query(value = "SELECT id FROM preguntas WHERE id_evaluacion = :idEvaluacion "
			+ "ORDER BY consecutivo ", nativeQuery = true)
	List<Integer> findByIdEvaluacion(@Param("idEvaluacion") Integer idEvaluacion);
	
}
