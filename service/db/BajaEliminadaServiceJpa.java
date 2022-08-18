package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.BajaEliminada;
import edu.mx.utdelacosta.repository.BajaEliminadaRepository;
import edu.mx.utdelacosta.service.IBajaEliminadaService;

@Service
public class BajaEliminadaServiceJpa implements IBajaEliminadaService{
	
	@Autowired
	BajaEliminadaRepository bajaEliminadaRepository;

	@Override
	public void guardar(BajaEliminada bajaE) {
		bajaEliminadaRepository.save(bajaE);
	}
	
}
