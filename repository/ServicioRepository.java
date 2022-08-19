package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Servicio;

public interface ServicioRepository extends CrudRepository<Servicio, Integer>{
	List<Servicio> findAll();
}
