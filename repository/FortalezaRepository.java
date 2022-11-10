package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Fortaleza;
import edu.mx.utdelacosta.model.Grupo;

public interface FortalezaRepository extends CrudRepository<Fortaleza, Integer>{
	List<Fortaleza> findByGrupoOrderByFortaleza(Grupo grupo);
}
