package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dtoreport.DosificacionPendienteDTO;
import edu.mx.utdelacosta.repository.DosificacionesRepository;
import edu.mx.utdelacosta.service.IDosificacionService;

@Service
public class DosificacionServiceJpa implements IDosificacionService{

	@Autowired
	private DosificacionesRepository dosificacionRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Dosificacion> buscarPorPersona(Persona persona) {
		return dosificacionRepository.findByPersona(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public Dosificacion buscarPorIdCargaHorariaEIdCorteEvaluativo(Integer idCargaHoraria, Integer idCorteEvaluativo) {
		return dosificacionRepository.findByIdCargaHorariaAndIdCorteEvaluativo(idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	public void guardar(Dosificacion dosificacion) {
		dosificacionRepository.save(dosificacion);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Dosificacion> buscarPorCargaHoraria(Integer cargaHoraria) {
		// TODO Auto-generated method stub
		return dosificacionRepository.findByIdCargaHoraria(cargaHoraria);
	}

	@Override
	@Transactional(readOnly = true)
	public Dosificacion buscarPorId(Integer id) {
		Optional<Dosificacion> optional = dosificacionRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DosificacionPendienteDTO> obtenerPendientesPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return dosificacionRepository.getAllPendientesAndPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dosificacion> buscarPorIdCargaHoraria(Integer idCargaHoraria) {
		return dosificacionRepository.findByIdCargaHoraria(idCargaHoraria);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Dosificacion buscarPorIdMateriaEIdPersona(Integer idMateria, Integer IdPersona) {
		return dosificacionRepository.findByIdMateriaAndIdPersona(idMateria, IdPersona);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarPendientesPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo) {
		return dosificacionRepository.coountByPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dosificacion> buscarImportadaPorCargaHoraria(Integer idCargaHoraria) {
		return dosificacionRepository.findImportedByCargaHoraria(idCargaHoraria);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> buscarColaboradoresPorDosificacion(Integer idDosificacion) {
		return dosificacionRepository.findColaboradoresByDosificacion(idDosificacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> buscarIdporIdCargaHoraria(Integer idCargaHorararia) {
		return dosificacionRepository.findIdByIdCargaHoraria(idCargaHorararia);
	}
}
