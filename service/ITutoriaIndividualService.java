package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;

public interface ITutoriaIndividualService {
	
	void guardar(TutoriaIndividual tutoriaIndividual);
	
	TutoriaIndividual buscarPorId(Integer id);
	
	List<TutoriaIndividual> buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo);
	
	List<TutoriaIndividual> buscarUltimas5PorAlumno(Alumno alumno);
	
	List<TutoriaIndividual> buscarPorAlumno(Alumno alumno);
	
	TutoriaIndividual ultimoRegistro();
	
	List<TutoriaIndividual> buscarEntreFechasPorGrupoYAlumno(Integer idGrupo, Integer idAlumno, Date fechaInicio, Date fechaFin);
	
}
