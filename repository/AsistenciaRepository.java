package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Asistencia;
import edu.mx.utdelacosta.model.Horario;

public interface AsistenciaRepository extends CrudRepository<Asistencia, Integer> {
	// Lista de meses
	@Query(value = "SELECT CAST(MM AS date) AS fecha "
			+ "FROM generate_series(CAST(:fechaInicio AS date),CAST(:fechaFin AS date),CAST('1 month' AS interval)) "
			+ "AS dia(MM) ORDER BY fecha ASC ", nativeQuery = true)
	List<Date> findAllByFechaInicioFechafinOrderByFechaAsc(@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin);

	// Lista de dias
	@Query(value = "SELECT CAST(dd AS date) AS fecha "
			+ "FROM generate_series(CAST(:fechaInicio AS date),CAST(:fechaFin AS date),CAST('1 day' AS interval))AS dia(dd) "
			+ "WHERE CAST(TO_CHAR(dia.dd, 'd') AS INT) NOT IN (7, 1) " + "LIMIT 200 ", nativeQuery = true)
	List<Date> findAllByFechaInicioFechafin(@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin);

	// Lista de asistencias de las cargas horarias asociadas al grupo y al alumno
	@Query(value = "SELECT asi.* FROM asistencias asi " + "INNER JOIN horarios hr ON hr.id=asi.id_horario "
			+ "INNER JOIN cargas_horarias cr ON cr.id=hr.id_carga_horaria "
			+ "WHERE cr.id_grupo=:idGrupo AND asi.id_alumno =:idAlumno AND hr.activo=true ", nativeQuery = true)
	List<Asistencia> findAllByGrupoAndAlumno(@Param("idGrupo") Integer idGrupo, @Param("idAlumno") Integer idAlumno);

	@Query(value = "SELECT * FROM asistencias a " + "INNER JOIN horarios h on h.id=a.id_horario "
			+ "INNER JOIN cargas_horarias ch on ch.id=h.id_carga_horaria "
			+ "WHERE fecha >= :fechaInicio and fecha<= :fechaFin and h.id_carga_horaria=:idCargaHoraria and ch.id_grupo= :idGrupo", nativeQuery = true)
	List<Asistencia> findByFechaInicioAndFechaFinAndIdCargaHorariaAndIdGrupo(@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin, @Param("idCargaHoraria") Integer idCargaHoraria,
			@Param("idGrupo") Integer idGrupo);

	List<Asistencia> findAllByHorario(Horario horario);

	@Query(value = "SELECT COUNT(DISTINCT( a.fecha,a.id_horario))  " + "from asistencias a "
			+ "INNER JOIN horarios h on a.id_horario=h.id "
			+ "INNER JOIN cargas_horarias cg on h.id_carga_horaria=cg.id "
			+ "WHERE a.fecha>= :fechaInicio and a.fecha <= :fechaFin and cg.id=:idCargaHoraria ", nativeQuery = true)
	Integer countByFechaInicioAndFechaFindAndCargaHoraria(@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin, @Param("idCargaHoraria") Integer idCarga);

	List<Asistencia> findByFechaAndHorario(Date fecha, Horario horario);

	Asistencia findByFechaAndAlumnoAndHorario(Date fecha, Alumno alumno, Horario horario);

	Optional<Asistencia> findById(Integer id);

	@Query(value = "SELECT a.*  " + "from asistencias a " + "INNER JOIN horarios h on a.id_horario=h.id "
			+ "INNER JOIN cargas_horarias cg on h.id_carga_horaria=cg.id "
			+ "WHERE a.valor='F' and a.id_alumno= :idAlumno and cg.id=:idCargaHoraria AND a.fecha>=:inicio AND a.fecha<=:fin ", nativeQuery = true)
	List<Asistencia> findFaltasByAlumnoAndCargaHoraria(@Param("idAlumno") Integer idAlumno,
			@Param("idCargaHoraria") Integer idCargaHoraria, @Param("inicio") Date fechaInicio,
			@Param("fin") Date fechaAsistencias);

	@Query(value = "SELECT a.*  " + "from asistencias a " + "INNER JOIN horarios h on a.id_horario=h.id "
			+ "INNER JOIN cargas_horarias cg on h.id_carga_horaria=cg.id "
			+ "WHERE a.valor='R' and a.id_alumno=:idAlumno and cg.id=:idCargaHoraria ", nativeQuery = true)
	List<Asistencia> findRetardosByAlumnoAndCargaHoraria(@Param("idAlumno") Integer idAlumno,
			@Param("idCargaHoraria") Integer idCargaHoraria);
	
	//cuenta el numero de asistencias por alumno, materia y corte(fecha de inicio y fecha de fin)
	@Query(value = "SELECT COUNT(a.*) as asistencias FROM asistencias a "
			+ "INNER JOIN horarios h ON h.id = a.id_horario "
			+ "WHERE h.id_carga_horaria = :idCargaHoraria "
			+ "AND a.fecha BETWEEN :fechaInicio and :fechaFin "
			+ "AND a.valor = 'A' OR a.valor = 'J' OR a.valor = 'R' "
			+ "AND id_alumno = :idAlumno", nativeQuery = true)
	Integer countAsistenciasByAlumnoAndCargaHorariaAndCorteEvalutivo(@Param("idAlumno") Integer idAlumno, @Param("idCargaHoraria") Integer idCargaHoraria,
				@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

	//cuenta el numero de faltas por alumno, materia y corte (fecha inicio y fecha fin) 
	@Query(value = "SELECT COUNT(a.*) as asistencias FROM asistencias a "
			+ "INNER JOIN horarios h ON h.id = a.id_horario "
			+ "WHERE h.id_carga_horaria = :idCargaHoraria "
			+ "AND a.fecha BETWEEN :fechaInicio and :fechaFin "
			+ "AND a.valor = 'F' AND id_alumno = :idAlumno", nativeQuery = true)
	Integer countFaltasByAlumnoAndCargaHorariaAndCorteEvalutivo(@Param("idAlumno") Integer idAlumno, @Param("idCargaHoraria") Integer idCargaHoraria,
				@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

}
