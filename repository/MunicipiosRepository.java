package edu.mx.utdelacosta.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Estado;
import edu.mx.utdelacosta.model.Municipio;

public interface MunicipiosRepository extends CrudRepository<Municipio, Integer>{
	List<Municipio> findByEstado(Estado estado);
}
