package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Persona;

public interface IPersonaService {
	void guardar(Persona persona);
	
	List<Persona> buscarTodas();
	
	Persona buscarPorId(Integer idPersona);
	
	Persona buscarPorEmail(String email);
	
	Integer buscarEmailExistente(String email);
	
	List<Persona> buscarPorPersonaCarreraAndPeriodo(Integer persona, Integer periodo);
	
	List<Persona> buscarProfesoresPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo);
}
