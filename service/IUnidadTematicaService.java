package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.UnidadTematica;

public interface IUnidadTematicaService {
	
	UnidadTematica buscarPorId(Integer id);
	void guardar(UnidadTematica unidadTematica);

}
