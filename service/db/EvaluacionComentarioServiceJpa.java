package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.EvaluacionComentario;
import edu.mx.utdelacosta.model.dto.ComentarioDTO;
import edu.mx.utdelacosta.repository.EvaluacionComentarioRepository;
import edu.mx.utdelacosta.service.IEvaluacionComentarioService;

@Service
public class EvaluacionComentarioServiceJpa implements IEvaluacionComentarioService{

	@Autowired
	private EvaluacionComentarioRepository evaComRepo;

	@Override
	public void guardar(EvaluacionComentario evaluacionComentario) {
		evaComRepo.save(evaluacionComentario);
	}

	@Override
	public EvaluacionComentario buscarEvaluacionComentarioPorPersona(Integer idPersona,
			Integer idEvaluacion, Integer idCarga) {
		return evaComRepo.findEvaluacionComentarioByPersona(idPersona, idEvaluacion, idCarga);
	}

	@Override
	public List<ComentarioDTO> buscarComentariosPorPersona(Integer idEvaluacion, Integer idCarrera, Integer idProfesor,
			Integer idMateria, Integer idPeriodo) {
		return evaComRepo.findComentarioByAlumno(idEvaluacion, idCarrera, idProfesor, idMateria, idPeriodo);
	}
		
}
