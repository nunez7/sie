package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.model.dtoimport.BajasUtNay;
import edu.mx.utdelacosta.model.dtoimport.CanalizacionesUtNay;
import edu.mx.utdelacosta.model.dtoimport.FocosAtencionUtNay;
import edu.mx.utdelacosta.model.dtoimport.FortalezasGrupoUtNay;
import edu.mx.utdelacosta.model.dtoimport.TemasGruposUtNay;
import edu.mx.utdelacosta.model.dtoimport.TutoriasUtNay;

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
