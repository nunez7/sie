package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.RespuestaEvaluacionInicial;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface RespuestaEvaluacionInicialRepository extends CrudRepository<RespuestaEvaluacionInicial, Integer>{
	//Opciones de repuesta para la pregunta enviada junto con su repuesta si es que la ahí 
	@Query(value = "SELECT opr.*, (SELECT r.activo FROM respuestas r "
			+ "INNER JOIN respuesta_evaluacion_inicial rei ON r.id=rei.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=rei.id_evaluacion_tutor "
			+ "WHERE r.activo AND r.id_pregunta=:idPregunta AND r.id_persona=:idPersona AND r.id_evaluacion=:idEvaluacion "
			+ "AND r.id_opcion_respuesta=opr.id AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion) AS respuesta "
			+ "FROM opcion_respuestas opr INNER JOIN pregunta_opcion_respuesta popr "
			+ "ON opr.id=popr.id_opcion_respuesta WHERE popr.id_pregunta=:idPregunta", nativeQuery = true)
	List<OpcionRespuestaDTO> findOpcionRespuestaAndRespuestaByPregunta(@Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona,@Param("idEvaluacion") Integer idEvaluacion, @Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT opr.* FROM respuestas r "
			+ "INNER JOIN respuesta_evaluacion_inicial rei ON r.id=rei.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=rei.id_evaluacion_tutor "
			+ "INNER JOIN opcion_respuestas opr ON opr.id=r.id_opcion_respuesta "
			+ "WHERE r.activo AND r.id_pregunta=:idPregunta AND r.id_persona=:idPersona AND r.id_evaluacion=:idEvaluacion "
			+ "AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion", nativeQuery = true)
	List<OpcionRespuestaDTO> findRespuestaByPregunta(@Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona,@Param("idEvaluacion") Integer idEvaluacion, @Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT rei.* FROM respuestas r "
			+ "INNER JOIN respuesta_evaluacion_inicial rei ON r.id=rei.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=rei.id_evaluacion_tutor "
			+ "WHERE r.activo = true AND r.id_pregunta=:idPregunta "
			+ "AND r.id_persona=:idPersona AND r.id_evaluacion=:idEvaluacion "
			+ "AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion", nativeQuery = true)
	RespuestaEvaluacionInicial findRespuestaCerradaByPregunta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona, @Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT rei.* FROM respuesta_comentario rc "
			+ "INNER JOIN comentarios c ON c.id=rc.id_comentario "
			+ "INNER JOIN respuesta_evaluacion_inicial rei ON rc.id=rei.id_respuesta_comentario "
			+ "INNER JOIN evaluacion_tutor et ON et.id=rei.id_evaluacion_tutor "
			+ "WHERE c.activo = true AND rc.id_pregunta=:idPregunta "
			+ "AND c.id_persona=:idPersona AND rc.id_evaluacion=:idEvaluacion "
			+ "AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion", nativeQuery = true)
	RespuestaEvaluacionInicial findRespuestaAbiertaByPregunta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona, @Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT rei.* FROM respuestas r "
			+ "INNER JOIN respuesta_evaluacion_inicial rei ON r.id=rei.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=rei.id_evaluacion_tutor "
			+ "WHERE r.activo = true AND r.id_pregunta=:idPregunta "
			+ "AND r.id_persona=:idPersona AND r.id_evaluacion=:idEvaluacion "
			+ "AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion", nativeQuery = true)
	List<RespuestaEvaluacionInicial> findRespuestaCerradaMultipleByPregunta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona, @Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT rei.* FROM respuestas r "
			+ "INNER JOIN respuesta_evaluacion_inicial rei ON r.id=rei.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=rei.id_evaluacion_tutor "
			+ "WHERE r.activo = true AND r.id_pregunta=:idPregunta AND r.id_opcion_respuesta=:idOpcionRespuesta "
			+ "AND r.id_persona=:idPersona AND r.id_evaluacion=:idEvaluacion "
			+ "AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion", nativeQuery = true)
	RespuestaEvaluacionInicial findRespuestaCerradaByPreguntaAndOpcionRespuesta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idOpcionRespuesta") Integer idOpcionRespuesta, @Param("idPersona") Integer idPersona, @Param("idGrupo") Integer idGrupo);
	
	
}
