package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.PagoAsignatura;
import edu.mx.utdelacosta.model.PagoGeneral;

public interface PagoAsignaturaRepository extends CrudRepository<PagoAsignatura, Integer>{
	
	//Optional<PagoAsignatura> findById (Integer id);
	
	PagoAsignatura findByPagoGeneral(PagoGeneral pago);

}
