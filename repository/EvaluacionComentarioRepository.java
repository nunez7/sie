package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.EvaluacionComentario;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;

public interface EvaluacionComentarioRepository extends CrudRepository<EvaluacionComentario, Integer>{
	//Se valida si ya ahi un comentario asociado a la carga horaria 	
	@Query(value = "SELECT ec.* "
			+ "FROM evaluacion_comentario ec "
			+ "INNER JOIN comentarios c "
			+ "ON c.id=ec.id_comentario "
			+ "INNER JOIN carga_evaluacion ce "
			+ "ON ce.id=ec.id_carga_evaluacion "
			+ "WHERE c.id_persona=:idPersona AND c.activo=true AND ec.id_evaluacion=:idEvaluacion AND ce.id_carga=:idCarga", nativeQuery = true)
	EvaluacionComentario findEvaluacionComentarioByPersona(@Param("idPersona") Integer idPersona, @Param("idEvaluacion") Integer idEvaluacion, @Param("idCarga") Integer idCarga);
	
	@Query(value = "SELECT a.id,(SELECT co.comentario "
			+ "FROM evaluacion_comentario ec "
			+ "INNER JOIN comentarios co "
			+ "ON co.id=ec.id_comentario "
			+ "INNER JOIN carga_evaluacion ce "
			+ "ON ce.id=ec.id_carga_evaluacion "
			+ "WHERE co.id_persona=p.id AND co.activo=true AND ec.id_evaluacion=:idEvaluacion AND ce.id_carga=ch.id) AS comentario "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN cargas_horarias ch ON ch.id_grupo=g.id "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE c.id=:idCarrera AND ch.id_profesor=:idProfesor AND ch.id_materia=:idMateria "			
			+ "AND ch.id_periodo=:idPeriodo AND ch.activo=true ORDER BY ch.id_grupo", nativeQuery = true)
	List<ComentarioDTO> findComentarioByAlumno(@Param("idEvaluacion") Integer idEvaluacion, @Param("idCarrera") Integer idCarrera, @Param("idProfesor") Integer idProfesor, @Param("idMateria") Integer idMateria, @Param("idPeriodo") Integer idPeriodo);
}
