package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Rol;

public interface IRolService {
	Rol buscarorId(Integer id);
	List<Rol> buscarTodos();
}
