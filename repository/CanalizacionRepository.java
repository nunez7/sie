package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Canalizacion;

public interface CanalizacionRepository extends CrudRepository<Canalizacion, Integer>{
	
	List<Canalizacion> findByAlumno(Alumno alumno);
	
	@Query(value = "SELECT c.* FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "WHERE ag.id_grupo=:idGrupo AND a.id =:idAlumno AND g.id_periodo =:idPeriodo", nativeQuery = true)
	List<Canalizacion> findByGrupoAndPeriodoAndAlumno(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo, @Param("idAlumno") Integer idAlumno);
	
	@Query(value = "SELECT c.* FROM canalizaciones c "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=c.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE ag.id_grupo=:idGrupo AND g.id_periodo =:idPeriodo", nativeQuery = true)
	List<Canalizacion> findByGrupoAndPeriodo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo);
	
}
