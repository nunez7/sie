package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AsistenciaTemaGrupal;
import edu.mx.utdelacosta.model.TemaGrupal;
import edu.mx.utdelacosta.model.dto.AlumnoAsistenciaDTO;

public interface AsistenciaTemaGrupalRepository extends CrudRepository<AsistenciaTemaGrupal, Integer>{

	@Query(value = "SELECT a.id, "
			+ "CONCAT(p.nombre,' ',p.primer_apellido,' ',p.segundo_apellido) AS nombreCompleto, "
			+ "(SELECT atg.asistencia FROM asistencia_tema_grupal atg "
			+ "WHERE atg.id_alumno=a.id and atg.id_tema_grupal=:idTemaGrupal) as asistencia "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE ag.id_grupo=:idGrupo "
			+ "ORDER BY primer_apellido ASC, segundo_apellido ASC, nombre ASC", nativeQuery = true)
	List<AlumnoAsistenciaDTO> findByTemaGrupalAndGrupo(@Param("idGrupo") Integer idGrupo,@Param("idTemaGrupal") Integer idTemaGrupal);
	
	AsistenciaTemaGrupal findByTemaGrupalAndAlumno(TemaGrupal temaGrupal, Alumno alumno);
	
	List<AsistenciaTemaGrupal> findByTemaGrupal(TemaGrupal temaGrupal);
	
}
