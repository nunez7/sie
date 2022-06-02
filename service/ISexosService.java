package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Sexo;

public interface ISexosService {
	Sexo buscarPorId(Integer id);
	List<Sexo> buscarTodos();
	Sexo buscarPorNombre(String id);
}
