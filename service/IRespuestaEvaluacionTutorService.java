package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.RespuestaEvaluacionTutor;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface IRespuestaEvaluacionTutorService {
	void guardar(RespuestaEvaluacionTutor respuestaEvaluacionTutor);
	RespuestaEvaluacionTutor buscarRespuestaPorPregunta(Integer idPersona, Integer idEvaluacion, Integer idPregunta, Integer idGrupo);
	List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(Integer idPregunta, Integer idPersona, Integer idEvaluacion,Integer idGrupo);
	
	//cuenta las respuestas de la evaluacion por alumno,grupo y evaluacion
	Integer contarPorIdPersonaYIdGrupoYActivo(Integer idPersona, Integer idGrupo);
	//suma de ponderaciones de respuestas
	Integer sumarPonderacionPorIdPreguntaIdGrupoIdEvaluacion(Integer idPregunta, Integer idGrupo, Integer idEvaluacion);
	//cuentas las respuestas de la evaluacion por pregunta, grupo y evauacion
	Integer contarPorIdPreguntaIdGrupoIdEvaluacion(Integer idPregunta, Integer idGrupo, Integer idEvaluacion);
	//cuenta los alumnos encuentados por grupo y periodo
	Integer contarAlumnosPorIdGrupoIdPeriodo(Integer idGrupo, Integer idPeriodo);
	
}
