package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Baja;

public interface BajaRepository extends CrudRepository<Baja, Integer>{
	//busca si hay una baja solicitda por el alumno
	@Query(value = "SELECT * FROM bajas WHERE id_alumno= :idAlumno "
			+ "AND estatus = :estatus AND fecha_autorizacion IS NULL ORDER BY fecha_solicitud DESC", nativeQuery = true)
	Baja findByEstatusAndAlumnoAndFechaAutorizacion(@Param("estatus") Integer estatus, @Param("idAlumno") Integer idAlumno);
	//Extrae todas las solicitudes de baja de los alumnos pertenecientes
	//a los grupos de las carreras que tiene asociada el usuario.
	@Query(value = "SELECT DISTINCT ON (ag.id_alumno, b.id) b.* FROM bajas b "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=b.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN persona_carrera pc ON c.id=pc.id_carrera "
			+ "WHERE pc.id_persona =:idPersona AND b.estatus=:estatus "
			+ "AND b.id_periodo = :idPeriodo "
			+ "AND b.fecha_autorizacion IS NULL ORDER BY b.id DESC", nativeQuery = true)
	List<Baja> findByPersonaAndStatusAndFechaNull(@Param("idPersona") Integer idPersona, @Param("estatus") Integer estatus,
				@Param("idPeriodo") Integer idPeriodo);
	
	//bajas para scolares 
	@Query(value = "SELECT b.* FROM bajas b "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE bg.tipo=:tipo AND b.estatus=:estatus ORDER BY b.fecha_solicitud DESC", nativeQuery = true)
	List<Baja> findByTipoAndStatus(@Param("tipo") Integer tipo, @Param("estatus") Integer estatus);
	
	@Query(value = "SELECT b.* FROM bajas b "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=b.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE bg.tipo=:tipo AND b.estatus=:estatus AND ag.id_grupo=:idGrupo AND b.id_periodo =:idPeriodo ORDER BY b.fecha_solicitud DESC", nativeQuery = true)
	List<Baja> findByTipoAndStatusAndGrupoAndPeriodo(@Param("tipo") Integer tipo, @Param("estatus") Integer estatus, @Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT b.* FROM bajas b "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=b.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras c ON g.id_carrera = c.id "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE bg.tipo=:tipo AND b.estatus=:estatus AND c.id=:idCarrera AND b.id_periodo =:idPeriodo ORDER BY b.fecha_solicitud DESC", nativeQuery = true)
	List<Baja> findByTipoAndStatusAndCarreraAndPeriodo(@Param("tipo") Integer tipo, @Param("estatus") Integer estatus, @Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
	
	//todas las bajas por carrera
	@Query(value = "SELECT DISTINCT b.* FROM bajas b "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=b.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN carreras c ON  c.id=g.id_carrera "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE b.estatus=:estatus AND c.id=:idCarrera AND g.id_periodo=:idPeriodo "
			+ "AND b.fecha_registro BETWEEN :fechaInicio AND :fechaFin ORDER BY b.fecha_solicitud DESC", nativeQuery = true)
	List<Baja> findByTipoAndStatusAndCarreraAndFechas(@Param("estatus") Integer estatus, @Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	//todas las bajas por persona carrera 
	@Query(value = "SELECT DISTINCT b.* FROM bajas b "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=b.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo AND b.id_periodo = g.id_periodo "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN persona_carrera pc ON c.id=pc.id_carrera "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE b.estatus=:estatus AND pc.id_persona=:idPersona "
			+ "AND b.fecha_registro BETWEEN :fechaInicio AND :fechaFin ORDER BY b.fecha_solicitud DESC", nativeQuery = true)
	List<Baja> findByTipoAndStatusAndPersonaAndFechas(@Param("estatus") Integer estatus, @Param("idPersona") Integer idPersona, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	//todas las bajas general
	@Query(value = "SELECT b.* FROM bajas b "
			+ "INNER JOIN baja_autoriza bg ON b.id=bg.id_baja "
			+ "WHERE b.estatus=:estatus "
			+ "AND b.fecha_registro >=:fechaInicio AND b.fecha_registro <=:fechaFin ORDER BY b.fecha_solicitud DESC", nativeQuery = true)
	List<Baja> findByTipoAndStatusAndFechas(@Param("estatus") Integer estatus, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	List<Baja> findByAlumnoOrderByFechaRegistroDesc(Alumno alumno);
	
	//cuentas las bajas activas del alumno
	@Query(value = "SELECT count(*) FROM bajas WHERE id_alumno = :idAlumno AND estatus = 0 ", nativeQuery = true)
	Integer countByAlumnoActivas(@Param("idAlumno") Integer idAlumno);
	
	///busca las bajas por grupo
	@Query(value = "SELECT * FROM bajas WHERE id_grupo = :idGrupo ORDER BY fecha_solicitud DESC", nativeQuery = true)
	List<Baja> findByGrupo(@Param("idGrupo") Integer idGrupo);
	
}
