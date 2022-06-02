package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.PrestamoDocumento;

public interface PrestamoDocumentoRepository extends CrudRepository<PrestamoDocumento, Integer>{
	
	@Query(value = "select pp.* "
			+ "FROM prestamo_documento pp "
			+ "INNER JOIN persona_documento pd ON pd.id = pp.id_persona_documento "
			+ "INNER JOIN alumnos a ON pd.id_persona = a.id_persona "
			+ "WHERE a.id=:idAlumno ORDER BY fecha_alta" ,nativeQuery = true)
	List<PrestamoDocumento> findByAlumonOrderByFechaAlta(@Param("idAlumno") Integer idAlumno);
}
