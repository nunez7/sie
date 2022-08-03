package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.ProgramacionTutoria;

public interface ProgramacionTutoriaRepository extends CrudRepository<ProgramacionTutoria, Integer>{

	List<ProgramacionTutoria> findByAlumnoAndGrupo(Alumno alumno, Grupo grupo);		
	
}
