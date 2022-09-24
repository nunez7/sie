package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Ciclo;
import edu.mx.utdelacosta.repository.CicloRepository;
import edu.mx.utdelacosta.service.ICicloService;

@Service
public class CicloServiceJpa implements ICicloService{
	
	@Autowired
	private CicloRepository cicloRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Ciclo> buscarTodos() {
		// TODO Auto-generated method stub
		return cicloRepository.findAll(Sort.by(Order.desc("id")));
	}

	@Override
	@Transactional(readOnly = true)
	public Ciclo buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Ciclo> optional = cicloRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
