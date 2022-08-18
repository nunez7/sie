package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.AsesoriaAlumno;

public interface AsesoriaAlumnoRepository extends CrudRepository<AsesoriaAlumno, Integer>{
	@Query(value = "SELECT DISTINCT(aa.*) FROM asesorias a "
			+ "INNER JOIN asesoria_alumno aa on a.id=aa.id_asesoria "
			+ "INNER JOIN alumnos_grupos ag on ag.id_alumno=aa.id_alumno "
			+ "INNER JOIN alumnos al on al.id=ag.id_alumno "
			+ "INNER JOIN personas p on p.id=al.id_persona "
			+ "INNER JOIN grupos g on g.id = ag.id_grupo "
			+ "WHERE ag.id_alumno=:idAlumno AND g.id_periodo =:idPeriodo AND a.id_tipo_asesoria=:tipo", nativeQuery = true)
	List<AsesoriaAlumno> findByAlumnoAndPeriodoAndTipo(@Param("idAlumno") Integer idAlumno, @Param("idPeriodo") Integer idPeriodo, @Param("tipo") Integer tipo);
	
	@Query(value = "SELECT DISTINCT(aa.*) FROM asesorias a "
			+ "INNER JOIN asesoria_alumno aa on a.id=aa.id_asesoria "
			+ "INNER JOIN alumnos_grupos ag on ag.id_alumno=aa.id_alumno "
			+ "INNER JOIN alumnos al on al.id=ag.id_alumno "
			+ "INNER JOIN personas p on p.id=al.id_persona "
			+ "INNER JOIN grupos g on g.id = ag.id_grupo "
			+ "WHERE g.id=:idGrupo AND ag.id_alumno=:idAlumno AND g.id_periodo =:idPeriodo AND a.id_tipo_asesoria=:tipo", nativeQuery = true)
	List<AsesoriaAlumno> findByGrupoAndAlumnoAndPeriodoAndTipo(@Param("idGrupo") Integer idGrupo, @Param("idAlumno") Integer idAlumno, @Param("idPeriodo") Integer idPeriodo, @Param("tipo") Integer tipo);
	
	@Query(value = "SELECT DISTINCT(aa.*) FROM asesorias a "
			+ "INNER JOIN asesoria_alumno aa on a.id=aa.id_asesoria "
			+ "INNER JOIN alumnos_grupos ag on ag.id_alumno=aa.id_alumno "
			+ "INNER JOIN alumnos al on al.id=ag.id_alumno "
			+ "INNER JOIN personas p on p.id=al.id_persona "
			+ "INNER JOIN grupos g on g.id = ag.id_grupo "
			+ "WHERE g.id=:idGrupo AND g.id_periodo =:idPeriodo AND a.id_tipo_asesoria=:tipo", nativeQuery = true)
	List<AsesoriaAlumno> findByGrupoAndPeriodoAndTipo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo, @Param("tipo") Integer tipo);
	
	@Query(value = "SELECT DISTINCT(aa.*) FROM asesorias a "
			+ "INNER JOIN asesoria_alumno aa on a.id=aa.id_asesoria "
			+ "INNER JOIN alumnos_grupos ag on ag.id_alumno=aa.id_alumno "
			+ "INNER JOIN alumnos al on al.id=ag.id_alumno "
			+ "INNER JOIN personas p on p.id=al.id_persona "
			+ "INNER JOIN grupos g on g.id = ag.id_grupo "
			+ "WHERE g.id=:idGrupo AND ag.id_alumno=:idAlumno AND g.id_periodo =:idPeriodo AND a.id_carga_horaria=:idCarga AND a.id_tipo_asesoria=:tipo", nativeQuery = true)
	List<AsesoriaAlumno> findByGrupoAndAlumnoAndPeriodoCargaAndTipo(@Param("idGrupo") Integer idGrupo, @Param("idAlumno") Integer idAlumno, @Param("idPeriodo") Integer idPeriodo, @Param("idCarga") Integer idCarga, @Param("tipo") Integer tipo);
	
	@Query(value = "SELECT DISTINCT(aa.*) FROM asesorias a "
			+ "INNER JOIN asesoria_alumno aa on a.id=aa.id_asesoria "
			+ "INNER JOIN alumnos_grupos ag on ag.id_alumno=aa.id_alumno "
			+ "INNER JOIN alumnos al on al.id=ag.id_alumno "
			+ "INNER JOIN personas p on p.id=al.id_persona "
			+ "INNER JOIN grupos g on g.id = ag.id_grupo "
			+ "WHERE g.id=:idGrupo AND g.id_periodo =:idPeriodo AND a.id_carga_horaria=:idCarga AND a.id_tipo_asesoria=:tipo", nativeQuery = true)
	List<AsesoriaAlumno> findByGrupoAndPeriodoCargaAndTipo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo, @Param("idCarga") Integer idCarga, @Param("tipo") Integer tipo);
}
