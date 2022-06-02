package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Testimonio;

public interface ITestimonioService {
	
	Testimonio buscarPorId(Integer id);
	
	List<Testimonio> buscarTodosPorIntegradora(boolean integradora);
	
	List<Testimonio> buscarTodosPorIntegradoraExtra(boolean integradora);
	
	Testimonio buscarPorNumeroIntegradora(Integer numero, Boolean integradora);
}
