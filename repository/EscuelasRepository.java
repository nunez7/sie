package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Escuela;
import edu.mx.utdelacosta.model.Estado;

public interface EscuelasRepository extends CrudRepository<Escuela, Integer>{
	List<Escuela> findByEstado(Estado estado);
}
