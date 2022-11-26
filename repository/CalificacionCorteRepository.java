package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CalificacionCorte;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;

public interface CalificacionCorteRepository extends CrudRepository<CalificacionCorte, Integer> {
	List<CalificacionCorte> findByCargaHorariaAndAlumnoOrderByCorteEvaluativo(CargaHoraria cargaHoraria, Alumno alumno);

	CalificacionCorte findByAlumnoAndCargaHorariaAndCorteEvaluativo(Alumno alumno, CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo);

	List<CalificacionCorte> findByCargaHorariaAndCorteEvaluativo(CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo);

	// suma las calificaciones por materia y corte de un grupo
	@Query(value = "SELECT COALESCE(ROUND(SUM(valor),2),0) as calificacion " + "FROM calificacion_corte "
			+ "WHERE id_carga_horaria=:cargaHoraria and id_corte_evaluativo=:corte", nativeQuery = true)
	Double findByIdCorteEvaluativoAndIdCargaHoraria(@Param("cargaHoraria") Integer idCargaHoraria,
			@Param("corte") Integer idCorteEvaluativo);

	// calificacion de un alumno por materia y corte
	@Query(value = "SELECT COALESCE(ROUND(SUM(valor),2),0) as calificacion " + "FROM calificacion_corte "
			+ "WHERE id_alumno = :alumno AND id_carga_horaria=:cargaHoraria "
			+ "AND id_corte_evaluativo= :corte ", nativeQuery = true)
	Double findByAlumnoAndCargaHorariaAndCorte(@Param("alumno") Integer idAlumno,
			@Param("cargaHoraria") Integer idCargaHoraria, @Param("corte") Integer idCorte);
	
	@Query(value = "SELECT CAST(cc.editable AS INT ) "
			+ "FROM calificacion_corte cc "
			+ "WHERE cc.id_alumno = :alumno AND cc.id_corte_evaluativo = :corte AND cc.id_carga_horaria = :carga", nativeQuery = true)
	Integer findRevalidadaByAlumnoAndCargaHorariaAndCorteEvaluativo(@Param("alumno")Integer alumno, @Param("carga") Integer cargaHoraria, @Param("corte") Integer corteEvaluativo);
	
	@Query(value = "SELECT COALESCE(SUM(c.valor  * (mi.ponderacion/10)/10) ,0)  AS ponderacion "
			+ "FROM mecanismo_instrumento mi "
			+ "INNER JOIN calificacion c ON c.id_mecanismo_instrumento = mi.id "
			+ "WHERE mi.id_carga_horaria = :carga AND id_corte_evaluativo = :corte AND c.id_alumno = :alumno", nativeQuery = true)
	Float findPromedioCorteByMecanismoIntrumentoAndCarga(@Param("carga")Integer cargaHoraria,@Param("corte") Integer corteEvaluativo,@Param("alumno") Integer alumno);
	
	@Query(value = "SELECT COUNT(*) FROM calificacion_corte cc "
			+ "INNER JOIN alumnos a ON a.id = cc.id_alumno "
			+ "WHERE id_carga_horaria = :idCargaHoraria AND id_corte_evaluativo = :idCorte "
			+ "AND a.estatus = 1", nativeQuery = true)
	Integer countByIdCargaHorariaAndIdCorteEvaluativo(@Param("idCargaHoraria")Integer idCargaHoraria, @Param("idCorte") Integer idCorte);

	@Query(value = "SELECT COUNT(DISTINCT(cc.id)) "
			+ "	FROM calificacion_corte cc "
			+ "	WHERE id_corte_evaluativo =:corte AND id_carga_horaria =:carga ", nativeQuery = true)
	Integer countCalificacionByCargaAndCorte(@Param("carga")CargaHoraria carga, @Param("corte") CorteEvaluativo corte);

}
