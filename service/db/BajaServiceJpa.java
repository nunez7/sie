package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public Baja buscarPorId(Integer id) {
		Optional<Baja> optional = bajaRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Baja buscarPorEstadoAlumnoYFechaAutorizacion(Integer estatus, Integer idAlumno) {
		return bajaRepo.findByEstatusAndAlumnoAndFechaAutorizacion(estatus, idAlumno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Baja> buscarPorPersonaYEstatus(Integer idPersona, Integer estatus, Integer idPeriodo) {
		return bajaRepo.findByPersonaAndStatusAndFechaNull(idPersona, estatus, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Baja> buscarPorTipoYStatus(Integer tipo, Integer estatus) {
		return bajaRepo.findByTipoAndStatus(tipo, estatus);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Baja> buscarPorTipoStatusGrupoYPeriodo(Integer tipo, Integer estatus, Integer idGrupo, Integer idPeriodo) {
		return bajaRepo.findByTipoAndStatusAndGrupoAndPeriodo(tipo, estatus, idGrupo, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Baja> buscarPorTipoStatusCarreraYPeriodo(Integer tipo, Integer estatus, Integer idCarrera, Integer idPeriodo) {
		return bajaRepo.findByTipoAndStatusAndCarreraAndPeriodo(tipo, estatus, idCarrera, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Baja> buscarPorTipoStatusCarreraEntreFechas(Integer estatus, Integer idCarrera,Integer idPeriodo, Date fechaInicio, Date fechaFin) {
		return bajaRepo.findByTipoAndStatusAndCarreraAndFechas(estatus, idCarrera, idPeriodo, fechaInicio, fechaFin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Baja> buscarPorTipoStatusPersonaEntreFechas(Integer estatus, Integer idPersona, Date fechaInicio, Date fechaFin) {
		return bajaRepo.findByTipoAndStatusAndPersonaAndFechas(estatus, idPersona, fechaInicio, fechaFin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Baja> buscarPorTipoStatusEntreFechas(Integer estatus, Date fechaInicio, Date fechaFin) {
		return bajaRepo.findByTipoAndStatusAndFechas( estatus, fechaInicio, fechaFin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Baja> buscarPorAlumno(Alumno alumno) {
		return bajaRepo.findByAlumnoOrderByFechaRegistroDesc(alumno);
	}

	@Override
	@Transactional
	public void eliminar(Baja baja) {
		bajaRepo.delete(baja);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarBajasActivasPorAlumno(Integer idAlumno) {
		// TODO Auto-generated method stub
		return bajaRepo.countByAlumnoActivas(idAlumno);
	}

	@Override
	public List<Baja> buscarPorGrupo(Integer idGrupo) {
		// TODO Auto-generated method stub
		return bajaRepo.findByGrupo(idGrupo);
	}
	
}
