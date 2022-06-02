package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Cuatrimestre;

public interface ICuatrimestreService {
	Cuatrimestre buscarPorId(Integer id);
	List<Cuatrimestre> buscarTodos();
}
