package edu.mx.utdelacosta.service.db;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.repository.EstadosRepository;
import edu.mx.utdelacosta.service.IEstadoService;

@Service
public class EstadosServiceJpa implements IEstadoService{

	@Autowired
	private EstadosRepository estadosRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<Estado> buscarTodos() {
		// TODO Auto-generated method stub
		return (List<Estado>) estadosRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Estado buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Estado> optional = estadosRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
