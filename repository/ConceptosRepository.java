package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.utdelacosta.model.Concepto;

public interface ConceptosRepository extends JpaRepository<Concepto, Integer>{
	
	List<Concepto> findAllByOrderByIdDesc();
}
