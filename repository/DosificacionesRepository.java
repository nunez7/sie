package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dtoreport.DosificacionPendienteDTO;

public interface DosificacionesRepository extends CrudRepository<Dosificacion, Integer> {
	List<Dosificacion> findByPersona(Persona persona); 
	
	@Query(value = "SELECT d.* "
			+ "FROM dosificaciones d "
			+ "INNER JOIN dosificaciones_cargas dc on d.id=dc.id_dosificacion "
			+ "WHERE dc.id_carga_horaria=:idCargaHoraria AND d.id_corte_evaluativo=:idCorteEvaluativo ", nativeQuery = true)
	Dosificacion findByIdCargaHorariaAndIdCorteEvaluativo(@Param("idCargaHoraria") Integer icCargaHoraria, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
	
	@Query(value="SELECT df.* FROM dosificaciones df "
			+ "INNER JOIN dosificaciones_cargas dfc on dfc.id_dosificacion=df.id "
			+ "WHERE dfc.id_carga_horaria = :idCargaHoraria", nativeQuery = true)
	List<Dosificacion> findByIdCargaHoraria(@Param("idCargaHoraria") Integer cargaHoraria);
	
	@Query(value="SELECT ds.id as idDosificacion, CONCAT(p.nombre,' ',p.primer_apellido,' ',p.segundo_apellido) as profesor, "
			+ "ct.consecutivo as parcial, m.nombre as materia, g.nombre as grupo, c.nombre as carrera "
			+ "FROM dosificaciones ds "
			+ "INNER JOIN dosificaciones_cargas dc ON dc.id_dosificacion = ds.id "
			+ "INNER JOIN cargas_horarias ch ON ch.id = dc.id_carga_horaria "
			+ "INNER JOIN materias m ON m.id = ch.id_materia "
			+ "INNER JOIN grupos g ON g.id = ch.id_grupo "
			+ "INNER JOIN carreras c ON c.id = g.id_carrera "
			+ "INNER JOIN personas p ON ds.id_persona = p.id "
			+ "INNER JOIN cortes_evaluativos ct ON ct.id = ds.id_corte_evaluativo "
			+ "AND c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :persona) "
			+ "AND ds.valida_director = 'False' AND g.id_periodo = :periodo", nativeQuery = true)
	List<DosificacionPendienteDTO> getAllPendientesAndPersonaCarreraAndPeriodo(@Param("persona") Integer idPersona, @Param("periodo") Integer idPeriodo);
	
	@Query(value = "SELECT d.* "
			+ "FROM dosificaciones d "
			+ "INNER JOIN dosificaciones_cargas dc on d.id=dc.id_dosificacion "
			+ "INNER JOIN cargas_horarias ch on dc.id_carga_horaria = ch.id "
			+ "INNER JOIN materias m on ch.id_materia = m.id "
			+ "WHERE m.id = :idMateria AND d.id_persona<> :idPersona LIMIT 1 ", nativeQuery = true)
	Dosificacion findByIdMateriaAndIdPersona(@Param("idMateria") Integer idMateria, @Param("idPersona") Integer idPersona);
	
	@Query(value = "SELECT COUNT(DISTINCT(ds.id)) as dosificaciones "
			+ "FROM dosificaciones ds "
			+ "INNER JOIN dosificaciones_cargas dc ON dc.id_dosificacion = ds.id "
			+ "INNER JOIN cargas_horarias ch ON ch.id = dc.id_carga_horaria "
			+ "INNER JOIN materias m ON m.id = ch.id_materia "
			+ "INNER JOIN grupos g ON g.id = ch.id_grupo "
			+ "INNER JOIN carreras c ON c.id = g.id_carrera "
			+ "INNER JOIN personas p ON ds.id_persona = p.id "
			+ "AND c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :persona) "
			+ "AND ds.valida_director = 'False' AND g.id_periodo = :periodo", nativeQuery = true)
	Integer coountByPersonaCarreraAndPeriodo(@Param("persona") Integer idPersona, @Param("periodo") Integer idPeriodo);

}
