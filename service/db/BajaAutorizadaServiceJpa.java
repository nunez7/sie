package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.BajaAutorizada;
import edu.mx.utdelacosta.repository.BajaAutorizadaRepository;
import edu.mx.utdelacosta.service.IBajaAutorizadaService;

@Service
public class BajaAutorizadaServiceJpa implements IBajaAutorizadaService{

	@Autowired
	private BajaAutorizadaRepository bajaAutoRepo;

	@Override
	public void guardar(BajaAutorizada bajaAutorizada) {
		bajaAutoRepo.save(bajaAutorizada);
	}
}
