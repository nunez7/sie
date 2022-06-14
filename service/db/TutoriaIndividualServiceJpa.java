package edu.mx.utdelacosta.service.db;

import java.util.List;

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
	public List<TutoriaIndividual> buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo) {
		return tutoriaIndRepo.findByAlumnoAndGrupo(alumno, grupo);
	}
	
}
