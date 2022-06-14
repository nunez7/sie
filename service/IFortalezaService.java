package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Fortaleza;
import edu.mx.utdelacosta.model.Grupo;

public interface IFortalezaService {
	void guardar(Fortaleza fortaleza);
	void eliminar(Fortaleza fortaleza);
	List<Fortaleza> buscarPorGrupo(Grupo grupo);
}
