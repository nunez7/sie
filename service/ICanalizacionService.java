package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Canalizacion;

public interface ICanalizacionService {
	void guardar(Canalizacion canalizacion);
	List<Canalizacion> buscarPorGrupoPeriodoYAlumno(Integer idGrupo, Integer idPeriodo, Integer idAlumno);
	List<Canalizacion> buscarPorAlumno(Alumno alumno);
}
