package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.DosificacionComentario;

public interface DosificacionesComentariosRepository extends CrudRepository<DosificacionComentario, Integer> {
	List<DosificacionComentario> findByIdPersona(Integer idPersona);
	List<DosificacionComentario> findByIdCargaHoraria(Integer idCargaHoraria);
	
	@Query(value = "SELECT COUNT(*) "
			+ "FROM dosificaciones_comentarios dc "
			+ "INNER JOIN cargas_horarias ch ON dc.id_carga_horaria=ch.id "
			+ "WHERE ch.id_profesor = :idProfesor AND ch.id_periodo = :idPeriodo ", nativeQuery = true)	
	Integer countByProfesorAndPeriodo(@Param("idPeriodo") Integer idPeriodo, @Param("idProfesor") Integer idProfesor);
}
