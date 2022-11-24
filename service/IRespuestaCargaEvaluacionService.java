package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.RespuestaCargaEvaluacion;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface IRespuestaCargaEvaluacionService {
	void guardar(RespuestaCargaEvaluacion respuestaCargaEvaluacion);
	RespuestaCargaEvaluacion buscarRespuestaPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona,  Integer idCarga);	
	List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona,  Integer idCarga);
	Integer contarPorGrupoYCargaHoraria(Integer idEvaluacion, Integer idGrupo, Integer idCarga);
	//cuenta los registros de respuestas por evvalucion de persona
	Integer contarPorIdPersonaYEvaluacionYIdCargaHoraria(Integer idPersona, Integer idEvaluacion, Integer idCargaHoraria);
}
