package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Persona;

public interface PersonasRepository extends CrudRepository<Persona, Integer> {
	Persona findByEmail(String email);

	@Query(value = "SELECT CAST(COUNT(a.email) AS VARCHAR) from personas a"
			+ " WHERE a.email ILIKE %:emai%", nativeQuery = true)
	Integer findExitenciaEmail(@Param("emai") String emai);

	@Query(value = "SELECT p.* FROM personas p " + "INNER JOIN cargas_horarias ch ON ch.id_profesor=p.id "
			+ "INNER JOIN grupos g ON g.id = ch.id_grupo " + "INNER JOIN carreras c ON c.id = g.id_carrera "
			+ "INNER JOIN persona_carrera ON persona_carrera.id_carrera = c.id "
			+ "WHERE c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona =:persona) "
			+ "AND ch.id_periodo =:periodo GROUP BY p.id ORDER BY p.nombre", nativeQuery = true)
	List<Persona> findByPersonaCarreraAndPeriodo(@Param("persona") Integer persona, @Param("periodo") Integer periodo);
	
	@Query(value = "SELECT DISTINCT p.* " 
			+ "FROM carreras c "
			+ "INNER JOIN grupos g ON c.id=g.id_carrera "
			+ "INNER JOIN cargas_horarias ch ON ch.id_grupo=g.id "
			+ "INNER JOIN personas p ON ch.id_profesor=p.id "
			+ "WHERE c.id=:idCarrera AND ch.id_periodo=:idPeriodo AND ch.activo=true ORDER BY p.nombre", nativeQuery = true)
	List<Persona> findProfesoresByCarreraAndPeriodo(@Param("idCarrera") Integer idCarrera,@Param("idPeriodo") Integer idPeriodo);
}
