package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Concepto;

public interface IConceptoService {

	Concepto buscarPorId(Integer id);
	List<Concepto> buscarTodos();
	void guardar(Concepto concepto);
}
