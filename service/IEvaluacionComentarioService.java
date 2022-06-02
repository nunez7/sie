package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.EvaluacionComentario;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;

public interface IEvaluacionComentarioService {
	void guardar(EvaluacionComentario evaluacionComentario);
	EvaluacionComentario buscarEvaluacionComentarioPorPersona( Integer idPersona, Integer idEvaluacion, Integer idCarga);
	List<ComentarioDTO> buscarComentariosPorPersona(Integer idEvaluacion, Integer idCarrera, Integer idProfesor, Integer idMateria, Integer idPeriodo);
}
