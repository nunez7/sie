package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Testimonio;
import edu.mx.utdelacosta.repository.TestimonioRepository;
import edu.mx.utdelacosta.service.ITestimonioService;

@Service
public class TestimonioServiceJpa implements ITestimonioService{

	@Autowired
	private TestimonioRepository testimonioRepository;
	
	@Override
	public Testimonio buscarPorId(Integer id) {
		Optional<Testimonio> optional = testimonioRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Testimonio> buscarTodosPorIntegradora(boolean integradora) {
		return testimonioRepository.findAllByIntegradora(integradora);
	}

	@Override
	public List<Testimonio> buscarTodosPorIntegradoraExtra(boolean integradora) {
		return testimonioRepository.findAllByIntegradoraExtra(integradora);
	}
	
	@Override
	public Testimonio buscarPorNumeroIntegradora(Integer numero, Boolean integradora) {
		// TODO Auto-generated method stub
		return testimonioRepository.findByNumeroAndIntegradora(numero, integradora);
	}
	

}
