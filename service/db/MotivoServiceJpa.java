package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Motivo;
import edu.mx.utdelacosta.repository.MotivoRepository;
import edu.mx.utdelacosta.service.IMotivoService;

@Service
public class MotivoServiceJpa implements IMotivoService{
	
	@Autowired
	private MotivoRepository motivoRepo;

	@Override
	public List<Motivo> buscarTodo() { 
		return  motivoRepo.findAllByOrderByMotivo();
	}


}
