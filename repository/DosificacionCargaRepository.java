package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionCarga;

public interface DosificacionCargaRepository extends CrudRepository<DosificacionCarga, Integer>{
	
	DosificacionCarga findByDosificacionAndCargaHoraria (Dosificacion dosificacion, CargaHoraria cargaHoraria);
	
	List<DosificacionCarga> findByCargaHoraria(CargaHoraria cargaHoraria);
	
}
