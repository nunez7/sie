package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionCarga;

public interface DosificacionCargaRepository extends CrudRepository<DosificacionCarga, Integer>{
	
	DosificacionCarga findByDosificacionAndCargaHoraria (Dosificacion dosificacion, CargaHoraria cargaHoraria);
	
	List<DosificacionCarga> findByCargaHoraria(CargaHoraria cargaHoraria);
	
	// se obtiene el numero de cargas con dosificaciones sin entregar, notificaciones/profesor
	@Query(value = "SELECT (SELECT count(d.id) "
			+ "	FROM dosificaciones_cargas dc "
			+ "	INNER JOIN cargas_horarias ch ON dc.id_carga_horaria = ch.id "
			+ "	INNER JOIN dosificaciones d ON dc.id_dosificacion=d.id "
			+ "	WHERE ch.id_profesor = :idProfesor AND ch.id_periodo = :idPeriodo AND d.id IS NULL ) + "
			+ "	(SELECT count(d.id) "
			+ "	FROM dosificacion_importada di "
			+ "	INNER JOIN cargas_horarias ch ON di.id_carga_horaria = ch.id "
			+ "	INNER JOIN dosificaciones d ON di.id_dosificacion=d.id "
			+ "	WHERE ch.id_profesor = :idProfesor AND ch.id_periodo = :idPeriodo AND d.id IS NULL ) AS total", nativeQuery = true)
	Integer countNoEntregadas(@Param("idProfesor") Integer idProfesor, @Param("idPeriodo") Integer idPeriodo);
	
}
