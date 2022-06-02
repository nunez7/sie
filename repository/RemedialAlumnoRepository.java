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
	
}
