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
	
	//cuenta las respuestas por alumno y grupo
	@Query(value = "SELECT COUNT(ret.*) as respuestas FROM respuesta_evaluacion_tutor ret "
			+ "INNER JOIN respuestas r ON r.id = ret.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id = ret.id_evaluacion_tutor "
			+ "WHERE r.activo = 'True' AND et.id_grupo = :idGrupo "
			+ "AND r.id_persona = :idPersona", nativeQuery = true)
	Integer countByIdPersonaAndIdGrupoAndActivo(@Param("idPersona") Integer idPersona, @Param("idGrupo") Integer idGrupo);
	
	
	//suma las ponderaciones de las respuestas por preguta, grupo y evaluacion
	@Query(value = "SELECT COALESCE(SUM((SELECT ponderacion FROM opcion_respuestas WHERE id=r.id_opcion_respuesta)),0) AS total "
			+ "FROM respuesta_evaluacion_tutor ret "
			+ "INNER JOIN respuestas r ON r.id=ret.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=ret.id_evaluacion_tutor "
			+ "WHERE r.activo='True' AND r.id_evaluacion=:idEvaluacion "
			+ "AND r.id_pregunta=:idPregunta AND et.id_grupo=:idGrupo "
			+ "AND et.id_evaluacion=:idEvaluacion", nativeQuery = true)
	Integer sumPonderacionByIdPreguntaAndIdGrupoAndIdEvaluacion(@Param("idPregunta") Integer idPregunta, @Param("idGrupo") Integer idGrupo,
					@Param("idEvaluacion") Integer idEvaluacion);
	
	//cuenta las respuestas de una evaluacion por carga y pregunta
	@Query(value = "SELECT COUNT((SELECT ponderacion FROM opcion_respuestas WHERE id=r.id_opcion_respuesta)) AS total "
			+ "FROM respuesta_evaluacion_tutor ret "
			+ "INNER JOIN respuestas r ON r.id=ret.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=ret.id_evaluacion_tutor "
			+ "WHERE r.activo='True' AND r.id_evaluacion=:idEvaluacion "
			+ "AND r.id_pregunta=:idPregunta AND et.id_grupo=:idGrupo "
			+ "AND et.id_evaluacion=:idEvaluacion", nativeQuery = true)
	Integer countRespuestasByIdPreguntaAndIdGrupoAndIdEvaluacion(@Param("idPregunta") Integer idPregunta, @Param("idGrupo") Integer idGrupo,
					@Param("idEvaluacion") Integer idEvaluacion);
	
	//cuenta los alumnos que respondieron la evaluacion por grupo y periodo
	@Query(value = "SELECT COUNT(DISTINCT(r.id_persona)) as alumnos FROM grupos g "
			+ "INNER JOIN evaluacion_tutor et ON g.id = et.id_grupo "
			+ "INNER JOIN respuesta_evaluacion_tutor ret ON ret.id_evaluacion_tutor = et.id "
			+ "INNER JOIN respuestas r ON r.id = ret.id_respuesta "
			+ "WHERE g.id = :idGrupo AND g.id_periodo = :idPeriodo ", nativeQuery = true)
	Integer countAlumnosByIdGrupoAndIdPeriodo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo);
	
}
