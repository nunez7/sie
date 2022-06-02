package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Periodo;

public interface IPeriodosService {
	List<Periodo> buscarTodos();
	Periodo buscarPorId(Integer idPeriodo);
	void guardar(Periodo periodo);
}
