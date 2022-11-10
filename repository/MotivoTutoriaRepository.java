package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.MotivoTutoria;

public interface MotivoTutoriaRepository extends CrudRepository<MotivoTutoria, Integer>{
	
	@Query(value = "SELECT * FROM motivos_tutoria WHERE id_tutoria_individual=:idTutoria", nativeQuery = true)
	List<MotivoTutoria> findByIdTutoria(@Param("idTutoria") Integer idTutoria);

}
