package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TemaGrupal;
import edu.mx.utdelacosta.repository.TemaGrupalRepository;
import edu.mx.utdelacosta.service.ITemaGrupalService;

@Service
public class TemaGrupalServiceJpa implements ITemaGrupalService{

	@Autowired
	private TemaGrupalRepository temaGrupalRepo;

	@Override
	public void guardar(TemaGrupal temaGrupal) { 
		temaGrupalRepo.save(temaGrupal);
	}

	@Override
	public List<TemaGrupal> buscarPorGrupo(Grupo grupo) {
		return temaGrupalRepo.findByGrupo(grupo);
	}

	@Override
	public TemaGrupal bucarPorId(Integer id) {
		Optional<TemaGrupal> optional = temaGrupalRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<TemaGrupal> buscarEntreFechasPorGrupo(Integer idGrupo, Date fechaInicio, Date fechaFin) {
		return temaGrupalRepo.findByGrupoAndFechaProgramada(idGrupo, fechaInicio, fechaFin);
	}

	@Override
	public List<TemaGrupal> buscarEntreFechasPorCarrera(Integer idCarrera, Integer idPeriodo, Date fechaInicio, Date fechaFin) {
		return temaGrupalRepo.findByCarreraAndFechaProgramada(idCarrera, idPeriodo, fechaInicio, fechaFin);
	}

	@Override
	public Integer TotalPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return temaGrupalRepo.findTotalByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}
	
}
