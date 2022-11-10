package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TemaGrupal;

public interface TemaGrupalRepository extends CrudRepository<TemaGrupal, Integer>{

	List<TemaGrupal> findByGrupoOrderByFechaProgramada(Grupo grupo);
	
	@Query(value = "SELECT * FROM tema_grupal where id_grupo =:idGrupo "
			+ "AND fecha_registro >=:fechaInicio AND fecha_registro <=:fechaFin ORDER BY fecha_programada", nativeQuery = true)
	List<TemaGrupal> findByGrupoAndFechaProgramada(@Param("idGrupo") Integer idGrupo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	@Query(value = "SELECT t.* FROM tema_grupal t "
			+ "INNER JOIN grupos g ON g.id=t.id_grupo "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo AND t.fecha_registro >=:fechaInicio "
			+ "AND t.fecha_registro <=:fechaFin ORDER BY t.fecha_programada", nativeQuery = true)
	List<TemaGrupal> findByCarreraAndFechaProgramada(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	@Query(value = "SELECT COUNT(t.*) FROM tema_grupal t "
			+ "INNER JOIN grupos g ON g.id=t.id_grupo "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "WHERE ca.id=:idCarrera AND g.id_periodo=:idPeriodo AND g.id_turno=:idTurno ORDER BY t.fecha_programada", nativeQuery = true)
	Integer findTotalByCarreraAndPeriodoAndTurno(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idTurno") Integer idTurno);
	
}
