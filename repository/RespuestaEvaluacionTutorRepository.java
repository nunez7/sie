package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.RespuestaEvaluacionTutor;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface RespuestaEvaluacionTutorRepository extends CrudRepository<RespuestaEvaluacionTutor, Integer>{
	//Se valida si ya una repuesta asociada a la evaluaci�n y a este grupo
	@Query(value = "SELECT ret.* FROM respuestas r "
			+ "INNER JOIN respuesta_evaluacion_tutor ret "
			+ "ON r.id=ret.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=ret.id_evaluacion_tutor "
			+ "WHERE r.activo AND r.id_pregunta=:idPregunta AND r.id_persona=:idPersona AND r.id_evaluacion=:idEvaluacion "
			+ "AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion", nativeQuery = true)
	RespuestaEvaluacionTutor findRespuestaByPregunta(@Param("idPersona") Integer idPersona, @Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idGrupo") Integer idGrupo);
	
	//Opciones de respuesta y respuesta asociadas a la evaluaci�n del tutor y el alumno  
	@Query(value = "SELECT opr.* , "
			+ "(SELECT r.activo FROM respuestas r "
			+ "INNER JOIN respuesta_evaluacion_tutor ret ON r.id=ret.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=ret.id_evaluacion_tutor "
			+ "WHERE r.activo AND r.id_pregunta=:idPregunta AND r.id_persona=:idPersona AND r.id_evaluacion=:idEvaluacion AND r.id_opcion_respuesta=opr.id "
			+ "AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion) AS respuesta "
			+ "FROM opcion_respuestas opr "
			+ "INNER JOIN pregunta_opcion_respuesta popr "
			+ "ON opr.id=popr.id_opcion_respuesta  "			
			+ "WHERE popr.id_pregunta=:idPregunta", nativeQuery = true)
	List<OpcionRespuestaDTO> findOpcionRespuestaAndRespuestaByPregunta(@Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona,@Param("idEvaluacion") Integer idEvaluacion, @Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT COUNT((SELECT ROUND(AVG( "
			+ "(SELECT (SELECT ponderacion FROM opcion_respuestas WHERE id=r.id_opcion_respuesta) "
			+ "FROM respuesta_evaluacion_tutor ret "
			+ "INNER JOIN respuestas r ON r.id=ret.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=ret.id_evaluacion_tutor "
			+ "WHERE r.activo=true AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=pr.id "
			+ "AND r.id_persona=p.id AND et.id_grupo=g.id AND et.id_evaluacion=:idEvaluacion))*100/5,2) "
			+ "FROM preguntas pr WHERE pr.id_evaluacion =:idEvaluacion)) "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo WHERE g.id =:idGrupo", nativeQuery = true)
	Integer countEncuestadosByGrupo(@Param("idEvaluacion") Integer idEvaluacion,@Param("idGrupo") Integer idGrupo);
	
	//cuenta las respuestas por alumno y grupo
	@Query(value = "SELECT COUNT(ret.*) as respuestas FROM respuesta_evaluacion_tutor ret "
			+ "INNER JOIN respuestas r ON r.id = ret.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id = ret.id_evaluacion_tutor "
			+ "WHERE r.activo = 'True' AND et.id_grupo = :idGrupo "
			+ "AND r.id_persona = :idPersona", nativeQuery = true)
	Integer countByIdPersonaAndIdGrupoAndActivo(@Param("idPersona") Integer idPersona, @Param("idGrupo") Integer idGrupo);
	
}
