package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Cliente;

public interface IClienteService {
	
	List<Cliente> buscarTodos();
	Cliente buscarPorId(Integer id);
	void guardar(Cliente cliente);
	String ultimaClave();
	List<Cliente> buscarPorNombreORfc(String like);
}
