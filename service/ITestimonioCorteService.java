package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.TestimonioCorte;

public interface ITestimonioCorteService {
	TestimonioCorte buscarPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria, Integer idCorteEvaluativo);
	void guardar (TestimonioCorte testimonioCorte);
	Integer contarAlumnoSD(Integer idCargaHoraria, Integer idCorteEvaluativo);
	Integer contarAlumnoSDPorCarreraYCorteEvaluativo(Integer idCarrera, Integer idCorteEvaluativo);
	Integer contarAlumnoSDPorCarrera(Integer idCarrera);
	Integer validarSDPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria, Integer idCorteEvaluativo);
	Integer countAlumnosSDByCarreraYTurno(Integer idCarrera, Integer idCorteEvaluativo, Integer idTurno);
}
