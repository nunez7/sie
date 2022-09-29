package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Rol;
import edu.mx.utdelacosta.repository.RolRepository;
import edu.mx.utdelacosta.service.IRolService;

@Service
public class RolServiceJpa implements IRolService{
	
	@Autowired
	private RolRepository repositoryRol;

	@Override
	@Transactional
	public Rol buscarorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Rol> optional = repositoryRol.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional
	public List<Rol> buscarTodos() {
		// TODO Auto-generated method stub
		return repositoryRol.findAll(Sort.by(Order.asc("descripcion")));
	}

}
