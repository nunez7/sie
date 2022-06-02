package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.TestimonioCorte;

public interface ITestimonioCorteService {
	TestimonioCorte buscarPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria, Integer idCorteEvaluativo);
	void guardar (TestimonioCorte testimonioCorte);
	Integer contarAlumnoSD(Integer idCargaHoraria, Integer idCorteEvaluativo);
	Integer countAlumnosSDByCarrera(Integer idCarrera, Integer idCorteEvaluativo);
}
