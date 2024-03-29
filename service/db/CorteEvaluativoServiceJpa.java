package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<CorteEvaluativo> buscarPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return corteEvaluativoRepository.findByIdCarreraAndIdPeriodo(idCarrera, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CorteEvaluativo> buscarPorCarreraYPeriodo(Carrera carrera, Periodo periodo) {
		// TODO Auto-generated method stub
		return corteEvaluativoRepository.findByCarreraAndPeriodoOrderByConsecutivo(carrera, periodo);
	}

	@Override
	@Transactional(readOnly = true)
	public CorteEvaluativo buscarPorId(Integer id) {
		Optional<CorteEvaluativo> optional = corteEvaluativoRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CorteEvaluativo> buscarPorPeriodo(Periodo periodo) {
		return corteEvaluativoRepository.findByPeriodoOrderByIdAsc(periodo);
	}

	@Override
	@Transactional(readOnly = true)
	public CorteEvaluativo buscarPorCorteYLimiteCaptura(Date fecha, CorteEvaluativo corte) {
		return corteEvaluativoRepository.findByCorteAndLimiteCaptura(fecha, corte);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CorteEvaluativo buscarPorFechaInicioYFechaFinYPeriodoYCarrera(Date fecha, Periodo periodo, Carrera carrera) {
		return corteEvaluativoRepository.findByFechaInicioAndFechaFinAndPeriodoAndCarrera(fecha, periodo, carrera);
	}

	@Override
	@Transactional(readOnly = true)
	public CorteEvaluativo buscarPorCorteYFechaRemedial(CorteEvaluativo corte, Date fecha) {
		return corteEvaluativoRepository.findByCorteEvaluativoAndFechaRemedial(corte, fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public CorteEvaluativo buscarPorCorteYFechaExtraordinario(CorteEvaluativo corte, Date fecha) {
		return corteEvaluativoRepository.findByCorteAndFechaExtraordinario(corte, fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public CorteEvaluativo buscarPorCorteYFechaDosificacion(CorteEvaluativo corte, Date fecha) {
		return corteEvaluativoRepository.findByCorteAndFechaDosificacion(corte, fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarPorFechaDosificacionYPeriodoYCarreraYCorteEvaluativo(Date fechaInicio, Integer idPeriodo,
			Integer idCarrera, Integer idCorteEvaluativo) {
		return corteEvaluativoRepository.findByFechaDosificacionAndPeriodoAndCarreraAndCorteEvaluativo(fechaInicio, idPeriodo, idCarrera, idCorteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer buscarPorCargaHorariaYCalendarioEvaluacion(Integer idCargaHoraria, Integer idCorteEvaluativo) {
		return corteEvaluativoRepository.findByCargaHorariaAndCalendarioEvaluacion(idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean buscarParcialConPlazoValido(Date fecha, Integer idCorte) {
		return corteEvaluativoRepository.findPlazoEntregaParcial(fecha, idCorte);
	}
}
