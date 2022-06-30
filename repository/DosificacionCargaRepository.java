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
	@Query(value = "SELECT COUNT(*) "
			+ "FROM cargas_horarias ch "
			+ "LEFT JOIN dosificaciones_cargas dc ON dc.id_carga_horaria=ch.id "
			+ "WHERE ch.id_profesor = :idProfesor and ch.id_periodo = :idPeriodo and dc.id IS NULL", nativeQuery = true)
	Integer countNoEntregadas(@Param("idProfesor") Integer idProfesor, @Param("idPeriodo") Integer idPeriodo);
	
}
