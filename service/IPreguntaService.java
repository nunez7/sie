package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;

public interface IPreguntaService {
	PromedioPreguntaDTO ObtenerPromedioPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idCarga, Integer idGrupo);
	PromedioPreguntaDTO ObtenerPromedioEvaTuPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idGrupo);
}
