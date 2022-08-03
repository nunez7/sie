package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Instrumento;
import edu.mx.utdelacosta.model.MecanismoInstrumento;

public interface IMecanismoInstrumentoService {
	
	MecanismoInstrumento buscarPorId(Integer id);
	
	List<MecanismoInstrumento> buscarPorIdCargaHorariaEIdCorteEvaluativoYActivo(Integer idCargaHoraria, Integer idCorteEvaluativo, Boolean activo);
	
	void guardar (MecanismoInstrumento mecanismo);
	
	void eliminar (MecanismoInstrumento mecanismo);
	
	MecanismoInstrumento buscarPorIdYActivo(Integer idMecanismo, Boolean activo);
	
	MecanismoInstrumento buscarPorIdCargaHorariaEIdCorteEvaluativoEInstrumentoYActivo(Integer idCargaHoraria, Integer idCorteEvaluativo,Instrumento instrumento, Boolean activo);
	
	List<MecanismoInstrumento> buscarPorIdCargaHorariaYActivo(Integer idCargaHoraria, Boolean activo);
	
	Integer sumaPonderacionPorIdCargaHorariaEIdCorteEvaluativo(Integer idCargaHoraria, Integer idCorteEvaluativo);
}
