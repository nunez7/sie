package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface IOpcionesRepuestaService {
	List<OpcionRespuestaDTO> buscarPorEvaluacionPreguntaYPersona(Integer idPregunta);
}
