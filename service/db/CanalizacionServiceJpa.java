package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Canalizacion;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.repository.CanalizacionRepository;
import edu.mx.utdelacosta.service.ICanalizacionService;

@Service
public class CanalizacionServiceJpa implements ICanalizacionService{
	
	@Autowired
	private CanalizacionRepository canalizacionRepo;

	@Override
	@Transactional
	public void guardar(Canalizacion canalizacion) {
		canalizacionRepo.save(canalizacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Canalizacion> buscarPorAlumno(Alumno alumno) {
		return canalizacionRepo.findByAlumno(alumno);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Canalizacion> buscarPorGrupoPeriodoYAlumno(Integer idGrupo, Integer idPeriodo, Integer idAlumno) {
		return canalizacionRepo.findByGrupoAndPeriodoAndAlumno(idGrupo, idPeriodo, idAlumno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Canalizacion> buscarPorGrupoPeriodo(Integer idGrupo, Integer idPeriodo) {
		return canalizacionRepo.findByGrupoAndPeriodo(idGrupo, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Canalizacion> buscarPorCarreraPeriodo(Integer idCarrera, Integer idPeriodo) {
		return canalizacionRepo.findByCarreraAndPeriodo(idCarrera, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return canalizacionRepo.findTotalByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarDistinctAlumnoPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return canalizacionRepo.findTotalDistinctAlumnoByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Canalizacion> buscarPorGrupoPeriodoAlumnoYServicio(Integer idGrupo, Integer idPeriodo, Integer idAlumno,Integer idServicio) {
		return canalizacionRepo.findByGrupoAndPeriodoAndAlumnoAndServicio(idGrupo, idPeriodo, idAlumno, idServicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Canalizacion> buscarPorGrupoPeriodoYServicio(Integer idGrupo, Integer idPeriodo, Integer idServicio) {
		return canalizacionRepo.findByGrupoAndPeriodoAndServicio(idGrupo, idPeriodo, idServicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Canalizacion> buscarPorCarreraPeriodoYServicio(Integer idCarrera, Integer idPeriodo, Integer idServicio) {
		return canalizacionRepo.findByCarreraAndPeriodoAndServicio(idCarrera, idPeriodo, idServicio);
	}

	@Override
	@Transactional(readOnly = true)
	public Canalizacion buscarPorTutoria(TutoriaIndividual tutoria) {
		return canalizacionRepo.findByTutoriaIndividual(tutoria);
	}

}
