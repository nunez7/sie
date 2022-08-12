package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Baja;

public interface BajaRepository extends CrudRepository<Baja, Integer>{
	Baja findByEstatusAndAlumnoAndFechaAutorizacion(Integer estatus, Alumno alumno, Date fecha);
	//Extrae todas las solicitudes de baja de los alumnos pertenecientes
	//a los grupos de las carreras que tiene asociada el usuario.
	@Query(value = "SELECT DISTINCT ON (ag.id_alumno) b.* FROM bajas b "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=b.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN persona_carrera pc ON c.id=pc.id_carrera "
			+ "WHERE pc.id_persona =:idPersona AND b.estatus=:estatus AND b.fecha_autorizacion is null", nativeQuery = true)
	List<Baja> findByPersonaAndStatusAndFechaNull(@Param("idPersona") Integer idPersona, @Param("estatus") Integer estatus);
	
	//bajas para scolares 
	@Query(value = "SELECT b.* FROM bajas b "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE bg.tipo=:tipo AND b.estatus=:estatus", nativeQuery = true)
	List<Baja> findByTipoAndStatus(@Param("tipo") Integer tipo, @Param("estatus") Integer estatus);
	
	@Query(value = "SELECT b.* FROM bajas b "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=b.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE bg.tipo=:tipo AND b.estatus=:estatus AND ag.id_grupo=:idGrupo AND g.id_periodo =:idPeriodo", nativeQuery = true)
	List<Baja> findByTipoAndStatusAndGrupoAndPeriodo(@Param("tipo") Integer tipo, @Param("estatus") Integer estatus, @Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo);
	
	
	@Query(value = "SELECT b.* FROM bajas b "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=b.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras c ON g.id_carrera = c.id "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE bg.tipo=:tipo AND b.estatus=:estatus AND c.id=:idCarrera AND g.id_periodo =:idPeriodo", nativeQuery = true)
	List<Baja> findByTipoAndStatusAndCarreraAndPeriodo(@Param("tipo") Integer tipo, @Param("estatus") Integer estatus, @Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
}
