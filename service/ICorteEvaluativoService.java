package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Periodo;

public interface ICorteEvaluativoService {
	
	List<CorteEvaluativo> buscarPorPeridoYCarreraFechaInicioAsc(Periodo periodo, Carrera carrera);
	
	void guardar(CorteEvaluativo corteEvaluativo);
	
	List<CorteEvaluativo> buscarPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo);
	//busca el corte por carrera y periodo
	List<CorteEvaluativo> buscarPorCarreraYPeriodo(Carrera carrera, Periodo periodo);
	
	CorteEvaluativo buscarPorId(Integer id);
	
	List<CorteEvaluativo> buscarPorPeriodo(Periodo periodo);
	CorteEvaluativo buscarPorFechaAsistenciaMayorQueYPeriodo(Date fechaAsistencia, Periodo periodo);
	//CorteEvaluativo buscarPorFechaInicioMenorQueYFechaAsistenciaMayorQueYPeriodoYCarrera(Date fechaInicio, Date fechaAsistencia, Periodo periodo, Carrera carrera);
	CorteEvaluativo buscarPorFechaInicioMenorQueYFechaAsistenciaMayorQueYPeriodoYCarrera(Date fecha, Integer periodo, Integer carrera);
	CorteEvaluativo buscarPorFechaInicioMenorQueYFinEvaluacionesMayorQueYPeriodoYCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);
	CorteEvaluativo buscarPorInicioRemedialMenorQueYFinRemedialMayorQueYPeriodoYCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);
	CorteEvaluativo buscarPorInicioExtraordinarioMenorQueYFinExtraordinarioMayorQueYPeriodoYCarrera(Date fechaInicio, Date fechaFin, Periodo periodo, Carrera carrera);

	Integer contarPorFechaDosificacionYPeriodoYCarreraYCorteEvaluativo(Date fechaInicio, Integer idPeriodo, Integer idCarrera, Integer idCorte);
}
