package edu.mx.utdelacosta.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;

public interface IUsuariosService {
	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	List<Usuario> buscarTodos();
	Usuario buscarPorId(Integer idUsuario);
	Usuario buscarPorUsuario(String username);
	int bloquear(int idUsuario);
	int activar(int idUsuario);
	void actualizarContrasenia(Integer id, String contrasenia);
	List<Usuario> buscarPorPersonaOUsuario(String nombre);
	List<Usuario> buscarByExample(Example<Usuario> example);
	Usuario buscarPorPersona(Persona persona);
	Page<Usuario> buscarTodos(Pageable page);
	List<Usuario> buscarUltimos10();
	
	List<Usuario> buscarAlumnosPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo);
	List<Usuario> buscarPersonal();
}

