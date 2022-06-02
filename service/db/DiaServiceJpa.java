package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Dia;
import edu.mx.utdelacosta.repository.DiaRepository;
import edu.mx.utdelacosta.service.IDiaService;

@Service
public class DiaServiceJpa implements IDiaService{
	
	@Autowired
	private DiaRepository diaRepo;
	
	@Override
	public List<Dia> buscarDias() {
		// TODO Auto-generated method stub
		return diaRepo.findAll();
	}
	
	@Override
	public Dia buscarPorId(Integer id) {
		Optional<Dia> optional = diaRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
