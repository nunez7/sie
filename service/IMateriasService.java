package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.PlanEstudio;

public interface IMateriasService {
	//busca la materia por id
	Materia buscarPorId(Integer id);
	//guarda la materia
	void guardar(Materia materia);
	//busca las materias por plan de estudio y cuatrimestre
	List<Materia> buscarPorGrupoYCarrera(Integer grupo, Integer carrera);
	//busca las materias por plan de estudio y cuatrimestre activas
	List<Materia> buscarPorGrupoYCarreraYActivos(Integer grupo, Integer carrera);
	//busca todas las materias 
	List<Materia> buscarTodas();
	//busca las materias por plan de estudio
	Materia buscarPorPlanEstudioYNombre(PlanEstudio plan, String nombre);
	//busca si hay una materia por plan de estudio y abreviatura
	Materia buscarPorPlanEstudioYAbreviatura(PlanEstudio plan, String abreviatura);
	
	List<Materia> buscarPorCargaActivaEnGrupo(Integer grupo, Integer carrera);
	
	List<Materia> buscarPorCarreraProfesorYPeriodo(Integer idCarrera,Integer idProfesor,Integer idPeriodo);
}
