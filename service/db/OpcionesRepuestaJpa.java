package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.OpcionRespuesta;
import edu.mx.utdelacosta.repository.OpcionesRepuestaRepository;
import edu.mx.utdelacosta.service.IOpcionesRepuestaService;

@Service
public class OpcionesRepuestaJpa implements IOpcionesRepuestaService{
	
	@Autowired
	private OpcionesRepuestaRepository OpcResRepo;

	@Override
	@Transactional(readOnly = true)
	public List<OpcionRespuesta> buscarPorPregunta(Integer idPregunta) {
		// TODO Auto-generated method stub
		return OpcResRepo.findAllByPregunta(idPregunta);
	}
	
	
	
}
