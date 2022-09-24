package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.ComentarioEvaluacionTutor;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;
import edu.mx.utdelacosta.repository.ComentarioEvaluacionTutorRepository;
import edu.mx.utdelacosta.service.IComentarioEvaluacionTutorService;

@Service
public class ComentarioEvaluacionTutorServiceJpa implements IComentarioEvaluacionTutorService{

	@Autowired
	private ComentarioEvaluacionTutorRepository comEvaTurtorRepo;
	
	@Override
	public void guardar(ComentarioEvaluacionTutor comentarioEvaluacionTutor) {
		comEvaTurtorRepo.save(comentarioEvaluacionTutor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ComentarioEvaluacionTutor buscarComentarioPorPersona(Integer idPersona, Integer idGrupo,
			Integer idEvaluacion) {
		return comEvaTurtorRepo.findEvaluacionComentarioByPersona(idPersona, idGrupo, idEvaluacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComentarioDTO> buscarComentariosPorGrupo(Integer idEvaluacion, Integer idGrupo) {
		return comEvaTurtorRepo.findComentarioByGrupo(idEvaluacion, idGrupo);
	}

}
