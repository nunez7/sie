package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.CalendarioEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.UnidadTematica;

public interface CalendarioEvaluacionRepository extends CrudRepository<CalendarioEvaluacion, Integer>{
	
	Optional<CalendarioEvaluacion> findById(Integer id);
	
	CalendarioEvaluacion findByCargaHorariaAndUnidadTematica(CargaHoraria carga, UnidadTematica unidad);
	
	List<CalendarioEvaluacion> findByCargaHorariaAndCorteEvaluativo(CargaHoraria carga, CorteEvaluativo corte);
	
	List<CalendarioEvaluacion> findByCargaHoraria(CargaHoraria cargaHoraria);
	
	@Query(value = "SELECT COUNT(DISTINCT(id_corte_evaluativo)) "
			+ "FROM calendario_evaluacion "
			+ "WHERE id_carga_horaria = :idCargaHoraria ", nativeQuery = true)
	Integer distinctByCorteEvaluativoByCargaHoraria(@Param("idCargaHoraria") Integer idCargaHoraria);
	
	@Query(value = "SELECT COUNT(*) "
			+ "FROM calendario_evaluacion "
			+ "WHERE id_carga_horaria=:idCarga AND id_corte_evaluativo=:idCorte", nativeQuery = true)
	Integer countByCargaHorariaAndCorteEvaluativo(@Param("idCarga") Integer idCarga, @Param("idCorte") Integer idCorte);

}
