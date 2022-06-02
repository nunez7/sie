package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.OpcionRespuesta;

public interface OpcionesRepuestaRepository extends CrudRepository<OpcionRespuesta, Integer>{
	
	@Query(value = "SELECT opr.* "
			+ "FROM opcion_respuestas opr "
			+ "INNER JOIN pregunta_opcion_respuesta popr "
			+ "ON opr.id=popr.id_opcion_respuesta "
			+ "WHERE popr.id_pregunta=:idPregunta", nativeQuery = true)
	List<OpcionRespuesta> findAllByEvaluacionAndPreguntaAndPersona(@Param("idPregunta") Integer idPregunta);
	
}
