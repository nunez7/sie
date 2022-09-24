package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionCarga;
import edu.mx.utdelacosta.repository.DosificacionCargaRepository;
import edu.mx.utdelacosta.service.IDosificacionCargaService;

@Service
public class DosificacionCargaServiceJpa implements IDosificacionCargaService{

	@Autowired
	private DosificacionCargaRepository dosiCargaRepository;
	
	@Override
	public void guardar(DosificacionCarga dosificacionCarga) {
		dosiCargaRepository.save(dosificacionCarga);		
	}

	@Override
	@Transactional(readOnly = true)
	public DosificacionCarga buscarPorDosificacionYCargaHoraria(Dosificacion dosificacion, CargaHoraria cargaHoraria) {
		return dosiCargaRepository.findByDosificacionAndCargaHoraria(dosificacion, cargaHoraria);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DosificacionCarga> buscarPorCargaHoraria(CargaHoraria cargaHoraria) {
		return dosiCargaRepository.findByCargaHoraria(cargaHoraria);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarNoEntregadas(Integer idProfesor, Integer idPeriodo) {
		return dosiCargaRepository.countNoEntregadas(idProfesor, idPeriodo);
	}

}
