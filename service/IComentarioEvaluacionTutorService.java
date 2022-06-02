package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.ComentarioEvaluacionTutor;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;

public interface IComentarioEvaluacionTutorService {
	
	void guardar(ComentarioEvaluacionTutor comentarioEvaluacionTutor);
	
	ComentarioEvaluacionTutor buscarComentarioPorPersona(Integer idPersona, Integer idGrupo, Integer idEvaluacion);
	
	List<ComentarioDTO> buscarComentariosPorGrupo(Integer idEvaluacion, Integer idGrupo);
}
