package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Puesto;
import edu.mx.utdelacosta.repository.PuestoRepository;
import edu.mx.utdelacosta.service.IPuestoService;

@Service
public class PuestoServiceJpa implements IPuestoService {
	
	@Autowired
	private PuestoRepository puestoRep;

	@Override
	public List<Puesto> buscarTodos() {
		// TODO Auto-generated method stub
		return puestoRep.findAll(Sort.by(Order.asc("nombre")));
	}

	@Override
	public void guardar(Puesto puesto) {
		// TODO Auto-generated method stub
		puestoRep.save(puesto);
	}

}
