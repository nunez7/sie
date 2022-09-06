package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Calificacion;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.dtoreport.CalificacionInstrumentoDTO;

public interface CalificacionRepository extends CrudRepository<Calificacion, Integer>{
	
	@Query(value = "SELECT ag.* FROM calificacion ag "
			+ "INNER JOIN mecanismo_instrumento g ON g.id=ag.id_mecanismo_instrumento "
			+ "WHERE ag.id_alumno=:idAlumno AND g.id_carga_horaria=:idCarga ", nativeQuery = true)
	List<Calificacion> findAllByAlumnoAndCargaHoraria(@Param("idAlumno") Integer idAlumno, @Param("idCarga") Integer idCarga);
	
	List<Calificacion> findByMecanismoInstrumento(MecanismoInstrumento mecanismoInstrumento);
	
	@Query(value = "select count(*) "
			+ "from calificacion "
			+ "where  id_mecanismo_instrumento = :idMecanismoInstrumento", nativeQuery = true)
	Integer countByIdMecanismoInstrumento(@Param("idMecanismoInstrumento") Integer idMecanismo);
	
	Optional<Calificacion> findById(Integer id);
	
	List<Calificacion> findByAlumno(Alumno alumno);
	 
    Calificacion findByAlumnoAndMecanismoInstrumento(Alumno alumno, MecanismoInstrumento mecanismoInstrumento);
    
    @Query(value = "SELECT COALESCE(SUM(c.valor),0) as ponderacion "
    		+ "FROM mecanismo_instrumento mi "
    		+ "LEFT JOIN calificacion c on mi.id=c.id_mecanismo_instrumento "
    		+ "WHERE mi.id_carga_horaria=:idCargaHoraria and mi.id_corte_evaluativo=:idCorteEvaluativo and mi.activo='True' and c.id_alumno=:idAlumno and mi.id_instrumento = :idInstrumento ",
    		//+ "GROUP BY mi.id, mi.id_instrumento, ponderacion", 
    		nativeQuery = true)
    CalificacionInstrumentoDTO findByCargaHorariaAndCorteEvaluativoAndInstrumento(@Param("idCargaHoraria") Integer idCargaHoraria, @Param("idCorteEvaluativo") Integer idCorteEvaluativo, @Param("idAlumno") Integer idAlumno, @Param("idInstrumento") Integer idMecanismo);
   
    
    @Query(value = "SELECT mi.id as mecanismo, mi.id_instrumento as instrumento, COALESCE(SUM(c.valor),0) as ponderacion "
    		+ "FROM mecanismo_instrumento mi "
    		+ "LEFT JOIN calificacion c on mi.id=c.id_mecanismo_instrumento "
    		+ "WHERE mi.id_carga_horaria=:idCargaHoraria and mi.id_corte_evaluativo=:idCorteEvaluativo and mi.activo='True' and c.id_alumno=:idAlumno "
    		+ "GROUP BY mi.id, mi.id_instrumento, ponderacion", nativeQuery = true)
    List<CalificacionInstrumentoDTO> findByCargaHorariaAndCorteEvaluativo(@Param("idCargaHoraria") Integer idCargaHoraria, @Param("idCorteEvaluativo") Integer idCorteEvaluativo, @Param("idAlumno") Integer idAlumno);
   
    
	@Query(value = "SELECT c.* "
			+ "FROM calificacion c "
			+ "INNER JOIN mecanismo_instrumento mi on mi.id=c.id_mecanismo_instrumento "
			+ "WHERE mi.id_carga_horaria=:idCargaHoraria and mi.id_corte_evaluativo=:idCorteEvaluativo and c.id_alumno=:idAlumno ", nativeQuery = true)
	List<Calificacion> findByIdAlumnoAndIdCargaHorariaAndIdCorteEvaluativo( @Param("idAlumno") Integer idAlumno, @Param("idCargaHoraria") Integer idCargaHoraria, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);

    @Query(value = "SELECT COALESCE(SUM(valor),0) as calificacion "
    		+ "FROM calificacion "
    		+ "WHERE id_alumno=:idAlumno AND id_mecanismo_instrumento=:idMecanismo ", nativeQuery = true)
    Float findCalificacionByAlumnoAndMecanismoInstrumento(@Param("idAlumno") Integer idAlumno, @Param("idMecanismo") Integer idMecanismo);
  
}
