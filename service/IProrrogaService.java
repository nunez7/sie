package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.TipoProrroga;

public interface IProrrogaService {
	
	List<Prorroga> buscarPorCarreraYPendientes(Integer idPersona, Integer idPeriodo);
	
	Prorroga buscarPorId(Integer id);
	
	void guardar(Prorroga prorroga);
	
	List<Prorroga> buscarPorProfesorYPeriodoYActivo(Integer idProfesor, Integer idPeriodo);
	
	List<Prorroga> buscarPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo);
	
	Integer prorrogasPorProfesorAndPeriodo(Integer idProfesor, Integer idPeriodo);

	List<Prorroga> buscarPorCargaHoraria (CargaHoraria cargaHoraria);
	
	List<Prorroga> buscarPorPersonaCarrerraAndAceptadas(Integer idPersona, Integer idPeriodo);
	
	Prorroga buscarPorCargaHorariaYCorteEvaluativoYTipoProrrgaYActivo(CargaHoraria cargaHoraria, CorteEvaluativo corteEvaluativo, TipoProrroga tipoProrroga, boolean activo);
	
	Prorroga buscarPorCargaHorariaYTipoProrrogaYFechaLimiteMayorQueYActivoYAceptada(CargaHoraria cargaHoraria, TipoProrroga tipoProrroga, Date fechaLimite, Boolean activo, Boolean aceptada);
	
	Prorroga buscarPorCargaHorariaYTipoProrrogaYActivoYAceptada(CargaHoraria cargaHoraria, TipoProrroga tipoProrroga, boolean activo, boolean aceptada);
	
	Prorroga buscarPorCargaHorariaYTipoProrrogaYCorteEvaluativoYActivoYAceptada(CargaHoraria cargaHoraria, TipoProrroga tipoProrroga, CorteEvaluativo corteEvaluativo, boolean activo, boolean aceptada);
	
	Integer contarProrrogasPendientesPorPersonaYPeriodo(Integer idPersona, Integer idPeriodo);
}
