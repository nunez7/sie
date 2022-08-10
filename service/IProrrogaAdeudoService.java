package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.ProrrogaAdeudo;

public interface IProrrogaAdeudoService {
	
	void guardar(ProrrogaAdeudo prorrogaAdeudo);
	
	List<ProrrogaAdeudo> buscarPorIdPersona(Integer idPersona);
	
	ProrrogaAdeudo buscarUltimaPorPersona(Integer idPersona);
}
