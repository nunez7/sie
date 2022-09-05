package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.TutoriaIndividual;
import edu.mx.utdelacosta.repository.TutoriaIndividualRepository;
import edu.mx.utdelacosta.service.ITutoriaIndividualService;

@Service
public class TutoriaIndividualServiceJpa implements ITutoriaIndividualService{
	
	@Autowired
	private TutoriaIndividualRepository tutoriaIndRepo;

	@Override
	public void guardar(TutoriaIndividual tutoriaIndividual) { 
		tutoriaIndRepo.save(tutoriaIndividual);
	}
	
	@Override
	public TutoriaIndividual buscarPorId(Integer id) {
		Optional<TutoriaIndividual> optional = tutoriaIndRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<TutoriaIndividual> buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo) {
		return tutoriaIndRepo.findByAlumnoAndGrupo(alumno, grupo);
	}
	
	@Override
	public List<TutoriaIndividual> buscarUltimas5PorAlumno(Alumno alumno) {
		return tutoriaIndRepo.findFirst5ByAlumnoOrderByFechaRegistroDesc(alumno);
	}
	
	@Override
	public List<TutoriaIndividual> buscarPorAlumnoYValidada(Alumno alumno, Boolean validada) {
		return tutoriaIndRepo.findByAlumnoAndValidadaOrderByFechaRegistroDesc(alumno, validada);
	}
	
	@Override
	public TutoriaIndividual ultimoRegistro() {
		return tutoriaIndRepo.findTopByOrderByIdDesc();
	}

	@Override
	public List<TutoriaIndividual> buscarEntreFechasPorGrupoYAlumno(Integer idGrupo, Integer idAlumno, Date fechaInicio, Date fechaFin) {
		return tutoriaIndRepo.findByGrupoAndPersonaAndFechaTutoria(idGrupo, idAlumno, fechaInicio, fechaFin);
	}

	@Override
	public List<TutoriaIndividual> buscarPorAlumno(Alumno alumno) {
		return tutoriaIndRepo.findByAlumnoOrderByFechaRegistroDesc(alumno);
	}

	@Override
	public List<TutoriaIndividual> buscarEntreFechasPorGrupo(Integer idGrupo, Date fechaInicio, Date fechaFin) {
		return tutoriaIndRepo.findByGrupoAndFechaTutoria(idGrupo, fechaInicio, fechaFin);
	}

	@Override
	public List<TutoriaIndividual> buscarEntreFechasPorCarrera(Integer idCarrera, Integer idPeriodo, Date fechaInicio, Date fechaFin) {
		return tutoriaIndRepo.findByCarreraAndFechaTutoria(idCarrera, idPeriodo, fechaInicio, fechaFin);
	}

	@Override
	public Integer totalPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return tutoriaIndRepo.findTotalByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

	@Override
	public Integer totalDistinctAlumnoPorCarreraPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return tutoriaIndRepo.findTotalDistinctAlumnoByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

}
