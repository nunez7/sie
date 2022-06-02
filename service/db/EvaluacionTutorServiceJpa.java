package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.model.EvaluacionTutor;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.repository.EvaluacionTutorRepository;
import edu.mx.utdelacosta.service.IEvaluacionTutorService;

@Service
public class EvaluacionTutorServiceJpa implements IEvaluacionTutorService{

	@Autowired
	private EvaluacionTutorRepository evaTutorRepo;

	@Override
	public void guardar(EvaluacionTutor evaluacionTutor) {
		evaTutorRepo.save(evaluacionTutor);
	}

	@Override
	public EvaluacionTutor buscarPorEvaluacionYGrupo(Evaluacion evaluacion, Grupo grupo) {
		return evaTutorRepo.findByEvaluacionAndGrupo(evaluacion, grupo);
	}

}
