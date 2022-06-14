package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<ProgramacionTutoria> buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo) {
		return proTutoriaRepo.findByAlumnoAndGrupo(alumno, grupo);
	}

	@Override
	public void guardar(ProgramacionTutoria tutoria) {
		proTutoriaRepo.save(tutoria);
	}

}
