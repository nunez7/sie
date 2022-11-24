package edu.mx.utdelacosta.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.TestimonioCorte;

public interface TestimonioCorteRepository extends CrudRepository<TestimonioCorte, Integer>{
	TestimonioCorte findByAlumnoAndCargaHorariaAndCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria, Integer idCorteEvaluativo);
	
	@Query(value = "SELECT COUNT(DISTINCT a.id) AS sin_derecho "
			+ "FROM testimonio_corte tc "
			+ "INNER JOIN alumnos a ON tc.id_alumno = a.id "
			+ "INNER JOIN cargas_horarias ch ON ch.id=tc.id_carga_horaria "
			+ "WHERE tc.sin_derecho=true AND ch.activo='True' "
			+ "AND tc.id_carga_horaria=:idCargaHoraria AND tc.id_corte_evaluativo=:idCorteEvaluativo ", nativeQuery = true)
	Integer countAlumnosSD(@Param("idCargaHoraria") Integer idCargaHoraria, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
	
	@Query(value = "SELECT  COUNT(DISTINCT a.id) as sin_derecho "
			+ "FROM testimonio_corte tc "
			+ "INNER JOIN alumnos a ON tc.id_alumno = a.id "
			+ "INNER JOIN cargas_horarias ch ON ch.id=tc.id_carga_horaria "
			+ "INNER JOIN grupos g ON g.id=ch.id_grupo "
			+ "WHERE tc.sin_derecho=true AND ch.activo='True' AND g.id_carrera=:idCarrera AND tc.id_corte_evaluativo=:idCorteEvaluativo ", nativeQuery = true)
	Integer countAlumnosSDByCarreraAndCorte(@Param("idCarrera") Integer idCarrera, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
	
	@Query(value = "SELECT  COUNT(DISTINCT a.id) as sin_derecho "
			+ "FROM testimonio_corte tc "
			+ "INNER JOIN alumnos a ON tc.id_alumno = a.id "
			+ "INNER JOIN cargas_horarias ch ON ch.id=tc.id_carga_horaria "
			+ "INNER JOIN grupos g ON g.id=ch.id_grupo "
			+ "WHERE tc.sin_derecho=true AND ch.activo='True' AND g.id_carrera=:idCarrera ", nativeQuery = true)
	Integer countAlumnosSDByCarrera(@Param("idCarrera") Integer idCarrera);
	
	@Query(value = "SELECT CAST(sin_derecho AS INT) "
			+ "FROM testimonio_corte tc "
			+ "WHERE id_alumno=:idAlumno AND id_carga_horaria=:idCargaHoraria AND id_corte_evaluativo=:idCorteEvaluativo", nativeQuery = true)
	Integer checkSDByAlumnoAndCargaHorariaAndCorteEvaluativo(@Param("idAlumno") Integer idAlumno, @Param("idCargaHoraria") Integer idCargaHoraria, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
	
	@Query(value = "SELECT  COUNT(DISTINCT tc.id) as sin_derecho "
			+ "FROM testimonio_corte tc "
			+ "INNER JOIN cargas_horarias ch ON ch.id=tc.id_carga_horaria "
			+ "INNER JOIN grupos g ON g.id=ch.id_grupo "
			+ "WHERE tc.sin_derecho=true AND ch.activo='True' AND g.id_carrera=:idCarrera AND tc.id_corte_evaluativo=:idCorteEvaluativo "
			+ "AND g.id_turno = :idTurno ", nativeQuery = true)
	Integer countAlumnosSDByCarreraAndTurno(@Param("idCarrera") Integer idCarrera, @Param("idCorteEvaluativo") Integer idCorteEvaluativo, @Param("idTurno") Integer idTurno);
}
