package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.ProrrogaAdeudo;

public interface ProrrogaAdeudoRepository extends CrudRepository<ProrrogaAdeudo, Integer> {
	
	@Query(value = "SELECT * FROM prorrogas_adeudos WHERE id_persona = :idPersona "
			+ "ORDER BY id DESC", nativeQuery = true)
	List<ProrrogaAdeudo> findByIdPersona(@Param("idPersona") Integer idPersona);
	
	//busca la ultima prorroga pro persona
	@Query(value = "SELECT * FROM prorrogas_adeudos WHERE id_persona = :idPersona "
			+ "ORDER BY id DESC LIMIT 1", nativeQuery = true)
	ProrrogaAdeudo findLastByIdPersona(@Param("idPersona") Integer idPersona);

}
