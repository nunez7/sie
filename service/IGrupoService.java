package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;

public interface IGrupoService {
	
	List<Grupo> buscarTodosDeAlumnosOrdenPorPeriodoDesc(Integer idAlumno);
	
	List<Grupo> buscarTodosDeAlumnosOrdenPorPeriodoAsc(Integer idAlumno);
	
	List<Grupo> buscarTodosDeAlumnosyGruposOrdenPorPeriodoAsc(Integer idAlumno, Integer idCarrera);
	
	Grupo buscarUltimoDeAlumno(Integer idAlumno);
	
	Grupo buscarPorId(Integer cveGrupo);
	
	Double obtenerPromedioAlumn(Integer idAlumno, Integer idGrupo);
	
	Integer buscarGrupoConsecutivo(String nombre, Integer periodo);
	
	List<Grupo> buscarTodoPorPeriodoOrdenPorId(Integer idPeriodo);
	
	List<Grupo> buscarPorPeriodoyCarrera(Integer idPerido, Integer idCarrera);
	
	void guardar(Grupo grupo);
	
	List<Grupo> buscarPorCarreraPeriodoAndPersonaCarrera(Integer persona, Integer periodo);
	
	Integer gruposPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo);
	
	List<Grupo> buscarPorCuatrimestreCarreraYPeriodo(Integer idCuatrimestre, Integer idCarrera, Integer idPeriodo);
	
	//Modificada
	//List<Grupo> buscarPorIdProfesor(Persona profesor);
	List<Grupo> buscarPorProfesorYPeriodoAsc(Persona profesor, Periodo periodo);
	
	List<Grupo> buscarPorProfesorYPeriodo(Integer idProfesor, Integer idPersona);
	
	Grupo buscarPorAlumnoPenultimoGrupo(Integer idAlumno);
	
	Boolean buscarPorGrupoYPeriodo(Integer idGrupo, Integer idPeriodo);
	
	Grupo buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo);
	
}
