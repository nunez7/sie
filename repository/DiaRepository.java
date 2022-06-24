package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Dia;

public interface DiaRepository extends CrudRepository<Dia, Integer>{
	
	@Query(value = "SELECT * FROM dias WHERE id NOT IN (6,7) ORDER BY ORDER BY consecutivo", nativeQuery = true)
	List<Dia> findAll();
}
