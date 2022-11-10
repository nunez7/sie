package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Canalizacion;
import edu.mx.utdelacosta.model.TutoriaIndividual;

public interface CanalizacionRepository extends CrudRepository<Canalizacion, Integer>{
	
	List<Canalizacion> findByAlumno(Alumno alumno);
	
	Canalizacion findByTutoriaIndividual(TutoriaIndividual tutoria);
	
	@Query(value = "SELECT c.* FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "WHERE ag.id_grupo=:idGrupo AND a.id =:idAlumno AND g.id_periodo =:idPeriodo ORDER BY c.fecha_canalizar", nativeQuery = true)
	List<Canalizacion> findByGrupoAndPeriodoAndAlumno(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo, @Param("idAlumno") Integer idAlumno);
	
	@Query(value = "SELECT c.* FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE ag.id_grupo=:idGrupo AND g.id_periodo =:idPeriodo ORDER BY c.fecha_canalizar", nativeQuery = true)
	List<Canalizacion> findByGrupoAndPeriodo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT c.* FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo ORDER BY g.nombre", nativeQuery = true)
	List<Canalizacion> findByCarreraAndPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT COUNT(c.*) FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo AND g.id_turno=:idTurno", nativeQuery = true)
	Integer findTotalByCarreraAndPeriodoAndTurno(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idTurno") Integer idTurno);
	
	@Query(value = "SELECT COUNT(DISTINCT c.id_alumno)  FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo AND g.id_turno=:idTurno", nativeQuery = true)
	Integer findTotalDistinctAlumnoByCarreraAndPeriodoAndTurno(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idTurno") Integer idTurno);
	
	@Query(value = "SELECT c.* FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "WHERE ag.id_grupo=:idGrupo AND a.id =:idAlumno AND g.id_periodo =:idPeriodo AND c.id_servicio=:idServicio ORDER BY c.fecha_canalizar", nativeQuery = true)
	List<Canalizacion> findByGrupoAndPeriodoAndAlumnoAndServicio(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo, @Param("idAlumno") Integer idAlumno, @Param("idServicio") Integer idServicio);
	
	@Query(value = "SELECT c.* FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE ag.id_grupo=:idGrupo AND g.id_periodo =:idPeriodo AND c.id_servicio=:idServicio ORDER BY c.fecha_canalizar", nativeQuery = true)
	List<Canalizacion> findByGrupoAndPeriodoAndServicio(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo, @Param("idServicio") Integer idServicio);
	
	@Query(value = "SELECT c.* FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo AND c.id_servicio=:idServicio ORDER BY g.nombre", nativeQuery = true)
	List<Canalizacion> findByCarreraAndPeriodoAndServicio(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idServicio") Integer idServicio);
}
