package edu.mx.utdelacosta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.mx.utdelacosta.model.PeriodoInscripcion;

public interface PeriodoInscripcionRepository extends JpaRepository<PeriodoInscripcion, Integer>{
	
	@Query(value = "SELECT * FROM periodos_inscripciones "
			+ "ORDER BY id DESC LIMIT 1", nativeQuery = true)
	PeriodoInscripcion findLast();
	
	@Query(value = "SELECT coalesce(max(pi.id), 0) FROM PeriodoInscripcion pi")
	Integer getMaxId();

}
