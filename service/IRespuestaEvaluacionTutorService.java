package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.RespuestaEvaluacionTutor;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface IRespuestaEvaluacionTutorService {
	void guardar(RespuestaEvaluacionTutor respuestaEvaluacionTutor);
	RespuestaEvaluacionTutor buscarRespuestaPorPregunta(Integer idPersona, Integer idEvaluacion, Integer idPregunta, Integer idGrupo);
	List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(Integer idPregunta, Integer idPersona, Integer idEvaluacion,Integer idGrupo);
	int contarEncuestadosPorGrupo(Integer idEvaluacion,Integer idGrupo);
	//cuenta las respuestas de la evaluacion por alumno,grupo y evaluacion
	Integer contarPorIdPersonaYIdGrupoYActivo(Integer idPersona, Integer idGrupo);
}
