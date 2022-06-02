package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.utdelacosta.model.AreaConocimiento;

public interface AreaConocimientoRepository extends JpaRepository<AreaConocimiento, Integer> {

	//trae todas las areas de conocimiento activas
	List<AreaConocimiento> findByActivo(boolean estatus);
}
