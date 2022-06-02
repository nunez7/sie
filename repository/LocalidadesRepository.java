package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Localidad;
import edu.mx.utdelacosta.model.Municipio;

public interface LocalidadesRepository extends CrudRepository<Localidad, Integer> {
	List<Localidad> findByMunicipio(Municipio municipio);
}
