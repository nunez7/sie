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

}
