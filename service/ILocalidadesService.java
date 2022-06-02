package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Municipio;

public interface ILocalidadesService {
	Localidad buscarPorid(Integer id);
	List<Localidad> buscarPorMunicipio(Municipio m);
}
