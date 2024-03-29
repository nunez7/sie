package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;

public interface CargaHorariaRepository extends JpaRepository<CargaHoraria, Integer> {
	List<CargaHoraria> findByGrupoAndActivoTrueAndMateriaCurricularTrueAndMateriaCalificacionTrue(Grupo grupo);

	// busca la carga horaria por materia y periodo
	@Query(value = "SELECT * FROM cargas_horarias "
			+ "WHERE id_materia = :materia AND id_periodo = :periodo AND id_grupo = :grupo "
			+ "AND activo = 'True' ", nativeQuery = true)
	CargaHoraria findByMateriaAndPeriodoAndGrupo(@Param("materia") Integer materia, @Param("periodo") Integer periodo,
			@Param("grupo") Integer grupo);

	@Query(value = "SELECT ch.* FROM cargas_horarias ch " + "INNER JOIN materias m ON ch.id_materia = m.id "
			+ "WHERE id_grupo = :grupo and id_periodo = :periodo "
			+ "AND ch.activo = 'True' ORDER BY m.nombre", nativeQuery = true)
	List<CargaHoraria> findByGrupoAndPeriodo(@Param("grupo") Integer grupo, @Param("periodo") Integer periodo);
	
	@Query(value = "SELECT ch.* FROM cargas_horarias ch " + "INNER JOIN materias m ON ch.id_materia = m.id "
			+ "WHERE id_grupo = :grupo and id_periodo = :periodo "
			+ "AND ch.activo = 'True' AND m.calificacion = 'True' ORDER BY m.nombre", nativeQuery = true)
	List<CargaHoraria> findByGrupoAndPeriodoAndCalificacionSi(@Param("grupo") Integer idGrupo, @Param("periodo") Integer idPeriodo);

	List<CargaHoraria> findByProfesorAndPeriodoAndActivo(Persona profesor, Periodo periodo, Boolean activo);

	@Query(value = "SELECT * FROM cargas_horarias ch "
			+ "INNER JOIN materias m ON ch.id_materia = m.id "
			+ "INNER JOIN grupos g ON ch.id_grupo = g.id "
			+ "WHERE ch.id_profesor = :profesor AND ch.id_periodo = :periodo "
			+ "AND m.curricular = 'True' AND m.calificacion = 'True' "
			+ "ORDER BY g.id_cuatrimestre,g.nombre, m.nombre ", nativeQuery = true)
	List<CargaHoraria> findByProfesorAndPeriodo(@Param("profesor") Persona profesor,@Param("periodo") Periodo periodo);

	@Query(value = "SELECT ch.* " + "FROM dosificaciones d "
			+ "INNER JOIN dosificaciones_cargas dc on d.id=dc.id_dosificacion "
			+ "INNER JOIN cargas_horarias ch on dc.id_carga_horaria = ch.id "
			+ "INNER JOIN materias m on ch.id_materia = m.id "
			// + "WHERE m.id =95 AND d.id_persona<>10383 "
			+ "WHERE m.id = :idMateria AND d.id_persona<> :idPersona LIMIT 1 ", nativeQuery = true)
	CargaHoraria findByIdMateriaAndIdPersona(@Param("idMateria") Integer idMateria,
			@Param("idPersona") Integer idPersona);
	
	@Query(value = "SELECT ch.* FROM cargas_horarias ch "
			   + "INNER JOIN materias m ON ch.id_materia = m.id "
			   + "WHERE ch.id_grupo=:idGrupo  and ch.id_periodo=:idPeriodo AND ch.id_profesor=:idProfesor "
			   + "AND ch.activo = true "
			   + "ORDER BY m.nombre" ,nativeQuery = true)
	List<CargaHoraria> findByGrupoAndProfesorAndPeriodo(@Param("idGrupo") Integer idGrupo,@Param("idProfesor") Integer idProfesor,@Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT ch.* FROM materias m "
			   + "INNER JOIN cargas_horarias ch ON m.id=ch.id_materia "
			   + "INNER JOIN grupos g ON g.id=ch.id_grupo "
			   + "INNER JOIN carreras c ON c.id=g.id_carrera "
			   + "WHERE c.id=:idCarrera AND ch.id_profesor=:idProfesor AND ch.id_materia=:idMateria AND ch.id_periodo=:idPeriodo AND ch.activo=true ORDER BY ch.id_grupo" ,nativeQuery = true)
	List<CargaHoraria> findByCarreraProfesorAndMateriaAndPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idProfesor") Integer idProfesor, @Param("idMateria") Integer idMateria, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT ch.* FROM cargas_horarias ch "
			   + "INNER JOIN grupos g ON g.id=ch.id_grupo "
			   + "INNER JOIN carreras c ON c.id=g.id_carrera "
			   + "INNER JOIN materias m ON ch.id_materia = m.id "
			   + "WHERE c.id=:idCarrera AND ch.id_profesor=:idProfesor AND ch.id_periodo=:idPeriodo "
			   + "AND ch.activo=true AND m.calificacion = 'True' AND m.activo = 'True' "
			   + "ORDER BY ch.id_grupo" ,nativeQuery = true)
	List<CargaHoraria> findByCarreraAndProfesorAndPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idProfesor") Integer idProfesor, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT DISTINCT(ch.*) "
			+ "	FROM cargas_horarias ch "
			+ "	INNER JOIN calendario_evaluacion ce on ce.id_carga_horaria = ch.id "
			+ " 	WHERE ch.id_profesor = :profesor AND ch.id_periodo = :periodo "
			+ " 	AND  ( "
			+ " 		SELECT COUNT(DISTINCT(ce2.id)) "
			+ " 		FROM calendario_evaluacion ce2 "
			+ " 		WHERE ce2.id_carga_horaria = ch.id "
			+ " 	) = ( "
			+ " 		SELECT COUNT(DISTINCT(ce3.id)) "
			+ " 		FROM calendario_evaluacion ce3 "
			+ " 		WHERE ce3.id_carga_horaria = :carga "
			+ " 	) AND ch.id <> :carga", nativeQuery = true)
	List<CargaHoraria> findByPersonaAndPeriodoAndCalendarioEvaluacion(@Param("profesor") Integer idProfesor, @Param("periodo") Integer idPeriodo, @Param("carga") Integer idCarga);
	
	//busca las cargas horarias por profesor, carrera y grupo
	@Query(value = "SELECT ch.* FROM cargas_horarias ch "
			+ "INNER JOIN materias m ON ch.id_materia = m.id "
			+ "INNER JOIN grupos g ON ch.id_grupo = g.id "
			+ "INNER JOIN planes_estudio pe ON pe.id = m.id_plan_estudio "
			+ "INNER JOIN carreras c ON c.id = pe.id_carrera "
			+ "WHERE ch.id_profesor = :idProfesor AND ch.id_periodo = :idPeriodo AND c.id = :idCarrera "
			+ "AND m.curricular = 'True' AND m.calificacion = 'True' "
			+ "ORDER BY g.id_cuatrimestre,g.nombre, m.nombre", nativeQuery = true)
	List<CargaHoraria> findByIdProfesorAndIdCarreraAndIdPeriodo(@Param("idProfesor") Integer idPersona, @Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
	
	//Busca los id de las cargas por professor, carrera y periodo
	@Query(value = "SELECT ch.id FROM cargas_horarias ch "
			+ "INNER JOIN materias m ON ch.id_materia = m.id "
			+ "INNER JOIN grupos g ON ch.id_grupo = g.id "
			+ "INNER JOIN planes_estudio pe ON pe.id = m.id_plan_estudio "
			+ "INNER JOIN carreras c ON c.id = pe.id_carrera "
			+ "WHERE ch.id_profesor = :idProfesor AND ch.id_periodo = :idPeriodo AND c.id = :idCarrera "
			+ "AND m.curricular = 'True' AND m.calificacion = 'True' AND m.activo = 'True' "
			+ "ORDER BY g.id_cuatrimestre,g.nombre, m.nombre", nativeQuery = true)
	List<Integer> findByIDprofesorAndIdCarreraAndIdPeriodo(@Param("idProfesor") Integer idProfesor, @Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
	
	//busca los id de las cargas por profesor y periodo
	@Query(value = "SELECT ch.id FROM cargas_horarias ch "
			+ "INNER JOIN materias m ON ch.id_materia = m.id "
			+ "INNER JOIN grupos g ON ch.id_grupo = g.id "
			+ "WHERE ch.id_profesor = :idProfesor AND ch.id_periodo = :idPeriodo "
			+ "AND m.curricular = 'True' AND m.calificacion = 'True' AND m.activo = 'True' "
			+ "ORDER BY g.id_cuatrimestre,g.nombre, m.nombre ", nativeQuery = true)
	List<Integer> findByIdProfesorAndIdPeriodo(@Param("idProfesor") Integer idProfesor,@Param("idPeriodo") Integer idPeriodo);
}
