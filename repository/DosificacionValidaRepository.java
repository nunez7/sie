package edu.mx.utdelacosta.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.DosificacionValida;

public interface DosificacionValidaRepository extends CrudRepository<DosificacionValida, Integer>{
	
	//busca una dosificacion valida por idDosificacion
	@Query(value = "SELECT dv.* FROM dosificaciones_valida dv "
			+ "INNER JOIN dosificaciones ds ON dv.id_dosificacion = ds.id "
			+ "WHERE dv.id_dosificacion = :idDosificacion AND ds.activo = 'True' ", nativeQuery = true)
	DosificacionValida buscarPorIdDosificacion(@Param("idDosificacion") Integer id);
}
