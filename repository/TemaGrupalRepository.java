package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TemaGrupal;

public interface TemaGrupalRepository extends CrudRepository<TemaGrupal, Integer>{

	List<TemaGrupal> findByGrupo(Grupo grupo);
	
}
