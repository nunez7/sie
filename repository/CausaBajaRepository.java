package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.mx.utdelacosta.model.CausaBaja;

public interface CausaBajaRepository extends JpaRepository<CausaBaja, Integer>{
	
	@Query(value = "SELECT * FROM causas_baja WHERE estatus = 'True' "
			+ "ORDER by descripcion", nativeQuery = true)
	List<CausaBaja> findAllByActivo();
	
}
