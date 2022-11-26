package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Remedial;
import edu.mx.utdelacosta.model.RemedialAlumno;
import edu.mx.utdelacosta.repository.RemedialAlumnoRepository;
import edu.mx.utdelacosta.service.IRemedialAlumnoService;

@Service
public class RemedialAlumnoServiceJpa implements IRemedialAlumnoService{
	
	@Autowired
	private RemedialAlumnoRepository remedialAlumnoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<RemedialAlumno> buscarPorGrupoYAlumno(Integer idGrupo, Integer idAlumno) {
		// TODO Auto-generated method stub
		return remedialAlumnoRepository.findAllByGrupoAndAlumnoOrderByCorteEvaluativoAsc(idGrupo, idAlumno);
	}
	
	@Override
	@Transactional(readOnly = true)
	public RemedialAlumno buscarPorId(Integer id) {
		Optional<RemedialAlumno> optional = remedialAlumnoRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	public void guardar(RemedialAlumno remedialAlumno) {
		remedialAlumnoRepository.save(remedialAlumno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RemedialAlumno> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo) {
		return remedialAlumnoRepository.findByAlumnoAndPeriodo(idAlumno, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RemedialAlumno> buscarPorAlumnoCargaYCorte(Alumno alumno, CargaHoraria cargaHoraria,
			CorteEvaluativo cortesEvaluativo) {
		// TODO Auto-generated method stub
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndCorteEvaluativoOrderByRemedialDesc(alumno, cargaHoraria, cortesEvaluativo);
	}		
	
	@Override
	@Transactional
	public void eliminar(RemedialAlumno remedialAlumno) {
		remedialAlumnoRepository.delete(remedialAlumno);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarRemedialesAlumno(Integer idCargaHoraria, Integer idTipoRemedial) {
		return remedialAlumnoRepository.countRemedialAlumno(idCargaHoraria, idTipoRemedial);
	}

	@Override
	@Transactional(readOnly = true)
	public RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedial(Alumno alumno, CargaHoraria cargaHoraria,
			Remedial remedial) {
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndRemedial(alumno, cargaHoraria, remedial);
	}

	@Override
	@Transactional(readOnly = true)
	public RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYPagado(Alumno alumno, CargaHoraria cargaHoraria,
			Remedial remedial) {
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndRemedialAndPagado(alumno, cargaHoraria, remedial, true);
	}

	@Override
	@Transactional(readOnly = true)
	public RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYCorte(Alumno alumno, CargaHoraria cargaHoraria,
			Remedial remedial, CorteEvaluativo corteEvaluativo) {
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndRemedialAndCorteEvaluativo(alumno, cargaHoraria, remedial, corteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYCorteYPagado(Alumno alumno, CargaHoraria cargaHoraria,
			Remedial remedial, CorteEvaluativo corteEvaluativo) {
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndRemedialAndCorteEvaluativoAndPagado(alumno, cargaHoraria, remedial, corteEvaluativo, true);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarRemedialesAlumnoPorCargaHorariaYRemedialYCorteEvaluativo(Integer idCargaHoraria, Integer idTipoRemedial, Integer idCorteEvaluativo) {
		return remedialAlumnoRepository.countRemedialAlumnoByCargaHorariaAndRemedialAndCorteEvalautivo(idCargaHoraria, idTipoRemedial, idCorteEvaluativo);
	}

	@Override
	@Transactional(readOnly = true)
	public RemedialAlumno buscarUltimoPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCarga,
			Integer idCorte) {
		return remedialAlumnoRepository.findByLastAlumnoAndCargaHorariaAndCorteEvaluativo(idAlumno, idCarga, idCorte);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RemedialAlumno> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo, Integer tipo) {
		return remedialAlumnoRepository.findByAlumnoAndPeriodo(idAlumno, idPeriodo, tipo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByCarreraAndCorteEvaluativo(Integer idCarrera, Integer tipoRemedial,
			Integer idCorteEvaluativo) {
		// TODO Auto-generated method stub
		return remedialAlumnoRepository.countByCarreraAndCorteEvaluativo(idCarrera, tipoRemedial, idCorteEvaluativo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer buscarCalificacionPorAlumnoYCargaHorariaYCorteEvaluativoYTipo(Integer idAlumno,
			Integer idCargaHoraria, Integer idCorte, Integer tipo) {
		// TODO Auto-generated method stub
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndCorteEvaluativoAndTipo(idAlumno, idCargaHoraria, idCorte, tipo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarPorCarreraYCorteEvaluativoYTurno(Integer idCarrera, Integer tipoRemedial,
			Integer idCorteEvaluativo, Integer turno) {
		return remedialAlumnoRepository.countByCarreraAndCorteEvaluativoAndTurno(idCarrera, tipoRemedial, idCorteEvaluativo, turno);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean exsisteRemedialAlumno(Integer idAlumno, Integer idCargaHoraria, Integer idCorteEvaluativo,
			Integer tipoRemedial) {
		return remedialAlumnoRepository.existsRemedialAlumno(idAlumno, idCargaHoraria, idCorteEvaluativo, tipoRemedial);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarRemedialesPorAlumnoYCorteEvaluativoYTipoIntegerRemedial(Integer idAlumno, Integer idCorteEvaluativo, Integer tipo) {
		// TODO Auto-generated method stub
		return remedialAlumnoRepository.countByAlumnoAndCorteEvaluativoAndTipoRemedial(idAlumno, idCorteEvaluativo, tipo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarRemedialesAlumnoPorCargaHorariaYRemedial(Integer idCargaHoraria, Integer idTipoRemedial) {
		// TODO Auto-generated method stub
		return remedialAlumnoRepository.countRemedialAlumnoByCargaHorariaAndRemedial(idCargaHoraria, idTipoRemedial);
}
	public Integer contarPorCarreraYTipoRemedial(Integer carrera, Integer tipo) {
		return remedialAlumnoRepository.countByCarreraAndTipoRemedial(carrera, tipo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarPorAlumnoYPeriodoYTipo(Integer alumno, Integer periodo, Integer tipo) {
		return remedialAlumnoRepository.countByAlumnoAndPeriodoAndTipo(alumno, periodo,tipo);
	}
	
}