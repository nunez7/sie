package edu.mx.utdelacosta.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;

public interface GruposRepository extends CrudRepository<Grupo, Integer> {

	@Query(value = "SELECT g.* FROM alumnos_grupos ag " + "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE ag.id_alumno=:idAlumno AND g.id_carrera NOT IN (26) "
			+ "ORDER BY id_periodo DESC", nativeQuery = true)
	List<Grupo> findAllByAlumnoOrderByPeriodoDesc(@Param("idAlumno") Integer idAlumno);

	@Query(value = "SELECT g.* FROM alumnos_grupos ag " + "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE ag.id_alumno=:idAlumno AND g.id_carrera NOT IN (26) " + "ORDER BY id_periodo", nativeQuery = true)
	List<Grupo> findAllByAlumnoOrderByPeriodoAsc(@Param("idAlumno") Integer idAlumno);

	@Query(value = "SELECT g.* FROM alumnos_grupos ag " + "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE ag.id_alumno=:idAlumno AND g.id_carrera=:idCarrera "
			+ "ORDER BY id_periodo DESC", nativeQuery = true)
	List<Grupo> findAllByAlumnoAndCarreraOrderByPeriodoDesc(@Param("idAlumno") Integer idAlumno,
			@Param("idCarrera") Integer idCarrera);

	@Query(value = "SELECT g.* FROM alumnos_grupos ag " + "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE ag.id_alumno=:idAlumno " + "ORDER BY id_periodo DESC LIMIT 1", nativeQuery = true)
	Grupo findLastGrupoByAlumno(@Param("idAlumno") Integer idAlumno);

	@Query(value = "SELECT * " + "FROM grupos g "
			+ "WHERE id_periodo=:idPeriodo AND id_carrera=:idCarrera AND activo=true "
			+ "ORDER BY nombre DESC", nativeQuery = true)
	List<Grupo> findAllByPeriodoAndCarreraOrderByNombre(@Param("idPeriodo") Integer idPeriodo,
			@Param("idCarrera") Integer idCarrera);

	/* PROMEDIO DE ALUMNOS */
	@Query(value = "SELECT COALESCE(ROUND(AVG(calificacion),2),0)AS calificacion " + "FROM calificacion_materia cm "
			+ "INNER JOIN cargas_horarias ch ON ch.id=cm.id_carga_horaria "
			+ "WHERE id_alumno=:idAlumno AND ch.activo='True' AND ch.id_grupo=:idGrupo ", nativeQuery = true)
	Double findAvgAlumno(@Param("idAlumno") Integer idAlumno, @Param("idGrupo") Integer idGrupo);

	/* CREACION DE GRUPOS */
	@Query(value = "SELECT CAST(COUNT(a.nombre)+1 AS VARCHAR) from grupos a"
			+ " WHERE a.nombre ILIKE %:nombre% AND id_periodo = :periodo", nativeQuery = true)
	Integer findLastGrupoByNombreAndPeriodo(@Param("nombre") String nombre, @Param("periodo") Integer id);

	@Query(value = "SELECT gp.* FROM grupos gp " + " WHERE gp.id_periodo=:periodo"
			+ " ORDER BY id DESC", nativeQuery = true)
	List<Grupo> findAllByPeriodoOrderByIdDesc(@Param("periodo") Integer periodo);

	// busca los grupos por carrera, planEstudio y periodo
	@Query(value = "SELECT * FROM grupos g " + "INNER JOIN carreras c ON g.id_carrera=c.id "
			+ "INNER JOIN periodos p ON g.id_periodo = p.id "
			+ "WHERE g.id_carrera = :carrera AND g.id_periodo = :periodo "
			+ "AND g.activo = 'true'", nativeQuery = true)
	ArrayList<Grupo> findByIdCarreraAndIdPeriodo(@Param("carrera") int carrera, @Param("periodo") int periodo);

	// busca los grupos por persona-carrera (modulo de director)
	@Query(value = "SELECT g.* FROM grupos g " + "INNER JOIN carreras c ON g.id_carrera = c.id "
			+ "WHERE c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :persona) "
			+ "AND id_periodo = :periodo ORDER BY g.nombre", nativeQuery = true)
	List<Grupo> findByCarreraAndPeriodoAndPersonaCarrera(@Param("persona") int persona, @Param("periodo") int periodo);

	@Query(value = "SELECT COUNT(g.*) as cantidad FROM grupos g "
			+ "INNER JOIN cargas_horarias ch ON g.id = ch.id_grupo "
			+ "WHERE ch.id_profesor = :profesor AND ch.id_periodo = :periodo "
			+ "AND ch.activo = 'True' ", nativeQuery = true)
	Integer countGruposByProfesorAndPeriodo(@Param("profesor") Integer profesor, @Param("periodo") Integer periodo);

	@Query(value = "SELECT * FROM grupos " + "WHERE id_carrera = :carrera AND id_cuatrimestre = :cuatrimestre "
			+ "AND id_periodo = :periodo ORDER BY nombre", nativeQuery = true)
	List<Grupo> findByCuatrimestreAndCarreraAndPeriodo(@Param("cuatrimestre") Integer idCuatrimestre,
			@Param("carrera") Integer idCarrera, @Param("periodo") Integer idPeriodo);
	
	Optional<Grupo> findById(Integer Id);
	
	//Modificada
	//List<Grupo> findByProfesorAndActivo(Persona profesor, Boolean Activo);
	List<Grupo> findByProfesorAndPeriodoAndActivoTrueOrderByPeriodoAsc(Persona profesor, Periodo periodo);
	
	@Query(value = "SELECT DISTINCT(g.*) FROM grupos g "
			+ "INNER JOIN alumnos_grupos ag on g.id=ag.id_grupo "
			+ "INNER JOIN cargas_horarias ch on g.id=ch.id_grupo "
			+ "WHERE ch.id_profesor=:idProfesor and ch.id_periodo=:idPeriodo", nativeQuery = true)
	List<Grupo> findByProfesorAndPeriodo(@Param("idProfesor") Integer idProfesor, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value ="SELECT g.* "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN grupos g ON ag.id_grupo=g.id "
			+ "WHERE id_alumno = :alumno "
			+ "ORDER BY id_grupo DESC LIMIT 2", nativeQuery = true)
	List<Grupo> findByAlumnoPenultimoGrupo(@Param("alumno") Integer idAlumno);
	
}
