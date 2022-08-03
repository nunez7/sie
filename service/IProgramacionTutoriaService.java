package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.ProgramacionTutoria;

public interface IProgramacionTutoriaService {

	void guardar(ProgramacionTutoria tutoria);
	
	List<ProgramacionTutoria> buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo);		
	
}
