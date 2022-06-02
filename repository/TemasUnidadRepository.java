package edu.mx.utdelacosta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.utdelacosta.model.TemaUnidad;

public interface TemasUnidadRepository extends JpaRepository<TemaUnidad, Integer>{
	Optional<TemaUnidad> findById(Integer id);
}
