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
			+ "AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=:idPregunta AND r.id_opcion_respuesta=opr.id AND r.id_persona=:idPersona "
			+ "AND ce.id_carga=:idCarga AND ce.id_evaluacion=:idEvaluacion ORDER BY rce.id DESC LIMIT 1) AS respuesta "
			+ "FROM opcion_respuestas opr "
			+ "INNER JOIN pregunta_opcion_respuesta popr "
			+ "ON opr.id=popr.id_opcion_respuesta "			
			+ "WHERE popr.id_pregunta=:idPregunta", nativeQuery = true)
	List<OpcionRespuestaDTO> findOpcionRespuestaAndRespuestaByPregunta(@Param("idEvaluacion") Integer idEvaluacion, @Param("idPregunta") Integer idPregunta, @Param("idPersona") Integer idPersona, @Param("idCarga") Integer idCarga);
	
	//cuenta las repuestas por evaluacion, cargahoraria y persona
	@Query(value = "SELECT COUNT(rce.*) as respuestas "
			+ "FROM respuesta_carga_evaluacion rce "
			+ "INNER JOIN respuestas r ON r.id=rce.id_respuesta "
			+ "INNER JOIN carga_evaluacion ce ON ce.id=rce.id_carga_evaluacion "
			+ "WHERE r.activo=true AND ce.activo= 'True' "
			+ "AND r.id_evaluacion=:idEvaluacion AND r.id_persona=:idPersona "
			+ "AND ce.id_carga=:idCargaHoraria AND ce.id_evaluacion=:idEvaluacion ", nativeQuery = true)
	Integer countByIdPersonaAndIdEvaluacionAndIdCargaHoraria(@Param("idPersona") Integer idPersona, @Param("idEvaluacion") Integer idEvaluacion, 
				@Param("idCargaHoraria") Integer idCargaHoraria);
	
	//suma las ponderaciones de las respuestas de una evaluacion por carga y pregunta
	@Query(value = "SELECT COALESCE(SUM((SELECT ponderacion FROM opcion_respuestas WHERE id=r.id_opcion_respuesta)),0) AS total "
			+ "FROM respuesta_carga_evaluacion rce "
			+ "INNER JOIN respuestas r ON r.id=rce.id_respuesta "
			+ "INNER JOIN carga_evaluacion ce ON ce.id=rce.id_carga_evaluacion "
			+ "WHERE r.activo=true AND ce.activo='True' "
			+ "AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=:idPregunta "
			+ "AND ce.id_carga=:idCarga AND ce.id_evaluacion=:idEvaluacion ", nativeQuery = true)
	Integer sumPonderacionByIdPreguntaAndIdCargaHorariaAndIdEvaluacion(@Param("idPregunta") Integer idPregunta, @Param("idCarga") Integer idCargaHoraria,
				@Param("idEvaluacion") Integer idEvaluacion);
	
	//cuenta las respuestas de una evaluacion por carga y pregunta
	@Query(value = "SELECT COUNT((SELECT ponderacion FROM opcion_respuestas WHERE id=r.id_opcion_respuesta)) AS total "
			+ "FROM respuesta_carga_evaluacion rce "
			+ "INNER JOIN respuestas r ON r.id=rce.id_respuesta "
			+ "INNER JOIN carga_evaluacion ce ON ce.id=rce.id_carga_evaluacion "
			+ "WHERE r.activo=true AND ce.activo='True' "
			+ "AND r.id_evaluacion=:idEvaluacion AND r.id_pregunta=:idPregunta "
			+ "AND ce.id_carga=:idCarga AND ce.id_evaluacion=:idEvaluacion ", nativeQuery = true)
	Integer countRespuestasByIdPreguntaAndIdCargaHorariaAndIdEvaluacion(@Param("idPregunta") Integer idPregunta, @Param("idCarga") Integer idCargaHoraria,
					@Param("idEvaluacion") Integer idEvaluacion);
	
	//cuentas los alumnos que respondieron por profesor, carrera, periodo
	@Query(value = "SELECT COUNT(DISTINCT(r.id_persona)) as alumnos FROM cargas_horarias ch "
			+ "INNER JOIN materias m ON m.id=ch.id_materia "
			+ "INNER JOIN grupos g on g.id=ch.id_grupo "
			+ "INNER JOIN carreras c on c.id=g.id_carrera "
			+ "INNER JOIN carga_evaluacion ce ON ce.id_carga = ch.id "
			+ "INNER JOIN respuesta_carga_evaluacion rce ON rce.id_carga_evaluacion = ce.id "
			+ "INNER JOIN respuestas r ON r.id = rce.id_respuesta "
			+ "WHERE ch.id_profesor = :idProfesor AND g.id_periodo = :idPeriodo "
			+ "AND g.id_carrera = :idCarrera "
			+ "AND m.calificacion = 'True' AND m.activo = 'True' "
			+ "AND m.curricular = 'True' ", nativeQuery = true)
	Integer countAlumnosByIdProfesorAndIdCarreraAndIdPeriodo(@Param("idProfesor") Integer idProfesor, @Param("idCarrera") Integer idCarrera,
					@Param("idPeriodo") Integer idPeriodo);
	
	//cuenta los alumnos que contestaron por profesor y periodo
	@Query(value = "SELECT COUNT(DISTINCT(r.id_persona)) as alumnos FROM cargas_horarias ch "
			+ "INNER JOIN materias m ON m.id=ch.id_materia "
			+ "INNER JOIN grupos g on g.id=ch.id_grupo "
			+ "INNER JOIN carreras c on c.id=g.id_carrera "
			+ "INNER JOIN carga_evaluacion ce ON ce.id_carga = ch.id "
			+ "INNER JOIN respuesta_carga_evaluacion rce ON rce.id_carga_evaluacion = ce.id "
			+ "INNER JOIN respuestas r ON r.id = rce.id_respuesta "
			+ "WHERE ch.id_profesor = :idProfesor AND g.id_periodo = :idPeriodo "
			+ "AND m.calificacion = 'True' AND m.activo = 'True' "
			+ "AND m.curricular = 'True'", nativeQuery = true)
	Integer countAlumnosByIdProfesorAndIdPeriodo(@Param("idProfesor") Integer idProfesor, @Param("idPeriodo") Integer idPeriodo);
}
