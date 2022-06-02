package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.OpcionRespuesta;

public interface IOpcionesRepuestaService {
	List<OpcionRespuesta> buscarPorEvaluacionPreguntaYPersona(Integer idPregunta);
}
