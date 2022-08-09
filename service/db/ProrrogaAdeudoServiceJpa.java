package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.ProrrogaAdeudo;
import edu.mx.utdelacosta.repository.ProrrogaAdeudoRepository;
import edu.mx.utdelacosta.service.IProrrogaAdeudoService;

@Service
public class ProrrogaAdeudoServiceJpa implements IProrrogaAdeudoService{

	@Autowired
	private ProrrogaAdeudoRepository prorrogaAdeudoRepository;
	
	@Override
	public void guardar(ProrrogaAdeudo prorrogaAdeudo) {
		prorrogaAdeudoRepository.save(prorrogaAdeudo);
	}

	@Override
	public List<ProrrogaAdeudo> buscarPorIdPersona(Integer idPersona) {
		// TODO Auto-generated method stub
		return prorrogaAdeudoRepository.findByIdPersona(idPersona);
	}

	@Override
	public ProrrogaAdeudo buscarUltimaPorPersona(Integer idPersona) {
		// TODO Auto-generated method stub
		return prorrogaAdeudoRepository.findLastByIdPersona(idPersona);
	}
	

}
