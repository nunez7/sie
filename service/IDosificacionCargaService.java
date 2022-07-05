package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionCarga;

public interface IDosificacionCargaService {
	void guardar(DosificacionCarga dosificacionCarga);
	
	DosificacionCarga buscarPorDosificacionYCargaHoraria(Dosificacion dosificacion, CargaHoraria cargaHoraria);
	
	List<DosificacionCarga> buscarPorCargaHoraria(CargaHoraria cargaHoraria);
	
	Integer contarNoEntregadas(Integer idProfesor, Integer idPeriodo);
}
