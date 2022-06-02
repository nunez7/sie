package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.CargaEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Evaluacion;
import edu.mx.utdelacosta.repository.CargaEvaluacionRepository;
import edu.mx.utdelacosta.service.ICargaEvaluacionService;

@Service
public class CargaEvaluacionServiceJpa implements ICargaEvaluacionService{

	@Autowired
	private CargaEvaluacionRepository cargaEvaRepo;
	
	@Override
	public CargaEvaluacion buscarCargaEvaluacion(Integer id) {
		Optional<CargaEvaluacion> optional = cargaEvaRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(CargaEvaluacion cargaEvaluacion) {
		cargaEvaRepo.save(cargaEvaluacion);		
	}

	@Override
	public CargaEvaluacion buscarPorCargaHorariaYEvaluacion(CargaHoraria cargaHoraria, Evaluacion evaluacion) {
		// TODO Auto-generated method stub
		return cargaEvaRepo.findByCargaHorariaAndEvaluacion(cargaHoraria, evaluacion);
	}

}
