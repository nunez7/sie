package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.FolioDTO;

public interface IPagoGeneralService {
	List<PagoGeneral> buscarPorAlumno (Integer idAlumno, Integer status);
	
	List<PagoGeneral> buscarPorAlumnoYFolio(Integer idAlumno, String folio, Integer status);
	
	String findUltimaReferenciaSEP(String referencia);
	
	String findUltimaReferencia(String referencia);
	
	void guardar(PagoGeneral pagoGeneral);
	
	PagoGeneral buscarPorId(Integer idPago);
	
	void eliminar(PagoGeneral pagoGeneral);
	
	PagoGeneral buscarPorAlumnoYConceptoYActivo(Alumno alumno, Integer concepto);
	//PagoGeneral buscarUltimoPagoGeneral();
	
	PagoGeneral buscarPorAlumnoYConceptoYCargaHoraria(Integer idAlumno, Integer idConcepto, Integer idCargaHoraria);
	
	PagoGeneral buscarPorAlumnoYConceptoYCargaHorariaYCorte(Integer idAlumno, Integer idConcepto, Integer idCargaHoraria, Integer idCorteEvaluativo);
	
	/*
	PagoGeneral buscarPorAlumnoYConceptoYAsignatura(Integer idAlumno, Integer idConcepto, Integer idAsignatura);
	
	PagoGeneral buscarPorAlumnoYConceptoYAsignaturaYCorte(Integer idAlumno, Integer idConcepto, Integer idAsignatura, Integer idCorteEvaluativo);
	*/
	
	Integer contarPorAlumnoYStatus(Integer idAlumno, Integer status);
	
	List<FolioDTO> buscarFolioPorFolioONombreOCliente (String folio);
	
	List<PagoGeneral> buscarTodosPorFolio(String folio);
	
	FolioDTO buscarPorFolio(String folio);
	
	FolioDTO buscarReciboPorFolio(String folio);
	
	PagoGeneral buscarUltimoPorFolio(String folio);
}
