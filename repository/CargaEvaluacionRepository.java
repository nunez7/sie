package edu.mx.utdelacosta.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.CargaEvaluacion;

public interface CargaEvaluacionRepository extends CrudRepository<CargaEvaluacion, Integer>{
	
	@Query(value="SELECT * FROM carga_evaluacion WHERE id_carga = :idCargaHoraria "
			+ "AND id_evaluacion = :idEvaluacion AND activo = 'True' LIMIT 1", nativeQuery = true)
	CargaEvaluacion findByCargaHorariaAndEvaluacion(@Param("idCargaHoraria") Integer idCargaHoraria, @Param("idEvaluacion") Integer idEvaluacion);
}
