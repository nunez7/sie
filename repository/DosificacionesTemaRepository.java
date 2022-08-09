package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionTema;
import edu.mx.utdelacosta.model.TemaUnidad;

public interface DosificacionesTemaRepository extends CrudRepository<DosificacionTema, Integer>{
	
	DosificacionTema findByTemaAndDosificacion(TemaUnidad tema, Dosificacion dosificacion);
	List<DosificacionTema> findByDosificacion(Dosificacion dosificacion);
	@Query(value = "SELECT dt.* "
			+ "	FROM temas_unidad tu "
			+ "	INNER JOIN dosificacion_tema dt on dt.id_tema = tu.id "
			+ "	WHERE tu.id_unidad_tematica = :unidad and dt.id_dosificacion = :dosificacion ", nativeQuery = true)
	List<DosificacionTema> findByUnidadTematicaAndDosificacion(@Param("unidad") Integer unidadTematica, @Param("dosificacion") Integer dosificacion);
}
