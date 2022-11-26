package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CalificacionCorte;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;

public interface ICalificacionCorteService {
	List<CalificacionCorte> buscarPorCargaYAlumno(CargaHoraria cargaHoraria, Alumno alumno);

	void guardar(CalificacionCorte calificacionCorte);

	CalificacionCorte buscarPorAlumnoYCargaHorariaYCorteEvaluativo(Alumno alumno, CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo);

	List<CalificacionCorte> buscarPorCargaHorariaYCorteEvaluativo(CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo);

	Double buscarPorCargaHorariaIdCorteEvaluativoIdGrupo(Integer idCargaHoraria, Integer idCorteEvaluativo);

	// busca la calificacion por alumno, cargaHoraria y corte
	Double buscarPorAlumnoCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo);

	Integer buscarRevalidadaPorAlumnoYCargaHorariaYCorteEvaluativo(Integer alumno, Integer cargaHoraria, Integer corteEvaluativo);
	
	Float buscarPromedioCortePorMecanismoIntrumentoYCarga(Integer cargaHoraria, Integer corteEvaluativo, Integer alumno);
	
	Integer contarPorIdCargaHorariaYidCorte(Integer idCargaHoraria, Integer idCorte);

	Integer contarCalificacionesPorCargaHorariaYCorteEvaluativo(CargaHoraria carga, CorteEvaluativo corte);

}
