package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.PeriodoInscripcion;
import edu.mx.utdelacosta.repository.PeriodoInscripcionRepository;
import edu.mx.utdelacosta.service.IPeriodoInscripcionService;

@Service
public class PeriodoInscripcionServiceJpa implements IPeriodoInscripcionService{
	
	@Autowired
	private PeriodoInscripcionRepository periodoRepository;

	@Override
	public List<PeriodoInscripcion> buscarActivos() {
		// TODO Auto-generated method stub
		return (List<PeriodoInscripcion>) periodoRepository.findAll(Sort.by(Order.desc("id")));
	}

	@Override
	public PeriodoInscripcion obtenerUltimo() {
		// TODO Auto-generated method stub
		return periodoRepository.findLast();
	}

	@Override
	public PeriodoInscripcion buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<PeriodoInscripcion> optional = periodoRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Integer ultimoId() {
		// TODO Auto-generated method stub
		return periodoRepository.getMaxId();
	}

}
