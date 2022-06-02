package edu.mx.utdelacosta.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Pregunta;
import edu.mx.utdelacosta.model.dto.PromedioPreguntaDTO;

public interface PreguntaRepository extends CrudRepository<Pregunta, Integer>{
	//Promedio por pregunta para la evaluación docente por grupo
	@Query(value = "SELECT pr.id, pr.consecutivo,pr.descripcion, "
			+ "(SELECT "
			+ "COALESCE(ROUND(AVG(COALESCE((SELECT "
			+ "(SELECT ponderacion FROM opcion_respuestas WHERE id=r.id_opcion_respuesta) "
			+ "FROM respuesta_carga_evaluacion rce "
			+ "INNER JOIN respuestas r ON r.id=rce.id_respuesta "
			+ "INNER JOIN carga_evaluacion ce ON ce.id=rce.id_carga_evaluacion "
			+ "WHERE r.activo=true AND ce.activo=true "
			+ "AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=pr.id AND r.id_persona=p.id AND ce.id_carga=:idCarga AND ce.id_evaluacion=:idEvaluacion),0))*100/5,2),0) "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE ag.id_grupo =:idGrupo)AS promedio "
			+ "FROM preguntas pr "
			+ "WHERE pr.id_evaluacion=:idEvaluacion AND pr.id=:idPregunta ORDER BY pr.consecutivo", nativeQuery = true)
	PromedioPreguntaDTO findPromedioByPregunta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idCarga") Integer idCarga, @Param("idGrupo") Integer idGrupo);
	
	//Promedio por pregunta para la evaluación del tutor
	@Query(value = "SELECT pr.id, pr.consecutivo,pr.descripcion, "
			+ "(SELECT COALESCE(ROUND(AVG(COALESCE( "				
			+ "(SELECT (SELECT ponderacion FROM opcion_respuestas WHERE id=r.id_opcion_respuesta) "
			+ "FROM respuesta_evaluacion_tutor ret "
			+ "INNER JOIN respuestas r ON r.id=ret.id_respuesta "
			+ "INNER JOIN evaluacion_tutor et ON et.id=ret.id_evaluacion_tutor "
			+ "WHERE r.activo=true "
			+ "AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=pr.id "
			+ "AND r.id_persona=p.id AND et.id_grupo=:idGrupo AND et.id_evaluacion=:idEvaluacion),0))*100/5,2),0) "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE ag.id_grupo=:idGrupo)AS promedio "
			+ "FROM preguntas pr "			
			+ "WHERE pr.id_evaluacion =:idEvaluacion AND pr.id=:idPregunta ORDER BY pr.consecutivo", nativeQuery = true)
	PromedioPreguntaDTO findPromedioEvaTuByPregunta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idGrupo") Integer idGrupo);	
	
}
