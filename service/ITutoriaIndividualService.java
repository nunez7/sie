package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;

public interface ITutoriaIndividualService {
	
	void guardar(TutoriaIndividual tutoriaIndividual);
	
	List<TutoriaIndividual> buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo);
	
}
