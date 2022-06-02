package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Submodulo;

public interface SubmodulosRepository extends CrudRepository<Submodulo, Integer>{
	List<Submodulo> findByModuloPadreAndActivoOrderByConsecutivoAsc(int modulo, boolean activo);
	
	Submodulo findByModuloPadreAndCveSubmodulo(Integer modulo, Integer submodulo);
}
