package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.repository.ProrrogaRepository;
import edu.mx.utdelacosta.service.IProrrogaService;

@Service
public class ProrrogasServiceJpa implements IProrrogaService{

	@Autowired
	private ProrrogaRepository prorrogaRepository;

	@Override
	public List<Prorroga> buscarPorCarreraYPendientes(Integer idPersona) {
		// TODO Auto-generated method stub
		return prorrogaRepository.findByCarreraAndRequested(idPersona);
	}

	@Override
	public Prorroga buscarPorId(Integer id) {
		Optional<Prorroga> prorroga =  prorrogaRepository.findById(id);
		if(prorroga.isPresent()) {
			return prorroga.get();
		}
		return null;
	}

	@Override
	public void guardar(Prorroga prorroga) {
		// TODO Auto-generated method stub
		prorrogaRepository.save(prorroga);
	}

	@Override
	public Integer prorrogasPorProfesorAndPeriodo(Integer idProfesor, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return prorrogaRepository.countProrrogasByIdProfesorAndPeriodo(idProfesor, idPeriodo);
	}
	
	@Override
	public Prorroga buscarPorCargaHorariaEIdTipoProrrogaYActivoYAceptada(CargaHoraria cargaHoraria, Integer idTipo,
			boolean activo, boolean aceptada) {
		return prorrogaRepository.findByCargaHorariaAndIdTipoProrrogaAndActivoAndAceptada(cargaHoraria, idTipo, activo, aceptada);
	}
	
	@Override
	public Prorroga buscarPorCargaHorariaYCorteEvaluativoEIdTipoProrrgaYActivo(CargaHoraria cargaHoraria, CorteEvaluativo corteEvaluativo, Integer idTipoProrroga,
			boolean activo) {
		return prorrogaRepository.findByCargaHorariaAndCorteEvaluativoAndIdTipoProrrogaAndActivo(cargaHoraria, corteEvaluativo, idTipoProrroga, activo);
	}

	@Override
	public Prorroga buscarPorCargaHorariaIdTipoProrrogaYFechaLimiteMayorQueYActivoYAceptada(CargaHoraria cargaHoraria, Integer idTipoProrroga,
			Date fechaLimite, Boolean activo, Boolean aceptada) {
		return prorrogaRepository.findByCargaHorariaAndIdTipoProrrogaAndFechaLimiteGreaterThanEqualAndActivoAndAceptada(cargaHoraria, idTipoProrroga, fechaLimite, activo, aceptada);
	}
	
	@Override
	public List<Prorroga> buscarPorCargaHoraria(CargaHoraria cargaHoraria) {
		return prorrogaRepository.findByCargaHoraria(cargaHoraria);
	}

	@Override
	public List<Prorroga> buscarPorIdProfesor(Integer idProfesor) {
		return prorrogaRepository.findByIdProfesor(idProfesor);
	}

	@Override
	public List<Prorroga> buscarPorProfesorYPeriodoYActivo(Integer idProfesor, Integer idPeriodo) {
		return prorrogaRepository.findByProfesorAndPeriodoAndActivo(idProfesor, idPeriodo);
	}

	@Override
	public List<Prorroga> buscarPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo) {
		return prorrogaRepository.findByProfesorAndPeriodo(idProfesor, idPeriodo);
	}
}
