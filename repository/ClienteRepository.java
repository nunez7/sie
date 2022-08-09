package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	//para extraer la ultima clave generada
	@Query(value = "SELECT CONCAT('C',LPAD(CAST(COUNT(clave)+1 AS VARCHAR),5,'0')) AS cadena "
			+ "FROM clientes", nativeQuery = true)
	String findLastClave();
	List<Cliente> findAllByOrderByIdDesc();
	//busca clientes por nombre o rfc
	@Query(value = "SELECT * FROM clientes WHERE nombre_cliente ILIKE %:like% "
			+ "OR rfc ILIKE %:like%", nativeQuery = true)
	List<Cliente> findByNombreOrRfc(@Param("like") String like);
	

}
