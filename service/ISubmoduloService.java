package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Submodulo;

public interface ISubmoduloService {
	List<Submodulo> buscarTodos();
	List<Submodulo> buscarPorModuloYEstatus(int modulo, boolean estatus);
	Submodulo buscarPorId(Integer idSubmodulo);
	Submodulo getPorDefecto(Integer idModulo);
	boolean existeRelacion(Integer modulo, Integer submodulo);
}
