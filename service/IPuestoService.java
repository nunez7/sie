package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Puesto;

public interface IPuestoService {
	List<Puesto> buscarTodos();
	void guardar(Puesto puesto);
}
