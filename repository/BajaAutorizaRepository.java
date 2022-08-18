package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.BajaAutoriza;

public interface BajaAutorizaRepository extends CrudRepository<BajaAutoriza, Integer>{
	
	BajaAutoriza findByBaja(Baja baja);

}
