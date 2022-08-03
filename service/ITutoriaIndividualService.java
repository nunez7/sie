package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.util.imports.dto.BajasUtNay;
import edu.mx.utdelacosta.util.imports.dto.CanalizacionesUtNay;
import edu.mx.utdelacosta.util.imports.dto.FocosAtencionUtNay;
import edu.mx.utdelacosta.util.imports.dto.FortalezasGrupoUtNay;
import edu.mx.utdelacosta.util.imports.dto.TemasGruposUtNay;
import edu.mx.utdelacosta.util.imports.dto.TutoriasUtNay;

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
