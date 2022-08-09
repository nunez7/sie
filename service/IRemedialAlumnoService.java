package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;

public interface IRemedialAlumnoService {
	List<RemedialAlumno> buscarPorGrupoYAlumno(Integer idGrupo, Integer idAlumno);
	
	List<RemedialAlumno> buscarPorAlumnoCargaYCorte(Alumno alumno, CargaHoraria cargaHoraria, CorteEvaluativo cortesEvaluativo);
	
	RemedialAlumno buscarPorId(Integer id);
	
	void guardar(RemedialAlumno remedialAlumno);
	
	void eliminar(RemedialAlumno remedialAlumno);
	
	List<RemedialAlumno> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo);
	
	RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedial(Alumno alumno, CargaHoraria cargaHoraria, Remedial remedial);
	
	RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYPagado(Alumno alumno, CargaHoraria cargaHoraria, Remedial remedial);
	
	RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYCorte(Alumno alumno, CargaHoraria cargaHoraria, Remedial remedial, CorteEvaluativo corteEvaluativo);
	
	RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYCorteYPagado(Alumno alumno, CargaHoraria cargaHoraria, Remedial remedial, CorteEvaluativo corteEvaluativo);
	
	Integer contarRemedialesAlumno(Integer idCargaHoraria, Integer idTipoRemedial);

	List<RemedialAlumno> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo, Integer tipo);
	
	Integer contarRemedialesAlumnoPorCargaHorariaYRemedialYCorteEvaluativo(Integer idCargaHoraria, Integer idTipoRemedial, Integer idCorteEvaluativo);
	
	RemedialAlumno buscarUltimoPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCarga, Integer idCorte);
	
	Integer countByCarreraAndCorteEvaluativo(Integer idCarrera, Integer tipoRemedial, Integer idCorteEvaluativo);
	
	Integer buscarCalificacionPorAlumnoYCargaHorariaYCorteEvaluativoYTipo(Integer idAlumno, Integer idCargaHoraria, Integer idCorte, Integer tipo);
	
	Integer contarPorCarreraYCorteEvaluativoYTurno(Integer idCarrera, Integer tipoRemedial, Integer idCorteEvaluativo, Integer turno);
}
