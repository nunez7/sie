package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Instrumento;
import edu.mx.utdelacosta.model.MecanismoInstrumento;

public interface MecanismoInstrumentoRepository extends CrudRepository<MecanismoInstrumento, Integer>{
	
	List<MecanismoInstrumento> findAll();
	
	List<MecanismoInstrumento> findByIdCargaHorariaAndIdCorteEvaluativoAndActivo(Integer idCargaHoraria, Integer idCorteEvaluativo, Boolean activo);
	
	List<MecanismoInstrumento> findByIdCargaHorariaAndActivoOrderByIdCorteEvaluativo(Integer idCargaHoraria, Boolean activo);
	
	MecanismoInstrumento findByIdCargaHorariaAndIdCorteEvaluativoAndInstrumentoAndActivo(Integer idCargaHoraria, Integer idCorteEvaluativo, Instrumento instrumento, Boolean activo);
	
	Optional<MecanismoInstrumento> findByIdAndActivo(Integer id, Boolean activo);
	
	@Query(value = "SELECT SUM(ponderacion) FROM mecanismo_instrumento mi "
			+ "WHERE id_carga_horaria =:idCarga AND id_corte_evaluativo =:idCorte" ,nativeQuery = true)
	Integer sumPonderacionByIdCargaHorariaAndIdCorteEvaluativo(@Param("idCarga") Integer idCargaHoraria, @Param("idCorte") Integer idCorteEvaluativo);
	
}
