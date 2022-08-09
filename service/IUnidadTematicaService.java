package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.UnidadTematica;

public interface IUnidadTematicaService {
	
	UnidadTematica buscarPorId(Integer id);
	
	void guardar(UnidadTematica unidadTematica);

	List<UnidadTematica> buscarPorDosificacion(Integer idDosificacion);

}
