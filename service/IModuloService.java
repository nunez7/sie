package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Modulo;

public interface IModuloService {
	List<Modulo> buscarTodos();

	List<Modulo> buscarPorEstatus(boolean estatus);

	Modulo buscarPorId(Integer idModulo);
	
	List<Modulo> buscarModulosPorRol(Integer idRol);
	
	boolean hasAccess (Integer idUsuario, Integer idModulo);
}
