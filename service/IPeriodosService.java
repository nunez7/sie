package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Periodo;

public interface IPeriodosService {
	List<Periodo> buscarTodos();
	Periodo buscarPorId(Integer idPeriodo);
	void guardar(Periodo periodo);
	List<Date> buscarDiasPorFechaInicioYFechafin(String fechaInicio, String fechaFin);
	Periodo buscarUltimo();
	Periodo buscarPorFechaInicioYFechafin(Date inicio, Date fin);
}