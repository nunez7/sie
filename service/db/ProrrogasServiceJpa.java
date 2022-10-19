package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public List<Prorroga> buscarPorCarreraYPendientes(Integer idPersona, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return prorrogaRepository.findByCarreraAndRequested(idPersona, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public Integer prorrogasPorProfesorAndPeriodo(Integer idProfesor, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return prorrogaRepository.countProrrogasByIdProfesorAndPeriodo(idProfesor, idPeriodo);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Prorroga buscarPorCargaHorariaYTipoProrrogaYFecha(CargaHoraria cargaHoraria,
			TipoProrroga tipoProrroga,Date fecha, CorteEvaluativo corte) {
		return prorrogaRepository.findByCargaHorariaAndTipoProrrogaAndFechaAndCorte(cargaHoraria, tipoProrroga,fecha, corte);
	}

	@Transactional(readOnly = true)
	@Override
	public Prorroga buscarPorCargaHorariaYCorteEvaluativoYTipoProrrgaYActivo(CargaHoraria cargaHoraria, CorteEvaluativo corteEvaluativo, TipoProrroga tipoProrroga,
			boolean activo) {
		return prorrogaRepository.findByCargaHorariaAndCorteEvaluativoAndTipoProrrogaAndActivo(cargaHoraria, corteEvaluativo, tipoProrroga, activo);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Prorroga buscarPorCargaHorariaYTipoProrrogaYFechaLimiteMayorQueYActivoYAceptada(CargaHoraria cargaHoraria, TipoProrroga tipoProrroga,
			Date fechaLimite, Boolean activo, Boolean aceptada) {
		return prorrogaRepository.findByCargaHorariaAndTipoProrrogaAndFechaLimiteGreaterThanEqualAndActivoAndAceptada(cargaHoraria, tipoProrroga, fechaLimite, activo, aceptada);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Prorroga> buscarPorCargaHoraria(CargaHoraria cargaHoraria) {
		return prorrogaRepository.findByCargaHoraria(cargaHoraria);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Prorroga> buscarPorProfesorYPeriodoYActivo(Integer idProfesor, Integer idPeriodo) {
		return prorrogaRepository.findByProfesorAndPeriodoAndActivo(idProfesor, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Prorroga> buscarPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo) {
		return prorrogaRepository.findByProfesorAndPeriodo(idProfesor, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Prorroga> buscarPorPersonaCarrerraAndAceptadas(Integer idPersona, Integer idPeriodo) {
		return prorrogaRepository.findByPersonaCarreraAndAccept(idPersona, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarProrrogasPendientesPorPersonaYPeriodo(Integer idPersona, Integer idPeriodo) {
		return prorrogaRepository.countPendientesByPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}
	
	@Transactional(readOnly = true)
	public Prorroga buscarPorCargaHorariaYTipoProrrogaYCorteEvaluativoYActivoYAceptada(CargaHoraria cargaHoraria,
			TipoProrroga tipoProrroga, CorteEvaluativo corteEvaluativo, boolean activo, boolean aceptada) {
		return prorrogaRepository.findByCargaHorariaAndTipoProrrogaAndCorteEvaluativoAndActivoAndAceptada(cargaHoraria, tipoProrroga, corteEvaluativo, activo, aceptada);
	}

	@Override
	public Boolean existeDeCalificacionPorFechaYCargaHorariaYCorte(Date fecha, Integer cargaHoraria,
			Integer corteEvaluativo) {
		return prorrogaRepository.existsCalificacionByCargaAndDate(fecha, cargaHoraria, corteEvaluativo);
	}
}
