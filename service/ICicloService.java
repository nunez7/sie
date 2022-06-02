package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Ciclo;

public interface ICicloService {
	List<Ciclo> buscarTodos();
	Ciclo buscarPorId(Integer id);
}
