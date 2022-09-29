package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Turno;
import edu.mx.utdelacosta.repository.TurnoRepository;
import edu.mx.utdelacosta.service.ITurnoService;

@Service
public class TurnoServiceJpa implements ITurnoService{
		
	@Autowired
	private TurnoRepository turnoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Turno> buscarTodos() {
		return (List<Turno>) turnoRepository.findAll();
	}
	
	
}
