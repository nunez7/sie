package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Prorroga;

public interface IProrrogaService {
	
	List<Prorroga> buscarPorCarreraYPendientes(Integer idPersona);
	
	Prorroga buscarPorId(Integer id);
	
	void guardar(Prorroga prorroga);
	
	List<Prorroga> buscarPorProfesorYPeriodoYActivo(Integer idProfesor, Integer idPeriodo);
	
	List<Prorroga> buscarPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo);
	
	Integer prorrogasPorProfesorAndPeriodo(Integer idProfesor, Integer idPeriodo);

	List<Prorroga> buscarPorCargaHoraria (CargaHoraria cargaHoraria);
	
	List<Prorroga> buscarPorIdProfesor (Integer idProfesor);
	
	Prorroga buscarPorCargaHorariaYCorteEvaluativoEIdTipoProrrgaYActivo(CargaHoraria cargaHoraria, CorteEvaluativo corteEvaluativo, Integer idTipoProrroga, boolean activo);
	
	Prorroga buscarPorCargaHorariaIdTipoProrrogaYFechaLimiteMayorQueYActivoYAceptada(CargaHoraria cargaHoraria, Integer idTipoProrroga, Date fechaLimite, Boolean activo, Boolean aceptada);
	
	Prorroga buscarPorCargaHorariaEIdTipoProrrogaYActivoYAceptada(CargaHoraria cargaHoraria, Integer idTipo, boolean activo, boolean aceptada);
	
}
