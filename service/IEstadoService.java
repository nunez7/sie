package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Estado;

public interface IEstadoService {
	List<Estado> buscarTodos();
	Estado buscarPorId(Integer id);
}
