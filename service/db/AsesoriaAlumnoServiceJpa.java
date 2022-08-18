package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.AsesoriaAlumno;
import edu.mx.utdelacosta.repository.AsesoriaAlumnoRepository;
import edu.mx.utdelacosta.service.IAsesoriaAlumnoService;

@Service
public class AsesoriaAlumnoServiceJpa implements IAsesoriaAlumnoService{
	
	@Autowired
	private AsesoriaAlumnoRepository asesoriaAlumnoRepository;

	@Override
	public void guardar(AsesoriaAlumno asesoriaAlumno) {
		asesoriaAlumnoRepository.save(asesoriaAlumno);		
	}

	@Override
	public List<AsesoriaAlumno> buscarPorAlumnoPeriodoYTipo(Integer idAlumno, Integer idPeriodo, Integer tipo) {
		return asesoriaAlumnoRepository.findByAlumnoAndPeriodoAndTipo(idAlumno, idPeriodo, tipo);
	}

	@Override
	public List<AsesoriaAlumno> buscarPorGrupoAlumnoPeriodoYTipo(Integer idGrupo, Integer idAlumno, Integer idPeriodo, Integer tipo) {
		return asesoriaAlumnoRepository.findByGrupoAndAlumnoAndPeriodoAndTipo(idGrupo, idAlumno, idPeriodo, tipo);
	}

	@Override
	public List<AsesoriaAlumno> buscarPorGrupoPeriodoYTipo(Integer idGrupo, Integer idPeriodo, Integer tipo) {
		return asesoriaAlumnoRepository.findByGrupoAndPeriodoAndTipo(idGrupo, idPeriodo, tipo);
	}

	@Override
	public List<AsesoriaAlumno> buscarPorGrupoAlumnoPeriodoCargaYTipo(Integer idGrupo, Integer idAlumno, Integer idPeriodo, Integer idCarga, Integer tipo) {
		return asesoriaAlumnoRepository.findByGrupoAndAlumnoAndPeriodoCargaAndTipo(idGrupo, idAlumno, idPeriodo, idCarga, tipo);
	}

	@Override
	public List<AsesoriaAlumno> buscarPorGrupoPeriodoCargaYTipo(Integer idGrupo, Integer idPeriodo, Integer idCarga, Integer tipo) {
		return asesoriaAlumnoRepository.findByGrupoAndPeriodoCargaAndTipo(idGrupo, idPeriodo, idCarga, tipo);
	}
	
	
}
