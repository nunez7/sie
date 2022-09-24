package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public List<CalificacionCorte> buscarPorCargaYAlumno(CargaHoraria cargaHoraria, Alumno alumno) {
		// TODO Auto-generated method stub
		return calificacionCorteRepository.findByCargaHorariaAndAlumnoOrderByCorteEvaluativo(cargaHoraria, alumno);
	}

	@Override
	public void guardar(CalificacionCorte calificacionCorte) {
		calificacionCorteRepository.save(calificacionCorte);

	}
	
	@Override
	@Transactional(readOnly = true)
	public Double buscarPorCargaHorariaIdCorteEvaluativoIdGrupo(Integer idCargaHoraria, Integer idCorteEvaluativo) {
		// TODO Auto-generated method stub
		return calificacionCorteRepository.findByIdCorteEvaluativoAndIdCargaHoraria(idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public Double buscarPorAlumnoCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo) {
		// TODO Auto-generated method stub
		return calificacionCorteRepository.findByAlumnoAndCargaHorariaAndCorte(idAlumno, idCargaHoraria,
				idCorteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public CalificacionCorte buscarPorAlumnoYCargaHorariaYCorteEvaluativo(Alumno alumno, CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo) {
		return calificacionCorteRepository.findByAlumnoAndCargaHorariaAndCorteEvaluativo(alumno, cargaHoraria,
				corteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CalificacionCorte> buscarPorCargaHorariaYCorteEvaluativo(CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo) {
		return calificacionCorteRepository.findByCargaHorariaAndCorteEvaluativo(cargaHoraria, corteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer buscarRevalidadaPorAlumnoYCargaHorariaYCorteEvaluativo(Integer alumno, Integer cargaHoraria,
			Integer corteEvaluativo) {
		return calificacionCorteRepository.findRevalidadaByAlumnoAndCargaHorariaAndCorteEvaluativo(alumno, cargaHoraria, corteEvaluativo);
	}
	
}
