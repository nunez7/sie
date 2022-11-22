package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Asesoria;
import edu.mx.utdelacosta.model.AsesoriaSolicitud;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.dtoreport.AsesoriaDTO;

public interface IAsesoriaService {
	void guardar (Asesoria asesoria);
	List<AsesoriaDTO> buscarPorIdGrupoYPeriodo(Integer idGrupo, Integer idPeriodo);
	List<AsesoriaDTO> buscarPorPersonaCarreraAndPeriodo(Integer idPersona, Integer idPeriodo);
	List<Asesoria> buscarPorGrupoPeriodoYTipo(Integer idGrupo, Integer idPeriodo, Integer tipo);
	List<Asesoria> buscarPorGrupoPeriodoCargaYTipo(Integer idGrupo, Integer idPeriodo, Integer idCarga, Integer tipo);
	void guardarAsesoriaSolicitud (AsesoriaSolicitud asesoria);
	List<AsesoriaSolicitud> buscarAsesoriasSolicitudPorGrupo(Integer idGrupo);
	List<AsesoriaDTO> buscarIndividualesPorGrupo(Grupo grupo);
	List<AsesoriaDTO> buscarGrupalesPorGrupo(Grupo grupo);
}
