package edu.mx.utdelacosta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.PersonaReferencia;

public interface PersonaReferenciaRepository extends JpaRepository<PersonaReferencia, Integer>{
	
	@Query(value = "SELECT * FROM persona_referencia WHERE referencia = :referencia", nativeQuery = true)
	PersonaReferencia findByReferencia(@Param("referencia") String referencia);

}
