package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Bitacora;
import edu.mx.utdelacosta.repository.BitacoraRepository;
import edu.mx.utdelacosta.service.IBitacoraService;

@Service
public class BitacoraServiceJpa implements IBitacoraService{

	@Autowired
	private BitacoraRepository bitacoraRepository;
	
	@Override
	public void guardar(Bitacora bitacora) {
		bitacoraRepository.save(bitacora);
		
	}
	
}
