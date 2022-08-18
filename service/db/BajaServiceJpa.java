package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.repository.BajaRepository;
import edu.mx.utdelacosta.service.IBajaService;

@Service
public class BajaServiceJpa implements IBajaService{

	@Autowired
	private BajaRepository bajaRepo;

	@Override
	public void guardar(Baja baja) {
		bajaRepo.save(baja);
	}
	
	@Override
	public Baja buscarPorId(Integer id) {
		Optional<Baja> optional = bajaRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Baja buscarPorEstadoAlumnoYFechaAutorizacion(Integer estatus, Alumno alumno, Date fecha) {
		return bajaRepo.findByEstatusAndAlumnoAndFechaAutorizacion(estatus, alumno, fecha);
	}

	@Override
	public List<Baja> buscarPorPersonaYEstatus(Integer idPersona, Integer estatus) {
		return bajaRepo.findByPersonaAndStatusAndFechaNull(idPersona, estatus);
	}

	@Override
	public List<Baja> buscarPorTipoYStatus(Integer tipo, Integer estatus) {
		return bajaRepo.findByTipoAndStatus(tipo, estatus);
	}

	@Override
	public List<Baja> buscarPorTipoStatusGrupoYPeriodo(Integer tipo, Integer estatus, Integer idGrupo, Integer idPeriodo) {
		return bajaRepo.findByTipoAndStatusAndGrupoAndPeriodo(tipo, estatus, idGrupo, idPeriodo);
	}

	@Override
	public List<Baja> buscarPorTipoStatusCarreraYPeriodo(Integer tipo, Integer estatus, Integer idCarrera, Integer idPeriodo) {
		return bajaRepo.findByTipoAndStatusAndCarreraAndPeriodo(tipo, estatus, idCarrera, idPeriodo);
	}

	@Override
	public List<Baja> buscarPorTipoStatusCarreraEntreFechas(Integer tipo, Integer estatus, Integer idCarrera, Date fechaInicio, Date fechaFin) {
		return bajaRepo.findByTipoAndStatusAndCarreraAndFechas(tipo, estatus, idCarrera, fechaInicio, fechaFin);
	}

	@Override
	public List<Baja> buscarPorTipoStatusPersonaEntreFechas(Integer tipo, Integer estatus, Integer idPersona, Date fechaInicio, Date fechaFin) {
		return bajaRepo.findByTipoAndStatusAndPersonaAndFechas(tipo, estatus, idPersona, fechaInicio, fechaFin);
	}

	@Override
	public List<Baja> buscarPorTipoStatusEntreFechas(Integer tipo, Integer estatus, Date fechaInicio, Date fechaFin) {
		return bajaRepo.findByTipoAndStatusAndFechas(tipo, estatus, fechaInicio, fechaFin);
	}

	@Override
	public List<Baja> buscarPorAlumno(Alumno alumno) {
		return bajaRepo.findByAlumnoOrderByFechaRegistroDesc(alumno);
	}

	@Override
	public void eliminar(Baja baja) {
		bajaRepo.delete(baja);
	}
	
}
