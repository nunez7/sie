package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.PersonaReferencia;
import edu.mx.utdelacosta.repository.PersonaReferenciaRepository;
import edu.mx.utdelacosta.service.IPersonaReferenciaService;

@Service
public class PersonaReferenciaJpa implements IPersonaReferenciaService {

	@Autowired
	private PersonaReferenciaRepository personaReferenciaRepository;
	
	@Override
	public void guardar(PersonaReferencia pr) {
		personaReferenciaRepository.save(pr);
	}

	@Override
	@Transactional(readOnly = true)
	public PersonaReferencia buscarPorReferencia(String referencia) {
		// TODO Auto-generated method stub
		return personaReferenciaRepository.findByReferencia(referencia);
	}

}
