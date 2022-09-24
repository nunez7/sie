package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Concepto;
import edu.mx.utdelacosta.repository.ConceptosRepository;
import edu.mx.utdelacosta.service.IConceptoService;

@Service
public class ConceptosServiceJpa implements IConceptoService{
	
	@Autowired
	private ConceptosRepository conceptosRepo;
	
	@Override
	@Transactional(readOnly = true)
	public Concepto buscarPorId(Integer id) {
		Optional<Concepto> optional = conceptosRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Concepto> buscarTodos() {
		// TODO Auto-generated method stub
		return conceptosRepo.findAllByOrderByIdDesc();
	}

	@Override
	public void guardar(Concepto concepto) {
		conceptosRepo.save(concepto);
	}
	
	@Override 
	@Transactional(readOnly = true)
	public List<Concepto> buscarOpcionales() { 
		return conceptosRepo.findAllOptionals(); 
	}

}
