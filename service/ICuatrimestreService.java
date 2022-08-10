package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.dto.PagoCuatrimestreDTO;

public interface ICuatrimestreService {
	Cuatrimestre buscarPorId(Integer id);
	List<Cuatrimestre> buscarTodos();
	List<PagoCuatrimestreDTO> buscarConPagosGenerados(Integer idPeriodo);
}
