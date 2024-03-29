package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Asesoria;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dtoreport.AsesoriaDTO;

public interface AsesoriaRepository extends CrudRepository<Asesoria, Integer>{
	

	@Query(value = "SELECT a.matricula as matricula, CONCAT(p.primer_apellido,' ',p.segundo_apellido,' ',p.nombre) as nombre, p.sexo, "
			+ " (SELECT COUNT(distinct(aa.*)) FROM asesoria_alumno aa "
			+ "  INNER JOIN asesorias ase on aa.id_asesoria = ase.id "
			+ "  WHERE aa.id_alumno = a.id and ase.id_tipo_asesoria = 1) as asesorias, "
			+ " (SELECT COUNT(distinct(aa.*)) FROM asesoria_alumno aa "
			+ "  INNER JOIN asesorias ase on aa.id_asesoria = ase.id "
			+ "  WHERE aa.id_alumno = a.id and ase.id_tipo_asesoria = 2) as asesoriasGrupales, ag.activo as Activo"
			+ " FROM alumnos a "
			+ " INNER JOIN alumnos_grupos ag on ag.id_alumno=a.id "
			+ " INNER JOIN personas p on p.id=a.id_persona "
			+ " INNER JOIN grupos g on g.id = ag.id_grupo "
			+ " WHERE ag.id_grupo=:idGrupo AND g.id_periodo = :idPeriodo "
			+ " ORDER BY TRANSLATE (p.primer_apellido,'������', 'AEIOUU') ASC, TRANSLATE (p.segundo_apellido,'������','AEIOUU') ASC, TRANSLATE (p.nombre,'������','AEIOUU') ASC", nativeQuery = true)
	List<AsesoriaDTO> findByIdGrupo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT COALESCE(COUNT(DISTINCT(aa.id)),0) as asesorias, al.matricula as matricula, CONCAT(p.primer_apellido,' ',p.segundo_apellido,' ',p.nombre) as nombre, "
			+ "p.sexo, g.nombre as grupo, al.estatus as estatus "
			+ "FROM asesorias a "
			+ "INNER JOIN asesoria_alumno aa on a.id=aa.id_asesoria "
			+ "INNER JOIN alumnos_grupos ag on ag.id_alumno=aa.id_alumno "
			+ "INNER JOIN alumnos al on al.id=ag.id_alumno "
			+ "INNER JOIN personas p on p.id=al.id_persona "
			+ "INNER JOIN grupos g on g.id = ag.id_grupo "
			+ "INNER JOIN carreras c on c.id = g.id_carrera "
			+ "WHERE c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona) AND g.id_periodo = :idPeriodo "
			+ "GROUP BY al.matricula, p.primer_apellido, p.segundo_apellido, p.nombre,p.sexo, g.nombre, al.estatus "
			+ "ORDER BY TRANSLATE (p.primer_apellido,'������','AEIOUU') ASC, TRANSLATE (p.segundo_apellido,'������','AEIOUU') ASC, TRANSLATE (p.nombre,'������','AEIOUU') ASC, asesorias ", nativeQuery = true)
	List<AsesoriaDTO> findByPersonaCarreraAndPeriodo(@Param("idPersona") Integer idPersona, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT DISTINCT(a.*) FROM asesorias a "
			+ "INNER JOIN cargas_horarias ca on ca.id=a.id_carga_horaria "
			+ "INNER JOIN grupos g ON g.id=ca.id_grupo "
			+ "WHERE g.id=:idGrupo AND g.id_periodo =:idPeriodo AND a.id_tipo_asesoria=:tipo", nativeQuery = true)
	List<Asesoria> findByGrupoAndPeriodoAndTipo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo, @Param("tipo") Integer tipo);
	
	@Query(value = "SELECT DISTINCT(a.*) FROM asesorias a "
			+ "INNER JOIN cargas_horarias ca on ca.id=a.id_carga_horaria "
			+ "INNER JOIN grupos g ON g.id=ca.id_grupo "
			+ "WHERE g.id=:idGrupo AND g.id_periodo =:idPeriodo AND a.id_carga_horaria=:idCarga AND a.id_tipo_asesoria=:tipo", nativeQuery = true)
	List<Asesoria> findByGrupoAndPeriodoAndCargaAndTipo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo, @Param("idCarga") Integer idCarga, @Param("tipo") Integer tipo);
	
	@Query(value = "SELECT g.nombre as grupo, m.nombre as materia, a.fecha_asesoria as fecha, a.tema as tema, a.comentario as comentario, "
			+ "	CONCAT(p.primer_apellido,' ', p.segundo_apellido,' ', p.nombre) as nombre, a2.matricula "
			+ "	FROM asesoria_alumno aa "
			+ "	INNER JOIN alumnos a2 on aa.id_alumno = a2.id "
			+ "	INNER JOIN personas p on a2.id_persona = p.id "
			+ "	INNER JOIN asesorias a on aa.id_asesoria = a.id "
			+ "	INNER JOIN cargas_horarias ch on a.id_carga_horaria = ch.id "
			+ "	INNER JOIN grupos g on ch.id_grupo = g.id "
			+ "	INNER JOIN materias m on ch.id_materia = m.id "
			+ "	WHERE ch.id_grupo = :grupo AND id_tipo_asesoria = 1  AND ch.id_profesor = :profesor"
			+ "	ORDER BY TRANSLATE (p.primer_apellido,'ÁÉÍÓÚÜ','AEIOUU') ASC, TRANSLATE (p.segundo_apellido,'ÁÉÍÓÚÜ','AEIOUU') ASC, TRANSLATE (p.nombre,'ÁÉÍÓÚÜ','AEIOUU') ASC ", nativeQuery = true)
	List<AsesoriaDTO> findIndividualesByGrupo(@Param("grupo")Grupo grupo, @Param("profesor") Persona persona);
	
	@Query(value = "SELECT g.nombre as grupo, m.nombre as materia, a.fecha_asesoria as fecha, a.tema, a.comentario "
			+ "	FROM asesorias a "
			+ "	INNER JOIN cargas_horarias ch on a.id_carga_horaria = ch.id "
			+ "	INNER JOIN grupos g on ch.id_grupo = g.id "
			+ "	INNER JOIN materias m on ch.id_materia = m.id "
			+ "	WHERE ch.id_grupo = :grupo AND id_tipo_asesoria = 2  AND ch.id_profesor = :profesor ", nativeQuery = true)
	List<AsesoriaDTO> findByGrupalesByGrupo(@Param("grupo") Grupo grupo, @Param("profesor") Persona persona);
}
