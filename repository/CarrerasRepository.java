package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Carrera;

public interface CarrerasRepository extends JpaRepository<Carrera, Integer>{
	@Query(value = "SELECT * FROM carreras "
			+ "WHERE id NOT IN(26, 14) "
			+ "ORDER BY nombre ", nativeQuery = true)
	List<Carrera> findAllExceptEnglishOrderByNombre();
	
	@Query(value = "SELECT * FROM carreras "
			+ "WHERE id NOT IN(26, 14) AND id_nivel_estudio = 1 "
			+ "ORDER BY nombre ", nativeQuery = true)
	List<Carrera> findAllTSU();
	
	@Query(value = "SELECT * FROM carreras c "
			+ "INNER JOIN persona_carrera pc on c.id = pc.id_carrera "
			+ "WHERE pc.id_persona = :idPersona ORDER BY c.nombre " , nativeQuery = true)
	List<Carrera> findCarrerasByIdPersona(@Param("idPersona") Integer idPersona);
}
