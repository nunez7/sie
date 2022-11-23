package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;

public interface RemedialAlumnoRepository extends CrudRepository<RemedialAlumno, Integer>{
	Optional<RemedialAlumno> findById(Integer id);
	
	RemedialAlumno findByAlumnoAndCargaHorariaAndRemedialAndPagado(Alumno alumno,CargaHoraria cargaHoraria, Remedial remedial, boolean pagado);
	
	RemedialAlumno findByAlumnoAndCargaHorariaAndRemedial(Alumno alumno,CargaHoraria cargaHoraria, Remedial remedial);
	
	RemedialAlumno findByAlumnoAndCargaHorariaAndRemedialAndCorteEvaluativo(Alumno alumno,CargaHoraria cargaHoraria, Remedial remedial, CorteEvaluativo corteEvaluativo);
	
	RemedialAlumno findByAlumnoAndCargaHorariaAndRemedialAndCorteEvaluativoAndPagado(Alumno alumno,CargaHoraria cargaHoraria, Remedial remedial, CorteEvaluativo corteEvaluativo, boolean pagado);
	
	@Query(value = "SELECT ra.* "
			+ "FROM remedial_alumno ra "
			+ "INNER JOIN cargas_horarias ca on ca.id=ra.id_carga_horaria "
			+ "WHERE ca.id_periodo= :idPeriodo and ra.id_alumno= :idAlumno", nativeQuery = true)
	List<RemedialAlumno> findByAlumnoAndPeriodo(@Param("idAlumno") Integer alumno,@Param("idPeriodo")  Integer idPeriodo);
	
	@Query(value="SELECT COUNT(*) "
			+ "FROM remedial_alumno ra "
			+ "INNER JOIN cargas_horarias cg on ra.id_carga_horaria=cg.id "
			+ "WHERE cg.id=:idCargaHoraria AND ra.tipo_remedial=:tipoRemedial ", nativeQuery = true)
	Integer countRemedialAlumno(@Param("idCargaHoraria") Integer idCargaHoraria, @Param("tipoRemedial") Integer tipoRemedial);

	
	@Query(value = "SELECT ra.* FROM cargas_horarias ch "
			+ "INNER JOIN remedial_alumno ra ON ch.id=ra.id_carga_horaria "
			+ "WHERE ch.id_grupo=:idGrupo AND ra.id_alumno=:idAlumno "
			+ "ORDER BY ra.id_corte ASC", nativeQuery = true)
	List<RemedialAlumno> findAllByGrupoAndAlumnoOrderByCorteEvaluativoAsc(@Param("idGrupo") Integer idGrupo, @Param("idAlumno") Integer idAlumno);
	
	List<RemedialAlumno> findByAlumnoAndCargaHorariaAndCorteEvaluativoOrderByRemedialDesc(Alumno alumno, CargaHoraria cargaHoraria, CorteEvaluativo cortesEvaluativo);
	
	@Query(value = "SELECT ra.* "
			+ "FROM remedial_alumno ra "
			+ "INNER JOIN cargas_horarias ca on ca.id=ra.id_carga_horaria "
			+ "WHERE ca.id_periodo= :idPeriodo and ra.id_alumno= :idAlumno AND tipo_remedial=:tipo", nativeQuery = true)
	List<RemedialAlumno> findByAlumnoAndPeriodo(@Param("idAlumno") Integer alumno,@Param("idPeriodo")  Integer idPeriodo, @Param("tipo") Integer tipoRemedial);
	
	@Query(value="SELECT COUNT(*) "
			+ "FROM remedial_alumno ra "
			+ "INNER JOIN cargas_horarias cg on ra.id_carga_horaria=cg.id "
			+ "WHERE cg.id=:idCargaHoraria AND ra.tipo_remedial=:tipoRemedial AND id_corte=:idCorteEvaluativo ", nativeQuery = true)
	Integer countRemedialAlumnoByCargaHorariaAndRemedialAndCorteEvalautivo(@Param("idCargaHoraria") Integer idCargaHoraria, @Param("tipoRemedial") Integer tipoRemedial, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
	
	@Query(value="SELECT * FROM remedial_alumno "
			+ "WHERE id_alumno=:idAlumno AND id_carga_horaria=:idCarga AND id_corte=:idCorte "
			+ "ORDER BY tipo_remedial DESC "
			+ "LIMIT 1 ", nativeQuery = true)
	RemedialAlumno findByLastAlumnoAndCargaHorariaAndCorteEvaluativo(@Param("idAlumno") Integer idAlumno, @Param("idCarga") Integer idCarga, @Param("idCorte") Integer idCorte);

	@Query(value="SELECT CAST(COUNT(DISTINCT ra.id)AS INT)AS cantidad "
			+ "FROM remedial_alumno ra "
			+ "INNER JOIN cargas_horarias cg on ra.id_carga_horaria=cg.id "
			+ "INNER JOIN grupos g ON g.id=cg.id_grupo "
			+ "WHERE g.id_carrera=:idCarrera AND ra.tipo_remedial=:tipoRemedial AND ra.id_corte=:idCorteEvaluativo", nativeQuery = true)
	Integer countByCarreraAndCorteEvaluativo(@Param("idCarrera") Integer idCarrera, @Param("tipoRemedial") Integer tipoRemedial, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
	
	//trae la calificacion de un remedial o extraordinario de un corte en caso de tenerlo
	@Query(value = "SELECT COALESCE(SUM(t.numero), 0) AS remedial FROM remedial_alumno ra "
			+ "INNER JOIN testimonios t on t.id = ra.tipo_testimonio "
			+ "WHERE id_alumno = :idAlumno AND id_carga_horaria = :idCargaHoraria "
			+ "AND id_corte = :idCorte AND tipo_remedial = :tipo", nativeQuery = true)
	Integer findByAlumnoAndCargaHorariaAndCorteEvaluativoAndTipo(@Param("idAlumno") Integer idAlumno, @Param("idCargaHoraria") Integer idCargaHoraria, @Param("idCorte") Integer idCorte, 
				@Param("tipo") Integer tipo);
	
	@Query(value="SELECT CAST(COUNT(DISTINCT ra.id)AS INT)AS cantidad "
			+ "FROM remedial_alumno ra "
			+ "INNER JOIN cargas_horarias cg on ra.id_carga_horaria=cg.id "
			+ "INNER JOIN grupos g ON g.id=cg.id_grupo "
			+ "WHERE g.id_carrera=:idCarrera AND ra.tipo_remedial=:tipoRemedial "
			+ "AND ra.id_corte=:idCorteEvaluativo AND g.id_turno = :idTurno ", nativeQuery = true)
	Integer countByCarreraAndCorteEvaluativoAndTurno(@Param("idCarrera") Integer idCarrera, @Param("tipoRemedial") Integer tipoRemedial, @Param("idCorteEvaluativo") Integer idCorteEvaluativo, @Param("idTurno") Integer idTurno);
	
	@Query(value = "SELECT EXISTS (SELECT ra.id FROM remedial_alumno ra "
			+ "	WHERE id_carga_horaria = :carga AND tipo_remedial = :tipo_remedial "
			+ "	AND id_corte = :corte AND ra.id_alumno = :alumno AND ra.tipo_testimonio IS NOT NULL) " ,  nativeQuery = true)
	Boolean existsRemedialAlumno(@Param("alumno")Integer idAlumno, @Param("carga") Integer idCargaHoraria,@Param("corte") Integer idCorteEvaluativo, @Param("tipo_remedial") Integer tipoRemedial);
	
	@Query(value = "SELECT COUNT(*) FROM remedial_alumno "
			+ "WHERE id_alumno = :idAlumno AND tipo_remedial = :tipo "
			+ "AND id_corte = :idCorte ", nativeQuery = true)
	Integer countByAlumnoAndCorteEvaluativoAndTipoRemedial(@Param("idAlumno") Integer idAlumno, @Param("idCorte") Integer idCorteEvaluativo,
				@Param("tipo") Integer tipo);
}
