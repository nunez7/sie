package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoGeneral;

public interface PagosAlumnoRepository extends CrudRepository<PagoAlumno, Integer>{

	PagoAlumno findByPagoGeneral (PagoGeneral pagoGeneral);
	
}
