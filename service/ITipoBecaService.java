package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.TipoBeca;

public interface ITipoBecaService {
	
	//busca todas los tipos de becas 
	List<TipoBeca> buscarTodasPorIdDesc();
	//método para guardar y editar
	void guardar(TipoBeca tipoBeca);
	//para obtener un tipo por id
	TipoBeca buscarPorId(Integer id);
	//busca los tipos de becas activas
	List<TipoBeca> buscarActivas();
}
