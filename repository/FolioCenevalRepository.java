package edu.mx.utdelacosta.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.FolioCeneval;

public interface FolioCenevalRepository extends CrudRepository<FolioCeneval, Integer> {
	FolioCeneval findByAlumno(Alumno alumno);
	
	@Query(value = "SELECT fc.* "
			+ "FROM folios_ceneval fc "
			+ "INNER JOIN alumnos a ON a.id=fc.id_alumno "
			+ "WHERE a.matricula=:matricula ", nativeQuery = true)
	FolioCeneval findByMatricula(@Param("matricula") String matricula);
}
