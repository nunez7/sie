package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Municipio;

public interface IMunicipiosService  {
	List<Municipio> buscarPorEstado(Estado estado);
	Municipio buscarPorId(Integer id);
}
