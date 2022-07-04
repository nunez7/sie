package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dtoreport.DosificacionPendienteDTO;

public interface IDosificacionService {
	List<Dosificacion> buscarPorPersona(Persona persona); 
	Dosificacion buscarPorIdCargaHorariaEIdCorteEvaluativo(Integer idCargaHoraria, Integer idCorteEvaluativo);
	void guardar(Dosificacion dosificacion);
	List<Dosificacion> buscarPorCargaHoraria(Integer cargaHoraria);
	Dosificacion buscarPorId(Integer id);
	List<DosificacionPendienteDTO> obtenerPendientesPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo);
	List<Dosificacion> buscarPorIdCargaHoraria(Integer idCargaHoraria);
	Dosificacion buscarPorIdMateriaEIdPersona(Integer idMateria, Integer IdPersona);
}
