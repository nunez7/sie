package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.FolioCeneval;

public interface IFolioCenevalService {
	void guardar(FolioCeneval folio);
	FolioCeneval buscarPorId(Integer id);
	FolioCeneval buscarPorAlumno(Alumno alumno);
	FolioCeneval buscarPorMatriculaAlumno(String matricula);
}
