package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.PlanEstudio;

public interface MateriasRepository extends JpaRepository<Materia, Integer> {
	
	//busca las materia por plan de estudio y nombre
	Optional<Materia> findByPlanEstudioAndNombre(PlanEstudio planEstudio, String nombre);
	
	//busca la materia por plan de estduo y abreviatura
	Optional<Materia> findByPlanEstudioAndAbreviatura(PlanEstudio planEstudio, String abreviatura);
	
	//busca las materias por grupo, carrera y activas 
	@Query(value = "SELECT m.* FROM materias m "
			+ "INNER JOIN planes_estudio pe ON m.id_plan_estudio = pe.id "
			+ "INNER JOIN cuatrimestres c ON m.id_cuatrimestre = c.id "
			+ "WHERE pe.id_carrera = :carrera AND m.activo = true "
			+ "AND m.id_cuatrimestre IN (SELECT DISTINCT g.id_cuatrimestre FROM grupos g WHERE g.id_carrera = :carrera AND g.id = :grupo) "
			+ "ORDER BY m.nombre", nativeQuery = true)
	List<Materia> findByGrupoAndCarreraAndActivo(@Param("grupo") Integer grupo, @Param("carrera") Integer carrera);
	
	//busca las materias por grupo y carrera
	@Query(value = "SELECT m.* FROM materias m "
			+ "INNER JOIN planes_estudio pe ON m.id_plan_estudio = pe.id "
			+ "INNER JOIN cuatrimestres c ON m.id_cuatrimestre = c.id "
			+ "WHERE pe.id_carrera = :carrera AND m.id_cuatrimestre "
			+ "IN (SELECT DISTINCT g.id_cuatrimestre FROM grupos g WHERE g.id_carrera = :carrera AND g.id = :grupo) "
			+ "ORDER BY m.nombre", nativeQuery = true)
	List<Materia> findByGrupoAndCarrera(@Param("grupo") Integer grupo, @Param("carrera") Integer carrera);
	
	@Query(value = "SELECT m.* "
			+ "FROM materias m "
			+ "INNER JOIN cargas_horarias ch ON ch.id_materia=m.id "
			+ "INNER JOIN grupos g ON g.id=ch.id_grupo "
			+ "WHERE ch.id_grupo=:grupo AND ch.activo=true AND g.id_carrera=:carrera ORDER BY m.nombre", nativeQuery = true)
	List<Materia> findByCargaActivaGrupo(@Param("grupo") Integer grupo, @Param("carrera") Integer carrera);
	
	@Query(value = "SELECT DISTINCT m.* FROM materias m "
			+ "INNER JOIN cargas_horarias ch ON m.id=ch.id_materia "
			+ "INNER JOIN grupos g ON g.id=ch.id_grupo "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "WHERE c.id=:idCarrera AND ch.id_profesor =:idProfesor AND ch.id_periodo=:idPeriodo AND ch.activo=true", nativeQuery = true)
	List<Materia> findByCarreraAndProfesorAndPeriodo(@Param("idCarrera") Integer idCarrera,@Param("idProfesor") Integer idProfesor,@Param("idPeriodo") Integer idPeriodo);
	
}
