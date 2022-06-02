package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionTema;
import edu.mx.utdelacosta.model.TemaUnidad;

public interface DosificacionesTemaRepository extends CrudRepository<DosificacionTema, Integer>{
	
	DosificacionTema findByTemaAndDosificacion(TemaUnidad tema, Dosificacion dosificacion);
	List<DosificacionTema> findByDosificacion(Dosificacion dosificacion);
}
