package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.ProrrogaAutoriza;
import edu.mx.utdelacosta.repository.ProrrogaAutorizaRepository;
import edu.mx.utdelacosta.service.IProrrogaAutoriza;

@Service
public class ProrrogaAutorizaServiceJpa implements IProrrogaAutoriza {

	@Autowired
	private ProrrogaAutorizaRepository prorrogaAutorizaRepository;
	
	@Override
	public void guardar(ProrrogaAutoriza prorrogaAutoriza) {
		// TODO Auto-generated method stub
		prorrogaAutorizaRepository.save(prorrogaAutoriza);
	}

}
