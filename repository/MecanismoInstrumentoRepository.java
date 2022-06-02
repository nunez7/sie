package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Instrumento;
import edu.mx.utdelacosta.model.MecanismoInstrumento;

public interface MecanismoInstrumentoRepository extends CrudRepository<MecanismoInstrumento, Integer>{
	
	List<MecanismoInstrumento> findAll();
	
	List<MecanismoInstrumento> findByIdCargaHorariaAndIdCorteEvaluativoAndActivo(Integer idCargaHoraria, Integer idCorteEvaluativo, Boolean activo);
	
	List<MecanismoInstrumento> findByIdCargaHorariaAndActivoOrderByIdCorteEvaluativo(Integer idCargaHoraria, Boolean activo);
	
	MecanismoInstrumento findByIdCargaHorariaAndIdCorteEvaluativoAndInstrumentoAndActivo(Integer idCargaHoraria, Integer idCorteEvaluativo, Instrumento instrumento, Boolean activo);
	
	Optional<MecanismoInstrumento> findByIdAndActivo(Integer id, Boolean activo);
	
}
