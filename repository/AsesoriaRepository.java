package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Asesoria;
import edu.mx.utdelacosta.model.dto.AsesoriaDTO;

public interface AsesoriaRepository extends CrudRepository<Asesoria, Integer>{
	

	@Query(value = "SELECT  COALESCE(COUNT(a.*),0) as asesorias, al.matricula as matricula, CONCAT(p.primer_apellido,' ',p.segundo_apellido,' ',p.nombre) as nombre, p.sexo "
			+ "FROM asesorias a "
			+ "INNER JOIN asesoria_alumno aa on a.id=aa.id_asesoria "
			+ "LEFT JOIN alumnos_grupos ag on ag.id_alumno=aa.id_alumno "
			+ "LEFT JOIN alumnos al on al.id=ag.id_alumno "
			+ "LEFT JOIN personas p on p.id=al.id_persona "
			+ "WHERE ag.id_grupo=:idGrupo "
			+ "GROUP BY al.matricula, p.primer_apellido, p.segundo_apellido, p.nombre,p.sexo "
			+ "ORDER BY p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AsesoriaDTO> findByIdGrupo(@Param("idGrupo") Integer idGrupo);

}
