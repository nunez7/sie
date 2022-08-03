package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TemaGrupal;

public interface TemaGrupalRepository extends CrudRepository<TemaGrupal, Integer>{

	List<TemaGrupal> findByGrupo(Grupo grupo);
	
	@Query(value = "SELECT * FROM tema_grupal where id_grupo =:idGrupo "
			+ "AND fecha_registro >=:fechaInicio AND fecha_registro <=:fechaFin", nativeQuery = true)
	List<TemaGrupal> findByGrupoAndFechaProgramada(@Param("idGrupo") Integer idGrupo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
}
