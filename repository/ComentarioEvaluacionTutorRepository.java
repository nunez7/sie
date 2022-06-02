package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.ComentarioEvaluacionTutor;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;

public interface ComentarioEvaluacionTutorRepository extends CrudRepository<ComentarioEvaluacionTutor, Integer>{

	//Se valida si ya hay un comentario asociado a al grupo y persona para la evaluacion del tutor
	@Query(value = "SELECT cet.* "
			+ "FROM comentarios_evaluacion_tutor cet "
			+ "INNER JOIN comentarios c "
			+ "ON c.id=cet.id_comentario "
			+ "INNER JOIN evaluacion_tutor et "
			+ "ON et.id = cet.id_evaluacion_tutor "
			+ "WHERE cet.id_evaluacion=:idEvaluacion AND c.id_persona=:idPersona "
			+ "AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion AND c.activo=true", nativeQuery = true)
	ComentarioEvaluacionTutor findEvaluacionComentarioByPersona(@Param("idPersona") Integer idPersona, @Param("idGrupo") Integer idGrupo, @Param("idEvaluacion") Integer idEvaluacion);
	
	@Query(value = "SELECT a.id,(SELECT co.comentario "
			+ "FROM comentarios_evaluacion_tutor cet "
			+ "INNER JOIN comentarios co "
			+ "ON co.id=cet.id_comentario "
			+ "INNER JOIN evaluacion_tutor et "
			+ "ON et.id=cet.id_evaluacion_tutor "
			+ "WHERE co.id_persona=p.id AND et.id_evaluacion=:idEvaluacion AND et.id_grupo=:idGrupo) "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN personas p ON p.id=a.id_persona "		
			+ "WHERE g.id=:idGrupo", nativeQuery = true)
	List<ComentarioDTO> findComentarioByGrupo(@Param("idEvaluacion") Integer idEvaluacion, @Param("idGrupo") Integer idGrupo);
	
}
