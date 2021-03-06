package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.repository.CuatrimestreRepository;
import edu.mx.utdelacosta.service.ICuatrimestreService;

@Service
public class CuatrimestreServiceJpa implements ICuatrimestreService{
	
	@Autowired
	private CuatrimestreRepository cuatriRepo;

	@Override
	public Cuatrimestre buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Cuatrimestre> optional = cuatriRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Cuatrimestre> buscarTodos() {
		// TODO Auto-generated method stub
		return cuatriRepo.findAll(Sort.by(Order.asc("id")));
	}

}
