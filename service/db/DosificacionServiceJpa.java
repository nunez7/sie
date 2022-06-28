package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Dosificacion> buscarPorPersona(Persona persona) {
		return dosificacionRepository.findByPersona(persona);
	}

	@Override
	public Dosificacion buscarPorIdCargaHorariaEIdCorteEvaluativo(Integer idCargaHoraria, Integer idCorteEvaluativo) {
		return dosificacionRepository.findByIdCargaHorariaAndIdCorteEvaluativo(idCargaHoraria, idCorteEvaluativo);
	}

	@Override
	public void guardar(Dosificacion dosificacion) {
		dosificacionRepository.save(dosificacion);
	}

	@Override
	public Dosificacion encontrarUltimo() {
		return dosificacionRepository.findFirst1By();
	}

	@Override
	public List<Dosificacion> buscarPorCargaHoraria(Integer cargaHoraria) {
		// TODO Auto-generated method stub
		return dosificacionRepository.findByIdCargaHoraria(cargaHoraria);
	}

	@Override
	public Dosificacion buscarPorId(Integer id) {
		Optional<Dosificacion> optional = dosificacionRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<DosificacionPendienteDTO> obtenerPendientesPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return dosificacionRepository.getAllPendientesAndPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}

	@Override
	public Dosificacion encontrarUltimaDosificacion() {
		return dosificacionRepository.findLastDosificacion();
	}

	@Override
	public List<Dosificacion> buscarPorIdCargaHoraria(Integer idCargaHoraria) {
		return dosificacionRepository.findByIdCargaHoraria(idCargaHoraria);
	}
	
	@Override
	public Dosificacion buscarPorIdMateriaEIdPersona(Integer idMateria, Integer IdPersona) {
		return dosificacionRepository.findByIdMateriaAndIdPersona(idMateria, IdPersona);
	}

	@Override
	public Integer contarPendientesPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo) {
		return dosificacionRepository.coountByPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}
}
