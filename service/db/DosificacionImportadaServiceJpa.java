package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.DosificacionImportada;
import edu.mx.utdelacosta.repository.DosificacionImportadaRepository;
import edu.mx.utdelacosta.service.IDosificacionImportadaService;

@Service
public class DosificacionImportadaServiceJpa implements IDosificacionImportadaService{

	@Autowired
	private DosificacionImportadaRepository dosiImpRepository;
	
	@Override
	public void guardar(DosificacionImportada dosiImportada) {
		dosiImpRepository.save(dosiImportada);
	}

}
