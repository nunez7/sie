package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.NotaCredito;
import edu.mx.utdelacosta.model.PagoGeneral;

public interface NotaCreditoRepository extends CrudRepository<NotaCredito, Integer> {
	
	NotaCredito findByPagoGeneral(PagoGeneral pagoGeneral);

}
