package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Instrumento;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.dto.MecanismoInstrumentoDTO;

public interface MecanismoInstrumentoRepository extends CrudRepository<MecanismoInstrumento, Integer>{
	
	List<MecanismoInstrumento> findAll();
	
	List<MecanismoInstrumento> findByIdCargaHorariaAndIdCorteEvaluativoAndActivo(Integer idCargaHoraria, Integer idCorteEvaluativo, Boolean activo);
	
	List<MecanismoInstrumento> findByIdCargaHorariaAndActivoOrderByIdCorteEvaluativo(Integer idCargaHoraria, Boolean activo);
	
	MecanismoInstrumento findByIdCargaHorariaAndIdCorteEvaluativoAndInstrumentoAndActivo(Integer idCargaHoraria, Integer idCorteEvaluativo, Instrumento instrumento, Boolean activo);
	
	Optional<MecanismoInstrumento> findByIdAndActivo(Integer id, Boolean activo);
	
	@Query(value = "SELECT SUM(ponderacion) FROM mecanismo_instrumento mi "
			+ "WHERE id_carga_horaria =:idCarga AND id_corte_evaluativo =:idCorte" ,nativeQuery = true)
	Integer sumPonderacionByIdCargaHorariaAndIdCorteEvaluativo(@Param("idCarga") Integer idCargaHoraria, @Param("idCorte") Integer idCorteEvaluativo);
	
	@Query(value = "SELECT (SELECT count(mi.id) FROM mecanismo_instrumento mi "
			+ "WHERE id_carga_horaria = :carga  AND id_corte_evaluativo = :corte) AS totalInstrumentos "
			+ ",(SELECT count(mi2.id) FROM mecanismo_instrumento mi2 "
			+ "INNER JOIN calificacion c ON mi2.id = c.id_mecanismo_instrumento "
			+ "WHERE id_carga_horaria = :carga  AND id_corte_evaluativo = :corte AND c.id_alumno = :alumno) AS calificacionInstrumentos ",nativeQuery = true)
	MecanismoInstrumentoDTO countMecanismoIntrumentoByCargaHorariaAndCorteEvaluativo(@Param("carga") Integer cargaHoraria, @Param("corte") Integer CorteEvaluativo, @Param("alumno") Integer alumno);
	
	//cuenta si hay mecanimos en una carga horaria de un parcial
	@Query(value = "SELECT COUNT(*) FROM mecanismo_instrumento WHERE id_carga_horaria = :idCargaHoraria "
			+ "AND id_corte_evaluativo = :idCorte AND activo = 'True'", nativeQuery = true)
	Integer countByIdCargaHorariaAndIdCorteEvaluativo(@Param("idCargaHoraria") Integer idCargaHoraria, @Param("idCorte") Integer idCorteEvaluativo);
}
