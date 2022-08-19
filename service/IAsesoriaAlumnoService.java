package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.AsesoriaAlumno;

public interface IAsesoriaAlumnoService {
	void guardar (AsesoriaAlumno asesoriaAlumno);
	List<AsesoriaAlumno> buscarPorAlumnoPeriodoYTipo(Integer idAlumno, Integer idPeriodo, Integer tipo);
	List<AsesoriaAlumno> buscarPorGrupoAlumnoPeriodoYTipo(Integer idGrupo, Integer idAlumno, Integer idPeriodo, Integer tipo);
	List<AsesoriaAlumno> buscarPorGrupoPeriodoYTipo(Integer idGrupo, Integer idPeriodo, Integer tipo);
	List<AsesoriaAlumno> buscarPorGrupoAlumnoPeriodoCargaYTipo(Integer idGrupo, Integer idAlumno, Integer idPeriodo, Integer idCarga, Integer tipo);
	List<AsesoriaAlumno> buscarPorGrupoPeriodoCargaYTipo(Integer idGrupo, Integer idPeriodo, Integer idCarga, Integer tipo);
}
