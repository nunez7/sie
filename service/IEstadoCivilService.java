package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.EstadoCivil;

public interface IEstadoCivilService {
	EstadoCivil buscarPorId(Integer id);
	EstadoCivil buscarPorDescripcion(String descripcion);
	List<EstadoCivil> buscarTodos();
}
