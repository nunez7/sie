package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Documento;

public interface DocumentosRepository extends CrudRepository<Documento, Integer>{
	
	Documento findByNombre(String nombre);

}
