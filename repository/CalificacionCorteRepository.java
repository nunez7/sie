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
}
