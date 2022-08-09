package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.PersonaReferencia;

public interface IPersonaReferenciaService {
	
	void guardar(PersonaReferencia pr);
	
	PersonaReferencia buscarPorReferencia(String referencia);
	
}
