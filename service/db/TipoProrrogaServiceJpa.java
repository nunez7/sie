package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.TipoProrroga;
import edu.mx.utdelacosta.repository.TipoProrrogaRepository;
import edu.mx.utdelacosta.service.ITipoProrrogaService;

@Service
public class TipoProrrogaServiceJpa implements ITipoProrrogaService{

	@Autowired
	private TipoProrrogaRepository tipoProrrogaRepository;
	
	@Override
	public List<TipoProrroga> buscarTodos() {
		return (List<TipoProrroga>) tipoProrrogaRepository.findAll();
	}

}
