package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Fortaleza;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.repository.FortalezaRepository;
import edu.mx.utdelacosta.service.IFortalezaService;

@Service
public class FortalezaServiceJpa implements IFortalezaService{
	
	@Autowired
	private FortalezaRepository fortalezaRepo;

	@Override
	public void guardar(Fortaleza fortaleza) { 
		fortalezaRepo.save(fortaleza);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Fortaleza> buscarPorGrupo(Grupo grupo) {
		return fortalezaRepo.findByGrupo(grupo);
	}

	@Override
	@Transactional
	public void eliminar(Fortaleza fortaleza) {
		fortalezaRepo.delete(fortaleza);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Fortaleza buscarPorId(Integer id) {
		Optional<Fortaleza> optional = fortalezaRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
}
