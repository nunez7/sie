package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Carrera;

public interface ICarrerasServices {
	
	List<Carrera> buscarTodas();

	Carrera buscarPorId(Integer id);
	
	List<Carrera> buscarCarrerasPorIdPersona(Integer id);
	
	List<Carrera> buscarTodasMenosIngles();
	
	List<Carrera> buscarTodasTSU();
}
