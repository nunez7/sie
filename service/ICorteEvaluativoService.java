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
	
	CorteEvaluativo buscarPorCorteYLimiteCaptura(Date fecha, CorteEvaluativo corte);
	
	CorteEvaluativo buscarPorFechaInicioYFechaFinYPeriodoYCarrera(Date fecha, Periodo periodo, Carrera carrera);
	
	CorteEvaluativo buscarPorCorteYFechaRemedial(CorteEvaluativo corte, Date fecha);
	
	CorteEvaluativo buscarPorCorteYFechaExtraordinario(CorteEvaluativo corte, Date fecha);
	
	CorteEvaluativo buscarPorCorteYFechaDosificacion(CorteEvaluativo corte, Date fecha);

	Integer contarPorFechaDosificacionYPeriodoYCarreraYCorteEvaluativo(Date fechaInicio, Integer idPeriodo, Integer idCarrera, Integer idCorte);
	
	Integer buscarPorCargaHorariaYCalendarioEvaluacion(Integer idCargaHoraria, Integer idCorteEvaluativo);
	
	Boolean buscarParcialConPlazoValido(Date fecha, Integer idCorte);
}
