package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Instrumento;

public interface InstrumentosRepository extends CrudRepository<Instrumento, Integer>{
	
	List<Instrumento> findAll();
	
	Optional<Instrumento> findById(Integer id);

}
