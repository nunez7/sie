package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CalificacionCorte;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.repository.CalificacionCorteRepository;
import edu.mx.utdelacosta.service.ICalificacionCorteService;

@Service
public class CalificacionCorteServiceJpa implements ICalificacionCorteService {

	@Autowired
	private CalificacionCorteRepository calificacionCorteRepository;

	@Override
	public List<CalificacionCorte> buscarPorCargaYAlumno(CargaHoraria cargaHoraria, Alumno alumno) {
		// TODO Auto-generated method stub
		return calificacionCorteRepository.findByCargaHorariaAndAlumnoOrderByCorteEvaluativo(cargaHoraria, alumno);
	}

	@Override
	public void guardar(CalificacionCorte calificacionCorte) {
		calificacionCorteRepository.save(calificacionCorte);

	}

	public Double buscarPorCargaHorariaIdCorteEvaluativoIdGrupo(Integer idCargaHoraria, Integer idCorteEvaluativo) {
		// TODO Auto-generated method stub
		return calificacionCorteRepository.findByIdCorteEvaluativoAndIdCargaHoraria(idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	public Double buscarPorAlumnoCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo) {
		// TODO Auto-generated method stub
		return calificacionCorteRepository.findByAlumnoAndCargaHorariaAndCorte(idAlumno, idCargaHoraria,
				idCorteEvaluativo);
	}

	@Override
	public CalificacionCorte buscarPorAlumnoYCargaHorariaYCorteEvaluativo(Alumno alumno, CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo) {
		return calificacionCorteRepository.findByAlumnoAndCargaHorariaAndCorteEvaluativo(alumno, cargaHoraria,
				corteEvaluativo);
	}

	@Override
	public List<CalificacionCorte> buscarPorCargaHorariaYCorteEvaluativo(CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo) {
		return calificacionCorteRepository.findByCargaHorariaAndCorteEvaluativo(cargaHoraria, corteEvaluativo);
	}
	
}
