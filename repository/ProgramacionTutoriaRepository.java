package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.ProgramacionTutoria;

public interface ProgramacionTutoriaRepository extends CrudRepository<ProgramacionTutoria, Integer>{

	List<ProgramacionTutoria> findByAlumnoAndGrupoOrderByFechaAsc(Alumno alumno, Grupo grupo);	
	
	@Query(value = "SELECT pt.* FROM programacion_tutoria pt "
			+ "INNER JOIN alumnos a ON pt.id_alumno = a.id "
			+ "WHERE id_grupo = :idGrupo AND a.estatus = 1 "
			+ "ORDER BY fecha ASC", nativeQuery = true)
	List<ProgramacionTutoria> findByGrupoOrderByFechaAsc(@Param("idGrupo") Grupo grupo);
	
	List<ProgramacionTutoria> findByAlumnoOrderByFechaAsc(Alumno alumno);
	
}
