package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.TipoProrroga;
import edu.mx.utdelacosta.repository.ProrrogaRepository;
import edu.mx.utdelacosta.service.IProrrogaService;

@Service
public class ProrrogasServiceJpa implements IProrrogaService{

	@Autowired
	private ProrrogaRepository prorrogaRepository;

	@Override
	public List<Prorroga> buscarPorCarreraYPendientes(Integer idPersona, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return prorrogaRepository.findByCarreraAndRequested(idPersona, idPeriodo);
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
	public Prorroga buscarPorCargaHorariaYTipoProrrogaYActivoYAceptada(CargaHoraria cargaHoraria,
			TipoProrroga tipoProrroga, boolean activo, boolean aceptada) {
		return prorrogaRepository.findByCargaHorariaAndTipoProrrogaAndActivoAndAceptada(cargaHoraria, tipoProrroga,
				activo, aceptada);
	}

	@Override
	public Prorroga buscarPorCargaHorariaYCorteEvaluativoYTipoProrrgaYActivo(CargaHoraria cargaHoraria, CorteEvaluativo corteEvaluativo, TipoProrroga tipoProrroga,
			boolean activo) {
		return prorrogaRepository.findByCargaHorariaAndCorteEvaluativoAndTipoProrrogaAndActivo(cargaHoraria, corteEvaluativo, tipoProrroga, activo);
	}
	
	@Override
	public Prorroga buscarPorCargaHorariaYTipoProrrogaYFechaLimiteMayorQueYActivoYAceptada(CargaHoraria cargaHoraria, TipoProrroga tipoProrroga,
			Date fechaLimite, Boolean activo, Boolean aceptada) {
		return prorrogaRepository.findByCargaHorariaAndTipoProrrogaAndFechaLimiteGreaterThanEqualAndActivoAndAceptada(cargaHoraria, tipoProrroga, fechaLimite, activo, aceptada);
	}
	
	@Override
	public List<Prorroga> buscarPorCargaHoraria(CargaHoraria cargaHoraria) {
		return prorrogaRepository.findByCargaHoraria(cargaHoraria);
	}

	@Override
	public List<Prorroga> buscarPorProfesorYPeriodoYActivo(Integer idProfesor, Integer idPeriodo) {
		return prorrogaRepository.findByProfesorAndPeriodoAndActivo(idProfesor, idPeriodo);
	}

	@Override
	public List<Prorroga> buscarPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo) {
		return prorrogaRepository.findByProfesorAndPeriodo(idProfesor, idPeriodo);
	}

	@Override
	public List<Prorroga> buscarPorPersonaCarrerraAndAceptadas(Integer idPersona, Integer idPeriodo) {
		return prorrogaRepository.findByPersonaCarreraAndAccept(idPersona, idPeriodo);
	}

	@Override
	public Integer contarProrrogasPendientesPorPersonaYPeriodo(Integer idPersona, Integer idPeriodo) {
		return prorrogaRepository.countPendientesByPersonaCarreraAndPeriodo(idPersona, idPeriodo);

	public Prorroga buscarPorCargaHorariaYTipoProrrogaYCorteEvaluativoYActivoYAceptada(CargaHoraria cargaHoraria,
			TipoProrroga tipoProrroga, CorteEvaluativo corteEvaluativo, boolean activo, boolean aceptada) {
		return prorrogaRepository.findByCargaHorariaAndTipoProrrogaAndCorteEvaluativoAndActivoAndAceptada(cargaHoraria, tipoProrroga, corteEvaluativo, activo, aceptada);
	}
}
