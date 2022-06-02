package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Dia;

public interface IDiaService {
	List<Dia> buscarDias();
	
	//busca el dia por su di
	Dia buscarPorId(Integer id);
}
