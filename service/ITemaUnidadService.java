package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.TemaUnidad;

public interface ITemaUnidadService {
	
	void guardar (TemaUnidad temaUnidad);
	TemaUnidad buscarPorId(Integer id);

}
