package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Sesion;
import edu.mx.utdelacosta.model.Usuario;

public interface SesionesRepository extends CrudRepository<Sesion, Integer>{
	List<Sesion> findByUsuario(Usuario usuario);
	Sesion findBySesionId(String sesionId);
}
