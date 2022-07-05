package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Instrumento;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.repository.MecanismoInstrumentoRepository;
import edu.mx.utdelacosta.service.IMecanismoInstrumentoService;

@Service
public class MecanismoInstrumentoServiceJpa implements IMecanismoInstrumentoService{

	@Autowired
	private MecanismoInstrumentoRepository mecanismoRepository;
	
	@Override
	public List<MecanismoInstrumento> buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(Integer idCargaHoraria,
			Integer idCorteEvaluativo, Boolean activo) {
		return mecanismoRepository.findByIdCargaHorariaAndIdCorteEvaluativoAndActivo(idCargaHoraria, idCorteEvaluativo, activo);
	}

	@Override
	public void guardar(MecanismoInstrumento mecanismo) {
		mecanismoRepository.save(mecanismo);		
	}

	@Override
	public MecanismoInstrumento buscarPorIdYActivo(Integer idMecanismo, Boolean activo) {
		Optional<MecanismoInstrumento> optional = mecanismoRepository.findByIdAndActivo(idMecanismo, activo);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<MecanismoInstrumento> buscarPorIdCargaHorariaYActivo(Integer idCargaHoraria, Boolean activo) {
		return mecanismoRepository.findByIdCargaHorariaAndActivoOrderByIdCorteEvaluativo(idCargaHoraria, activo);
	}

	@Override
	public MecanismoInstrumento buscarPorIdCargaHorariaEIdCorteEvaluativoEInstrumentoYActivo(Integer idCargaHoraria,
			Integer idCorteEvaluativo,Instrumento instrumento, Boolean activo) {
		return mecanismoRepository.findByIdCargaHorariaAndIdCorteEvaluativoAndInstrumentoAndActivo(idCargaHoraria, idCorteEvaluativo, instrumento, activo);
	}

	@Override
	public void eliminar(MecanismoInstrumento mecanismo) {
		mecanismoRepository.delete(mecanismo);
	}

	@Override
	public MecanismoInstrumento buscarPorId(Integer id) {
		Optional<MecanismoInstrumento> optional = mecanismoRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Integer sumaPonderacionPorIdCargaHorariaEIdCorteEvaluativo(Integer idCargaHoraria,
			Integer idCorteEvaluativo) {
		return mecanismoRepository.sumPonderacionByIdCargaHorariaAndIdCorteEvaluativo(idCargaHoraria, idCorteEvaluativo);
	}	

}
