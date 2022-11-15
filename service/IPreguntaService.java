package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.PreguntaFrecuente;
import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;

public interface IPreguntaService {
	PromedioPreguntaDTO ObtenerPromedioPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idCarga, Integer idGrupo);
	PromedioPreguntaDTO ObtenerPromedioEvaTuPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idGrupo);
	Pregunta buscarPorId(Integer id);

	List<PreguntaFrecuente> preguntasFrecuentesPorModulo(Integer idModulo);
}
