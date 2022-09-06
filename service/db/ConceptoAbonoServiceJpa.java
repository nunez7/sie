package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.ConceptoAbono;
import edu.mx.utdelacosta.repository.ConceptoAbonoRepository;
import edu.mx.utdelacosta.service.IConceptoAbonoService;

@Service
public class ConceptoAbonoServiceJpa implements IConceptoAbonoService{

	@Autowired 
	private ConceptoAbonoRepository conceptoAbonoRepository;
	
	@Override
	public void guardar(ConceptoAbono conceptoAbono) {
		conceptoAbonoRepository.save(conceptoAbono);
	}

}
