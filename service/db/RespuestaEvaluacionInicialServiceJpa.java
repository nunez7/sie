package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.RespuestaEvaluacionInicial;
import edu.mx.utdelacosta.model.dto.OpcionRespuestaDTO;
import edu.mx.utdelacosta.repository.RespuestaEvaluacionInicialRepository;
import edu.mx.utdelacosta.service.IRespuestaEvaluacionInicialService;

@Service
public class RespuestaEvaluacionInicialServiceJpa implements IRespuestaEvaluacionInicialService{

	@Autowired
	private RespuestaEvaluacionInicialRepository resEvaIniRepo;

	@Override
	public void guardar(RespuestaEvaluacionInicial respuesta) {
		resEvaIniRepo.save(respuesta);
	}
	
	@Override
	public List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(Integer idPregunta, Integer idPersona,Integer idEvaluacion, Integer idGrupo) {
		return resEvaIniRepo.findOpcionRespuestaAndRespuestaByPregunta(idPregunta, idPersona, idEvaluacion, idGrupo);
	}
	
	@Override
	public OpcionRespuestaDTO buscarRespuestaPorPregunta(Integer idPregunta, Integer idPersona, Integer idEvaluacion, Integer idGrupo) {
		return resEvaIniRepo.findRespuestaByPregunta(idPregunta, idPersona, idEvaluacion, idGrupo);
	}

	
	@Override
	public RespuestaEvaluacionInicial buscarPorId(Integer id) {
		Optional<RespuestaEvaluacionInicial> optional = resEvaIniRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public RespuestaEvaluacionInicial buscarRespuestaCerradaPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona, Integer idGrupo) {
		return resEvaIniRepo.findRespuestaCerradaByPregunta(idEvaluacion, idPregunta, idPersona, idGrupo);
	}

	@Override
	public RespuestaEvaluacionInicial buscarRespuestaAbiertaPorPregunta(Integer idEvaluacion, Integer idPregunta, Integer idPersona, Integer idGrupo) {
		return resEvaIniRepo.findRespuestaAbiertaByPregunta(idEvaluacion, idPregunta, idPersona, idGrupo);
	}
	
}
