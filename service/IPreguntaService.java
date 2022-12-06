package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.PreguntaFrecuente;

public interface IPreguntaService {
	
	Pregunta buscarPorId(Integer id);

	List<PreguntaFrecuente> preguntasFrecuentesPorModulo(Integer idModulo);
	
	List<Integer> buscarPorIdEvaluacion(Integer idEvaluacion);
}
