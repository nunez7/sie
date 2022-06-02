package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Testimonio;

public interface TestimonioRepository extends CrudRepository<Testimonio, Integer>{
	List<Testimonio> findAll();
	
	@Query(value = "SELECT * "
			+ "FROM testimonios "
			+ "WHERE id <>12 and integradora= :integradora", nativeQuery = true)
	List<Testimonio> findAllByIntegradora(@Param("integradora") boolean integradora);
	
	@Query(value = "SELECT * "
			+ "FROM testimonios "
			+ "WHERE id <>12 and numero <>10 and integradora= :integradora", nativeQuery = true)
	List<Testimonio> findAllByIntegradoraExtra(@Param("integradora") boolean integradora);
	
	Testimonio findByNumeroAndIntegradora(Integer numero, Boolean integradora);
}
