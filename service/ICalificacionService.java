package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Calificacion;
import edu.mx.utdelacosta.model.MecanismoInstrumento;
import edu.mx.utdelacosta.model.dtoreport.CalificacionInstrumentoDTO;

public interface ICalificacionService {
	List<Calificacion> buscarPorAlumnoYCargaH(Integer idAlumno, Integer idCarga);

	void guardar(Calificacion calificacion);

	void eliminar(Calificacion calificacion);
	
	Integer contarPorIdMecanismoInstrumento(Integer idMecanismo);

	List<Calificacion> buscarPorMecanismoInstrumento(MecanismoInstrumento mecanismoInstrumento);

	Calificacion buscarPorId(Integer id);

	List<Calificacion> buscarPorIdAlumnoEIdCargaHorariaEIdCorteEvaluativo(Integer idAlumno, Integer IdCargaHoraria,
			Integer idCorteEvaluativo);

	List<Calificacion> buscarPorAlumno(Alumno alumno);

	Calificacion buscarPorAlumnoYMecanismoInstrumento(Alumno alumno, MecanismoInstrumento mecanismoInstrumento);

	Integer buscarCalificacionPorAlumnoYMecanismoInstrumento(Integer idAlumno, Integer idMecanismo);

	List<CalificacionInstrumentoDTO> findByCargaHorariaAndCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo);

	CalificacionInstrumentoDTO buscarPorCargaHorariaYCorteEvaluativoEInstrumento(Integer idAlumno, Integer idCargaHoraria,
			Integer idCorteEvaluativo, Integer idInstrumento);

}
