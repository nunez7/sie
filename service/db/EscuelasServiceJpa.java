package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Escuela;
import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.repository.EscuelasRepository;
import edu.mx.utdelacosta.service.IEscuelaService;

@Service
public class EscuelasServiceJpa implements IEscuelaService{
	
	@Autowired
	EscuelasRepository escuelasRepo;
	
	@Override
	public List<Escuela> buscarTodoPorEstado(Estado estado) {
		return escuelasRepo.findByEstado(estado);
	}

	@Override
	public Escuela buscarPorId(Integer idEscuela) {
		Optional<Escuela> optional = escuelasRepo.findById(idEscuela);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
