package edu.mx.utdelacosta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.utdelacosta.model.UnidadTematica;

public interface UnidadesTematicasRepository extends JpaRepository<UnidadTematica, Integer>{
	Optional<UnidadTematica> findById(Integer id);
}
