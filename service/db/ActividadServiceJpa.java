package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Actividad;
import edu.mx.utdelacosta.repository.ActividadRepository;
import edu.mx.utdelacosta.service.IActividadService;

@Service
public class ActividadServiceJpa implements IActividadService{

	@Autowired
	private ActividadRepository actividadRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Actividad> buscarTodas() {
		// TODO Auto-generated method stub
		return actividadRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Actividad buscarPorId(Integer id) {
		Optional<Actividad> optional = Optional.ofNullable(actividadRepository.getById(id));
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
