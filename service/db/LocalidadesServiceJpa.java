package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Municipio;
import edu.mx.utdelacosta.repository.LocalidadesRepository;
import edu.mx.utdelacosta.service.ILocalidadesService;

@Service
public class LocalidadesServiceJpa implements ILocalidadesService {
	
	@Autowired
	private LocalidadesRepository localidadRepo;

	@Override
	@Transactional(readOnly = true)
	public Localidad buscarPorid(Integer id) {
		// TODO Auto-generated method stub
		Optional<Localidad> optional = localidadRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Localidad> buscarPorMunicipio(Municipio m) {
		// TODO Auto-generated method stub
		return localidadRepo.findByMunicipio(m);
	}

}
