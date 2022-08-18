package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Canalizacion;
import edu.mx.utdelacosta.repository.CanalizacionRepository;
import edu.mx.utdelacosta.service.ICanalizacionService;

@Service
public class CanalizacionServiceJpa implements ICanalizacionService{
	
	@Autowired
	private CanalizacionRepository canlizacionRepo;

	@Override
	public void guardar(Canalizacion canalizacion) {
		canlizacionRepo.save(canalizacion);
	}

	@Override
	public List<Canalizacion> buscarPorAlumno(Alumno alumno) {
		return canlizacionRepo.findByAlumno(alumno);
	}
	
	@Override
	public List<Canalizacion> buscarPorGrupoPeriodoYAlumno(Integer idGrupo, Integer idPeriodo, Integer idAlumno) {
		return canlizacionRepo.findByGrupoAndPeriodoAndAlumno(idGrupo, idPeriodo, idAlumno);
	}

	@Override
	public List<Canalizacion> buscarPorGrupoPeriodo(Integer idGrupo, Integer idPeriodo) {
		return canlizacionRepo.findByGrupoAndPeriodo(idGrupo, idPeriodo);
	}

	@Override
	public List<Canalizacion> buscarPorCarreraPeriodo(Integer idCarrera, Integer idPeriodo) {
		return canlizacionRepo.findByCarreraAndPeriodo(idCarrera, idPeriodo);
	}

	@Override
	public Integer contarPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return canlizacionRepo.findTotalByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

	@Override
	public Integer contarDistinctAlumnoPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return canlizacionRepo.findTotalDistinctAlumnoByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

	@Override
	public List<Canalizacion> buscarPorGrupoPeriodoAlumnoYServicio(Integer idGrupo, Integer idPeriodo, Integer idAlumno,Integer idServicio) {
		return canlizacionRepo.findByGrupoAndPeriodoAndAlumnoAndServicio(idGrupo, idPeriodo, idAlumno, idServicio);
	}

	@Override
	public List<Canalizacion> buscarPorGrupoPeriodoYServicio(Integer idGrupo, Integer idPeriodo, Integer idServicio) {
		return canlizacionRepo.findByGrupoAndPeriodoAndServicio(idGrupo, idPeriodo, idServicio);
	}

	@Override
	public List<Canalizacion> buscarPorCarreraPeriodoYServicio(Integer idCarrera, Integer idPeriodo, Integer idServicio) {
		return canlizacionRepo.findByCarreraAndPeriodoAndServicio(idCarrera, idPeriodo, idServicio);
	}

}
