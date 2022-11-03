package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Calificacion;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.dtoreport.CalificacionInstrumentoDTO;
import edu.mx.utdelacosta.repository.CalificacionRepository;
import edu.mx.utdelacosta.service.ICalificacionService;

@Service
public class CalificacionServiceJpa implements ICalificacionService {

	@Autowired
	private CalificacionRepository calificacionRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Calificacion> buscarPorAlumnoYCargaH(Integer idAlumno, Integer idCarga) {
		return calificacionRepository.findAllByAlumnoAndCargaHoraria(idAlumno, idCarga);
	}

	@Override
	public void guardar(Calificacion calificacion) {
		calificacionRepository.save(calificacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Calificacion> buscarPorMecanismoInstrumento(MecanismoInstrumento mecanismoInstrumento) {
		return calificacionRepository.findByMecanismoInstrumento(mecanismoInstrumento);
	}

	@Override
	@Transactional(readOnly = true)
	public Calificacion buscarPorId(Integer id) {
		Optional<Calificacion> optional = calificacionRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Calificacion> buscarPorIdAlumnoEIdCargaHorariaEIdCorteEvaluativo(Integer IdAlumno,
			Integer IdCargaHoraria, Integer idCorteEvaluativo) {
		return calificacionRepository.findByIdAlumnoAndIdCargaHorariaAndIdCorteEvaluativo(IdAlumno, IdCargaHoraria,
				idCorteEvaluativo);
	}

	@Override
	@Transactional
	public void eliminar(Calificacion calificacion) {
		calificacionRepository.delete(calificacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Calificacion> buscarPorAlumno(Alumno alumno) {
		return calificacionRepository.findByAlumno(alumno);
	}

	@Override
	@Transactional(readOnly = true)
	public Calificacion buscarPorAlumnoYMecanismoInstrumento(Alumno alumno, MecanismoInstrumento mecanismoInstrumento) {
		return calificacionRepository.findByAlumnoAndMecanismoInstrumento(alumno, mecanismoInstrumento);
	}

	@Override
	@Transactional(readOnly = true)
	public CalificacionInstrumentoDTO buscarPorAlumnoYMecanismoInstrumentoActivo(Alumno alumno, MecanismoInstrumento mecanismo) {
		return calificacionRepository.findByAlumnoAndMecanismoInstrumentoActivo(alumno, mecanismo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CalificacionInstrumentoDTO> findByCargaHorariaAndCorteEvaluativo(Integer idAlumno,
			Integer idCargaHoraria, Integer idCorteEvaluativo) {
		return calificacionRepository.findByCargaHorariaAndCorteEvaluativo(idCargaHoraria, idCorteEvaluativo, idAlumno);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Float buscarCalificacionPorAlumnoYMecanismoInstrumento(Integer idAlumno, Integer idMecanismo) {
		return calificacionRepository.findCalificacionByAlumnoAndMecanismoInstrumento(idAlumno, idMecanismo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarPorIdMecanismoInstrumento(Integer idMecanismo) {
		return calificacionRepository.countByIdMecanismoInstrumento(idMecanismo);
	}
}
