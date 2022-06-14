package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.PagoCuatrimestre;
import edu.mx.utdelacosta.model.PagoGeneral;

public interface PagoCuatrimestreRepository extends CrudRepository<PagoCuatrimestre, Integer>{
	
	PagoCuatrimestre findByPagoGeneral(PagoGeneral pagoGeneral);	
	
}
