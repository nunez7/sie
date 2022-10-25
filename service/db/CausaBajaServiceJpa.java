package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.CausaBaja;
import edu.mx.utdelacosta.repository.CausaBajaRepository;
import edu.mx.utdelacosta.service.ICausaBajaService;

@Service
public class CausaBajaServiceJpa implements ICausaBajaService{

	@Autowired
	private CausaBajaRepository causaBajaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<CausaBaja> buscarActivas() {
		// TODO Auto-generated method stub
		return causaBajaRepository.findAllByActivo();
	}

}
