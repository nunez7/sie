package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.RespuestaEvaluacionInicial;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface IRespuestaEvaluacionInicialService {
	void guardar(RespuestaEvaluacionInicial respuesta);
	void eliminar(RespuestaEvaluacionInicial respuesta);
	List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(Integer idPregunta, Integer idPersona, Integer idEvaluacion,Integer idGrupo);
	List<OpcionRespuestaDTO> buscarRespuestaPorPregunta(Integer idPregunta, Integer idPersona, Integer idEvaluacion,Integer idGrupo);
	RespuestaEvaluacionInicial buscarPorId(Integer id);
	RespuestaEvaluacionInicial buscarRespuestaCerradaPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona, Integer idGrupo);
	RespuestaEvaluacionInicial buscarRespuestaAbiertaPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona, Integer idGrupo);
	List<RespuestaEvaluacionInicial> buscarRespuestaCerradaMultiplePorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona, Integer idGrupo);
	RespuestaEvaluacionInicial buscarRespuestaCerradaPorPreguntaYOpcionRespuesta(Integer idEvaluacion, Integer idPregunta, Integer idOpcionRespuesta, Integer idPersona, Integer idGrupo);
}
