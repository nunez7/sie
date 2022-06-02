package edu.mx.utdelacosta.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Concepto;

public interface ConceptosRepository extends CrudRepository<Concepto, Integer>{
	Optional<Concepto> findById(Integer id);
}
