package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.FocosAtencion;
import edu.mx.utdelacosta.model.Grupo;

public interface FocosAtencionRepository extends CrudRepository<FocosAtencion, Integer>{
	List<FocosAtencion> findByGrupoOrderByDescripcion(Grupo grupo);
}
