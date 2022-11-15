package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.PreguntaFrecuente;

public interface PreguntaFrecuenteRepository extends CrudRepository<PreguntaFrecuente, Integer>{
	
	@Query(value = "SELECT * FROM preguntas_frecuentes "
			+ "WHERE activo='True' AND id_modulo=:idModulo "
			+ "ORDER BY id", nativeQuery = true) 
	List<PreguntaFrecuente> getAllByModulo(@Param("idModulo") Integer idModulo);

}
