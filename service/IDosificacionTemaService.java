package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionTema;
import edu.mx.utdelacosta.model.TemaUnidad;

public interface IDosificacionTemaService {
	DosificacionTema buscarPorTemaYDosificacion(TemaUnidad tema, Dosificacion dosificacion);
	void guardar (DosificacionTema dosificacionTema);
	List<DosificacionTema> buscarPorDosificacion(Dosificacion dosificacion);
	List<DosificacionTema> buscarPorUnidadTematicaYDosificacion(Integer unidadTematica, Integer dosificacion);
}
