package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.CambioGrupo;
import edu.mx.utdelacosta.repository.CambioGrupoRepository;
import edu.mx.utdelacosta.service.ICambioGrupoService;

@Service
public class CambioGrupoServiceJpa implements ICambioGrupoService{

	@Autowired
	private CambioGrupoRepository cambioGrupoRepository;
	
	@Override
	public void guardar(CambioGrupo cambioGrupo) {
		cambioGrupoRepository.save(cambioGrupo);
	}

}
