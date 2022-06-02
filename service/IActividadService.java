package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Actividad;

public interface IActividadService {
	
	List<Actividad> buscarTodas();
	//busca la acctividad por id
	Actividad buscarPorId(Integer id);

}
