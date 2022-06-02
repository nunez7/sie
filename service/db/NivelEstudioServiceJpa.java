package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.NivelEstudio;
import edu.mx.utdelacosta.repository.NivelEstudioRepository;
import edu.mx.utdelacosta.service.INivelEstudioService;

@Service
public class NivelEstudioServiceJpa implements INivelEstudioService{
	
	@Autowired
	private NivelEstudioRepository nivelERepository;

	@Override
	public NivelEstudio buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<NivelEstudio> optional = nivelERepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<NivelEstudio> buscarTodos() {
		// TODO Auto-generated method stub
		return (List<NivelEstudio>) nivelERepository.findAll();
	}

}
