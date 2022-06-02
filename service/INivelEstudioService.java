package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.NivelEstudio;

public interface INivelEstudioService {
	NivelEstudio buscarPorId(Integer id);
	List<NivelEstudio> buscarTodos();
}
