package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;
import edu.mx.utdelacosta.repository.PreguntaRepository;
import edu.mx.utdelacosta.service.IPreguntaService;

@Service
public class PreguntaServiceJpa implements IPreguntaService{

	@Autowired
	private PreguntaRepository preguntaRepo;

	@Override
	public PromedioPreguntaDTO ObtenerPromedioPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idCarga,Integer idGrupo) {
		return preguntaRepo.findPromedioByPregunta(idEvaluacion, idPregunta, idCarga, idGrupo);
	}

	@Override
	public PromedioPreguntaDTO ObtenerPromedioEvaTuPorPregunta(Integer idEvaluacion, Integer idPregunta,Integer idGrupo) {
		return preguntaRepo.findPromedioEvaTuByPregunta(idEvaluacion, idPregunta, idGrupo);
	}
	

}
