package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Servicio;
import edu.mx.utdelacosta.repository.ServicioRepository;
import edu.mx.utdelacosta.service.IServicioService;

@Service
public class ServicioServiceJpa implements IServicioService{
	
	@Autowired
	private ServicioRepository servicioRepository;

	@Override
	@Transactional(readOnly = true)
	public Servicio buscarPorId(Integer id) {
		Optional<Servicio> optional = servicioRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> buscarTodos() {
		return servicioRepository.findAll();
	}
	
}
