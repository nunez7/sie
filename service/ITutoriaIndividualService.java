package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.model.dtoimport.BajasUtNay;
import edu.mx.utdelacosta.model.dtoimport.CanalizacionesUtNay;
import edu.mx.utdelacosta.model.dtoimport.FocosAtencionUtNay;
import edu.mx.utdelacosta.model.dtoimport.FortalezasGrupoUtNay;
import edu.mx.utdelacosta.model.dtoimport.TemasGruposUtNay;
import edu.mx.utdelacosta.model.dtoimport.TutoriasUtNay;

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
	
	List<TutoriasUtNay> buscarTodoTutoriasUtNay();
	
	List<CanalizacionesUtNay> buscarCanalizacionesUtNay();
	
	List<TemasGruposUtNay> buscarTemasGrupalesByUtNay();
	
	List<FocosAtencionUtNay> buscarFocosAtencionByUtNay();
	
	List<FortalezasGrupoUtNay> buscarFortalezasGrupoByUtNay();
	
	List<BajasUtNay> buscarBajasByUtNay();
	
}
