package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Canalizacion;

public interface ICanalizacionService {
	void guardar(Canalizacion canalizacion);
	List<Canalizacion> buscarPorAlumno(Alumno alumno);
	List<Canalizacion> buscarPorGrupoPeriodoYAlumno(Integer idGrupo, Integer idPeriodo, Integer idAlumno);
	List<Canalizacion> buscarPorGrupoPeriodo(Integer idGrupo, Integer idPeriodo);
	List<Canalizacion> buscarPorCarreraPeriodo(Integer idCarrera, Integer idPeriodo);
	
	Integer contarPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
	Integer contarDistinctAlumnoPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
}
