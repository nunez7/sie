package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TemaGrupal;

public interface ITemaGrupalService {

	void guardar(TemaGrupal temaGrupal);
	
	void eliminar(TemaGrupal temaGrupal);
	
	List<TemaGrupal> buscarPorGrupo(Grupo grupo);
	
	TemaGrupal  bucarPorId(Integer id);
	
	List<TemaGrupal> buscarEntreFechasPorGrupo(Integer idGrupo, Date fechaInicio, Date fechaFin);
	
	List<TemaGrupal> buscarEntreFechasPorCarrera(Integer idCarrera, Integer idPeriodo, Date fechaInicio, Date fechaFin);
	
	Integer TotalPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
}
