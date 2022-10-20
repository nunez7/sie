package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.DosificacionValida;
import edu.mx.utdelacosta.repository.DosificacionValidaRepository;
import edu.mx.utdelacosta.service.IDosificacionValida;

@Service
public class DosificacionValidaServiceJpa implements IDosificacionValida{

	@Autowired
	private DosificacionValidaRepository dosificacionValidaRepository;
	
	@Override
	public void guardar(DosificacionValida dosificacionValida) {
		dosificacionValidaRepository.save(dosificacionValida);
	}

	@Override
	public DosificacionValida buscarPorIdDosificacion(Integer idDosificacion) {
		// TODO Auto-generated method stub
		return dosificacionValidaRepository.buscarPorIdDosificacion(idDosificacion);
	}

}
