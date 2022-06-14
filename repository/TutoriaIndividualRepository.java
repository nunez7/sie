package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;

public interface TutoriaIndividualRepository extends CrudRepository<TutoriaIndividual, Integer>{
	
	List<TutoriaIndividual> findByAlumnoAndGrupo(Alumno alumno, Grupo grupo);
	
}
