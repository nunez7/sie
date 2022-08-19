package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.util.imports.dto.BajasUtNay;
import edu.mx.utdelacosta.util.imports.dto.CanalizacionesUtNay;
import edu.mx.utdelacosta.util.imports.dto.FocosAtencionUtNay;
import edu.mx.utdelacosta.util.imports.dto.FortalezasGrupoUtNay;
import edu.mx.utdelacosta.util.imports.dto.TemasGruposUtNay;
import edu.mx.utdelacosta.util.imports.dto.TutoriasUtNay;

public interface TutoriaIndividualRepository extends CrudRepository<TutoriaIndividual, Integer>{
	
	List<TutoriaIndividual> findByAlumnoAndGrupo(Alumno alumno, Grupo grupo);
	
	List<TutoriaIndividual> findFirst5ByAlumnoOrderByFechaRegistroDesc(Alumno alumno);
	
	List<TutoriaIndividual> findByAlumnoOrderByFechaRegistroDesc(Alumno alumno);
	
	List<TutoriaIndividual> findByAlumnoAndValidadaOrderByFechaRegistroDesc(Alumno alumno, Boolean validada);
	
	TutoriaIndividual findTopByOrderByIdDesc();
	
	@Query(value = "SELECT * FROM tutoria_individual where id_grupo =:idGrupo And id_alumno=:idAlumno "
			+ "AND fecha_registro >=:fechaInicio AND fecha_registro <=:fechaFin", nativeQuery = true)
	List<TutoriaIndividual> findByGrupoAndPersonaAndFechaTutoria(@Param("idGrupo") Integer idGrupo, @Param("idAlumno") Integer idAlumno, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	@Query(value = "SELECT * FROM tutoria_individual where id_grupo =:idGrupo "
			+ "AND fecha_registro >=:fechaInicio AND fecha_registro <=:fechaFin", nativeQuery = true)
	List<TutoriaIndividual> findByGrupoAndFechaTutoria(@Param("idGrupo") Integer idGrupo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	@Query(value = "SELECT t.* FROM tutoria_individual t "
			+ "INNER JOIN grupos g ON g.id=t.id_grupo  "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo AND t.fecha_registro >=:fechaInicio AND t.fecha_registro <=:fechaFin", nativeQuery = true)
	List<TutoriaIndividual> findByCarreraAndFechaTutoria(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	@Query(value = "SELECT COUNT(t.*) FROM tutoria_individual t "
			+ "INNER JOIN grupos g ON g.id=t.id_grupo "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo AND g.id_turno=:idTurno", nativeQuery = true)
	Integer findTotalByCarreraAndPeriodoAndTurno(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idTurno") Integer idTurno);
	
	@Query(value = "SELECT COUNT(DISTINCT t.id_alumno) FROM tutoria_individual t "
			+ "INNER JOIN grupos g ON g.id=t.id_grupo "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo AND g.id_turno=:idTurno", nativeQuery = true)
	Integer findTotalDistinctAlumnoByCarreraAndPeriodoAndTurno(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idTurno") Integer idTurno);
	
	//importaciones
	@Query(value = "SELECT * FROM tutorias_ut_nay ORDER BY id", nativeQuery = true)
	List<TutoriasUtNay> findAllUtNay();
	
	@Query(value = "SELECT * FROM canalizaciones_ut_nay ORDER BY id", nativeQuery = true)
	List<CanalizacionesUtNay> findCanalizacionesByUtNay();
	
	@Query(value = "SELECT * FROM temas_grupos_ut_nay ORDER BY id", nativeQuery = true)
	List<TemasGruposUtNay> findTemasGrupalesByUtNay();
	
	@Query(value = "SELECT * FROM focos_atencion_ut_nay ORDER BY id", nativeQuery = true)
	List<FocosAtencionUtNay> findFocosAtencionByUtNay();
	
	@Query(value = "SELECT * FROM fortalezas_grupo_ut_nay ORDER BY id", nativeQuery = true)
	List<FortalezasGrupoUtNay> findFortalezasGrupoByUtNay();
	
	@Query(value = "SELECT * FROM bajas_ut_nay ORDER BY id", nativeQuery = true)
	List<BajasUtNay> findBajasByUtNay();
	
}
