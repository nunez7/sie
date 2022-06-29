package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Periodo;

public interface CorteEvaluativoRepository extends JpaRepository<CorteEvaluativo, Integer>{
	List<CorteEvaluativo> findByPeriodoAndCarreraOrderByFechaInicioAsc(Periodo periodo, Carrera carrera);
	
	@Query(value = "SELECT * FROM cortes_evaluativos WHERE id_carrera = :idCarrera AND id_periodo = :idPeriodo", nativeQuery = true)
	List<CorteEvaluativo> findByIdCarreraAndIdPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);

	//busca el Corte por carrera y periodo
	List<CorteEvaluativo> findByCarreraAndPeriodoOrderByConsecutivo(Carrera carrera, Periodo periodo);

	List<CorteEvaluativo> findByPeriodoOrderByIdAsc(Periodo periodo);
	
	List<CorteEvaluativo> findAll();
	
	CorteEvaluativo findByFechaAsistenciaGreaterThanEqualAndPeriodo(Date fechaAsistencia, Periodo periodo);
	
	@Query(value = "SELECT ce.* from cortes_evaluativos ce "
			+ "WHERE id_periodo = :idPeriodo and fecha_inicio <= :fecha and fecha_asistencia >= :fecha "
			+ "AND id_carrera = :idCarrera ", nativeQuery = true)
	CorteEvaluativo findByFechaInicioLessThanEqualAndFechaAsistenciaGreaterThanEqualAndPeriodoAndCarrera(@Param("fecha")Date fecha,@Param("idPeriodo") Integer periodo,@Param("idCarrera") Integer carrera);
	
	Optional<CorteEvaluativo> findById(Integer id);
	
	CorteEvaluativo findByFechaInicioLessThanEqualAndFinEvaluacionesGreaterThanEqualAndPeriodoAndCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);
	
	CorteEvaluativo findByInicioRemedialLessThanEqualAndFinRemedialGreaterThanEqualAndPeriodoAndCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);
	
	CorteEvaluativo findByInicioExtraordinarioLessThanEqualAndFinExtraordinarioGreaterThanEqualAndPeriodoAndCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);

	@Query(value = "SELECT COUNT(*) FROM cortes_evaluativos "
			+ "WHERE fecha_dosificacion >= :fechaDosificacion AND id_periodo = :idPeriodo "
			+ "AND id_carrera = :idCarrera AND id = :idCorteEvaluativo ", nativeQuery = true)
	Integer findByFechaDosificacionAndPeriodoAndCarreraAndCorteEvaluativo(@Param("fechaDosificacion") Date fechaDosificacion,@Param("idPeriodo") Integer idPeriodo,@Param("idCarrera") Integer idCarrrera, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);

}