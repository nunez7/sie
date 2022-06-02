package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.CalendarioEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.UnidadTematica;

public interface ICalendarioEvaluacionService {
	void guarda (CalendarioEvaluacion calendarioEvaluacion);
	
	CalendarioEvaluacion buscarPorId(Integer id);
	
	CalendarioEvaluacion buscarPorCargaHorariaYUnidadTematica(CargaHoraria carga, UnidadTematica unidad);
	
	List<CalendarioEvaluacion> buscarPorCargaHorariaYCorteEvaluativo(CargaHoraria carga, CorteEvaluativo corte);
	
	List<CalendarioEvaluacion> buscarPorCargaHoraria(CargaHoraria carga);
	
	Integer distinguirPorCorteEvaluativoPorCargaHoraria(Integer idCargaHoraria);
	
	Integer contarPorCargaHorariaYCorteEvaluativo(Integer idCarga, Integer idCorte);
}
