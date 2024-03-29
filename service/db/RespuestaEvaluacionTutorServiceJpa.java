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
	public Integer contarPorIdPersonaYIdGrupoYActivo(Integer idPersona, Integer idGrupo) {
		// TODO Auto-generated method stub
		return resEvaTuRepo.countByIdPersonaAndIdGrupoAndActivo(idPersona, idGrupo);
	}

	@Override
	public Integer sumarPonderacionPorIdPreguntaIdGrupoIdEvaluacion(Integer idPregunta, Integer idGrupo,
			Integer idEvaluacion) {
		// TODO Auto-generated method stub
		return resEvaTuRepo.sumPonderacionByIdPreguntaAndIdGrupoAndIdEvaluacion(idPregunta, idGrupo, idEvaluacion);
	}

	@Override
	public Integer contarPorIdPreguntaIdGrupoIdEvaluacion(Integer idPregunta, Integer idGrupo, Integer idEvaluacion) {
		// TODO Auto-generated method stub
		return resEvaTuRepo.countRespuestasByIdPreguntaAndIdGrupoAndIdEvaluacion(idPregunta, idGrupo, idEvaluacion);
	}

	@Override
	public Integer contarAlumnosPorIdGrupoIdPeriodo(Integer idGrupo, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return resEvaTuRepo.countAlumnosByIdGrupoAndIdPeriodo(idGrupo, idPeriodo);
	}

}
