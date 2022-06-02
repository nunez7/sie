package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Escuela;
import edu.mx.utdelacosta.model.Estado;

public interface IEscuelaService {
	List<Escuela> buscarTodoPorEstado(Estado estado);
	Escuela buscarPorId(Integer idEscuela);
}
