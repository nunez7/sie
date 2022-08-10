package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Grupo;
public interface AlumnoGrupoRepository extends CrudRepository<AlumnoGrupo, Integer>{
	AlumnoGrupo findByAlumnoAndGrupo(Alumno alumno, Grupo grupo);
	
	@Query(value = "SELECT ag.* FROM alumnos_grupos ag "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE ag.id_alumno=:idAlumno AND g.id_periodo=:idPeriodo "
			+ "AND ag.activo = 'True'", nativeQuery = true)
	AlumnoGrupo checkInscrito(@Param("idAlumno") Integer idAlumno, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT * FROM alumnos_grupos WHERE id_alumno = :alumno AND id_grupo = :grupo", nativeQuery = true)
	AlumnoGrupo findByIdAlumnoAndIdGrupo(@Param("alumno") Integer idAlumno, @Param("grupo") Integer grupo);
	
	@Query(value = "SELECT * FROM alumnos_grupos WHERE id_alumno = :alumno ORDER BY id DESC", nativeQuery = true)
	List<AlumnoGrupo> findAllByIdAlumnoDesc(@Param("alumno") Integer idAlumno);
	
	@Query(value = "SELECT ag.* FROM alumnos_grupos ag "
			   + "INNER JOIN grupos g on ag.id_grupo=g.id "
			   + "WHERE id_alumno =:alumno AND g.id_periodo =:periodo "
			   + "AND ag.activo = 'True'", nativeQuery = true)
			 List<AlumnoGrupo> findByIdAlumnoAndIdPeriodo(@Param("alumno") Integer idAlumno, @Param("periodo") Integer idPeriodo);

	@Query(value = "SELECT COUNT(ag.*) FROM alumnos_grupos ag "
				+ "WHERE ag.id_grupo  = :idGrupo AND ag.activo = 'True' ",nativeQuery = true)
	Integer countAlumnosByGrupo(@Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT ag.* FROM grupos g "
			+ "	INNER JOIN alumnos_grupos ag on ag.id_grupo=g.id "
			+ "	WHERE ag.id_alumno =:idAlumno LIMIT 1", nativeQuery = true)
	AlumnoGrupo findFirstGrupoProspecto(@Param("idAlumno") Integer idAlumno);
	
	@Query(value = "SELECT COUNT(DISTINCT(algr.*)) "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos algr on algr.id_alumno = a.id "
			+ "INNER JOIN grupos gr on gr.id = algr.id_grupo "
			+ "INNER JOIN pago_cuatrimestre pc on pc.id_alumno_grupo=algr.id "
			+ "WHERE gr.id_periodo=:periodo and gr.id_cuatrimestre = :cuatrimestre " ,nativeQuery = true)
	Integer countByPeriodoAndCuatrimestreAndPagoGenerado(@Param("periodo")Integer idPeriodo, @Param("cuatrimestre") Integer idCuatrimestre);
	
	@Query(value="SELECT algr.* "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos algr on algr.id_alumno = a.id "
			+ "LEFT JOIN grupos gr on gr.id = algr.id_grupo "
			+ "WHERE gr.id_periodo=:periodo and gr.id_cuatrimestre = :cuatrimestre "
			+ "ORDER BY algr.id DESC ", nativeQuery = true)
	List<AlumnoGrupo> findByPeriodoAndCuatrimestre(@Param("periodo")Integer idPeriodo, @Param("cuatrimestre") Integer idCuatrimestre);
	
	@Query(value = "SELECT count(distinct(algr.id)) " + "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos algr on algr.id_alumno = a.id "
			+ "LEFT JOIN grupos gr on gr.id = algr.id_grupo "
			+ "WHERE gr.id_periodo=:periodo and gr.id_cuatrimestre = :cuatrimestre ", nativeQuery = true)
	Integer contarPorPeriodoAndCuatrimestre(@Param("periodo") Integer idPeriodo,
			@Param("cuatrimestre") Integer idCuatrimestre);

}
