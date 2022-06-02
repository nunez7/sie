package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Sesion;
import edu.mx.utdelacosta.model.Usuario;

public interface ISesionesService {
	void guardar(Sesion sesion);
	List<Sesion> buscarTodas();
	Sesion buscarPorId(Integer idSesion);
	Sesion buscarPorSesion(String idSesion);
	List<Sesion> buscarPorUsuario(Usuario usuario);
}
