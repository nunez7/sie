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
	
	@Query(value = "SELECT * FROM cortes_evaluativos WHERE id_carrera = :idCarrera AND id_periodo = :idPeriodo "
			+ "ORDER BY consecutivo", nativeQuery = true)
	List<CorteEvaluativo> findByIdCarreraAndIdPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);

	//busca el Corte por carrera y periodo
	List<CorteEvaluativo> findByCarreraAndPeriodoOrderByConsecutivo(Carrera carrera, Periodo periodo);

	List<CorteEvaluativo> findByPeriodoOrderByIdAsc(Periodo periodo);
	
	List<CorteEvaluativo> findAll();
	
	@Query(value = "SELECT ce.* from cortes_evaluativos ce "
			+ "WHERE ce.id = :corte AND ce.limite_captura >= :fecha", nativeQuery = true)
	CorteEvaluativo findByCorteAndLimiteCaptura(@Param("fecha")Date fecha,@Param("corte") CorteEvaluativo corte);
	
	//se busca un corte en base a la fecha, periodo y carrera
	@Query(value = "SELECT ce.* "
			+ "FROM cortes_evaluativos ce "
			+ "WHERE ce.id_periodo = :periodo AND id_carrera = :carrera "
			+ "AND ce.fecha_inicio <= :fecha AND fecha_fin >= :fecha", nativeQuery = true)
	CorteEvaluativo findByFechaInicioAndFechaFinAndPeriodoAndCarrera(@Param("fecha")Date fecha,@Param("periodo") Periodo periodo,@Param("carrera") Carrera carrera);
	
	Optional<CorteEvaluativo> findById(Integer id);
	
	CorteEvaluativo findByFechaInicioLessThanEqualAndFinEvaluacionesGreaterThanEqualAndPeriodoAndCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);
	
	CorteEvaluativo findByInicioRemedialLessThanEqualAndFinRemedialGreaterThanEqualAndPeriodoAndCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);
	
	CorteEvaluativo findByInicioExtraordinarioLessThanEqualAndFinExtraordinarioGreaterThanEqualAndPeriodoAndCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);

	@Query(value = "SELECT COUNT(*) FROM cortes_evaluativos "
			+ "WHERE fecha_dosificacion >= :fechaDosificacion AND id_periodo = :idPeriodo "
			+ "AND id_carrera = :idCarrera AND id = :idCorteEvaluativo ", nativeQuery = true)
	Integer findByFechaDosificacionAndPeriodoAndCarreraAndCorteEvaluativo(@Param("fechaDosificacion") Date fechaDosificacion,@Param("idPeriodo") Integer idPeriodo,@Param("idCarrera") Integer idCarrrera, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
	
	//busca el corte evaluativo de una carga horaria que comparta el consecutivo con otro corte, se usa para el copiado de instrumentos desde profesor
	@Query(value = "SELECT distinct(ce.id) "
			+ "	FROM cortes_evaluativos ce "
			+ "	INNER JOIN calendario_evaluacion ce2 ON ce2.id_corte_evaluativo = ce.id "
			+ "	WHERE ce2.id_carga_horaria = :carga AND ce.consecutivo = (SELECT ce3.consecutivo "
			+ "		FROM cortes_evaluativos ce3 "
			+ "		WHERE ce3.id = :corte)", nativeQuery = true)
	Integer findByCargaHorariaAndCalendarioEvaluacion(@Param("carga") Integer idCargaHoraria, @Param("corte") Integer idCorteEvaluativo);

	
	@Query(value = "SELECT EXISTS(SELECT id "
			+ "				  FROM cortes_evaluativos ce "
			+ "				  WHERE ce.id = :idCorte "
			+ "				  AND ce.fecha_inicio <= :fecha AND ce.limite_captura >= :fecha)	", nativeQuery = true)
	Boolean findPlazoEntregaParcial(@Param("fecha") Date fecha, @Param("idCorte") Integer idCorte);
}
