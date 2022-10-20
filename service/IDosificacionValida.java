package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.DosificacionValida;

public interface IDosificacionValida {
	
	//valida la dosificacion 
	void guardar(DosificacionValida dosificacionValida);
	//se busca si hay un objeto de dosificacion valida
	DosificacionValida buscarPorIdDosificacion(Integer idDosificacion);
}
