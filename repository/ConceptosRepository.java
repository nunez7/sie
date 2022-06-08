package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.utdelacosta.model.Concepto;

public interface ConceptosRepository extends JpaRepository<Concepto, Integer>{
	
	List<Concepto> findAllByOrderByIdDesc();

	//metodo de gestion de pagos
	@Query(value = "SELECT * FROM conceptos c WHERE c.id not in (7,8,9,10,13,14,15,20,21,22) ORDER BY concepto", nativeQuery = true)
	List<Concepto> findAllOptionals();
}
