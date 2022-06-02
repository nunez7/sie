package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.Documento;

public interface IDocumentosService {
	
	Documento buscarPorId(Integer id);
	
	Documento buscarPorNombre(String nombre);
	
}
