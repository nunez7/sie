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
}
