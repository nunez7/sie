package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.RespuestaEvaluacionTutor;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;
import edu.mx.utdelacosta.repository.RespuestaEvaluacionTutorRepository;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionTutorService;

@Service
public class RespuestaEvaluacionTutorServiceJpa implements IRespuestaEvaluacionTutorService{

	@Autowired
	private RespuestaEvaluacionTutorRepository resEvaTuRepo;
	
	@Override
	public void guardar(RespuestaEvaluacionTutor respuestaEvaluacionTutor) {
		resEvaTuRepo.save(respuestaEvaluacionTutor);		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(
			Integer idPregunta, Integer idPersona, Integer idEvaluacion, Integer idGrupo) {
		return resEvaTuRepo.findOpcionRespuestaAndRespuestaByPregunta(idPregunta, idPersona, idEvaluacion, idGrupo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public RespuestaEvaluacionTutor buscarRespuestaPorPregunta(Integer idPersona, Integer idEvaluacion,
			Integer idPregunta, Integer idGrupo) {
		return resEvaTuRepo.findRespuestaByPregunta(idPersona, idEvaluacion, idPregunta, idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public int contarEncuestadosPorGrupo(Integer idEvaluacion, Integer idGrupo) {
		return resEvaTuRepo.countEncuestadosByGrupo(idEvaluacion, idGrupo);
	}

}
