package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.UsuarioPreferencia;

public interface IUsuarioPreferenciaService {
	void guardar(UsuarioPreferencia usuario);
	UsuarioPreferencia buscarPorId(Integer id); 
}
