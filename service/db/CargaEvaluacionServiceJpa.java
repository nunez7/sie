package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.CargaEvaluacion;
import edu.mx.utdelacosta.repository.CargaEvaluacionRepository;
import edu.mx.utdelacosta.service.ICargaEvaluacionService;

@Service
public class CargaEvaluacionServiceJpa implements ICargaEvaluacionService{

	@Autowired
	private CargaEvaluacionRepository cargaEvaRepo;
	
	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public CargaEvaluacion buscarPorCargaHorariaYEvaluacion(Integer idCargaHoraria, Integer idEvaluacion) {
		// TODO Auto-generated method stub
		return cargaEvaRepo.findByCargaHorariaAndEvaluacion(idCargaHoraria, idEvaluacion);
	}

}
