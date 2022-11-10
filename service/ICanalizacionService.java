package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Canalizacion;
import edu.mx.utdelacosta.model.TutoriaIndividual;

public interface ICanalizacionService {
	void guardar(Canalizacion canalizacion);
	List<Canalizacion> buscarPorAlumno(Alumno alumno);
	Canalizacion buscarPorTutoria(TutoriaIndividual tutoria);
	List<Canalizacion> buscarPorGrupoPeriodoYAlumno(Integer idGrupo, Integer idPeriodo, Integer idAlumno);
	List<Canalizacion> buscarPorGrupoPeriodo(Integer idGrupo, Integer idPeriodo);
	List<Canalizacion> buscarPorCarreraPeriodo(Integer idCarrera, Integer idPeriodo);
	
	Integer contarPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
	Integer contarDistinctAlumnoPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
	
	List<Canalizacion> buscarPorGrupoPeriodoAlumnoYServicio(Integer idGrupo, Integer idPeriodo, Integer idAlumno, Integer idServicio);
	List<Canalizacion> buscarPorGrupoPeriodoYServicio(Integer idGrupo, Integer idPeriodo, Integer idServicio);
	List<Canalizacion> buscarPorCarreraPeriodoYServicio(Integer idCarrera, Integer idPeriodo, Integer idServicio);
}
