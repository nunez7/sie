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
	List<Carrera> findAllTSUExceptEnglishOrderByNombre();
	
	@Query(value = "SELECT * FROM carreras c "
			+ "INNER JOIN persona_carrera pc on c.id = pc.id_carrera "
			+ "WHERE pc.id_persona = :idPersona ORDER BY c.nombre " , nativeQuery = true)
	List<Carrera> findCarrerasByIdPersona(@Param("idPersona") Integer idPersona);
	
	@Query(value = "SELECT DISTINCT(c.*) "
			+ "	FROM carreras c "
			+ "	INNER JOIN grupos g ON g.id_carrera = c.id "
			+ "	INNER JOIN cargas_horarias ch ON ch.id_grupo = g.id "
			+ "	WHERE ch.id_profesor = :profesor and ch.id_periodo = :periodo", nativeQuery = true)
	List<Carrera> findCarrerasByPersonaAndPeriodo(@Param("profesor") Integer idPersona, @Param("periodo") Integer idPeriodo);
	
	@Query(value = "SELECT c.id FROM carreras c "
			+ "WHERE c.carrera_siguiente = :carreraSiguiente", nativeQuery = true)
	List<Integer> findCarreraAnterior(@Param("carreraSiguiente")Integer idCarrera);
}
