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
	
	List<TutoriaIndividual> buscarPorAlumnoYValidada(Alumno alumno, Boolean validada);
	
	TutoriaIndividual ultimoRegistro();
	
	List<TutoriaIndividual> buscarEntreFechasPorGrupoYAlumno(Integer idGrupo, Integer idAlumno, Date fechaInicio, Date fechaFin);
	
	List<TutoriaIndividual> buscarEntreFechasPorGrupo(Integer idGrupo, Date fechaInicio, Date fechaFin);
	
	List<TutoriaIndividual> buscarEntreFechasPorCarrera(Integer idCarrera, Integer idPeriodo, Date fechaInicio, Date fechaFin);
	
	Integer totalPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
	Integer totalDistinctAlumnoPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
		
}
