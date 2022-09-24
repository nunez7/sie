package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.CalendarioEvaluacion;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.UnidadTematica;
import edu.mx.utdelacosta.repository.CalendarioEvaluacionRepository;
import edu.mx.utdelacosta.service.ICalendarioEvaluacionService;

@Service
public class CalendarioEvaluacionServiceJpa implements ICalendarioEvaluacionService{

	@Autowired
	private CalendarioEvaluacionRepository calendarioRepository;
	
	@Override
	public void guarda(CalendarioEvaluacion calendarioEvaluacion) {
		calendarioRepository.save(calendarioEvaluacion);
	}

	@Override
	@Transactional(readOnly = true)
	public CalendarioEvaluacion buscarPorId(Integer id) {
		Optional<CalendarioEvaluacion> optional = calendarioRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public CalendarioEvaluacion buscarPorCargaHorariaYUnidadTematica(CargaHoraria carga,
			UnidadTematica unidad) {
		return calendarioRepository.findByCargaHorariaAndUnidadTematica(carga, unidad);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CalendarioEvaluacion> buscarPorCargaHorariaYCorteEvaluativo(CargaHoraria carga, CorteEvaluativo corte) {
		return calendarioRepository.findByCargaHorariaAndCorteEvaluativo(carga, corte);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CalendarioEvaluacion> buscarPorCargaHoraria(CargaHoraria carga) {
		return calendarioRepository.findByCargaHoraria(carga);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer distinguirPorCorteEvaluativoPorCargaHoraria(Integer idCargaHoraria) {
		return calendarioRepository.distinctByCorteEvaluativoByCargaHoraria(idCargaHoraria);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarPorCargaHorariaYCorteEvaluativo(Integer idCarga, Integer idCorte) {
		return calendarioRepository.countByCargaHorariaAndCorteEvaluativo(idCarga, idCorte);
	}

}
