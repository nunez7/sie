package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.PreguntaFrecuente;
import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;
import edu.mx.utdelacosta.repository.PreguntaFrecuenteRepository;
import edu.mx.utdelacosta.repository.PreguntaRepository;
import edu.mx.utdelacosta.service.IPreguntaService;

@Service
public class PreguntaServiceJpa implements IPreguntaService{

	@Autowired
	private PreguntaRepository preguntaRepo;
	
	@Autowired
	private PreguntaFrecuenteRepository preguntaRepository;

	@Override
	@Transactional(readOnly = true)
	public PromedioPreguntaDTO ObtenerPromedioPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idCarga,Integer idGrupo) {
		return preguntaRepo.findPromedioByPregunta(idEvaluacion, idPregunta, idCarga, idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public PromedioPreguntaDTO ObtenerPromedioEvaTuPorPregunta(Integer idEvaluacion, Integer idPregunta,Integer idGrupo) {
		return preguntaRepo.findPromedioEvaTuByPregunta(idEvaluacion, idPregunta, idGrupo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Pregunta buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Pregunta> optional = preguntaRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	public List<PreguntaFrecuente> preguntasFrecuentesPorModulo(Integer idModulo) {
		// TODO Auto-generated method stub
		return preguntaRepository.getAllByModulo(idModulo);
	}

}
