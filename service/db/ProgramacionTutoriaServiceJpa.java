package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.ProgramacionTutoria;
import edu.mx.utdelacosta.repository.ProgramacionTutoriaRepository;
import edu.mx.utdelacosta.service.IProgramacionTutoriaService;

@Service
public class ProgramacionTutoriaServiceJpa implements IProgramacionTutoriaService{
	
	@Autowired
	private ProgramacionTutoriaRepository proTutoriaRepo;

	@Override
	@Transactional(readOnly = true)
	public List<ProgramacionTutoria> buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo) {
		return proTutoriaRepo.findByAlumnoAndGrupoOrderByFechaAsc(alumno, grupo);
	}

	@Override
	public void guardar(ProgramacionTutoria tutoria) {
		proTutoriaRepo.save(tutoria);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProgramacionTutoria> buscarPorGrupo(Grupo grupo) {
		return proTutoriaRepo.findByGrupoOrderByFechaAsc(grupo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProgramacionTutoria> buscarPorAlumno(Alumno alumno) {
		return proTutoriaRepo.findByAlumnoOrderByFechaAsc(alumno);
	}

}
