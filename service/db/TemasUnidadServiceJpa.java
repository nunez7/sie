package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.TemaUnidad;
import edu.mx.utdelacosta.repository.TemasUnidadRepository;
import edu.mx.utdelacosta.service.ITemaUnidadService;

@Service
public class TemasUnidadServiceJpa implements ITemaUnidadService{

	@Autowired
	private TemasUnidadRepository temasUnidadRepository;
	
	@Override
	public void guardar(TemaUnidad temaUnidad) {
		// TODO Auto-generated method stub
		temasUnidadRepository.save(temaUnidad);
	}

	@Override
	@Transactional(readOnly = true)
	public TemaUnidad buscarPorId(Integer id) {
		Optional<TemaUnidad> temaUnidad = temasUnidadRepository.findById(id);
		if(temaUnidad.isPresent()) {
			return temaUnidad.get();
		}
		return null;
	}

}
