package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.DosificacionImportada;
import edu.mx.utdelacosta.model.dto.DosificacionImportarDTO;

public interface DosificacionImportadaRepository extends CrudRepository<DosificacionImportada, Integer>{
	
	@Query(value ="SELECT DISTINCT(d.id_persona) AS persona "
			+ "	, MAX(CONCAT(p.nombre, ' ', p.primer_apellido, ' ',p.segundo_apellido)) AS nombre ,MAX(ch.id) AS carga "
			+ "	FROM dosificaciones d "
			+ "	INNER JOIN dosificaciones_cargas dc ON dc.id_dosificacion = d.id "
			+ "	INNER JOIN cargas_horarias ch ON  dc.id_carga_horaria = ch.id "
			+ "	INNER JOIN personas p ON ch.id_profesor = p.id "
			+ "	WHERE ch.id_materia = :materia AND ch.id <> :carga AND ch.id_periodo = :periodo "
			+ "	GROUP BY d.id_persona ", nativeQuery = true)
	List<DosificacionImportarDTO> findImportByMateriaAndPeriodo(@Param("materia") Integer idMateria, @Param("carga") Integer idCarga, @Param("periodo") Integer idPeriodo);
}
