package edu.mx.utdelacosta.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.EstadoCivil;

public interface EstadoCivilRepository extends CrudRepository<EstadoCivil, Integer>{
	Optional<EstadoCivil> findByDescripcion(String descripcion);
}
