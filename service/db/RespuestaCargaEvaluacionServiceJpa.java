package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public RespuestaCargaEvaluacion buscarRespuestaPorPregunta(Integer idEvaluacion,
			Integer idPregunta, Integer idPersona, Integer idCarga) {
		return respuestaCarEva.findRespuestaByPregunta(idEvaluacion, idPregunta, idPersona, idCarga);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OpcionRespuestaDTO> buscarOpcionesRespuestaYRespuestaPorPregunta(
			Integer idEvaluacion, Integer idPregunta, Integer idPersona, Integer idCarga) {
		return respuestaCarEva.findOpcionRespuestaAndRespuestaByPregunta(idEvaluacion, idPregunta, idPersona, idCarga);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarPorIdPersonaYEvaluacionYIdCargaHoraria(Integer idPersona, Integer idEvaluacion,
			Integer idCargaHoraria) {
		return respuestaCarEva.countByIdPersonaAndIdEvaluacionAndIdCargaHoraria(idPersona, idEvaluacion, idCargaHoraria);
	}

	@Override
	public Integer sumarPonderacionPorIdPreguntaIdCargaHorariaIdEvaluacion(Integer idPregunta, Integer idCargaHoraria,
			Integer idEvaluacion) {
		// TODO Auto-generated method stub
		return respuestaCarEva.sumPonderacionByIdPreguntaAndIdCargaHorariaAndIdEvaluacion(idPregunta, idCargaHoraria, idEvaluacion);
	}

	@Override
	public Integer contarPorIdPreguntaIdCargaHorariaIdEvaluacion(Integer idPregunta, Integer idCargaHoraria,
			Integer idEvaluacion) {
		// TODO Auto-generated method stub
		return respuestaCarEva.countRespuestasByIdPreguntaAndIdCargaHorariaAndIdEvaluacion(idPregunta, idCargaHoraria, idEvaluacion);
	}

	@Override
	public Integer contarAlumnosPorIdProfesorIdCarreraIdPeriodo(Integer idProfesor, Integer idCarrera,
			Integer idPeriodo) {
		// TODO Auto-generated method stub
		return respuestaCarEva.countAlumnosByIdProfesorAndIdCarreraAndIdPeriodo(idProfesor, idCarrera, idPeriodo);
	}

	@Override
	public Integer contarAlumnosPorIdProfesorIdPeriodo(Integer idProfesor, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return respuestaCarEva.countAlumnosByIdProfesorAndIdPeriodo(idProfesor, idPeriodo);
	}

}
