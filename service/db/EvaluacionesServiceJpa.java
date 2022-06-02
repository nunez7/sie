package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.repository.EvaluacionesRepository;
import edu.mx.utdelacosta.service.IEvaluacionesService;

@Service
public class EvaluacionesServiceJpa implements IEvaluacionesService{
	
	@Autowired
	private EvaluacionesRepository evalaucionRepo;
	
	@Override
	public Evaluacion buscar(Integer id) {		
		Optional<Evaluacion> optional = evalaucionRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
}
