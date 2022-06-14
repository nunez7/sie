package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.PagoCuatrimestre;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.repository.PagoCuatrimestreRepository;
import edu.mx.utdelacosta.service.IPagoCuatrimestreService;

@Service
public class PagoCuatrimestreServiceJpa implements IPagoCuatrimestreService{

	@Autowired
	private PagoCuatrimestreRepository pagoCuatriRepo;
	
	@Override
	public PagoCuatrimestre buscarPorPagoGeneral(PagoGeneral pagoGeneral) {
		return pagoCuatriRepo.findByPagoGeneral(pagoGeneral);
	}

	@Override
	public void guardar(PagoCuatrimestre pagoCuatrimestre) {
		pagoCuatriRepo.save(pagoCuatrimestre);	
	}

}
