package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.RespuestaCargaEvaluacion;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;
import edu.mx.utdelacosta.repository.RespuestaCargaEvaluacionRepository;
import edu.mx.utdelacosta.service.IRespuestaCargaEvaluacionService;

@Service
public class RespuestaCargaEvaluacionServiceJpa implements IRespuestaCargaEvaluacionService{
	
	@Autowired
	private RespuestaCargaEvaluacionRepository respuestaCarEva;
	
	@Override
	public void guardar(RespuestaCargaEvaluacion respuestaCargaEvaluacion) {
		respuestaCarEva.save(respuestaCargaEvaluacion);
	}

	@Override
	public RespuestaCargaEvaluacion buscarRespuestaPorPregunta(Integer idEvaluacion,
			Integer idPregunta, Integer idPersona, Integer idCarga) {
		return respuestaCarEva.findRespuestaByPregunta(idEvaluacion, idPregunta, idPersona, idCarga);
	}
	
	@Override
	public List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(
			Integer idEvaluacion, Integer idPregunta, Integer idPersona, Integer idCarga) {
		return respuestaCarEva.findOpcionRespuestaAndRespuestaByPregunta(idEvaluacion, idPregunta, idPersona, idCarga);
	}

	@Override
	public Integer contarPorGrupoYCargaHoraria(Integer idEvaluacion, Integer idGrupo, Integer idCarga) {
		return respuestaCarEva.countRePuestasByAlumno(idEvaluacion, idGrupo, idCarga);
	}

}
