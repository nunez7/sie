package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Motivo;

public interface MotivoRepository extends CrudRepository<Motivo, Integer>{

	List<Motivo> findAllByOrderByMotivo();
	
}
