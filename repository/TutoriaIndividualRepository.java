package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;

public interface TutoriaIndividualRepository extends CrudRepository<TutoriaIndividual, Integer>{
	
	List<TutoriaIndividual> findByAlumnoAndGrupo(Alumno alumno, Grupo grupo);
	
	List<TutoriaIndividual> findFirst5ByAlumnoOrderByFechaRegistroDesc(Alumno alumno);
	
	List<TutoriaIndividual> findByAlumnoOrderByFechaRegistroDesc(Alumno alumno);
	
	TutoriaIndividual findTopByOrderByIdDesc();
	
	@Query(value = "SELECT * FROM tutoria_individual where id_grupo =:idGrupo And id_alumno=:idAlumno "
			+ "AND fecha_tutoria >=:fechaInicio AND fecha_tutoria <=:fechaFin", nativeQuery = true)
	List<TutoriaIndividual> findByGrupoAndPersonaAndFechaTutoria(@Param("idGrupo") Integer idGrupo, @Param("idAlumno") Integer idAlumno, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
			
}
