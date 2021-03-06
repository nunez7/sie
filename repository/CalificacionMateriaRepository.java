package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CalificacionMateria;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;

public interface CalificacionMateriaRepository extends CrudRepository<CalificacionMateria, Integer> {
	CalificacionMateria findByCargaHorariaAndAlumno(CargaHoraria cargaHoraria, Alumno alumno);

	@Query(value = "SELECT m.id AS idMateria, m.nombre, m.abreviatura, COALESCE(cm.calificacion, 0)AS calificacion, "
			+ "COALESCE(cm.estatus, 'NA')AS estatus, m.integradora " + "FROM materias m "
			+ "INNER JOIN cargas_horarias ch ON ch.id_materia=m.id "
			+ "LEFT JOIN calificacion_materia cm ON cm.id_carga_horaria=ch.id "
			+ "WHERE ch.id_grupo=:idGrupo AND id_alumno=:idAlumno AND ch.activo='True'  "
			+ "ORDER BY m.nombre", nativeQuery = true)
	List<MateriaPromedioDTO> findByGrupoAlumno(@Param("idGrupo") Integer idGrupo, @Param("idAlumno") Integer idAlumno);

	@Query(value = "SELECT ca.* " + "FROM calificacion_materia ca "
			+ "INNER JOIN cargas_horarias ch on ch.id = ca.id_carga_horaria "
			+ "WHERE ca.id_alumno=:idAlumno AND ch.id_periodo=:idPeriodo", nativeQuery = true)
	List<CalificacionMateria> findByIdAlumnoAndIdPeriodo(@Param("idAlumno") Integer idAlumno,
			@Param("idPeriodo") Integer idPeriodo);

	@Query(value = "SELECT COALESCE(estatus, 'NA') as estatus "
			+ "FROM calificacion_materia WHERE id_carga_horaria = :cargaHoraria "
			+ "AND id_alumno = :alumno", nativeQuery = true)
	String findEstatusByAlumnoAndCargaHoraria(@Param("alumno") Integer idAlumno,
			@Param("cargaHoraria") Integer idCargaHoraria);

	@Query(value = "SELECT COALESCE(ROUND(SUM(calificacion),2), 0) FROM calificacion_materia "
			+ "WHERE id_carga_horaria=:idCargaHoraria and id_alumno=:idAlumno", nativeQuery = true)
	Float findCalificacionByAlumnoAndCargaHoraria(@Param("idCargaHoraria") Integer idAlumno,
			@Param("idAlumno") Integer idCargaHoraria);

	@Query(value = "SELECT ca.* " + "FROM calificacion_materia ca "
			+ "INNER JOIN cargas_horarias ch on ch.id = ca.id_carga_horaria "
			+ "WHERE ch.id_grupo=:idGrupo AND ch.id_periodo=:idPeriodo", nativeQuery = true)
	List<CalificacionMateria> findByIdGrupoAndIdPeriodo(@Param("idGrupo") Integer idGrupo,
			@Param("idPeriodo") Integer idPeriodo);

}
