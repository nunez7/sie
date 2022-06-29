package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dto.AlumnoPersonalDTO;

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
	
	//para traerse todos los cajeros
	@Query(value = "SELECT p.* FROM usuarios u "
			+ "INNER JOIN usuario_rol ur ON ur.id_usuario = u.id "
			+ "INNER JOIN personas p ON p.id = u.id_persona "
			+ "WHERE ur.id_rol = 8", nativeQuery = true)
	List<Persona> findAllCajeros();
	
	@Query(value = "SELECT COALESCE(a.id,0) as idAlumno, COALESCE(pr.id,0) as idEmpleado, p.id as idPersona,COALESCE(a.matricula,pr.no_empleado) as matricula, "
			+ "CONCAT(p.primer_apellido, ' ' , p.segundo_apellido, ' ' , p.nombre) AS nombre, COALESCE(a.id,0) as tipo "
			+ "FROM personas p "
			+ "LEFT JOIN alumnos a ON a.id_persona = p.id "
			+ "LEFT JOIN personal pr ON pr.id_persona = p.id "
			+ "WHERE (a.matricula iLIKE %:like% "
			+ "OR CONCAT(p.nombre, ' ',p.primer_apellido) iLIKE %:like% "
			+ "OR CONCAT(p.primer_apellido, ' ', p.nombre) iLIKE %:like% "
			+ "OR CONCAT(p.segundo_apellido, ' ',p.nombre) iLIKE %:like% "
			+ "OR pr.no_empleado iLIKE %:like%) "
			+ "ORDER BY p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoPersonalDTO> findByNombreOrNoEmpleadoOrMatricula(@Param("like") String like);

	@Query(value = "SELECT DISTINCT(p.*) FROM cargas_horarias ch "
			+ "INNER JOIN grupos g ON ch.id_grupo = g.id "
			+ "INNER JOIN carreras c ON g.id_carrera = c.id "
			+ "INNER JOIN persona_carrera pc ON pc.id_carrera = c.id "
			+ "INNER JOIN personas p ON pc.id_persona = p.id "
			+ "INNER JOIN usuarios u ON u.id_persona = p.id "
			+ "INNER JOIN usuario_rol ur ON ur.id_usuario = u.id "
			+ "WHERE u.activo = 'True' AND ur.id_rol = 3 AND ch.id = :idCargaHoraria "
			+ "ORDER BY p.id", nativeQuery = true)
	Persona findDirectorCarreraByCarga(@Param("idCargaHoraria") Integer idCargaHoraria);
}
