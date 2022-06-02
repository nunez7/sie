package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.RespuestaCargaEvaluacion;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;

public interface RespuestaCargaEvaluacionRepository extends CrudRepository<RespuestaCargaEvaluacion, Integer>{
	//Se valida si ya una repuesta asociada a la evaluaci�n y carga horaria	
	@Query(value = "SELECT rce.* "
			+ "FROM respuesta_carga_evaluacion rce "
			+ "INNER JOIN respuestas r ON r.id=rce.id_respuesta "
			+ "INNER JOIN carga_evaluacion ce ON ce.id=rce.id_carga_evaluacion "
			+ "WHERE r.activo=true AND ce.activo=true "
			+ "AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=:idPregunta AND r.id_persona=:idPersona AND ce.id_carga=:idCarga AND ce.id_evaluacion=:idEvaluacion", nativeQuery = true)
	RespuestaCargaEvaluacion findRespuestaByPregunta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona, @Param("idCarga") Integer idCarga);		
	
	//Extrae las opciones de repuesta con la opciones seleccionada si la hay, 
	//para la persona en cesión, en la evaluación, pregunta y carga horaria seleccionada. 
	//Retorna el activo de la repuesta vinculada en la columna respuesta como un boolean para porder validar en la vista la opción seleccionada si la ahí. 
	@Query(value = "SELECT opr.* , "
			+ "(SELECT r.activo "
			+ "FROM respuesta_carga_evaluacion rce "
			+ "INNER JOIN respuestas r ON r.id=rce.id_respuesta "
			+ "INNER JOIN carga_evaluacion ce ON ce.id=rce.id_carga_evaluacion "
			+ "WHERE r.activo=true AND ce.activo=true "
			+ "AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=:idPregunta AND r.id_opcion_respuesta=opr.id AND r.id_persona=:idPersona AND ce.id_carga=:idCarga AND ce.id_evaluacion=:idEvaluacion) AS respuesta "
			+ "FROM opcion_respuestas opr "
			+ "INNER JOIN pregunta_opcion_respuesta popr "
			+ "ON opr.id=popr.id_opcion_respuesta "			
			+ "WHERE popr.id_pregunta=:idPregunta", nativeQuery = true)
	List<OpcionRespuestaDTO> findOpcionRespuestaAndRespuestaByPregunta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona, @Param("idCarga") Integer idCarga);
	
	@Query(value = "SELECT COUNT((SELECT ROUND(AVG((SELECT(SELECT ponderacion FROM opcion_respuestas WHERE id=r.id_opcion_respuesta) FROM respuesta_carga_evaluacion rce " 
			 + "INNER JOIN respuestas r ON r.id=rce.id_respuesta "
			 + "INNER JOIN carga_evaluacion ce ON ce.id=rce.id_carga_evaluacion "
			 + "WHERE r.activo=true AND ce.activo=true "
			 + "AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=pr.id AND r.id_persona=p.id AND ce.id_carga=:idCarga AND ce.id_evaluacion=:idEvaluacion)),2)FROM preguntas pr WHERE pr.id_evaluacion =:idEvaluacion)) "
			 + "FROM alumnos_grupos ag "
			 + "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			 + "INNER JOIN personas p ON p.id=a.id_persona "
			 + "INNER JOIN grupos g ON g.id=ag.id_grupo "
			 + "INNER JOIN cargas_horarias ch ON ch.id_grupo=g.id "
			 + "WHERE g.id =:idGrupo AND ch.id=:idCarga", nativeQuery = true)
	Integer countRePuestasByAlumno(@Param("idEvaluacion") Integer idEvaluacion,@Param("idGrupo") Integer idGrupo, @Param("idCarga") Integer idCarga);
}
