package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Servicio;

public interface IServicioService {
	Servicio buscarPorId(Integer id);
	List<Servicio> buscarTodos();
}
