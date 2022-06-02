package edu.mx.utdelacosta.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.PlanEstudio;

public interface PlanEstudioRepository extends JpaRepository<PlanEstudio, Integer>{
	
	//busca los planes de estudio por persona-carrera
	@Query(value = "SELECT * FROM planes_estudio pe "
			+ "INNER JOIN carreras c ON pe.id_carrera = c.id "
			+ "WHERE c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona) "
			+ "ORDER BY plan", nativeQuery = true)
	List<PlanEstudio> findByPersonaAndCarrera(@Param("idPersona") Integer idPersona);
	
	//busca los planes de estudio por carrera
	@Query(value = "SELECT * FROM planes_estudio WHERE id_carrera = :carrera AND activo = 'True'", nativeQuery = true)
	List<PlanEstudio> findByCarrera(@Param("carrera") Integer idCarrera);
	
	
	@Query(value = "SELECT  * FROM planes_estudio ORDER BY id_carrera, nivel", nativeQuery = true)
	List<PlanEstudio> findAllOrderByCarreraNivel();
	
}
