package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.RespuestaCargaEvaluacion;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface IRespuestaCargaEvaluacionService {
	void guardar(RespuestaCargaEvaluacion respuestaCargaEvaluacion);
	RespuestaCargaEvaluacion buscarRespuestaPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona,  Integer idCarga);	
	List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona,  Integer idCarga);
	//cuenta los registros de respuestas por evvalucion de persona
	Integer contarPorIdPersonaYEvaluacionYIdCargaHoraria(Integer idPersona, Integer idEvaluacion, Integer idCargaHoraria);
	//suma de ponderaciones de respuestas
	Integer sumarPonderacionPorIdPreguntaIdCargaHorariaIdEvaluacion(Integer idPregunta, Integer idCargaHoraria, Integer idEvaluacion);
	//cuentas la cantidad de respuestas por carga 
	Integer contarPorIdPreguntaIdCargaHorariaIdEvaluacion(Integer idPregunta, Integer idCargaHoraria, Integer idEvaluacion);
	//cuenta los alumnos encuentados por profesor, carrera y periodo (asistente-director)
	Integer contarAlumnosPorIdProfesorIdCarreraIdPeriodo(Integer idProfesor, Integer idCarrera, Integer idPeriodo);
	//cuenta los alumnos por profesor y periodo (profesor)
	Integer contarAlumnosPorIdProfesorIdPeriodo(Integer idProfesor, Integer idPeriodo);
}
