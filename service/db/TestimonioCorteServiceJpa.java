package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.TestimonioCorte;
import edu.mx.utdelacosta.repository.TestimonioCorteRepository;
import edu.mx.utdelacosta.service.ITestimonioCorteService;

@Service
public class TestimonioCorteServiceJpa implements ITestimonioCorteService{

	@Autowired
	private TestimonioCorteRepository testimonioRepository;
	
	@Override
	@Transactional(readOnly = true)
	public TestimonioCorte buscarPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo) {
		return testimonioRepository.findByAlumnoAndCargaHorariaAndCorteEvaluativo(idAlumno, idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	public void guardar(TestimonioCorte testimonioCorte) {
		testimonioRepository.save(testimonioCorte);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnoSD(Integer idCargaHoraria, Integer idCorteEvaluativo) {
		return testimonioRepository.countAlumnosSD(idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnoSDPorCarreraYCorteEvaluativo(Integer idCarrera, Integer idCorteEvaluativo) {
		// TODO Auto-generated method stub
		return testimonioRepository.countAlumnosSDByCarreraAndCorte(idCarrera, idCorteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer validarSDPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo) {
		return testimonioRepository.checkSDByAlumnoAndCargaHorariaAndCorteEvaluativo(idAlumno, idCargaHoraria, idCorteEvaluativo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countAlumnosSDByCarreraYTurno(Integer idCarrera, Integer idCorteEvaluativo, Integer idTurno) {
		return testimonioRepository.countAlumnosSDByCarreraAndTurno(idCarrera, idCorteEvaluativo, idTurno);
	}

	@Override
	public Integer contarAlumnoSDPorCarrera(Integer idCarrera) {
		return testimonioRepository.countAlumnosSDByCarrera(idCarrera);
	}

}
