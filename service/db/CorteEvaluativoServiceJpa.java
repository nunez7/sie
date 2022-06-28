package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.repository.CorteEvaluativoRepository;
import edu.mx.utdelacosta.service.ICorteEvaluativoService;

@Service
public class CorteEvaluativoServiceJpa implements ICorteEvaluativoService{
	
	@Autowired
	private CorteEvaluativoRepository corteEvaluativoRepository;
	
	@Override
	public List<CorteEvaluativo> buscarPorPeridoYCarreraFechaInicioAsc(Periodo periodo, Carrera carrera) {
		// TODO Auto-generated method stub
		return corteEvaluativoRepository.findByPeriodoAndCarreraOrderByFechaInicioAsc(periodo, carrera);
	}

	@Override
	public void guardar(CorteEvaluativo corteEvaluativo) {
		// TODO Auto-generated method stub
		corteEvaluativoRepository.save(corteEvaluativo);
	}

	@Override
	public List<CorteEvaluativo> buscarPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return corteEvaluativoRepository.findByIdCarreraAndIdPeriodo(idCarrera, idPeriodo);
	}

	@Override
	public List<CorteEvaluativo> buscarPorCarreraYPeriodo(Carrera carrera, Periodo periodo) {
		// TODO Auto-generated method stub
		return corteEvaluativoRepository.findByCarreraAndPeriodoOrderByConsecutivo(carrera, periodo);
	}

	@Override
	public CorteEvaluativo buscarPorId(Integer id) {
		Optional<CorteEvaluativo> optional = corteEvaluativoRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	public List<CorteEvaluativo> buscarPorPeriodo(Periodo periodo) {
		return corteEvaluativoRepository.findByPeriodoOrderByIdAsc(periodo);
	}

	@Override
	public CorteEvaluativo buscarPorFechaAsistenciaMayorQueYPeriodo(Date fechaAsistencia, Periodo periodo) {
		return corteEvaluativoRepository.findByFechaAsistenciaGreaterThanEqualAndPeriodo(fechaAsistencia, periodo);
	}

	/*
	@Override
	public CorteEvaluativo buscarPorFechaInicioMenorQueYFechaAsistenciaMayorQueYPeriodoYCarrera(Date fechaInicio, Date fechaAsistencia, Periodo periodo, Carrera carrera) {
		return corteEvaluativoRepository.findByFechaInicioLessThanEqualAndFechaAsistenciaGreaterThanEqualAndPeriodoAndCarrera(fechaInicio, fechaAsistencia, periodo, carrera);
	}
	*/
	
	@Override
	public CorteEvaluativo buscarPorFechaInicioMenorQueYFechaAsistenciaMayorQueYPeriodoYCarrera(Date fecha, Integer periodo, Integer carrera) {
		return corteEvaluativoRepository.findByFechaInicioLessThanEqualAndFechaAsistenciaGreaterThanEqualAndPeriodoAndCarrera(fecha, periodo, carrera);
	}

	@Override
	public CorteEvaluativo buscarPorFechaInicioMenorQueYFinEvaluacionesMayorQueYPeriodoYCarrera(Date fechaInicio, Date fechaFin,
			Periodo periodo, Carrera carrera) {
		return corteEvaluativoRepository.findByFechaInicioLessThanEqualAndFinEvaluacionesGreaterThanEqualAndPeriodoAndCarrera(fechaInicio, fechaFin, periodo, carrera);
	}

	@Override
	public CorteEvaluativo buscarPorInicioRemedialMenorQueYFinRemedialMayorQueYPeriodoYCarrera(Date fechaInicio, Date fechaFin,
			Periodo periodo, Carrera carrera) {
		return corteEvaluativoRepository.findByInicioRemedialLessThanEqualAndFinRemedialGreaterThanEqualAndPeriodoAndCarrera(fechaInicio, fechaFin, periodo, carrera);
	}

	@Override
	public CorteEvaluativo buscarPorInicioExtraordinarioMenorQueYFinExtraordinarioMayorQueYPeriodoYCarrera(Date fechaInicio,
			Date fechaFin, Periodo periodo, Carrera carrera) {
		return corteEvaluativoRepository.findByInicioExtraordinarioLessThanEqualAndFinExtraordinarioGreaterThanEqualAndPeriodoAndCarrera(fechaInicio, fechaFin, periodo, carrera);
	}

	@Override
	public Integer contarPorFechaDosificacionYPeriodoYCarreraYCorteEvaluativo(Date fechaInicio, Integer idPeriodo,
			Integer idCarrera, Integer idCorteEvaluativo) {
		return corteEvaluativoRepository.findByFechaDosificacionAndPeriodoAndCarreraAndCorteEvaluativo(fechaInicio, idPeriodo, idCarrera, idCorteEvaluativo);
	}
}
