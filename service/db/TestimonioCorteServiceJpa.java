package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.TestimonioCorte;
import edu.mx.utdelacosta.repository.TestimonioCorteRepository;
import edu.mx.utdelacosta.service.ITestimonioCorteService;

@Service
public class TestimonioCorteServiceJpa implements ITestimonioCorteService{

	@Autowired
	private TestimonioCorteRepository testimonioRepository;
	
	@Override
	public TestimonioCorte buscarPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo) {
		return testimonioRepository.findByAlumnoAndCargaHorariaAndCorteEvaluativo(idAlumno, idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	public void guardar(TestimonioCorte testimonioCorte) {
		testimonioRepository.save(testimonioCorte);
	}
	
	@Override
	public Integer contarAlumnoSD(Integer idCargaHoraria, Integer idCorteEvaluativo) {
		return testimonioRepository.countAlumnosSD(idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	public Integer countAlumnosSDByCarrera(Integer idCarrera, Integer idCorteEvaluativo) {
		// TODO Auto-generated method stub
		return testimonioRepository.countAlumnosSDByCarrera(idCarrera, idCorteEvaluativo);
	}

	@Override
	public Integer validarSDPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo) {
		return testimonioRepository.checkSDByAlumnoAndCargaHorariaAndCorteEvaluativo(idAlumno, idCargaHoraria, idCorteEvaluativo);
	}

}
