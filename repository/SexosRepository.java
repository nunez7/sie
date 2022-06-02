package edu.mx.utdelacosta.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Sexo;

public interface SexosRepository extends CrudRepository<Sexo, Integer>{
	Optional<Sexo> findByNombre(String nombre);
}
