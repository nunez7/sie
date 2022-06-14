package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TemaGrupal;

public interface ITemaGrupalService {

	void guardar(TemaGrupal temaGrupal);
	
	List<TemaGrupal> buscarPorGrupo(Grupo grupo);
	
	TemaGrupal  bucarPorId(Integer id);
	
}
