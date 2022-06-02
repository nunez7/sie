package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Sexo;
import edu.mx.utdelacosta.repository.SexosRepository;
import edu.mx.utdelacosta.service.ISexosService;

@Service
public class SexosServiceJpa implements ISexosService{
	
	@Autowired
	private SexosRepository sexoRepository;

	@Override
	public Sexo buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Sexo> optional = sexoRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Sexo> buscarTodos() {
		// TODO Auto-generated method stub
		return (List<Sexo>) sexoRepository.findAll();
	}

	@Override
	public Sexo buscarPorNombre(String nombre) {
		// TODO Auto-generated method stub
		Optional<Sexo> optional = sexoRepository.findByNombre(nombre);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
