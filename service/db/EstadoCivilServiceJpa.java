package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.EstadoCivil;
import edu.mx.utdelacosta.repository.EstadoCivilRepository;
import edu.mx.utdelacosta.service.IEstadoCivilService;

@Service
public class EstadoCivilServiceJpa implements IEstadoCivilService{
	
	@Autowired
	private EstadoCivilRepository estadoRepository;

	@Override
	@Transactional(readOnly = true)
	public EstadoCivil buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<EstadoCivil> optional = estadoRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public EstadoCivil buscarPorDescripcion(String descripcion) {
		// TODO Auto-generated method stub
		Optional<EstadoCivil> optional = estadoRepository.findByDescripcion(descripcion);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EstadoCivil> buscarTodos() {
		// TODO Auto-generated method stub
		return (List<EstadoCivil>) estadoRepository.findAll();
	}

}
