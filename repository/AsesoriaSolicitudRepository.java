package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.AsesoriaSolicitud;

public interface AsesoriaSolicitudRepository extends CrudRepository<AsesoriaSolicitud, Integer>{
	
	@Query(value = "SELECT * FROM asesoria_solicitud WHERE id_grupo=:idGrupo ORDER BY fecha, hora", nativeQuery = true)
	List<AsesoriaSolicitud> findByIdGrupoOrderByFecha(@Param("idGrupo") Integer idGrupo);

}
