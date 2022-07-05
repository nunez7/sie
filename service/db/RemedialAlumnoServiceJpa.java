package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<RemedialAlumno> buscarPorGrupoYAlumno(Integer idGrupo, Integer idAlumno) {
		// TODO Auto-generated method stub
		return remedialAlumnoRepository.findAllByGrupoAndAlumnoOrderByCorteEvaluativoAsc(idGrupo, idAlumno);
	}
	
	@Override
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
	public List<RemedialAlumno> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo) {
		return remedialAlumnoRepository.findByAlumnoAndPeriodo(idAlumno, idPeriodo);
	}

	@Override
	public List<RemedialAlumno> buscarPorAlumnoCargaYCorte(Alumno alumno, CargaHoraria cargaHoraria,
			CorteEvaluativo cortesEvaluativo) {
		// TODO Auto-generated method stub
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndCorteEvaluativoOrderByRemedialDesc(alumno, cargaHoraria, cortesEvaluativo);
	}		
	
	@Override
	public void eliminar(RemedialAlumno remedialAlumno) {
		remedialAlumnoRepository.delete(remedialAlumno);
		
	}

	@Override
	public Integer contarRemedialesAlumno(Integer idCargaHoraria, Integer idTipoRemedial) {
		return remedialAlumnoRepository.countRemedialAlumno(idCargaHoraria, idTipoRemedial);
	}

	@Override
	public RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedial(Alumno alumno, CargaHoraria cargaHoraria,
			Remedial remedial) {
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndRemedial(alumno, cargaHoraria, remedial);
	}

	@Override
	public RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYPagado(Alumno alumno, CargaHoraria cargaHoraria,
			Remedial remedial) {
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndRemedialAndPagado(alumno, cargaHoraria, remedial, true);
	}

	@Override
	public RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYCorte(Alumno alumno, CargaHoraria cargaHoraria,
			Remedial remedial, CorteEvaluativo corteEvaluativo) {
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndRemedialAndCorteEvaluativo(alumno, cargaHoraria, remedial, corteEvaluativo);
	}

	@Override
	public RemedialAlumno buscarPorAlumnoYCargaHorariaYRemedialYCorteYPagado(Alumno alumno, CargaHoraria cargaHoraria,
			Remedial remedial, CorteEvaluativo corteEvaluativo) {
		return remedialAlumnoRepository.findByAlumnoAndCargaHorariaAndRemedialAndCorteEvaluativoAndPagado(alumno, cargaHoraria, remedial, corteEvaluativo, true);
	}

	@Override
	public Integer contarRemedialesAlumnoPorCargaHorariaYRemedialYCorteEvaluativo(Integer idCargaHoraria, Integer idTipoRemedial, Integer idCorteEvaluativo) {
		return remedialAlumnoRepository.countRemedialAlumnoByCargaHorariaAndRemedialAndCorteEvalautivo(idCargaHoraria, idTipoRemedial, idCorteEvaluativo);
	}

	@Override
	public RemedialAlumno buscarUltimoPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCarga,
			Integer idCorte) {
		return remedialAlumnoRepository.findByLastAlumnoAndCargaHorariaAndCorteEvaluativo(idAlumno, idCarga, idCorte);
	}

	@Override
	public List<RemedialAlumno> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo, Integer tipo) {
		return remedialAlumnoRepository.findByAlumnoAndPeriodo(idAlumno, idPeriodo, tipo);
	}

	@Override
	public Integer countByCarreraAndCorteEvaluativo(Integer idCarrera, Integer tipoRemedial,
			Integer idCorteEvaluativo) {
		return remedialAlumnoRepository.countByCarreraAndCorteEvaluativo(idCarrera, tipoRemedial, idCorteEvaluativo);
	}
	
}
