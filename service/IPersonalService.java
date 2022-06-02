package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;

public interface IPersonalService {
	Personal buscarPorPersona(Persona persona);
	List<Personal> buscarTodos();
	List<Personal> buscarTodosPorNombre();
	List<Personal> buscarJefes();
	Personal buscarPorId(Integer id);
	void guardar(Personal personal);
	List<Personal> buscarProfesores();
}
