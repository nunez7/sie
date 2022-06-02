package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.PlanEstudio;

public interface IPlanEstudioService {
	
	//busca los planes de estudio dependiendo al acceso de carreras de la persona
	List<PlanEstudio> buscarPorPersonaCarrera(Integer idPersona);
	//guarda el planes de estudio
	void guardar(PlanEstudio planEstudio);
	//busca el plan de estudio por su id
	PlanEstudio buscarPorId(Integer id);
	//busca los planes de estudio por carrera
	List<PlanEstudio> buscarPorCarrera(Integer idCarrera);
	
	List<PlanEstudio> buscarTodos();
	
	List<PlanEstudio> buscarTodosOrdenPorCarrerayNivel();
}
