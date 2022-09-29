package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Testimonio;
import edu.mx.utdelacosta.repository.TestimonioRepository;
import edu.mx.utdelacosta.service.ITestimonioService;

@Service
public class TestimonioServiceJpa implements ITestimonioService{

	@Autowired
	private TestimonioRepository testimonioRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Testimonio buscarPorId(Integer id) {
		Optional<Testimonio> optional = testimonioRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Testimonio> buscarTodosPorIntegradora(boolean integradora) {
		return testimonioRepository.findAllByIntegradora(integradora);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Testimonio> buscarTodosPorIntegradoraExtra(boolean integradora) {
		return testimonioRepository.findAllByIntegradoraExtra(integradora);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Testimonio buscarPorNumeroIntegradora(Integer numero, Boolean integradora) {
		// TODO Auto-generated method stub
		return testimonioRepository.findByNumeroAndIntegradora(numero, integradora);
	}
	

}
