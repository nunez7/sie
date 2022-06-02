package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Municipio;
import edu.mx.utdelacosta.repository.MunicipiosRepository;
import edu.mx.utdelacosta.service.IMunicipiosService;

@Service
public class MunicipiosServiceJpa implements IMunicipiosService{

	@Autowired
	private MunicipiosRepository municipiosRepo;
	
	@Override
	public List<Municipio> buscarPorEstado(Estado estado) {
		return municipiosRepo.findByEstado(estado);
	}

	@Override
	public Municipio buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Municipio> optional = municipiosRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
