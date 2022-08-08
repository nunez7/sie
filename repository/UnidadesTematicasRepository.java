package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.UnidadTematica;

public interface UnidadesTematicasRepository extends JpaRepository<UnidadTematica, Integer>{
	Optional<UnidadTematica> findById(Integer id);
	
	@Query(value = "SELECT distinct(ut.*)  "
			+ "FROM unidades_tematicas ut "
			+ "INNER JOIN temas_unidad tu on tu.id_unidad_tematica=ut.id "
			+ "INNER JOIN dosificacion_tema dt on dt.id_tema = tu.id "
			+ "WHERE dt.id_dosificacion=:dosificacion" , nativeQuery = true)
	List<UnidadTematica> findByDosificacion(@Param("dosificacion") Integer idDosificacion);
}
